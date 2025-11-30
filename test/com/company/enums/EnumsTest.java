package com.company.enums;

import com.company.Assert;

import java.util.List;

// Test class for Enums

public class EnumsTest {

    public void testAccreditationLevelValues() {
        AccreditationLevel[] levels = AccreditationLevel.values();
        Assert.assertTrue("Should have accreditation levels", levels.length > 0);
        
        for (AccreditationLevel level : levels) {
            Assert.assertNotNull("Level should have name", level.getName());
            Assert.assertTrue("Min students should be positive", level.getMinStudents() > 0);
            Assert.assertTrue("Max students should be >= min", 
                      level.getMaxStudents() >= level.getMinStudents());
            Assert.assertNotNull("Level should have description", level.getDescription());
            Assert.assertFalse("Description should not be empty", level.getDescription().isEmpty());
        }
    }

    public void testAccreditationLevelPreSchool() {
        AccreditationLevel level = AccreditationLevel.PRE_SCHOOL;
        Assert.assertEquals("Preschool", level.getName());
        Assert.assertTrue("Preschool should have reasonable student range",
                  level.getMinStudents() > 0 && level.getMaxStudents() > level.getMinStudents());
        Assert.assertNotNull("Preschool should have description", level.getDescription());
        Assert.assertTrue("Description should contain relevant information",
                  level.getDescription().toLowerCase().contains("child") || 
                  level.getDescription().toLowerCase().contains("education"));
    }

    public void testDepartmentTypeValues() {
        DepartmentType[] types = DepartmentType.values();
        Assert.assertTrue("Should have department types", types.length > 0);
        
        for (DepartmentType type : types) {
            Assert.assertNotNull("Type should have name", type.getName());
        }
    }

    public void testShopTypesValues() {
        ShopTypes[] shopTypes = ShopTypes.values();
        Assert.assertTrue("Should have shop types", shopTypes.length > 0);
        
        for (ShopTypes shopType : shopTypes) {
            Assert.assertNotNull("Shop type should have name", shopType.getShopType());
            Assert.assertTrue("Min departments should be >= 0", shopType.getMinDept() >= 0);
            Assert.assertTrue("Max departments should be >= min", 
                      shopType.getMaxDept() >= shopType.getMinDept());
            Assert.assertNotNull("Should have description", shopType.getDescription());
        }
    }

    public void testShopTypesGetAppropriateDepartments() {
        ShopTypes supermarket = ShopTypes.Supermarket;
        List<DepartmentType> departments = supermarket.getAppropriateDepartments();
        
        Assert.assertNotNull("Should return department list", departments);
        Assert.assertFalse("Supermarket should have appropriate departments", departments.isEmpty());
        
        // Supermarket description mentions "food", "grocery", "household products"
        // So it should include GROCERY and HOUSEHOLD
        boolean hasGrocery = departments.contains(DepartmentType.GROCERY);
        boolean hasHousehold = departments.contains(DepartmentType.HOUSEHOLD);
        Assert.assertTrue("Supermarket should have grocery or household departments",
                  hasGrocery || hasHousehold);
    }

    public void testShopTypesPharmacy() {
        ShopTypes pharmacy = ShopTypes.Pharmacy;
        List<DepartmentType> departments = pharmacy.getAppropriateDepartments();
        
        Assert.assertNotNull("Should return department list", departments);
        Assert.assertFalse("Pharmacy should have appropriate departments", departments.isEmpty());
        
        // Pharmacy should have PHARMACY department
        Assert.assertTrue("Pharmacy should have pharmacy department",
                  departments.contains(DepartmentType.PHARMACY));
    }

    public void testShopTypesBakery() {
        ShopTypes bakery = ShopTypes.Bakery;
        List<DepartmentType> departments = bakery.getAppropriateDepartments();
        
        Assert.assertNotNull("Should return department list", departments);
        Assert.assertFalse("Bakery should have appropriate departments", departments.isEmpty());
        
        // Bakery should have BAKERY department
        Assert.assertTrue("Bakery should have bakery department",
                  departments.contains(DepartmentType.BAKERY));
    }

    public void testShopTypesBookstore() {
        ShopTypes bookstore = ShopTypes.Bookstore;
        List<DepartmentType> departments = bookstore.getAppropriateDepartments();
        
        Assert.assertNotNull("Should return department list", departments);
        Assert.assertFalse("Bookstore should have appropriate departments", departments.isEmpty());
        
        // Bookstore should have BOOKS department
        Assert.assertTrue("Bookstore should have books department",
                  departments.contains(DepartmentType.BOOKS));
    }

    public void testAllShopTypesHaveAppropriateDepartments() {
        for (ShopTypes shopType : ShopTypes.values()) {
            List<DepartmentType> departments = shopType.getAppropriateDepartments();
            Assert.assertNotNull("Shop type " + shopType + " should return department list", departments);
            Assert.assertFalse("Shop type " + shopType + " should have at least one department",
                       departments.isEmpty());
        }
    }
}

