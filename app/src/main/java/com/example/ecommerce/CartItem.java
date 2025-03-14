package com.example.ecommerce;

import java.io.Serializable;

public class CartItem implements Serializable {
    private String title;
    private String imagePath;
    private double price;
    private int quantity;

    public CartItem(String title, String imagePath, double price, int quantity) {
        this.title = title;
        this.imagePath = imagePath;
        this.price = price;
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
