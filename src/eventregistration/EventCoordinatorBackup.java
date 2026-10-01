package eventregistration;

import java.util.ArrayList;

public class EventCoordinatorBackup {

    // List of all events
    private ArrayList<Event> events;

    // List of all registrations
    private ArrayList<Registration> registrations;

    // Constructor
    public EventCoordinatorBackup() {
        events = new ArrayList<>();
        registrations = new ArrayList<>();
    }

    // Create and add a new event
    public void createEvent(Event event) {
        events.add(event);
        System.out.println("Event created successfully.");
    }

    // Register a participant for an event
    public void registerParticipant(
            Event event,
            Participant participant) throws RegistrationException {

        // Check registration deadline
        if (!event.isRegistrationOpen()) {
            throw new RegistrationException(
                    "Registration deadline has passed.");
        }

        // Check whether participant is already registered
        if (event.getParticipants().contains(participant)) {
            throw new RegistrationException(
                    "Participant is already registered.");
        }

        // Check whether participant is already on waitlist
        if (event.getWaitlist().contains(participant)) {
            throw new RegistrationException(
                    "Participant is already on the waitlist.");
        }

        // If event is full, add participant to waitlist
        if (event.isFull()) {

            event.addToWaitlist(participant);

            System.out.println(
                    "Event is full. Participant added to waitlist.");

        } else {

            // Register participant
            event.addParticipant(participant);

            Registration registration = new Registration(event, participant);

            registrations.add(registration);

            System.out.println(
                    "Participant registered successfully.");
        }
    }

    // Cancel a participant's registration
    public void cancelRegistration(
            Event event,
            Participant participant) {

        if (!event.getParticipants().contains(participant)) {
            System.out.println(
                    "Participant is not registered for this event.");
            return;
        }

        // Remove participant from registered list
        event.removeParticipant(participant);

        // Remove corresponding registration record
        removeRegistration(event, participant);

        System.out.println(
                "Registration cancelled successfully.");

        // Promote first person from waitlist
        promoteFromWaitlist(event);
    }

    // Remove registration record
    private void removeRegistration(
            Event event,
            Participant participant) {

        for (int i = 0; i < registrations.size(); i++) {

            Registration registration = registrations.get(i);

            if (registration.getEvent() == event
                    && registration.getParticipant() == participant) {

                registrations.remove(i);
                break;
            }
        }
    }

    // Promote a participant from the waitlist
    private void promoteFromWaitlist(Event event) {

        if (event.getWaitlist().isEmpty()) {
            return;
        }

        Participant participant = event.getWaitlist().remove(0);

        event.addParticipant(participant);

        Registration registration = new Registration(event, participant);

        registrations.add(registration);

        System.out.println(
                participant.getName()
                        + " has been promoted from the waitlist.");
    }

    // Display all events
    public void displayAllEvents() {

        if (events.isEmpty()) {
            System.out.println("No events available.");
            return;
        }

        System.out.println("\n===== Available Events =====");

        for (Event event : events) {
            event.displayEventDetails();
        }
    }

    // Display participants of a specific event
    public void displayParticipants(Event event) {
        event.displayParticipants();
    }

    // Display waitlist of a specific event
    public void displayWaitlist(Event event) {
        event.displayWaitlist();
    }

    // Find an event by its name
    public Event findEventByName(String eventName) {

        for (Event event : events) {

            if (event.getEventName().equalsIgnoreCase(eventName)) {
                return event;
            }
        }

        return null;
    }

    // Display all registration records
    public void displayRegistrations() {

        if (registrations.isEmpty()) {
            System.out.println("No registration records.");
            return;
        }

        System.out.println("\n===== Registration Records =====");

        for (Registration registration : registrations) {
            registration.displayRegistrationDetails();
        }
    }
}