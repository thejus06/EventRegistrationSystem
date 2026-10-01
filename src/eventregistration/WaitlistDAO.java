package eventregistration;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WaitlistDAO {

    // Add participant to waitlist
    public void addToWaitlist(
            Event event,
            Participant participant) {

        String sql = "INSERT INTO waitlist " +
                "(event_id, participant_id, waitlist_time) " +
                "VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    getEventId(event));

            statement.setInt(
                    2,
                    participant.getParticipantId());

            statement.setTimestamp(
                    3,
                    java.sql.Timestamp.valueOf(
                            java.time.LocalDateTime.now()));

            statement.executeUpdate();

            System.out.println(
                    "Participant added to database waitlist successfully.");

        } catch (SQLException e) {

            System.out.println(
                    "Error adding participant to waitlist.");

            e.printStackTrace();
        }
    }

    // Remove participant from waitlist
    public void removeFromWaitlist(
            Event event,
            Participant participant) {

        String sql = "DELETE FROM waitlist " +
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
                        "Participant removed from database waitlist.");

            } else {

                System.out.println(
                        "No matching waitlist entry found.");
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error removing participant from waitlist.");

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

    public void loadWaitlist(
            ArrayList<Event> events,
            ArrayList<Participant> participants) {

        String sql = "SELECT w.event_id, w.participant_id, " +
                "e.name " +
                "FROM waitlist w " +
                "JOIN events e ON w.event_id = e.id " +
                "ORDER BY w.waitlist_time";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                java.sql.ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                int participantId = resultSet.getInt("participant_id");

                String eventName = resultSet.getString("name");

                Event event = null;

                for (Event currentEvent : events) {

                    if (currentEvent.getEventName()
                            .equalsIgnoreCase(eventName)) {

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

                    if (!event.getWaitlist()
                            .contains(participant)) {

                        event.addToWaitlist(participant);
                    }
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error loading waitlist from database.");

            e.printStackTrace();
        }
    }
}