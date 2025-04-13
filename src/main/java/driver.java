import com.flipfit.flipfit.model.Booking;
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
import java.util.Map;

public class driver {

    public static void main(String[] args) {
        // Setup Repositories and Services
        CenterRepository centerRepository = new CenterRepository();
        BookingRepository bookingRepository = new BookingRepository();
        UserRepository userRepository = new UserRepository();

        CenterService centerService = new CenterService(centerRepository);
        SlotService slotService = new SlotService(centerRepository);
        UserService userService = new UserService(userRepository);
        BookingService bookingService = new BookingService(slotService, bookingRepository, userRepository);

        // Setup Center
        Center center1 = new Center("center-id1", "flipfit-1", "bangalore");
        centerService.addCenter(center1);

        // Setup Users
        User user1 = new NormalUser("user-id-1", "mayank");
        User user2 = new NormalUser("user-id-2", "tka4798");
        userService.addUser(user1);
        userService.addUser(user2);

        // Setup Slots
        LocalDateTime[] times = {
                LocalDateTime.of(2025, 4, 15, 9, 0),
                LocalDateTime.of(2025, 4, 15, 10, 0),
                LocalDateTime.of(2025, 4, 15, 11, 0),
                LocalDateTime.of(2025, 4, 15, 12, 0),
                LocalDateTime.of(2025, 4, 15, 14, 0)
        };

        Slot slot1 = new PremiumSlot("slot-1",times[0]);
        Slot slot2 = new NormalSlot("slot-2", times[0]);
        Slot slot3 = new NormalSlot("slot-3", times[1]);
        Slot slot4 = new NormalSlot("slot-4", times[2]);
        Slot slot5 = new NormalSlot("slot-5", times[3]);
        Slot slot6 = new NormalSlot("slot-6", times[4]);

        // Add Workout Variations to Slots
        Map<Slot, Map<WorkoutVariation, Integer>> slotVariationData = Map.of(
                slot1, Map.of(
                        WorkoutVariation.WEIGHTS, 2,
                        WorkoutVariation.CARDIO, 1
                ),
                slot2, Map.of(
                        WorkoutVariation.WEIGHTS, 3,
                        WorkoutVariation.CARDIO, 2,
                        WorkoutVariation.SWIMMING, 1,
                        WorkoutVariation.YOGA, 1
                ),
                slot3, Map.of(
                        WorkoutVariation.WEIGHTS, 2,
                        WorkoutVariation.SWIMMING, 1,
                        WorkoutVariation.YOGA, 1
                ),
                slot4, Map.of(
                        WorkoutVariation.WEIGHTS, 1,
                        WorkoutVariation.CARDIO, 1
                ),
                slot5, Map.of(
                        WorkoutVariation.WEIGHTS, 1,
                        WorkoutVariation.CARDIO, 1
                ),
                slot6, Map.of(
                        WorkoutVariation.WEIGHTS, 1,
                        WorkoutVariation.CARDIO, 1
                )
        );

        slotVariationData.forEach((slot, variationSeats) ->
                variationSeats.forEach((variation, seats) ->
                        slotService.addWorkoutVariationInASlot(slot, variation, seats)
                )
        );

        // Register Slots to Center
        List.of(slot1, slot2, slot3, slot4, slot5, slot6)
                .forEach(slot -> slotService.addSlotInCenter(slot, center1));

        System.out.println("Slots in center: " + centerRepository.getSlotsInCenter(center1).size());

        // Booking Flow
        bookAndPrint(bookingService, userRepository, user1, center1, WorkoutVariation.WEIGHTS, slot2);
        bookAndPrint(bookingService, userRepository, user2, center1, WorkoutVariation.WEIGHTS, slot2);

        // Cancel Booking
        System.out.println("Cancel - flow started");
        bookingService.cancelBooking(user1, center1, slot2);
        System.out.println("User1 Bookings after cancel: " + userRepository.getAllBookings(user1).size());
        System.out.println("Cancel - flow completed");
    }

    private static void bookAndPrint(BookingService bookingService, UserRepository userRepository, User user, Center center, WorkoutVariation variation, Slot slot) {
        System.out.println("Booking - flow started for user: " + user.getUserId());
        Booking booking = bookingService.book(user, center, variation, slot);
        if (booking != null) {
            System.out.println("Booking successful, ID: " + booking.getBookingId());
        } else {
            System.out.println("Booking failed or added to waiting queue");
        }
        System.out.println("Total bookings for user: " + userRepository.getAllBookings(user).size());
    }
}
