package com.example.suachuatranchauhalong_custonmer.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.suachuatranchauhalong_custonmer.Adapter.OrderAdapter;
import com.example.suachuatranchauhalong_custonmer.Object.Order;
import com.example.suachuatranchauhalong_custonmer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class OrderChoXacNhan extends AppCompatActivity {
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    ArrayList<Order> arrayListOrder;
    OrderAdapter orderAdapter;
    RecyclerView recyclerViewOrderChoXacNhan;
    Calendar calen;
    String dateOrder;
    TextView txtTittle;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_cho_xac_nhan);
        initReferenceObject();
        addControls();
        check();
        initData();
    }
    private void initReferenceObject()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getInstance().getCurrentUser();
    }
    boolean check = false;
    private void check()
    {
        databaseReference.child("ListOrder").
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                        {
                            for(DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren())
                            {
                                Order order = dataSnapshot2.getValue(Order.class);
                                if(order.getStatus()==1)
                                {
                                    check = true;
                                }
                            }



                        }
                        if(check==false)
                        {
//                            txtTittle.setText("Hiện tại không có đơn hàng nào");
                            txtTittle.setVisibility(View.VISIBLE);
                        }
                        else if(check==true)
                        {
                            txtTittle.setVisibility(View.INVISIBLE);
                        }
                        }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
    private void initData()
    {
//        calen = Calendar.getInstance();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy");
//        dateOrder = "" + simpleDateFormat.format(calen.getTime());
        arrayListOrder = new ArrayList<>();
        databaseReference.child("ListOrder").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayListOrder.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    for(DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren())
                    {
                        Order order = dataSnapshot2.getValue(Order.class);
                        if(order.getStatus()==1)
                        {
                            arrayListOrder.add(order);
                        }
                    }

                }
                orderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        DividerItemDecoration dividerHorizontal =
//                new DividerItemDecoration(OrderChoXacNhan.this, DividerItemDecoration.VERTICAL);
//
//        recyclerViewOrderChoXacNhan.addItemDecoration(dividerHorizontal);
        orderAdapter = new OrderAdapter(arrayListOrder,this);
        recyclerViewOrderChoXacNhan.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        recyclerViewOrderChoXacNhan.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewOrderChoXacNhan.setAdapter(orderAdapter);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void addControls() {
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.ActivityChoXacNhan_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Đơn hàng cần xác nhận");
        recyclerViewOrderChoXacNhan = (RecyclerView) findViewById(R.id.ActivityOrderChoXacNhan_recycleViewOrder);
        txtTittle = (TextView) findViewById(R.id.ActivityOrderChoXacNhan_txtTittle);
    }
}