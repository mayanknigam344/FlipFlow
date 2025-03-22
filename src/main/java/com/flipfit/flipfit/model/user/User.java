package com.flipfit.flipfit.model.user;

import com.flipfit.flipfit.model.Center;
import com.flipfit.flipfit.model.MyBooking;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
public abstract class User {
    Long userId;
    String userName;
    List<MyBooking> bookings;
    UserType userType;

    boolean ifUserIsAllowedToBookSlotInCenterForAGivenDay(User user, Center center, Date date){
        List<MyBooking> myBookings = user.getBookings();
        List<MyBooking> alreadyBookedForAGivenCenter = myBookings.stream()
                .filter(myBooking -> myBooking.getSlot().getSlotDate().equals(date))
                .filter(myBooking -> myBooking.getCenter().equals(center))
                .toList();
        return alreadyBookedForAGivenCenter.size()<=3;
    }
}
