package com.company.service.residential;
import com.company.model.Building;

public class ResidentialFullPrintable implements ResidentialPrintable {
    @Override
    public void printInfo(Building building) {
        System.out.println("Full information for Residential Building " + building.getName());
        System.out.println(building.getAddress() + " " +  building.getCapacity() + " " +  building.getDescription());
    }
}

