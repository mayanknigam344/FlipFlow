package com.flipflow.controller;

import com.flipflow.model.booking.Booking;
import com.flipflow.model.user.User;
import com.flipflow.service.UserService;
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
