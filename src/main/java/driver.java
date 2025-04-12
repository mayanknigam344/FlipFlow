import com.flipfit.flipfit.model.Center;
import com.flipfit.flipfit.model.WorkoutVariation;
import com.flipfit.flipfit.model.slot.NormalSlot;
import com.flipfit.flipfit.model.slot.PremiumSlot;
import com.flipfit.flipfit.model.slot.Slot;
import com.flipfit.flipfit.model.user.NormalUser;
import com.flipfit.flipfit.model.user.User;
import com.flipfit.flipfit.repository.BookingRepository;
import com.flipfit.flipfit.repository.CenterRepository;
import com.flipfit.flipfit.repository.UserRepository;
import com.flipfit.flipfit.service.BookingService;
import com.flipfit.flipfit.service.CenterService;
import com.flipfit.flipfit.service.SlotService;
import com.flipfit.flipfit.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

public class driver {

    public static void main(String[] args) {
        CenterRepository centerRepository = new CenterRepository();
        BookingRepository bookingRepository = new BookingRepository();
        UserRepository userRepository = new UserRepository();

        CenterService centerService = new CenterService(centerRepository);
        SlotService slotService = new SlotService(centerRepository);
        UserService userService = new UserService(userRepository);
        BookingService bookingService = new BookingService(slotService,bookingRepository,userRepository);

        // create centers
        Center center1 = new Center("center-id1","flipfit-1","bangalore");
        //Add centers
        centerService.addCenter(center1);

        //  create user and add
        User user = new NormalUser("user-id","mayank");
        userService.addUser(user);

        //create slots
        // April 12, 2025

        LocalDateTime  dateTime1 = LocalDateTime.of(2025,4,15,9,0);
        LocalDateTime  dateTime2 = LocalDateTime.of(2025,4,15,10,0);

        Slot slot1 = new PremiumSlot("slot-1",dateTime1);
        Slot slot2 = new NormalSlot("slot-2",dateTime1);
        Slot slot3 = new NormalSlot("slot-3",dateTime2);

        // Adding workout variation in each slot
        // slot1 - premium
        slotService.addWorkoutVariationInASlot(slot1, WorkoutVariation.WEIGHTS,1);
        slotService.addWorkoutVariationInASlot(slot1,WorkoutVariation.CARDIO,1);

        // slot2 - normal
        slotService.addWorkoutVariationInASlot(slot2, WorkoutVariation.WEIGHTS,1);
        slotService.addWorkoutVariationInASlot(slot2,WorkoutVariation.CARDIO,1);
        slotService.addWorkoutVariationInASlot(slot2,WorkoutVariation.SWIMMING,1);
        slotService.addWorkoutVariationInASlot(slot2,WorkoutVariation.YOGA,1);

        //slot3 - normal
        slotService.addWorkoutVariationInASlot(slot3,WorkoutVariation.SWIMMING,1);
        slotService.addWorkoutVariationInASlot(slot3,WorkoutVariation.YOGA,1);

        // Add the slots in the center
        slotService.addSlotInCenter(slot1,center1);
        slotService.addSlotInCenter(slot2,center1);
        slotService.addSlotInCenter(slot3,center1);

        List<Slot> slots = centerRepository.getSlotsInCenter(center1);
        System.out.println(slots);  // size 3

        // book
        bookingService.book(user,center1,WorkoutVariation.WEIGHTS,slot2);

        //List<Booking> bookings  = userRepository.getAllBookings(user);
        /*bookingRepository.getBooking(booking.getBookingId());
        System.out.println(booking);*/

        bookingService.book(user,center1,WorkoutVariation.WEIGHTS,slot2);
    }
}
