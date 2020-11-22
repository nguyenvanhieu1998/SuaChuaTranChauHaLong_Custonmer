package com.example.suachuatranchauhalong_custonmer.Object;

public class OrderDetail2 {
    private String idOrderDetail;
    private String idOrder;
    private String idDrink;
    private String imgDrink;
    private String nameDrink;
    private int mount ;
    private float price;
    private String bonus;
    private float priceBonus;
    private String idCustomer;
    public OrderDetail2()
    {

    }

    public OrderDetail2(String idOrderDetail, String idOrder, String idDrink,String imgDrink,String nameDrink, int mount,float price, String bonus,float priceBonus,String idCustomer) {
        this.idOrderDetail = idOrderDetail;
        this.idOrder = idOrder;
        this.idDrink = idDrink;
        this.imgDrink = imgDrink;
        this.nameDrink = nameDrink;
        this.mount = mount;
        this.price = price;
        this.bonus = bonus;
        this.priceBonus = priceBonus;
        this.idCustomer = idCustomer;

    }

    public String getIdOrderDetail() {
        return idOrderDetail;
    }

    public void setIdOrderDetail(String idOrderDetail) {
        this.idOrderDetail = idOrderDetail;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getIdDrink() {
        return idDrink;
    }

    public String getImgDrink() {
        return imgDrink;
    }

    public void setImgDrink(String imgDrink) {
        this.imgDrink = imgDrink;
    }

    public String getNameDrink() {
        return nameDrink;
    }

    public void setNameDrink(String nameDrink) {
        this.nameDrink = nameDrink;
    }

    public void setIdDrink(String idDrink) {
        this.idDrink = idDrink;
    }

    public int getMount() {
        return mount;
    }

    public void setMount(int mount) {
        this.mount = mount;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    public float getPriceBonus() {
        return priceBonus;
    }

    public void setPriceBonus(float priceBonus) {
        this.priceBonus = priceBonus;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }
}
