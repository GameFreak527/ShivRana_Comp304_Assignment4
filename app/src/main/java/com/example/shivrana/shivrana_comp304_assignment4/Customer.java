package com.example.shivrana.shivrana_comp304_assignment4;

import android.graphics.Color;

public class Customer {
    private int id;
    private String userName,password,firstName,lastName,address,postalCode;
    public Customer(){
    }
    public Customer(int id,String userName,String password,String firstName,String lastName,String address,String postalCode){
        this.id = id;
        this.userName =userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.postalCode = postalCode;
    }

    public Customer(String userName,String password,String firstName,String lastName,String address,String postalCode){
        this.userName =userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.postalCode = postalCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return String.format("%d \t %s \t %s \t %s \t %s \t %s \t %s",id,userName,password,firstName,lastName,address,postalCode);
    }
}
