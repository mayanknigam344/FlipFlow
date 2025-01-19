package com.flipfit.flipfit.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Booking {
    private String bookingId;
    private String bookingDate;
    private Center center;
    private Slot slot;
    private User user;
}
