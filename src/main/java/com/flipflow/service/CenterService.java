package com.flipflow.service;

import com.flipflow.model.Center;
import com.flipflow.model.WorkoutVariation;
import com.flipflow.model.slot.Slot;
import com.flipflow.repository.CenterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CenterService {

    private final CenterRepository centerRepository;

    public void addCenter(Center center){
         centerRepository.addCenter(center);
    }

    public List<WorkoutVariation> getWorkoutVariationsForAGivenCenter(Center center){
        return center.getSlots()
                .stream()
                .map(Slot::getWorkoutVariationToSeats)
                .flatMap(map -> map.keySet().stream())
                .distinct()
                .toList();
    }
}