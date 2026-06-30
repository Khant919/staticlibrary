package org.example.Model;

import java.util.HashMap;
import java.util.Map;

public class CartModel {
    private static CartModel instance;
    private final Map<String, CartItem> items = new HashMap<>();
    private String paymentType = "";

    private CartModel() {
    }

    public static synchronized CartModel getInstance() {
        if (instance == null) {
            instance = new CartModel();
        }
        return instance;
    }

    public void addItem(String title, double price) {
        if (items.containsKey(title)) {
            items.get(title).incrementQuantity();
        } else {
            items.put(title, new CartItem(title, price));
        }
    }

    public void removeItem(String title) {
        items.remove(title);
    }

    public Map<String, CartItem> getItems() {
        return items;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public void clear() {
        items.clear();
        paymentType = "";
    }

    public static class CartItem {
        private final String title;
        private final double price;
        private int quantity;

        public CartItem(String title, double price) {
            this.title = title;
            this.price = price;
            this.quantity = 1;
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

        public void incrementQuantity() {
            this.quantity++;
        }
    }
}


//public class Cart {
//    private static Cart instance;
//    private Map<String, CartItem> items;
//    private String paymentType;
//
//    private Cart() {
//        items = new HashMap<>();
//    }
//
//    public static Cart getInstance() {
//        if (instance == null) {
//            instance = new Cart();
//        }
//        return instance;
//    }
//
//    public Map<String, CartItem> getItems() {
//        return items;
//    }
//
//    public void addItem(CartItem item) {
//        items.put(item.getTitle(), item);
//    }
//
//    public void removeItem(String title) {
//        items.remove(title);
//    }
//
//    public void clear() {
//        items.clear();
//    }
//
//    public void setPaymentType(String paymentType) {
//        this.paymentType = paymentType;
//    }
//
//    public String getPaymentType() {
//        return paymentType;
//    }
//
//    public void addItem(String name, double price) {
//        
//    }
//
//
//    // Inner CartItem class
//    public static class CartItem {
//        private String title;
//        private double price;
//        private int quantity;
//
//        public CartItem(String title, double price, int quantity) {
//            this.title = title;
//            this.price = price;
//            this.quantity = quantity;
//        }
//
//        public String getTitle() {
//            return title;
//        }
//
//        public double getPrice() {
//            return price;
//        }
//
//        public int getQuantity() {
//            return quantity;
//        }
//    }
//}
