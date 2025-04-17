package com.flipfit.flipfit.service;

import com.flipfit.flipfit.model.Center;
import com.flipfit.flipfit.model.WorkoutVariation;
import com.flipfit.flipfit.model.slot.Slot;
import com.flipfit.flipfit.model.slot.SlotType;
import com.flipfit.flipfit.model.user.User;
import com.flipfit.flipfit.repository.CenterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class SlotService {

    private final CenterRepository centerRepository;

    public void addSlotInCenter(Slot slot , Center center){
        centerRepository.addSlotInCenter(slot,center);
        log.info("Slot {} added to center {}", slot.getSlotId(), center.getCenterId());
    }

    public void addWorkoutVariationToSlot(Slot slot, WorkoutVariation variation, int seats){
        Map<WorkoutVariation, Integer> seatMap  = slot.getWorkoutVariationToSeats();
        seatMap.put(variation,seatMap.getOrDefault(variation,0)+seats);
        log.info("Added {} seats for variation {} in slot {}", seats, variation, slot.getSlotId());
    }

    public List<Slot> getSlotsForCenterAtTime(Center center, User user, LocalDateTime dateTime) {
        return user.isVip()
                ? getAllSlotsForCenterAtTime(center, dateTime)
                : getNormalSlotsForCenterAtTime(center, dateTime);
    }

    public List<Slot> getNormalSlotsForCenterAtTime(Center center, LocalDateTime dateTime){
        List<Slot> allSlots = getAllSlotsForCenterAtTime(center,dateTime);
        return allSlots.stream()
                .filter(slot -> slot.getType().equals(SlotType.NORMAL_SLOT))
                .toList();
    }

    public List<Slot> getAllSlotsForCenterAtTime(Center center, LocalDateTime dateTime){
        return  center.getSlots()
                .stream()
                .filter(slot -> slot.getSlotDateTime().equals(dateTime))
                .toList();
    }
}
