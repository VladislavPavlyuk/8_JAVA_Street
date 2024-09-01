package com.company.service.school;

import com.company.model.Building;

import java.awt.print.Printable;

public class SchoolBriefPrintable implements SchoolPrintable {
    @Override
    public void printInfo(Building building) {
        System.out.println("Brief information for School " + building.getName());
        System.out.println(building.getAddress());
    }
}
