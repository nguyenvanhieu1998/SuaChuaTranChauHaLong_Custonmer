package com.example.suachuatranchauhalong_custonmer.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class ActivityOrderDetail_MyOrder_Customer extends AppCompatActivity implements View.OnClickListener {
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
    ListenerIdShipperGiaoHang listenerIdShipperGiaoHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail__my_order__customer);
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
    static String idOrder;
    static String idShipper;
    private void checkStatusOrder()
    {
        databaseReference.child("ListOrder").child(firebaseUser.getUid().toString()).
                child(intent.getStringExtra("idOrderSendActivityOrderDetailOfCustomer_MyOrder")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Order order = dataSnapshot.getValue(Order.class);
                if(order.getStatus()==0)
                {
                    btnHuyDonHang.setVisibility(View.INVISIBLE);
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
                    checkBoxChoXacNhan.setChecked(true);
                    checkBoxDaXacNhan.setChecked(true);
                    checkBoxDangChuanBiDo.setChecked(true);
                    checkBoxDangGiaoHang.setChecked(false);
                    checkBoxDaGiaoHang.setChecked(false);
                }
                if(order.getStatus()==4)
                {
                    idOrder = order.getIdOrder().toString();
                    idShipper = dataSnapshot.
                            child("Shipper").child("idShipper").getValue().toString();
//                    try {
//                        listenerIdShipperGiaoHang.setIdShipper(dataSnapshot.
//                                child("Shipper").child("idShipper").getValue().toString());
//
//                        listenerIdShipperGiaoHang.setIdOrder(order.getIdOrder().toString());
//                    }catch (NullPointerException ex){

                 //   }
                    btnHuyDonHang.setVisibility(View.INVISIBLE);
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
                    btnHuyDonHang.setVisibility(View.INVISIBLE);
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
        txtMaOrder.setText("Mã đơn hàng : "+ intent.getStringExtra("idOrderSendActivityOrderDetailOfCustomer_MyOrder"));
        orderDetailArrayList = new ArrayList<>();
        databaseReference.child("ListOrder").child(firebaseUser.getUid().toString()).
                child(intent.getStringExtra("idOrderSendActivityOrderDetailOfCustomer_MyOrder")).
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
                new DividerItemDecoration(ActivityOrderDetail_MyOrder_Customer.this, DividerItemDecoration.VERTICAL);
        recyclerViewDrinkOfOrderDetail.addItemDecoration(dividerHorizontal);
        detailOrderAdapter = new DetailOrderAdapter(orderDetailArrayList,this);
        recyclerViewDrinkOfOrderDetail.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        recyclerViewDrinkOfOrderDetail.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewDrinkOfOrderDetail.setAdapter(detailOrderAdapter);
    }
    private void getInformationInOrder() {
        databaseReference.child("ListOrder").child(firebaseUser.getUid().toString()).
                child(intent.getStringExtra("idOrderSendActivityOrderDetailOfCustomer_MyOrder")).
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
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.ActivityOrderDetailOfCustomer_MyOrder_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Chi tiết đơn hàng");
        recyclerViewDrinkOfOrderDetail = (RecyclerView) findViewById(R.id.ActivityOrderDetailOfCustomer_MyOrder_recyclerDrink);
        txtMaOrder = (TextView) findViewById(R.id.ActivityOrderDetailOfCustomer_MyOrder_txtMaOrder);
        imgLocationShipper = (ImageView) findViewById(R.id.ActivityOrderDetailOfCustomer_MyOrder_imgLocationShipper);
        txtStatusThanhToan = (TextView) findViewById(R.id.ActivityOrderDetailOfCustomer_MyOrder_txtStatusThanhToan);
        txtStatusOrder = (TextView) findViewById(R.id.ActivityOrderDetailOfCustomer_MyOrder_txtStatusOrder);
        // txtStatusThanhToan = (TextView) findViewById(R.id.ActivityOrderOfCustomer_txtSta);
        txtName = (TextView) findViewById(R.id.ActivityOrderDetailOfCustomer_MyOrder_txtNameCustomer);
        txtPhone = (TextView) findViewById(R.id.ActivityOrderDetailOfCustomer_MyOrder_txtPhoneCustomer);
        txtAddress = (TextView) findViewById(R.id.ActivityOrderDetailOfCustomer_MyOrder_txtAddressCustomer);
        //  imgPhone = (ImageView) findViewById(R.id.ActivityOrderDetailShipper_imgCallPhone);
        //imgMessage = (ImageView) findViewById(R.id.ActivityOrderDetailShipper_imgMessage);
        txtMount = (TextView) findViewById(R.id.ActivityOrderDetailOfCustomer_MyOrder_txtMountDrinkDetail) ;
        btnHuyDonHang = (Button) findViewById(R.id.ActivityOrderDetailOfCustomer_MyOrder_btnHuyDonHang);
        txtThanhToan = (TextView) findViewById(R.id.ActivityOrderDetailOfCustomer_MyOrder_txtThanhToan);
        txtKhuyenMai = (TextView) findViewById(R.id.ActivityOrderDetailOfCustomer_MyOrder_txtKhuyenMai);
        txtPhiVanChuyen = (TextView) findViewById(R.id.ActivityOrderDetailOfCustomer_MyOrder_txtPhiVanChuyen);
        txtTotalThanhToan = (TextView) findViewById(R.id.ActivityOrderDetailOfCustomer_MyOrder_txtTongThanhToan);
        checkBoxChoXacNhan = (CheckBox) findViewById(R.id.ActivityOrderDetailOfCustomer_MyOrder_checkBoxChoXacNhan);
        checkBoxDaXacNhan = (CheckBox) findViewById(R.id.ActivityOrderDetailOfCustomer_MyOrder_checkBoxDaXacNhan);
        checkBoxDangChuanBiDo = (CheckBox) findViewById(R.id.ActivityOrderDetailOfCustomer_MyOrder_checkBoxDangChuanBiDo);
        checkBoxDangGiaoHang = (CheckBox) findViewById(R.id.ActivityOrderDetailOfCustomer_MyOrder_checkBoxDangGiaoHang);
        checkBoxDaGiaoHang = (CheckBox) findViewById(R.id.ActivityOrderDetailOfCustomer_MyOrder_checkBoxDaGiaoHang);
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
            case R.id.ActivityOrderDetailOfCustomer_MyOrder_btnHuyDonHang :
                DialogHuyHang();
                break;
            case R.id.ActivityOrderDetailOfCustomer_MyOrder_imgLocationShipper :
                Intent intent2 = new Intent(ActivityOrderDetail_MyOrder_Customer.this,FollowOrder.class);
                intent2.putExtra("idOrderSendFollowOrder",idOrder);
                intent2.putExtra("idShipperSendFollowOrder",idShipper);
                startActivity(intent2);
                break;
        }

    }
    public void DialogHuyHang() {
        AlertDialog.Builder alert = new AlertDialog.Builder(ActivityOrderDetail_MyOrder_Customer.this);
        alert.setTitle("Thông báo");
        alert.setIcon(R.drawable.ic_baseline_info_24);
        alert.setMessage("Bạn có chắc muốn đặt hàng ? ");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Intent intent = new Intent(getActivity(), Login_Customer.class);
//                startActivity(intent);
                huyDonHang();
                Toast.makeText(ActivityOrderDetail_MyOrder_Customer.this, "Bạn đã hủy đơn hàng thành công", Toast.LENGTH_SHORT).show();
                // finish();
                //  Toast.makeText(ActivityCart.this, "Bạn đã đặt hàng thành công", Toast.LENGTH_SHORT).show();
            }
        });
        alert.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog a = alert.create();
        a.show();
    }

    private void huyDonHang() {
        databaseReference.child("ListOrder").child(firebaseUser.getUid().toString()).
                child(intent.getStringExtra("idOrderSendActivityOrderDetailOfCustomer_MyOrder")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Order order = dataSnapshot.getValue(Order.class);
                if(order.getStatus()==1 || order.getStatus()==2)
                {
                    databaseReference.child("ListOrder").child(firebaseUser.getUid().toString()).
                            child(intent.getStringExtra("idOrderSendActivityOrderDetailOfCustomer_MyOrder")).child("status").setValue(0);

                    Toast.makeText(ActivityOrderDetail_MyOrder_Customer.this, "Bạn đã hủy đơn hàng thành công", Toast.LENGTH_SHORT).show();
                }
                else if(order.getStatus()==3 || order.getStatus()==4 || order.getStatus()==5)
                {
                    Toast.makeText(ActivityOrderDetail_MyOrder_Customer.this, "Hiện tại bạn không thể hủy đơn hàng", Toast.LENGTH_SHORT).show();
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
        intentCallPhone = new Intent();
        intentCallPhone.setAction(Intent.ACTION_VIEW);
        intentCallPhone.setData(Uri.parse("tel:" + phoneCustomerInOrder));
        startActivity(intentCallPhone);
    }
}