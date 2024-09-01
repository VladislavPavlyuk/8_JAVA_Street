package com.company.model;
import com.company.service.hospital.HospitalPrintable;

// больница
public class Hospital extends Building {

    private HospitalPrintable hospitalPrintable;

    public Hospital(HospitalPrintable hospitalPrintable) {
        this.hospitalPrintable = hospitalPrintable;
    }

    public void setHospitalPrintable(HospitalPrintable hospitalPrintable) {
        this.hospitalPrintable = hospitalPrintable;
    }

    private int numberOfPatients;

    public Hospital(int address, int numberOfPatients,  HospitalPrintable hospitalPrintable) {
        super(address);
        this.hospitalPrintable = hospitalPrintable;
        this.numberOfPatients = numberOfPatients;
    }

    public int getNumberOfPatients() {
        return numberOfPatients;
    }

    public void setNumberOfPatients(int numberOfPatients) {
        this.numberOfPatients = numberOfPatients;
    }

    @Override
    public void initializeFromString(String data) {

        String[] parts = data.split(",");
        this.address = Integer.parseInt(parts[0]); //Integer.parseInt(str);
        this.numberOfPatients = Integer.parseInt(parts[1]);
    }

    @Override
    public void printInfo() {
        hospitalPrintable.printInfo(this);
    }
}

