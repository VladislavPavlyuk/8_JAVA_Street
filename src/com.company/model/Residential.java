package com.company.model;
import com.company.service.residential.ResidentialPrintable;

// Жилой дом
public class Residential extends Building {

    private ResidentialPrintable residentialPrintable;

    public Residential(ResidentialPrintable residentialPrintable) {
        this.residentialPrintable = residentialPrintable;
    }

    public void setResidentialPrintable(ResidentialPrintable residentialPrintable) {
        this.residentialPrintable = residentialPrintable;
    }

    @Override
    public void printInfo() {
        residentialPrintable.printInfo(this);
    }

    private int numberOfResidents;

    public Residential(int numberOfResidents,int address,String name, int capacity, String description) {
        super(address,name,capacity,description);
        this.numberOfResidents = numberOfResidents;
    }

    public int getNumberOfResidents() {
        return numberOfResidents;
    }

    public void setNumberOfResidents(int numberOfResidents) {
        this.numberOfResidents = numberOfResidents;
    }

    @Override
    public void initializeFromString(String data) {
        // Пример: "address,numberOfResidents"
        String[] parts = data.split(",");
        this.address = Integer.parseInt(parts[0]);
        this.numberOfResidents = Integer.parseInt(parts[1]);
    }
/*
    @Override
    public void printInfo() {
        System.out.println("Residential Building at " + address + " with " + numberOfResidents + " residents.");
    }*/
}
