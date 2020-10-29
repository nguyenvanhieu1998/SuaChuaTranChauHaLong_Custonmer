package com.example.suachuatranchauhalong_custonmer.Object;

import java.util.Date;

public class Drink {
    private String idDrink;
    private String idMenuDrink;
    private String nameDrink;
    private String imgUriDrink;
    private int priceDrink;
    private String dateCreateDrink;
    private int status;
    public Drink()
    {

    }

    public Drink(String idDrink, String idMenuDrink, String nameDrink, String imgUriDrink, int priceDrink, String dateCreateDrink,int status) {
        this.idDrink = idDrink;
        this.idMenuDrink = idMenuDrink;
        this.nameDrink = nameDrink;
        this.imgUriDrink = imgUriDrink;
        this.priceDrink = priceDrink;
        this.dateCreateDrink = dateCreateDrink;
        this.status =  status;
    }

    public String getIdDrink() {
        return idDrink;
    }

    public void setIdDrink(String idDrink) {
        this.idDrink = idDrink;
    }

    public String getIdMenuDrink() {
        return idMenuDrink;
    }

    public void setIdMenuDrink(String idMenuDrink) {
        this.idMenuDrink = idMenuDrink;
    }

    public String getNameDrink() {
        return nameDrink;
    }

    public void setNameDrink(String nameDrink) {
        this.nameDrink = nameDrink;
    }

    public String getImgUriDrink() {
        return imgUriDrink;
    }

    public void setImgUriDrink(String imgUriDrink) {
        this.imgUriDrink = imgUriDrink;
    }

    public int getPriceDrink() {
        return priceDrink;
    }

    public void setPriceDrink(int priceDrink) {
        this.priceDrink = priceDrink;
    }

    public String getDateCreateDrink() {
        return dateCreateDrink;
    }

    public void setDateCreateDrink(String dateCreateDrink) {
        this.dateCreateDrink = dateCreateDrink;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
