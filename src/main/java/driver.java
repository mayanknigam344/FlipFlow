import com.flipfit.flipfit.model.Center;
import com.flipfit.flipfit.model.WorkoutVariation;
import com.flipfit.flipfit.model.slot.NormalSlot;
import com.flipfit.flipfit.model.slot.PremiumSlot;
import com.flipfit.flipfit.model.slot.Slot;
import com.flipfit.flipfit.repository.CenterRepository;
import com.flipfit.flipfit.service.CenterService;
import com.flipfit.flipfit.service.SlotService;

import java.time.LocalDate;
import java.time.LocalTime;

public class driver {

    public static void main(String[] args) {
        CenterRepository centerRepository = new CenterRepository();

        CenterService centerService = new CenterService(centerRepository);
        SlotService slotService = new SlotService(centerRepository);

        // create centers
        Center center1 = new Center("center-id1","flipfit-1","bangalore");

        //create slots
        // April 12, 2025
        LocalDate date = LocalDate.of(2025, 4, 12);
        LocalTime time = LocalTime.of(9, 0);
        LocalTime time2= LocalTime.of(10,0);

        Slot slot1 = new PremiumSlot("slot-1", date,time);
        Slot slot2 = new NormalSlot("slot-2",date,time);
        Slot slot3 = new NormalSlot("slot-3",date,time2);


        // Adding workout variation in each slot
        // slot1
        slotService.addWorkoutVariationInASlot(slot1, WorkoutVariation.WEIGHTS,20);
        slotService.addWorkoutVariationInASlot(slot1,WorkoutVariation.CARDIO,20);

        // slot2
        slotService.addWorkoutVariationInASlot(slot2, WorkoutVariation.WEIGHTS,20);
        slotService.addWorkoutVariationInASlot(slot2,WorkoutVariation.CARDIO,20);
        slotService.addWorkoutVariationInASlot(slot2,WorkoutVariation.SWIMMING,20);
        slotService.addWorkoutVariationInASlot(slot2,WorkoutVariation.YOGA,20);

        //slot3
        slotService.addWorkoutVariationInASlot(slot3,WorkoutVariation.SWIMMING,20);
        slotService.addWorkoutVariationInASlot(slot3,WorkoutVariation.YOGA,20);

        //Add centers
        centerService.addCenter(center1);

        // Add the slots in the center
        slotService.addSlotInCenter(slot1,center1);
        slotService.addSlotInCenter(slot2,center1);
        slotService.addSlotInCenter(slot3,center1);


    }
}
