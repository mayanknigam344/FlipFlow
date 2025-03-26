package com.flipfit.flipfit.model.slot;

import com.flipfit.flipfit.model.WorkoutVariation;

import java.sql.Date;
import java.sql.Time;
import java.util.Map;

public class NormalSlot extends Slot {
    public NormalSlot(Long slotId, Date slotDate, Time startTime,  Map<WorkoutVariation,Integer> workoutVariationVsSeatCount) {
        this.slotId = slotId;
        this.slotDate = slotDate;
        this.startTime = startTime;
        this.workoutVariationVsSeatCount = workoutVariationVsSeatCount;
        this.slotType = SlotType.NORMAL_SLOT;
    }
}
