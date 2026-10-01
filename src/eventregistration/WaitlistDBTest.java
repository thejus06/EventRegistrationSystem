package eventregistration;

import java.time.LocalDateTime;

public class WaitlistDBTest {

    public static void main(String[] args) {

        Event event = new Event(
                "Java Workshop",
                "15-10-2026",
                "College Auditorium",
                2,
                LocalDateTime.of(2026, 10, 10, 18, 0));

        Participant participant = new Participant(102, "Anoop", "1237812313");

        WaitlistDAO waitlistDAO = new WaitlistDAO();

        waitlistDAO.addToWaitlist(event, participant);
    }
}