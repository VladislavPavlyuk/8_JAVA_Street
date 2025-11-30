package com.company.service.residential;
import com.company.model.IBuilding;
import com.company.model.Residential;

public class ResidentialFullPrintable implements ResidentialPrintable {
    @Override
    public void printInfo(IBuilding building) {
        if (building instanceof Residential) {
            Residential residential = (Residential) building;
            System.out.println("RESIDENTIAL BUILDING");
            System.out.println("Name: " + (residential.getName() != null ? residential.getName() : "Not specified"));
            System.out.println("Address: " + residential.getAddress());
            System.out.println("Number of apartments: " + residential.getNumberOfApartments());
            System.out.println("Number of residents: " + residential.getNumberOfResidents());
            System.out.println("Average residents per apartment: " + 
                (residential.getNumberOfApartments() > 0 ? 
                    String.format("%.2f", (double) residential.getNumberOfResidents() / residential.getNumberOfApartments()) : 
                    "0.00"));
            if (residential.getDescription() != null && residential.getDescription().length() > 0) {
                System.out.println("Description: " + residential.getDescription());
            }
        }
    }
}
