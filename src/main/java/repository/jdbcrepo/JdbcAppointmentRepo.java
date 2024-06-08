package repository.jdbcrepo;

import domain.Appointment;
import domain.MedicalHistory;
import repository.AppointmentRepository;
import repository.PetRepository;

import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class JdbcAppointmentRepo implements AppointmentRepository {
    private File configFile = new File("sqlUrl.txt");
    private Connection con;

    private Map<Integer, Appointment> appointments = new HashMap<>();
    private int nextId = 1;

    public JdbcAppointmentRepo() throws IOException, SQLException, ClassNotFoundException {
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
        ResultSet rs = stmt.executeQuery("SELECT * FROM appointment");
        while (rs.next()) {
            appointments.put(rs.getInt(1), new Appointment(rs.getInt(1), rs.getString(3), rs.getString(2), rs.getInt(5), rs.getInt(4)));
            nextId = rs.getInt(1) + 1;
        }
    }

    @Override
    public boolean createAppointment(String date, String description, int petId, int vetId) {
        int appointmentId = nextId++;
        Appointment newAppointment = new Appointment(appointmentId, date, description, petId, vetId);
        appointments.put(appointmentId, newAppointment);
        //SQL Insert
        try {
            Statement stmt = con.createStatement();
            String sql = "INSERT INTO appointment (appointmentId, date, description, petId, veterinarianId) VALUES (" + appointmentId + ",'" + date + "','" + description + "'," + petId + "," + vetId + ")";
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e);
        }
        return true;
    }

    @Override
    public boolean updateAppointment(Appointment appointment) {
        appointments.put(appointment.getAppointmentId(), appointment);
        //SQL Update
        try {
            Statement stmt = con.createStatement();
            String sql = "UPDATE appointment SET date = '" + appointment.getDate() + "', description = '" + appointment.getDescription() + "', petId = " + appointment.getPetId() + ", veterinarianId = " + appointment.getVeterinarianId() + " WHERE appointmentId = " + appointment.getAppointmentId();
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e);
        }
        return true;
    }

    @Override
    public boolean deleteAppointment(int appointmentId) {
        appointments.remove(appointmentId);
        //SQL Delete
        try {
            Statement stmt = con.createStatement();
            String sql = "DELETE FROM appointment WHERE appointmentId = " + appointmentId;
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e);
        }
        return true;
    }

    @Override
    public Appointment findAppointment(int appointmentId) {
        return appointments.get(appointmentId);
    }

    @Override
    public Stream<Appointment> getAllAppointments() {
        return appointments.values().stream();
    }

    @Override
    public Stream<Appointment> getAppointmentsFromPet(int petId) {
        return appointments.values().stream()
                .filter(appointment -> appointment.getPetId() == petId);
    }

    @Override
    public Stream<Appointment> getAppointmentsFromVet(int vetId) {
        return appointments.values().stream()
                .filter(appointment -> appointment.getVeterinarianId() == vetId);
    }
}
