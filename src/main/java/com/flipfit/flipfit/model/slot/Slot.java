package com.flipfit.flipfit.model.slot;

import com.flipfit.flipfit.model.WorkoutVariation;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@Getter
@Setter
public abstract class Slot {
    String slotId;
    LocalDate slotDate;
    LocalTime startTime;
    // Integer - NumberOfSeats
    Map<WorkoutVariation,Integer> workoutVariationVsSeatCount;
    SlotType slotType;

    @Override
    public String toString() {
        return "Slot{" +
                "slotId='" + slotId + '\'' +
                ", slotDate=" + slotDate +
                ", startTime=" + startTime +
                ", workoutVariationVsSeatCount=" + workoutVariationVsSeatCount +
                '}';
    }
}
