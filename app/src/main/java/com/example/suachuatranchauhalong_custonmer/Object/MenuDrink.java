package com.example.suachuatranchauhalong_custonmer.Object;

public class MenuDrink {
    public int id;
    public String name;
    public int imageUri;

    public MenuDrink(int id, String name, int imageUri) {
        this.id = id;
        this.name = name;
        this.imageUri = imageUri;
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
}
