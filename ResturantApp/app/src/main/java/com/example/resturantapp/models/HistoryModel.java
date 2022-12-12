package com.example.resturantapp.models;



import java.io.Serializable;
import java.util.List;

public class HistoryModel implements Serializable {

    String currentDate;
    String currentTime;
    private String totalOrderPrice;
    public List<MyCartModel> foods;
    boolean isExpandable;

    public HistoryModel() {
    }

    public HistoryModel(String currentDate, String currentTime, String totalOrderPrice, List<MyCartModel> foods) {
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.totalOrderPrice = totalOrderPrice;
        this.foods = foods;
        this.isExpandable = false;
    }

    public boolean isExpandable() {
        return isExpandable;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String CurrentDate) {
        this.currentDate = currentDate;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getTotalOrderPrice() {
        return totalOrderPrice;
    }

    public void setTotalOrderPrice(String totalOrderPrice) {
        this.totalOrderPrice = totalOrderPrice;
    }

    public List<MyCartModel> getFoods() {
        return foods;
    }

    public void setFoods(List<MyCartModel> foods) {
        this.foods = foods;
    }
}
