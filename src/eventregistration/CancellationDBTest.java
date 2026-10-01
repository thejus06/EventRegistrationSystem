package eventregistration;

import java.time.LocalDateTime;

public class CancellationDBTest {

    public static void main(String[] args) {

        EventCoordinator coordinator = new EventCoordinator();

        Event event = new Event(
                "Java Workshop",
                "15-10-2026",
                "College Auditorium",
                2,
                LocalDateTime.of(2026, 10, 10, 18, 0));

        Participant rahul = new Participant(
                101,
                "Rahul",
                "983124124");

        Participant anoop = new Participant(
                102,
                "Anoop",
                "1237812313");

        // Recreate the current in-memory state
        event.addParticipant(rahul);
        event.addToWaitlist(anoop);

        System.out.println("Before cancellation:");

        event.displayParticipants();
        event.displayWaitlist();

        // Cancel Rahul
        coordinator.cancelRegistration(
                event,
                rahul);

        System.out.println("\nAfter cancellation:");

        event.displayParticipants();
        event.displayWaitlist();
    }
}