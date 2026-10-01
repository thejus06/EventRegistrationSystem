package eventregistration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class ParticipantDAO {

    // Save participant to MySQL
    public void addParticipant(Participant participant) {

        String sql = "INSERT INTO participants (id, name, contact) " +
                "VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(
                    1,
                    participant.getParticipantId());

            statement.setString(
                    2,
                    participant.getName());

            statement.setString(
                    3,
                    participant.getContactInformation());

            statement.executeUpdate();

            System.out.println(
                    "Participant saved to database successfully.");

        } catch (SQLException e) {

            System.out.println(
                    "Error saving participant to database.");

            e.printStackTrace();
        }
    }

    // Load all participants from MySQL
    public ArrayList<Participant> getAllParticipants() {

        ArrayList<Participant> participants = new ArrayList<>();

        String sql = "SELECT id, name, contact FROM participants";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                java.sql.ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                int id = resultSet.getInt("id");

                String name = resultSet.getString("name");

                String contact = resultSet.getString("contact");

                Participant participant = new Participant(
                        id,
                        name,
                        contact);

                participants.add(participant);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error loading participants from database.");

            e.printStackTrace();
        }

        return participants;
    }

    // Display all participants from MySQL
    public void displayAllParticipants() {

        ArrayList<Participant> participants = getAllParticipants();

        System.out.println(
                "\n===== Participants from Database =====");

        if (participants.isEmpty()) {

            System.out.println(
                    "No participants found.");

            return;
        }

        for (Participant participant : participants) {

            System.out.println(
                    "ID: "
                            + participant.getParticipantId()
                            + " | Name: "
                            + participant.getName()
                            + " | Contact: "
                            + participant.getContactInformation());
        }
    }
}