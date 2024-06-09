package org.group11;

import repository.filerepo.*;
import repository.jdbcrepo.*;
import repository.memoryrepo.*;
import ui.Menu;

import java.io.Console;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Console console = System.console();
        System.out.println("[--- Please select Database ---]");
        System.out.println("1. Memory");
        System.out.println("2. File");
        System.out.println("3. JDBC (SQL)");
        System.out.println("[-----------------------------]");
        System.out.println("Please enter the number :");
        var value = console.readLine();
        switch (value) {
            case "1" -> new Menu(new MemoryOwnerRepo(),new MemoryPetRepo(), new MemoryAppointmentRepo(), new MemoryMedHisRepo(), new MemoryVetRepo()).start();
            case "2" -> new Menu(new FileOwnerRepo(),new FilePetRepo(), new FileAppointmentRepo(), new FileMedHisRepo(), new FileVetRepo()).start();
            case "3" -> {
                try {
                    new Menu(new JdbcOwnerRepo(), new JdbcPetRepo(), new JdbcAppointmentRepo(), new JdbcMedHisRepo(), new JdbcVetRepo()).start();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Server is now offline. . .");
                }
            }
        }
//        new JdbcOwnerRepo().addOwner(123456789,"1234","Test","Test","Test");
    }
}