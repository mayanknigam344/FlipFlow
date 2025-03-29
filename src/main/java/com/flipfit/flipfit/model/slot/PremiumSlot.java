package com.flipfit.flipfit.model.slot;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
public class PremiumSlot extends Slot {
    public PremiumSlot(String slotId, Date slotDate, Time startTime) {
        this.slotId = slotId;
        this.slotDate = slotDate;
        this.startTime = startTime;
        this.slotType = SlotType.PREMIUM_SLOT;
    }
}
