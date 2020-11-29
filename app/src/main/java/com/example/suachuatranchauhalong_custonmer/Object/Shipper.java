package com.example.suachuatranchauhalong_custonmer.Object;

public class Shipper {
    private String idShipper;
    private String nameShipper;
    private String avtShipperURL;
    private boolean sexShipper;
    private String phoneShipper;
    private String addressShipper;
    private String emailShipper;
    private String nameCarShipper;
    private String colorCarShipper;
    private String driverPlatesShipper;
    private String driverLicenseShipper;
    private String permission;
    public Shipper()
    {

    }

    public Shipper(String idShipper, String nameShipper, String avtShipperURL, boolean sexShipper, String phoneShipper, String addressShipper, String emailShipper, String nameCarShipper, String colorCarShipper, String driverPlatesShipper, String driverLicenseShipper, String permission) {
        this.idShipper = idShipper;
        this.nameShipper = nameShipper;
        this.avtShipperURL = avtShipperURL;
        this.sexShipper = sexShipper;
        this.phoneShipper = phoneShipper;
        this.addressShipper = addressShipper;
        this.emailShipper = emailShipper;
        this.nameCarShipper = nameCarShipper;
        this.colorCarShipper = colorCarShipper;
        this.driverPlatesShipper = driverPlatesShipper;
        this.driverLicenseShipper = driverLicenseShipper;
        this.permission = permission;
      }

    public String getIdShipper() {
        return idShipper;
    }

    public void setIdShipper(String idShipper) {
        this.idShipper = idShipper;
    }

    public String getNameShipper() {
        return nameShipper;
    }

    public void setNameShipper(String nameShipper) {
        this.nameShipper = nameShipper;
    }

    public String getAvtShipperURL() {
        return avtShipperURL;
    }

    public void setAvtShipperURL(String avtShipperURL) {
        this.avtShipperURL = avtShipperURL;
    }

    public boolean isSexShipper() {
        return sexShipper;
    }

    public void setSexShipper(boolean sexShipper) {
        this.sexShipper = sexShipper;
    }

    public String getPhoneShipper() {
        return phoneShipper;
    }

    public void setPhoneShipper(String phoneShipper) {
        this.phoneShipper = phoneShipper;
    }

    public String getAddressShipper() {
        return addressShipper;
    }

    public void setAddressShipper(String addressShipper) {
        this.addressShipper = addressShipper;
    }

    public String getEmailShipper() {
        return emailShipper;
    }

    public void setEmailShipper(String emailShipper) {
        this.emailShipper = emailShipper;
    }

    public String getNameCarShipper() {
        return nameCarShipper;
    }

    public void setNameCarShipper(String nameCarShipper) {
        this.nameCarShipper = nameCarShipper;
    }

    public String getColorCarShipper() {
        return colorCarShipper;
    }

    public void setColorCarShipper(String colorCarShipper) {
        this.colorCarShipper = colorCarShipper;
    }

    public String getDriverPlatesShipper() {
        return driverPlatesShipper;
    }

    public void setDriverPlatesShipper(String driverPlatesShipper) {
        this.driverPlatesShipper = driverPlatesShipper;
    }

    public String getDriverLicenseShipper() {
        return driverLicenseShipper;
    }

    public void setDriverLicenseShipper(String driverLicenseShipper) {
        this.driverLicenseShipper = driverLicenseShipper;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

}

