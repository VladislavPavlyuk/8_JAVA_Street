package com.company.utils;

import com.company.model.Man;
import com.company.model.Person;
import com.company.model.Woman;
import com.company.service.man.ManPrintBrief;
import com.company.service.woman.WomanPrintFull;

import java.util.Random;

public class PersonFactory {

    private static String[] nameDatMan = new String[] {"Ivan", "Petr", "Pavel"};
    private static String[] lastNameDatMan;
    private static String[] nameDatWoman = new String[] {"Inna", "Oksana", "Irina"};
    private static String[] lastNameDatWoman;

    static {
        lastNameDatMan = new String[] {"Petrov", "Artemyev", "Sokolov", "Ivanov"};
        lastNameDatWoman = new String[] {"Petrova", "Artemyeva", "Sokolova", "Ivanova"};
    }

    private PersonFactory() {}

    public static Person createMan() {
        Random rand = new Random();

        Person person = new Man(new ManPrintBrief());
        person.setName(nameDatMan[rand.nextInt(nameDatMan.length)]);
        person.setLastName(lastNameDatMan[rand.nextInt(lastNameDatMan.length)]);
        return person;
    }

    public static Person createWoman() {
        Random rand = new Random();

        Person person = new Woman(new WomanPrintFull());
        person.setName(nameDatWoman[rand.nextInt(nameDatWoman.length)]);
        person.setLastName(lastNameDatWoman[rand.nextInt(lastNameDatWoman.length)]);
        return person;
    }

    public static Person createRandomPerson() {
        Random rand = new Random();
        int choice = rand.nextInt(2);
        Person result = null;

        switch (choice) {
            case 0:
                result = createMan();
                break;
            case 1:
                result = createWoman();
                break;
        }
        return result;
    }
}
