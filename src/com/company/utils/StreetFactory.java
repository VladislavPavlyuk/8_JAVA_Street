package com.company.utils;
import com.company.model.*;
import com.company.enums.*;
import com.company.service.hospital.*;
import com.company.service.residential.*;
import com.company.service.school.*;
import com.company.service.shop.*;
import java.util.*;

public class StreetFactory {

    private static Random rand = new Random();
    private static final int MIN_ADDRESS = 1;

    private StreetFactory() {}

    public static Street createStreet(String streetName, int buildingCount) {
        Street street = new Street(streetName);
        Set<Integer> usedAddresses = new HashSet<>();
        
        // Create residential buildings without addresses to determine population
        List<Residential> residentialBuildings = createResidentialBuildings(buildingCount);
        
        // Calculate total population
        int totalPopulation = calculateTotalPopulation(residentialBuildings);
        
        // Calculate population units (per 10000 residents)
        int populationUnits = calculatePopulationUnits(totalPopulation);
        
        // Calculate institution counts based on population
        InstitutionCounts counts = calculateInstitutionCounts(populationUnits);
        
        // Calculate maximum address needed
        int maxAddress = buildingCount + counts.getTotalInstitutions();
        
        // Assign addresses to residential buildings and add to street
        assignAddressesToResidentialBuildings(street, residentialBuildings, usedAddresses, maxAddress);
        
        // Create and add all institutions to street
        createSchoolsForStreet(street, counts, usedAddresses, maxAddress);
        createHealthCentersForStreet(street, counts.healthCenters, usedAddresses, maxAddress);
        createShopsForStreet(street, counts, usedAddresses, maxAddress);
        
        // Sort all buildings by address numbers
        street.sortBuildingsByAddress();
        
        return street;
    }

     // Creates residential buildings without addresses for population calculation.
    private static List<Residential> createResidentialBuildings(int buildingCount) {
        List<Residential> residentialBuildings = new ArrayList<>();
        for (int i = 0; i < buildingCount; i++) {
            Residential residential = createResidentialWithoutAddress();
            residentialBuildings.add(residential);
        }
        return residentialBuildings;
    }

     // Calculates total population from residential buildings.
    private static int calculateTotalPopulation(List<Residential> residentialBuildings) {
        int totalPopulation = 0;
        for (Residential residential : residentialBuildings) {
            totalPopulation += residential.getNumberOfResidents();
        }
        return totalPopulation;
    }

     // Calculates population units based on total population (per 10000 residents).

    private static int calculatePopulationUnits(int totalPopulation) {
        return Math.max(1, (int) Math.ceil((double) totalPopulation / 10000));
    }

     // Data class to hold institution counts.

    private static class InstitutionCounts {
        int preschools;
        int primarySchools;
        int secondarySchools;
        int healthCenters;
        int supermarkets;
        int smallShops;
        
        int getTotalInstitutions() {
            return preschools + primarySchools + secondarySchools + 
                   healthCenters + supermarkets + smallShops;
        }
    }

     // Calculates counts of all institutions based on population units.

    private static InstitutionCounts calculateInstitutionCounts(int populationUnits) {
        InstitutionCounts counts = new InstitutionCounts();
        
        // Calculate school counts
        SchoolCounts schoolCounts = calculateSchoolCounts(populationUnits);
        counts.preschools = schoolCounts.preschools;
        counts.primarySchools = schoolCounts.primarySchools;
        counts.secondarySchools = schoolCounts.secondarySchools;
        
        // Health Centers: 1 per 10000 residents
        counts.healthCenters = populationUnits;
        
        // Shops: 15-30 per 10000 residents (1 Supermarket + small local shops)
        int totalShopsPerUnit = 15 + rand.nextInt(16); // 15-30 shops per 10000 residents
        int totalShops = totalShopsPerUnit * populationUnits;
        counts.supermarkets = populationUnits; // 1 supermarket per 10000 residents
        counts.smallShops = totalShops - counts.supermarkets; // Remaining shops are small local shops
        
        return counts;
    }

     // Data class to hold school counts.

    private static class SchoolCounts {
        int preschools;
        int primarySchools;
        int secondarySchools;
    }

     // Calculates school counts: 7-10 per 10000 residents (5-7 Preschools, 2-3 Primary, 1 Secondary).

    private static SchoolCounts calculateSchoolCounts(int populationUnits) {
        SchoolCounts counts = new SchoolCounts();
        
        int totalSchoolsPerUnit = 7 + rand.nextInt(4); // 7-10 schools per 10000 residents
        
        // Distribute schools: prioritize preschools (5-7), then primary (2-3), then secondary (1)
        int preschoolsPerUnit = 5 + rand.nextInt(3); // 5-7 preschools
        int primarySchoolsPerUnit = 2 + rand.nextInt(2); // 2-3 primary schools
        int secondarySchoolsPerUnit = 1; // 1 secondary school
        
        // Adjust to match total if needed
        int sumPerUnit = preschoolsPerUnit + primarySchoolsPerUnit + secondarySchoolsPerUnit;
        if (sumPerUnit > totalSchoolsPerUnit) {
            // Reduce preschools if needed
            preschoolsPerUnit = Math.max(5, totalSchoolsPerUnit - primarySchoolsPerUnit - secondarySchoolsPerUnit);
        } else if (sumPerUnit < totalSchoolsPerUnit) {
            // Add extra to preschools
            preschoolsPerUnit += (totalSchoolsPerUnit - sumPerUnit);
        }
        
        counts.preschools = preschoolsPerUnit * populationUnits;
        counts.primarySchools = primarySchoolsPerUnit * populationUnits;
        counts.secondarySchools = secondarySchoolsPerUnit * populationUnits;
        
        return counts;
    }

     // Assigns addresses to residential buildings and adds them to the street.

    private static void assignAddressesToResidentialBuildings(Street street, 
                                                               List<Residential> residentialBuildings,
                                                               Set<Integer> usedAddresses, 
                                                               int maxAddress) {
        for (Residential residential : residentialBuildings) {
            residential.setAddress(generateUniqueAddress(usedAddresses, maxAddress));
            street.addBuilding(residential);
        }
    }

      //Creates schools and adds them to the street.

    private static void createSchoolsForStreet(Street street, InstitutionCounts counts,
                                               Set<Integer> usedAddresses, int maxAddress) {
        for (int i = 0; i < counts.preschools; i++) {
            School school = createSchoolWithLevel(usedAddresses, AccreditationLevel.PRE_SCHOOL, maxAddress);
            street.addBuilding(school);
        }
        for (int i = 0; i < counts.primarySchools; i++) {
            School school = createSchoolWithLevel(usedAddresses, AccreditationLevel.ELEMENTARY, maxAddress);
            street.addBuilding(school);
        }
        for (int i = 0; i < counts.secondarySchools; i++) {
            School school = createSchoolWithLevel(usedAddresses, AccreditationLevel.HIGH, maxAddress);
            street.addBuilding(school);
        }
    }

     // Creates health centers/clinics and adds them to the street.

    private static void createHealthCentersForStreet(Street street, int healthCenterCount,
                                                     Set<Integer> usedAddresses, int maxAddress) {
        for (int i = 0; i < healthCenterCount; i++) {
            Hospital clinic = createHealthCenter(usedAddresses, maxAddress);
            street.addBuilding(clinic);
        }
    }

     // Creates shops (supermarkets and small shops) and adds them to the street.

    private static void createShopsForStreet(Street street, InstitutionCounts counts,
                                              Set<Integer> usedAddresses, int maxAddress) {
        // Create supermarkets
        for (int i = 0; i < counts.supermarkets; i++) {
            Shop shop = createShopWithType(usedAddresses, ShopTypes.Supermarket, maxAddress);
            street.addBuilding(shop);
        }
        
        // Create small local shops
        ShopTypes[] smallShopTypes = getSmallShopTypes();
        for (int i = 0; i < counts.smallShops; i++) {
            ShopTypes shopType = smallShopTypes[rand.nextInt(smallShopTypes.length)];
            Shop shop = createShopWithType(usedAddresses, shopType, maxAddress);
            street.addBuilding(shop);
        }
    }

     // Returns array of small shop types for random selection.

    private static ShopTypes[] getSmallShopTypes() {
        return new ShopTypes[] {
            ShopTypes.ConvenienceStore, ShopTypes.GroceryStore, ShopTypes.Kiosk,
            ShopTypes.Stall, ShopTypes.ClothingBoutique, ShopTypes.Bakery,
            ShopTypes.Pharmacy, ShopTypes.CosmeticsStore, ShopTypes.Bookstore,
            ShopTypes.ToyStore, ShopTypes.ShoeStore, ShopTypes.StationeryStore,
            ShopTypes.Florist, ShopTypes.CandyShop, ShopTypes.CoffeeTeaShop
        };
    }

    public static Street createDefaultStreet() {
        // Create street with residential buildings to generate population
        // Then add schools, hospitals/clinics, and shops based on population
        return createStreet("Main Street", 20);
    }

    private static int generateUniqueAddress(Set<Integer> usedAddresses, int maxAddress) {
        // Check if we've exhausted all available addresses
        int availableAddresses = maxAddress - MIN_ADDRESS + 1;
        if (usedAddresses.size() >= availableAddresses) {
            throw new IllegalStateException(
                String.format("All available addresses (%d-%d) are already in use", 
                    MIN_ADDRESS, maxAddress));
        }
        
        int address;
        int attempts = 0;
        int maxAttempts = 1000; // Maximum attempts to find a unique address
        
        // Try to generate a random unique address
        do {
            address = MIN_ADDRESS + rand.nextInt(maxAddress - MIN_ADDRESS + 1);
            attempts++;
            
            // If too many attempts, fall back to sequential search
            if (attempts > maxAttempts) {
                // Sequential search for first available address
                address = MIN_ADDRESS;
                while (usedAddresses.contains(address) && address <= maxAddress) {
                    address++;
                }
                
                // If we've exhausted all addresses, throw exception
                if (address > maxAddress) {
                    throw new IllegalStateException(
                        String.format("All available addresses (%d-%d) are already in use", 
                            MIN_ADDRESS, maxAddress));
                }
                break;
            }
        } while (usedAddresses.contains(address));
        
        // Add the address to the used set
        usedAddresses.add(address);
        return address;
    }
    
    // Creates a residential building without an address (for population calculation).
    // Address will be assigned later based on total building count.
    private static Residential createResidentialWithoutAddress() {
        Residential residential = new Residential(new ResidentialFullPrintable());
        residential.setName("Residential Building");
        
        // Set capacity as number of apartments (reasonable range: 10-100 apartments)
        int numberOfApartments = 10 + rand.nextInt(91); // 10 to 100 apartments
        residential.setCapacity(numberOfApartments);
        residential.setDescription("Multi-apartment residential building");
        
        // Initialize apartments (will automatically calculate residents, max 3 per apartment)
        residential.setNumberOfApartments(numberOfApartments);
        
        return residential;
    }

    public static Hospital createHospital(Set<Integer> usedAddresses) {
        // Default maxAddress for menu usage - use a reasonable default
        int defaultMaxAddress = Math.max(1000, usedAddresses.size() + 100);
        return createHospital(usedAddresses, defaultMaxAddress);
    }
    
    public static Hospital createHospital(Set<Integer> usedAddresses, int maxAddress) {
        Hospital hospital = new Hospital(new HospitalFullPrintable());
        hospital.setAddress(generateUniqueAddress(usedAddresses, maxAddress));
        hospital.setName("Hospital #" + hospital.getAddress());
        hospital.setCapacity(50 + rand.nextInt(250));
        hospital.setDescription("Medical institution");
        hospital.setNumberOfPatients(rand.nextInt(hospital.getCapacity()));
        return hospital;
    }

    public static Residential createResidential(Set<Integer> usedAddresses) {
        // Default maxAddress for menu usage - use a reasonable default
        int defaultMaxAddress = Math.max(1000, usedAddresses.size() + 100);
        return createResidential(usedAddresses, defaultMaxAddress);
    }
    
    public static Residential createResidential(Set<Integer> usedAddresses, int maxAddress) {
        Residential residential = new Residential(new ResidentialFullPrintable());
        residential.setAddress(generateUniqueAddress(usedAddresses, maxAddress));
        residential.setName("Residential Building #" + residential.getAddress());
        
        // Set capacity as number of apartments (reasonable range: 10-100 apartments)
        int numberOfApartments = 10 + rand.nextInt(91); // 10 to 100 apartments
        residential.setCapacity(numberOfApartments);
        residential.setDescription("Multi-apartment residential building");
        
        // Initialize apartments (will automatically calculate residents, max 3 per apartment)
        residential.setNumberOfApartments(numberOfApartments);
        
        return residential;
    }

    public static School createSchool(Set<Integer> usedAddresses) {
        // Default maxAddress for menu usage - use a reasonable default
        int defaultMaxAddress = Math.max(1000, usedAddresses.size() + 100);
        return createSchool(usedAddresses, defaultMaxAddress);
    }
    
    public static School createSchool(Set<Integer> usedAddresses, int maxAddress) {
        School school = new School(new SchoolFullPrintable());
        school.setAddress(generateUniqueAddress(usedAddresses, maxAddress));
        
        AccreditationLevel[] levels = AccreditationLevel.values();
        AccreditationLevel level = levels[rand.nextInt(levels.length)];
        school.setAccreditationLevel(level);
        school.setName("School #" + school.getAddress());
        school.setCapacity(level.getMaxStudents());
        school.setDescription(level.getName());
        
        return school;
    }
    
    public static School createSchoolWithLevel(Set<Integer> usedAddresses, AccreditationLevel level) {
        // Default maxAddress for menu usage - use a reasonable default
        int defaultMaxAddress = Math.max(1000, usedAddresses.size() + 100);
        return createSchoolWithLevel(usedAddresses, level, defaultMaxAddress);
    }
    
    public static School createSchoolWithLevel(Set<Integer> usedAddresses, AccreditationLevel level, int maxAddress) {
        School school = new School(new SchoolFullPrintable());
        school.setAddress(generateUniqueAddress(usedAddresses, maxAddress));
        school.setAccreditationLevel(level);
        school.setName(level.getName() + " #" + school.getAddress());
        school.setCapacity(level.getMaxStudents());
        school.setDescription(level.getName());
        
        return school;
    }

    public static Shop createShop(Set<Integer> usedAddresses) {
        // Default maxAddress for menu usage - use a reasonable default
        int defaultMaxAddress = Math.max(1000, usedAddresses.size() + 100);
        return createShop(usedAddresses, defaultMaxAddress);
    }
    
    public static Shop createShop(Set<Integer> usedAddresses, int maxAddress) {
        Shop shop = new Shop(new ShopFullPrintable());
        shop.setAddress(generateUniqueAddress(usedAddresses, maxAddress));
        
        ShopTypes[] shopTypes = ShopTypes.values();
        ShopTypes shopType = shopTypes[rand.nextInt(shopTypes.length)];
        shop.setShopType(shopType);
        shop.setName(shopType.getShopType());
        shop.setCapacity(100 + rand.nextInt(500));
        
        return shop;
    }
    
    public static Shop createShopWithType(Set<Integer> usedAddresses, ShopTypes shopType) {
        // Default maxAddress for menu usage - use a reasonable default
        int defaultMaxAddress = Math.max(1000, usedAddresses.size() + 100);
        return createShopWithType(usedAddresses, shopType, defaultMaxAddress);
    }
    
    public static Shop createShopWithType(Set<Integer> usedAddresses, ShopTypes shopType, int maxAddress) {
        Shop shop = new Shop(new ShopFullPrintable());
        shop.setAddress(generateUniqueAddress(usedAddresses, maxAddress));
        shop.setShopType(shopType);
        shop.setName(shopType.getShopType());
        shop.setCapacity(100 + rand.nextInt(500));
        
        return shop;
    }
    
    public static Hospital createHealthCenter(Set<Integer> usedAddresses) {
        // Default maxAddress for menu usage - use a reasonable default
        int defaultMaxAddress = Math.max(1000, usedAddresses.size() + 100);
        return createHealthCenter(usedAddresses, defaultMaxAddress);
    }
    
    public static Hospital createHealthCenter(Set<Integer> usedAddresses, int maxAddress) {
        Hospital clinic = new Hospital(new HospitalFullPrintable());
        clinic.setAddress(generateUniqueAddress(usedAddresses, maxAddress));
        clinic.setName("Health Center/Clinic #" + clinic.getAddress());
        clinic.setCapacity(20 + rand.nextInt(30)); // Smaller capacity for clinic
        clinic.setDescription("Health Center/Clinic - provides basic medical services");
        clinic.setNumberOfPatients(rand.nextInt(clinic.getCapacity()));
        return clinic;
    }

    public static IBuilding createRandomBuilding(Set<Integer> usedAddresses) {
        // Default maxAddress for menu usage - use a reasonable default
        int defaultMaxAddress = Math.max(1000, usedAddresses.size() + 100);
        return createRandomBuilding(usedAddresses, defaultMaxAddress);
    }
    
    public static IBuilding createRandomBuilding(Set<Integer> usedAddresses, int maxAddress) {
        int choice = rand.nextInt(4);
        
        switch (choice) {
            case 0:
                return createHospital(usedAddresses, maxAddress);
            case 1:
                return createResidential(usedAddresses, maxAddress);
            case 2:
                return createSchool(usedAddresses, maxAddress);
            case 3:
                return createShop(usedAddresses, maxAddress);
            default:
                return null;
        }
    }

    // Methods for creating individual buildings (for use in menu)
    public static Hospital createHospital() {
        Set<Integer> usedAddresses = new HashSet<>();
        return createHospital(usedAddresses);
    }

    public static Residential createResidential() {
        Set<Integer> usedAddresses = new HashSet<>();
        return createResidential(usedAddresses);
    }

    public static School createSchool() {
        Set<Integer> usedAddresses = new HashSet<>();
        return createSchool(usedAddresses);
    }

    public static Shop createShop() {
        Set<Integer> usedAddresses = new HashSet<>();
        return createShop(usedAddresses);
    }
}
