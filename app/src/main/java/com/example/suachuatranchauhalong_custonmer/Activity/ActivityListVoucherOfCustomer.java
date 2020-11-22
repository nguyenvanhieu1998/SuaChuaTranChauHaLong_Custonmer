package com.example.suachuatranchauhalong_custonmer.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.suachuatranchauhalong_custonmer.Adapter.RequestRedeenPointAdapter;
import com.example.suachuatranchauhalong_custonmer.Adapter.VoucherAdapter;
import com.example.suachuatranchauhalong_custonmer.InterfaceOnClick.VoucherOfCustomerOnItemClick;
import com.example.suachuatranchauhalong_custonmer.InterfaceOnClick.VoucherOnItemClick;
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

public class ActivityListVoucherOfCustomer extends AppCompatActivity implements VoucherOnItemClick {
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    Toolbar toolbar;
    RecyclerView recyclerViewVoucherOfCustomer;
    ArrayList<Voucher> voucherArrayList;
    VoucherAdapter voucherAdapter;
//    RequestRedeenPointAdapter requestRedeenPointAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_voucher_of_customer);
        initReferenceObject();
        addControls();
        initData();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void initReferenceObject()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getInstance().getCurrentUser();
    }
    private void initData()
    {
            voucherArrayList = new ArrayList<>();
            databaseReference.child("ListCustomer").child(firebaseUser.getUid().toString()).child("ListVoucher").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    voucherArrayList.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                    {
                           Voucher voucher = dataSnapshot1.getValue(Voucher.class);
                            voucherArrayList.add(voucher);

                    }
                    voucherAdapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            voucherAdapter = new VoucherAdapter(this,voucherArrayList,this);
        recyclerViewVoucherOfCustomer.setAdapter(voucherAdapter);
        recyclerViewVoucherOfCustomer.setHasFixedSize(true);
            int spanCount = 2;//Số cột nếu thiết lập lưới đứng, số dòng nếu lưới ngang
            int orientation = GridLayoutManager.VERTICAL;//Lưới ngang
//int orientation = GridLayoutManager.HORIZONTAL;//Lưới đứng

            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
            gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
// Gắn vào RecylerView
        recyclerViewVoucherOfCustomer.setItemAnimator(new LandingAnimator());
        recyclerViewVoucherOfCustomer.setLayoutManager(gridLayoutManager);
    }
    private void addControls()
    {
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.ActivityListVoucherOfCustomer_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Voucher của bạn");
        recyclerViewVoucherOfCustomer = (RecyclerView) findViewById(R.id.ActivityListVoucherOfCustomer_recycleViewVoucher);
    }

    @Override
    public void onItemClickListener(Voucher voucher) {

    }
//
//    @Override
//    public void onItemClickListener(final Voucher voucher) {
//        databaseReference.child("ListCustomer").child(firebaseUser.getUid().toString()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Customer customer = snapshot.getValue(Customer.class);
//                if(customer.getPoint()<voucher.getPoint())
//                {
//                    AlertDialog.Builder alert2 = new AlertDialog.Builder(ActivityListVoucherOfCustomer.this);
//                    alert2.setTitle("Thông báo");
//                    alert2.setIcon(R.drawable.ic_baseline_info_24);
//                    alert2.setMessage("Bạn không đủ điểm");
//                    alert2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                        }
//                    });
//                    AlertDialog a = alert2.create();
//                    a.show();
//                }
//                else {
//                    DialogRedeenPoint(voucher,customer.getPoint());
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//    }
//    public void DialogRedeenPoint(final Voucher voucher, final int point)
//    {
//        AlertDialog.Builder alert = new AlertDialog.Builder(ActivityListVoucherOfCustomer.this);
//        alert.setTitle("Thông báo");
//        alert.setIcon(R.drawable.ic_baseline_info_24);
//        alert.setMessage("Bạn có muốn đổi "+voucher.getPoint() +" điểm " +
//                "lấy voucher giảm giá " + voucher.getPricePromotion()+ "đ " +
//                "cho đơn hàng " + voucher.getPriceApply() + " đ " +"không");
//        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                databaseReference.child("ListCustomer").child(firebaseUser.getUid().toString()).child("point").setValue(point-voucher.getPoint());
//            }
//        });
//        alert.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//        AlertDialog a = alert.create();
//        a.show();
//    }
}