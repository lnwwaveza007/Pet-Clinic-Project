package repository.jdbcrepo;

import domain.MedicalHistory;
import domain.Pet;
import domain.exceptions.DataNotFound;
import repository.MedHisRepository;

import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class JdbcMedHisRepo implements MedHisRepository {
    private File configFile = new File("sqlUrl.txt");
    private Connection con;

    private final Map<Integer, MedicalHistory> medHistories = new HashMap<>();
    private int nextId = 1;

    public JdbcMedHisRepo() throws IOException, SQLException, ClassNotFoundException {
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
        ResultSet rs = stmt.executeQuery("SELECT * FROM medicalhistory");
        while (rs.next()) {
            medHistories.put(rs.getInt(1), new MedicalHistory(rs.getInt(1), rs.getString(3), rs.getString(2), rs.getInt(5), rs.getInt(4)));
            nextId = rs.getInt(1) + 1;
        }
    }

    @Override
    public boolean addMedHis(String date, String description, int petId, int vetId) {
        if (date == null || description == null) {
            throw new IllegalArgumentException("Date and description cannot be null");
        }
        int medicalHistoryId = nextId++;
        MedicalHistory medHis = new MedicalHistory(medicalHistoryId, date, description, petId, vetId);
        medHistories.put(nextId, medHis);
        //SQL Insert
        try {
            Statement stmt = con.createStatement();
            String sql = "INSERT INTO medicalhistory (medHisId, date, description, petId, vetId) VALUES (" + medicalHistoryId + ",'" + date + "','" + description + "'," + petId + "," + vetId + ")";
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e);
        }
        return true;
    }

    @Override
    public MedicalHistory getMedHis(int medHisId) {
        if (!medHistories.containsKey(medHisId)) {
            throw new DataNotFound("Medical History not found");
        }
        return medHistories.get(medHisId);
    }

    @Override
    public Stream<MedicalHistory> getAllMedHis() {
        return medHistories.values().stream();
    }

    @Override
    public Stream<MedicalHistory> getMedHisFromPet(int petId) {
        return medHistories.values().stream()
                .filter(medHis -> medHis.getPetId() == petId);
    }

    @Override
    public Stream<MedicalHistory> getMedHisFromVet(int vetId) {
        return medHistories.values().stream()   
                .filter(medHis -> medHis.getVeterinarianId() == vetId);
    }

    @Override
    public Stream<MedicalHistory> getMedHisFromOwner(int ownerId) {
        return medHistories.values().stream()
                .filter(medHis -> medHis.getPetId() == ownerId);
    }
}
