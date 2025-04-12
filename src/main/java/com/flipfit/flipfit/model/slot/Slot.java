package com.flipfit.flipfit.model.slot;

import com.flipfit.flipfit.model.WorkoutVariation;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
public abstract class Slot {
    String slotId;
    LocalDateTime slotDateAndTime;
    // Integer - NumberOfSeats
    Map<WorkoutVariation,Integer> workoutVariationVsSeatCount;
    SlotType slotType;

    @Override
    public String toString() {
        return "Slot{" +
                "slotId='" + slotId + '\'' +
                ", slotDataAndTime=" + slotDateAndTime +
                ", workoutVariationVsSeatCount=" + workoutVariationVsSeatCount +
                '}';
    }
}
