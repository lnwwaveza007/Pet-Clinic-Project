package repository.memoryrepo;

import domain.Pet;
import domain.exceptions.DataNotFound;
import repository.PetRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class MemoryPetRepo implements PetRepository {
    private final Map<Integer, Pet> pets = new HashMap<>();
    private int nextId = 1;

    @Override
    public void addPet(String name,String type,int age,String breed, String color, double weight, int ownerId) {
        int petId = nextId++;
        Pet pet = new Pet(petId, name, type, age, breed, color, weight, ownerId);
        pets.put(petId, pet);
    }

    @Override
    public Pet updatePet(Pet pet) {
        if (!pets.containsKey(pet.getPetId())) {
            throw new DataNotFound("Pet not found");
        }
        pets.put(pet.getPetId(), pet);
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
