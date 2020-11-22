package com.example.suachuatranchauhalong_custonmer.Object;

public class FragmentPoint {
    private int id;
    private int img1;
    private String name;
    private int img2;

    public FragmentPoint(int id,int img1, String name, int img2) {
        this.id = id;
        this.img1 = img1;
        this.name = name;
        this.img2 = img2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImg1() {
        return img1;
    }

    public void setImg1(int img1) {
        this.img1 = img1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg2() {
        return img2;
    }

    public void setImg2(int img2) {
        this.img2 = img2;
    }
}
