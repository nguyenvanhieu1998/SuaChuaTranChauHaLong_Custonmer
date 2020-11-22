package com.example.suachuatranchauhalong_custonmer.Object;



public class Drink {
    private String idDrink;
    private String idMenuDrink;
    private String nameDrink;
    private String imgUriDrink;
    private float priceDrink;
    private String dateCreateDrink;
    private int status;
    private int statusLove;
    public Drink()
    {

    }

    public Drink(String idDrink, String idMenuDrink, String nameDrink, String imgUriDrink, float priceDrink, String dateCreateDrink,int status,int statusLove) {
        this.idDrink = idDrink;
        this.idMenuDrink = idMenuDrink;
        this.nameDrink = nameDrink;
        this.imgUriDrink = imgUriDrink;
        this.priceDrink = priceDrink;
        this.dateCreateDrink = dateCreateDrink;
        this.status =  status;
        this.statusLove = statusLove;
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

    public float getPriceDrink() {
        return priceDrink;
    }

    public void setPriceDrink(float priceDrink) {
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

    public int getStatusLove() {
        return statusLove;
    }

    public void setStatusLove(int statusLove) {
        this.statusLove = statusLove;
    }
}
