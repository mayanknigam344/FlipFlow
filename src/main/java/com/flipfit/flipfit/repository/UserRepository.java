package com.flipfit.flipfit.repository;


import com.flipfit.flipfit.exception.UserAlreadyPresentException;
import com.flipfit.flipfit.model.Booking;
import com.flipfit.flipfit.model.Center;
import com.flipfit.flipfit.model.slot.Slot;
import com.flipfit.flipfit.model.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class UserRepository {

     List<User> users = new ArrayList<>();

    public User addUser(User user) {
        if(users.contains(user)) {
            throw new UserAlreadyPresentException("User already present");
        }
        users.add(user);
        log.info("Added user:{}", user.getUserId());
        return user;
    }

    public void addBookingForUser(User user, Booking booking){
        log.info("Booking added for user {} with booking id {}", user.getUserId(),booking.getBookingId());
        user.getBookings().add(booking);
    }

    public Optional<Booking> getBookingForUser(User user, Center center, Slot slot){
        return user.getBookings()
                .stream()
                .filter(booking -> booking.getCenter().equals(center))
                .filter(booking -> booking.getSlot().getSlotId().equals(slot.getSlotId()))
                .filter(booking-> booking.getSlot().getSlotDateAndTime().equals(slot.getSlotDateAndTime()))
                .findFirst();
    }

    public List<Booking> getAllBookingsForUserInADay(User user, LocalDateTime dateTime){
        return user.getBookings()
                .stream()
                .filter(booking -> booking.getBookingDateTime().toLocalDate().equals(dateTime.toLocalDate()))
                .toList();
    }

    public void removeBooking(User user, Booking booking){
        List<Booking> bookings = user.getBookings();
        bookings.remove(booking);
    }

    public List<Booking> getAllBookings(User user){
        return user.getBookings();
    }
}
