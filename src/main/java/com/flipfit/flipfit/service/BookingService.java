package com.flipfit.flipfit.service;

import com.flipfit.flipfit.exception.SlotNotFoundException;
import com.flipfit.flipfit.exception.UserBookingMaxLimitReachedForAGivenCenter;
import com.flipfit.flipfit.model.booking.Booking;
import com.flipfit.flipfit.model.Center;
import com.flipfit.flipfit.model.WorkoutVariation;
import com.flipfit.flipfit.model.booking.BookingStatus;
import com.flipfit.flipfit.model.slot.Slot;
import com.flipfit.flipfit.model.user.User;
import com.flipfit.flipfit.model.user.UserType;
import com.flipfit.flipfit.repository.BookingRepository;
import com.flipfit.flipfit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingService {

    private final SlotService slotService;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    PriorityQueue<Booking> bookingQueue = new PriorityQueue<>(
            (b1, b2) -> Integer.compare(b2.getPriority(), b1.getPriority())
    );

    public Booking book(User user, Center center, WorkoutVariation workoutVariation, Slot slot) {
        log.info("Booking flow started for User {} Center {} Slot {} and Workout variation {}",user.getUserId(),center.getCenterId(),slot.getSlotId(),workoutVariation);

        validateUserBookingLimit(user, center, slot);
        validateSlotAvailability(center, user, slot);

        log.info("Validated the user bookings. Great!! User is allowed to do booking.");

        int seats = slot.getSeatCountForWorkout(workoutVariation);
        log.info("Seat count for a given workout variation {} and slot {} is {}",workoutVariation,slot.getSlotId(), seats);

        Booking booking = buildBooking(user, center, workoutVariation, slot);

        if(seats>0){
            booking = confirmBooking(user, booking, workoutVariation, slot, seats);
        }else{
            booking = bookingRepository.updateBookingStatus(booking,BookingStatus.WAITING);
            bookingQueue.add(booking);
            log.info("Added the booking in the waiting queue. Booking details - {}",booking.getBookingId());
        }
        log.info("Booking flow completed");
        return booking;
    }

    public void cancelBooking(User user , Center center, Slot slot){
        log.info("Cancel flow started for user: {} and slot: {}", user.getUserId(), slot.getSlotId());

        Optional<Booking> bookingOpt = userRepository.getBooking(user, center, slot);
        if (bookingOpt.isEmpty()) {
            log.info("No booking found to cancel for user: {}", user.getUserId());
            return;
        }
        Booking canceledBooking = bookingOpt.get();
        updateBookingAndReleaseSeat(user, canceledBooking);

        if (bookingQueue.isEmpty()) {
            log.info("Cancel flow completed. No bookings in waiting queue.");
            return;
        }
        Booking replacementBooking = findBookingWithSameSlot(canceledBooking, bookingQueue);

        if (replacementBooking == null) {
            log.info("No matching booking in waiting queue for slot: {}", slot.getSlotId());
            return;
        }

        log.info("Promoting booking from waiting queue: {}", replacementBooking);
        promoteBookingFromQueue(replacementBooking);
    }

    public List<Booking> viewUserBooking(String userId, LocalDateTime date){
        markBookingsCompleted(userId);
        log.info("Marked past bookings as 'COMPLETED' for user {} (excluding those already 'CANCELED')", userId);
        return userRepository.getAllBookingsForUserInADay(userId,date);
    }

    private void validateUserBookingLimit(User user, Center center, Slot slot) {
        List<Booking> bookings = bookingRepository.getBookingsForUserAtCenterOnDate(user, center, slot);
        if (bookings.size() >= 3) {
            throw new UserBookingMaxLimitReachedForAGivenCenter("User has reached max bookings at center: " + user.getUserId());
        }
    }

    private void validateSlotAvailability(Center center, User user, Slot slot) {
        List<Slot> slots = slotService.getSlotsForCenterAtTime(center, user, slot.getSlotDateTime());
        if (!slots.contains(slot)) {
            throw new SlotNotFoundException("Given Slot not found at given center.");
        }
    }
    private Booking buildBooking(User user, Center center, WorkoutVariation workoutVariation, Slot slot) {
        int priority = user.getUserType() == UserType.FK_VIP_USER ? 1 : 0;
        return Booking.builder()
                .bookingDateTime(slot.getSlotDateTime())
                .center(center)
                .workoutVariation(workoutVariation)
                .slot(slot)
                .user(user)
                .priority(priority)
                .build();
    }

    private Booking confirmBooking(User user, Booking booking, WorkoutVariation workoutVariation, Slot slot, int seats) {
        slot.decrementSeatForWorkout(workoutVariation);

        String bookingId = UUID.randomUUID().toString();
        booking = booking.toBuilder().bookingId(bookingId).build();

        Booking updatedBooking = bookingRepository.updateBookingStatus(booking,BookingStatus.CONFIRMED);
        bookingRepository.addBooking(updatedBooking);
        userRepository.addBooking(user, updatedBooking);

        log.info("Booking successful with ID {} for user {}", updatedBooking.getBookingId(), user.getUserId());
        return updatedBooking;
    }

    private void updateBookingAndReleaseSeat(User user, Booking booking) {
        Booking updatedBooking = bookingRepository.updateBookingStatus(booking,BookingStatus.CANCELLED);
        userRepository.replaceBooking(user,booking,updatedBooking);
        updatedBooking.getSlot().incrementSeatForWorkout(updatedBooking.getWorkoutVariation());
        log.info("Booking {} cancelled and seat released", updatedBooking.getBookingId());
    }

    private Booking findBookingWithSameSlot(Booking canceledBooking, PriorityQueue<Booking> queue) {
        return queue.stream()
                .filter(b -> b.getSlot().equals(canceledBooking.getSlot()))
                .findFirst()
                .orElse(null);
    }

    private void promoteBookingFromQueue(Booking booking) {
        book(booking.getUser(), booking.getCenter(), booking.getWorkoutVariation(), booking.getSlot());
        bookingQueue.remove(booking);
    }

    private void markBookingsCompleted(String userId){
        User user = userRepository.getUserById(userId);
        List<Booking> updatedBookings = user.getBookings().stream()
                .map(booking -> {
                    if (booking.getStatus() == BookingStatus.CONFIRMED && booking.getBookingDateTime().isBefore(LocalDateTime.now())) {
                        log.info("Changing status of booking with ID: {} from {} to COMPLETED", booking.getBookingId(), booking.getStatus());
                        return booking.toBuilder().status(BookingStatus.COMPLETED).build();
                    }
                    return booking;
                }).toList();

        user.setBookings(updatedBookings);
    }
}
