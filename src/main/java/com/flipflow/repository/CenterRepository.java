package com.flipflow.repository;

import com.flipflow.exception.CenterAlreadyExistsException;
import com.flipflow.model.Center;
import com.flipflow.model.slot.Slot;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
@Getter
public class CenterRepository {
    List<Center> centers= new ArrayList<>();

    public void addCenter(Center center) {
        if(centers.contains(center)) {
            throw new CenterAlreadyExistsException("Center already exists");
        }
        centers.add(center);
        log.info("Added center {}", center.getCenterId());
    }

    public void addSlotInCenter(Slot slot, Center center) {
        log.info("Adding slot {} in center {}", slot, center.getCenterId());
        center.getSlots().add(slot);
    }

    public List<Slot> getSlotsInCenter(Center center){
        return center.getSlots();
    }
}
