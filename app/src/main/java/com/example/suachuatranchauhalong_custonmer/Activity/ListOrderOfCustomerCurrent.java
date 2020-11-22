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

import java.util.ArrayList;
import java.util.Calendar;

public class ListOrderOfCustomerCurrent extends AppCompatActivity {
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    ArrayList<Order> arrayListOrder;
    OrderAdapter orderAdapter;
    RecyclerView recyclerViewMyOrder;
    Calendar calen;
    String dateOrder;
    TextView txtTittle;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_order_of_customer_current);
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
            databaseReference.child("ListOrder").child(firebaseUser.getUid().toString()).
                    addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                                {
                                    Order order = dataSnapshot1.getValue(Order.class);
                                    if(order.getStatusCurrent()==0)
                                    {
                                        check = true;
                                    }
                                }
                            if(check==false)
                            {
                                txtTittle.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }
        private void initData()
        {
            arrayListOrder = new ArrayList<>();
            databaseReference.child("ListOrder").child(firebaseUser.getUid().toString()).
                    addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    arrayListOrder.clear();
                        for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                        {
                            Order order = dataSnapshot1.getValue(Order.class);
                            if(order.getStatusCurrent()==0)
                            {
                                arrayListOrder.add(order);
                            }
                        }

                    orderAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
//            DividerItemDecoration dividerHorizontal =
//                    new DividerItemDecoration(ListOrderOfCustomerCurrent.this, DividerItemDecoration.VERTICAL);
//
//            recyclerViewMyOrder.addItemDecoration(dividerHorizontal);
            orderAdapter = new OrderAdapter(arrayListOrder,this);
            recyclerViewMyOrder.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
            recyclerViewMyOrder.setLayoutManager(new LinearLayoutManager(this));
            recyclerViewMyOrder.setAdapter(orderAdapter);
        }
        @Override
        public boolean onSupportNavigateUp() {
            onBackPressed();
            return true;
        }
        private void addControls() {
            toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.ActivityListMyOrder_toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Đơn hàng của tôi");
            recyclerViewMyOrder = (RecyclerView) findViewById(R.id.ActivityListMyOrder_recycleViewOrder);
            txtTittle = (TextView) findViewById(R.id.ActivityListMyOrder_txtTittle);
        }
    }