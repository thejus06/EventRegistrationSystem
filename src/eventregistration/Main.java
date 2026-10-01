package eventregistration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static EventCoordinator coordinator = new EventCoordinator();

    private static ArrayList<Participant> participants = new ArrayList<>();

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void main(String[] args) {

        boolean running = true;

        System.out.println("====================================");
        System.out.println("   EVENT REGISTRATION SYSTEM");
        System.out.println("====================================");

        while (running) {

            displayMenu();

            int choice = readInteger("Enter your choice: ");

            switch (choice) {

                case 1:
                    createEvent();
                    break;

                case 2:
                    createParticipant();
                    break;

                case 3:
                    registerParticipant();
                    break;

                case 4:
                    cancelRegistration();
                    break;

                case 5:
                    coordinator.displayAllEvents();
                    break;

                case 6:
                    displayParticipants();
                    break;

                case 7:
                    displayWaitlist();
                    break;

                case 8:
                    coordinator.displayRegistrations();
                    break;

                case 9:
                    running = false;
                    System.out.println(
                            "Thank you for using the system.");
                    break;

                default:
                    System.out.println(
                            "Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    // Display the main menu
    private static void displayMenu() {

        System.out.println("\n========== MENU ==========");
        System.out.println("1. Create Event");
        System.out.println("2. Create Participant");
        System.out.println("3. Register Participant");
        System.out.println("4. Cancel Registration");
        System.out.println("5. Display All Events");
        System.out.println("6. Display Participants");
        System.out.println("7. Display Waitlist");
        System.out.println("8. Display Registration Records");
        System.out.println("9. Exit");
        System.out.println("==========================");
    }

    // Create a new event
    private static void createEvent() {

        System.out.println("\n----- Create Event -----");

        System.out.print("Enter event name: ");
        String eventName = scanner.nextLine();

        System.out.print("Enter event date: ");
        String date = scanner.nextLine();

        System.out.print("Enter location: ");
        String location = scanner.nextLine();

        int capacity = readInteger(
                "Enter maximum capacity: ");

        LocalDateTime deadline = readDateTime(
                "Enter registration deadline "
                        + "(yyyy-MM-dd HH:mm): ");

        Event event = new Event(
                eventName,
                date,
                location,
                capacity,
                deadline);

        coordinator.createEvent(event);
    }

    // Create a new participant
    private static void createParticipant() {

        System.out.println("\n----- Create Participant -----");

        int id = readInteger("Enter participant ID: ");

        System.out.print("Enter participant name: ");
        String name = scanner.nextLine();

        System.out.print("Enter contact information: ");
        String contact = scanner.nextLine();

        Participant participant = new Participant(id, name, contact);

        participants.add(participant);

        System.out.println(
                "Participant created successfully.");
    }

    // Register participant for an event
    private static void registerParticipant() {

        System.out.println("\n----- Register Participant -----");

        System.out.print("Enter event name: ");
        String eventName = scanner.nextLine();

        Event event = coordinator.findEventByName(eventName);

        if (event == null) {
            System.out.println("Event not found.");
            return;
        }

        int participantId = readInteger("Enter participant ID: ");

        Participant participant = findParticipantById(participantId);

        if (participant == null) {
            System.out.println("Participant not found.");
            return;
        }

        try {

            coordinator.registerParticipant(
                    event,
                    participant);

        } catch (RegistrationException e) {

            System.out.println(
                    "Registration failed: "
                            + e.getMessage());
        }
    }

    // Cancel a participant's registration
    private static void cancelRegistration() {

        System.out.println(
                "\n----- Cancel Registration -----");

        System.out.print("Enter event name: ");
        String eventName = scanner.nextLine();

        Event event = coordinator.findEventByName(eventName);

        if (event == null) {
            System.out.println("Event not found.");
            return;
        }

        int participantId = readInteger("Enter participant ID: ");

        Participant participant = findParticipantById(participantId);

        if (participant == null) {
            System.out.println("Participant not found.");
            return;
        }

        coordinator.cancelRegistration(
                event,
                participant);
    }

    // Display registered participants
    private static void displayParticipants() {

        System.out.println(
                "\n----- Display Participants -----");

        System.out.print("Enter event name: ");
        String eventName = scanner.nextLine();

        Event event = coordinator.findEventByName(eventName);

        if (event == null) {
            System.out.println("Event not found.");
            return;
        }

        coordinator.displayParticipants(event);
    }

    // Display waitlisted participants
    private static void displayWaitlist() {

        System.out.println(
                "\n----- Display Waitlist -----");

        System.out.print("Enter event name: ");
        String eventName = scanner.nextLine();

        Event event = coordinator.findEventByName(eventName);

        if (event == null) {
            System.out.println("Event not found.");
            return;
        }

        coordinator.displayWaitlist(event);
    }

    // Find participant using ID
    private static Participant findParticipantById(
            int participantId) {

        for (Participant participant : participants) {

            if (participant.getParticipantId() == participantId) {

                return participant;
            }
        }

        return null;
    }

    // Read an integer safely
    private static int readInteger(String message) {

        while (true) {

            try {

                System.out.print(message);

                int value = Integer.parseInt(
                        scanner.nextLine());

                return value;

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number.");
            }
        }
    }

    // Read date and time safely
    private static LocalDateTime readDateTime(
            String message) {

        while (true) {

            try {

                System.out.print(message);

                String input = scanner.nextLine();

                return LocalDateTime.parse(
                        input,
                        dateTimeFormatter);

            } catch (Exception e) {

                System.out.println(
                        "Invalid date/time format.");

                System.out.println(
                        "Use: yyyy-MM-dd HH:mm");
            }
        }
    }
}