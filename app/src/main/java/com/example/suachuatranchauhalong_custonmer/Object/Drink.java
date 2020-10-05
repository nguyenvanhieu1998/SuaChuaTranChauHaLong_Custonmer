package com.example.suachuatranchauhalong_custonmer.Object;

import java.util.Date;

public class Drink {
    public int id;
    public String name;
    public int imageUri;
    public int amount;
    public int price;
    public String type;
    public String dateCreate;

    public Drink(int id, String name, int imageUri, int amount, int price, String type, String dateCreate) {
        this.id = id;
        this.name = name;
        this.imageUri = imageUri;
        this.amount = amount;
        this.price = price;
        this.type = type;
        this.dateCreate = dateCreate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageUri() {
        return imageUri;
    }

    public void setImageUri(int imageUri) {
        this.imageUri = imageUri;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }
}
