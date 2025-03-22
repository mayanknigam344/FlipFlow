package com.flipfit.flipfit.service;

import com.flipfit.flipfit.exception.WorkoutTypeAlreadyPresentException;
import com.flipfit.flipfit.model.slot.Slot;
import com.flipfit.flipfit.model.WorkoutVariation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SlotService {

    public void addWorkoutTypeInSlot(Slot slot, WorkoutVariation workoutVariation) {
        List<WorkoutVariation> workoutVariations = slot.getWorkoutVariationInSlot();
        if(workoutVariations.contains(workoutVariation)) {
            throw new WorkoutTypeAlreadyPresentException("Workout type already exists");
        }
        workoutVariations.add(workoutVariation);
    }
}
