package com.flipfit.flipfit.repository;


import com.flipfit.flipfit.model.Booking;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookingRepository {

    List<Booking> bookings = new ArrayList<>();

    public void addBooking(Booking booking){
        bookings.add(booking);
    }

    public void removeBooking(Booking booking){
        bookings.remove(booking);
    }
}
