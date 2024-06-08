package services;

import domain.*;
import domain.exceptions.MissingInformation;
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
    public Stream<Owner> getAllOwners() {
        return ownerRepository.getAllOwners();
    }

    public void createOwner(int idCard, String password, String name, String address, String phone) {
        if (idCard < 0 || password.isEmpty() || name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
            throw new MissingInformation("Missing Information");
        }
        ownerRepository.addOwner(idCard, password, name, address, phone);
    }

    public void addPet(String name, String type, int age, String breed, String color, double weight, int ownerId) {
        if (name.isEmpty() || type.isEmpty() || breed.isEmpty() || color.isEmpty() || weight < 0) {
            throw new MissingInformation("Missing Information");
        }
        petRepository.addPet(name, type, age, breed, color, weight, ownerId);
    }

    public Owner getOwner(int idCard) {
        return ownerRepository.findOwner(idCard);
    }

    public boolean checkPassword(int idCard,String password) {
        var owner = ownerRepository.findOwner(idCard);
        if (owner == null) return false;
        return owner.getPassword().equals(password);
    }

    public boolean checkPasswordVet(int idCard,String password) {
        var vet = vetRepository.getVet(idCard);
        if (vet == null) return false;
        return vet.getPassword().equals(password);
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


    public void createAppointment(int petId, int vetId, String date, String desc) {
        if (date.isEmpty() || desc.isEmpty()) {
            throw new MissingInformation("Missing Information");
        }
        appointmentRepository.createAppointment(date, desc, petId, vetId);
    }

    public boolean updateAppointment(Appointment appointment) {
        return appointmentRepository.updateAppointment(appointment);
    }

    public void deleteAppointment(int appointmentId) {
        appointmentRepository.deleteAppointment(appointmentId);
    }

    //Vet Section
    public void createVet(int idCard, String password, String name, String address, String phone) {
        if (idCard < 0 || password.isEmpty() || name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
            throw new MissingInformation("Missing Information");
        }
        vetRepository.addVet(idCard, password, name, address, phone);
    }

    public void removeVet(int idCard) {
        vetRepository.deleteVet(idCard);
    }

    public Stream<Veterinarian> getAllVets() {
        return vetRepository.getAllVets();
    }

    //Medical History Section
    public boolean addMedicalHistory(int petId,int vetId, String date, String description) {
        if (date.isEmpty() || description.isEmpty()) {
            throw new MissingInformation("Missing Information");
        }
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
