package domain;

import java.io.Serializable;

public class Pet implements Serializable {
    private final int petId;
    private String name;
    private String type;
    private int age;
    private String breed;
    private String color;
    private double weight;
    private int ownerId;

    public Pet(int petId, String name, String type, int age, String breed, String color, double weight, int ownerId) {
        this.petId = petId;
        this.name = name;
        this.type = type;
        this.age = age;
        this.breed = breed;
        this.color = color;
        this.weight = weight;
        this.ownerId = ownerId;
    }

    public int getPetId() {
        return petId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
}
