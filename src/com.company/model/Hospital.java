package com.company.model;
import com.company.service.hospital.HospitalPrintable;

// больница
class Hospital extends Building {

    private HospitalPrintable hospitalPrintable;

    public Hospital(HospitalPrintable hospitalPrintable) {
        this.hospitalPrintable = hospitalPrintable;
    }

    public void setHospitalPrintable(HospitalPrintable hospitalPrintable) {
        this.hospitalPrintable = hospitalPrintable;
    }

    private int numberOfPatients;

    private String accreditationLevel;

    public Hospital(int address, int numberOfPatients, String accreditationLevel, HospitalPrintable hospitalPrintable) {
        super(address);
        this.hospitalPrintable = hospitalPrintable;
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

        String[] parts = data.split(",");
        this.address = Integer.parseInt(parts[0]); //Integer.parseInt(str);
        this.numberOfPatients = Integer.parseInt(parts[1]);
        this.accreditationLevel = parts[2];
    }

    @Override
    public void printInfo() {
        hospitalPrintable.printInfo(this);
    }
}

