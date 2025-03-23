package com.flipfit.flipfit.model.slot;

import com.flipfit.flipfit.model.WorkoutVariation;

import java.sql.Date;
import java.sql.Time;

public class NormalSlot extends Slot {
    public NormalSlot(Long slotId, Date slotDate, Time startTime, WorkoutVariation workoutVariation) {
        this.slotId = slotId;
        this.slotDate = slotDate;
        this.startTime = startTime;
        this.workoutVariation = workoutVariation;
        this.slotType = SlotType.NORMAL_SLOT;
    }
}
