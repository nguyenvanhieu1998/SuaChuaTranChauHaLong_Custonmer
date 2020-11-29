package com.example.suachuatranchauhalong_custonmer.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.suachuatranchauhalong_custonmer.Adapter.RequestRedeenPointAdapter;
import com.example.suachuatranchauhalong_custonmer.Adapter.VoucherAdapter;
import com.example.suachuatranchauhalong_custonmer.Adapter.VoucherOfCustomerAdapter;
import com.example.suachuatranchauhalong_custonmer.InterfaceOnClick.VoucherOfCustomerOnItemClick;
import com.example.suachuatranchauhalong_custonmer.Object.Customer;
import com.example.suachuatranchauhalong_custonmer.Object.Voucher;
import com.example.suachuatranchauhalong_custonmer.Object.Voucher_Customer;
import com.example.suachuatranchauhalong_custonmer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.LandingAnimator;

public class ActivityRedeenPoint extends AppCompatActivity implements VoucherOfCustomerOnItemClick {
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    Toolbar toolbar;
    RecyclerView recyclerViewVoucher;
    ArrayList<Voucher> voucherArrayList;
    VoucherOfCustomerAdapter voucherOfCustomerAdapter;
    //RequestRedeenPointAdapter requestRedeenPointAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeen_point);
        initReferenceObject();
        addControls();
        initDataRecycleVoucher();
        getPointUserCurrent();
    }
    private void initReferenceObject()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getInstance().getCurrentUser();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void initDataRecycleVoucher()
    {
        voucherArrayList = new ArrayList<>();
        databaseReference.child("ListVoucher").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                voucherArrayList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    Voucher voucher = dataSnapshot1.getValue(Voucher.class);
                    if(voucher.getStatus()==1)
                    {
                        voucherArrayList.add(voucher);
                    }

                }
                voucherOfCustomerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        voucherOfCustomerAdapter = new VoucherOfCustomerAdapter(this,voucherArrayList,this);
        recyclerViewVoucher.setAdapter(voucherOfCustomerAdapter);
        recyclerViewVoucher.setHasFixedSize(true);
        int spanCount = 2;//Số cột nếu thiết lập lưới đứng, số dòng nếu lưới ngang
        int orientation = GridLayoutManager.VERTICAL;//Lưới ngang
//int orientation = GridLayoutManager.HORIZONTAL;//Lưới đứng

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
// Gắn vào RecylerView
        recyclerViewVoucher.setItemAnimator(new LandingAnimator());
        recyclerViewVoucher.setLayoutManager(gridLayoutManager);
    }
    private void addControls()
    {
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.ActivityRedeenPoint_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Đổi điểm thưởng");
        recyclerViewVoucher = (RecyclerView) findViewById(R.id.ActivityRedeenPoint_recycleViewVoucher);
    }
    private static int check=0;
    private static int pointCustomer;
    private void getPointUserCurrent()
    {
        databaseReference.child("ListCustomer").child(firebaseUser.getUid().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Customer customer = snapshot.getValue(Customer.class);
                pointCustomer = customer.getPoint();
                Log.d("point1 : " , "" + pointCustomer);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onItemClickListener(final Voucher voucher) {
    //    databaseReference.child("ListCustomer").child(firebaseUser.getUid().toString()).child("point").
//        databaseReference.child("ListCustomer").child(firebaseUser.getUid().toString()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Customer customer = snapshot.getValue(Customer.class);
//                if(customer.getPoint()<voucher.getPoint())
//                {
//                    check = 0;
//                   // Toast.makeText(ActivityRedeenPoint.this, "Bạn không đủ điểm", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    check = 1;
//                }
//                pointCustomer = customer.getPoint();
//                Log.d("point1 : " , "" + pointCustomer);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
        Log.d("point2 : " , ""+ pointCustomer);
        if(pointCustomer < voucher.getPoint())
        {
            AlertDialog.Builder alert2 = new AlertDialog.Builder(ActivityRedeenPoint.this);
                    alert2.setTitle("Thông báo");
                    alert2.setIcon(R.drawable.ic_baseline_info_24);
                    alert2.setMessage("Bạn không đủ điểm");
                    alert2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                           // finish();
                        }
                    });
                    AlertDialog a = alert2.create();
                    a.show();
        }
        else if(pointCustomer >= voucher.getPoint())
        {
            DialogRedeenPoint(voucher,pointCustomer);
        }
    }
    public void DialogRedeenPoint(final Voucher voucher, final int point)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(ActivityRedeenPoint.this);
        alert.setTitle("Thông báo");
        alert.setIcon(R.drawable.ic_baseline_info_24);
        alert.setMessage("Bạn có muốn đổi "+voucher.getPoint() +" điểm " +
                "lấy voucher giảm giá " + voucher.getPricePromotion()+ "%" +
                "cho đơn hàng " + voucher.getPriceApply() + " đ " +"không ?");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                databaseReference.child("ListCustomer").child(firebaseUser.getUid().toString()).
                        child("ListVoucher").child(voucher.getIdVoucher().toString()).setValue(voucher);

                databaseReference.child("ListCustomer").child(firebaseUser.getUid().toString()).
                        child("ListVoucher").child(voucher.getIdVoucher().toString()).child("statusUse").setValue(1);

                databaseReference.child("ListCustomer").child(firebaseUser.getUid().toString()).
                        child("point").setValue(point-voucher.getPoint());
                Toast.makeText(ActivityRedeenPoint.this, "Đổi điểm thành công", Toast.LENGTH_SHORT).show();
                //finish();
            }
        });
        alert.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               //  finish();
            }
        });
        AlertDialog a = alert.create();
        a.show();
    }
}