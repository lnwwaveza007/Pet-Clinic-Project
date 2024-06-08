package repository.filerepo;

import domain.Appointment;
import domain.Pet;
import repository.AppointmentRepository;
import repository.PetRepository;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class FileAppointmentRepo implements AppointmentRepository {
    private static final String APPOINTMENT_FILE = "appointments.dat";
    private Map<Integer, Appointment> appointments = new HashMap<>();
    private int nextId = 1;
    private PetRepository petRepository;

    public FileAppointmentRepo() {
        if (new File(APPOINTMENT_FILE).exists()) {
            // Load data from file
            try (FileInputStream fis = new FileInputStream(APPOINTMENT_FILE); BufferedInputStream bis = new BufferedInputStream(fis); ObjectInputStream ois = new ObjectInputStream(bis)) {
                nextId = (int) ois.readInt();
                appointments = (Map<Integer, Appointment>) ois.readObject();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean createAppointment(String date, String description, int petId, int vetId) {
        int appointmentId = nextId++;
        Appointment newAppointment = new Appointment(appointmentId, date, description, petId, vetId);
        appointments.put(appointmentId, newAppointment);
        try (FileOutputStream fos = new FileOutputStream(APPOINTMENT_FILE); BufferedOutputStream bos = new BufferedOutputStream(fos); ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeInt(nextId);
            oos.writeObject(appointments);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public boolean updateAppointment(Appointment appointment) {
        appointments.put(appointment.getAppointmentId(), appointment);
        try (FileOutputStream fos = new FileOutputStream(APPOINTMENT_FILE); BufferedOutputStream bos = new BufferedOutputStream(fos); ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeInt(nextId);
            oos.writeObject(appointments);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public boolean deleteAppointment(int appointmentId) {
        try (FileOutputStream fos = new FileOutputStream(APPOINTMENT_FILE); BufferedOutputStream bos = new BufferedOutputStream(fos); ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeInt(nextId);
            oos.writeObject(appointments);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return appointments.remove(appointmentId) != null;
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

    @Override
    public Stream<Appointment> getAppointmentsFromOwner(int ownerId) {
        return appointments.values().stream()
                .filter(appointment -> {
                    Pet pet = petRepository.findPetFromId(appointment.getPetId());
                    return pet != null && pet.getOwnerId() == ownerId;
                });
    }
}
