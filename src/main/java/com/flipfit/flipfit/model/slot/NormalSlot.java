package com.flipfit.flipfit.model.slot;

import java.time.LocalDateTime;
import java.util.HashMap;

public class NormalSlot extends Slot {
    public NormalSlot(String slotId, LocalDateTime slotDateAndTime) {
        this.slotId = slotId;
        this.slotDateAndTime = slotDateAndTime;
        this.slotType = SlotType.NORMAL_SLOT;
        this.workoutVariationVsSeatCount = new HashMap<>();
    }
}
