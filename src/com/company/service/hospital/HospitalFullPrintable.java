package com.company.service.hospital;
import com.company.model.Building;

public class HospitalFullPrintable implements HospitalPrintable {
    @Override
    public void printInfo(Building building) {
        System.out.println("Full information for Hospital " + building.getName());
        System.out.println(building.getAddress() + " " +  building.getCapacity() + " " +  building.getDescription());
    }
}

