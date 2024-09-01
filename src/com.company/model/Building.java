package com.company.model;

// Абстрактный класс для всех типов домов
public abstract class Building {
    public int address;
    private String name;
    private int capacity;
    private String description;

    public Building(int address, String name, int capacity, String description) {
        this.address = address;
        this.name = name;
        this.capacity = capacity;
        this.description = description;
    }
    public Building() {}

    public Building(int address) {
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public abstract void initializeFromString(String data);

    public abstract void printInfo();
}
