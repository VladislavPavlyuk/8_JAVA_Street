package com.company;
import com.company.model.Building;
import com.company.model.Street;
import com.company.utils.StreetFactory;

public class Main {
        public static void main(String[] args) {

            System.out.println("Hello street!");

            Street street1 = StreetFactory.createRandomBuilding();
            street1.print();

            Street street2 = StreetFactory.createRandomBuilding();
            street2.print();

            System.out.println("-".repeat(50));

            Building[] arrBuilding = new Building[10];

            for (int i = 0; i < arrBuilding.length; i++) {
                arrBuilding[i] = StreetFactory.createRandomBuilding();
            }

            for (int i = 0; i < arrBuilding.length; i++) {
                System.out.println();
                arrBuilding[i].print();
                System.out.println();
            }
           /* Street street = new Street();

            Residential house1 = new Residential(1, 4);
            Shop shop1 = new Shop(2, 1);
            School school1 = new School("125 Main St", 300, "High School");

            street.addBuilding(house1);
            street.addBuilding(shop1);
            street.addBuilding(school1);

            street.printStreetInfo();

            List<Shop> nearbyShops = street.findShopsNearResidentialBuilding(house1, 2, "Grocery");
            System.out.println("Nearby shops:");
            for (Shop shop : nearbyShops) {
                shop.printInfo();
            }*/


        }
    }