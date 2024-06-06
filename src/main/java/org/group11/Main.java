package org.group11;

import java.io.Console;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Console console = System.console();
        while (true) {
            System.out.println("Hello world!");
            System.out.println("1. To enter room");
            System.out.println("2. To exit room");
            if (console.readLine().equalsIgnoreCase("1")) {
                room();
            } else {
                break;
            }
        }
    }

    public static void room() {
        Console console = System.console();
        System.out.println("Yay you're now in room!");
        System.out.println("1. To enter room");
        System.out.println("2. To exit room");
        if (console.readLine().equalsIgnoreCase("1")) {
            System.out.println("You entered the room");
        } else {
            return;
        }
    }
}