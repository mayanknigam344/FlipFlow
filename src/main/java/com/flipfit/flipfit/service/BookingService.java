package com.flipfit.flipfit.service;

import com.flipfit.flipfit.model.Center;
import com.flipfit.flipfit.model.WorkoutVariation;
import com.flipfit.flipfit.model.slot.Slot;
import com.flipfit.flipfit.model.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BookingService {

    @Autowired SlotService slotService;

    public void book(User user, Center center, WorkoutVariation workoutVariation, Slot slot) {
        List<Slot> slotsInACenter = center.getSlots();
        if(slotsInACenter.contains(slot)){
            int seatsForWorkoutVariation = slotService.getSeatCountInaSlotForaWorkoutVariation(slot,workoutVariation);
            if(seatsForWorkoutVariation>0){
                slot.getWorkoutVariationVsSeatCount().put(workoutVariation, seatsForWorkoutVariation-1);
            }
           log.info("Booking is Successful");
        }


    }
}
