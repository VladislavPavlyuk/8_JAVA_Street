package com.company.model;


// Магазин
class Shop extends Building {
    private int numberOfDepartments;


    public Shop(String address, int numberOfDepartments, ShopTypes type) {
        super(address);
        this.numberOfDepartments = numberOfDepartments;
    }

    public int getNumberOfDepartments() {
        return numberOfDepartments;
    }

    public void setNumberOfDepartments(int numberOfDepartments) {
        this.numberOfDepartments = numberOfDepartments;
    }

    @Override
    public void initializeFromString(String data) {
        // Пример: "address,numberOfDepartments"
        String[] parts = data.split(",");
        this.address = parts[0];
        this.numberOfDepartments = Integer.parseInt(parts[1]);
    }

    @Override
    public void printInfo() {
        System.out.println("Shop at " + address + " with " + numberOfDepartments + " departments.");
    }
}
