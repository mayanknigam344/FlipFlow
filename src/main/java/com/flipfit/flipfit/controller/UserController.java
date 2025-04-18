package com.flipfit.flipfit.controller;

import com.flipfit.flipfit.model.booking.Booking;
import com.flipfit.flipfit.model.user.User;
import com.flipfit.flipfit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    public List<Booking> getAllBookingForAUser(User user){
        return userService.getAllBookingsForAUser(user);
    }
}
