package com.flipflow.model.slot;

import com.flipflow.model.WorkoutVariation;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
public abstract class Slot {
    String slotId;
    LocalDateTime slotDateTime;
    // Integer - NumberOfSeats
    Map<WorkoutVariation,Integer> workoutVariationToSeats;
    SlotType type;

    public int getSeatCountForWorkout(WorkoutVariation variation) {
        return workoutVariationToSeats.getOrDefault(variation, 0);
    }

    public void decrementSeatForWorkout(WorkoutVariation variation) {
        workoutVariationToSeats.put(
                variation,
                getSeatCountForWorkout(variation) - 1
        );
    }

    public void incrementSeatForWorkout(WorkoutVariation variation) {
        workoutVariationToSeats.put(
                variation,
                getSeatCountForWorkout(variation) + 1
        );
    }

    @Override
    public String toString() {
        return "Slot{" +
                "slotId='" + slotId + '\'' +
                ", slotDataAndTime=" + slotDateTime +
                ", workoutVariationVsSeatCount=" + workoutVariationToSeats +
                '}';
    }
}
