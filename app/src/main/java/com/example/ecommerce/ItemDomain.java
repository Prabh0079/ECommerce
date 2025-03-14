package com.example.ecommerce;

import java.io.Serializable;

public class ItemDomain implements Serializable {
    private String id; // Renamed from 'Id' to 'id' to follow Java naming conventions
    private String title; // Renamed from 'Title' to 'title'
    private String imagePath; // Renamed from 'ImagePath' to 'imagePath'
    private String description; // Renamed from 'Description' to 'description'
    private double price; // Renamed from 'Price' to 'price'
    private double star; // Renamed from 'Star' to 'star'

    // Default constructor (needed for Firebase)
    public ItemDomain() {
    }

    // Custom constructor with parameters
    public ItemDomain(String id, String title, String description, double price) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getStar() {
        return star;
    }

    public void setStar(double star) {
        this.star = star;
    }

    // Additional validation logic can be added if necessary, for example:
    // Ensuring the price and star are non-negative
    public boolean isValid() {
        return price >= 0 && star >= 0 && star <= 5;
    }
}
