package com.flipfit.flipfit.model;

import com.flipfit.flipfit.model.slot.Slot;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Center {
    String centerId;
    String name;
    String city;
    Location location;
    List<Slot> slots;

    public Center(String centerId, String name,String city){
        this.centerId = centerId;
        this.name = name;
        this.city = city;
    }
}
