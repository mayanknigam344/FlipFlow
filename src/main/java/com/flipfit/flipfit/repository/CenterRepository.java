package com.flipfit.flipfit.repository;

import com.flipfit.flipfit.exception.CenterAlreadyExistsException;
import com.flipfit.flipfit.model.Center;
import com.flipfit.flipfit.model.slot.Slot;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
@Slf4j
@Getter
public class CenterRepository {
    List<Center> centers= new ArrayList<>();

    //String - CenterId
    HashMap<String, List<Slot>> slotsInCenter = new HashMap<>();

    public void addCenter(Center center) {
        if(centers.contains(center)) {
            throw new CenterAlreadyExistsException("Center already exists");
        }
        centers.add(center);
        log.info("Added center {}", center.getCenterId());
    }

    public void addSlotInCenter(Slot slot, Center center) {
        slotsInCenter.computeIfAbsent(center.getCenterId(),k->new ArrayList<>());
        slotsInCenter.get(center.getCenterId()).add(slot);
    }

    public List<Slot> getSlotsInCenter(Center center){
        return slotsInCenter.get(center.getCenterId());
    }
}
