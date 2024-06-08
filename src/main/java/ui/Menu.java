package ui;

import java.io.*;
import repository.*;
import services.ClinicServices;


public class Menu {
    private final ClinicServices clinicServices;
    Console console = System.console();
    public Menu(OwnerRepository ownerRepository, PetRepository petRepository, AppointmentRepository appointmentRepository, MedHisRepository medHisRepository, VetRepository vetRepository) {
        clinicServices = new ClinicServices(ownerRepository, petRepository, appointmentRepository, medHisRepository, vetRepository);
    }

    public void main(String[] args) {
        ClinicServices clinicServices = new ClinicServices(null, null, null, null, null);
        while (true) {
            mainMenu();
            System.out.println("Enter your choice: ");

            var value = console.readLine();
            switch (value) {
                case "1":
                    adminMenu(console);
                    break;
                case "2":
                    loginOwner(console); //login
                    break;
                case "3":
                    register(console);
                    break;
                case "4":
                    loginVet(console);
                    break;
                case "5":
                    System.out.println("Exit!");
                    break;
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
    public void register(Console console) {
        System.out.println("Register Menu");
        System.out.println("Enter your ID Card: ");
        String idCard = console.readLine();
        System.out.println("Enter your Password: ");
        String password = console.readLine();
        System.out.println("Enter your Name: ");
        String name = console.readLine();
        System.out.println("Enter your Address: ");
        String address = console.readLine();
        System.out.println("Enter your Phone Number: ");
        String phone = console.readLine();
        clinicServices.createOwner();
    }

    //login
    public void loginOwner(Console console) {
        System.out.println("Enter your ID Card: ");
        String idCard = console.readLine();

        System.out.println("Enter your Password: ");
        char[] password = console.readPassword();
        if (clinicServices.checkPassword()) {
            ownerMenu(console);
        }
    }

    public void loginVet(Console console) {
        System.out.println("Enter your ID Card: ");
        String idCard = console.readLine();

        System.out.println("Enter your Password: ");
        char[] password = console.readPassword();
        if (clinicServices.checkPassword()) {
            vetMenu(console);
        }
    }

    public void loginAdmin(Console console) {
        System.out.println("Enter Password: ");
        String password = console.readLine();

    }


    //menu
    public void adminMenu(Console console) {
        System.out.println("Admin Menu!");
        System.out.println("1: Create Vet");
        System.out.println("2: Remove Vet");
        System.out.println("3: View all Appointments");
        System.out.println("4: View all Owners");
        System.out.println("5: View all Pets");
        System.out.println("6: Check Vet");
        System.out.println("7: Medical History's");
        System.out.println("8: Exit");

        var value = console.readLine();
        switch (value) {
            case "1":
                createVetMenu(console);
                break;
            case "2":
                clinicServices.removeVet();
                break;
            case "3":
                clinicServices.getAllAppointments();
                break;
            case "4":
                //clinicServices.getAllOwner();
                break;
            case "5":
                clinicServices.getAllPetByOwner();
                break;
            case "6":
                //clinicServices.getAllVet();
                break;
            case "7":
                adminMedicalHistory(console);
                break;
            case "8":
                System.out.println("Exit!");
                return;
            default:
                System.out.println("Please Enter a Number");
                break;
        }

    }

    public void ownerMenu(Console console) {
        System.out.println("Owner Menu!");
        System.out.println("1: Show All Pets"); //list pet + enter pet view
        System.out.println("2: Add Pet Data");
        System.out.println("3: View All Appointments"); //update & cancel?
        System.out.println("4: Medical History's");
        System.out.println("5: Change Password");
        System.out.println("6: Exit");

        var value = console.readLine();
        switch (value) {
            case "1":
                clinicServices.getAllPetByOwner();
                break;
            case "2":
                clinicServices.addPet();
                break;
            case "3":
                clinicServices.getAllAppointmentByOwner();
                break;
            case "4":
                clinicServices.getAllMedicalHistoryByOwner();
                break;
            case "5":
                System.out.println("Exit!");
                return;
            default:
                System.out.println("Please Enter a Number");
                break;
        }

    }

    public void vetMenu(Console console) {
        System.out.println("Vet Menu!");
        System.out.println("1: Create Appointment");
        System.out.println("2: Cancel Appointments");
        System.out.println("3: View Appointments"); // update appointments & cancel
        System.out.println("4: Medical History's");
        System.out.println("5: Exit");

        var value = console.readLine();
        switch (value) {
            case "1":
                clinicServices.createAppointment();
                break;
            case "2":
                clinicServices.deleteAppointment();
                break;
            case "3":
                clinicServices.getAllAppointmentByVet();
                break;
            case "4":
                clinicServices.getAllMedicalHistoryByVet();
                break;
            case "5":
                System.out.println("Exit!");
                return;
            default:
                System.out.println("Please Enter a Number");
        }

    }

    public void petView(Console console) {
        System.out.println("Pet Menu    !");
        System.out.println("1: Check Status");
        System.out.println("2: View Appointments"); // cancel?
        System.out.println("3: Create Appointment");
        System.out.println("4: Cancel Appointments");
        System.out.println("5: Exit");

        var value = console.readLine();
        switch (value) {
            case "1":
                //checkStatus();
                break;
            case "2":
                clinicServices.getAllAppointmentByPet();
                break;
            case "3":
                clinicServices.createAppointment();
                break;
            case "4":
                clinicServices.deleteAppointment();
                return;
            default:
                System.out.println("Please Enter a Number");
        }
    }

    //medical history
    public void adminMedicalHistory(Console console) {
        System.out.println("Medical History!");
        System.out.println("1: View Medical History From Owner");
        System.out.println("2: View Medical History From Pet");
        System.out.println("3: View Medical History From Vet");
        System.out.println("4: Exit");

        var value = console.readLine();
        switch (value) {
            case "1":
                clinicServices.getAllMedicalHistoryByOwner();
                break;
            case "2":
                clinicServices.getAllMedicalHistoryByPet();
                break;
            case "3":
                clinicServices.getAllMedicalHistoryByVet();
                break;
            case "4":
                System.out.println("Exit!");
                return;
            default:
                System.out.println("Please Enter a Number");
        }
    }

    //appointment

    public void appointmentList() {
    }

    public void createAppointmentMenu() {
        System.out.println("Enter your Pet ID: ");
        String petId = console.readLine();
        System.out.println("Enter Vet ID: ");
        String vetId = console.readLine();
        System.out.println("Enter date: ");
        String date = console.readLine();
        System.out.println("Enter Description: ");
        String desc = console.readLine();
        clinicServices.createAppointment();
    }

    public void updateAppointmentMenu(Console console) {
        System.out.println("Enter your Pet ID: ");
        String petId = console.readLine();
        System.out.println("Enter Vet ID: ");
        String vetId = console.readLine();
        System.out.println("Enter date: ");
        String date = console.readLine();
        System.out.println("Enter Description: ");
        String desc = console.readLine();
        clinicServices.updateAppointment();
    }

    //petlist
    public void petList() {
    }

    public void addPetData(Console console) {
        System.out.println("Enter your Pet Name: ");
        String name = console.readLine();
        System.out.println("Enter your Pet Type: ");
        String type = console.readLine();
        System.out.println("Enter your Pet Age: ");
        String age = console.readLine();
        System.out.println("Enter your Pet Breed: ");
        String breed = console.readLine();
        System.out.println("Enter your Pet Color: ");
        String color = console.readLine();
        System.out.println("Enter your Pet Weight: ");
        String weight = console.readLine();
        System.out.println("Enter your ID: ");
        String ownerId = console.readLine(); //same as idCard?
        clinicServices.addPet();
    }

    //create - remove vet
    public void createVetMenu(Console console) {
        System.out.println("Enter your ID Card: ");
        String idCard = console.readLine();
        System.out.println("Enter your Password: ");
        String password = console.readLine();
        System.out.println("Enter your Name: ");
        String name = console.readLine();
        System.out.println("Enter your Address: ");
        String address = console.readLine();
        System.out.println("Enter your Phone Number: ");
        String phone = console.readLine();
        clinicServices.createVet();
    }

    /*Pet View =, Pet list, Appointment List(Cancel Appointments), Login(Owner =,Vet =), Regeister Owner =, Add pet Data, Create Appointments(Owner, Vet)
     * update appointment, Create vet =, Remove Vet =,*/

    //pet list, appointment list
}