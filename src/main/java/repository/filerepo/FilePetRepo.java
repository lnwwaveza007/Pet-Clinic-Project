package repository.filerepo;

import domain.Pet;
import domain.Veterinarian;
import domain.exceptions.DataNotFound;
import repository.PetRepository;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class FilePetRepo implements PetRepository {
    private static final String PET_FILE = "datas/pets.dat";
    private Map<Integer, Pet> pets = new HashMap<>();
    private int nextId = 1;

    public FilePetRepo() {
        //Check Directory
        File directory = new File("datas");
        if (!directory.exists()) {
            directory.mkdir();
        }

        if (new File(PET_FILE).exists()) {
            // Load data from file
            try (FileInputStream fis = new FileInputStream(PET_FILE); BufferedInputStream bis = new BufferedInputStream(fis); ObjectInputStream ois = new ObjectInputStream(bis)) {
                nextId = (int) ois.readInt();
                pets = (Map<Integer, Pet>) ois.readObject();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void addPet(String name,String type,int age,String breed, String color, double weight, int ownerId) {
        int petId = nextId++;
        Pet pet = new Pet(petId, name, type, age, breed, color, weight, ownerId);
        pets.put(petId, pet);
        //Writing
        try (FileOutputStream fos = new FileOutputStream(PET_FILE); BufferedOutputStream bos = new BufferedOutputStream(fos); ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeInt(nextId);
            oos.writeObject(pets);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Pet updatePet(Pet pet) {
        if (!pets.containsKey(pet.getPetId())) {
            throw new DataNotFound("Pet not found");
        }
        pets.put(pet.getPetId(), pet);
        //Writing
        try (FileOutputStream fos = new FileOutputStream(PET_FILE); BufferedOutputStream bos = new BufferedOutputStream(fos); ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeInt(nextId);
            oos.writeObject(pets);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return pet;
    }

    @Override
    public Pet findPetFromId(int petId) {
        if (!pets.containsKey(petId)) {
            throw new DataNotFound("Pet not found");
        }
        return pets.get(petId);
    }

    @Override
    public Stream<Pet> getAllPets() {
        return pets.values().stream();
    }
}
