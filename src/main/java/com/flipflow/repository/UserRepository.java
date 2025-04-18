package com.flipflow.repository;


import com.flipflow.exception.UserAlreadyPresentException;
import com.flipflow.model.booking.Booking;
import com.flipflow.model.Center;
import com.flipflow.model.slot.Slot;
import com.flipflow.model.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class UserRepository {

     HashMap<String, User> usersById = new HashMap<>();

    public void addUser(User user) {
        if(usersById.containsKey(user.getUserId())) {
            throw new UserAlreadyPresentException("User already exists with ID: " + user.getUserId());
        }
        usersById.put(user.getUserId(),user);
        log.info("Successfully added user with ID: {}", user.getUserId());
    }

    public User getUserById(String userId){
        return usersById.get(userId);
    }

    public void addBooking(User user, Booking booking){
        log.info("Adding booking with ID: {} for user with ID: {}", booking.getBookingId(), user.getUserId());
        user.getBookings().add(booking);
    }

    public void replaceBooking(User user, Booking booking, Booking updatedBooking){
        removeBooking(user, booking);
        addBooking(user,updatedBooking);
    }

    public void removeBooking(User user, Booking booking){
        List<Booking> bookings = user.getBookings();
        if (bookings.remove(booking)) {
            log.info("Booking with ID: {} removed for user with ID: {}", booking.getBookingId(), user.getUserId());
        } else {
            log.warn("Booking with ID: {} not found for user with ID: {}", booking.getBookingId(), user.getUserId());
        }
    }

    public Optional<Booking> getBooking(User user, Center center, Slot slot){
        return user.getBookings().stream()
                .filter(booking -> booking.getCenter().equals(center) &&
                        booking.getSlot().equals(slot) &&
                        booking.getSlot().getSlotDateTime().equals(slot.getSlotDateTime()))
                .findFirst();
    }

    public List<Booking> getAllBookingsForUserInADay(String userId, LocalDateTime dateTime){
        User user = getUserById(userId);
        return user.getBookings()
                .stream()
                .filter(booking -> booking.getBookingDateTime().toLocalDate().equals(dateTime.toLocalDate()))
                .toList();
    }
}
