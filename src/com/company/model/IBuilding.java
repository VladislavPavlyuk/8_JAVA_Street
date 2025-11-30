package com.company.model;

/**
 * Interface for all types of buildings.
 * Defines common operations that all buildings must implement.
 */
public interface IBuilding {
    /**
     * Gets the address of the building.
     * @return building address
     */
    int getAddress();
    
    /**
     * Sets the address of the building.
     * @param address building address
     */
    void setAddress(int address);
    
    /**
     * Gets the name of the building.
     * @return building name
     */
    String getName();
    
    /**
     * Sets the name of the building.
     * @param name building name
     */
    void setName(String name);
    
    /**
     * Gets the capacity of the building.
     * @return building capacity
     */
    int getCapacity();
    
    /**
     * Sets the capacity of the building.
     * @param capacity building capacity
     */
    void setCapacity(int capacity);
    
    /**
     * Gets the description of the building.
     * @return building description
     */
    String getDescription();
    
    /**
     * Sets the description of the building.
     * @param description building description
     */
    void setDescription(String description);
    
    /**
     * Initializes building from string data.
     * Format depends on building type.
     * @param data string data containing building information
     */
    void initializeFromString(String data);
    
    /**
     * Prints information about the building.
     */
    void printInfo();
}

