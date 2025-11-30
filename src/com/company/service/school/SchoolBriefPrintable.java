package com.company.service.school;

import com.company.model.IBuilding;
import com.company.model.School;

public class SchoolBriefPrintable implements SchoolPrintable {
    @Override
    public void printInfo(IBuilding building) {
        if (building instanceof School) {
            School school = (School) building;
            String levelInfo = school.getAccreditationLevel() != null ? 
                school.getAccreditationLevel().getName() : "Not specified";
            String description = school.getAccreditationLevel() != null && 
                school.getAccreditationLevel().getDescription() != null ?
                " - " + school.getAccreditationLevel().getDescription() : "";
            System.out.println("School: " + (school.getName() != null ? school.getName() : "Not specified") + 
                             ", Address: " + school.getAddress() + 
                             ", Students: " + school.getNumberOfStudents() + 
                             ", Level: " + levelInfo + description);
        }
    }
}
