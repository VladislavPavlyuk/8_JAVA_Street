package com.company;


import java.util.Random;

public class StreetFactory {

    Random rand = new Random();

    int streetPopulation = 100 + rand.nextInt(10000);

    int residentalBuildings = streetPopulation / 3;

    int shops = streetPopulation / 300;

    int schools = streetPopulation / 1500;

    int hospitals = streetPopulation / 5000;

    private StreetFactory() {}

    public static StreetFactory createResidentalBuilding() {
        Random rand = new Random();

        ResidentialBuilding residental = new ResidentialBuilding(new ManPrintBrief());
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
