package com.flipfit.flipfit.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Getter
@Setter
public class Slot {
    Long slotId;
    Date slotDate;
    Time startTime;
    List<WorkoutVariation> workoutVariationInSlot;
    int seatCount;
    List<User> users;
    SlotType slotType;
    boolean isAvailable;
}
