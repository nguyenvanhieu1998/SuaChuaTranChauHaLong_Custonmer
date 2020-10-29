package com.example.suachuatranchauhalong_custonmer.Object;

public class OrderDetail {
    private String idOrderDetail;
    private String idOrder;
    private String idDrink;
    private int mount ;
    public OrderDetail()
    {

    }

    public OrderDetail(String idOrderDetail, String idOrder, String idDrink, int mount) {
        this.idOrderDetail = idOrderDetail;
        this.idOrder = idOrder;
        this.idDrink = idDrink;
        this.mount = mount;
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

    public void setIdDrink(String idDrink) {
        this.idDrink = idDrink;
    }

    public int getMount() {
        return mount;
    }

    public void setMount(int mount) {
        this.mount = mount;
    }
}
