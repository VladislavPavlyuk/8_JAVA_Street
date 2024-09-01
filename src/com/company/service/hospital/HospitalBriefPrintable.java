package com.company.service.hospital;
import com.company.model.Building;

public class HospitalBriefPrintable implements HospitalPrintable {
    @Override
    public void printInfo(Building building) {
        System.out.println("Brief information for Hospital " + building.getName());
        System.out.println(building.getAddress());
    }
}