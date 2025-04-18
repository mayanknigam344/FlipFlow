package com.flipfit.flipfit.controller;

import com.flipfit.flipfit.model.booking.Booking;
import com.flipfit.flipfit.model.Center;
import com.flipfit.flipfit.model.WorkoutVariation;
import com.flipfit.flipfit.model.slot.Slot;
import com.flipfit.flipfit.model.user.User;
import com.flipfit.flipfit.service.BookingService;
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
