package com.example.suachuatranchauhalong_custonmer.Object;

import java.io.Serializable;

public class News implements Serializable {
    private String idNews;
    private String imgTittle;
    private String tittle;
    private String descri;
    private String dateCreate;
    private String type;
    private int status;
    public News()
    {

    }
    public News(String idNews, String imgTittle, String tittle, String descri, String dateCreate, String type,int status) {
        this.idNews = idNews;
        this.imgTittle = imgTittle;
        this.tittle = tittle;
        this.descri = descri;
        this.dateCreate = dateCreate;
        this.type = type;
        this.status = status;
    }

    public String getIdNews() {
        return idNews;
    }

    public void setIdNews(String idNews) {
        this.idNews = idNews;
    }

    public String getImgTittle() {
        return imgTittle;
    }

    public void setImgTittle(String imgTittle) {
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
