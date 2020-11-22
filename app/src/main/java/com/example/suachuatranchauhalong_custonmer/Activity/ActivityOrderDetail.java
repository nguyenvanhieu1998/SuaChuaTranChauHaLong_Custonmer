package com.example.suachuatranchauhalong_custonmer.Activity;

import androidx.appcompat.app.AppCompatActivity;
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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.suachuatranchauhalong_custonmer.Adapter.DetailOrderAdapter;
import com.example.suachuatranchauhalong_custonmer.Object.Customer;
import com.example.suachuatranchauhalong_custonmer.Object.ListenerIDOrder;
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
import android.os.Bundle;
import android.widget.Toast;

public class ActivityOrderDetail extends AppCompatActivity implements View.OnClickListener{
    RecyclerView recyclerViewDrinkOfOrderDetail;
    DetailOrderAdapter detailOrderAdapter;
    ArrayList<OrderDetail> orderDetailArrayList;
    ImageView imgLocationShipper,imgCallPhoneCustomer;
    TextView txtStatusThanhToan,txtStatusOrder,txtMount,txtMaOrder,txtName,txtPhone,txtAddress,txtThanhToan,txtPhiVanChuyen,txtKhuyenMai,txtTotalThanhToan;
    //ImageView imgPhone,imgMessage;
    Button btnXacNhanDonHang,btnDangChuanBiDo,btnHuyDonHang;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    Intent intent,intentCallPhone,intentMessage;
    String sđt = "";
    Toolbar toolbar;
    LinearLayout linearContent;
    TextView txtThongBao;
    CheckBox checkBoxChoXacNhan,checkBoxDaXacNhan,checkBoxDangChuanBiDo,checkBoxDangGiaoHang,checkBoxDaGiaoHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        initReferenceObject();
        // sendDataForDetailOrderAdapter();
        addControls();
      // check();
        checkStatusOrder();
        getInformationInOrder();
        getListDrinkDetail();
        addEvents();
    }

    boolean check = false;
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
        intent = getIntent();
    }
    private void checkStatusOrder()
    {
        databaseReference.child("ListOrder").child(intent.getStringExtra("idCustomer")).
                child(intent.getStringExtra("idOrder")).
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                            Order order = dataSnapshot.getValue(Order.class);
                                if(order.getStatus()==0)
                                {
                                    txtStatusThanhToan.setText("Chưa thanh toán");
                                    txtStatusOrder.setText("Trạng thái : Đã hủy" );
                                    btnDangChuanBiDo.setVisibility(View.INVISIBLE);
                                    btnXacNhanDonHang.setVisibility(View.INVISIBLE);
                                    btnHuyDonHang.setVisibility(View.INVISIBLE);
                                    checkBoxChoXacNhan.setChecked(false);
                                    checkBoxDaXacNhan.setChecked(false);
                                    checkBoxDangChuanBiDo.setChecked(false);
                                    checkBoxDangGiaoHang.setChecked(false);
                                    checkBoxDaGiaoHang.setChecked(false);
                                }
                                if(order.getStatus()==1)
                                {
                                    txtStatusThanhToan.setText("Chưa thanh toán");
                                   btnDangChuanBiDo.setVisibility(View.INVISIBLE);
                                    btnXacNhanDonHang.setVisibility(View.VISIBLE);
                                    btnHuyDonHang.setVisibility(View.VISIBLE);
                                    checkBoxChoXacNhan.setChecked(true);
                                    checkBoxDaXacNhan.setChecked(false);
                                    checkBoxDangChuanBiDo.setChecked(false);
                                    checkBoxDangGiaoHang.setChecked(false);
                                    checkBoxDaGiaoHang.setChecked(false);
                                }
                                if(order.getStatus()==2)
                                {
                                    txtStatusThanhToan.setText("Chưa thanh toán");
                                   btnDangChuanBiDo.setVisibility(View.VISIBLE);
                                    btnXacNhanDonHang.setVisibility(View.INVISIBLE);
                                    btnHuyDonHang.setVisibility(View.INVISIBLE);
                                    checkBoxChoXacNhan.setChecked(true);
                                    checkBoxDaXacNhan.setChecked(true);
                                    checkBoxDangChuanBiDo.setChecked(false);
                                    checkBoxDangGiaoHang.setChecked(false);
                                    checkBoxDaGiaoHang.setChecked(false);
                                }
                                if(order.getStatus()==3)
                                {
                                    txtStatusThanhToan.setText("Chưa thanh toán");
                                    btnDangChuanBiDo.setVisibility(View.INVISIBLE);
                                    btnXacNhanDonHang.setVisibility(View.INVISIBLE);
                                    btnHuyDonHang.setVisibility(View.INVISIBLE);
                                    checkBoxChoXacNhan.setChecked(true);
                                    checkBoxDaXacNhan.setChecked(true);
                                    checkBoxDangChuanBiDo.setChecked(true);
                                    checkBoxDangGiaoHang.setChecked(false);
                                    checkBoxDaGiaoHang.setChecked(false);
                                }
                                if(order.getStatus()==4)
                                {
                                    txtStatusThanhToan.setText("Chưa thanh toán");
                                    btnDangChuanBiDo.setVisibility(View.INVISIBLE);
                                    btnXacNhanDonHang.setVisibility(View.INVISIBLE);
                                    btnHuyDonHang.setVisibility(View.INVISIBLE);
                                  //  imgLocationShipper.setVisibility(View.VISIBLE);
                                    checkBoxChoXacNhan.setChecked(true);
                                    checkBoxDaXacNhan.setChecked(true);
                                    checkBoxDangChuanBiDo.setChecked(true);
                                    checkBoxDangGiaoHang.setChecked(true);
                                    checkBoxDaGiaoHang.setChecked(false);
                                }
                                if(order.getStatus()==5)
                                {
                                    txtStatusThanhToan.setText("Đã thanh toán");
                                    btnDangChuanBiDo.setVisibility(View.INVISIBLE);
                                    btnXacNhanDonHang.setVisibility(View.INVISIBLE);
                                    btnHuyDonHang.setVisibility(View.INVISIBLE);
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
        databaseReference.child("ListOrder").child(intent.getStringExtra("idCustomer")).
                child(intent.getStringExtra("idOrder")).
                child("ListOrderDetail").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orderDetailArrayList.clear();
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
                new DividerItemDecoration(ActivityOrderDetail.this, DividerItemDecoration.VERTICAL);
        recyclerViewDrinkOfOrderDetail.addItemDecoration(dividerHorizontal);
        detailOrderAdapter = new DetailOrderAdapter(orderDetailArrayList,this);
        recyclerViewDrinkOfOrderDetail.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        recyclerViewDrinkOfOrderDetail.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewDrinkOfOrderDetail.setAdapter(detailOrderAdapter);
    }
    private static String callPhoneCustomer;
    private void getInformationInOrder() {
        databaseReference.child("ListOrder").child(intent.getStringExtra("idCustomer"))
                .child(intent.getStringExtra("idOrder")).
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
                                callPhoneCustomer = customer.getPhoneCustomer().toString();
                                if(customer.getIdCustomer().toString().equals(firebaseUser.getUid().toString()))
                                {
                                    imgCallPhoneCustomer.setVisibility(View.INVISIBLE);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
//                        if(order.isStatusThanhToan()==true)
//                        {
//                            txtStatusThanhToan.setText("Đã thanh toán");
//                        }
//                        else
//                        {
//                            txtStatusThanhToan.setText("Chưa thanh toán");
//
//                        }
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
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.ActivityOrderDetail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Chi tiết đơn hàng");
        imgCallPhoneCustomer = (ImageView) findViewById(R.id.ActivityOrderDetail_imgPhoneCustomer);
        txtStatusThanhToan = (TextView) findViewById(R.id.ActivityOrderDetail_txtStatusThanhToan);
        txtThongBao = (TextView) findViewById(R.id.ActivityOrderDetail_txtThongBao);
        linearContent = (LinearLayout) findViewById(R.id.ActivityOrderDetail_linearContent);
        recyclerViewDrinkOfOrderDetail = (RecyclerView) findViewById(R.id.ActivityOrderDetail_recyclerDrink);
        txtMaOrder = (TextView) findViewById(R.id.ActivityOrderDetail_txtMaOrder);
        imgLocationShipper = (ImageView) findViewById(R.id.ActivityOrderDetail_imgLocationShipper);
        txtStatusOrder = (TextView) findViewById(R.id.ActivityOrderDetail_txtStatusOrder);
        // txtStatusThanhToan = (TextView) findViewById(R.id.ActivityOrderOfCustomer_txtSta);
        txtName = (TextView) findViewById(R.id.ActivityOrderDetail_txtNameCustomer);
        txtPhone = (TextView) findViewById(R.id.ActivityOrderDetail_txtPhoneCustomer);
        txtAddress = (TextView) findViewById(R.id.ActivityOrderDetail_txtAddressCustomer);
        //  imgPhone = (ImageView) findViewById(R.id.ActivityOrderDetailShipper_imgCallPhone);
        //imgMessage = (ImageView) findViewById(R.id.ActivityOrderDetailShipper_imgMessage);
        txtMount = (TextView) findViewById(R.id.ActivityOrderDetail_txtMountDrinkDetail) ;
        btnXacNhanDonHang = (Button) findViewById(R.id.ActivityOrderDetail_btnXacNhanDonHang);
        btnHuyDonHang = (Button) findViewById(R.id.ActivityOrderDetail_btnHuyDonHang);
        btnDangChuanBiDo = (Button) findViewById(R.id.ActivityOrderDetail_btnDangChuanBiDo);
        txtThanhToan = (TextView) findViewById(R.id.ActivityOrderDetail_txtThanhToan);
        txtKhuyenMai = (TextView) findViewById(R.id.ActivityOrderDetail_txtKhuyenMai);
        txtPhiVanChuyen = (TextView) findViewById(R.id.ActivityOrderDetail_txtPhiVanChuyen);
        txtTotalThanhToan = (TextView) findViewById(R.id.ActivityOrderDetail_txtTongThanhToan);
        checkBoxChoXacNhan = (CheckBox) findViewById(R.id.ActivityOrderDetail_checkBoxChoXacNhan);
        checkBoxDaXacNhan = (CheckBox) findViewById(R.id.ActivityOrderDetail_checkBoxDaXacNhan);
        checkBoxDangChuanBiDo = (CheckBox) findViewById(R.id.ActivityOrderDetail_checkBoxDangChuanBiDo);
        checkBoxDangGiaoHang = (CheckBox) findViewById(R.id.ActivityOrderDetail_checkBoxDangGiaoHang);
        checkBoxDaGiaoHang = (CheckBox) findViewById(R.id.ActivityOrderDetail_checkBoxDaGiaoHang);
    }
    private void addEvents()
    {
        //  imgMessage.setOnClickListener(this);
        //imgPhone.setOnClickListener(this);
        btnHuyDonHang.setOnClickListener(this);
        imgCallPhoneCustomer.setOnClickListener(this);
       btnDangChuanBiDo.setOnClickListener(this);
        btnXacNhanDonHang.setOnClickListener(this);
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
            case R.id.ActivityOrderDetail_btnDangChuanBiDo :
                DialogDangChuanBiDo();
                break;
            case R.id.ActivityOrderDetail_btnXacNhanDonHang :
                DialogXacNhanDonHang();
                break;
            case R.id.ActivityOrderDetail_imgPhoneCustomer :
                redirectActivityCallPhone(callPhoneCustomer);
                break;
            case R.id.ActivityOrderDetail_btnHuyDonHang :
                DialogHuyDonHang();
                break;

        }

    }

    private void DialogHuyDonHang() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Thông báo");
        alert.setIcon(R.drawable.ic_baseline_info_24);
        alert.setMessage("Hủy đơn hàng này ? ");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateStatusOrderToHuyDonHang();
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

    private void updateStatusOrderToHuyDonHang() {
//        databaseReference.child("ListOrder").child(intent.getStringExtra("idCustomer")).
//                child(intent.getStringExtra("idOrder")).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Order order = dataSnapshot.getValue(Order.class);
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
        databaseReference.child("ListOrder").child(intent.getStringExtra("idCustomer")).
                child(intent.getStringExtra("idOrder")).child("status").setValue(0);

        databaseReference.child("ListOrder").child(intent.getStringExtra("idCustomer")).
                child(intent.getStringExtra("idOrder")).child("statusCurrent").setValue(0);
        Toast.makeText(this, "Hủy đơn hàng thành công", Toast.LENGTH_SHORT).show();
    }

    private void DialogDangChuanBiDo() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Thông báo");
        alert.setIcon(R.drawable.ic_baseline_info_24);
        alert.setMessage("Trạng thái hiện tại của đơn hàng là đang chuẩn bị đồ");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateStatusOrderToDangChuanBiDo();
            }
        });
        AlertDialog a = alert.create();
        a.show();
    }

    private void updateStatusOrderToDangChuanBiDo() {
//        databaseReference.child("ListOrder").child(intent.getStringExtra("idCustomer")).
//                child(intent.getStringExtra("idOrder")).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Order order = dataSnapshot.getValue(Order.class);
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
        databaseReference.child("ListOrder").child(intent.getStringExtra("idCustomer")).
                child(intent.getStringExtra("idOrder")).child("status").setValue(3);

        Toast.makeText(this, "Đang chuẩn bị đồ cho đơn hàng", Toast.LENGTH_SHORT).show();
    }
    private void redirectActivityCallPhone(String phoneCustomerInOrder) {
        intentCallPhone  = new Intent();
        intentCallPhone.setAction(Intent.ACTION_VIEW);
        intentCallPhone.setData(Uri.parse("tel:" + phoneCustomerInOrder));
        startActivity(intentCallPhone);
    }

    public void DialogXacNhanDonHang()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Thông báo");
        alert.setIcon(R.drawable.ic_baseline_info_24);
        alert.setMessage("Xác nhận đơn hàng ? ");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateStatusOrderToXacNhanDonHang();
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
    private void updateStatusOrderToXacNhanDonHang() {
//        databaseReference.child("ListOrder").child(intent.getStringExtra("idCustomer")).
//                child(intent.getStringExtra("idOrder")).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Order order = dataSnapshot.getValue(Order.class);
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
        databaseReference.child("ListOrder").child(intent.getStringExtra("idCustomer")).
                child(intent.getStringExtra("idOrder")).child("status").setValue(2);
        Toast.makeText(this, "Đơn hàng đã được xác nhận", Toast.LENGTH_SHORT).show();
    }

}