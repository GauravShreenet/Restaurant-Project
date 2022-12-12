package com.example.resturantapp.models;

public class BookingModel {
    String name;
    String phone;
    String numberOfPeople;
    String date;
    String time;
    String status;


    public BookingModel() {
    }

    public BookingModel(String name, String phone, String numberOfPeople, String date, String time) {
        this.name = name;
        this.phone = phone;
        this.numberOfPeople = numberOfPeople;
        this.date = date;
        this.time = time;
        this.status = "Placed";
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

    public String getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(String numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
