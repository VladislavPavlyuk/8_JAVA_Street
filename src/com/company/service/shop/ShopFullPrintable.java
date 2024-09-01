package com.company.service.shop;
import com.company.model.Building;


public class ShopFullPrintable implements ShopPrintable {
    @Override
    public void printInfo(Building building) {
        System.out.println("Full information for Shop " + building.getName());
        System.out.println(building.getAddress() +  building.getDescription());
    }
}
