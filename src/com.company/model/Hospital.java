package com.company.model;

// больница
class Hospital extends Building {
    private int numberOfPatients;
    private String accreditationLevel;

    public Hospital(String address, int numberOfPatients, String accreditationLevel) {
        super(address);
        this.numberOfPatients = numberOfPatients;
        this.accreditationLevel = accreditationLevel;
    }

    public int getNumberOfStudents() {
        return numberOfPatients;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfPatients = numberOfStudents;
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
        this.numberOfPatients = Integer.parseInt(parts[1]);
        this.accreditationLevel = parts[2];
    }

    @Override
    public void printInfo() {
        System.out.println("School at " + address + " with " + numberOfPatients + " students, accreditation level: " + accreditationLevel + ".");
    }
}
