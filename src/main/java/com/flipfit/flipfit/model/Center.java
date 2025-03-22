package com.flipfit.flipfit.model;

import com.flipfit.flipfit.model.slot.Slot;
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
    List<WorkoutVariation> workoutVariationInCenter;
}
