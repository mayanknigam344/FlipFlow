package com.flipflow.controller;

import com.flipflow.model.booking.Booking;
import com.flipflow.model.Center;
import com.flipflow.model.WorkoutVariation;
import com.flipflow.model.slot.Slot;
import com.flipflow.model.user.User;
import com.flipflow.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    public Booking book(User user, Center center, Slot slot , WorkoutVariation workoutVariation){
        return bookingService.book(user,center,workoutVariation,slot);
    }

    public void cancel(User user, Center center, Slot slot){
         bookingService.cancelBooking(user,center,slot);
    }
}
