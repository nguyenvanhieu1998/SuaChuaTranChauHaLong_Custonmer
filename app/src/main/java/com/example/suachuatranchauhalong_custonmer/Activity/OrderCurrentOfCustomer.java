package com.example.suachuatranchauhalong_custonmer.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.suachuatranchauhalong_custonmer.Adapter.DetailOrderAdapter;
import com.example.suachuatranchauhalong_custonmer.Object.Customer;
import com.example.suachuatranchauhalong_custonmer.Object.ListenerIdShipperGiaoHang;
import com.example.suachuatranchauhalong_custonmer.Object.Order;
import com.example.suachuatranchauhalong_custonmer.Object.OrderDetail;
import com.example.suachuatranchauhalong_custonmer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import android.widget.Toast;

public class OrderCurrentOfCustomer extends AppCompatActivity implements View.OnClickListener{
    RecyclerView recyclerViewDrinkOfOrderDetail;
    DetailOrderAdapter detailOrderAdapter;
    ArrayList<OrderDetail> orderDetailArrayList;
    ImageView imgLocationShipper;
    TextView txtStatusThanhToan,txtStatusOrder,txtMount,txtMaOrder,txtName,txtPhone,txtAddress,txtThanhToan,txtPhiVanChuyen,txtKhuyenMai,txtTotalThanhToan;
    //ImageView imgPhone,imgMessage;
    Button btnHuyDonHang;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    Intent intent,intentCallPhone,intentMessage;
    String sđt = "";
    Toolbar toolbar;
    LinearLayout linearContent;
    TextView txtThongBao;
    CheckBox checkBoxChoXacNhan,checkBoxDaXacNhan,checkBoxDangChuanBiDo,checkBoxDangGiaoHang,checkBoxDaGiaoHang;
    ListenerIdShipperGiaoHang listenerIdShipperGiaoHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_current_of_customer);
        initReferenceObject();
        // sendDataForDetailOrderAdapter();
        addControls();
        check();
       checkStatusOrder();
     getInformationInOrder();
        getListDrinkDetail();
        addEvents();
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
                            if(order.getStatusCurrent()==1)
                            {
                                check = true;
                            }
                        }
                        if(check==true)
                        {
                            txtThongBao.setVisibility(View.INVISIBLE);
                            linearContent.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            txtThongBao.setVisibility(View.VISIBLE);
                            linearContent.setVisibility(View.INVISIBLE);
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
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
        listenerIdShipperGiaoHang = new ListenerIdShipperGiaoHang();
      //  intent = getIntent();
    }
    private void checkStatusOrder()
    {
        databaseReference.child("ListOrder").child(firebaseUser.getUid().toString()).
                addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    Order order = dataSnapshot1.getValue(Order.class);
                    if(order.getStatusCurrent()==1)
                    {
                        if(order.getStatus()==0)
                        {
                            imgLocationShipper.setVisibility(View.INVISIBLE);
                            btnHuyDonHang.setVisibility(View.INVISIBLE);
                            txtStatusThanhToan.setText("Chưa thanh toán");
                            txtStatusOrder.setText("Trạng thái : Đã hủy" );
                            checkBoxChoXacNhan.setChecked(false);
                            checkBoxDaXacNhan.setChecked(false);
                            checkBoxDangChuanBiDo.setChecked(false);
                            checkBoxDangGiaoHang.setChecked(false);
                            checkBoxDaGiaoHang.setChecked(false);
                        }
                        if(order.getStatus()==1)
                        {
                            btnHuyDonHang.setVisibility(View.VISIBLE);
                            imgLocationShipper.setVisibility(View.INVISIBLE);
                            txtStatusThanhToan.setText("Chưa thanh toán");
                            checkBoxChoXacNhan.setChecked(true);
                            checkBoxDaXacNhan.setChecked(false);
                            checkBoxDangChuanBiDo.setChecked(false);
                            checkBoxDangGiaoHang.setChecked(false);
                            checkBoxDaGiaoHang.setChecked(false);
                        }
                        if(order.getStatus()==2)
                        {
                            btnHuyDonHang.setVisibility(View.VISIBLE);
                            imgLocationShipper.setVisibility(View.INVISIBLE);
                            txtStatusThanhToan.setText("Chưa thanh toán");
                            checkBoxChoXacNhan.setChecked(true);
                            checkBoxDaXacNhan.setChecked(true);
                            checkBoxDangChuanBiDo.setChecked(false);
                            checkBoxDangGiaoHang.setChecked(false);
                            checkBoxDaGiaoHang.setChecked(false);
                        }
                        if(order.getStatus()==3)
                        {
                            btnHuyDonHang.setVisibility(View.INVISIBLE);
                            imgLocationShipper.setVisibility(View.INVISIBLE);
                            txtStatusThanhToan.setText("Chưa thanh toán");
                            btnHuyDonHang.setVisibility(View.INVISIBLE);
                            checkBoxChoXacNhan.setChecked(true);
                            checkBoxDaXacNhan.setChecked(true);
                            checkBoxDangChuanBiDo.setChecked(true);
                            checkBoxDangGiaoHang.setChecked(false);
                            checkBoxDaGiaoHang.setChecked(false);
                        }
                        if(order.getStatus()==4)
                        {
                            try {
                                listenerIdShipperGiaoHang.setIdShipper(dataSnapshot1.
                                        child("Shipper").child("idShipper").getValue().toString());

                                listenerIdShipperGiaoHang.setIdOrder(order.getIdOrder().toString());
                            }catch (NullPointerException ex){

                            }
                            txtStatusThanhToan.setText("Chưa thanh toán");
                            btnHuyDonHang.setVisibility(View.INVISIBLE);
                            imgLocationShipper.setVisibility(View.VISIBLE);
                            checkBoxChoXacNhan.setChecked(true);
                            checkBoxDaXacNhan.setChecked(true);
                            checkBoxDangChuanBiDo.setChecked(true);
                            checkBoxDangGiaoHang.setChecked(true);
                            checkBoxDaGiaoHang.setChecked(false);
                        }
                        if(order.getStatus()==5)
                        {
                            imgLocationShipper.setVisibility(View.INVISIBLE);
                            txtStatusThanhToan.setText("Đã thanh toán");
                            btnHuyDonHang.setVisibility(View.INVISIBLE);
                            checkBoxChoXacNhan.setChecked(true);
                            checkBoxDaXacNhan.setChecked(true);
                            checkBoxDangChuanBiDo.setChecked(true);
                            checkBoxDangGiaoHang.setChecked(true);
                            checkBoxDaGiaoHang.setChecked(true);
                        }
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void getListDrinkDetail() {
        orderDetailArrayList = new ArrayList<>();
        databaseReference.child("ListOrder").child(firebaseUser.getUid().toString()).
               addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               // getInformationInOrder();
               // orderDetailArrayList.clear();
                for ( DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Order order = dataSnapshot1.getValue(Order.class);
                    try {

                    if(order.getStatusCurrent()==1)
                    {
                        txtMaOrder.setText("Mã đơn hàng : "+ order.getIdOrder().toString());
                        databaseReference.child("ListOrder").child(firebaseUser.getUid().toString()).
                                child(order.getIdOrder().toString()).child("ListOrderDetail").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                orderDetailArrayList.clear();
                                for(DataSnapshot dataSnapshot2 : dataSnapshot.getChildren())
                                {
                                    OrderDetail orderDetail = dataSnapshot2.getValue(OrderDetail.class);
                                    orderDetailArrayList.add(orderDetail);
                                }
                                detailOrderAdapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                    }catch (NullPointerException ex){
                        Log.w("tezzz123", " : null" + ex);
                    }
                 //   for (DataSnapshot d)


                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        DividerItemDecoration dividerHorizontal =
                new DividerItemDecoration(OrderCurrentOfCustomer.this, DividerItemDecoration.VERTICAL);
        recyclerViewDrinkOfOrderDetail.addItemDecoration(dividerHorizontal);
        detailOrderAdapter = new DetailOrderAdapter(orderDetailArrayList,this);
        recyclerViewDrinkOfOrderDetail.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        recyclerViewDrinkOfOrderDetail.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewDrinkOfOrderDetail.setAdapter(detailOrderAdapter);
    }
    private void getInformationInOrder() {
        databaseReference.child("ListOrder").child(firebaseUser.getUid().toString()).
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (final DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            Order order = dataSnapshot1.getValue(Order.class);
                            if(order.getStatusCurrent()==1)
                            {
                                databaseReference.child("ListCustomer").child(order.getIdCustomer().toString()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Customer customer = dataSnapshot.getValue(Customer.class);
                                        txtName.setText("Họ tên : " + customer.getNameCustomer().toString());
                                        txtPhone.setText("Số điện thoại : " + customer.getPhoneCustomer().toString());
                                        txtAddress.setText("Địa chỉ : " + customer.getAddressCustomer().toString());
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
//                                if(order.isStatusThanhToan()==true)
//                                {
//                                   txtStatusThanhToan.setText("Đã thanh toán");
//                                }
//                                else
//                                {
//                                    txtStatusThanhToan.setText("Chưa thanh toán");
//
//                                }
                                txtMount.setText("Số lượng : " + order.getMount());
                                txtThanhToan.setText("Thanh toán : "+ order.getPrice());
                                txtKhuyenMai.setText("Khuyến mãi : "+ order.getPromotion());
                                txtPhiVanChuyen.setText("Phí vận chuyển : "+ order.getPriceShip());
                                txtTotalThanhToan.setText("Tổng thanh toán : "+ order.getTotalPrice());
                            }

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
    private void addControls()
    {
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.ActivityOrderCurrentOfCustomer_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Đơn hàng hiện tại");
        txtStatusThanhToan = (TextView) findViewById(R.id.ActivityOrderCurrentOfCustomer_txtStatusThanhToan);
        txtThongBao = (TextView) findViewById(R.id.ActivityOrderCurrentOfCustomer_txtThongBao);
        linearContent = (LinearLayout) findViewById(R.id.ActivityOrderCurrentOfCustomer_linearContent);
        recyclerViewDrinkOfOrderDetail = (RecyclerView) findViewById(R.id.ActivityOrderCurrentOfCustomer_recyclerDrink);
        txtMaOrder = (TextView) findViewById(R.id.ActivityOrderCurrentOfCustomer_txtMaOrder);
        imgLocationShipper = (ImageView) findViewById(R.id.ActivityOrderCurrentOfCustomer_imgLocationShipper);
        txtStatusOrder = (TextView) findViewById(R.id.ActivityOrderCurrentOfCustomer_txtStatusOrder);
        // txtStatusThanhToan = (TextView) findViewById(R.id.ActivityOrderOfCustomer_txtSta);
        txtName = (TextView) findViewById(R.id.ActivityOrderCurrentOfCustomer_txtNameCustomer);
        txtPhone = (TextView) findViewById(R.id.ActivityOrderCurrentOfCustomer_txtPhoneCustomer);
        txtAddress = (TextView) findViewById(R.id.ActivityOrderCurrentOfCustomer_txtAddressCustomer);
        //  imgPhone = (ImageView) findViewById(R.id.ActivityOrderDetailShipper_imgCallPhone);
        //imgMessage = (ImageView) findViewById(R.id.ActivityOrderDetailShipper_imgMessage);
        txtMount = (TextView) findViewById(R.id.ActivityOrderCurrentOfCustomer_txtMountDrinkDetail) ;
        btnHuyDonHang = (Button) findViewById(R.id.ActivityOrderCurrentOfCustomer_btnHuyDonHang);
        txtThanhToan = (TextView) findViewById(R.id.ActivityOrderCurrentOfCustomer_txtThanhToan);
        txtKhuyenMai = (TextView) findViewById(R.id.ActivityOrderCurrentOfCustomer_txtKhuyenMai);
        txtPhiVanChuyen = (TextView) findViewById(R.id.ActivityOrderCurrentOfCustomer_txtPhiVanChuyen);
        txtTotalThanhToan = (TextView) findViewById(R.id.ActivityOrderCurrentOfCustomer_txtTongThanhToan);
        checkBoxChoXacNhan = (CheckBox) findViewById(R.id.ActivityOrderCurrentOfCustomer_checkBoxChoXacNhan);
        checkBoxDaXacNhan = (CheckBox) findViewById(R.id.ActivityOrderCurrentOfCustomer_checkBoxDaXacNhan);
        checkBoxDangChuanBiDo = (CheckBox) findViewById(R.id.ActivityOrderCurrentOfCustomer_checkBoxDangChuanBiDo);
        checkBoxDangGiaoHang = (CheckBox) findViewById(R.id.ActivityOrderCurrentOfCustomer_checkBoxDangGiaoHang);
        checkBoxDaGiaoHang = (CheckBox) findViewById(R.id.ActivityOrderCurrentOfCustomer_checkBoxDaGiaoHang);
    }
    private void addEvents()
    {
        //  imgMessage.setOnClickListener(this);
        //imgPhone.setOnClickListener(this);
        imgLocationShipper.setOnClickListener(this);
        btnHuyDonHang.setOnClickListener(this);
    }
    //    public void sendDataForDetailOrderAdapter()
//    {
//        ListenerIDOrder send  = new ListenerIDOrder();
////        send.setIDOrder(intent.getStringExtra("IDOrder").toString());
////        send.setIDOrderDetail(intent.getStringExtra("IDOrderDetail").toString());
//    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ActivityOrderCurrentOfCustomer_btnHuyDonHang :
                DialogHuyHang();
                break;
            case R.id.ActivityOrderCurrentOfCustomer_imgLocationShipper :
                Intent intent = new Intent(OrderCurrentOfCustomer.this,FollowOrder.class);
                startActivity(intent);
                break;
        }

    }
    public void DialogHuyHang()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Thông báo");
        alert.setIcon(R.drawable.ic_baseline_info_24);
        alert.setMessage("Bạn có chắc muốn hủy đơn hàng này ? ");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                huyDonHang();
                // Intent intent = new Intent(DrinkDetail.this,MenuDrinkDetail.class);
                // startActivity(intent);
               // finish();
            }
        });
        alert.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog a = alert.create();
        a.show();
    }
    private void huyDonHang() {
        databaseReference.child("ListOrder").child(firebaseUser.getUid().toString())
              .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Order order = dataSnapshot1.getValue(Order.class);
                    if(order.getStatusCurrent()==1)
                    {
                        if(order.getStatus()==1 || order.getStatus()==2)
                        {
                            databaseReference.child("ListOrder").child(firebaseUser.getUid().toString()).
                                    child(order.getIdOrder()).child("status").setValue(0);
                            databaseReference.child("ListOrder").child(firebaseUser.getUid().toString()).
                                    child(order.getIdOrder()).child("statusCurrent").setValue(0);
                            Toast.makeText(OrderCurrentOfCustomer.this, "Bạn đã hủy đơn hàng thành công", Toast.LENGTH_SHORT).show();
                        }
                        else if(order.getStatus()==3 || order.getStatus()==4 || order.getStatus()==5)
                        {
                            Toast.makeText(OrderCurrentOfCustomer.this, "Hiện tại bạn không thể hủy đơn hàng", Toast.LENGTH_SHORT).show();
                        }
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}