package repository.filerepo;

import domain.Owner;
import domain.Veterinarian;
import domain.exceptions.DataNotFound;
import domain.exceptions.DuplicateDataException;
import repository.OwnerRepository;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class FileOwnerRepo implements OwnerRepository {
    private static final String OWNER_FILE = "owners.dat";
    private Map<Integer, Owner> owners = new HashMap<>();

    public FileOwnerRepo() {
        if (new File(OWNER_FILE).exists()) {
            // Load data from file
            try (FileInputStream fis = new FileInputStream(OWNER_FILE); BufferedInputStream bis = new BufferedInputStream(fis); ObjectInputStream ois = new ObjectInputStream(bis)) {
                owners = (Map<Integer, Owner>) ois.readObject();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean addOwner(int idCard, String password, String name, String address, String phone) {
        if (owners.containsKey(idCard)) {
            throw new DuplicateDataException("Owner already exists");
        }
        Owner owner = new Owner(idCard, password, name, address, phone);
        owners.put(idCard, owner);
        try (FileOutputStream fos = new FileOutputStream(OWNER_FILE); BufferedOutputStream bos = new BufferedOutputStream(fos); ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(owners);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public Owner updateOwner(Owner owner) {
        if (!owners.containsKey(owner.getIdCard())) {
            throw new DataNotFound("Owner not found");
        }
        owners.put(owner.getIdCard(), owner);
        try (FileOutputStream fos = new FileOutputStream(OWNER_FILE); BufferedOutputStream bos = new BufferedOutputStream(fos); ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(owners);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
