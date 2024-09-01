package com.company.utils;
import com.company.model.*;
import com.company.service.residential.*;
import com.company.service.shop.*;
import java.util.Random;

public class StreetFactory {

    static Random rand = new Random();

    static int streetPopulation = 100 + rand.nextInt(10000);
    // Max Buildings
    static int hospitalsMax = streetPopulation / 5000;
    static int residentialMax = streetPopulation / 300;
    static int schoolsMax = streetPopulation / 1500;
    static int shopsMax = streetPopulation / 300;

    static int []streetAdresses = new int[hospitalsMax + residentialMax + schoolsMax + shopsMax];
    // Capacity
    static int []hospitalCapacity = {50,150,200,250,300};
    static int []residentialCapacity = {50, 100, 150, 200, 250, 300};
    static int []schoolCapacity = {250,500,1000};

    private StreetFactory() {}

    public static Building createResidental() {
        Random rand = new Random();
        Residential residental = new Residential(new ResidentialFullPrintable());
        residental.setAddress(streetAdresses[rand.nextInt(streetAdresses.length)]);
        residental.setCapacity(residentialCapacity[rand.nextInt(residentialCapacity.length)]);
        residental.setNumberOfResidents(rand.nextInt(residental.getCapacity()));
        return residental;
    }

    public static Building createShop() {
        Random rand = new Random();
        Shop shop = new Shop(new ShopFullPrintable());
        shop.setAddress(streetAdresses[rand.nextInt(streetAdresses.length)]);
        shop.setCapacity(residentialCapacity[rand.nextInt(residentialCapacity.length)]);
        shop.setNumberOfDepartments(rand.nextInt(shop.getNumberOfDepartments()));
        return residental;
    }

    public static Building createRandomBuilding() {

        Random rand = new Random();
        int choice = rand.nextInt(4);

        Building result = null;

        switch (choice) {
            case 0:
                if (0 < hospitalsMax) {
                    result = createHospital();
                    hospitalsMax--;
                }
                break;
            case 1:
                if (0 < residentialMax) {
                    result = createResidental();
                    residentialMax--;
                }
                break;
            case 2:
                if (0 < schoolsMax) {
                    result = createSchool();
                    schoolsMax--;
                }
                break;
            case 3:
                if (0 < shopsMax) {
                    result = createShop();
                    shopsMax--;
                }
                break;
        }
        return result;
    }
}
