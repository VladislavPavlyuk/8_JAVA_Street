package com.company.service.school;
import com.company.model.IBuilding;
import com.company.model.School;

public class SchoolFullPrintable implements SchoolPrintable {
    @Override
    public void printInfo(IBuilding building) {
        if (building instanceof School) {
            School school = (School) building;
            System.out.println("SCHOOL");
            System.out.println("Name: " + (school.getName() != null ? school.getName() : "Not specified"));
            System.out.println("Address: " + school.getAddress());
            System.out.println("Capacity: " + school.getCapacity());
            System.out.println("Number of students: " + school.getNumberOfStudents());
            System.out.println("Accreditation level: " + 
                (school.getAccreditationLevel() != null ? school.getAccreditationLevel().getName() : "Not specified"));
            if (school.getAccreditationLevel() != null && school.getAccreditationLevel().getDescription() != null) {
                System.out.println("Description: " + school.getAccreditationLevel().getDescription());
            } else if (school.getDescription() != null && school.getDescription().length() > 0) {
                System.out.println("Description: " + school.getDescription());
            }
        }
    }
}
