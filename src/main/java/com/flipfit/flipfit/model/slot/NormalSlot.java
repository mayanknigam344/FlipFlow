package com.flipfit.flipfit.model.slot;

import java.sql.Date;
import java.sql.Time;

public class NormalSlot extends Slot {
    public NormalSlot(String slotId, Date slotDate, Time startTime) {
        this.slotId = slotId;
        this.slotDate = slotDate;
        this.startTime = startTime;
        this.slotType = SlotType.NORMAL_SLOT;
    }
}
