package com.flipflow.service;

import com.flipflow.model.booking.Booking;
import com.flipflow.model.user.User;
import com.flipflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public void addUser(User user){
        userRepository.addUser(user);
    }

    public List<Booking> getAllBookingsForAUser(User user){
        return user.getBookings();
    }

}
