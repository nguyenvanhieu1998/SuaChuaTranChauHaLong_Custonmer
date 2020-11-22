package com.example.suachuatranchauhalong_custonmer.Object;

public class Voucher {
    private String idVoucher;
    private String imgVoucher;
    private float priceApply;
    private int pricePromotion;
    private int point;
    private int status;
    private String dateCreateVoucher;

    public Voucher()
    {

    }
    public Voucher(String idVoucher, String imgVoucher, float priceApply, int pricePromotion,int point, int status,String dateCreateVoucher) {
        this.idVoucher = idVoucher;
        this.imgVoucher = imgVoucher;
        this.priceApply = priceApply;
        this.pricePromotion = pricePromotion;
        this.point = point;
        this.status = status;
        this.dateCreateVoucher = dateCreateVoucher;
    }

    public String getIdVoucher() {
        return idVoucher;
    }

    public void setIdVoucher(String idVoucher) {
        this.idVoucher = idVoucher;
    }

    public String getImgVoucher() {
        return imgVoucher;
    }

    public void setImgVoucher(String imgVoucher) {
        this.imgVoucher = imgVoucher;
    }

    public float getPriceApply() {
        return priceApply;
    }

    public void setPriceApply(float priceApply) {
        this.priceApply = priceApply;
    }

    public int getPricePromotion() {
        return pricePromotion;
    }

    public void setPricePromotion(int pricePromotion) {
        this.pricePromotion = pricePromotion;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDateCreateVoucher() {
        return dateCreateVoucher;
    }

    public void setDateCreateVoucher(String dateCreateVoucher) {
        this.dateCreateVoucher = dateCreateVoucher;
    }
}
