package com.flipfit.flipfit.service;

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

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
//TODO : Need to refactor this code
public class BookingService {

    private final  SlotService slotService;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    List<Booking> premiumBookingQueue = new ArrayList<>();
    List<Booking> normalBookingQueue = new ArrayList<>();

    public Booking book(User user, Center center, WorkoutVariation workoutVariation, Slot slot, Date date) {
        Booking booking=null;

        // check if user is allowed to do booking or not.
        List<Booking> bookingListForAUserInADay= userRepository.getAllBookingsForUserInADay(user,date);
        List<Booking> bookingPerCenter = bookingListForAUserInADay.stream()
                .filter(bookingInCenter -> bookingInCenter.getCenter().getCenterId().equals(center.getCenterId()))
                .toList();

        if(bookingPerCenter.size()>3){
            throw new UserBookingMaxLimitReachedForAGivenCenter("User already have 3 bookings in a given center");
        }

        // User is allowed, so get all the slots for a given center for a given date
        // If user is VIP User then it will see only premium slots
        List<Slot> slotsInACenterForAGivenDate = slotService.getSlotsForACenterAndGivenDate(center,user,date);

        //check if that slot exists or not.
        if(slotsInACenterForAGivenDate.contains(slot)){
            // Get the seat in the slot for a given workout variation
            int seatsForWorkoutVariation = slotService.getSeatCountInaSlotForaWorkoutVariation(slot,workoutVariation);
            // make a booking object, set bookingID only when booking is successful.
            booking = Booking.builder().bookingDate(date).center(center).slot(slot).build();
            if(seatsForWorkoutVariation>0){
                slot.getWorkoutVariationVsSeatCount().put(workoutVariation, seatsForWorkoutVariation-1);
                String bookingId = generateUserId();
                booking.setBookingId(bookingId);
                bookingRepository.addBooking(booking);
                userRepository.addBookingForUser(user,booking);
                log.info("Booking is Successful with booking id {} for user {} and added in the bookingList",bookingId,user.getUserId());
            }else{
                // otherwise add the booking in list(based on user type).
                if(user.getUserType().equals(UserType.FK_VIP_USER)){
                    premiumBookingQueue.add(booking);
                }else{
                    normalBookingQueue.add(booking);
                }
                log.info("Booking is unsuccessful but added in waiting queue");
            }
        }
        return booking;
    }

    private String generateUserId() {
        return UUID.randomUUID().toString();
    }
}
