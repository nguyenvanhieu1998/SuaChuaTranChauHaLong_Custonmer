package com.example.suachuatranchauhalong_custonmer.Presenter.UpdateInformationUser;

import com.example.suachuatranchauhalong_custonmer.Model.UpdateInformationUser.ModelResponeToPresenterListenerUpdateInformation;
import com.example.suachuatranchauhalong_custonmer.Model.UpdateInformationUser.ModelUpdateInformationUserCurrent;
import com.example.suachuatranchauhalong_custonmer.View.UpdateInformationUser.ViewUpdateInformationListener;

public class PresenterUpdateInformationForUserCurrent implements ModelResponeToPresenterListenerUpdateInformation {
    private ViewUpdateInformationListener callback;
    private ModelUpdateInformationUserCurrent modelUpdateInformationUserCurrent = new ModelUpdateInformationUserCurrent(this);
    public PresenterUpdateInformationForUserCurrent(ViewUpdateInformationListener callback)
    {
        this.callback = callback;
    }
    public void receivedHandleUpdateInformation(String phone,String address,String sex)
    {
        modelUpdateInformationUserCurrent.handleUpdateInformation(phone,address,sex);
    }
    @Override
    public void onUpdateInformationUserCurrentSuccess() {
        callback.onUpdateInformationUserCurrentSuccess();
    }

    @Override
    public void onUpdateInformationUserCurrentFaile() {
        callback.onUpdateInformationUserCurrentFaile();
    }

    @Override
    public void onUpdateInformationUserCurrentEmrty() {
        callback.onUpdateInformationUserCurrentEmrty();
    }

    @Override
    public void onUpdateInformationUserCurrentDataNoChange() {
        callback.onUpdateInformationUserCurrentDataNoChange();
    }
}
