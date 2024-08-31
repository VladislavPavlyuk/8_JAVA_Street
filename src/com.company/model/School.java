package com.company.model;
import com.company.model;

// Школа
class School extends Building {
    private int numberOfStudents;
    private String accreditationLevel;

    public School(String address, int numberOfStudents, String accreditationLevel) {
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
        this.address = parts[0];
        this.numberOfStudents = Integer.parseInt(parts[1]);
        this.accreditationLevel = parts[2];
    }

    @Override
    public void printInfo() {
        System.out.println("School at " + address + " with " + numberOfStudents + " students, accreditation level: " + accreditationLevel + ".");
    }
}
