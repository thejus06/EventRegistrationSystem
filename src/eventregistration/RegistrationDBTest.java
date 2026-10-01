package eventregistration;

public class RegistrationDBTest {

    public static void main(String[] args) {

        Event event = new Event(
                "Java Workshop",
                "15-10-2026",
                "College Auditorium",
                2,
                java.time.LocalDateTime.of(2026, 10, 10, 18, 0));

        Participant participant = new Participant(101, "Rahul", "983124124");

        Registration registration = new Registration(event, participant);

        RegistrationDAO registrationDAO = new RegistrationDAO();

        registrationDAO.addRegistration(registration);
    }
}