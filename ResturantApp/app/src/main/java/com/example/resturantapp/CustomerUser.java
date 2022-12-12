package com.example.resturantapp;

//this class will be use to store the user information in the object and then is passed to firebase database
public class CustomerUser {
    public String firstName, lastName, email, phone, as;

    //empty constructor for empty object for the class
    CustomerUser(){

    }

    //constructor to pass the variables
    public CustomerUser(String firstName, String lastName, String email, String phone){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.as = "customer";
    }

    public String getAs() {
        return as;
    }

    public void setAs(String as) {
        this.as = as;
    }
}
