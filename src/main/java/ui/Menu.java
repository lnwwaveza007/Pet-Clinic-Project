package ui;

import java.io.*;
import services.ClinicServices;


public class Menu {
    static Console console = System.console();

    public static void main(String[] args) {
        while (true) {
            mainMenu();
            System.out.println("Enter your choice: ");

            var value = console.readLine();
            switch (value) {
                case "1":
                    adminMenu(console);
                    break;
                case "2":
                    ownerMenu(console);
                    break;
                case "3":
                    createOwner();
                    break;
                case "4":
                    vetMenu(console);
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

    public static void mainMenu() {
        System.out.println("Welcome to Main Menu");
        System.out.println("1: Admin Menu");
        System.out.println("2: Owner's Menu");
        System.out.println("3: Register Menu");
        System.out.println("4: Vet Menu");
        System.out.println("5: Exit");
    }

    public static void adminMenu(Console console) {
        System.out.println("Admin Menu!");
        System.out.println("1: Create Vet");
        System.out.println("2: Remove Vet");
        System.out.println("3: View all Apointments");
        System.out.println("4: View all Owners");
        System.out.println("5: View all Pets");
        System.out.println("6: Check Vet");
        System.out.println("7: Medical History's");
        System.out.println("8: Exit");

        var value = console.readLine();
        switch (value) {
            case "1":
                createVet();
                break;
            case "2":
                removeVet();
                break;
            case "3":
                getAllAppointments();
                break;
            case "4":
                //เอา getallowner มาาาาาาาาาาา!!!!!!!!!!!!
                break;
            case "5":
                getAllPetByOwner();
                break;
            case "6":
                //เอา getallVet มาาาาาาาาาาา!!!!!!!!!!!!
                break;
            case "7":
                medicalHistory(console);
                break;
            case "8":
                System.out.println("Exit!");
                return;
            default:
                System.out.println("Please Enter a Number");
                break;
        }

    }
    public static void ownerMenu(Console console) {
        System.out.println("Owner Menu!");
        System.out.println("1: Show All Pets"); //list pet + enter pet view
        System.out.println("2: Add Pet Data");
        System.out.println("3: Check All Appointments");
        System.out.println("4: Medical History's");
        System.out.println("5: Change Password");
        System.out.println("6: Exit");

        var value = console.readLine();
        switch (value) {
            case "1":
                getAllPetByOwner();
                break;
            case "2":
                addPet();
                break;
            case "3":
                getAllAppointmentByOwner();
                break;
            case "4":
                getAllMedicalHistoryByOwner();
                break;
            case "5":
                System.out.println("Exit!");
                return;
            default:
                System.out.println("Please Enter a Number");
                break;
        }

    }

    public static void vetMenu(Console console) {
        System.out.println("Vet Menu!");
        System.out.println("1: Create Appointment");
        System.out.println("2: Cancel Appointments");
        System.out.println("3: View Appointments"); // update appointments
        System.out.println("4: Medical History's");
        System.out.println("5: Exit");

        var value = console.readLine();
        switch(value) {
            case "1":
                createAppointment();
                break;
            case "2":
                deleteAppointment();
                break;
            case "3":
                getAllAppointmentByVet();
                break;
            case "4":
                getAllMedicalHistoryByVet();
                break;
            case "5":
                System.out.println("Exit!");
                return;
            default:
                System.out.println("Please Enter a Number");
        }

    }

    public static void adminMedicalHistory(Console console) {
        System.out.println("Medical History!");
        System.out.println("1: View Medical History From Owner");
        System.out.println("2: View Medical History From Pet");
        System.out.println("3: View Medical History From Vet");
        System.out.println("4: Exit");

        var value = console.readLine();
        switch(value) {
            case "1":
                getAllMedicalHistoryByOwner();
                break;
            case "2":
                getAllMedicalHistoryByPet();
                break;
            case "3":
                getAllMedicalHistoryByVet();
                break;
            case "4":
                System.out.println("Exit!");
                return;
            default:
                System.out.println("Please Enter a Number");
        }
    }
    /*Pet View, Pet list, Appointment List, Login(Owner,Vet), Regeister Owner, Add pet Data, Create Appointments(Owner, Vet)
    * Cancel Appointments, update appointment, Create vet, Remove Vet,*/
}