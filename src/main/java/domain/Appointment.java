package domain;

public class Appointment {
    private final int appointmentId;
    private String date;
    private String description;
    private int petId;
    private int veterinarianId;

    public Appointment(int appointmentId, String date, String description, int petId, int veterinarianId){
        this.appointmentId = appointmentId;
        this.date = date;
        this.description = description;
        this.petId = petId;
        this.veterinarianId = veterinarianId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public int getVeterinarianId() {
        return veterinarianId;
    }

    public void setVeterinarianId(int veterinarianId) {
        this.veterinarianId = veterinarianId;
    }
}
