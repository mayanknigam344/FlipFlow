package com.flipfit.flipfit.model.slot;

import com.flipfit.flipfit.model.WorkoutVariation;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;
import java.util.Map;

@Getter
@Setter
public abstract class Slot {
    String slotId;
    Date slotDate;
    Time startTime;
    // Integer - NumberOfSeats
    Map<WorkoutVariation,Integer> workoutVariationVsSeatCount;
    SlotType slotType;
}
