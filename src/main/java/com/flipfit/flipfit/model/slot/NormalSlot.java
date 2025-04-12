package com.flipfit.flipfit.model.slot;

import java.time.LocalDate;
import java.time.LocalTime;

public class NormalSlot extends Slot {
    public NormalSlot(String slotId, LocalDate slotDate, LocalTime startTime) {
        this.slotId = slotId;
        this.slotDate = slotDate;
        this.startTime = startTime;
        this.slotType = SlotType.NORMAL_SLOT;
    }
}
