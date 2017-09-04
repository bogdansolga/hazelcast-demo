package com.sg.hazelcast.demo.model;

import java.io.Serializable;

@SuppressWarnings("unused")
public class ProductDTO implements Serializable {

    private final int id;
    private final String name;
    private final double price;

    private int discountPercentage;

    public ProductDTO(int id, String name, double price) {
        this.id = id; this.name = name; this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return id + ": " + name + ", " + price;
    }

    public void setDiscountPercentage(final int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getDiscountedPrice() {
        return "The discounted price for '" + name + "' is " + ((100.0f - discountPercentage) * price) / 100;
    }
}
