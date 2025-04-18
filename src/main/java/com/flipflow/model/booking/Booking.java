package com.flipflow.model.booking;

import com.flipflow.model.Center;
import com.flipflow.model.WorkoutVariation;
import com.flipflow.model.slot.Slot;
import com.flipflow.model.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder(toBuilder = true)
public class Booking {
    private String bookingId;
    private LocalDateTime bookingDateTime;
    private Center center;
    private Slot slot;
    private WorkoutVariation workoutVariation;
    private User user;
    // 0 for Normal 1 for VIP
    private int priority;
    private BookingStatus status;

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId='" + bookingId + '\'' +
                ", bookingDateTime=" + bookingDateTime +
                ", center=" + center +
                ", workoutVariation=" + workoutVariation +
                ", user=" + user +
                ", priority=" + priority +
                ", status=" + status+
                '}';
    }
}
