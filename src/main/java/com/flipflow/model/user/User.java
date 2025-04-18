package com.flipflow.model.user;

import com.flipflow.model.booking.Booking;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class User {
    String userId;
    String userName;
    UserType userType;
    List<Booking> bookings;

    public boolean isVip() {
        return this.userType == UserType.FK_VIP_USER;
    }
}
