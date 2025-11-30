package com.company.menu;

import com.company.model.*;
import com.company.enums.*;
import com.company.utils.StreetFactory;
import java.util.*;

public class Menu {
    private Scanner scanner;
    private Street street;

    public Menu() {
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println("WELCOME TO THE STREET MANAGEMENT SYSTEM");
        
        initializeStreet();
        
        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = getIntInput("Select menu item: ");
            
            switch (choice) {
                case 1:
                    printStreetInfo();
                    break;
                case 2:
                    printAllBuildings();
                    break;
                case 3:
                    addBuilding();
                    break;
                case 4:
                    removeBuilding();
                    break;
                case 5:
                    findShopsNearResidential();
                    break;
                case 6:
                    initializeBuildingFromString();
                    break;
                case 7:
                    printBuildingByAddress();
                    break;
                case 0:
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            
            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }

    private void initializeStreet() {
        System.out.println("\nStreet initialization:");
        System.out.print("Enter street name (or press Enter for default): ");
        String streetName = scanner.nextLine().trim();
        if (streetName.isEmpty()) {
            streetName = "Main Street";
        }
        
        System.out.print("Enter number of residential buildings (or press Enter for default): ");
        String countInput = scanner.nextLine().trim();
        int residentialCount = 20;
        if (!countInput.isEmpty()) {
            try {
                residentialCount = Integer.parseInt(countInput);
                if (residentialCount < 1) {
                    residentialCount = 20;
                }
            } catch (NumberFormatException e) {
                residentialCount = 20;
            }
        }
        
        System.out.println("Creating street with " + residentialCount + " residential buildings...");
        System.out.println("Schools, hospitals/clinics, and shops will be created based on population.");
        this.street = StreetFactory.createStreet(streetName, residentialCount);
        System.out.println("Street successfully created!");
        System.out.println("Total population: " + street.getTotalPopulation());
        System.out.println("Total buildings: " + street.getBuildingCount());
    }

    private void printMainMenu() {
        System.out.println("\nMAIN MENU");
        System.out.println("1. Display street information");
        System.out.println("2. Display information about all buildings");
        System.out.println("3. Add building to street");
        System.out.println("4. Remove building from street");
        System.out.println("5. Find shops near residential building");
        System.out.println("6. Initialize building from string");
        System.out.println("7. Display building information by address");
        System.out.println("0. Exit");
    }

    private void printStreetInfo() {
        street.printStreetInfo();
    }

    private void printAllBuildings() {
        System.out.println("\nInformation about all buildings on the street:");
        List<IBuilding> buildings = street.getBuildings();
        if (buildings.isEmpty()) {
            System.out.println("There are no buildings on the street.");
        } else {
            for (IBuilding building : buildings) {
                building.printInfo();
                System.out.println();
            }
        }
    }

    private void addBuilding() {
        System.out.println("\nADDING BUILDING TO STREET");
        printBuildingTypeMenu();
        
        int choice = getIntInput("Select building type: ");
        IBuilding building = createBuildingByType(choice);
        
        if (building == null) {
            return;
        }
        
        // For shops, select shop type first, then ask for address
        if (building instanceof Shop) {
            Shop shop = (Shop) building;
            printShopTypes();
            int shopChoice = getIntInput("Select shop type: ");
            ShopTypes[] shopTypes = ShopTypes.values();
            if (shopChoice >= 0 && shopChoice < shopTypes.length) {
                shop.setShopType(shopTypes[shopChoice]);
            } else {
                System.out.println("Invalid shop type selection.");
                return;
            }
        }
        
        int address = getIntInput("Enter building address: ");
        
        // Check if address is occupied
        if (street.isAddressOccupied(address)) {
            int nextAvailable = street.getNextAvailableAddress();
            System.out.println("Address " + address + " is already occupied.");
            System.out.println("Suggested next available address: " + nextAvailable);
            String useSuggested = getStringInput("Use suggested address? (y/n): ");
            if (useSuggested != null && (useSuggested.equalsIgnoreCase("y") || useSuggested.equalsIgnoreCase("yes"))) {
                address = nextAvailable;
                System.out.println("Using address: " + address);
            } else {
                System.out.println("Building addition cancelled.");
                return;
            }
        }
        
        building.setAddress(address);
        configureBuilding(building, address);
        
        street.addBuilding(building);
        System.out.println("Building successfully added!");
    }
    
    /**
     * Prints the menu for selecting building type.
     */
    private void printBuildingTypeMenu() {
        System.out.println("1. Hospital");
        System.out.println("2. Residential building");
        System.out.println("3. School");
        System.out.println("4. Shop");
    }
    
    // Creates a building based on user's choice.

    private IBuilding createBuildingByType(int choice) {
        switch (choice) {
            case 1:
                return StreetFactory.createHospital();
            case 2:
                return StreetFactory.createResidential();
            case 3:
                return StreetFactory.createSchool();
            case 4:
                return StreetFactory.createShop();
            default:
                System.out.println("Invalid choice.");
                return null;
        }
    }
    
    // Configures building-specific properties based on building type.

    private void configureBuilding(IBuilding building, int address) {
        if (building instanceof Hospital) {
            configureHospital((Hospital) building, address);
        } else if (building instanceof Residential) {
            configureResidential((Residential) building, address);
        } else if (building instanceof School) {
            configureSchool((School) building, address);
        } else if (building instanceof Shop) {
            configureShop((Shop) building, address);
        }
    }
    
    // Configures hospital-specific properties.

    private void configureHospital(Hospital hospital, int address) {
        hospital.setName("Hospital #" + address);
        int capacity = getIntInput("Enter capacity: ");
        hospital.setCapacity(capacity);
        // Set number of patients to a random value within capacity (0 to capacity)
        hospital.setNumberOfPatients(new java.util.Random().nextInt(capacity + 1));
    }
    
    // Configures residential building-specific properties.

    private void configureResidential(Residential residential, int address) {
        residential.setName("Residential Building #" + address);
        int apartments = getIntInput("Enter number of apartments: ");
        
        // Set capacity to match number of apartments to ensure consistency
        residential.setCapacity(apartments);
        
        // Set number of apartments (this will automatically calculate residents: 1-3 per apartment)
        // The setNumberOfApartments method will set exactly the number specified and calculate
        // residents randomly (1-3 per apartment)
        residential.setNumberOfApartments(apartments);
        
        System.out.println("Building initialized with " + residential.getNumberOfApartments() + 
                         " apartments and " + residential.getNumberOfResidents() + " residents " +
                         "(1-3 residents per apartment)");
    }
    
    // Configures school-specific properties.

    private void configureSchool(School school, int address) {
        school.setName("School #" + address);
        printAccreditationLevels();
        int levelChoice = getIntInput("Select accreditation level: ");
        AccreditationLevel[] levels = AccreditationLevel.values();
        if (levelChoice >= 1 && levelChoice <= levels.length) {
            school.setAccreditationLevel(levels[levelChoice - 1]);
        }
    }
    
    // Configures shop-specific properties.
     // Shop type is already set in addBuilding() method, so we only set the name here.

    private void configureShop(Shop shop, int address) {
        shop.setName("Shop #" + address);
        // Shop type is already set in addBuilding() method
        // Number of departments is automatically assigned randomly when shop type is set
    }

    private void removeBuilding() {
        System.out.println("\nRemoving building:");
        int address = getIntInput("Enter building address to remove: ");
        street.removeBuildingByAddress(address);
        System.out.println("Building removed (if it existed).");
    }

    private void findShopsNearResidential() {
        // First, ask for the address of the residential building
        int address = getIntInput("Enter residential building address: ");
        
        Residential residential = street.getResidentialBuildingByAddress(address);
        
        if (residential == null) {
            System.out.println("No residential building found at address " + address + ".");
            return;
        }
        
        System.out.println("\nSearching for shops near residential building:");
        System.out.println("Selected residential building:");
        residential.printInfo();
        
        int range = getIntInput("Enter search radius (number of buildings): ");
        
        System.out.println("\nAvailable department types:");
        DepartmentType[] deptTypes = DepartmentType.values();
        for (int i = 0; i < deptTypes.length; i++) {
            System.out.println((i + 1) + ". " + deptTypes[i].getName());
        }
        
        int deptChoice = getIntInput("Select department type: ");
        if (deptChoice < 1 || deptChoice > deptTypes.length) {
            System.out.println("Invalid choice.");
            return;
        }
        
        DepartmentType selectedDept = deptTypes[deptChoice - 1];
        List<Shop> nearbyShops = street.findShopsNearResidentialBuilding(residential, range, selectedDept);
        
        System.out.println("\nFound shops with department \"" + selectedDept.getName() + "\":");
        if (nearbyShops.isEmpty()) {
            System.out.println("No shops found.");
        } else {
            for (Shop shop : nearbyShops) {
                shop.printInfo();
                System.out.println();
            }
        }
    }

    private void initializeBuildingFromString() {
        System.out.println("\nInitializing building from string:");
        printInitializationInstructions();
        printBuildingTypeMenu();
        
        int choice = getIntInput("Your choice: ");
        
        // For shops, show shop types list before asking for data
        if (choice == 4) {
            System.out.println("\nAvailable shop types (you can use number, enum name, or display name):");
            printShopTypes();
        }
        
        String data = getStringInput("Enter data (comma-separated): ");
        
        IBuilding building = createBuildingForInitialization(choice);
        if (building == null) {
            return;
        }
        
        building.initializeFromString(data);
        
        // Check if address is occupied
        int address = building.getAddress();
        if (street.isAddressOccupied(address)) {
            int nextAvailable = street.getNextAvailableAddress();
            System.out.println("Address " + address + " is already occupied.");
            System.out.println("Suggested next available address: " + nextAvailable);
            String useSuggested = getStringInput("Use suggested address? (y/n): ");
            if (useSuggested != null && (useSuggested.equalsIgnoreCase("y") || useSuggested.equalsIgnoreCase("yes"))) {
                building.setAddress(nextAvailable);
                System.out.println("Using address: " + nextAvailable);
            } else {
                System.out.println("Building initialization cancelled.");
                return;
            }
        }
        
        street.addBuilding(building);
        System.out.println("Building successfully initialized and added!");
        building.printInfo();
    }
    
    // Prints instructions for building initialization from string.

    private void printInitializationInstructions() {
        System.out.println("String format depends on building type:");
        System.out.println("  - Residential building: address,numberOfApartments (max 3 residents per apartment)");
        System.out.println("  - Hospital: address,capacity");
        System.out.println("  - School: address,accreditationLevel");
        System.out.println("  - Shop: address,shopType (number of departments will be assigned randomly)");
    }
    
    // Creates a building for initialization from string.

    private IBuilding createBuildingForInitialization(int choice) {
        switch (choice) {
            case 1:
                return new Hospital(new com.company.service.hospital.HospitalFullPrintable());
            case 2:
                return new Residential(new com.company.service.residential.ResidentialFullPrintable());
            case 3:
                return new School(new com.company.service.school.SchoolFullPrintable());
            case 4:
                return new Shop(new com.company.service.shop.ShopFullPrintable());
            default:
                System.out.println("Invalid choice.");
                return null;
        }
    }

    private void printBuildingByAddress() {
        int address = getIntInput("Enter building address: ");
        List<IBuilding> buildings = street.getBuildings();
        
        for (IBuilding building : buildings) {
            if (building.getAddress() == address) {
                building.printInfo();
                return;
            }
        }
        
        System.out.println("Building with address " + address + " not found.");
    }

    private void printAccreditationLevels() {
        System.out.println("\nAccreditation levels:");
        AccreditationLevel[] levels = AccreditationLevel.values();
        for (int i = 0; i < levels.length; i++) {
            System.out.println((i + 1) + ". " + levels[i].getName());
        }
    }

    private void printShopTypes() {
        System.out.println("\nShop types:");
        ShopTypes[] shopTypes = ShopTypes.values();
        for (int i = 0; i < shopTypes.length; i++) {
            System.out.println(i + ". " + shopTypes[i].getShopType());
        }
    }

    private int getIntInput(String prompt) {
        System.out.print(prompt);
        try {
            int value = Integer.parseInt(scanner.nextLine().trim());
            return value;
        } catch (NumberFormatException e) {
            System.out.println("Input error. Enter a number.");
            return getIntInput(prompt);
        }
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
