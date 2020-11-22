package com.example.suachuatranchauhalong_custonmer.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.suachuatranchauhalong_custonmer.Adapter.RequestRedeenPointAdapter;
import com.example.suachuatranchauhalong_custonmer.Object.Voucher_Customer;
import com.example.suachuatranchauhalong_custonmer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ActivityListVoucherConfirm extends AppCompatActivity {
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    Toolbar toolbar;
    RecyclerView recyclerViewVoucherConfirm;
    ArrayList<Voucher_Customer> voucher_customerArrayList;
    RequestRedeenPointAdapter requestRedeenPointAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_voucher_confirm);
        initReferenceObject();
        addControls();
        initData();
    }
    private void initReferenceObject()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getInstance().getCurrentUser();
    }
    private void initData()
    {
//        databaseReference.child("ListRequestRedeenPoint")
    }
    private void addControls()
    {
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.ActivityListVoucherConfirm_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Yêu cầu đổi điểm thưởng");
        recyclerViewVoucherConfirm = (RecyclerView) findViewById(R.id.ActivityListVoucherConfirm_recycleViewRequest);
    }
}