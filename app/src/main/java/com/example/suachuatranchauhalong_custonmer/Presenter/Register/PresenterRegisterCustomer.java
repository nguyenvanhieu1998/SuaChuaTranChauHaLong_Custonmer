package com.example.suachuatranchauhalong_custonmer.Presenter.Register;

import com.example.suachuatranchauhalong_custonmer.Model.Register.ModelRegisterCustomer;
import com.example.suachuatranchauhalong_custonmer.Model.Register.ModelResponeToPresenterListener;
import com.example.suachuatranchauhalong_custonmer.View.Register.ViewRegisterListener;

public class PresenterRegisterCustomer implements ModelResponeToPresenterListener {
    private ViewRegisterListener callback;
    private ModelRegisterCustomer modelRegisterCustomer = new ModelRegisterCustomer(this);
    public PresenterRegisterCustomer(ViewRegisterListener callback)
    {
        this.callback = callback;
    }
    public void receivedHandleRegister(String email,String pass, String rePass)
    {
        modelRegisterCustomer.handleRegister(email,pass,rePass);
    }

    @Override
    public void onRegisterEmprty() {
        callback.onRegisterEmprty();
    }

    @Override
    public void onRegisterSuccess() {
        callback.onRegisterSuccess();
    }

    @Override
    public void onRegisterFaile() {
        callback.onRegisterFaile();
    }

    @Override
    public void onRegisterPasswordDifficult() {
        callback.onRegisterPasswordDifficult();
    }
}
