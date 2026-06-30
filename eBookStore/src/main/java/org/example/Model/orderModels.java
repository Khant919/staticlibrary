package org.example.Model;

import java.util.ArrayList;
import java.util.List;

public class orderModels {
    private int orderId;
    private String paymentType;
    private double totalAmount; // New field for the total amount
    private final List<OrderItem> items;

    public orderModels(String paymentType) {
        this.paymentType = paymentType;
        this.items = new ArrayList<>();
        this.totalAmount = 0.0; // Initialize total amount
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void addItem(OrderItem item) {
        this.items.add(item);
        this.totalAmount += item.getPrice() * item.getQuantity(); // Calculate total amount
    }

    public static class OrderItem {
        private final String title;
        private final double price;
        private final int quantity;

        public OrderItem(String title, double price, int quantity) {
            this.title = title;
            this.price = price;
            this.quantity = quantity;
        }

        public String getTitle() {
            return title;
        }

        public double getPrice() {
            return price;
        }

        public int getQuantity() {
            return quantity;
        }
    }
}

