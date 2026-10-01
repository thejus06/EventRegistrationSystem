package eventregistration;

import java.time.LocalDateTime;

public class CoordinatorRegistrationTest {

    public static void main(String[] args) {

        EventCoordinator coordinator = new EventCoordinator();

        // Create a new test event
        Event event = new Event(
                "JDBC Integration Test",
                "20-10-2026",
                "Computer Lab",
                1,
                LocalDateTime.of(2026, 10, 15, 18, 0));

        // Participant 103 already exists in MySQL
        Participant participant = new Participant(
                103,
                "Arun",
                "12312313231");

        // Save event through EventCoordinator
        coordinator.createEvent(event);

        try {

            // Register participant
            coordinator.registerParticipant(
                    event,
                    participant);

        } catch (RegistrationException e) {

            System.out.println(
                    "Registration failed: "
                            + e.getMessage());
        }
    }
}