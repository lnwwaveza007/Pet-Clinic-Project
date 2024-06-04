package repository;

import domain.MedicalHistory;

import java.util.stream.Stream;

public interface MedHisRepository {
    public boolean addMedHis(String date, String description, int petId, int vetId);
    public MedicalHistory getMedHis(int medHisId);
    public Stream<MedicalHistory> getAllMedHis();
    public Stream<MedicalHistory> getMedHisFromPet(int petId);
    public Stream<MedicalHistory> getMedHisFromVet(int vetId);
    public Stream<MedicalHistory> getMedHisFromOwner(int ownerId);
}
