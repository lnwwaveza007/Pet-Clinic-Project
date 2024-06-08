package repository.memoryrepo;

import domain.Owner;
import domain.exceptions.DataNotFound;
import domain.exceptions.DuplicateDataException;
import repository.OwnerRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class MemoryOwnerRepo implements OwnerRepository {
    private final Map<Integer, Owner> owners = new HashMap<>();

    @Override
    public boolean addOwner(int idCard, String password, String name, String address, String phone) {
        if (owners.containsKey(idCard)) {
            throw new DuplicateDataException("Owner already exists");
        }
        Owner owner = new Owner(idCard, password, name, address, phone);
        owners.put(idCard, owner);
        return true;
    }

    @Override
    public Owner updateOwner(Owner owner) {
        if (!owners.containsKey(owner.getIdCard())) {
            throw new DataNotFound("Owner not found");
        }
        owners.put(owner.getIdCard(), owner);
        return owner;
    }

    @Override
    public Owner findOwner(int idCard) {
        if (!owners.containsKey(idCard)) {
            throw new DataNotFound("Owner not found");
        }
        return owners.get(idCard);
    }

    @Override
    public Stream<Owner> getAllOwners() {
        return owners.values().stream();
    }
}
