package com.flipfit.flipfit.model.slot;

import com.flipfit.flipfit.model.WorkoutVariation;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class NormalSlot extends Slot {
    public NormalSlot(Long slotId, Date slotDate, Time startTime, List<WorkoutVariation> workoutVariationInSlot) {
        this.slotId = slotId;
        this.slotDate = slotDate;
        this.startTime = startTime;
        this.workoutVariationInSlot = workoutVariationInSlot;
        this.slotType = SlotType.NORMAL_SLOT;
    }
}
