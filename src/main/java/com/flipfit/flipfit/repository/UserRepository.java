package com.flipfit.flipfit.repository;


import com.flipfit.flipfit.exception.UserAlreadyPresentException;
import com.flipfit.flipfit.model.Booking;
import com.flipfit.flipfit.model.Center;
import com.flipfit.flipfit.model.slot.Slot;
import com.flipfit.flipfit.model.user.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Repository;

import java.util.*;

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

    public Optional<Booking> getBookingForUser(User user, Center center, Slot slot){
        List<Booking> bookings = getAllBookingsForUser(user);
        return bookings.stream()
                .filter(booking -> booking.getCenter().equals(center))
                .filter(booking-> DateUtils.isSameDay(booking.getBookingDate(),slot.getSlotDate()))
                .filter(booking -> booking.getSlot().getSlotId().equals(slot.getSlotId()))
                .findFirst();

    }

    public List<Booking> getAllBookingsForUserInADay(User user, Date date){
        List<Booking> bookings = getAllBookingsForUser(user);
        return bookings.stream()
                .filter(booking -> DateUtils.isSameDay(booking.getBookingDate(),date))
                .toList();
    }

    public List<Booking> getAllBookingsForUser(User user){
        return userIdVsBookings.get(user.getUserId());
    }

    public void removeBooking(Booking booking){
        List<Booking> bookings = getAllBookingsForUser(booking.getUser());
        bookings.remove(booking);
    }
}
