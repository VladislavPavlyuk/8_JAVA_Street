package com.company.model;
import com.company.service.hospital.HospitalPrintable;

// Hospital
public class Hospital extends Building {

    private HospitalPrintable hospitalPrintable;
    private int numberOfPatients;

    public Hospital() {
        super();
    }

    public Hospital(int address) {
        super(address);
    }

    public Hospital(int address, String name, int capacity, String description) {
        super(address, name, capacity, description);
    }

    public Hospital(HospitalPrintable hospitalPrintable) {
        super();
        this.hospitalPrintable = hospitalPrintable;
    }

    public Hospital(int address, int numberOfPatients, HospitalPrintable hospitalPrintable) {
        super(address);
        this.hospitalPrintable = hospitalPrintable;
        this.numberOfPatients = numberOfPatients;
    }

    public void setHospitalPrintable(HospitalPrintable hospitalPrintable) {
        this.hospitalPrintable = hospitalPrintable;
    }

    public int getNumberOfPatients() {
        return numberOfPatients;
    }

    public void setNumberOfPatients(int numberOfPatients) {
        this.numberOfPatients = numberOfPatients;
    }

    @Override
    public void initializeFromString(String data) {
        try {
            // Example: "address,capacity"
            String[] parts = data.split(",");
            if (parts.length >= 1) {
                setAddress(Integer.parseInt(parts[0].trim()));
            }
            if (parts.length >= 2) {
                int capacity = Integer.parseInt(parts[1].trim());
                setCapacity(capacity);
                // Set number of patients to a random value within capacity (0 to capacity)
                java.util.Random rand = new java.util.Random();
                this.numberOfPatients = rand.nextInt(capacity + 1);
            }
        } catch (Exception e) {
            System.err.println("Error initializing hospital from string: " + e.getMessage());
        }
    }

    @Override
    public void printInfo() {
        if (hospitalPrintable != null) {
            hospitalPrintable.printInfo(this);
        } else {
            System.out.println("Hospital: " + getName() + ", Address: " + address + 
                             ", Patients: " + numberOfPatients);
        }
    }
}
