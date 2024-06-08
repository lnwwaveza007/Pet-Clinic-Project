package org.group11;

import repository.filerepo.FilePetRepo;
import repository.filerepo.FileVetRepo;
import repository.memoryrepo.*;
import ui.Menu;

import java.io.Console;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //new Menu(new MemoryOwnerRepo(),new MemoryPetRepo(), new MemoryAppointmentRepo(), new MemoryMedHisRepo(), new MemoryVetRepo()).start();
        //new FilePetRepo().addPet("Rex", "Dog", 3, "Golden Retriever", "Golden", 20, 1);

        System.out.println(new FilePetRepo().findPetFromId(0).getName());
    }
}