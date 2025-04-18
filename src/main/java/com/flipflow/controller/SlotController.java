package com.flipflow.controller;

import com.flipflow.model.Center;
import com.flipflow.model.WorkoutVariation;
import com.flipflow.model.slot.Slot;
import com.flipflow.service.SlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class SlotController {

    private final SlotService slotService;

    public void addSlotInACenter(Slot slot , Center center){
        slotService.addSlotInCenter(slot, center);
    }

    public void addWorkoutVariationsForASlot(Slot slot, WorkoutVariation workoutVariation, int seats){
        slotService.addWorkoutVariationToSlot(slot,workoutVariation,seats);
    }
}
