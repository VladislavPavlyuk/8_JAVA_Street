package com.company.model;
import com.company.enums.*;
import java.util.*;

import com.company.service.shop.ShopPrintable;

// Shop
public class Shop extends Building {

    private ShopPrintable shopPrintable;
    private List<DepartmentType> departments;
    private ShopTypes shopType;
    private String shopDescription;

    public Shop() {
        super();
        this.departments = new ArrayList<>();
    }

    public Shop(int address) {
        super(address);
        this.departments = new ArrayList<>();
    }

    public Shop(int address, String name, int capacity, String description) {
        super(address, name, capacity, description);
        this.departments = new ArrayList<>();
    }

    public Shop(ShopPrintable shopPrintable) {
        super();
        this.shopPrintable = shopPrintable;
        this.departments = new ArrayList<>();
    }

    public Shop(int address, ShopTypes shopType, ShopPrintable shopPrintable) {
        super(address);
        this.shopType = shopType;
        this.shopPrintable = shopPrintable;
        this.departments = new ArrayList<>();
        initializeDepartments();
    }

    private void initializeDepartments() {
        if (shopType == null) {
            return;
        }
        
        List<DepartmentType> appropriateDepts = getAppropriateDepartments();
        
        if (appropriateDepts.isEmpty()) {
            handleEmptyDepartments();
        } else {
            int numberOfDepartments = calculateNumberOfDepartments();
            this.departments = selectRandomDepartments(appropriateDepts, numberOfDepartments);
        }
        
        updateShopMetadata();
    }
    
    /**
     * Gets the list of appropriate departments for the current shop type.
     * @return list of appropriate departments, empty list if shopType is null
     */
    private List<DepartmentType> getAppropriateDepartments() {
        if (shopType == null) {
            return new ArrayList<>();
        }
        return shopType.getAppropriateDepartments();
    }
    
    /**
     * Handles the case when no appropriate departments are found.
     * Sets departments to contain only OTHER as fallback.
     */
    private void handleEmptyDepartments() {
        this.departments = new ArrayList<>();
        this.departments.add(DepartmentType.OTHER);
    }
    
    /**
     * Calculates a random number of departments within the shop type's range.
     * @return random number between minDept and maxDept (inclusive)
     */
    private int calculateNumberOfDepartments() {
        Random rand = new Random();
        int range = shopType.getMaxDept() - shopType.getMinDept() + 1;
        return rand.nextInt(range) + shopType.getMinDept();
    }
    
    /**
     * Selects random departments from the list of appropriate departments.
     * @param appropriateDepts list of appropriate departments to choose from
     * @param numberOfDepartments desired number of departments to select
     * @return list of selected departments (may be less than requested if not enough available)
     */
    private List<DepartmentType> selectRandomDepartments(List<DepartmentType> appropriateDepts, int numberOfDepartments) {
        Random rand = new Random();
        Set<DepartmentType> selectedTypes = new HashSet<>();
        List<DepartmentType> availableDepts = new ArrayList<>(appropriateDepts);
        
        // Ensure we don't select more departments than available
        int maxSelect = Math.min(numberOfDepartments, availableDepts.size());
        
        while (selectedTypes.size() < maxSelect && !availableDepts.isEmpty()) {
            int randomIndex = rand.nextInt(availableDepts.size());
            DepartmentType selectedDept = availableDepts.get(randomIndex);
            selectedTypes.add(selectedDept);
            availableDepts.remove(randomIndex);
        }
        
        return new ArrayList<>(selectedTypes);
    }
    
    /**
     * Updates shop metadata (description and name) from the shop type.
     */
    private void updateShopMetadata() {
        if (shopType != null) {
            this.shopDescription = shopType.getDescription();
            this.setName(shopType.getShopType());
        }
    }

    public void setShopPrintable(ShopPrintable shopPrintable) {
        this.shopPrintable = shopPrintable;
    }

    public ShopTypes getShopType() {
        return shopType;
    }

    public void setShopType(ShopTypes shopType) {
        this.shopType = shopType;
        initializeDepartments();
    }

    public List<DepartmentType> getDepartments() {
        return departments;
    }

    public void setDepartments(List<DepartmentType> departments) {
        this.departments = departments;
    }

    public void addDepartment(DepartmentType department) {
        if (!departments.contains(department)) {
            departments.add(department);
        }
    }

    public void removeDepartment(DepartmentType department) {
        departments.remove(department);
    }

    public boolean hasDepartment(DepartmentType departmentType) {
        return departments.contains(departmentType);
    }

    public int getNumberOfDepartments() {
        return departments.size();
    }

    public String getShopDescription() {
        return shopDescription;
    }

    public void setShopDescription(String shopDescription) {
        this.shopDescription = shopDescription;
    }

    @Override
    public void initializeFromString(String data) {
        try {
            // Format: "address,shopType"
            // shopType can be: enum name (SUPERMARKET), display name (Supermarket), or index (0-based)
            String[] parts = data.split(",");
            
            if (parts.length >= 1) {
                parseAddressFromString(parts[0]);
            }
            
            if (parts.length >= 2) {
                String shopTypeInput = parts[1].trim();
                setShopTypeFromString(shopTypeInput);
                // After setting shop type, initializeDepartments() is automatically called
                // which will randomly assign number of departments within minDept and maxDept
            }
        } catch (Exception e) {
            System.err.println("Error initializing shop from string: " + e.getMessage());
        }
    }
    
    /**
     * Sets shop type from string input.
     * Supports: enum name (SUPERMARKET), display name (Supermarket), or index (0-based)
     * @param input shop type as string
     */
    private void setShopTypeFromString(String input) {
        // First, try to parse as index (0-based)
        try {
            int index = Integer.parseInt(input);
            ShopTypes[] shopTypes = ShopTypes.values();
            if (index >= 0 && index < shopTypes.length) {
                setShopType(shopTypes[index]);
                return;
            }
        } catch (NumberFormatException e) {
            // Not a number, continue to try as name
        }
        
        // Try to find by enum name or display name
        for (ShopTypes type : ShopTypes.values()) {
            if (type.getShopType().equalsIgnoreCase(input) || type.name().equalsIgnoreCase(input)) {
                setShopType(type);
                return;
            }
        }
        
        // If not found, shop type remains null
        // Departments will be empty or set to OTHER
    }
    
    /**
     * Parses address from string part.
     * @param addressPart string containing address
     */
    private void parseAddressFromString(String addressPart) {
        this.address = Integer.parseInt(addressPart.trim());
    }
    
    /**
     * Initializes departments from string data.
     * @param numDepts number of departments to initialize
     */
    private void initializeDepartmentsFromString(int numDepts) {
        departments.clear();
        
        if (shopType != null) {
            selectDepartmentsWithShopType(numDepts);
        } else {
            selectDepartmentsWithoutShopType(numDepts);
        }
    }
    
    /**
     * Selects departments when shop type is set (uses appropriate departments).
     * @param numDepts number of departments to select
     */
    private void selectDepartmentsWithShopType(int numDepts) {
        List<DepartmentType> appropriateDepts = shopType.getAppropriateDepartments();
        
        if (appropriateDepts.isEmpty()) {
            departments.add(DepartmentType.OTHER);
        } else {
            selectRandomDepartmentsFromList(appropriateDepts, numDepts);
        }
    }
    
    /**
     * Selects departments when shop type is not set (uses random selection from all types).
     * @param numDepts number of departments to select
     */
    private void selectDepartmentsWithoutShopType(int numDepts) {
        Random rand = new Random();
        DepartmentType[] allTypes = DepartmentType.values();
        
        for (int i = 0; i < numDepts && i < allTypes.length; i++) {
            DepartmentType dept = allTypes[rand.nextInt(allTypes.length)];
            if (!departments.contains(dept)) {
                departments.add(dept);
            }
        }
    }
    
    /**
     * Selects random departments from a given list.
     * @param availableDepts list of available departments to choose from
     * @param numDepts desired number of departments to select
     */
    private void selectRandomDepartmentsFromList(List<DepartmentType> availableDepts, int numDepts) {
        Random rand = new Random();
        List<DepartmentType> deptsCopy = new ArrayList<>(availableDepts);
        int maxSelect = Math.min(numDepts, deptsCopy.size());
        
        for (int i = 0; i < maxSelect && !deptsCopy.isEmpty(); i++) {
            int randomIndex = rand.nextInt(deptsCopy.size());
            DepartmentType selectedDept = deptsCopy.get(randomIndex);
            if (!departments.contains(selectedDept)) {
                departments.add(selectedDept);
            }
            deptsCopy.remove(randomIndex);
        }
    }

    @Override
    public void printInfo() {
        if (shopPrintable != null) {
            shopPrintable.printInfo(this);
        } else {
            System.out.println("Shop: " + getName() + ", Address: " + address + 
                             ", Departments: " + getNumberOfDepartments());
        }
    }
}
