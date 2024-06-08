package repository.memoryrepo;

import domain.Appointment;
import domain.Pet;
import repository.AppointmentRepository;
import repository.PetRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class MemoryAppointmentRepo implements AppointmentRepository {
    private Map<Integer, Appointment> appointments = new HashMap<>();
    private int nextId = 1;
    private PetRepository petRepository;

    @Override
    public boolean createAppointment(String date, String description, int petId, int vetId) {
        int appointmentId = nextId++;
        Appointment newAppointment = new Appointment(appointmentId, date, description, petId, vetId);
        appointments.put(appointmentId, newAppointment);
        return true;
    }

    @Override
    public boolean updateAppointment(Appointment appointment) {
        appointments.put(appointment.getAppointmentId(), appointment);
        return true;
    }

    @Override
    public boolean deleteAppointment(int appointmentId) {
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
}
