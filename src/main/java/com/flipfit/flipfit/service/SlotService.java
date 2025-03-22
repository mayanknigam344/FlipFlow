package com.flipfit.flipfit.service;

import com.flipfit.flipfit.exception.WorkoutTypeAlreadyPresentException;
import com.flipfit.flipfit.model.Center;
import com.flipfit.flipfit.model.WorkoutVariation;
import com.flipfit.flipfit.model.slot.Slot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SlotService {

    private final List<Slot> slots;
    HashMap<Center,List<Slot>> slotsInACenter;

    public void addSlotInCenter(Slot slot, Center center) {
        List<Slot> slotsInCenter = center.getSlots();
        slotsInCenter.add(slot);
        log.info("Slot added for center {} , slot {}",center.getCenterId(),slot.getSlotId());
        slotsInACenter.putIfAbsent(center,new ArrayList<>());
        slotsInACenter.get(center).add(slot);
        slots.add(slot);
    }

    public void addWorkoutTypeInSlot(Slot slot, WorkoutVariation workoutVariation) {
        List<WorkoutVariation> workoutVariations = slot.getWorkoutVariationInASlot();
        if(workoutVariations.contains(workoutVariation)) {
            throw new WorkoutTypeAlreadyPresentException("Workout type already exists");
        }
        workoutVariations.add(workoutVariation);
    }

    public List<Slot> viewAllSlotsForAGivenCenterAndAGivenDate(Center center, Date date){
        if(center.getSlots().isEmpty())
            return List.of();
        return slotsInACenter.get(center)
                .stream()
                .filter(slot -> slot.getSlotDate().equals(date))
                .toList();
    }
}
