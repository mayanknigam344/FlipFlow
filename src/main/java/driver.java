import com.flipfit.flipfit.model.Center;
import com.flipfit.flipfit.model.slot.PremiumSlot;
import com.flipfit.flipfit.model.slot.Slot;
import com.flipfit.flipfit.repository.CenterRepository;
import com.flipfit.flipfit.service.CenterService;
import com.flipfit.flipfit.service.SlotService;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class driver {

    public static void main(String[] args) {
        CenterRepository centerRepository = new CenterRepository();

        CenterService centerService = new CenterService(centerRepository);
        SlotService slotService = new SlotService(centerRepository);

        // create centers
        Center center1 = new Center("center-id1","flipfit-1","bangalore");

        //create slots
        Slot slot1 = new PremiumSlot("slot-1", Date.valueOf("29-03-2025"), Time.valueOf("9AM"));

        //Add centers
        centerService.addCenter(center1);

        // Add the slots in the center
        slotService.addSlotInCenter(slot1,center1);

        // display the slots for a given center
        List<Slot>slots =  slotService.getAllSlotsForACenter(center1);
        for(Slot s : slots){
            System.out.println(s);
        }
    }
}
