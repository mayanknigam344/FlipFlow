import com.flipfit.flipfit.controller.CenterController;
import com.flipfit.flipfit.controller.SlotController;
import com.flipfit.flipfit.model.Center;
import com.flipfit.flipfit.model.slot.PremiumSlot;
import com.flipfit.flipfit.model.slot.Slot;
import com.flipfit.flipfit.service.CenterService;
import com.flipfit.flipfit.service.SlotService;

import java.sql.Date;
import java.sql.Time;

public class driver {

    public static void main(String[] args) {
        CenterController centerController = new CenterController();
        SlotController slotController = new SlotController();

        CenterService centerService = new CenterService();
        SlotService slotService = new SlotService();

        // create centers
        Center center1 = new Center("center-id1","flipfit-1","bangalore");

        //create slots
        Slot slot1 = new PremiumSlot("slot-1", Date.valueOf("29-03-2025"), Time.valueOf("9AM"));

        // Add the slots in the center
        slotService.addSlotInCenter(slot1,center1);

        // display the slots for a given center
        slotService.getAllSlots(center1);
    }
}
