package repository.memoryrepo;

import domain.Pet;
import repository.PetRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class MemoryPetRepo implements PetRepository {
    private final Map<Integer, Pet> pets = new HashMap<>();
    private int nextId;

    @Override
    public void addPet(String name,String type,int age,String breed, String color, double weight, int ownerId) {
        int petId = nextId++;
        Pet pet = new Pet(petId, name, type, age, breed, color, weight, ownerId);
        pets.put(petId, pet);
    }

    @Override
    public Pet updatePet(Pet pet) {
        pets.put(pet.getPetId(), pet);
        return pet;
    }

    @Override
    public Pet findPetFromId(int petId) {
        return pets.get(petId); //throw exception here?
    }

    @Override
    public Stream<Pet> getAllPets() {
        return pets.values().stream();
    }
}
