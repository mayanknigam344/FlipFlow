package com.flipfit.flipfit.model;

import com.flipfit.flipfit.model.slot.Slot;
import com.flipfit.flipfit.model.user.User;
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
