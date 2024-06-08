package repository;

import domain.Appointment;

import java.util.stream.Stream;

public interface AppointmentRepository {
    public boolean createAppointment(String date, String description, int petId, int vetId);
    public boolean updateAppointment(Appointment appointment);
    public boolean deleteAppointment(int appointmentId);
    public Appointment findAppointment(int appointmentId);
    public Stream<Appointment> getAllAppointments();
    public Stream<Appointment> getAppointmentsFromPet(int petId);
    public Stream<Appointment> getAppointmentsFromVet(int vetId);
}
