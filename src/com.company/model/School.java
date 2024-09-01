package com.company.model;

import com.company.service.school.SchoolPrintable;

// Школа
public class School extends Building {

    private SchoolPrintable schoolPrintable;

    public School(SchoolPrintable schoolPrintable) {
        this.schoolPrintable = schoolPrintable;
    }

    public void setSchoolPrintable(SchoolPrintable schoolPrintable) {
        this.schoolPrintable = schoolPrintable;
    }

    private int numberOfStudents;

    private String accreditationLevel;

    public School(int address, int numberOfStudents, String accreditationLevel) {
        super(address);
        this.numberOfStudents = numberOfStudents;
        this.accreditationLevel = accreditationLevel;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public String getAccreditationLevel() {
        return accreditationLevel;
    }

    public void setAccreditationLevel(String accreditationLevel) {
        this.accreditationLevel = accreditationLevel;
    }

    @Override
    public void initializeFromString(String data) {
        // Пример: "address,numberOfStudents,accreditationLevel"
        String[] parts = data.split(",");
        this.address = Integer.parseInt(parts[0]);
        this.numberOfStudents = Integer.parseInt(parts[1]);
        this.accreditationLevel = parts[2];
    }

    @Override
    public void printInfo() {
        schoolPrintable.printInfo(this);
    }
}
