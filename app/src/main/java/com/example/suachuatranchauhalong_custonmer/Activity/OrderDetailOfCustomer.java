package com.example.suachuatranchauhalong_custonmer.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.suachuatranchauhalong_custonmer.Adapter.DetailOrderAdapter;
import com.example.suachuatranchauhalong_custonmer.Object.Customer;
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

public class OrderDetailOfCustomer extends AppCompatActivity implements View.OnClickListener{
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
    CheckBox checkBoxChoXacNhan,checkBoxDaXacNhan,checkBoxDangChuanBiDo,checkBoxDangGiaoHang,checkBoxDaGiaoHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_of_customer);
        initReferenceObject();
        // sendDataForDetailOrderAdapter();
        addControls();
        checkStatusOrder();
        getInformationInOrder();
        getListDrinkDetail();
        addEvents();
    }
    private void initReferenceObject()
    {
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
    private void checkStatusOrder()
    {
        databaseReference.child("ListOrder").child(firebaseUser.getUid().toString()).
                child(intent.getStringExtra("idOrder")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Order order = dataSnapshot.getValue(Order.class);
                if(order.getStatus()==0)
                {
                    imgLocationShipper.setVisibility(View.INVISIBLE);
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
                    imgLocationShipper.setVisibility(View.INVISIBLE);
                    txtStatusThanhToan.setText("Chưa thanh toán");
                    checkBoxChoXacNhan.setChecked(true);
                    checkBoxDaXacNhan.setChecked(true);
                    checkBoxDangChuanBiDo.setChecked(true);
                    checkBoxDangGiaoHang.setChecked(false);
                    checkBoxDaGiaoHang.setChecked(false);
                }
                if(order.getStatus()==4)
                {
                   // imgLocationShipper.setVisibility(View.INVISIBLE);
                    txtStatusThanhToan.setText("Chưa thanh toán");
                    imgLocationShipper.setVisibility(View.VISIBLE);
                    checkBoxChoXacNhan.setChecked(true);
                    checkBoxDaXacNhan.setChecked(true);
                    checkBoxDangChuanBiDo.setChecked(true);
                    checkBoxDangGiaoHang.setChecked(true);
                    checkBoxDaGiaoHang.setChecked(false);
                }
                if(order.getStatus()==5)
                {
                    txtStatusThanhToan.setText("Đã thanh toán");
                    imgLocationShipper.setVisibility(View.INVISIBLE);
                    checkBoxChoXacNhan.setChecked(true);
                    checkBoxDaXacNhan.setChecked(true);
                    checkBoxDangChuanBiDo.setChecked(true);
                    checkBoxDangGiaoHang.setChecked(true);
                    checkBoxDaGiaoHang.setChecked(true);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void getListDrinkDetail() {
        txtMaOrder.setText("Mã đơn hàng : "+ intent.getStringExtra("idOrder"));
        orderDetailArrayList = new ArrayList<>();
        databaseReference.child("ListOrder").child(firebaseUser.getUid().toString()).
                child(intent.getStringExtra("idOrder")).
                child("ListOrderDetail").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                     OrderDetail orderDetail = dataSnapshot1.getValue(OrderDetail.class);
                        orderDetailArrayList.add(orderDetail);

                }
                detailOrderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        DividerItemDecoration dividerHorizontal =
                new DividerItemDecoration(OrderDetailOfCustomer.this, DividerItemDecoration.VERTICAL);
        recyclerViewDrinkOfOrderDetail.addItemDecoration(dividerHorizontal);
        detailOrderAdapter = new DetailOrderAdapter(orderDetailArrayList,this);
        recyclerViewDrinkOfOrderDetail.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        recyclerViewDrinkOfOrderDetail.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewDrinkOfOrderDetail.setAdapter(detailOrderAdapter);
    }
    private void getInformationInOrder() {
        databaseReference.child("ListOrder").child(firebaseUser.getUid().toString()).
                child(intent.getStringExtra("idOrder")).
               addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Order order = dataSnapshot.getValue(Order.class);
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
//                if(order.isStatusThanhToan()==true)
//                {
//                    txtStatusThanhToan.setText("Đã thanh toán");
//                }
//                else
//                {
//                    txtStatusThanhToan.setText("Chưa thanh toán");
//
//                }
                txtMount.setText("Số lượng : " + order.getMount());
                txtThanhToan.setText("Thanh toán : "+ order.getPrice());
                txtKhuyenMai.setText("Khuyến mãi : "+ order.getPromotion());
                txtPhiVanChuyen.setText("Phí vận chuyển : "+ order.getPriceShip());
                txtTotalThanhToan.setText("Tổng thanh toán : "+ order.getTotalPrice());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void addControls()
    {
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.ActivityOrderOfCustomer_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Thanh toán");
        recyclerViewDrinkOfOrderDetail = (RecyclerView) findViewById(R.id.ActivityOrderOfCustomer_recyclerDrink);
        txtMaOrder = (TextView) findViewById(R.id.ActivityOrderOfCustomer_txtMaOrder);
        imgLocationShipper = (ImageView) findViewById(R.id.ActivityOrderOfCustomer_imgLocationShipper);
        txtStatusThanhToan = (TextView) findViewById(R.id.ActivityOrderOfCustomer_txtStatusThanhToan);
        txtStatusOrder = (TextView) findViewById(R.id.ActivityOrderOfCustomer_txtStatusOrder);
        // txtStatusThanhToan = (TextView) findViewById(R.id.ActivityOrderOfCustomer_txtSta);
        txtName = (TextView) findViewById(R.id.ActivityOrderOfCustomer_txtNameCustomer);
        txtPhone = (TextView) findViewById(R.id.ActivityOrderOfCustomer_txtPhoneCustomer);
        txtAddress = (TextView) findViewById(R.id.ActivityOrderOfCustomer_txtAddressCustomer);
        //  imgPhone = (ImageView) findViewById(R.id.ActivityOrderDetailShipper_imgCallPhone);
        //imgMessage = (ImageView) findViewById(R.id.ActivityOrderDetailShipper_imgMessage);
        txtMount = (TextView) findViewById(R.id.ActivityOrderOfCustomer_txtMountDrinkDetail) ;
        btnHuyDonHang = (Button) findViewById(R.id.ActivityOrderOfCustomer_btnHuyDonHang);
        txtThanhToan = (TextView) findViewById(R.id.ActivityOrderOfCustomer_txtThanhToan);
        txtKhuyenMai = (TextView) findViewById(R.id.ActivityOrderOfCustomer_txtKhuyenMai);
        txtPhiVanChuyen = (TextView) findViewById(R.id.ActivityOrderOfCustomer_txtPhiVanChuyen);
        txtTotalThanhToan = (TextView) findViewById(R.id.ActivityOrderOfCustomer_txtTongThanhToan);
        checkBoxChoXacNhan = (CheckBox) findViewById(R.id.ActivityOrderOfCustomer_checkBoxChoXacNhan);
        checkBoxDaXacNhan = (CheckBox) findViewById(R.id.ActivityOrderOfCustomer_checkBoxDaXacNhan);
        checkBoxDangChuanBiDo = (CheckBox) findViewById(R.id.ActivityOrderOfCustomer_checkBoxDangChuanBiDo);
        checkBoxDangGiaoHang = (CheckBox) findViewById(R.id.ActivityOrderOfCustomer_checkBoxDangGiaoHang);
        checkBoxDaGiaoHang = (CheckBox) findViewById(R.id.ActivityOrderOfCustomer_checkBoxDaGiaoHang);
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
            case R.id.ActivityOrderOfCustomer_btnHuyDonHang :
                huyDonHang();
                break;
            case R.id.ActivityOrderOfCustomer_imgLocationShipper :
                Intent intent = new Intent(OrderDetailOfCustomer.this,FollowOrder.class);
                startActivity(intent);
                break;
        }

    }

    private void huyDonHang() {
        databaseReference.child("ListOrder").child(firebaseUser.getUid().toString()).
                child(intent.getStringExtra("idOrder")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Order order = dataSnapshot.getValue(Order.class);
                if(order.getStatus()==1 || order.getStatus()==2)
                {
                    databaseReference.child("ListOrder").child(firebaseUser.getUid().toString()).
                            child(intent.getStringExtra("idOrder")).child("status").setValue(0);
                    databaseReference.child("ListOrder").child(firebaseUser.getUid().toString()).
                            child(intent.getStringExtra("idOrder")).child("statusCurrent").setValue(0);
                    Toast.makeText(OrderDetailOfCustomer.this, "Bạn đã hủy đơn hàng thành công", Toast.LENGTH_SHORT).show();
                }
                else if(order.getStatus()==3 || order.getStatus()==4 || order.getStatus()==5)
                {
                    Toast.makeText(OrderDetailOfCustomer.this, "Hiện tại bạn không thể hủy đơn hàng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void redirectActivityMessage(String phoneCustomerInOrder) {
        intentMessage = new Intent();
        intentMessage.setAction(Intent.ACTION_SENDTO);
        intentMessage.putExtra("sms_body","Chào bạn.....");
        intentMessage.setData(Uri.parse("sms:" + phoneCustomerInOrder));
        startActivity(intentMessage);
    }

    private void redirectActivityCallPhone(String phoneCustomerInOrder) {
        intentCallPhone  = new Intent();
        intentCallPhone.setAction(Intent.ACTION_VIEW);
        intentCallPhone.setData(Uri.parse("tel:" + phoneCustomerInOrder));
        startActivity(intentCallPhone);
    }
}