package com.company.model;
import com.company.service.residential.ResidentialPrintable;
import java.util.*;

// Residential building
public class Residential extends Building {

    private ResidentialPrintable residentialPrintable;
    private int numberOfResidents;
    private int numberOfApartments;
    private List<Integer> residentsPerApartment; // List of residents in each apartment (max 3 per apartment)
    private static final int MAX_RESIDENTS_PER_APARTMENT = 3;

    public Residential() {
        super();
        this.residentsPerApartment = new ArrayList<>();
    }

    public Residential(int address) {
        super(address);
        this.residentsPerApartment = new ArrayList<>();
    }

    public Residential(int address, String name, int capacity, String description) {
        super(address, name, capacity, description);
        this.residentsPerApartment = new ArrayList<>();
    }

    public Residential(ResidentialPrintable residentialPrintable) {
        super();
        this.residentialPrintable = residentialPrintable;
        this.residentsPerApartment = new ArrayList<>();
    }

    public Residential(int address, int numberOfResidents, String name, int capacity, String description) {
        super(address, name, capacity, description);
        this.residentsPerApartment = new ArrayList<>();
        this.numberOfResidents = numberOfResidents;
        initializeApartmentsFromResidents();
    }

    public Residential(int address, int numberOfResidents, ResidentialPrintable residentialPrintable) {
        super(address);
        this.residentsPerApartment = new ArrayList<>();
        this.numberOfResidents = numberOfResidents;
        this.residentialPrintable = residentialPrintable;
        initializeApartmentsFromResidents();
    }

    public void setResidentialPrintable(ResidentialPrintable residentialPrintable) {
        this.residentialPrintable = residentialPrintable;
    }

    public int getNumberOfResidents() {
        return numberOfResidents;
    }

    public void setNumberOfResidents(int numberOfResidents) {
        this.numberOfResidents = numberOfResidents;
        initializeApartmentsFromResidents();
    }
    
    public int getNumberOfApartments() {
        return numberOfApartments;
    }
    
    public void setNumberOfApartments(int numberOfApartments) {
        this.numberOfApartments = numberOfApartments;
        initializeResidentsFromApartments();
    }
    
    public List<Integer> getResidentsPerApartment() {
        return new ArrayList<>(residentsPerApartment);
    }
    
    // Initialize apartments based on number of residents (max 3 per apartment)
    private void initializeApartmentsFromResidents() {
        if (numberOfResidents <= 0) {
            numberOfApartments = 0;
            residentsPerApartment.clear();
            return;
        }
        
        residentsPerApartment.clear();
        numberOfApartments = calculateNumberOfApartments();
        int remainingResidents = distributeResidentsAcrossApartments();
        handleRemainingResidents(remainingResidents);
        fillEmptyApartments();
        verifyAndUpdateResidentCount();
    }
    
    // Calculates the number of apartments based on number of residents and capacity.
     // Return calculated number of apartments

    private int calculateNumberOfApartments() {
        Random rand = new Random();
        int minApartments = (int) Math.ceil((double) numberOfResidents / MAX_RESIDENTS_PER_APARTMENT);
        int maxApartments = getCapacity() > 0 ? getCapacity() : minApartments * 2;
        return Math.max(minApartments, rand.nextInt(Math.max(1, maxApartments - minApartments + 1)) + minApartments);
    }
    

     // Distributes residents across apartments (max 3 per apartment).
     // Return number of remaining residents that couldn't be distributed

    private int distributeResidentsAcrossApartments() {
        Random rand = new Random();
        int remainingResidents = numberOfResidents;
        
        for (int i = 0; i < numberOfApartments && remainingResidents > 0; i++) {
            int residentsInApartment = calculateResidentsForApartment(i, remainingResidents, rand);
            residentsPerApartment.add(residentsInApartment);
            remainingResidents -= residentsInApartment;
        }
        
        return remainingResidents;
    }
    

     // Calculates how many residents should be assigned to a specific apartment.

    private int calculateResidentsForApartment(int apartmentIndex, int remainingResidents, Random rand) {
        if (apartmentIndex == numberOfApartments - 1) {
            // Last apartment gets all remaining residents (up to max)
            return Math.min(remainingResidents, MAX_RESIDENTS_PER_APARTMENT);
        } else {
            // Random distribution, but max 3 per apartment
            // Make sure we leave enough for remaining apartments (at least 1 per apartment if possible)
            int apartmentsLeft = numberOfApartments - apartmentIndex - 1;
            int minForRemaining = Math.max(0, apartmentsLeft - (remainingResidents - MAX_RESIDENTS_PER_APARTMENT));
            int maxForThis = Math.min(MAX_RESIDENTS_PER_APARTMENT, remainingResidents - minForRemaining);
            
            if (maxForThis <= 0) {
                return Math.min(1, remainingResidents);
            } else {
                return Math.min(rand.nextInt(maxForThis) + 1, remainingResidents);
            }
        }
    }
    

     // Handles any remaining residents that couldn't be distributed in the initial pass.
     // Distributes them across existing apartments up to the maximum capacity.

    private void handleRemainingResidents(int remainingResidents) {
        while (remainingResidents > 0 && !residentsPerApartment.isEmpty()) {
            for (int i = 0; i < residentsPerApartment.size() && remainingResidents > 0; i++) {
                int current = residentsPerApartment.get(i);
                if (current < MAX_RESIDENTS_PER_APARTMENT) {
                    int canAdd = Math.min(MAX_RESIDENTS_PER_APARTMENT - current, remainingResidents);
                    residentsPerApartment.set(i, current + canAdd);
                    remainingResidents -= canAdd;
                }
            }
            // Safety break to avoid infinite loop if all apartments are full
            if (remainingResidents > 0) {
                break;
            }
        }
    }
    
         // Fills any remaining empty apartments with 0 residents.

    private void fillEmptyApartments() {
        while (residentsPerApartment.size() < numberOfApartments) {
            residentsPerApartment.add(0);
        }
    }
    

     // Verifies the actual distribution and updates numberOfResidents to match.

    private void verifyAndUpdateResidentCount() {
        int actualTotal = 0;
        for (int residents : residentsPerApartment) {
            actualTotal += residents;
        }
        this.numberOfResidents = actualTotal;
    }
    
    // Initialize residents based on number of apartments (max 3 per apartment)
    private void initializeResidentsFromApartments() {
        if (numberOfApartments <= 0) {
            numberOfResidents = 0;
            residentsPerApartment.clear();
            return;
        }
        
        Random rand = new Random();
        residentsPerApartment.clear();
        numberOfResidents = 0;
        
        for (int i = 0; i < numberOfApartments; i++) {
            int residentsInApartment = rand.nextInt(MAX_RESIDENTS_PER_APARTMENT) + 1; // 1 to 3 residents
            residentsPerApartment.add(residentsInApartment);
            numberOfResidents += residentsInApartment;
        }
    }

    @Override
    public void initializeFromString(String data) {
        try {
            // Example: "address,numberOfApartments"
            // Format: address,numberOfApartments (residents will be calculated: 1-3 per apartment)
            String[] parts = data.split(",");
            if (parts.length >= 1) {
                setAddress(Integer.parseInt(parts[0].trim()));
            }
            if (parts.length >= 2) {
                int numberOfApartments = Integer.parseInt(parts[1].trim());
                // Always treat second value as number of apartments
                // Set capacity to match number of apartments for consistency
                setCapacity(numberOfApartments);
                // Set number of apartments (will automatically calculate residents: 1-3 per apartment)
                setNumberOfApartments(numberOfApartments);
            }
        } catch (Exception e) {
            System.err.println("Error initializing residential building from string: " + e.getMessage());
        }
    }

    @Override
    public void printInfo() {
        if (residentialPrintable != null) {
            residentialPrintable.printInfo(this);
        } else {
            System.out.println("Residential building: " + getName() + ", Address: " + address + 
                             ", Apartments: " + numberOfApartments + 
                             ", Residents: " + numberOfResidents);
        }
    }
}
