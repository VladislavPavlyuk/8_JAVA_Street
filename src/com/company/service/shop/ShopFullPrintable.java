package com.company.service.shop;
import com.company.model.IBuilding;
import com.company.model.Shop;
import com.company.enums.DepartmentType;

public class ShopFullPrintable implements ShopPrintable {
    @Override
    public void printInfo(IBuilding building) {
        if (building instanceof Shop) {
            Shop shop = (Shop) building;
            System.out.println("SHOP");
            System.out.println("Name: " + (shop.getName() != null ? shop.getName() : "Not specified"));
            System.out.println("Address: " + shop.getAddress());
            System.out.println("Shop type: " + (shop.getShopType() != null ? shop.getShopType().getShopType() : "Not specified"));
            System.out.println("Capacity: " + shop.getCapacity());
            System.out.println("Number of departments: " + shop.getNumberOfDepartments());
            System.out.println("Departments:");
            if (shop.getDepartments().isEmpty()) {
                System.out.println("  - No departments specified");
            } else {
                for (DepartmentType dept : shop.getDepartments()) {
                    System.out.println("  • " + dept.getName());
                }
            }
            if (shop.getShopDescription() != null && shop.getShopDescription().length() > 0) {
                System.out.println("Description: " + shop.getShopDescription());
            }
        }
    }
}
