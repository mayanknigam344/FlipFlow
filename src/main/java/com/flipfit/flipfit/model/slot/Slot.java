package com.flipfit.flipfit.model.slot;

import com.flipfit.flipfit.model.WorkoutVariation;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
public abstract class Slot {
    Long slotId;
    Date slotDate;
    Time startTime;
    WorkoutVariation workoutVariation;
    SlotType slotType;
}
