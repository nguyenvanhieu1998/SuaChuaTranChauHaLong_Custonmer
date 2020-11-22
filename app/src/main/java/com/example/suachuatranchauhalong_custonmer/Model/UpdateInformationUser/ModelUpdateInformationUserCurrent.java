package com.example.suachuatranchauhalong_custonmer.Model.UpdateInformationUser;

import androidx.annotation.NonNull;

import com.example.suachuatranchauhalong_custonmer.Object.Customer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ModelUpdateInformationUserCurrent {
    ModelResponeToPresenterListenerUpdateInformation callback;
    private FirebaseAuth firebaseAuth ;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    String bitmapImgFaceUserCurrent,phoneUserCurrent,addressUserCurrent,sexUserCurrent;
    public  ModelUpdateInformationUserCurrent(ModelResponeToPresenterListenerUpdateInformation callback)
    {
        this.callback = callback;
    }
    public void handleUpdateInformation(String phone,String address,String sex)
    {
        initReferenceObject();
        getDataOfUserCurrent();
        if( phone.equals("") || address.equals("")  )
        {
            callback.onUpdateInformationUserCurrentEmrty();
        }
        else if ( phone.equals(phoneUserCurrent) && address.equals(addressUserCurrent) && sex.equals(sexUserCurrent))
        {
            callback.onUpdateInformationUserCurrentDataNoChange();
        }
        else
        {
            updateInformationUserCurrent(phone,address,sex);
        }


    }
    Boolean checkSex;
    private void updateInformationUserCurrent(String phone,String address,String sex) {
        databaseReference.child("ListCustomer").child(firebaseUser.getUid().toString()).child("phoneCustomer").setValue(phone);
        databaseReference.child("ListCustomer").child(firebaseUser.getUid().toString()).child("addressCustomer").setValue(address);
        checkSex = sex.equals("Nam") ? true : false;
        databaseReference.child("ListCustomer").child(firebaseUser.getUid().toString()).child("sexCustomer").setValue(checkSex);
        callback.onUpdateInformationUserCurrentSuccess();
    }

    private void  initReferenceObject()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getInstance().getCurrentUser();
    }
    private void getDataOfUserCurrent()
    {
        databaseReference.child("ListCustomer").child(firebaseUser.getUid().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Customer customer = snapshot.getValue(Customer.class);
                bitmapImgFaceUserCurrent = "" + customer.getAvtUriCustomer();
                phoneUserCurrent = customer.getPhoneCustomer();
                addressUserCurrent = customer.getAddressCustomer();
                sexUserCurrent = customer.isSexCustomer() ? "Nam" : "Nữ";
//                nameCarUserCurrent = shipper.getNameCarShipper();
//                colorCarUserCurrent = shipper.getColorCarShipper();
//                licensePlateCarUserCurrent = shipper.getDriverPlatesShipper();
//                drivingLicenseCarUserCurrent = shipper.getDriverLicenseShipper();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
