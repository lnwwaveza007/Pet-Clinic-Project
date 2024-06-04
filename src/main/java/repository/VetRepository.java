package repository;

import domain.Veterinarian;

public interface VetRepository {
    public boolean addVet(int idcard, String password, String name,String address, String phone);
    public boolean updateVet(Veterinarian vet);
    public boolean deleteVet(int idcard);
}
