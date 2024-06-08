package repository.jdbcrepo;

import domain.Owner;
import domain.Pet;
import domain.exceptions.DataNotFound;
import repository.PetRepository;

import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class JdbcPetRepo implements PetRepository {
    private File configFile = new File("sqlUrl.txt");
    private Connection con;

    private final Map<Integer, Pet> pets = new HashMap<>();
    private int nextId = 1;

    public JdbcPetRepo() throws IOException, SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        if (configFile.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(configFile));
            var sqlUrl = reader.readLine();
            var username = reader.readLine();
            var password = reader.readLine();
            reader.close();
            con = DriverManager.getConnection(sqlUrl, username, password);
        } else {
            BufferedWriter writer = new BufferedWriter(new FileWriter(configFile));
            writer.write("jdbc:mysql://localhost:3306/petclinic");
            writer.newLine();
            writer.write("root");
            writer.newLine();
            writer.write("pet@kmutt123");
            writer.close();
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/petclinic", "root", "pet@kmutt123");
        }
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM pet");
        while (rs.next()) {
            pets.put(rs.getInt(1), new Pet(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(7), rs.getString(4), rs.getString(5), rs.getDouble(8), rs.getInt(6)));
            nextId = rs.getInt(1) + 1;
        }
    }

    @Override
    public void addPet(String name,String type,int age,String breed, String color, double weight, int ownerId) {
        int petId = nextId++;
        Pet pet = new Pet(petId, name, type, age, breed, color, weight, ownerId);
        pets.put(petId, pet);
        //SQL Insert
        try {
            Statement stmt = con.createStatement();
            String sql = "INSERT INTO pet (petId, name, type, age, breed, color, weight, ownerId) VALUES (" + petId + ",'" + name + "','" + type + "'," + age + ",'" + breed + "','" + color + "'," + weight + "," + ownerId + ")";
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public Pet updatePet(Pet pet) {
        if (!pets.containsKey(pet.getPetId())) {
            throw new DataNotFound("Pet not found");
        }
        pets.put(pet.getPetId(), pet);
        //SQL Update
        try {
            Statement stmt = con.createStatement();
            String sql = "UPDATE pet SET name = '" + pet.getName() + "', type = '" + pet.getType() + "', age = " + pet.getAge() + ", breed = '" + pet.getBreed() + "', color = '" + pet.getColor() + "', weight = " + pet.getWeight() + ", ownerId = " + pet.getOwnerId() + " WHERE petId = " + pet.getPetId();
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e);
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
