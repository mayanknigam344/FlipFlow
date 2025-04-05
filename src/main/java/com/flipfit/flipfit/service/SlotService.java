package com.flipfit.flipfit.service;

import com.flipfit.flipfit.model.Center;
import com.flipfit.flipfit.model.WorkoutVariation;
import com.flipfit.flipfit.model.slot.Slot;
import com.flipfit.flipfit.model.slot.SlotType;
import com.flipfit.flipfit.model.user.User;
import com.flipfit.flipfit.model.user.UserType;
import com.flipfit.flipfit.repository.CenterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class SlotService {

    private final CenterRepository centerRepository;

    public void addSlotInCenter(Slot slot , Center center){
        centerRepository.addSlotInCenter(slot,center);
    }

    public List<Slot> getAllSlotsForACenter(Center center){
        return centerRepository.getSlotsInCenter(center);
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

    public int getSeatCountInaSlotForAWorkoutVariation(Slot slot, WorkoutVariation workoutVariation){
        return slot.getWorkoutVariationVsSeatCount().get(workoutVariation);
    }

    public void incrementSeatCountInCaseOfCancel(Slot slot, WorkoutVariation workoutVariation){
        Map<WorkoutVariation, Integer> seatMap = slot.getWorkoutVariationVsSeatCount();
        seatMap.merge(workoutVariation, 1, Integer::sum);
    }

    public List<Slot> getSlotsForACenterAndGivenDateAndTime(Center center, User user, Date date, Time time) {
        if(user.getUserType().equals(UserType.FK_VIP_USER))
            return viewAllPremiumSlotsForAGivenDateAndTimeAndGivenCenter(center, date,time);
        else
            return viewNormalSlotsForAGivenDateAndTimeAndGivenCenter(center, date,time);
    }

    public List<Slot> viewNormalSlotsForAGivenDateAndTimeAndGivenCenter(Center center, Date date, Time time){
        List<Slot> allSlots = viewAllSlotsForAGivenCenterAndAGivenDateAndTime(center, date,time);
        List<Slot> premiumSlots = viewAllPremiumSlotsForAGivenDateAndTimeAndGivenCenter(center, date,time);
        List<Slot> normalSlots = new ArrayList<>();
        for(Slot slot : allSlots) {
            if(!premiumSlots.contains(slot)){
                normalSlots.add(slot);
            }
        }
        return normalSlots;
    }

    public List<Slot> viewAllSlotsForAGivenCenterAndAGivenDateAndTime(Center center, Date date, Time time){
        if(center.getSlots().isEmpty())
            return List.of();
        return centerRepository.getSlotsInCenter(center)
                .stream()
                .filter(slot -> DateUtils.isSameDay(slot.getSlotDate(),date))
                .filter(slot -> slot.getStartTime().toLocalTime().equals(time.toLocalTime()))
                .toList();
    }

    public List<Slot> viewAllPremiumSlotsForAGivenDateAndTimeAndGivenCenter(Center center, Date date, Time time){
        if(center.getSlots().isEmpty())
            return List.of();
        return centerRepository.getSlotsInCenter(center)
                .stream()
                .filter(slot -> slot.getSlotType().equals(SlotType.PREMIUM_SLOT))
                .filter(slot -> DateUtils.isSameDay(slot.getSlotDate(),date))
                .filter(slot -> slot.getStartTime().toLocalTime().equals(time.toLocalTime()))
                .toList();
    }
}
