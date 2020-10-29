package com.example.suachuatranchauhalong_custonmer.Object;

public class MenuDrink {
    private String idMenuDrink;
    private String nameMenuDrink;
    private String imageUriMenuDrink;
    private String dateCreateMenuDrink;
    private int status;
    public MenuDrink(){}
    public MenuDrink(String idMenuDrink, String nameMenuDrink, String imageUriMenuDrink, String dateCreateMenuDrink, int status) {
        this.idMenuDrink = idMenuDrink;
        this.nameMenuDrink = nameMenuDrink;
        this.imageUriMenuDrink = imageUriMenuDrink;
        this.dateCreateMenuDrink = dateCreateMenuDrink;
        this.status = status;
    }

    public String getIdMenuDrink() {
        return idMenuDrink;
    }

    public void setIdMenuDrink(String idMenuDrink) {
        this.idMenuDrink = idMenuDrink;
    }

    public String getNameMenuDrink() {
        return nameMenuDrink;
    }

    public void setNameMenuDrink(String nameMenuDrink) {
        this.nameMenuDrink = nameMenuDrink;
    }

    public String getImageUriMenuDrink() {
        return imageUriMenuDrink;
    }

    public void setImageUriMenuDrink(String imageUriMenuDrink) {
        this.imageUriMenuDrink = imageUriMenuDrink;
    }

    public String getDateCreateMenuDrink() {
        return dateCreateMenuDrink;
    }

    public void setDateCreateMenuDrink(String dateCreateMenuDrink) {
        this.dateCreateMenuDrink = dateCreateMenuDrink;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
