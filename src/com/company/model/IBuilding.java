package com.company.model;

// Interface for all types of buildings.
// Defines common operations that all buildings must implement.

public interface IBuilding {

    // Gets the address of the building.
    int getAddress();
    
    // Sets the address of the building.
    void setAddress(int address);
    
    // Gets the name of the building.
    String getName();
    
    // Sets the name of the building.
    void setName(String name);
    
    //Gets the capacity of the building.
    int getCapacity();
    
    // Sets the capacity of the building.
    void setCapacity(int capacity);
    
    // Gets the description of the building.
    String getDescription();
    
    // Sets the description of the building.
    void setDescription(String description);
    
    // Initializes building from string data.
    void initializeFromString(String data);
    
    // Prints information about the building.
    void printInfo();
}

