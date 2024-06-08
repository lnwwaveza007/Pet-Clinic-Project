package repository.jdbcrepo;

import domain.Owner;
import domain.exceptions.DataNotFound;
import domain.exceptions.DuplicateDataException;
import repository.OwnerRepository;

import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class JdbcOwnerRepo implements OwnerRepository {
    private File configFile = new File("sqlUrl.txt");
    private Connection con;

    private final Map<Integer, Owner> owners = new HashMap<>();

    public JdbcOwnerRepo() throws IOException, SQLException, ClassNotFoundException {
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
        ResultSet rs = stmt.executeQuery("SELECT * FROM owner");
        while (rs.next()) {
            owners.put(rs.getInt(1), new Owner(rs.getInt(1), rs.getString(3), rs.getString(2), rs.getString(4), rs.getString(5)));
        }
    }

    @Override
    public boolean addOwner(int idCard, String password, String name, String address, String phone) {
        if (owners.containsKey(idCard)) {
            throw new DuplicateDataException("Owner already exists");
        }
        Owner owner = new Owner(idCard, password, name, address, phone);
        owners.put(idCard, owner);
        //SQL Insert
        try {
            Statement stmt = con.createStatement();
            String sql = "INSERT INTO owner (idCard, password, name, address, phone) VALUES (" + idCard + ",'" + password + "','" + name + "','" + address + "','" + phone + "')";
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e);
        }
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
