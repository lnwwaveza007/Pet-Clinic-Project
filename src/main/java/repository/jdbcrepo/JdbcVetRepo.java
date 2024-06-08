package repository.jdbcrepo;

import domain.Owner;
import domain.Veterinarian;
import domain.exceptions.DataNotFound;
import domain.exceptions.DuplicateDataException;
import repository.VetRepository;

import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class JdbcVetRepo implements VetRepository {
    private File configFile = new File("sqlUrl.txt");
    private Connection con;

    private final Map<Integer, Veterinarian> vets = new HashMap<>();

    public JdbcVetRepo() throws IOException, SQLException, ClassNotFoundException {
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
        ResultSet rs = stmt.executeQuery("SELECT * FROM veterinarian");
        while (rs.next()) {
            vets.put(rs.getInt(1), new Veterinarian(rs.getInt(1), rs.getString(3), rs.getString(2), rs.getString(4), rs.getString(5)));
        }
    }

    @Override
    public boolean addVet(int idCard, String password, String name, String address, String phone) {
        if (vets.containsKey(idCard)) {
            throw new DuplicateDataException("Vet already exists");
        }
        Veterinarian vet = new Veterinarian(idCard, password, name, address, phone);
        vets.put(idCard, vet);
        //SQL Insert
        try {
            Statement stmt = con.createStatement();
            String sql = "INSERT INTO veterinarian (idCard, password, name, address, phone) VALUES (" + idCard + ",'" + password + "','" + name + "','" + address + "','" + phone + "')";
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e);
        }
        return true;
    }

    @Override
    public boolean updateVet(Veterinarian vet) {
        if (!vets.containsKey(vet.getIdCard())) {
            throw new DataNotFound("Vet does not exist");
        }
        vets.put(vet.getIdCard(), vet);
        //SQL Update
        try {
            Statement stmt = con.createStatement();
            String sql = "UPDATE veterinarian SET password = '" + vet.getPassword() + "', name = '" + vet.getName() + "', address = '" + vet.getAddress() + "', phone = '" + vet.getPhone() + "' WHERE idCard = " + vet.getIdCard();
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e);
        }
        return true;
    }

    @Override
    public boolean deleteVet(int idCard) {
        if (!vets.containsKey(idCard)) {
            throw new DataNotFound("Vet does not exist");
        }
        vets.remove(idCard);
        //SQL Update
        try {
            Statement stmt = con.createStatement();
            String sql = "DELETE FROM veterinarian WHERE idCard = " + idCard;
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e);
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
