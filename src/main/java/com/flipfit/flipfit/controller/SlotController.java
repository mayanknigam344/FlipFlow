package com.flipfit.flipfit.controller;

import com.flipfit.flipfit.model.slot.Slot;
import com.flipfit.flipfit.model.WorkoutVariation;
import com.flipfit.flipfit.service.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class SlotController {

    @Autowired SlotService slotService;
    public void addWorkoutVariationsForASlot(Slot slot, WorkoutVariation workoutVariation){
        slotService.addWorkoutTypeInSlot(slot,workoutVariation);
    }
}
