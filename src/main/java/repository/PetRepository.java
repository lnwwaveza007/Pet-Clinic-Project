package repository;

import domain.Owner;
import domain.Pet;

import java.util.stream.Stream;

public interface PetRepository {
    public void addPet(String name,String type,int age,String breed, String color, double weight, int ownerId);
    public Pet updatePet(Pet pet);
    public Pet findPetFromId(int petId);
    public Stream<Pet> getAllPets();
}
