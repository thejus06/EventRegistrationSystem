package eventregistration;

import java.time.LocalDateTime;

public class Registration {

    // References to the event and participant
    private Event event;
    private Participant participant;

    // Time at which registration was made
    private LocalDateTime registrationTime;

    // Constructor
    public Registration(Event event, Participant participant) {
        this.event = event;
        this.participant = participant;
        this.registrationTime = LocalDateTime.now();
    }

    // Getter for event
    public Event getEvent() {
        return event;
    }

    // Getter for participant
    public Participant getParticipant() {
        return participant;
    }

    // Getter for registration time
    public LocalDateTime getRegistrationTime() {
        return registrationTime;
    }

    // Display registration details
    public void displayRegistrationDetails() {

        System.out.println("\n----- Registration Details -----");
        System.out.println("Event: " + event.getEventName());
        System.out.println("Participant ID: "
                + participant.getParticipantId());
        System.out.println("Participant Name: "
                + participant.getName());
        System.out.println("Registration Time: "
                + registrationTime);
    }
}