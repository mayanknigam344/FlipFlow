# FlipFlow – Gym Booking and Scheduling Backend System

**FlipFlow** is a backend system designed to support a fitness platform partnering with gyms across Bangalore (with future expansion to other cities). This system provides a scalable and extensible solution for managing gym centers, workouts, slots, user bookings, and cancellations with support for premium and normal user personas.

## 🚀 Features

- **Multi-Center Support**: Add gym centers with details such as name, city, location, latitude, and longitude.
- **Flexible Slot Scheduling**: Configure centers with multiple hourly slots (e.g., 6–9 AM, 6–9 PM) and specify operating days.
- **Workout Variants**: Support for multiple workouts per slot (e.g., Yoga, Cardio, Swimming, Weights) with extensibility for new types.
- **User Personas**:
    - `VIP`: Access to premium slots and prioritized queueing.
    - `NORMAL`: Limited slot access, fallback priority on waitlists.
- **Booking Rules**:
    - Max 3 slots per day per center.
    - Multiple bookings allowed across different centers.
- **Waitlist Management**: Cancellations trigger reallocation of slots based on user persona priority (VIPs first).
- **Extensible Architecture**: Designed with separation of concerns and abstraction to support future enhancements like dynamic pricing or city-based filtering.
- **In-Memory Implementation**: Uses efficient in-memory data structures with no external DB dependencies.

## 🧪 Testable Functionalities

- Add new centers with relevant details
- Add new workout types per center
- Add or delete workout slots with capacity and waitlist limits
- View all available workouts in a center
- Register users
- Get available slots for a center on a given day (filtered by user type)
- Show slots for a user based on persona, workout type, and time range
- Book a workout for a user if slots are available
- View bookings for a user on a given day
- Cancel a booking and auto-assign the slot from the waitlist (VIPs prioritized)

## 🧰 Tech Stack

- Java 17+
- No external database – purely in-memory
- Object-oriented design
- Clean code architecture using design patterns (Strategy, Decorator, etc.)
- Modular and testable codebase

## 🏗️ Getting Started

1. Clone the repository.
2. Open the project in your favorite IDE.
3. Run the `main()` method from the driver/test class to execute and validate the features.
4. Use the provided test cases or extend them with your own for additional validations.

## 📁 Suggested Folder Structure

```
/flipflow
│
├── model/
│   └── entities, DTOs, and enums
│
├── service/
│   └── business logic (booking, slot management, user handling)
│
├── repository/
│   └── in-memory data stores
│
├── util/
│   └── helper utilities (distance calculation, pricing, etc.)
│
├── driver/
│   └── main test/demo class
│
└── README.md
```