package com.company.service.residential;
import com.company.model.Building;

public class ResidentialBriefPrintable implements ResidentialPrintable {
    @Override
    public void printInfo(Building building) {
        System.out.println("Brief information for Residential " + building.getName());
        System.out.println(building.getAddress());
    }
}
