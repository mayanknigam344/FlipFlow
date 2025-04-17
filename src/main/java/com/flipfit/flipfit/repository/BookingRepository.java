package com.flipfit.flipfit.repository;

import com.flipfit.flipfit.model.Booking;
import com.flipfit.flipfit.model.Center;
import com.flipfit.flipfit.model.slot.Slot;
import com.flipfit.flipfit.model.user.User;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookingRepository {

    // String - bookingId
    Map<String, Booking> bookings = new HashMap<>();

    public void addBooking(Booking booking){
        bookings.put(booking.getBookingId(),booking);
    }

    public void removeBooking(String bookingId){
        bookings.remove(bookingId);
    }

    public Booking getBooking(String bookingId){
        return bookings.get(bookingId);
    }

    public List<Booking> getBookingsForUserAtCenterOnDate(User user, Center center, Slot slot) {
        List<Booking> bookings = getBookingsForUserOnDate(user,slot.getSlotDateTime());
        return bookings.stream()
                .filter(b -> b.getCenter().getCenterId().equals(center.getCenterId()))
                .toList();
    }

    private List<Booking> getBookingsForUserOnDate(User user, LocalDateTime date){
        return user.getBookings()
                .stream()
                .filter(booking -> booking.getUser().getUserId().equals(user.getUserId()))
                .filter(b -> b.getBookingDateTime().toLocalDate().equals(date.toLocalDate()))
                .toList();
    }
}
