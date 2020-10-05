package com.example.suachuatranchauhalong_custonmer.Presenter.Login;

import com.example.suachuatranchauhalong_custonmer.Model.Login.ModelLoginCustomer;
import com.example.suachuatranchauhalong_custonmer.Model.Login.ModelResponeToPresenterListener;
import com.example.suachuatranchauhalong_custonmer.View.Login.ViewLoginListener;

//Thực thi interface ModelResponeToPresenterListener để nhận kết quả từ lớp Model
public class PresenterLoginCustomer implements ModelResponeToPresenterListener {
    //Khai báo interface ViewLoginListener để báo lại kết quả nhận từ model cho lớp Activity
    private ViewLoginListener callback;
    private ModelLoginCustomer modelLoginCustomer = new ModelLoginCustomer(this);;
    public PresenterLoginCustomer(ViewLoginListener callback)
    {
        this.callback = callback;
    }
    //Lắng nghe được thông báo được gửi từ Activity
    public void receivedHandleLogin(String email, String password)
    {
        //Báo cho lớp Model biết shipper có click vào nút Login
        modelLoginCustomer.handleLogin(email,password);
    }
    //Nhận kết quả xử lý logic từ lớp Model rồi đẩy kết quả vào interface ViewLoginListener
    @Override
    public void onLoginEmprty() {
        callback.onLoginEmprty();
    }

    @Override
    public void onLoginSuccess() {
        callback.onLoginSuccess();
    }

    @Override
    public void onLoginFaile() {
        callback.onLoginFaile();

    }
}
