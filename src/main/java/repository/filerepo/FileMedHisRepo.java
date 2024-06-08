package repository.filerepo;

import domain.MedicalHistory;
import domain.Pet;
import domain.Veterinarian;
import domain.exceptions.DataNotFound;
import repository.MedHisRepository;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class FileMedHisRepo implements MedHisRepository {
    private static final String MEDHIS_FILE = "medHistories.dat";
    private Map<Integer, MedicalHistory> medHistories = new HashMap<>();
    private int nextId = 1;

    public FileMedHisRepo() {
        if (new File(MEDHIS_FILE).exists()) {
            // Load data from file
            try (FileInputStream fis = new FileInputStream(MEDHIS_FILE); BufferedInputStream bis = new BufferedInputStream(fis); ObjectInputStream ois = new ObjectInputStream(bis)) {
                nextId = (int) ois.readInt();
                medHistories = (Map<Integer, MedicalHistory>) ois.readObject();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean addMedHis(String date, String description, int petId, int vetId) {
        if (date == null || description == null) {
            throw new IllegalArgumentException("Date and description cannot be null");
        }
        int medicalHistoryId = nextId++;
        MedicalHistory medHis = new MedicalHistory(medicalHistoryId, date, description, petId, vetId);
        medHistories.put(nextId, medHis);
        try (FileOutputStream fos = new FileOutputStream(MEDHIS_FILE); BufferedOutputStream bos = new BufferedOutputStream(fos); ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeInt(nextId);
            oos.writeObject(medHistories);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public MedicalHistory getMedHis(int medHisId) {
        if (!medHistories.containsKey(medHisId)) {
            throw new DataNotFound("Medical History not found");
        }
        return medHistories.get(medHisId);
    }

    @Override
    public Stream<MedicalHistory> getAllMedHis() {
        return medHistories.values().stream();
    }

    @Override
    public Stream<MedicalHistory> getMedHisFromPet(int petId) {
        return medHistories.values().stream()
                .filter(medHis -> medHis.getPetId() == petId);
    }

    @Override
    public Stream<MedicalHistory> getMedHisFromVet(int vetId) {
        return medHistories.values().stream()   
                .filter(medHis -> medHis.getVeterinarianId() == vetId);
    }

    @Override
    public Stream<MedicalHistory> getMedHisFromOwner(int ownerId) {
        return medHistories.values().stream()
                .filter(medHis -> medHis.getPetId() == ownerId);
    }
}
