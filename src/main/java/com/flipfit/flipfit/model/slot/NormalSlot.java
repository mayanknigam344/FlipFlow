package com.flipfit.flipfit.model.slot;

import java.time.LocalDateTime;
import java.util.HashMap;

public class NormalSlot extends Slot {
    public NormalSlot(String slotId, LocalDateTime slotDateTime) {
        this.slotId = slotId;
        this.slotDateTime = slotDateTime;
        this.type = SlotType.NORMAL_SLOT;
        this.workoutVariationToSeats = new HashMap<>();
    }
}
