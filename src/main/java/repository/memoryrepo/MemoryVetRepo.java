package repository.memoryrepo;

import domain.Veterinarian;
import repository.VetRepository;

import java.util.HashMap;
import java.util.Map;

public class MemoryVetRepo implements VetRepository {
    private final Map<Integer, Veterinarian> vets = new HashMap<>();
    @Override
    public boolean addVet(int idCard, String password, String name, String address, String phone) {
        Veterinarian vet = new Veterinarian(idCard, password, name, address, phone);
        vets.put(idCard, vet);
        return true;
    }

    @Override
    public boolean updateVet(Veterinarian vet) {
        vets.put(vet.getIdCard(), vet);
        return true;
    }

    @Override
    public boolean deleteVet(int idCard) {
        vets.remove(idCard);
        return true;
    }
}
