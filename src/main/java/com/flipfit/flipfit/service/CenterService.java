package com.flipfit.flipfit.service;

import com.flipfit.flipfit.exception.CenterAlreadyExistsException;
import com.flipfit.flipfit.exception.WorkoutTypeAlreadyPresentException;
import com.flipfit.flipfit.model.Center;
import com.flipfit.flipfit.model.WorkoutVariations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CenterService {
    List<Center> centers;

    public Center addCenter(Center center) {
        if(centers.contains(center)) {
            throw new CenterAlreadyExistsException("Center already exists");
        }
        centers.add(center);
        return center;
    }
    public void addWorkoutTypeInCenter(Center center, WorkoutVariations workoutVariation) {
        List<WorkoutVariations> workoutVariations = center.getWorkoutVariations();
        if(workoutVariations.contains(workoutVariation)) {
            throw new WorkoutTypeAlreadyPresentException("Workout type already exists");
        }
        workoutVariations.add(workoutVariation);
    }
}