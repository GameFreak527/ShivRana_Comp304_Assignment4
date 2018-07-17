package com.example.shivrana.shivrana_comp304_assignment4;

public class Item {
    int id,price;
    String name,category;

    public Item(int id,String name, int price, String category){
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }
    public Item(){}

    public  Item(String name,int price, String category){
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return String.format("%d \t %s \t %d \t %s",id,name,price,category);
    }
}
