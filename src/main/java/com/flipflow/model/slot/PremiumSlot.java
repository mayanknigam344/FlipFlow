package com.flipflow.model.slot;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;

@Getter
@Setter
public class PremiumSlot extends Slot {
    public PremiumSlot(String slotId, LocalDateTime slotDateTime) {
        this.slotId = slotId;
        this.slotDateTime = slotDateTime;
        this.type = SlotType.PREMIUM_SLOT;
        this.workoutVariationToSeats = new HashMap<>();
    }
}
