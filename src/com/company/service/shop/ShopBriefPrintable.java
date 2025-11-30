package com.company.service.shop;
import com.company.model.IBuilding;
import com.company.model.Shop;

public class ShopBriefPrintable implements ShopPrintable {
    @Override
    public void printInfo(IBuilding building) {
        if (building instanceof Shop) {
            Shop shop = (Shop) building;
            System.out.println("Shop: " + (shop.getName() != null ? shop.getName() : "Not specified") + 
                             ", Address: " + shop.getAddress() + 
                             ", Departments: " + shop.getNumberOfDepartments());
        }
    }
}
