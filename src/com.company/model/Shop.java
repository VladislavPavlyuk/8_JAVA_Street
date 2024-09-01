
package com.company.model;
import com.company.enums.*;
import java.util.Random;
import com.company.service.shop.ShopPrintable;

// Магазин
public class Shop extends Building {

    private ShopPrintable shopPrintable;

    public Shop(ShopPrintable shopPrintable) {
        this.shopPrintable = shopPrintable;
    }

    public void setShopPrintable(ShopPrintable shopPrintable) {
        this.shopPrintable = shopPrintable;
    }

    private int numberOfDepartments;
    private String shopType;
    private String shopDescription;

    public Shop(int address, int minDept, int maxDept, String shopType, String shopDescription) {
        super(address);
        Random rand = new Random();
        this.numberOfDepartments = rand.nextInt((maxDept - minDept) + 1) + minDept;
        this.shopType = shopType;
        this.shopDescription = shopDescription;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public int getNumberOfDepartments() {
        return numberOfDepartments;
    }


    public void setNumberOfDepartments(int numberOfDepartments) {
        this.numberOfDepartments = numberOfDepartments;
    }

    @Override
    public void initializeFromString(String data) {

        String[] parts = data.split(",");
        this.address = Integer.parseInt(parts[0]);
        this.numberOfDepartments = Integer.parseInt(parts[1]);
    }

    @Override
    public void printInfo() {
        shopPrintable.printInfo(this);
    }
}
