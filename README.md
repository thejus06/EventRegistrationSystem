# Event Registration System

A Java-based Event Registration System developed using Object-Oriented Programming (OOP), MySQL, and JDBC.

## Project Description

The Event Registration System allows event coordinators to create events and manage participant registrations.

The system supports:

- Creating events
- Creating participants
- Registering participants for events
- Checking registration deadlines
- Checking event capacity
- Automatically adding participants to a waitlist when an event is full
- Cancelling participant registrations
- Automatically promoting participants from the waitlist
- Displaying event details
- Displaying registered participants
- Displaying waitlist information
- Storing data permanently in a MySQL database

The application is currently implemented as a Command Line Interface (CLI) application.

## Technologies Used

- Java
- Object-Oriented Programming (OOP)
- MySQL
- JDBC
- MySQL Connector/J
- ArrayList
- Exception Handling

## OOP Concepts Used

The project demonstrates several Java OOP concepts:

- Classes and Objects
- Encapsulation
- Association
- Constructors
- Methods
- Access Modifiers
- Collections
- Exception Handling

## Main Classes

### Participant

Stores participant information such as:

- Participant ID
- Name
- Contact information

### Event

Stores event information such as:

- Event name
- Date
- Location
- Maximum capacity
- Registration deadline
- Registered participants
- Waitlist

### Registration

Connects an event with a participant and records the registration time.

### EventCoordinator

Handles the main event management and registration logic.

It manages:

- Events
- Participants
- Registrations
- Waitlists

## Database

The project uses MySQL for persistent data storage.

Database name:

```text
event_registration