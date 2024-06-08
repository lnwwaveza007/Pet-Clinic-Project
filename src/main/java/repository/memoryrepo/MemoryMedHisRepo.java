package repository.memoryrepo;

import domain.MedicalHistory;
import repository.MedHisRepository;

import java.util.stream.Stream;
import java.util.Map;
import java.util.HashMap;

public class MemoryMedHisRepo implements MedHisRepository {
    private final Map<Integer, MedicalHistory> medHistories = new HashMap<>();
    private int nextId = 1;

    @Override
    public boolean addMedHis(String date, String description, int petId, int vetId) {
        int medicalHistoryId = nextId++;
        MedicalHistory medHis = new MedicalHistory(medicalHistoryId, date, description, petId, vetId);
        medHistories.put(nextId, medHis);
        return true;
    }

    @Override
    public MedicalHistory getMedHis(int medHisId) {
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
