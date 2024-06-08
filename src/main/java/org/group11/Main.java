package org.group11;

import repository.filerepo.*;
import repository.memoryrepo.*;
import ui.Menu;

import java.io.Console;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Console console = System.console();
        System.out.println("Please select Database");
        System.out.println("1. Memory");
        System.out.println("2. File");
        System.out.println("3. JDBC (SQL)");
        System.out.println("Please enter the number :");
        var value = console.readLine();
        switch (value) {
            case "1" -> new Menu(new MemoryOwnerRepo(),new MemoryPetRepo(), new MemoryAppointmentRepo(), new MemoryMedHisRepo(), new MemoryVetRepo()).start();
            case "2" -> new Menu(new FileOwnerRepo(),new FilePetRepo(), new FileAppointmentRepo(), new FileMedHisRepo(), new FileVetRepo()).start();
        }
    }
}