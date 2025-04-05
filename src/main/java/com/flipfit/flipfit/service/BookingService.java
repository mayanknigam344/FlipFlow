package com.flipfit.flipfit.service;

import com.flipfit.flipfit.exception.SlotNotFoundException;
import com.flipfit.flipfit.exception.UserBookingMaxLimitReachedForAGivenCenter;
import com.flipfit.flipfit.model.Booking;
import com.flipfit.flipfit.model.Center;
import com.flipfit.flipfit.model.WorkoutVariation;
import com.flipfit.flipfit.model.slot.Slot;
import com.flipfit.flipfit.model.user.User;
import com.flipfit.flipfit.model.user.UserType;
import com.flipfit.flipfit.repository.BookingRepository;
import com.flipfit.flipfit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingService {

    private final  SlotService slotService;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    PriorityQueue<Booking> bookingQueue = new PriorityQueue<>(
            (b1, b2) -> Integer.compare(b2.getPriority(), b1.getPriority())
    );

    public Booking book(User user, Center center, WorkoutVariation workoutVariation, Slot slot) {
        Booking booking=null;

        // check if user is allowed to do booking or not.
        List<Booking> bookingListForAUserInADay= userRepository.getAllBookingsForUserInADay(user,slot.getSlotDate());
        List<Booking> bookingPerCenter = bookingListForAUserInADay.stream()
                .filter(bookingInCenter -> bookingInCenter.getCenter().getCenterId().equals(center.getCenterId()))
                .toList();

        if(bookingPerCenter.size()>3){
            throw new UserBookingMaxLimitReachedForAGivenCenter("User already have 3 bookings in a given center");
        }

        // User is allowed, so get all the slots for a given center for a given date
        // If user is VIP User then it will see only premium slots
        List<Slot> slotsInACenterForAGivenDateAndTime = slotService.getSlotsForACenterAndGivenDateAndTime(center,user,slot.getSlotDate(),slot.getStartTime());

        if(!slotsInACenterForAGivenDateAndTime.contains(slot)) {
            throw new SlotNotFoundException("Slot not found in the given center");
        }

        // Get the seat in the slot for a given workout variation
        int seatsForWorkoutVariation = slotService.getSeatCountInaSlotForAWorkoutVariation(slot,workoutVariation);

        // make a booking object, set bookingID only when booking is successful.
        booking = Booking.builder().bookingDate(slot.getSlotDate()).center(center).slot(slot).build();
        if(seatsForWorkoutVariation>0){
                slot.getWorkoutVariationVsSeatCount().put(workoutVariation, seatsForWorkoutVariation-1);
                String bookingId = generateUserId();
                booking.setBookingId(bookingId);
                booking.setWorkoutVariation(workoutVariation);
                bookingRepository.addBooking(booking);
                userRepository.addBookingForUser(user,booking);
                log.info("Booking is Successful with booking id {} for user {} and added in the bookingList",bookingId,user.getUserId());
        }else{
            // otherwise add the booking in list(based on user type).
            // 1 for VIP, 0 for Normal
            booking.setPriority(user.getUserType().equals(UserType.FK_VIP_USER) ? 1 : 0);
            bookingQueue.add(booking);
            log.info("Added in the waiting queue");
        }
        return booking;
    }

    public void cancelBooking(User user , Center center, Slot slot){
        Optional<Booking> booking = userRepository.getBookingForUser(user,center,slot);
        if(booking.isPresent()){
            //removing the booking from bookings and user lists
            bookingRepository.removeBooking(booking.get());
            userRepository.removeBooking(booking.get());

            // increment seat in case of cancel
            slotService.incrementSeatCountInCaseOfCancel(booking.get().getSlot(),booking.get().getWorkoutVariation());

            Booking notBooked = bookingQueue.poll();
            book(notBooked.getUser(),notBooked.getCenter(),notBooked.getWorkoutVariation(),notBooked.getSlot());
        }
    }

    private String generateUserId() {
        return UUID.randomUUID().toString();
    }
}
