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
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
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
        if (slot.getWorkoutVariationVsSeatCount() == null) {
            slot.setWorkoutVariationVsSeatCount(new HashMap<>());
        }
        Map<WorkoutVariation, Integer> hmap = slot.getWorkoutVariationVsSeatCount();
        hmap.put(workoutVariation,hmap.getOrDefault(workoutVariation,0)+seats);
    }

    public int getSeatCountInaSlotForAWorkoutVariation(Slot slot, WorkoutVariation workoutVariation){
        return slot.getWorkoutVariationVsSeatCount().get(workoutVariation);
    }

    public void incrementSeatCountInCaseOfCancel(Slot slot, WorkoutVariation workoutVariation){
        Map<WorkoutVariation, Integer> seatMap = slot.getWorkoutVariationVsSeatCount();
        seatMap.merge(workoutVariation, 1, Integer::sum);
    }

    public List<Slot> getSlotsForACenterAndGivenDateAndTime(Center center, User user, LocalDate date, LocalTime time) {
        // for premium users - show both types of slots. i.e premium slots and normal slots
        if(user.getUserType().equals(UserType.FK_VIP_USER))
            return viewAllSlotsForAGivenCenterAndAGivenDateAndTime(center, date,time);
        else
            return viewNormalSlotsForAGivenDateAndTimeAndGivenCenter(center, date,time);
    }

    public List<Slot> viewNormalSlotsForAGivenDateAndTimeAndGivenCenter(Center center, LocalDate date, LocalTime time){
        List<Slot> allSlots = viewAllSlotsForAGivenCenterAndAGivenDateAndTime(center, date,time);
        return allSlots.stream()
                .filter(slot -> slot.getSlotType().equals(SlotType.NORMAL_SLOT))
                .toList();
    }

    public List<Slot> viewAllSlotsForAGivenCenterAndAGivenDateAndTime(Center center, LocalDate date, LocalTime time){
        if(center.getSlots().isEmpty())
            return List.of();
        return centerRepository.getSlotsInCenter(center)
                .stream()
                .filter(slot -> slot.getSlotDate().equals(date))
                .filter(slot -> slot.getStartTime().equals(time))
                .toList();
    }
}
