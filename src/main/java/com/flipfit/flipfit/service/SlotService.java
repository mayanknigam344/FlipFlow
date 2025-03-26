package com.flipfit.flipfit.service;

import com.flipfit.flipfit.model.Center;
import com.flipfit.flipfit.model.WorkoutVariation;
import com.flipfit.flipfit.model.slot.Slot;
import com.flipfit.flipfit.model.slot.SlotType;
import com.flipfit.flipfit.model.user.User;
import com.flipfit.flipfit.model.user.UserType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void addWorkoutVariationInASlot(Slot slot, WorkoutVariation workoutVariation, int seats){
       Map<WorkoutVariation,Integer> hmap = slot.getWorkoutVariationVsSeatCount();
       if(hmap.containsKey(workoutVariation)){
           hmap.put(workoutVariation,hmap.get(workoutVariation)+seats);
       }
       else {
           hmap.put(workoutVariation, seats);
       }
    }

    public int getSeatCountInaSlotForaWorkoutVariation(Slot slot, WorkoutVariation workoutVariation){
        return slot.getWorkoutVariationVsSeatCount().get(workoutVariation);
    }

    public List<Slot> viewSlotsForACenterAndGivenDate(Center center, Date date, User user) {
        if(user.getUserType().equals(UserType.FK_VIP_USER))
            return viewAllPremiumSlotsForAGivenDateAndGivenCenter(center, date);
        else
            return viewNormalSlotsForAGivenDateAndGivenCenter(center, date);
    }

    public List<Slot> viewNormalSlotsForAGivenDateAndGivenCenter(Center center, Date date){
        List<Slot> allSlots = viewAllSlotsForAGivenCenterAndAGivenDate(center, date);
        List<Slot> premiumSlots = viewAllPremiumSlotsForAGivenDateAndGivenCenter(center, date);
        List<Slot> normalSlots = new ArrayList<>();
        for(Slot slot : allSlots) {
            if(!premiumSlots.contains(slot)){
                normalSlots.add(slot);
            }
        }
        return normalSlots;
    }

    public List<Slot> viewAllSlotsForAGivenCenterAndAGivenDate(Center center, Date date){
        if(center.getSlots().isEmpty())
            return List.of();
        return slotsInACenter.get(center)
                .stream()
                .filter(slot -> slot.getSlotDate().equals(date))
                .toList();
    }

    public List<Slot> viewAllPremiumSlotsForAGivenDateAndGivenCenter(Center center, Date date){
        if(center.getSlots().isEmpty())
            return List.of();
        return slotsInACenter.get(center)
                .stream()
                .filter(slot -> slot.getSlotType().equals(SlotType.PREMIUM_SLOT))
                .filter(slot -> slot.getSlotDate().equals(date))
                .toList();
    }
}
