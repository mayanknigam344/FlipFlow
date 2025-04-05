package com.flipfit.flipfit.model;

import com.flipfit.flipfit.model.slot.Slot;
import com.flipfit.flipfit.model.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Setter
@Getter
@Builder
public class Booking {
    private String bookingId;
    private Date bookingDate;
    private Center center;
    private Slot slot;
    private WorkoutVariation workoutVariation;
    private User user;
    // 0 for Normal 1 for VIP
    private int priority;
}
