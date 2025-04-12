package com.flipfit.flipfit.model.slot;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;


@Getter
@Setter
public class PremiumSlot extends Slot {
    public PremiumSlot(String slotId, LocalDate slotDate, LocalTime startTime) {
        this.slotId = slotId;
        this.slotDate = slotDate;
        this.startTime = startTime;
        this.slotType = SlotType.PREMIUM_SLOT;
    }
}
