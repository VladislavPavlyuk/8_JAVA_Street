package com.company.enums;

public enum DepartmentType {
    GROCERY("Grocery"),
    CLOTHING("Clothing"),
    ELECTRONICS("Electronics"),
    PHARMACY("Pharmacy"),
    HOUSEHOLD("Household"),
    BAKERY("Bakery"),
    MEAT("Meat"),
    DAIRY("Dairy"),
    BEVERAGES("Beverages"),
    TOYS("Toys"),
    BOOKS("Books"),
    SPORTS("Sports"),
    COSMETICS("Cosmetics"),
    JEWELRY("Jewelry"),
    FURNITURE("Furniture"),
    AUTOMOTIVE("Automotive"),
    GARDEN("Garden"),
    PET_SUPPLIES("Pet Supplies"),
    STATIONERY("Stationery"),
    OTHER("Other");

    private final String name;

    DepartmentType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

