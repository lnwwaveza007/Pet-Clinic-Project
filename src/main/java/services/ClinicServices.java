package services;

import domain.Appointment;
import domain.MedicalHistory;
import domain.Pet;
import repository.*;

import java.util.stream.Stream;

public class ClinicServices {
    private final OwnerRepository ownerRepository;
    private final PetRepository petRepository;
    private final AppointmentRepository appointmentRepository;
    private final MedHisRepository medHisRepository;
    private final VetRepository vetRepository;

    public ClinicServices(OwnerRepository ownerRepository, PetRepository petRepository, AppointmentRepository appointmentRepository, MedHisRepository medHisRepository, VetRepository vetRepository) {
        this.ownerRepository = ownerRepository;
        this.petRepository = petRepository;
        this.appointmentRepository = appointmentRepository;
        this.medHisRepository = medHisRepository;
        this.vetRepository = vetRepository;
    }

    //Owner Section
    public boolean createOwner(int idCard, String password, String name, String address, String phone) {
        return ownerRepository.addOwner(idCard, password, name, address, phone);
    }

    public boolean addPet(String name,String type,int age,String breed, String color, double weight, int ownerId) {
        petRepository.addPet(name, type, age, breed, color, weight, ownerId);
        return true;
    }

    public boolean changeOwnerPassword(int idCard, String oldPassword, String newPassword) {
        var owner = ownerRepository.findOwner(idCard);
        if (owner == null) return false;
        if (owner.getPassword().equals(oldPassword)) {
            owner.setPassword(newPassword);
            ownerRepository.updateOwner(owner);
            return true;
        } else {
            return false;
        }
    }

    public Stream<Pet> getAllPetByOwner(int ownerId) {
        return petRepository.getAllPets().filter(pet -> pet.getOwnerId() == ownerId);
    }

    public Pet getPetById(int petId) {
        return petRepository.findPetFromId(petId);
    }

    //Appointment Section
    public Stream<Appointment> getAllAppointmentByOwner(int ownerId) {
        return appointmentRepository.getAppointmentsFromOwner(ownerId);
    }

    public Stream<Appointment> getAllAppointmentByPet(int petId) {
        return appointmentRepository.getAppointmentsFromPet(petId);
    }

    public Stream<Appointment> getAllAppointmentByVet(int vetId) {
        return appointmentRepository.getAppointmentsFromVet(vetId);
    }
    public Stream<Appointment> getAllAppointments() {
        return appointmentRepository.getAllAppointments();
    }
    public Stream<Appointment> getAllAppointmentsInDate(String date) {
        return appointmentRepository.getAllAppointments().filter(appointment -> appointment.getDate().contains(date));
    }


    public boolean createAppointment(int petId, int vetId, String date, String desc) {
        appointmentRepository.createAppointment(date, desc, petId, vetId);
        return true;
    }

    public boolean updateAppointment(Appointment appointment) {
        return appointmentRepository.updateAppointment(appointment);
    }

    public boolean deleteAppointment(int appointmentId) {
        return appointmentRepository.deleteAppointment(appointmentId);
    }

    //Vet Section
    public boolean createVet(int idCard, String password, String name, String address, String phone) {
        return vetRepository.addVet(idCard, password, name, address, phone);
    }

    public boolean removeVet(int idCard) {
        return vetRepository.deleteVet(idCard);
    }

    //Medical History Section
    public boolean addMedicalHistory(int petId,int vetId, String date, String description) {
        medHisRepository.addMedHis(date, description, petId, vetId);
        return true;
    }

    public Stream<MedicalHistory> getAllMedicalHistoryByPet(int petId) {
        return medHisRepository.getMedHisFromPet(petId);
    }

    public Stream<MedicalHistory> getAllMedicalHistoryByVet(int vetId) {
        return medHisRepository.getMedHisFromVet(vetId);
    }

    public Stream<MedicalHistory> getAllMedicalHistory() {
        return medHisRepository.getAllMedHis();
    }

    public Stream<MedicalHistory> getAllMedicalHistoryByOwner(int ownerId) {
        return medHisRepository.getMedHisFromOwner(ownerId);
    }

}
