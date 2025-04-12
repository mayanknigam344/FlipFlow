package com.flipfit.flipfit.model.slot;

import java.time.LocalDateTime;

public class NormalSlot extends Slot {
    public NormalSlot(String slotId, LocalDateTime slotDateAndTime) {
        this.slotId = slotId;
        this.slotDateAndTime = slotDateAndTime;
        this.slotType = SlotType.NORMAL_SLOT;
    }
}
