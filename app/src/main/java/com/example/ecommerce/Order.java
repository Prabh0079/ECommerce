package com.example.ecommerce;

public class Order {

    private String orderId;
    private String customerId;
    private double totalPrice;

    // Default constructor required for Firebase
    public Order() {
    }

    // Constructor
    public Order(String orderId, String customerId, double totalPrice) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.totalPrice = totalPrice;
    }

    // Getters and Setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
