package com.company.service.school;
import com.company.model.Building;

public class SchoolFullPrintable implements SchoolPrintable {
    @Override
    public void printInfo(Building building) {
        System.out.println("Full information for School " + building.getName());
        System.out.println(building.getAddress() + " " +  building.getCapacity() + " " +  building.getDescription());
    }
}
