package com.example.resturantapp.models;


import java.io.Serializable;
import java.util.List;

public class Request implements Serializable {


    private String name;
    private String phone;

    private String totalOrderPrice;
    private String status;
    public List<MyCartModel> foods;
    boolean isExpandable;


    public Request() {
    }

    public Request(String name, String phone, String totalOrderPrice, List<MyCartModel> foods) {

        this.name = name;
        this.phone = phone;
        this.totalOrderPrice = totalOrderPrice;
        this.foods = foods;
        this.status = "Placed";
        isExpandable = false;
    }

    public boolean isExpandable() {
        return isExpandable;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalOrderPrice() {
        return totalOrderPrice;
    }

    public void setTotalOrderPrice(String totalOrderPrice) {
        this.totalOrderPrice = totalOrderPrice;
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

    public List<MyCartModel> getFoods() {
        return foods;
    }

    public void setFoods(List<MyCartModel> foods) {
        this.foods = foods;
    }
}
