package com.company.model;

import com.company.enums.DepartmentType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

// Street
public class Street {
    private String streetName;
    private List<IBuilding> buildings;

    public Street() {
        this.buildings = new ArrayList<>();
        this.streetName = "Street";
    }

    public Street(String streetName) {
        this.streetName = streetName;
        this.buildings = new ArrayList<>();
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public List<IBuilding> getBuildings() {
        return buildings;
    }

    public void addBuilding(IBuilding building) {
        buildings.add(building);
    }

    public void removeBuilding(IBuilding building) {
        buildings.remove(building);
    }

    public void removeBuildingByAddress(int address) {
        buildings.removeIf(b -> b.getAddress() == address);
    }

    public void printStreetInfo() {
        System.out.println("Street: " + streetName);
        System.out.println("Total buildings: " + buildings.size());
        System.out.println("Total population: " + getTotalPopulation());
        System.out.println("Residential buildings: " + getResidentialBuildingCount());
        System.out.println("Total apartments: " + getTotalApartments());
        for (IBuilding building : buildings) {
            building.printInfo();
            System.out.println();
        }
    }
    
    public int getTotalPopulation() {
        int total = 0;
        for (IBuilding building : buildings) {
            if (building instanceof Residential) {
                total += ((Residential) building).getNumberOfResidents();
            }
        }
        return total;
    }
    
    public int getResidentialBuildingCount() {
        int count = 0;
        for (IBuilding building : buildings) {
            if (building instanceof Residential) {
                count++;
            }
        }
        return count;
    }
    
    public int getTotalApartments() {
        int total = 0;
        for (IBuilding building : buildings) {
            if (building instanceof Residential) {
                total += ((Residential) building).getNumberOfApartments();
            }
        }
        return total;
    }
    
    public int getResidentsInBuilding(int address) {
        for (IBuilding building : buildings) {
            if (building instanceof Residential && building.getAddress() == address) {
                return ((Residential) building).getNumberOfResidents();
            }
        }
        return 0;
    }

    public List<Shop> findShopsNearResidentialBuilding(Residential residentialBuilding, int range, DepartmentType departmentType) {
        List<Shop> nearbyShops = new ArrayList<>();
        
        if (residentialBuilding == null) {
            return nearbyShops;
        }

        int residentialAddress = residentialBuilding.getAddress();
        
        for (IBuilding building : buildings) {
            if (building instanceof Shop) {
                Shop shop = (Shop) building;
                int shopAddress = shop.getAddress();
                int distance = Math.abs(shopAddress - residentialAddress);
                
                if (distance <= range && shop.hasDepartment(departmentType)) {
                    nearbyShops.add(shop);
                }
            }
        }
        
        return nearbyShops;
    }

    public Residential getRandomResidentialBuilding() {
        List<Residential> residentialBuildings = new ArrayList<>();
        for (IBuilding building : buildings) {
            if (building instanceof Residential) {
                residentialBuildings.add((Residential) building);
            }
        }
        
        if (residentialBuildings.isEmpty()) {
            return null;
        }
        
        Random rand = new Random();
        return residentialBuildings.get(rand.nextInt(residentialBuildings.size()));
    }
    
    /**
     * Gets a residential building by its address.
     * @param address address of the residential building
     * @return Residential building with the specified address, or null if not found or not a residential building
     */
    public Residential getResidentialBuildingByAddress(int address) {
        for (IBuilding building : buildings) {
            if (building instanceof Residential && building.getAddress() == address) {
                return (Residential) building;
            }
        }
        return null;
    }

    public int getBuildingCount() {
        return buildings.size();
    }
    
    /**
     * Checks if an address is already occupied by another building.
     * @param address address to check
     * @return true if address is occupied, false otherwise
     */
    public boolean isAddressOccupied(int address) {
        for (IBuilding building : buildings) {
            if (building.getAddress() == address) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Gets the next available address (next after the maximum address).
     * @return next available address (max address + 1, or 1 if no buildings exist)
     */
    public int getNextAvailableAddress() {
        if (buildings.isEmpty()) {
            return 1;
        }
        int maxAddress = buildings.stream()
            .mapToInt(IBuilding::getAddress)
            .max()
            .orElse(0);
        return maxAddress + 1;
    }
    
    // Sorts buildings by their address numbers in ascending order.

    public void sortBuildingsByAddress() {
        Collections.sort(buildings, new Comparator<IBuilding>() {
            @Override
            public int compare(IBuilding b1, IBuilding b2) {
                return Integer.compare(b1.getAddress(), b2.getAddress());
            }
        });
    }
}
