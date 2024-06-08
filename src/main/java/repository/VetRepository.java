package repository;

import domain.Veterinarian;

import java.util.stream.Stream;

public interface VetRepository {
    public Veterinarian getVet(int idCard);
    public Stream<Veterinarian> getAllVets();
    public boolean addVet(int idCard, String password, String name,String address, String phone);
    public boolean updateVet(Veterinarian vet);
    public boolean deleteVet(int idCard);
}
