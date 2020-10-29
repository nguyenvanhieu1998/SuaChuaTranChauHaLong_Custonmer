package com.example.suachuatranchauhalong_custonmer.Object;

public class Customer {
    private String idCustomer;
    private String avtUriCustomer;
    private String nameCustomer;
    private boolean sexCustomer;
    private String emailCustomer;
    private String phoneCustomer;
    private String addressCustomer;
    private String permission;
    private int mountOrder;
    private int point;
    public Customer()
    {

    }

    public Customer(String idCustomer, String avtUriCustomer, String nameCustomer, boolean sexCustomer, String emailCustomer, String phoneCustomer, String addressCustomer, String permission, int mountOrder, int point) {
        this.idCustomer = idCustomer;
        this.avtUriCustomer = avtUriCustomer;
        this.nameCustomer = nameCustomer;
        this.sexCustomer = sexCustomer;
        this.emailCustomer = emailCustomer;
        this.phoneCustomer = phoneCustomer;
        this.addressCustomer = addressCustomer;
        this.point = point;
        this.permission = permission;
        this.mountOrder = mountOrder;

    }
    public int getMountOrder() {
        return mountOrder;
    }

    public void setMountOrder(int mountOrder) {
        this.mountOrder = mountOrder;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getAvtUriCustomer() {
        return avtUriCustomer;
    }

    public void setAvtUriCustomer(String avtUriCustomer) {
        this.avtUriCustomer = avtUriCustomer;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public boolean isSexCustomer() {
        return sexCustomer;
    }

    public void setSexCustomer(boolean sexCustomer) {
        this.sexCustomer = sexCustomer;
    }

    public String getEmailCustomer() {
        return emailCustomer;
    }

    public void setEmailCustomer(String emailCustomer) {
        this.emailCustomer = emailCustomer;
    }

    public String getPhoneCustomer() {
        return phoneCustomer;
    }

    public void setPhoneCustomer(String phoneCustomer) {
        this.phoneCustomer = phoneCustomer;
    }

    public String getAddressCustomer() {
        return addressCustomer;
    }

    public void setAddressCustomer(String addressCustomer) {
        this.addressCustomer = addressCustomer;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
