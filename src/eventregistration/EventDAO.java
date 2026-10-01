package eventregistration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventDAO {

    // Save event to MySQL
    public void addEvent(Event event) {

        String sql = "INSERT INTO events " +
                "(name, event_date, location, max_capacity, registration_deadline) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            java.time.LocalDate eventDate = java.time.LocalDate.parse(
                    event.getDate(),
                    formatter);

            statement.setString(
                    1,
                    event.getEventName());

            statement.setDate(
                    2,
                    java.sql.Date.valueOf(eventDate));

            statement.setString(
                    3,
                    event.getLocation());

            statement.setInt(
                    4,
                    event.getMaximumCapacity());

            statement.setTimestamp(
                    5,
                    java.sql.Timestamp.valueOf(
                            event.getRegistrationDeadline()));

            statement.executeUpdate();

            System.out.println(
                    "Event saved to database successfully.");

        } catch (SQLException e) {

            System.out.println(
                    "Error saving event to database.");

            e.printStackTrace();

        } catch (Exception e) {

            System.out.println(
                    "Invalid event date format.");

            e.printStackTrace();
        }
    }

    // Load all events from MySQL
    public ArrayList<Event> getAllEvents() {

        ArrayList<Event> events = new ArrayList<>();

        String sql = "SELECT name, event_date, location, " +
                "max_capacity, registration_deadline " +
                "FROM events";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                java.sql.ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                String name = resultSet.getString("name");

                java.sql.Date eventDate = resultSet.getDate("event_date");

                String location = resultSet.getString("location");

                int maxCapacity = resultSet.getInt("max_capacity");

                java.sql.Timestamp deadline = resultSet.getTimestamp(
                        "registration_deadline");

                // Convert MySQL DATE to the format
                // used by the Event class
                String formattedDate = new java.text.SimpleDateFormat(
                        "dd-MM-yyyy").format(eventDate);

                LocalDateTime registrationDeadline = deadline.toLocalDateTime();

                Event event = new Event(
                        name,
                        formattedDate,
                        location,
                        maxCapacity,
                        registrationDeadline);

                events.add(event);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error loading events from database.");

            e.printStackTrace();
        }

        return events;
    }
}