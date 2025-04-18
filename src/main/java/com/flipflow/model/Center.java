package com.flipflow.model;

import com.flipflow.model.slot.Slot;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
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
        this.slots = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Center{" +
                "centerId='" + centerId + '\'' +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", location=" + location +
                ", slots=" + slots +
                '}';
    }
}
