package com.example.suachuatranchauhalong_custonmer.Presenter.SetInformationForUserCurrent;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.suachuatranchauhalong_custonmer.Model.SetInformationForUserCurrent.ModelResponeToPresenterListenerSetInformation;
import com.example.suachuatranchauhalong_custonmer.Model.SetInformationForUserCurrent.ModelSetInformationUserCurrent;
import com.example.suachuatranchauhalong_custonmer.View.SetInformationForUserCurrent.ViewSetInformationListener;

public class PresenterSetInformationForUserCurrent implements ModelResponeToPresenterListenerSetInformation {
    //Khai báo interface ViewLoginListener để báo lại kết quả nhận từ model cho lớp Activity
    private ViewSetInformationListener callback;
    private ModelSetInformationUserCurrent modelSetInformationUserCurrent = new ModelSetInformationUserCurrent(this);;
    public PresenterSetInformationForUserCurrent(ViewSetInformationListener callback)
    {
        this.callback = callback;
    }
    //Lắng nghe được thông báo được gửi từ Activity
    public void receivedHandleSetInformation(String uid,String name, Bitmap bitmapFace,
                                             String getSexUserCurrent, String phone, String address,
                                             String email, ImageView imgFace)
    {
        //Báo cho lớp Model biết shipper có click vào nút Login
        modelSetInformationUserCurrent.handleSetInformation(uid,name,bitmapFace,getSexUserCurrent,phone,address,email,imgFace);
    }


    @Override
    public void onSetInformationUserRegisterEmprty() {
        callback.onSetInformationUserRegisterEmprty();
    }

    @Override
    public void onSetInformationUserRegisterSuccess() {
        callback.onSetInformationUserRegisterSuccess();
    }

    @Override
    public void onSetInformationUserRegisterFaile() {
        callback.onSetInformationUserRegisterFaile();
    }
    //Nhận kết quả xử lý logic từ lớp Model rồi đẩy kết quả vào interface ViewLoginListener

}
