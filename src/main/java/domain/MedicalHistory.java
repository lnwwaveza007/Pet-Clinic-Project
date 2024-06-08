package domain;

import java.io.Serializable;

public class MedicalHistory implements Serializable {
    private final int medicalHistoryId;
    private String date;
    private String description;
    private int petId;
    private int veterinarianId;

    public MedicalHistory(int medicalHistoryId, String date, String description, int petId, int veterinarianId){
        this.medicalHistoryId = medicalHistoryId;
        this.date = date;
        this.description = description;
        this.petId = petId;
        this.veterinarianId = veterinarianId;
    }

    public int getMedicalHistoryId() {
        return medicalHistoryId;
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
