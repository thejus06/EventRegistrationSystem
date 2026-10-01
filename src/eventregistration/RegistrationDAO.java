package eventregistration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class RegistrationDAO {

    // Add a registration to the database
    public void addRegistration(Registration registration) {

        String sql = "INSERT INTO registrations " +
                "(event_id, participant_id, registration_time) " +
                "VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    getEventId(registration.getEvent()));

            statement.setInt(
                    2,
                    registration.getParticipant().getParticipantId());

            statement.setTimestamp(
                    3,
                    java.sql.Timestamp.valueOf(
                            registration.getRegistrationTime()));

            statement.executeUpdate();

            System.out.println(
                    "Registration saved to database successfully.");

        } catch (SQLException e) {

            System.out.println(
                    "Error saving registration to database.");

            e.printStackTrace();
        }
    }

    // Delete a registration from the database
    public void deleteRegistration(
            Event event,
            Participant participant) {

        String sql = "DELETE FROM registrations " +
                "WHERE event_id = ? AND participant_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    getEventId(event));

            statement.setInt(
                    2,
                    participant.getParticipantId());

            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {

                System.out.println(
                        "Registration deleted from database successfully.");

            } else {

                System.out.println(
                        "No matching registration found in database.");
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error deleting registration from database.");

            e.printStackTrace();
        }
    }

    // Find the database ID of an event
    private int getEventId(Event event) {

        String sql = "SELECT id FROM events WHERE name = ?";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(
                    1,
                    event.getEventName());

            try (java.sql.ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    return resultSet.getInt("id");
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error finding event ID.");

            e.printStackTrace();
        }

        return -1;
    }

    public ArrayList<Registration> getAllRegistrations(
            ArrayList<Event> events,
            ArrayList<Participant> participants) {

        ArrayList<Registration> registrations = new ArrayList<>();

        String sql = "SELECT r.event_id, r.participant_id, " +
                "r.registration_time, e.name " +
                "FROM registrations r " +
                "JOIN events e ON r.event_id = e.id";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                java.sql.ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                int eventId = resultSet.getInt("event_id");

                int participantId = resultSet.getInt("participant_id");

                java.sql.Timestamp registrationTime = resultSet.getTimestamp(
                        "registration_time");

                Event event = null;

                for (Event currentEvent : events) {

                    if (currentEvent.getEventName()
                            .equalsIgnoreCase(
                                    resultSet.getString("name"))) {

                        event = currentEvent;
                        break;
                    }
                }

                Participant participant = null;

                for (Participant currentParticipant : participants) {

                    if (currentParticipant.getParticipantId() == participantId) {

                        participant = currentParticipant;
                        break;
                    }
                }

                if (event != null && participant != null) {

                    Registration registration = new Registration(
                            event,
                            participant);

                    registrations.add(registration);

                    // Keep the Event object synchronized
                    if (!event.getParticipants()
                            .contains(participant)) {

                        event.addParticipant(participant);
                    }
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error loading registrations from database.");

            e.printStackTrace();
        }

        return registrations;
    }
}