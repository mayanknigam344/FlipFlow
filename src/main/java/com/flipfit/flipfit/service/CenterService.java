package com.flipfit.flipfit.service;

import com.flipfit.flipfit.exception.CenterAlreadyExistsException;
import com.flipfit.flipfit.model.Center;
import com.flipfit.flipfit.model.WorkoutVariation;
import com.flipfit.flipfit.model.slot.Slot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CenterService {
    List<Center> centers;

    public Center addCenter(Center center) {
        if(centers.contains(center)) {
            throw new CenterAlreadyExistsException("Center already exists");
        }
        centers.add(center);
        log.info("Added center {}", center.getCenterId());
        return center;
    }

    public List<WorkoutVariation> getWorkoutVariationsForAGivenCenter(Center center){
        return center.getSlots()
                .stream()
                .map(Slot::getWorkoutVariationVsSeatCount)
                .flatMap(workoutVariationIntegerMap -> workoutVariationIntegerMap.keySet().stream())
                .distinct()
                .toList();
    }
}