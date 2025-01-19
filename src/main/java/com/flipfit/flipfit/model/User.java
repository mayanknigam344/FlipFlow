package com.flipfit.flipfit.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class User {
    Long userId;
    String userName;
    List<MyBooking> bookings;
    UserType userType;
}
