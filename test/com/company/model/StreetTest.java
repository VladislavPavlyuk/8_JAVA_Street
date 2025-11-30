package com.company.model;

import com.company.enums.DepartmentType;
import com.company.service.hospital.HospitalFullPrintable;
import com.company.service.residential.ResidentialFullPrintable;
import com.company.service.school.SchoolFullPrintable;
import com.company.service.shop.ShopFullPrintable;
import com.company.enums.ShopTypes;
import com.company.enums.AccreditationLevel;
import com.company.Assert;

// Test class for Street

public class StreetTest {

    public void testStreetCreation() {
        Street street = new Street();
        Assert.assertNotNull(street);
        Assert.assertEquals("Street", street.getStreetName());
        Assert.assertEquals(0, street.getBuildingCount());
    }

    public void testStreetWithName() {
        Street street = new Street("Main Street");
        Assert.assertEquals("Main Street", street.getStreetName());
        Assert.assertEquals(0, street.getBuildingCount());
    }

    public void testAddBuilding() {
        Street street = new Street("Test Street");
        Residential residential = new Residential(new ResidentialFullPrintable());
        residential.setAddress(1);
        
        street.addBuilding(residential);
        
        Assert.assertEquals(1, street.getBuildingCount());
        Assert.assertTrue(street.getBuildings().contains(residential));
    }

    public void testRemoveBuilding() {
        Street street = new Street("Test Street");
        Residential residential = new Residential(new ResidentialFullPrintable());
        residential.setAddress(1);
        
        street.addBuilding(residential);
        street.removeBuilding(residential);
        
        Assert.assertEquals(0, street.getBuildingCount());
    }

    public void testRemoveBuildingByAddress() {
        Street street = new Street("Test Street");
        Residential residential = new Residential(new ResidentialFullPrintable());
        residential.setAddress(100);
        
        street.addBuilding(residential);
        street.removeBuildingByAddress(100);
        
        Assert.assertEquals(0, street.getBuildingCount());
    }

    public void testTotalPopulation() {
        Street street = new Street("Test Street");
        
        Residential residential1 = new Residential(new ResidentialFullPrintable());
        residential1.setAddress(1);
        residential1.setNumberOfResidents(10);
        street.addBuilding(residential1);
        
        Residential residential2 = new Residential(new ResidentialFullPrintable());
        residential2.setAddress(2);
        residential2.setNumberOfResidents(20);
        street.addBuilding(residential2);
        
        // Add non-residential building (should not count)
        Hospital hospital = new Hospital(new HospitalFullPrintable());
        hospital.setAddress(3);
        street.addBuilding(hospital);
        
        Assert.assertEquals(30, street.getTotalPopulation());
    }

    public void testResidentialBuildingCount() {
        Street street = new Street("Test Street");
        
        Residential residential1 = new Residential(new ResidentialFullPrintable());
        residential1.setAddress(1);
        street.addBuilding(residential1);
        
        Residential residential2 = new Residential(new ResidentialFullPrintable());
        residential2.setAddress(2);
        street.addBuilding(residential2);
        
        Hospital hospital = new Hospital(new HospitalFullPrintable());
        hospital.setAddress(3);
        street.addBuilding(hospital);
        
        Assert.assertEquals(2, street.getResidentialBuildingCount());
    }

    public void testTotalApartments() {
        Street street = new Street("Test Street");
        
        Residential residential1 = new Residential(new ResidentialFullPrintable());
        residential1.setAddress(1);
        residential1.setNumberOfApartments(10);
        street.addBuilding(residential1);
        
        Residential residential2 = new Residential(new ResidentialFullPrintable());
        residential2.setAddress(2);
        residential2.setNumberOfApartments(15);
        street.addBuilding(residential2);
        
        Assert.assertEquals(25, street.getTotalApartments());
    }

    public void testGetResidentsInBuilding() {
        Street street = new Street("Test Street");
        
        Residential residential = new Residential(new ResidentialFullPrintable());
        residential.setAddress(100);
        residential.setNumberOfResidents(25);
        street.addBuilding(residential);
        
        Assert.assertEquals(25, street.getResidentsInBuilding(100));
        Assert.assertEquals(0, street.getResidentsInBuilding(999)); // Non-existent address
    }

    public void testFindShopsNearResidentialBuilding() {
        Street street = new Street("Test Street");
        
        // Create residential building
        Residential residential = new Residential(new ResidentialFullPrintable());
        residential.setAddress(100);
        street.addBuilding(residential);
        
        // Create shop with grocery department at address 105 (distance = 5)
        // Manually add GROCERY department to guarantee it's present
        Shop shop1 = new Shop(new ShopFullPrintable());
        shop1.setAddress(105);
        shop1.setShopType(ShopTypes.GroceryStore);
        shop1.addDepartment(DepartmentType.GROCERY); // Ensure GROCERY is present
        street.addBuilding(shop1);
        
        // Create shop without grocery department at address 102 (distance = 2)
        Shop shop2 = new Shop(new ShopFullPrintable());
        shop2.setAddress(102);
        shop2.setShopType(ShopTypes.Bookstore);
        street.addBuilding(shop2);
        
        // Find shops with grocery department within range 10
        java.util.List<Shop> nearbyShops = street.findShopsNearResidentialBuilding(
            residential, 10, DepartmentType.GROCERY);
        
        Assert.assertTrue("Should find at least one shop with grocery department", 
                  nearbyShops.size() > 0);
        
        // Check that found shops have the required department
        for (Shop shop : nearbyShops) {
            Assert.assertTrue("Shop should have grocery department", 
                      shop.hasDepartment(DepartmentType.GROCERY));
            int distance = Math.abs(shop.getAddress() - residential.getAddress());
            Assert.assertTrue("Shop should be within range", distance <= 10);
        }
    }

    public void testGetRandomResidentialBuilding() {
        Street street = new Street("Test Street");
        
        Residential residential1 = new Residential(new ResidentialFullPrintable());
        residential1.setAddress(1);
        street.addBuilding(residential1);
        
        Residential residential2 = new Residential(new ResidentialFullPrintable());
        residential2.setAddress(2);
        street.addBuilding(residential2);
        
        // Should return one of the residential buildings
        Residential random = street.getRandomResidentialBuilding();
        Assert.assertNotNull(random);
        Assert.assertTrue(random == residential1 || random == residential2);
    }

    public void testGetRandomResidentialBuildingEmpty() {
        Street street = new Street("Test Street");
        
        // No residential buildings
        Residential random = street.getRandomResidentialBuilding();
        Assert.assertNull(random);
    }

    public void testSortBuildingsByAddress() {
        Street street = new Street("Test Street");
        
        // Add buildings in random order
        Residential residential1 = new Residential(new ResidentialFullPrintable());
        residential1.setAddress(50);
        street.addBuilding(residential1);
        
        Hospital hospital = new Hospital(new HospitalFullPrintable());
        hospital.setAddress(10);
        street.addBuilding(hospital);
        
        School school = new School(new SchoolFullPrintable());
        school.setAddress(30);
        street.addBuilding(school);
        
        Shop shop = new Shop(new ShopFullPrintable());
        shop.setAddress(20);
        street.addBuilding(shop);
        
        // Sort buildings
        street.sortBuildingsByAddress();
        
        // Verify sorted order
        java.util.List<IBuilding> buildings = street.getBuildings();
        Assert.assertEquals(10, buildings.get(0).getAddress());
        Assert.assertEquals(20, buildings.get(1).getAddress());
        Assert.assertEquals(30, buildings.get(2).getAddress());
        Assert.assertEquals(50, buildings.get(3).getAddress());
    }
}


