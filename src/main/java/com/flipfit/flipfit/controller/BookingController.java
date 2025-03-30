package com.flipfit.flipfit.controller;

import com.flipfit.flipfit.model.Booking;
import com.flipfit.flipfit.model.Center;
import com.flipfit.flipfit.model.WorkoutVariation;
import com.flipfit.flipfit.model.slot.Slot;
import com.flipfit.flipfit.model.user.User;
import com.flipfit.flipfit.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.sql.Date;

@Controller
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    public Booking book(User user, Center center, Slot slot , WorkoutVariation workoutVariation, Date date){
        return bookingService.book(user,center,workoutVariation,slot,date);
    }
}
