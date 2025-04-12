package com.flipfit.flipfit.model.slot;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;

@Getter
@Setter
public class PremiumSlot extends Slot {
    public PremiumSlot(String slotId, LocalDateTime slotDateAndTime) {
        this.slotId = slotId;
        this.slotDateAndTime = slotDateAndTime;
        this.slotType = SlotType.PREMIUM_SLOT;
        this.workoutVariationVsSeatCount = new HashMap<>();
    }
}
