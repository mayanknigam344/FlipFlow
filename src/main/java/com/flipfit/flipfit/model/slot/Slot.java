package com.flipfit.flipfit.model.slot;

import com.flipfit.flipfit.model.WorkoutVariation;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Getter
@Setter
public abstract class Slot {
    Long slotId;
    Date slotDate;
    Time startTime;
    List<WorkoutVariation> workoutVariationInSlot;
    SlotType slotType;
}
