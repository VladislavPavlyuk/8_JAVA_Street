package com.company.utils;

import com.company.model.*;
import com.company.enums.*;
import com.company.Assert;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

// Test class for StreetFactory

public class StreetFactoryTest {

    public void testCreateDefaultStreet() {
        Street street = StreetFactory.createDefaultStreet();
        
        Assert.assertNotNull(street);
        Assert.assertEquals("Main Street", street.getStreetName());
        Assert.assertTrue("Street should have buildings", street.getBuildingCount() > 0);
    }

    public void testCreateStreet() {
        Street street = StreetFactory.createStreet("Test Street", 10);
        
        Assert.assertNotNull(street);
        Assert.assertEquals("Test Street", street.getStreetName());
        Assert.assertTrue("Street should have buildings", street.getBuildingCount() > 0);
    }

    public void testStreetHasResidentialBuildings() {
        Street street = StreetFactory.createStreet("Test Street", 5);
        
        int residentialCount = street.getResidentialBuildingCount();
        Assert.assertEquals("Should have exactly 5 residential buildings", 5, residentialCount);
    }

    public void testUniqueAddresses() {
        Street street = StreetFactory.createStreet("Test Street", 10);
        
        Set<Integer> addresses = new HashSet<>();
        for (IBuilding building : street.getBuildings()) {
            int address = building.getAddress();
            Assert.assertFalse("Address should be unique: " + address, 
                       addresses.contains(address));
            addresses.add(address);
        }
    }

    public void testAddressesWithinRange() {
        Street street = StreetFactory.createStreet("Test Street", 5);
        
        // Calculate expected max address
        int residentialCount = 5;
        int totalPopulation = street.getTotalPopulation();
        int populationUnits = Math.max(1, (int) Math.ceil((double) totalPopulation / 10000));
        
        // Estimate institutions (schools, clinics, shops)
        int estimatedInstitutions = populationUnits * 30; // Rough estimate
        int expectedMaxAddress = residentialCount + estimatedInstitutions;
        
        // Check all addresses are within reasonable range
        for (IBuilding building : street.getBuildings()) {
            Assert.assertTrue("Address should be positive: " + building.getAddress(),
                      building.getAddress() > 0);
            Assert.assertTrue("Address should not exceed reasonable maximum: " + building.getAddress(),
                      building.getAddress() <= expectedMaxAddress * 2); // Allow some margin
        }
    }

    public void testBuildingsSortedByAddress() {
        Street street = StreetFactory.createStreet("Test Street", 5);
        
        List<IBuilding> buildings = street.getBuildings();
        
        // Verify buildings are sorted by address
        for (int i = 1; i < buildings.size(); i++) {
            Assert.assertTrue("Buildings should be sorted by address",
                      buildings.get(i-1).getAddress() <= buildings.get(i).getAddress());
        }
    }

    public void testPopulationBasedInstitutions() {
        Street street = StreetFactory.createStreet("Test Street", 20);
        
        int totalPopulation = street.getTotalPopulation();
        int populationUnits = Math.max(1, (int) Math.ceil((double) totalPopulation / 10000));
        
        // Should have schools (7-10 per unit)
        int schoolCount = 0;
        int preschoolCount = 0;
        int primarySchoolCount = 0;
        int secondarySchoolCount = 0;
        for (IBuilding building : street.getBuildings()) {
            if (building instanceof School) {
                schoolCount++;
                School school = (School) building;
                if (school.getAccreditationLevel() == AccreditationLevel.PRE_SCHOOL) {
                    preschoolCount++;
                } else if (school.getAccreditationLevel() == AccreditationLevel.ELEMENTARY) {
                    primarySchoolCount++;
                } else if (school.getAccreditationLevel() == AccreditationLevel.HIGH) {
                    secondarySchoolCount++;
                }
            }
        }
        Assert.assertTrue("Should have schools", schoolCount > 0);
        Assert.assertTrue("Should have preschools", preschoolCount > 0);
        Assert.assertTrue("Should have primary schools", primarySchoolCount > 0);
        Assert.assertTrue("Should have secondary schools", secondarySchoolCount > 0);
        
        // Should have health centers (1 per unit)
        int healthCenterCount = 0;
        for (IBuilding building : street.getBuildings()) {
            if (building instanceof Hospital) {
                healthCenterCount++;
            }
        }
        Assert.assertTrue("Should have health centers", healthCenterCount >= populationUnits);
        
        // Should have shops (15-30 per unit)
        int shopCount = 0;
        int supermarketCount = 0;
        for (IBuilding building : street.getBuildings()) {
            if (building instanceof Shop) {
                shopCount++;
                Shop shop = (Shop) building;
                if (shop.getShopType() == ShopTypes.Supermarket) {
                    supermarketCount++;
                }
            }
        }
        Assert.assertTrue("Should have shops", shopCount >= populationUnits * 15);
        Assert.assertTrue("Should have at least one supermarket per population unit", 
                  supermarketCount >= populationUnits);
    }

    public void testStreetHasAllBuildingTypes() {
        // Test that street contains all expected building types
        Street street = StreetFactory.createStreet("Test Street", 10);
        
        boolean hasResidential = false;
        boolean hasSchool = false;
        boolean hasHospital = false;
        boolean hasShop = false;
        
        for (IBuilding building : street.getBuildings()) {
            if (building instanceof Residential) {
                hasResidential = true;
            } else if (building instanceof School) {
                hasSchool = true;
            } else if (building instanceof Hospital) {
                hasHospital = true;
            } else if (building instanceof Shop) {
                hasShop = true;
            }
        }
        
        Assert.assertTrue("Street should have residential buildings", hasResidential);
        Assert.assertTrue("Street should have schools", hasSchool);
        Assert.assertTrue("Street should have hospitals/clinics", hasHospital);
        Assert.assertTrue("Street should have shops", hasShop);
    }

    public void testCreateResidential() {
        Set<Integer> usedAddresses = new HashSet<>();
        Residential residential = StreetFactory.createResidential(usedAddresses);
        
        Assert.assertNotNull(residential);
        Assert.assertTrue("Should have address", residential.getAddress() > 0);
        Assert.assertTrue("Should have apartments", residential.getNumberOfApartments() > 0);
        Assert.assertTrue("Should have residents", residential.getNumberOfResidents() > 0);
    }

    public void testCreateSchool() {
        Set<Integer> usedAddresses = new HashSet<>();
        School school = StreetFactory.createSchool(usedAddresses);
        
        Assert.assertNotNull(school);
        Assert.assertTrue("Should have address", school.getAddress() > 0);
        Assert.assertNotNull("Should have accreditation level", school.getAccreditationLevel());
    }

    public void testCreateSchoolWithLevel() {
        Set<Integer> usedAddresses = new HashSet<>();
        School school = StreetFactory.createSchoolWithLevel(
            usedAddresses, AccreditationLevel.PRE_SCHOOL);
        
        Assert.assertNotNull(school);
        Assert.assertEquals(AccreditationLevel.PRE_SCHOOL, school.getAccreditationLevel());
        Assert.assertTrue("Should have address", school.getAddress() > 0);
    }

    public void testCreateShop() {
        Set<Integer> usedAddresses = new HashSet<>();
        Shop shop = StreetFactory.createShop(usedAddresses);
        
        Assert.assertNotNull(shop);
        Assert.assertTrue("Should have address", shop.getAddress() > 0);
        Assert.assertNotNull("Should have shop type", shop.getShopType());
    }

    public void testCreateShopWithType() {
        Set<Integer> usedAddresses = new HashSet<>();
        Shop shop = StreetFactory.createShopWithType(usedAddresses, ShopTypes.Supermarket);
        
        Assert.assertNotNull(shop);
        Assert.assertEquals(ShopTypes.Supermarket, shop.getShopType());
        Assert.assertTrue("Should have address", shop.getAddress() > 0);
    }

    public void testCreateHospital() {
        Set<Integer> usedAddresses = new HashSet<>();
        Hospital hospital = StreetFactory.createHospital(usedAddresses);
        
        Assert.assertNotNull(hospital);
        Assert.assertTrue("Should have address", hospital.getAddress() > 0);
        Assert.assertTrue("Should have capacity", hospital.getCapacity() > 0);
    }

    public void testCreateHealthCenter() {
        Set<Integer> usedAddresses = new HashSet<>();
        Hospital clinic = StreetFactory.createHealthCenter(usedAddresses);
        
        Assert.assertNotNull(clinic);
        Assert.assertTrue("Should have address", clinic.getAddress() > 0);
        Assert.assertTrue("Should have capacity", clinic.getCapacity() > 0);
        Assert.assertTrue("Clinic capacity should be reasonable (20-50)", 
                  clinic.getCapacity() >= 20 && clinic.getCapacity() <= 50);
    }

    public void testCreateRandomBuilding() {
        Set<Integer> usedAddresses = new HashSet<>();
        IBuilding building = StreetFactory.createRandomBuilding(usedAddresses);
        
        Assert.assertNotNull(building);
        Assert.assertTrue("Should be instance of known building type",
                  building instanceof Residential ||
                  building instanceof School ||
                  building instanceof Hospital ||
                  building instanceof Shop);
    }
}



