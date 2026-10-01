package eventregistration;

public class Participant {

    // Attributes
    private int participantId;
    private String name;
    private String contactInformation;

    // Constructor
    public Participant(int participantId, String name, String contactInformation) {
        this.participantId = participantId;
        this.name = name;
        this.contactInformation = contactInformation;
    }

    // Getter for participant ID
    public int getParticipantId() {
        return participantId;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Getter for contact information
    public String getContactInformation() {
        return contactInformation;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Setter for contact information
    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    // Display participant details
    public void displayParticipantDetails() {
        System.out.println("Participant ID: " + participantId);
        System.out.println("Name: " + name);
        System.out.println("Contact Information: " + contactInformation);
    }
}