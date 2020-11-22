package com.example.suachuatranchauhalong_custonmer.Object;

public class Order {
    private String idOrder;
    private String idCustomer;
    private int mount;
    private float price;
    private float promotion;
    private float priceShip;
    private float totalPrice;
    private String dateOrder;
    private int status;
    private boolean statusThanhToan;
    private int statusCurrent;

    public Order()
    {

    }

    public Order(String idOrder, String idCustomer,
                 int mount, float price, float promotion, float priceShip,
                 float totalPrice, String dateOrder, int status, boolean statusThanhToan,int statusCurrent) {
        this.idOrder = idOrder;
        this.idCustomer = idCustomer;
        this.mount = mount;
        this.price = price;
        this.promotion = promotion;
        this.priceShip = priceShip;
        this.totalPrice = totalPrice;
        this.dateOrder = dateOrder;
        this.status = status;
        this.statusThanhToan = statusThanhToan;
        this.statusCurrent = statusCurrent;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
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

    public float getPromotion() {
        return promotion;
    }

    public void setPromotion(float promotion) {
        this.promotion = promotion;
    }

    public float getPriceShip() {
        return priceShip;
    }

    public void setPriceShip(float priceShip) {
        this.priceShip = priceShip;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(String dateOrder) {
        this.dateOrder = dateOrder;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isStatusThanhToan() {
        return statusThanhToan;
    }

    public void setStatusThanhToan(boolean statusThanhToan) {
        this.statusThanhToan = statusThanhToan;
    }

    public int getStatusCurrent() {
        return statusCurrent;
    }

    public void setStatusCurrent(int statusCurrent) {
        this.statusCurrent = statusCurrent;
    }
}
