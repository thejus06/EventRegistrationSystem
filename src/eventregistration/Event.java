package eventregistration;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Event {

    // Event details
    private String eventName;
    private String date;
    private String location;
    private int maximumCapacity;
    private LocalDateTime registrationDeadline;

    // Lists of participants
    private ArrayList<Participant> participants;
    private ArrayList<Participant> waitlist;

    // Constructor
    public Event(String eventName, String date, String location,
            int maximumCapacity, LocalDateTime registrationDeadline) {

        this.eventName = eventName;
        this.date = date;
        this.location = location;
        this.maximumCapacity = maximumCapacity;
        this.registrationDeadline = registrationDeadline;

        participants = new ArrayList<>();
        waitlist = new ArrayList<>();
    }

    // Getters
    public String getEventName() {
        return eventName;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public int getMaximumCapacity() {
        return maximumCapacity;
    }

    public LocalDateTime getRegistrationDeadline() {
        return registrationDeadline;
    }

    public ArrayList<Participant> getParticipants() {
        return participants;
    }

    public ArrayList<Participant> getWaitlist() {
        return waitlist;
    }

    // Check whether registration deadline has passed
    public boolean isRegistrationOpen() {
        return LocalDateTime.now().isBefore(registrationDeadline);
    }

    // Check whether the event is full
    public boolean isFull() {
        return participants.size() >= maximumCapacity;
    }

    // Add participant to registered list
    public void addParticipant(Participant participant) {
        participants.add(participant);
    }

    // Remove participant from registered list
    public void removeParticipant(Participant participant) {
        participants.remove(participant);
    }

    // Add participant to waitlist
    public void addToWaitlist(Participant participant) {
        waitlist.add(participant);
    }

    // Remove participant from waitlist
    public void removeFromWaitlist(Participant participant) {
        waitlist.remove(participant);
    }

    // Display event details
    public void displayEventDetails() {

        System.out.println("\n----- Event Details -----");
        System.out.println("Event Name: " + eventName);
        System.out.println("Date: " + date);
        System.out.println("Location: " + location);
        System.out.println("Maximum Capacity: " + maximumCapacity);
        System.out.println("Registration Deadline: " + registrationDeadline);
        System.out.println("Registered Participants: " + participants.size());
        System.out.println("Waitlist Size: " + waitlist.size());
    }

    // Display registered participants
    public void displayParticipants() {

        System.out.println("\n----- Registered Participants -----");

        if (participants.isEmpty()) {
            System.out.println("No participants registered.");
            return;
        }

        for (Participant participant : participants) {
            System.out.println(
                    participant.getParticipantId()
                            + " - "
                            + participant.getName());
        }
    }

    // Display waitlisted participants
    public void displayWaitlist() {

        System.out.println("\n----- Waitlist -----");

        if (waitlist.isEmpty()) {
            System.out.println("Waitlist is empty.");
            return;
        }

        for (Participant participant : waitlist) {
            System.out.println(
                    participant.getParticipantId()
                            + " - "
                            + participant.getName());
        }
    }
}