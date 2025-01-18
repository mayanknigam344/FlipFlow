package com.flipfit.flipfit.controller;

import com.flipfit.flipfit.model.User;
import com.flipfit.flipfit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    @Autowired UserService userService;

    public User addUser(User user) {
        return userService.addUser(user);
    }

}
