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

import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingService {

    private final SlotService slotService;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    PriorityQueue<Booking> bookingQueue = new PriorityQueue<>(
            (b1, b2) -> Integer.compare(b2.getPriority(), b1.getPriority())
    );

    public Booking book(User user, Center center, WorkoutVariation workoutVariation, Slot slot) {
        log.info("Booking flow started");
        Booking booking;

        // check if user is allowed to do booking or not.
        log.info("Validating if user with user id{} is allowed to do booking or not",user.getUserId());
        List<Booking> bookingListForAUserInADay= userRepository.getAllBookingsForUserInADay(user,slot.getSlotDateAndTime());
        List<Booking> bookingPerCenter = bookingListForAUserInADay.stream()
                .filter(bookingInCenter -> bookingInCenter.getCenter().getCenterId().equals(center.getCenterId()))
                .toList();

        if(bookingPerCenter.size()>3){
            throw new UserBookingMaxLimitReachedForAGivenCenter("User already have 3 bookings in a given center");
        }
        log.info("Validated the user bookings. Great!! User is allowed to do booking.");

        // User is allowed, so get all the slots for a given center for a given date.
        // If user is VIP User then it will see both types of slots.
        // If user is Normal User then it will see only normal slots.
        List<Slot> slotsInACenterForAGivenDateAndTime = slotService.getSlotsForACenterAndGivenDateAndTime(center,user,slot.getSlotDateAndTime());

        // checking if slot exists or not
        if(!slotsInACenterForAGivenDateAndTime.contains(slot)) {
            throw new SlotNotFoundException("Slot not found in the given center");
        }

        // Get the seat count in the slot for a given workout variation
        int seatsForWorkoutVariation = slotService.getSeatCountInaSlotForAWorkoutVariation(slot,workoutVariation);
        log.info("Seat count for a given workout variation {} and slot {} is {}",workoutVariation,slot.getSlotId(),seatsForWorkoutVariation);

        // make a booking object, setting bookingID only when booking is successful.
        // Priority - 1 for VIP, 0 for Normal
        booking = Booking.builder().bookingDateTime(slot.getSlotDateAndTime()).center(center).slot(slot).priority(user.getUserType().equals(UserType.FK_VIP_USER) ? 1 : 0).build();
        if(seatsForWorkoutVariation>0){
            // Updating the seat count for a given workout variation and slot.
            slot.getWorkoutVariationVsSeatCount().put(workoutVariation, seatsForWorkoutVariation-1);

            String bookingId = generateUserId();
            booking = booking.toBuilder().workoutVariation(workoutVariation).bookingId(bookingId).build();

            bookingRepository.addBooking(booking);
            userRepository.addBookingForUser(user,booking);
            log.info("Booking is Successful with booking id {} for user {} and added in the bookingList",bookingId,user.getUserId());
        }else{
            // otherwise add the booking in list(based on user type).
            bookingQueue.add(booking);
            log.info("Added in the waiting queue");
        }
        log.info("Booking flow completed");
        return booking;
    }

    public void cancelBooking(User user , Center center, Slot slot){
        log.info("Cancel flow started");
        Optional<Booking> booking = userRepository.getBookingForUser(user,center,slot);
        if(booking.isPresent()){
            //removing the booking from bookings and user lists
            bookingRepository.removeBooking(booking.get().getBookingId());
            userRepository.removeBooking(user, booking.get());

            // increment seat in case of cancel
            slotService.incrementSeatCountInCaseOfCancel(booking.get().getSlot(),booking.get().getWorkoutVariation());
            if(bookingQueue.isEmpty()){
                log.info("Cancel flow completed, also there are no bookings in waiting queue");
                return;
            }
            Booking notBooked = bookingQueue.poll();
            log.info("Fetching the booking{} and priority {} from waiting queue",notBooked,booking.get().getPriority());
            book(notBooked.getUser(),notBooked.getCenter(),notBooked.getWorkoutVariation(),notBooked.getSlot());
        }
    }

    private String generateUserId() {
        return UUID.randomUUID().toString();
    }
}
