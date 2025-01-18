package com.flipfit.flipfit.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Center {
    long centerId;
    String name;
    String city;
    Location location;
    List<Slot> slots;
}
