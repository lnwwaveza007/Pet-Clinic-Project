package repository;

import domain.Veterinarian;

import java.util.stream.Stream;

public interface VetRepository {
    public boolean addVet(int idcard, String password, String name,String address, String phone);
    public boolean updateVet(Veterinarian vet);
    public Veterinarian findVet(int idcard);
    public boolean deleteVet(int idcard);
    public Stream<Veterinarian> getAllVets();
}
