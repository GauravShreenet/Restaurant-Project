package com.example.resturantapp;

import com.google.firebase.firestore.DocumentId;

import java.io.Serializable;

public class EntreeModel implements Serializable {

    @DocumentId
    String productId;
    private String name, url;
    private double price;
    private int quantity;

    public EntreeModel() {
    }

    public EntreeModel(String name, long price, int quantity) {

        this.name = name;
        this.price = price;
        this.url = url;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setNames(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
