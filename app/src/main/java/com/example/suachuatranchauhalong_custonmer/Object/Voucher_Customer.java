package com.example.suachuatranchauhalong_custonmer.Object;

public class Voucher_Customer {
    private String idVoucherCustomer;
    private String idCustomer;
    private String idVoucher;
    private String dateApply;
    private int status;

    public Voucher_Customer()
    {

    }

    public Voucher_Customer(String idVoucherCustomer, String idCustomer, String idVoucher, String dateApply,int status) {
        this.idVoucherCustomer = idVoucherCustomer;
        this.idCustomer = idCustomer;
        this.idVoucher = idVoucher;
        this.dateApply = dateApply;
    }

    public String getIdVoucherCustomer() {
        return idVoucherCustomer;
    }

    public void setIdVoucherCustomer(String idVoucherCustomer) {
        this.idVoucherCustomer = idVoucherCustomer;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getIdVoucher() {
        return idVoucher;
    }

    public void setIdVoucher(String idVoucher) {
        this.idVoucher = idVoucher;
    }

    public String getDateApply() {
        return dateApply;
    }

    public void setDateApply(String dateApply) {
        this.dateApply = dateApply;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
