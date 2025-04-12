package com.flipfit.flipfit.repository;

import com.flipfit.flipfit.model.Booking;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
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
}
