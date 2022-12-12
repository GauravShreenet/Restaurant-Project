package com.example.resturantapp.models;

import java.io.Serializable;
import java.util.List;

public class RequestAdd implements Serializable {


    private String name;
    private String phone;
    private String address;
    private String totalOrderPrice;
    private String status;
    private List<MyCartModel> foods;

    public RequestAdd() {
    }

    public RequestAdd(String name, String phone, String address, String totalOrderPrice, List<MyCartModel> foods) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.totalOrderPrice = totalOrderPrice;
        this.foods = foods;
        this.status = "0";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotalOrderPrice() {
        return totalOrderPrice;
    }

    public void setTotalOrderPrice(String totalOrderPrice) {
        this.totalOrderPrice = totalOrderPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<MyCartModel> getFoods() {
        return foods;
    }

    public void setFoods(List<MyCartModel> foods) {
        this.foods = foods;
    }
}
