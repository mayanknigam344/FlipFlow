package com.flipfit.flipfit.service;

import com.flipfit.flipfit.exception.WorkoutTypeAlreadyPresentException;
import com.flipfit.flipfit.model.Center;
import com.flipfit.flipfit.model.WorkoutVariation;
import com.flipfit.flipfit.model.slot.Slot;
import com.flipfit.flipfit.model.user.User;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@Service
public class SlotService {

    public void addWorkoutTypeInSlot(Slot slot, WorkoutVariation workoutVariation) {
        List<WorkoutVariation> workoutVariations = slot.getWorkoutVariationInASlot();
        if(workoutVariations.contains(workoutVariation)) {
            throw new WorkoutTypeAlreadyPresentException("Workout type already exists");
        }
        workoutVariations.add(workoutVariation);
    }

    public Map<Slot,Boolean> viewAllSlotsForAGivenCenterAndUser(Center center, Date date, User user){
        // TODO: Needs Rework
        return null;
    }
}
