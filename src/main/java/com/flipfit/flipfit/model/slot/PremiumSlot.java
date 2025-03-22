package com.flipfit.flipfit.model.slot;

import com.flipfit.flipfit.model.WorkoutVariation;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Getter
@Setter
public class PremiumSlot extends Slot {
    public PremiumSlot(Long slotId, Date slotDate, Time startTime, List<WorkoutVariation> workoutVariationInSlot) {
        this.slotId = slotId;
        this.slotDate = slotDate;
        this.startTime = startTime;
        this.workoutVariationInSlot = workoutVariationInSlot;
        this.slotType = SlotType.PREMIUM_SLOT;
    }
}
