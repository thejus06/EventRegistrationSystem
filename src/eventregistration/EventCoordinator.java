package eventregistration;

import java.util.ArrayList;

public class EventCoordinator {

    private ArrayList<Event> events;
    private ArrayList<Registration> registrations;

    // Database access objects
    private ParticipantDAO participantDAO;
    private EventDAO eventDAO;
    private RegistrationDAO registrationDAO;
    private WaitlistDAO waitlistDAO;

    public EventCoordinator() {

        events = new ArrayList<>();
        registrations = new ArrayList<>();

        participantDAO = new ParticipantDAO();
        eventDAO = new EventDAO();
        registrationDAO = new RegistrationDAO();
        waitlistDAO = new WaitlistDAO();

        // Load events from MySQL
        events = eventDAO.getAllEvents();

        // Load participants from MySQL
        ArrayList<Participant> participants = participantDAO.getAllParticipants();

        // Load registrations from MySQL
        registrations = registrationDAO.getAllRegistrations(
                events,
                participants);
        // Load waitlist from MySQL
        waitlistDAO.loadWaitlist(
                events,
                participants);
    }

    // Create event
    public void createEvent(Event event) {

        events.add(event);

        // Save event to MySQL
        eventDAO.addEvent(event);

        System.out.println("Event created successfully.");
    }

    // Create participant
    public void createParticipant(Participant participant) {

        // Save participant to MySQL
        participantDAO.addParticipant(participant);

        System.out.println("Participant created successfully.");
    }

    // Register participant
    public void registerParticipant(
            Event event,
            Participant participant) throws RegistrationException {

        // Check registration deadline
        if (!event.isRegistrationOpen()) {
            throw new RegistrationException(
                    "Registration deadline has passed.");
        }

        // Check if already registered
        if (event.getParticipants().contains(participant)) {
            throw new RegistrationException(
                    "Participant is already registered.");
        }

        // Check if already on waitlist
        if (event.getWaitlist().contains(participant)) {
            throw new RegistrationException(
                    "Participant is already on the waitlist.");
        }

        // If event is full, add to waitlist
        if (event.isFull()) {

            event.addToWaitlist(participant);

            // Save waitlist entry to MySQL
            waitlistDAO.addToWaitlist(event, participant);

            System.out.println(
                    "Event is full. Participant added to waitlist.");

        } else {

            // Add participant to event
            event.addParticipant(participant);

            // Create registration object
            Registration registration = new Registration(event, participant);

            registrations.add(registration);

            // Save registration to MySQL
            registrationDAO.addRegistration(registration);

            System.out.println(
                    "Participant registered successfully.");
        }
    }

    // Cancel registration
    public void cancelRegistration(
            Event event,
            Participant participant) {

        if (!event.getParticipants().contains(participant)) {

            System.out.println(
                    "Participant is not registered for this event.");

            return;
        }

        // Remove participant from Java event list
        event.removeParticipant(participant);

        // Remove registration from Java list
        removeRegistration(event, participant);

        // Remove registration from MySQL
        registrationDAO.deleteRegistration(
                event,
                participant);

        System.out.println(
                "Registration cancelled successfully.");

        // Promote first participant from waitlist
        promoteFromWaitlist(event);
    }

    // Remove registration from ArrayList
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

    // Promote participant from waitlist
    private void promoteFromWaitlist(Event event) {

        if (event.getWaitlist().isEmpty()) {
            return;
        }

        // Get the first participant from the Java waitlist
        Participant participant = event.getWaitlist().remove(0);

        // Remove participant from MySQL waitlist
        waitlistDAO.removeFromWaitlist(
                event,
                participant);

        // Add participant to registered participants
        event.addParticipant(participant);

        // Create a new registration
        Registration registration = new Registration(event, participant);

        registrations.add(registration);

        // Save new registration to MySQL
        registrationDAO.addRegistration(
                registration);

        System.out.println(
                participant.getName()
                        + " has been promoted from the waitlist.");
    }

    // Display all events
    public void displayAllEvents() {

        if (events.isEmpty()) {

            System.out.println(
                    "No events available.");

            return;
        }

        System.out.println(
                "\n===== All Events =====");

        for (Event event : events) {
            event.displayEventDetails();
        }
    }

    // Display registered participants
    public void displayParticipants(Event event) {

        event.displayParticipants();
    }

    // Display waitlist
    public void displayWaitlist(Event event) {

        event.displayWaitlist();
    }

    // Find event by name
    public Event findEventByName(String eventName) {

        for (Event event : events) {

            if (event.getEventName()
                    .equalsIgnoreCase(eventName)) {

                return event;
            }
        }

        return null;
    }

    // Display registration records
    public void displayRegistrations() {

        if (registrations.isEmpty()) {

            System.out.println(
                    "No registration records.");

            return;
        }

        System.out.println(
                "\n===== Registration Records =====");

        for (Registration registration : registrations) {

            registration.displayRegistrationDetails();
        }
    }
}