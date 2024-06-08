package repository.filerepo;

import domain.Veterinarian;
import domain.exceptions.DataNotFound;
import domain.exceptions.DuplicateDataException;
import repository.VetRepository;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class FileVetRepo implements VetRepository {
    private static final String VET_FILE = "datas/vets.dat";
    private Map<Integer, Veterinarian> vets = new HashMap<>();

    public FileVetRepo() {
        //Check Directory
        File directory = new File("datas");
        if (!directory.exists()) {
            directory.mkdir();
        }

        if (new File(VET_FILE).exists()) {
            // Load data from file
            try (FileInputStream fis = new FileInputStream(VET_FILE); BufferedInputStream bis = new BufferedInputStream(fis);ObjectInputStream ois = new ObjectInputStream(bis)) {
                vets = (Map<Integer, Veterinarian>) ois.readObject();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean addVet(int idCard, String password, String name, String address, String phone) {
        if (vets.containsKey(idCard)) {
            throw new DuplicateDataException("Vet already exists");
        }
        Veterinarian vet = new Veterinarian(idCard, password, name, address, phone);
        vets.put(idCard, vet);
        //File writing
        try (FileOutputStream fos = new FileOutputStream(VET_FILE); BufferedOutputStream bos = new BufferedOutputStream(fos); ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(vets);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public boolean updateVet(Veterinarian vet) {
        if (!vets.containsKey(vet.getIdCard())) {
            throw new DataNotFound("Vet does not exist");
        }
        vets.put(vet.getIdCard(), vet);
        //File writing
        try (FileOutputStream fos = new FileOutputStream(VET_FILE); BufferedOutputStream bos = new BufferedOutputStream(fos); ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(vets);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public boolean deleteVet(int idCard) {
        if (!vets.containsKey(idCard)) {
            throw new DataNotFound("Vet does not exist");
        }
        vets.remove(idCard);
        //File writing
        try (FileOutputStream fos = new FileOutputStream(VET_FILE); BufferedOutputStream bos = new BufferedOutputStream(fos); ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(vets);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
