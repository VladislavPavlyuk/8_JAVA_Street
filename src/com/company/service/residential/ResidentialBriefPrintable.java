package com.company.service.residential;
import com.company.model.IBuilding;
import com.company.model.Residential;

public class ResidentialBriefPrintable implements ResidentialPrintable {
    @Override
    public void printInfo(IBuilding building) {
        if (building instanceof Residential) {
            Residential residential = (Residential) building;
            System.out.println("Residential building: " + (residential.getName() != null ? residential.getName() : "Not specified") + 
                             ", Address: " + residential.getAddress() + 
                             ", Apartments: " + residential.getNumberOfApartments() +
                             ", Residents: " + residential.getNumberOfResidents());
        }
    }
}
