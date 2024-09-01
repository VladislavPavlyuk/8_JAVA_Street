package com.company.service.shop;
import com.company.model.Building;

public class ShopBriefPrintable implements ShopPrintable{
    @Override
    public void printInfo(Building building) {
        System.out.println("Brief information for School " + building.getName());
        System.out.println(building.getAddress());
    }
}
