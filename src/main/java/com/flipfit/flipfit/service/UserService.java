package com.flipfit.flipfit.service;

import com.flipfit.flipfit.model.Booking;
import com.flipfit.flipfit.model.user.User;
import com.flipfit.flipfit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public User addUser(User user){
        return userRepository.addUser(user);
    }

    public List<Booking> getAllBookingsForAUser(User user){
        return user.getBookings();
    }

}
