package repository.memoryrepo;

import domain.Veterinarian;
import domain.exceptions.DataNotFound;
import domain.exceptions.DuplicateDataException;
import repository.VetRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class MemoryVetRepo implements VetRepository {
    private final Map<Integer, Veterinarian> vets = new HashMap<>();

    @Override
    public boolean addVet(int idCard, String password, String name, String address, String phone) {
        if (vets.containsKey(idCard)) {
            throw new DuplicateDataException("Vet already exists");
        }
        Veterinarian vet = new Veterinarian(idCard, password, name, address, phone);
        vets.put(idCard, vet);
        return true;
    }

    @Override
    public boolean updateVet(Veterinarian vet) {
        if (!vets.containsKey(vet.getIdCard())) {
            throw new DataNotFound("Vet does not exist");
        }
        vets.put(vet.getIdCard(), vet);
        return true;
    }

    @Override
    public boolean deleteVet(int idCard) {
        if (!vets.containsKey(idCard)) {
            throw new DataNotFound("Vet does not exist");
        }
        vets.remove(idCard);
        return true;
    }

    @Override
    public Veterinarian getVet(int idCard) {
        if (!vets.containsKey(idCard)) {
            throw new DataNotFound("Vet does not exist");
        }
        return vets.get(idCard);
    }

    @Override
    public Stream<Veterinarian> getAllVets() {
        return vets.values().stream();
    }
}
