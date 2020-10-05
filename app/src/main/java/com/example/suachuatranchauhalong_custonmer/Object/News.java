package com.example.suachuatranchauhalong_custonmer.Object;

public class News {
    private int id;
    private int imgTittle;
    private String tittle;
    private String descri;
    private String dateCreate;
    private String type;

    public News(int id, int imgTittle, String tittle, String descri, String dateCreate, String type) {
        this.id = id;
        this.imgTittle = imgTittle;
        this.tittle = tittle;
        this.descri = descri;
        this.dateCreate = dateCreate;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImgTittle() {
        return imgTittle;
    }

    public void setImgTittle(int imgTittle) {
        this.imgTittle = imgTittle;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
