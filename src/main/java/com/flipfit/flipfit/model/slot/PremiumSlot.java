package com.flipfit.flipfit.model.slot;

import com.flipfit.flipfit.model.WorkoutVariation;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
public class PremiumSlot extends Slot {
    public PremiumSlot(Long slotId, Date slotDate, Time startTime, WorkoutVariation workoutVariation) {
        this.slotId = slotId;
        this.slotDate = slotDate;
        this.startTime = startTime;
        this.workoutVariation = workoutVariation;
        this.slotType = SlotType.PREMIUM_SLOT;
    }
}
