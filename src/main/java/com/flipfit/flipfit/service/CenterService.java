package com.flipfit.flipfit.service;

import com.flipfit.flipfit.model.Center;
import com.flipfit.flipfit.model.WorkoutVariation;
import com.flipfit.flipfit.model.slot.Slot;
import com.flipfit.flipfit.repository.CenterRepository;
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
        return centerRepository.getSlotsInCenter(center)
                .stream()
                .map(Slot::getWorkoutVariationVsSeatCount)
                .flatMap(map -> map.keySet().stream())
                .distinct()
                .toList();
    }
}