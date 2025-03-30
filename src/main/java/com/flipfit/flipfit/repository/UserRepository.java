package com.flipfit.flipfit.repository;


import com.flipfit.flipfit.exception.UserAlreadyPresentException;
import com.flipfit.flipfit.model.Booking;
import com.flipfit.flipfit.model.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
@Slf4j
public class UserRepository {

     List<User> users = new ArrayList<>();

    // String - userId
    HashMap<String, List<Booking>> userIdVsBookings = new HashMap<>();

    public User addUser(User user) {
        if(users.contains(user)) {
            throw new UserAlreadyPresentException("User already present");
        }
        users.add(user);
        log.info("Added user:{}", user.getUserId());
        return user;
    }

    public void addBookingForUser(User user, Booking booking){
        userIdVsBookings.computeIfAbsent(user.getUserId(), k -> new ArrayList<>()).add(booking);
    }

    public List<Booking> getAllBookingsForUserInADay(User user, Date date){
        List<Booking> bookings = getAllBookingsForUser(user);
        return bookings.stream()
                .filter(booking -> booking.getBookingDate().equals(date))
                .toList();
    }

    public List<Booking> getAllBookingsForUser(User user){
        return userIdVsBookings.get(user.getUserId());
    }
}
