package com.example.sauna;

import android.annotation.SuppressLint;

public class Sauna {
    private String make; //make
    private String productCode; // Unique Identifier
    private String woodType;
    private int capacity; // # of persons
    private String kindOfHeating; // example: (electric, infrared)
    private double price;
    private String imageURL;

    public static final String fileLineSeparator = "\n";

    Sauna(String make, String productCode, String woodType, int capacity, String kindofHeating, double price, String imageURL) {
        this.make = make;
        this.productCode = productCode;
        this.woodType = woodType;
        this.capacity = capacity;
        this.kindOfHeating = kindofHeating;
        this.price = price;
        this.imageURL = imageURL;
    }

    public Sauna(String code) {
        productCode = code;
    }

    public String getMake() {
        return make;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getWoodType() {
        return woodType;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getKindOfHeating() {
        return kindOfHeating;
    }

    public double getPrice() {
        return price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getShipping() {
        if (capacity <= 3) {
            return (double) Math.round(price * 5) / 100.0;
        } else {
            return (double) Math.round(price * 8) / 100.0;
        }
    }

    public String toString(String _SEP_) {
        return make + _SEP_ + productCode + _SEP_ + woodType + _SEP_ + capacity + _SEP_ +
              kindOfHeating + _SEP_ + String.format("%.2f", price) + _SEP_ + imageURL;
    }

    public String toPrintString() {
        return make + ", " + productCode + ", " + capacity + " persons, $" + String.format("%.2f", price);
    }

    @Override
    public boolean equals(Object o) {
        //if (((String) o).equals(productCode)) return true;
        Sauna sauna = (Sauna) o;
        return productCode.equals(sauna.productCode);
    }

}

