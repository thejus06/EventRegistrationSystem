package eventregistration;

import java.time.LocalDateTime;

public class EventDBTest {

    public static void main(String[] args) {

        Event event = new Event(
                "Java Workshop",
                "15-10-2026",
                "College Auditorium",
                2,
                LocalDateTime.of(2026, 10, 10, 18, 0));

        EventDAO eventDAO = new EventDAO();

        eventDAO.addEvent(event);
    }
}