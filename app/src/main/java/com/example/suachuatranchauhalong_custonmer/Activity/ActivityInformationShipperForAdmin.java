package com.example.suachuatranchauhalong_custonmer.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.suachuatranchauhalong_custonmer.Object.Customer;
import com.example.suachuatranchauhalong_custonmer.Object.Order;
import com.example.suachuatranchauhalong_custonmer.Object.Shipper;
import com.example.suachuatranchauhalong_custonmer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityInformationShipperForAdmin extends AppCompatActivity {
    CircleImageView circleImageViewShipper;
    TextView txtEmail,txtPhone,txtSex,txtAddress,txtName,txtMountOrder,txtNameCar,txtColorCar,txtBienSoXe,txtBangLaiXe;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    Intent intent;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_shipper_for_admin);
        initReferenceObject();
        addControls();
        initData();
    }
    private void initReferenceObject() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        intent = getIntent();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    int mountOrderOfShipper;
    private void addControls()
    {
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.ActivityInformationShipperForAdmin_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Thông tin shipper");
        circleImageViewShipper = (CircleImageView) findViewById(R.id.ActivityInformationShipperForAdmin_imgShipper);
        txtEmail = (TextView) findViewById(R.id.ActivityInformationShipperForAdmin_emailShipper);
        txtPhone = (TextView) findViewById(R.id.ActivityInformationShipperForAdmin_phoneShipper);
        txtSex = (TextView) findViewById(R.id.ActivityInformationShipperForAdmin_sexShipper);
        txtAddress = (TextView) findViewById(R.id.ActivityInformationShipperForAdmin_addressShipper);
        txtName = (TextView) findViewById(R.id.ActivityInformationShipperForAdmin_nameShipper);
        txtMountOrder = (TextView) findViewById(R.id.ActivityInformationShipperForAdmin_mountOrderShipper);
        txtNameCar = (TextView) findViewById(R.id.ActivityInformationShipperForAdmin_nameCarShipper);
        txtColorCar = (TextView) findViewById(R.id.ActivityInformationShipperForAdmin_colorCarShipper);
        txtBienSoXe = (TextView) findViewById(R.id.ActivityInformationShipperForAdmin_bienSoXeShipper);
        txtBangLaiXe = (TextView) findViewById(R.id.ActivityInformationShipperForAdmin_bangLaiXeShipper);
    }
    private void initData()
    {
        databaseReference.child("ListShipper").child(intent.getStringExtra("idShipperSendActivityInformationShipper")).
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Shipper shipper = snapshot.getValue(Shipper.class);
                        Picasso.get()
                                .load(shipper.getAvtShipperURL())
                                .fit()
                                .into(circleImageViewShipper);
                        txtName.setText(shipper.getNameShipper());
                        txtEmail.setText(shipper.getEmailShipper());
                        txtPhone.setText(shipper.getPhoneShipper());
                        txtAddress.setText(shipper.getAddressShipper());
                        txtSex.setText(shipper.isSexShipper() ? "Nam":"Nữ");
                        txtNameCar.setText(shipper.getNameCarShipper());
                        txtColorCar.setText(shipper.getColorCarShipper());
                        txtBienSoXe.setText(shipper.getDriverPlatesShipper());
                        txtBangLaiXe.setText(shipper.getDriverLicenseShipper());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        databaseReference.child("ListOrder").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mountOrderOfShipper=0;
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren())
                {
                    for (DataSnapshot dataSnapshot11 : dataSnapshot1.getChildren())
                    {
                        try {
                            if(dataSnapshot11.child("Shipper").child("idShipper").getValue().equals(intent.getStringExtra("idShipperSendActivityInformationShipper")))
                            {
                                mountOrderOfShipper++ ;
                            }
                        }catch (NullPointerException ex){

                        }

                    }
                }

                txtMountOrder.setText("Đã nhận : "+ mountOrderOfShipper);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void addEvents()
    {

    }
}