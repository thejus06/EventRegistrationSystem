package eventregistration;

public class CoordinatorDBTest {

    public static void main(String[] args) {

        EventCoordinator coordinator = new EventCoordinator();

        Participant participant = new Participant(
                103,
                "Arun",
                "12312313231");

        coordinator.createParticipant(participant);
    }
}