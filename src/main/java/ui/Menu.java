package ui;

import java.io.*;
import java.util.Scanner;

import domain.*;
import repository.*;
import services.ClinicServices;


public class Menu {
    private final ClinicServices clinicServices;
    Console console = System.console();
    Scanner scanner = new Scanner(System.in);

    public Menu(OwnerRepository ownerRepository, PetRepository petRepository, AppointmentRepository appointmentRepository, MedHisRepository medHisRepository, VetRepository vetRepository) {
        clinicServices = new ClinicServices(ownerRepository, petRepository, appointmentRepository, medHisRepository, vetRepository);
    }

    public void start() {
        while (true) {
            mainMenu();
            System.out.println("Enter your choice: ");

            var value = console.readLine();
            switch (value) {
                case "1":
                    loginAdmin();
                    break;
                case "2":
                    loginOwner(); //login
                    break;
                case "3":
                    register();
                    break;
                case "4":
                    loginVet();
                    break;
                case "5":
                    System.out.println("Exit!");
                    return;
                default:
                    System.out.println("Please Enter a Number");
                    break;
            }
        }
    }

    public void mainMenu() {
        System.out.println("Welcome to Main Menu");
        System.out.println("1: Admin Menu");
        System.out.println("2: Owner's Menu");
        System.out.println("3: Register Menu");
        System.out.println("4: Vet Menu");
        System.out.println("5: Exit");
    }

    //register
    public void register() {
        try {
            System.out.println("Register Menu");
            System.out.println("Enter your ID Card: ");
            int idCard = scanner.nextInt();
            System.out.println("Enter your Password: ");
            String password = console.readLine();
            System.out.println("Enter your Name: ");
            String name = console.readLine();
            System.out.println("Enter your Address: ");
            String address = console.readLine();
            System.out.println("Enter your Phone Number: ");
            String phone = console.readLine();
            clinicServices.createOwner(idCard, password, name, address, phone);
            System.out.println("Register Success!");
        } catch (Exception e) {
            System.out.println("Register Failed!");
            return;
        }
    }

    //login
    public void loginOwner() {
        System.out.println("Enter your ID Card: ");
        int idCard = scanner.nextInt();

        System.out.println("Enter your Password: ");
        char[] password = console.readPassword();
        if (clinicServices.checkPassword(idCard, new String(password))) {
            System.out.println("Login Success!");
            ownerMenu(idCard);
        }
    }

    public void loginVet() {
        System.out.println("Enter your ID Card: ");
        int idCard = scanner.nextInt();

        System.out.println("Enter your Password: ");
        char[] password = console.readPassword();
        if (clinicServices.checkPasswordVet(idCard, new String(password))) {
            vetMenu(idCard);
        }
    }

    public void loginAdmin() {
        System.out.println("Enter Password: ");
        String password = new String(console.readPassword());

        if (password.equals("password")) {
            adminMenu();
        } else {
            System.out.println("Wrong Password!");
        }
    }


    //menu
    public void adminMenu() {
        while (true) {
            System.out.println("Admin Menu!");
            System.out.println("1: Create Vet");
            System.out.println("2: Remove Vet");
            System.out.println("3: View all Appointments");
            System.out.println("4: View all Owners");
            System.out.println("5: View all Pets");
            System.out.println("6: Check Vet");
            System.out.println("7: Medical History");
            System.out.println("8: Exit");

            var value = console.readLine();
            switch (value) {
                case "1":
                    createVetMenu();
                    break;
                case "2":
                    removeVetMenu();
                    break;
                case "3":
                    getAllAppointments();
                    break;
                case "4":
                    var owner = listAllOwner();
                    if (owner != null) {
                        System.out.println("Owner Description : ");
                        System.out.println("Name: " + owner.getName());
                        System.out.println("ID Card: " + owner.getIdCard());
                        System.out.println("Address: " + owner.getAddress());
                        System.out.println("Phone: " + owner.getPhone());
                    }
                    break;
                case "5":
                    var owner2 = listAllOwner();
                    if (owner2 != null) {
                        var pet = listPetbyOwner(owner2.getIdCard());
                        if (pet != null) {
                            System.out.println("Pet Description : ");
                            System.out.println("Name: " + pet.getName());
                            System.out.println("Type: " + pet.getType());
                            System.out.println("Age: " + pet.getAge());
                            System.out.println("Breed: " + pet.getBreed());
                            System.out.println("Color: " + pet.getColor());
                            System.out.println("Weight: " + pet.getWeight());
                        }
                    }
                    break;
                case "6":
                    var vet = listVet();
                    if (vet != null) {
                        System.out.println("Vet Description : ");
                        System.out.println("Name: " + vet.getName());
                        System.out.println("ID Card: " + vet.getIdCard());
                        System.out.println("Address: " + vet.getAddress());
                        System.out.println("Phone: " + vet.getPhone());
                    }
                    break;
                case "7":
                    adminMedicalHistory();
                    break;
                case "8":
                    System.out.println("Exit!");
                    return;
                default:
                    System.out.println("Please Enter a Number");
                    break;
            }
        }

    }

    public void ownerMenu(int idCard) {
        while (true) {
            System.out.println("Owner Menu!");
            System.out.println("1: Show All Pets"); //list pet + enter pet view
            System.out.println("2: Add Pet Data");
            System.out.println("3: View All Appointments"); //update & cancel?
            System.out.println("4: Medical History");
            System.out.println("5: Change Password");
            System.out.println("6: Exit");

            var value = console.readLine();
            switch (value) {
                case "1":
                    var pet = listPetbyOwner(idCard);
                    if (pet != null) {
                        petView(pet.getPetId());
                    }
                    break;
                case "2":
                    addPetData(idCard);
                    break;
                case "3":
                    getAllAppointmentsOwner(idCard);
                    break;
                case "4":
                    var history = getAllMedicalHistoryByOwner(idCard);
                    if (history != null) {
                        medicalHistoryView(history);
                    }
                    break;
                case "5":
                    System.out.println("Enter your old Password: ");
                    String oldPassword = console.readLine();
                    System.out.println("Enter your new Password: ");
                    String newPassword = console.readLine();
                    if (clinicServices.changeOwnerPassword(idCard, oldPassword, newPassword)) {
                        System.out.println("Password Changed!");
                    } else {
                        System.out.println("Password Not Changed!");
                    }
                    break;
                case "6":
                    System.out.println("Exit!");
                    return;
                default:
                    System.out.println("Please Enter a Number");
                    break;
            }
        }

    }

    public Owner listAllOwner() {
        int index = 1;
        for (Owner o : (Iterable<Owner>) clinicServices.getAllOwners()::iterator) {
            System.out.println(index + ". " + o.getName() + " (" + o.getIdCard() + ")");
            index++;
        }
        System.out.println("Type 0 for exit");
        System.out.println("Enter number to select owner : ");
        int value = scanner.nextInt();
        if (value == 0) {
            return null;
        } else if (value <= index) {
            return clinicServices.getAllOwners().skip(value - 1).findFirst().get();
        } else {
            System.out.println("There is no owner with that number!");
            return null;
        }
    }

    public void vetMenu(int vetId) {
        while (true) {
            System.out.println("Vet Menu!");
            System.out.println("1: Create Appointment");
            System.out.println("2: View Appointments");
            System.out.println("3: Medical History");
            System.out.println("4: Exit");

            var value = console.readLine();
            switch (value) {
                case "1":
                    createAppointmentMenuVet(vetId);
                    break;
                case "2":
                    getAllAppointmentsVet(vetId);
                    break;
                case "3":
                    var history = getAllMedicalHistoryByVet(vetId);
                    if (history != null) {
                        medicalHistoryView(history);
                    }
                    break;
                case "4":
                    System.out.println("Exit!");
                    return;
                default:
                    System.out.println("Please Enter a Number");
            }
        }

    }

    public void petView(int petId) {
        while (true) {
            System.out.println("Pet Menu    !");
            System.out.println("1: Check Status");
            System.out.println("2: View Appointments");
            System.out.println("3: Create Appointment");
            System.out.println("4: Medical History");
            System.out.println("5: Exit");

            var value = console.readLine();
            switch (value) {
                case "1":
                    Pet pet = clinicServices.getPetById(petId);
                    System.out.println("Pet Status: ");
                    System.out.println("Name: " + pet.getName());
                    System.out.println("Type: " + pet.getType());
                    System.out.println("Age: " + pet.getAge());
                    System.out.println("Breed: " + pet.getBreed());
                    System.out.println("Color: " + pet.getColor());
                    System.out.println("Weight: " + pet.getWeight());
                    break;
                case "2":
                    getAllAppointmentsPet(petId);
                    break;
                case "3":
                    createAppointmentMenu(petId);
                    break;
                case "4":
                    var history = getAllHistoryPet(petId);
                    if (history != null) {
                        medicalHistoryView(history);
                    }
                    break;
                case "5":
                    System.out.println("Exit!");
                    return;
                default:
                    System.out.println("Please Enter a Number");
            }
        }
    }

    public Pet listPetbyOwner(int idCard) {
        int index = 1;
        for (Pet p : (Iterable<Pet>) clinicServices.getAllPetByOwner(idCard)::iterator) {
            System.out.println(index + ". " + p.getName() + " (" + p.getPetId() + ")");
            index++;
        }
        System.out.println("Type 0 for exit");
        System.out.println("Enter number to select pet : ");
        int value = scanner.nextInt();
        if (value == 0) {
            return null;
        } else if (value <= index) {
            return clinicServices.getAllPetByOwner(idCard).skip(value - 1).findFirst().get();
        } else {
            System.out.println("There is no pet with that number!");
            return null;
        }
    }

    //medical history
    public void adminMedicalHistory() {
        System.out.println("Medical History!");
        System.out.println("1: View Medical History From Owner");
        System.out.println("2: View Medical History From Pet");
        System.out.println("3: View Medical History From Vet");
        System.out.println("4: Exit");

        var value = console.readLine();
        switch (value) {
            case "1":
                var owner = listAllOwner();
                getAllAppointmentsOwner(owner.getIdCard());
                break;
            case "2":
                var pet = listPetbyOwner(listAllOwner().getIdCard());
                getAllHistoryPet(pet.getPetId());
                break;
            case "3":
                var vet = listVet();
                getAllMedicalHistoryByVet(vet.getIdCard());
                break;
            case "4":
                System.out.println("Exit!");
                return;
            default:
                System.out.println("Please Enter a Number");
        }
    }

    public void medicalHistoryView(MedicalHistory medicalHistory) {
        while (true) {
            System.out.println("Medical History Menu!");
            System.out.println("1: Medical History's Detail");
            System.out.println("2: Exit");

            var value = console.readLine();
            switch (value) {
                case "1":
                    System.out.println("Medical History Detail: ");
                    System.out.println("Date: " + medicalHistory.getDate());
                    System.out.println("Vet ID: " + medicalHistory.getVeterinarianId());
                    System.out.println("Pet ID: " + medicalHistory.getPetId());
                    System.out.println("Description: " + medicalHistory.getDescription());
                    break;
                case "2":
                    System.out.println("Exit!");
                    return;
                default:
                    System.out.println("Please Enter a Number");
            }
        }
    }

    public MedicalHistory getAllHistoryPet(int petId) {
        int index = 1;
        for (MedicalHistory m : (Iterable<MedicalHistory>) clinicServices.getAllMedicalHistoryByPet(petId)::iterator) {
            System.out.println(index + ". " + m.getDate() + " (" + m.getMedicalHistoryId() + ")");
            index++;
        }
        System.out.println("Type 0 for exit");
        System.out.println("Enter number to select medical history : ");
        int value = scanner.nextInt();
        if (value == 0) {
            return null;
        } else if (value <= index) {
            return clinicServices.getAllMedicalHistoryByPet(petId).skip(value - 1).findFirst().get();
        } else {
            System.out.println("There is no medical history with that number!");
            return null;
        }
    }

    public MedicalHistory getAllMedicalHistoryByOwner(int ownerId) {
        int index = 1;
        for (MedicalHistory m : (Iterable<MedicalHistory>) clinicServices.getAllMedicalHistoryByOwner(ownerId)::iterator) {
            System.out.println(index + ". " + m.getDate() + " (" + m.getMedicalHistoryId() + ")");
            index++;
        }
        System.out.println("Type 0 for exit");
        System.out.println("Enter number to select medical history : ");
        int value = scanner.nextInt();
        if (value == 0) {
            return null;
        } else if (value <= index) {
            return clinicServices.getAllMedicalHistoryByOwner(ownerId).skip(value - 1).findFirst().get();
        } else {
            System.out.println("There is no medical history with that number!");
            return null;
        }
    }

    public MedicalHistory getAllMedicalHistoryByVet(int vetId) {
        int index = 1;
        for (MedicalHistory m : (Iterable<MedicalHistory>) clinicServices.getAllMedicalHistoryByVet(vetId)::iterator) {
            System.out.println(index + ". " + m.getDate() + " (" + m.getMedicalHistoryId() + ")");
            index++;
        }
        System.out.println("Type 0 for exit");
        System.out.println("Enter number to select medical history : ");
        int value = scanner.nextInt();
        if (value == 0) {
            return null;
        } else if (value <= index) {
            return clinicServices.getAllMedicalHistoryByVet(vetId).skip(value - 1).findFirst().get();
        } else {
            System.out.println("There is no medical history with that number!");
            return null;
        }
    }

    //Appointment
    public void appointmentView(Appointment appointment) {
        while (true) {
            System.out.println("Appointment Menu!");
            System.out.println("1: Appointment's Detail");
            System.out.println("2: Cancel Appointment");
            System.out.println("3: Exit");

            var value = console.readLine();
            switch (value) {
                case "1":
                    System.out.println("Appointment Detail: ");
                    System.out.println("Date: " + appointment.getDate());
                    System.out.println("Vet ID: " + appointment.getVeterinarianId());
                    System.out.println("Pet ID: " + appointment.getPetId());
                    System.out.println("Description: " + appointment.getDescription());
                    break;
                case "2":
                    clinicServices.deleteAppointment(appointment.getAppointmentId());
                    System.out.println("Appointment Deleted!");
                    return;
                case "3":
                    System.out.println("Exit!");
                    return;
                default:
                    System.out.println("Please Enter a Number");
            }
        }
    }

    public void getAllAppointmentsOwner(int ownerId) {
        int index = 1;
        for (Appointment a : (Iterable<Appointment>) clinicServices.getAllAppointmentByOwner(ownerId)::iterator) {
            System.out.println(index + ". " + clinicServices.getPetById(a.getPetId()).getName() + " " + a.getDate() + " (" + a.getAppointmentId() + ")");
            index++;
        }
        System.out.println("Type 0 for exit");
        System.out.println("Enter number to select appointment : ");
        int value = scanner.nextInt();
        if (value == 0) {
            return;
        } else if (value <= index) {
            appointmentView(clinicServices.getAllAppointmentByOwner(ownerId).skip(value - 1).findFirst().get());
        } else {
            System.out.println("There is no appointment with that number!");
            return;
        }
    }

    public void getAllAppointmentsPet(int petId) {
        int index = 1;
        for (Appointment a : (Iterable<Appointment>) clinicServices.getAllAppointmentByPet(petId)::iterator) {
            System.out.println(index + ". " + a.getDate() + " (" + a.getAppointmentId() + ")");
            index++;
        }
        System.out.println("Type 0 for exit");
        System.out.println("Enter number to select appointment : ");
        int value = scanner.nextInt();
        if (value == 0) {
            return;
        } else if (value <= index) {
            appointmentView(clinicServices.getAllAppointmentByPet(petId).skip(value - 1).findFirst().get());
        } else {
            System.out.println("There is no appointment with that number!");
            return;
        }
    }

    public void getAllAppointmentsVet(int vetId) {
        int index = 1;
        for (Appointment a : (Iterable<Appointment>) clinicServices.getAllAppointmentByVet(vetId)::iterator) {
            System.out.println(index + ". " + a.getDate() + " (" + a.getAppointmentId() + ")");
            index++;
        }
        System.out.println("Type 0 for exit");
        System.out.println("Enter number to select appointment : ");
        int value = scanner.nextInt();
        if (value == 0) {
            return;
        } else if (value <= index) {
            appointmentView(clinicServices.getAllAppointmentByVet(vetId).skip(value - 1).findFirst().get());
        } else {
            System.out.println("There is no appointment with that number!");
            return;
        }
    }

    public void getAllAppointments() {
        int index = 1;
        for (Appointment a : (Iterable<Appointment>) clinicServices.getAllAppointments()::iterator) {
            System.out.println(index + ". " + a.getDate() + " (" + a.getAppointmentId() + ")");
            index++;
        }
        System.out.println("Type 0 for exit");
        System.out.println("Enter number to select appointment : ");
        int value = scanner.nextInt();
        if (value == 0) {
            return;
        } else if (value <= index) {
            appointmentView(clinicServices.getAllAppointments().skip(value - 1).findFirst().get());
        } else {
            System.out.println("There is no appointment with that number!");
            return;
        }
    }

    public void createAppointmentMenu(int petId) {
        try {
            var vetId = listVet().getIdCard();
            System.out.println("Enter date: ");
            System.out.println("Format: dd/mm/yyyy hh:mm");
            System.out.println("Example: 01/01/2021 10:00");
            String date = console.readLine();
            System.out.println("Enter Description: ");
            String desc = console.readLine();
            clinicServices.createAppointment(petId, vetId, date, desc);
            System.out.println("Appointment Created!");
        }
        catch (Exception e) {
            System.out.println("Appointment Failed!");
            return;
        }
    }

    public void createAppointmentMenuVet(int vetId) {
        try {
            var owner = listAllOwner();
            var pet = listPetbyOwner(owner.getIdCard());
            var petId = pet.getPetId();
            System.out.println("Enter date: ");
            System.out.println("Format: dd/mm/yyyy hh:mm");
            System.out.println("Example: 01/01/2021 10:00");
            String date = console.readLine();
            System.out.println("Enter Description: ");
            String desc = console.readLine();
            clinicServices.createAppointment(petId, vetId, date, desc);
            System.out.println("Appointment Created!");
        }
        catch (Exception e) {
            System.out.println("Appointment Failed!");
            return;
        }
    }

    //Pet

    public void addPetData(int ownerId) {
        try {
            System.out.println("Enter your Pet Name: ");
            String name = console.readLine();
            System.out.println("Enter your Pet Type: ");
            String type = console.readLine();
            System.out.println("Enter your Pet Age: ");
            int age = scanner.nextInt();
            System.out.println("Enter your Pet Breed: ");
            String breed = console.readLine();
            System.out.println("Enter your Pet Color: ");
            String color = console.readLine();
            System.out.println("Enter your Pet Weight: ");
            double weight = scanner.nextDouble();
            clinicServices.addPet(name, type, age, breed, color, weight, ownerId);
            System.out.println(name + "'s Added!");
        } catch (Exception e) {
            System.out.println("Pet Data Failed!");
            return;
        }
    }

    //Veterinarian
    public void createVetMenu() {
        try {
            System.out.println("Enter ID Card: ");
            int idCard = scanner.nextInt();
            System.out.println("Enter Password: ");
            String password = console.readLine();
            System.out.println("Enter Name: ");
            String name = console.readLine();
            System.out.println("Enter Address: ");
            String address = console.readLine();
            System.out.println("Enter Phone Number: ");
            String phone = console.readLine();
            clinicServices.createVet(idCard, password, name, address, phone);
            System.out.println(name + " Added!");
        } catch (Exception e) {
            System.out.println("Vet Data Failed!");
            return;
        }
    }

    public void removeVetMenu() {
        var vet = listVet();
        if (vet != null) {
            clinicServices.removeVet(vet.getIdCard());
            System.out.println(vet.getName() + " Removed!");
        }
    }

    public Veterinarian listVet() {
        int index = 1;
        for (Veterinarian p : (Iterable<Veterinarian>) clinicServices.getAllVets()::iterator) {
            System.out.println(index + ". " + p.getName() + " (" + p.getPhone() + ")");
            index++;
        }
        System.out.println("Type 0 for exit");
        System.out.println("Enter number to select Veterinarian : ");
        int value = scanner.nextInt();
        if (value == 0) {
            return null;
        } else if (value <= index) {
            return clinicServices.getAllVets().skip(value - 1).findFirst().get();
        } else {
            System.out.println("There is no veterinarian with that number!");
            return null;
        }
    }

    /*Pet View =, Pet list, Appointment List(Cancel Appointments), Login(Owner =,Vet =), Regeister Owner =, Add pet Data, Create Appointments(Owner, Vet)
     * update appointment, Create vet =, Remove Vet =,*/

    //pet list, appointment list
}