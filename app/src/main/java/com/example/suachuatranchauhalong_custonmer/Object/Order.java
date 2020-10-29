package com.example.suachuatranchauhalong_custonmer.Object;

public class Order {
    private String idOrder;
    private String idOrderDetail;
    private String idCustomer;
    private int mount;
    private int price;
    private int promotion;
    private int priceShip;
    private int totalPrice;
    private String dateOrder;
    private int status;
    private boolean statusThanhToan;

    public Order()
    {

    }

    public Order(String idOrder, String idOrderDetail, String idCustomer,
                 int mount, int price, int promotion, int priceShip,
                 int totalPrice, String dateOrder, int status, boolean statusThanhToan) {
        this.idOrder = idOrder;
        this.idOrderDetail = idOrderDetail;
        this.idCustomer = idCustomer;
        this.mount = mount;
        this.price = price;
        this.promotion = promotion;
        this.priceShip = priceShip;
        this.totalPrice = totalPrice;
        this.dateOrder = dateOrder;
        this.status = status;
        this.statusThanhToan = statusThanhToan;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getIdOrderDetail() {
        return idOrderDetail;
    }

    public void setIdOrderDetail(String idOrderDetail) {
        this.idOrderDetail = idOrderDetail;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPromotion() {
        return promotion;
    }

    public void setPromotion(int promotion) {
        this.promotion = promotion;
    }

    public int getPriceShip() {
        return priceShip;
    }

    public void setPriceShip(int priceShip) {
        this.priceShip = priceShip;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
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
}
