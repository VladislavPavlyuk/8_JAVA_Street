package com.company.model;

import com.company.enums.*;
import com.company.service.shop.ShopFullPrintable;
import com.company.Assert;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

// Test class for Shop

public class ShopTest {

    public void testShopCreation() {
        Shop shop = new Shop();
        Assert.assertNotNull(shop);
        Assert.assertNotNull("Should have departments list", shop.getDepartments());
    }

    public void testShopWithAddress() {
        Shop shop = new Shop(100);
        Assert.assertEquals(100, shop.getAddress());
    }

    public void testShopWithType() {
        Shop shop = new Shop(new ShopFullPrintable());
        shop.setShopType(ShopTypes.Supermarket);
        
        Assert.assertEquals(ShopTypes.Supermarket, shop.getShopType());
        Assert.assertNotNull("Should have departments", shop.getDepartments());
    }

    public void testShopDepartmentsFromType() {
        Shop shop = new Shop(new ShopFullPrintable());
        shop.setShopType(ShopTypes.Pharmacy);
        
        List<DepartmentType> departments = shop.getDepartments();
        Assert.assertNotNull("Should have departments", departments);
        Assert.assertFalse("Should have at least one department", departments.isEmpty());
        
        // Pharmacy should have PHARMACY department
        boolean hasPharmacy = false;
        for (DepartmentType dept : departments) {
            if (dept == DepartmentType.PHARMACY) {
                hasPharmacy = true;
                break;
            }
        }
        Assert.assertTrue("Pharmacy shop should have pharmacy department", hasPharmacy);
    }

    public void testShopHasDepartment() {
        Shop shop = new Shop(new ShopFullPrintable());
        shop.setShopType(ShopTypes.GroceryStore);
        
        // Manually add GROCERY department to test hasDepartment method
        shop.addDepartment(DepartmentType.GROCERY);
        
        // Grocery store should have GROCERY department
        Assert.assertTrue("Grocery store should have grocery department",
                  shop.hasDepartment(DepartmentType.GROCERY));
        
        // Verify it doesn't have a department it shouldn't have
        Assert.assertFalse("Grocery store should not have pharmacy department (unless added)",
                  shop.hasDepartment(DepartmentType.PHARMACY));
    }

    public void testShopDepartmentCount() {
        Shop shop = new Shop(new ShopFullPrintable());
        shop.setShopType(ShopTypes.Supermarket);
        
        List<DepartmentType> departments = shop.getDepartments();
        ShopTypes shopType = shop.getShopType();
        List<DepartmentType> appropriateDepts = shopType.getAppropriateDepartments();
        
        // Department count should be within range, but not exceed available appropriate departments
        int maxPossible = Math.min(shopType.getMaxDept(), appropriateDepts.size());
        int minExpected = Math.min(shopType.getMinDept(), appropriateDepts.size());
        
        Assert.assertTrue("Department count should be within range (got " + departments.size() + 
                  ", expected between " + minExpected + " and " + maxPossible + ")",
                  departments.size() >= minExpected && departments.size() <= maxPossible);
    }

    public void testShopInitializeFromString() {
        // Test initialization with shop type name (Bakery)
        Shop shop = new Shop(new ShopFullPrintable());
        shop.initializeFromString("200,Bakery");
        
        Assert.assertEquals(200, shop.getAddress());
        Assert.assertEquals("Shop type should be set to Bakery", ShopTypes.Bakery, shop.getShopType());
        // Should have departments initialized randomly (within minDept and maxDept)
        Assert.assertNotNull("Should have departments", shop.getDepartments());
        ShopTypes shopType = shop.getShopType();
        List<DepartmentType> appropriateDepts = shopType.getAppropriateDepartments();
        int maxPossible = Math.min(shopType.getMaxDept(), appropriateDepts.size());
        int minExpected = Math.min(shopType.getMinDept(), appropriateDepts.size());
        Assert.assertTrue("Should have departments within range", 
                  shop.getDepartments().size() >= minExpected && 
                  shop.getDepartments().size() <= maxPossible);
    }

    public void testShopInitializeFromStringWithEnumName() {
        // Test initialization with enum name (SUPERMARKET)
        Shop shop = new Shop(new ShopFullPrintable());
        shop.initializeFromString("300,SUPERMARKET");
        
        Assert.assertEquals(300, shop.getAddress());
        Assert.assertEquals("Shop type should be set to Supermarket", ShopTypes.Supermarket, shop.getShopType());
        // Should have departments initialized randomly
        Assert.assertNotNull("Should have departments", shop.getDepartments());
        ShopTypes shopType = shop.getShopType();
        List<DepartmentType> appropriateDepts = shopType.getAppropriateDepartments();
        int maxPossible = Math.min(shopType.getMaxDept(), appropriateDepts.size());
        int minExpected = Math.min(shopType.getMinDept(), appropriateDepts.size());
        Assert.assertTrue("Should have departments within range", 
                  shop.getDepartments().size() >= minExpected && 
                  shop.getDepartments().size() <= maxPossible);
    }

    public void testShopInitializeFromStringWithIndex() {
        // Test initialization with numeric index (0-based)
        // Index 0 should be Supermarket
        Shop shop = new Shop(new ShopFullPrintable());
        shop.initializeFromString("400,0");
        
        Assert.assertEquals(400, shop.getAddress());
        ShopTypes[] shopTypes = ShopTypes.values();
        Assert.assertEquals("Index 0 should be Supermarket", ShopTypes.Supermarket, shop.getShopType());
        // Should have departments initialized randomly
        Assert.assertNotNull("Should have departments", shop.getDepartments());
    }

    public void testShopInitializeFromStringWithoutShopType() {
        // Test initializeFromString with invalid shop type (will not set shop type)
        Shop shop = new Shop(new ShopFullPrintable());
        shop.initializeFromString("300,InvalidType");
        
        Assert.assertEquals(300, shop.getAddress());
        // Shop type should remain null if invalid type provided
        Assert.assertNull("Shop type should be null for invalid input", shop.getShopType());
        // Departments should be empty or minimal
        Assert.assertNotNull("Should have departments list", shop.getDepartments());
    }

    public void testShopInitializeFromStringOnlyAddress() {
        // Test initializeFromString with only address (no department count)
        Shop shop = new Shop(new ShopFullPrintable());
        shop.setShopType(ShopTypes.Bookstore);
        shop.initializeFromString("500");
        
        Assert.assertEquals(500, shop.getAddress());
        // Departments should remain unchanged (from shop type initialization)
        Assert.assertNotNull("Should have departments", shop.getDepartments());
    }

    public void testShopOnlyAppropriateDepartments() {
        Shop shop = new Shop(new ShopFullPrintable());
        shop.setShopType(ShopTypes.Bookstore);
        
        List<DepartmentType> departments = shop.getDepartments();
        List<DepartmentType> appropriateDepts = ShopTypes.Bookstore.getAppropriateDepartments();
        
        // All departments should be from appropriate list
        for (DepartmentType dept : departments) {
            Assert.assertTrue("Department " + dept + " should be appropriate for bookstore",
                      appropriateDepts.contains(dept));
        }
    }

    public void testShopMetadataUpdate() {
        // Test that shop metadata (name and description) are updated when shop type is set
        Shop shop = new Shop(new ShopFullPrintable());
        shop.setShopType(ShopTypes.Pharmacy);
        
        // Verify that shop name matches shop type
        Assert.assertEquals("Shop name should match shop type", 
                  ShopTypes.Pharmacy.getShopType(), shop.getName());
        
        // Verify that shop description matches shop type description
        Assert.assertEquals("Shop description should match shop type description",
                  ShopTypes.Pharmacy.getDescription(), shop.getShopDescription());
    }

    public void testShopDepartmentCountInRange() {
        // Test that department count is within minDept and maxDept range
        Shop shop = new Shop(new ShopFullPrintable());
        shop.setShopType(ShopTypes.Supermarket);
        
        List<DepartmentType> departments = shop.getDepartments();
        ShopTypes shopType = shop.getShopType();
        List<DepartmentType> appropriateDepts = shopType.getAppropriateDepartments();
        
        // Department count cannot exceed available appropriate departments
        int maxPossible = Math.min(shopType.getMaxDept(), appropriateDepts.size());
        Assert.assertTrue("Department count should not exceed max possible (got " + departments.size() + 
                  ", expected <= " + maxPossible + ")",
                  departments.size() <= maxPossible);
        
        // Department count should be at least minDept, but not more than available departments
        // If available departments < minDept, then we get all available departments
        int minExpected = Math.min(shopType.getMinDept(), appropriateDepts.size());
        Assert.assertTrue("Department count should be at least min(minDept, available) (got " + departments.size() + 
                  ", expected >= " + minExpected + ")",
                  departments.size() >= minExpected);
        
        // Verify that all departments are from appropriate list
        for (DepartmentType dept : departments) {
            Assert.assertTrue("Department " + dept + " should be from appropriate list",
                      appropriateDepts.contains(dept));
        }
    }

    public void testShopWithNullType() {
        // Test that shop can be created without shop type
        Shop shop = new Shop();
        Assert.assertNull("Shop type should be null initially", shop.getShopType());
        Assert.assertNotNull("Departments list should exist", shop.getDepartments());
        Assert.assertTrue("Departments list should be empty when shop type is null",
                  shop.getDepartments().isEmpty());
    }

    public void testShopDepartmentSelectionUniqueness() {
        // Test that selected departments are unique (no duplicates)
        Shop shop = new Shop(new ShopFullPrintable());
        shop.setShopType(ShopTypes.DepartmentStore);
        
        List<DepartmentType> departments = shop.getDepartments();
        Set<DepartmentType> uniqueDepartments = new HashSet<>(departments);
        
        // All departments should be unique
        Assert.assertEquals("All departments should be unique",
                  departments.size(), uniqueDepartments.size());
    }

    public void testShopMinDeptRespected() {
        // Test shops with minDept = 1 to ensure at least one department is selected
        Shop shop = new Shop(new ShopFullPrintable());
        shop.setShopType(ShopTypes.JewelryStore); // minDept = 1, maxDept = 2
        
        List<DepartmentType> departments = shop.getDepartments();
        ShopTypes shopType = shop.getShopType();
        
        // Should have at least minDept departments
        Assert.assertTrue("Should have at least " + shopType.getMinDept() + " department(s)",
                  departments.size() >= shopType.getMinDept());
    }

    public void testShopMaxDeptRespected() {
        // Test shops with small maxDept to ensure it's respected
        Shop shop = new Shop(new ShopFullPrintable());
        shop.setShopType(ShopTypes.Kiosk); // minDept = 1, maxDept = 1, has 2 appropriate depts
        
        List<DepartmentType> departments = shop.getDepartments();
        ShopTypes shopType = shop.getShopType();
        List<DepartmentType> appropriateDepts = shopType.getAppropriateDepartments();
        
        // Should not exceed maxDept or available departments
        int maxPossible = Math.min(shopType.getMaxDept(), appropriateDepts.size());
        Assert.assertTrue("Should not exceed max possible departments",
                  departments.size() <= maxPossible);
    }
}

