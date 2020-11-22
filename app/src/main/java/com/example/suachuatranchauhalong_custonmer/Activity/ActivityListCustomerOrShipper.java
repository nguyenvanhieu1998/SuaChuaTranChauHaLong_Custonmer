package com.example.suachuatranchauhalong_custonmer.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suachuatranchauhalong_custonmer.Adapter.CustomerAdapter;
import com.example.suachuatranchauhalong_custonmer.Adapter.ShipperAdapter;
import com.example.suachuatranchauhalong_custonmer.InterfaceOnClick.OnItemClickListener;
import com.example.suachuatranchauhalong_custonmer.Object.Customer;
import com.example.suachuatranchauhalong_custonmer.Object.MenuDrink;
import com.example.suachuatranchauhalong_custonmer.Object.OrderDetail;
import com.example.suachuatranchauhalong_custonmer.Object.Shipper;
import com.example.suachuatranchauhalong_custonmer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class ActivityListCustomerOrShipper extends AppCompatActivity implements OnItemClickListener {
    TextView txtTittle;
    RecyclerView recyclerViewHuman;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    CustomerAdapter customerAdapter;
    ShipperAdapter shipperAdapter;
    ArrayList<Shipper> arrayListShipper;
    ArrayList<Customer> arrayListCustomer;
    Intent intent;
    int check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_customer_or_shipper);
        initReferencesObject();
        addControls();
        setData();
    }
    private void addControls() {
        txtTittle = (TextView) findViewById(R.id.ActivityListCustomerOrShipper_txtTittle);
        recyclerViewHuman = (RecyclerView) findViewById(R.id.ActivityListCustomerOrShipper_recycleViewShipperOrCustomer);
    }
    private void setData()
    {
        if(check==1)
        {
            txtTittle.setText("Danh sách khách hàng");
            arrayListCustomer = new ArrayList<>();
            databaseReference.child("ListCustomer").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Customer customer = dataSnapshot1.getValue(Customer.class);
                        arrayListCustomer.add(customer);
                    }
                    customerAdapter.notifyDataSetChanged();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            customerAdapter = new CustomerAdapter(this,arrayListCustomer,this);
            recyclerViewHuman.setAdapter(customerAdapter);
        }
        else if(check==2)
        {
            txtTittle.setText("Danh sách shipper");
            arrayListShipper = new ArrayList<>();
            databaseReference.child("ListShipper").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                    {
                        Shipper shipper = dataSnapshot1.getValue(Shipper.class);
                        arrayListShipper.add(shipper);
                    }
                    shipperAdapter.notifyDataSetChanged();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            shipperAdapter = new ShipperAdapter(this,arrayListShipper,this);
            recyclerViewHuman.setAdapter(shipperAdapter);
        }
        recyclerViewHuman.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        recyclerViewHuman.setLayoutManager(new LinearLayoutManager(this));
    }
    private void initReferencesObject() {
        intent = getIntent();
        check = intent.getIntExtra("key_itemMenu",0);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
    }


    @Override
    public void onItemClickListener(MenuDrink menuDrink) {

    }

    @Override
    public void onItemClickListener(Customer customer) {
        Toast.makeText(this, "" + customer.getIdCustomer(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClickListener(Shipper shipper) {
        Toast.makeText(this, "" + shipper.getIdShipper(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClickListener(OrderDetail orderDetail) {

    }
}