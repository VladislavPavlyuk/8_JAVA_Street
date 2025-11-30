package com.company.model;

import com.company.service.residential.ResidentialFullPrintable;
import com.company.Assert;

// Test class for Residential building

public class ResidentialTest {

    public void testResidentialCreation() {
        Residential residential = new Residential();
        Assert.assertNotNull(residential);
        Assert.assertEquals(0, residential.getAddress());
    }

    public void testResidentialWithAddress() {
        Residential residential = new Residential(100);
        Assert.assertEquals(100, residential.getAddress());
    }

    public void testMaxResidentsPerApartment() {
        Residential residential = new Residential();
        residential.setNumberOfResidents(100);
        
        // Check that no apartment has more than 3 residents
        int totalResidents = 0;
        for (int residents : residential.getResidentsPerApartment()) {
            Assert.assertTrue("Each apartment should have at most 3 residents", 
                       residents <= 3);
            totalResidents += residents;
        }
        
        // Verify that all residents are distributed and numberOfResidents matches
        Assert.assertEquals("All 100 residents must be distributed", 100, totalResidents);
        Assert.assertEquals("numberOfResidents should match actual distribution", 
                    100, residential.getNumberOfResidents());
    }

    public void testResidentsDistribution() {
        Residential residential = new Residential();
        residential.setNumberOfResidents(30);
        
        int totalResidents = 0;
        for (int residents : residential.getResidentsPerApartment()) {
            Assert.assertTrue("Each apartment should have at most 3 residents",
                       residents <= 3);
            totalResidents += residents;
        }
        
        // Verify that all residents are distributed
        Assert.assertEquals("Total residents should match", 30, totalResidents);
        // Verify that numberOfResidents matches actual distribution (after verifyAndUpdateResidentCount)
        Assert.assertEquals("numberOfResidents should match actual distribution", 
                    30, residential.getNumberOfResidents());
    }

    public void testApartmentsFromResidents() {
        Residential residential = new Residential();
        residential.setNumberOfResidents(15);
        
        // Should have at least ceil(15/3) = 5 apartments
        Assert.assertTrue("Should have at least 5 apartments for 15 residents",
                   residential.getNumberOfApartments() >= 5);
        
        // Check that residents are distributed correctly
        int totalResidents = 0;
        int apartmentsWithResidents = 0;
        for (int residents : residential.getResidentsPerApartment()) {
            Assert.assertTrue("Each apartment should have at most 3 residents",
                       residents <= 3);
            totalResidents += residents;
            if (residents > 0) {
                apartmentsWithResidents++;
            }
        }
        
        // The total should match the number of residents set
        // This is the most important check - all residents must be accounted for
        Assert.assertEquals("All 15 residents must be distributed across apartments",
                    15, totalResidents);
        
        // Verify that numberOfResidents matches
        Assert.assertEquals("numberOfResidents should be 15", 
                    15, residential.getNumberOfResidents());
        
        // Verify we have reasonable number of occupied apartments (at least 5 for 15 residents)
        Assert.assertTrue("Should have at least 5 apartments with residents",
                    apartmentsWithResidents >= 5);
    }

    public void testApartmentsFromApartments() {
        Residential residential = new Residential();
        residential.setNumberOfApartments(10);
        
        Assert.assertEquals(10, residential.getNumberOfApartments());
        
        // Check that each apartment has 1-3 residents
        for (int residents : residential.getResidentsPerApartment()) {
            Assert.assertTrue("Each apartment should have 1-3 residents",
                       residents >= 1 && residents <= 3);
        }
        
        // Total residents should be between 10 and 30
        int totalResidents = residential.getNumberOfResidents();
        Assert.assertTrue("Total residents should be between 10 and 30",
                   totalResidents >= 10 && totalResidents <= 30);
    }

    public void testInitializeFromString() {
        Residential residential = new Residential();
        residential.initializeFromString("123,50");
        
        Assert.assertEquals(123, residential.getAddress());
        // Should treat 50 as number of apartments
        // Apartments will have residents distributed (1-3 per apartment)
        Assert.assertEquals("Should have exactly 50 apartments", 50, residential.getNumberOfApartments());
        Assert.assertTrue("Should have residents (1-3 per apartment)", 
                  residential.getNumberOfResidents() >= 50 && 
                  residential.getNumberOfResidents() <= 150);
    }

    public void testInitializeFromStringWithApartments() {
        Residential residential = new Residential();
        residential.initializeFromString("456,20");
        
        Assert.assertEquals(456, residential.getAddress());
        // Should treat 20 as number of apartments
        Assert.assertEquals(20, residential.getNumberOfApartments());
        // Should have residents calculated (1-3 per apartment)
        Assert.assertTrue("Should have residents (1-3 per apartment)",
                  residential.getNumberOfResidents() >= 20 && 
                  residential.getNumberOfResidents() <= 60);
    }

    public void testInitializeFromStringLargeNumberOfApartments() {
        // Test with 100 apartments (previously would be treated as residents)
        Residential residential = new Residential();
        residential.initializeFromString("172,100");
        
        Assert.assertEquals(172, residential.getAddress());
        // Should treat 100 as number of apartments (not residents)
        Assert.assertEquals("Should have exactly 100 apartments", 100, residential.getNumberOfApartments());
        // Should have residents calculated (1-3 per apartment = 100 to 300 residents)
        Assert.assertTrue("Should have residents (1-3 per apartment)",
                  residential.getNumberOfResidents() >= 100 && 
                  residential.getNumberOfResidents() <= 300);
    }

    public void testZeroResidents() {
        Residential residential = new Residential();
        residential.setNumberOfResidents(0);
        
        Assert.assertEquals(0, residential.getNumberOfResidents());
        Assert.assertEquals(0, residential.getNumberOfApartments());
        Assert.assertTrue(residential.getResidentsPerApartment().isEmpty());
    }

    public void testZeroApartments() {
        Residential residential = new Residential();
        residential.setNumberOfApartments(0);
        
        Assert.assertEquals(0, residential.getNumberOfApartments());
        Assert.assertEquals(0, residential.getNumberOfResidents());
        Assert.assertTrue(residential.getResidentsPerApartment().isEmpty());
    }

    public void testSingleResident() {
        Residential residential = new Residential();
        residential.setNumberOfResidents(1);
        
        Assert.assertEquals("Should have 1 resident", 1, residential.getNumberOfResidents());
        Assert.assertTrue("Should have at least 1 apartment", 
                   residential.getNumberOfApartments() >= 1);
        
        int totalResidents = 0;
        for (int residents : residential.getResidentsPerApartment()) {
            Assert.assertTrue("Each apartment should have at most 3 residents",
                       residents <= 3);
            totalResidents += residents;
        }
        
        Assert.assertEquals("All 1 resident must be distributed", 1, totalResidents);
        Assert.assertEquals("numberOfResidents should match", 1, residential.getNumberOfResidents());
    }

    public void testExactMultipleOfThree() {
        // Test with 9 residents (exactly 3 apartments with 3 residents each)
        Residential residential = new Residential();
        residential.setNumberOfResidents(9);
        
        Assert.assertTrue("Should have at least 3 apartments for 9 residents",
                   residential.getNumberOfApartments() >= 3);
        
        int totalResidents = 0;
        for (int residents : residential.getResidentsPerApartment()) {
            Assert.assertTrue("Each apartment should have at most 3 residents",
                       residents <= 3);
            totalResidents += residents;
        }
        
        Assert.assertEquals("All 9 residents must be distributed", 9, totalResidents);
        Assert.assertEquals("numberOfResidents should match", 9, residential.getNumberOfResidents());
    }

    public void testResidentsNotDivisibleByThree() {
        // Test with 7 residents (should require at least 3 apartments: 3+3+1)
        Residential residential = new Residential();
        residential.setNumberOfResidents(7);
        
        Assert.assertTrue("Should have at least 3 apartments for 7 residents",
                   residential.getNumberOfApartments() >= 3);
        
        int totalResidents = 0;
        for (int residents : residential.getResidentsPerApartment()) {
            Assert.assertTrue("Each apartment should have at most 3 residents",
                       residents <= 3);
            totalResidents += residents;
        }
        
        Assert.assertEquals("All 7 residents must be distributed", 7, totalResidents);
        Assert.assertEquals("numberOfResidents should match", 7, residential.getNumberOfResidents());
    }

    public void testResidentsWithCapacityLimit() {
        // Test with capacity limit that restricts number of apartments
        Residential residential = new Residential(1, "Test Building", 5, "Test Description");
        residential.setNumberOfResidents(20);
        
        // With capacity of 5, maxApartments = 5
        // But we need at least ceil(20/3) = 7 apartments (minApartments)
        // Since minApartments > maxApartments, we get minApartments = 7
        // Capacity cannot limit below the minimum required
        Assert.assertTrue("Should have at least 7 apartments for 20 residents (capacity cannot limit below minimum)",
                   residential.getNumberOfApartments() >= 7);
        
        int totalResidents = 0;
        for (int residents : residential.getResidentsPerApartment()) {
            Assert.assertTrue("Each apartment should have at most 3 residents",
                       residents <= 3);
            totalResidents += residents;
        }
        
        Assert.assertEquals("All 20 residents must be distributed", 20, totalResidents);
        Assert.assertEquals("numberOfResidents should match", 20, residential.getNumberOfResidents());
    }

    public void testResidentsWithLargeCapacity() {
        // Test with capacity larger than minimum required
        Residential residential = new Residential(1, "Test Building", 20, "Test Description");
        residential.setNumberOfResidents(15);
        
        // With capacity of 20, maxApartments = 20
        // We need at least ceil(15/3) = 5 apartments (minApartments)
        // Result should be between 5 and 20 apartments
        Assert.assertTrue("Should have at least 5 apartments for 15 residents",
                   residential.getNumberOfApartments() >= 5);
        Assert.assertTrue("Should have at most 20 apartments (capacity limit)",
                   residential.getNumberOfApartments() <= 20);
        
        int totalResidents = 0;
        for (int residents : residential.getResidentsPerApartment()) {
            Assert.assertTrue("Each apartment should have at most 3 residents",
                       residents <= 3);
            totalResidents += residents;
        }
        
        Assert.assertEquals("All 15 residents must be distributed", 15, totalResidents);
        Assert.assertEquals("numberOfResidents should match", 15, residential.getNumberOfResidents());
    }
}

