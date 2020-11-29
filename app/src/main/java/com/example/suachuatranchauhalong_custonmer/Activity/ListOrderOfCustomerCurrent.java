package com.example.suachuatranchauhalong_custonmer.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suachuatranchauhalong_custonmer.Adapter.OrderAdapter;
import com.example.suachuatranchauhalong_custonmer.Adapter.OrderApater_Customer;
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
    OrderApater_Customer orderApater_customer;
    RecyclerView recyclerViewMyOrder;
    Calendar calen;
    String dateOrder;
    TextView txtTittle;
    Toolbar toolbar;
    Spinner spinnerStatusOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_order_of_customer_current);
        initReferenceObject();
        addControls();
        check();
       // initData();
        initSpinner();
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
                                    if(dataSnapshot1.getChildrenCount()!=0)
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
    String arr[]={
            "Tất cả",
            "Chờ xác nhận",
            "Đã xác nhận",
            "Đang chuẩn bị đồ",
            "Đang giao hàng",
            "Đã giao hàng",
            "Đã hủy"};

    private void initSpinner()
    {

        ArrayAdapter<String> adapter=new ArrayAdapter<String>
                (
                        this,
                        android.R.layout.simple_spinner_item,
                        arr
                );
        //phải gọi lệnh này để hiển thị danh sách cho Spinner
        adapter.setDropDownViewResource
                (android.R.layout.simple_list_item_single_choice);
        //Thiết lập adapter cho Spinner
        spinnerStatusOrder.setAdapter(adapter);
        spinnerStatusOrder.setOnItemSelectedListener(new MyProcessEvent());
    }
    private class MyProcessEvent implements
            AdapterView.OnItemSelectedListener
    {
        //Khi có chọn lựa thì vào hàm này
//        public void onItemSelected(AdapterView<?> arg0,
//                                   View arg1,
//                                   int arg2,
//                                   long arg3) {
//            //arg2 là phần tử được chọn trong data source
//
//        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            // selection.setText(arr[arg2]);
           // yearOfSpinner = Integer.parseInt(arr[i]);
            initData(arr[i]);
        //  Toast.makeText(ListOrderOfCustomerCurrent.this, "" + arr[i], Toast.LENGTH_SHORT).show();
        }

        //Nếu không chọn gì cả
        public void onNothingSelected(AdapterView<?> arg0) {
            //  selection.setText("");
        }
    }
        private void initData(final String status)
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
//                            if(order.getStatusCurrent()==1)
//                            {
                            if(status.equals("Tất cả"))
                            {
                                if(order.getStatus()==0 || order.getStatus()==1 || order.getStatus()==2 ||
                                        order.getStatus()==3 || order.getStatus()==4 || order.getStatus()==5 )
                                {
                                    arrayListOrder.add(order);
                                }

                            }
                            if(status.equals("Chờ xác nhận"))
                            {
                                if(order.getStatus()==1  )
                                {
                                    arrayListOrder.add(order);
                                }

                            }
                            if(status.equals("Đã xác nhận"))
                            {
                                if(order.getStatus()==2 )
                                {
                                    arrayListOrder.add(order);
                                }

                            }
                            if(status.equals("Đang chuẩn bị đồ"))
                            {
                                if(order.getStatus()==3 )
                                {
                                    arrayListOrder.add(order);
                                }

                            }
                            if(status.equals("Đang giao hàng"))
                            {
                                if(order.getStatus()==4 )
                                {
                                    arrayListOrder.add(order);
                                }

                            }
                            if(status.equals("Đã giao hàng"))
                            {
                                if(order.getStatus()==5 )
                                {
                                    arrayListOrder.add(order);
                                }

                            }
                            if(status.equals("Đã hủy"))
                            {
                                if(order.getStatus()==0 )
                                {
                                    arrayListOrder.add(order);
                                }

                            }

                           // }
                        }

                    orderApater_customer.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
//            DividerItemDecoration dividerHorizontal =
//                    new DividerItemDecoration(ListOrderOfCustomerCurrent.this, DividerItemDecoration.VERTICAL);
//
//            recyclerViewMyOrder.addItemDecoration(dividerHorizontal);
            orderApater_customer = new OrderApater_Customer(arrayListOrder,this);
            recyclerViewMyOrder.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
            recyclerViewMyOrder.setLayoutManager(new LinearLayoutManager(this));
            recyclerViewMyOrder.setAdapter(orderApater_customer);
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
            spinnerStatusOrder = (Spinner) findViewById(R.id.ActivityListMyOrder_spinnerStatus) ;
            recyclerViewMyOrder = (RecyclerView) findViewById(R.id.ActivityListMyOrder_recycleViewOrder);
            txtTittle = (TextView) findViewById(R.id.ActivityListMyOrder_txtTittle);
        }
    }