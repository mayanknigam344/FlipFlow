package com.flipfit.flipfit.controller;

import com.flipfit.flipfit.model.Booking;
import com.flipfit.flipfit.model.Center;
import com.flipfit.flipfit.model.WorkoutVariation;
import com.flipfit.flipfit.model.slot.Slot;
import com.flipfit.flipfit.model.user.User;
import com.flipfit.flipfit.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BookingController {
    @Autowired BookingService bookingService;

    public Booking book(User user, Center center, Slot slot , WorkoutVariation workoutVariation){
        return bookingService.book(user,center,workoutVariation,slot);
    }

}
