package com.example.suachuatranchauhalong_custonmer.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suachuatranchauhalong_custonmer.Adapter.DetailOrderAdapter;
import com.example.suachuatranchauhalong_custonmer.Adapter.FragmentUpdateDrinkOfCart_OrderDetailAdapter;
import com.example.suachuatranchauhalong_custonmer.FragmentDialog.FragmentDialogHiddenMenuDrinkForAdmin;
import com.example.suachuatranchauhalong_custonmer.FragmentDialog.FragmentDialogListVoucherOfCart;
import com.example.suachuatranchauhalong_custonmer.FragmentDialog.FragmentDialogUpdateDrinkOfCart;
import com.example.suachuatranchauhalong_custonmer.InterfaceOnClick.OnItemClickListener;
import com.example.suachuatranchauhalong_custonmer.Object.Customer;
import com.example.suachuatranchauhalong_custonmer.Object.Drink;
import com.example.suachuatranchauhalong_custonmer.Object.ListenerIdOrderDetail;
import com.example.suachuatranchauhalong_custonmer.Object.ListenerIdVoucher;
import com.example.suachuatranchauhalong_custonmer.Object.ListenerPriceOrderOfCart;
import com.example.suachuatranchauhalong_custonmer.Object.MenuDrink;
import com.example.suachuatranchauhalong_custonmer.Object.Order;
import com.example.suachuatranchauhalong_custonmer.Object.OrderDetail;
import com.example.suachuatranchauhalong_custonmer.Object.OrderDetail2;
import com.example.suachuatranchauhalong_custonmer.Object.Shipper;
import com.example.suachuatranchauhalong_custonmer.Object.Voucher;
import com.example.suachuatranchauhalong_custonmer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ActivityCart extends AppCompatActivity implements OnItemClickListener,ListenerIdVoucher {
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    RecyclerView recyclerViewDrinkOfOrderDetail;
    FragmentUpdateDrinkOfCart_OrderDetailAdapter detailOrderAdapter;
    ArrayList<OrderDetail> orderDetailArrayList;
    TextView txtMount, txtStatusThanhToan, txtName, txtPhone, txtAddress, txtThanhToan, txtPhiVanChuyen, txtKhuyenMai, txtTotalThanhToan;
    ImageView imgPhone, imgMessage;
    Button btnDatHang;
    Intent intent;
    Toolbar toolbar;
    Location location;
    LocationManager locationManager;
    String diaChi = "";
    ValueEventListener valueEventListener;
    ListenerPriceOrderOfCart listenerPriceOrderOfCart;
    double lat = 20.985045682596606;
    double log = 105.83865921205458;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initReferenceObject();
        addControls();
        checkPermission();
        getPricePromotion();
        //InitAddress();
        InitAddress();
        addEvents();
       getAllDataInOrder();

    }

//    public ActivityCart(float khuyenMaiiiii)
//    {
//          khuyenMai = khuyenMaiiiii;
//    }

    private void checkPermission() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 12);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
    }
    static int chekckk;
    private void addEvents() {
        databaseReference.child("ListCustomer").child(firebaseUser.getUid().toString()).child("ListVoucher").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists())
                {
                    chekckk=0;

                }
                else
                {
                    chekckk=1;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogDatHang();
            }
        });
        btnApDungVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chekckk==0)
                {
                    Toast.makeText(ActivityCart.this, "Bạn hiện tại không có voucher nào", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    FragmentDialogListVoucherOfCart fragmentDialogListVoucherOfCart = new FragmentDialogListVoucherOfCart();
                    fragmentDialogListVoucherOfCart.show(getSupportFragmentManager(),"fragmentDialogListVoucherOfCart");
                }

            }
        });
    }

    private void InitAddress() {
        try {
            diaChi = GetAddress();
        } catch (Exception e) {
            diaChi = "96 Định Công, Phương Liệt, Thanh Xuân, Hà Nội";
//            location = new Location("ok");
//            location.setLatitude(20.985101);
//            location.setLongitude(105.838750);
        }
        Log.d("Address location : ",diaChi);
    }

    private String GetAddress() {
        String DiaChi = "";
        Geocoder geocoder = new Geocoder(ActivityCart.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            DiaChi = addresses.get(0).getAddressLine(0);
            Log.d("AddLine", addresses.get(0).getAddressLine(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return null;
//        }
        return DiaChi;
    }
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location position) {
            Geocoder geocoder = new Geocoder(ActivityCart.this, Locale.getDefault());
            try {
                location.setLatitude(position.getLatitude());
                location.setLongitude(position.getLongitude());

                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                diaChi = addresses.get(0).getAddressLine(0);
                Log.d("AddLine", addresses.get(0).getAddressLine(0));
            } catch (Exception e){}
        }


        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    public void DialogDatHang() {
        AlertDialog.Builder alert = new AlertDialog.Builder(ActivityCart.this);
        alert.setTitle("Thông báo");
        alert.setIcon(R.drawable.ic_baseline_info_24);
        alert.setMessage("Bạn có chắc muốn đặt hàng ? ");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Intent intent = new Intent(getActivity(), Login_Customer.class);
//                startActivity(intent);
                updateIdOrder();
                Toast.makeText(ActivityCart.this, "Bạn đã đặt hàng thành công", Toast.LENGTH_SHORT).show();
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

   // String key_order;20.985045682596606, 105.83865921205458

    private void updateIdOrder() {
        final String dateOrder;
        Calendar calen = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy");
        dateOrder =  simpleDateFormat.format(calen.getTime());
      final  String key_order = databaseReference.child("ListOrder").child(firebaseUser.getUid().toString()).push().getKey();
        Order order = new Order(key_order, firebaseUser.getUid().toString(),
                mount, price, khuyenMai, phiVanChuyen, totalThanhToan, dateOrder,
                1, false, 1);
        databaseReference.child("ListOrder").child(firebaseUser.getUid().toString()).child(key_order).setValue(order);
        databaseReference.child("ListOrderDetail").child(firebaseUser.getUid().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    OrderDetail orderDetail = dataSnapshot1.getValue(OrderDetail.class);
                    if (orderDetail.getIdOrder().toString().equals("")) {
                        try {
                            databaseReference.child("ListOrderDetail").child(firebaseUser.getUid().toString()).
                                    child(orderDetail.getIdOrderDetail()).child("idOrder").setValue(key_order);

//                            databaseReference.child("ListOrder").child(firebaseUser.getUid().toString()).child(key_order).child("Location").child("latitude").setValue(location.getLatitude() + "");
//                            databaseReference.child("ListOrder").child(firebaseUser.getUid().toString()).child(key_order).child("Location").child("longitude").setValue(location.getLongitude() + "");
//
                            databaseReference.child("ListOrder").child(firebaseUser.getUid().toString()).child(key_order).child("Location").child("latitude").setValue(lat);
                            databaseReference.child("ListOrder").child(firebaseUser.getUid().toString()).child(key_order).child("Location").child("longitude").setValue(log);

                            databaseReference.child("ListOrder").child(firebaseUser.getUid().toString()).
                                    child(key_order).child("ListOrderDetail").
                                    child(orderDetail.getIdOrderDetail()).setValue(orderDetail);

                            databaseReference.child("ListOrder").child(firebaseUser.getUid().toString()).
                                    child(key_order).child("ListOrderDetail").
                                    child(orderDetail.getIdOrderDetail()).child("idOrder").setValue(key_order);
                        }catch (NullPointerException ex){

                        }
                        //    Drink drink = dataSnapshot.getValue(Drink.class);

                    }

                }
                //    detailOrderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        finish();
        Intent intent = new Intent(this, OrderDetailOfCustomer.class);
        intent.putExtra("idOrder",key_order);
        startActivity(intent);
       // overridePendingTransition(R.anim.anim_intent_trai_sang_phai,R.anim.anim_intent_exit);

    }
//    private void updateIdOrder2() {
//        final String dateOrder;
//        Calendar calen = Calendar.getInstance();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy");
//        dateOrder = "Thời gian : " + simpleDateFormat.format(calen.getTime());
//        key_order = databaseReference.child("ListOrder").push().getKey();
//        Order order = new Order(key_order, firebaseUser.getUid().toString(),
//                mount, price, khuyenMai, phiVanChuyen, totalThanhToan, dateOrder,
//                1, false, 1);
//        databaseReference.child("ListOrder").child(key_order).setValue(order);
//        valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                try {
//                    for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
//                        OrderDetail orderDetail = dataSnapshot1.getValue(OrderDetail.class);
//                        if (orderDetail.getIdOrder().toString().equals("")) {
//                            //    Drink drink = dataSnapshot.getValue(Drink.class);
//                            databaseReference.child("ListOrderDetail").
//                                    child(orderDetail.getIdOrderDetail()).child("idOrder").setValue(key_order);
//
//                            databaseReference.child("ListOrder").child(key_order).child("Location").child("latitude").setValue(location.getLatitude() + "");
//                            databaseReference.child("ListOrder").child(key_order).child("Location").child("longitude").setValue(location.getLongitude() + "");
////                                totalPriceOrder += drink.getPriceDrink()*orderDetail.getMount();
////                                txtPriceOfOrder.setText(""+totalPriceOrder);
//                            databaseReference.child("ListOrder").
//                                    child(key_order).child("ListOrderDetail").
//                                    child(orderDetail.getIdOrderDetail()).setValue(orderDetail);
//                        }
//
//                    }
//                }catch (NullPointerException ex){
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        };
//        databaseReference.child("ListOrderDetail").child(firebaseUser.getUid().toString()).addValueEventListener(valueEventListener);
////
////
////        databaseReference.child("ListOrderDetail").addValueEventListener(new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////
////
////                //    detailOrderAdapter.notifyDataSetChanged();
////            }
////
////            @Override
////            public void onCancelled(@NonNull DatabaseError databaseError) {
////
////            }
////        });
//       // finish();
//        Intent intent = new Intent(this, OrderCurrentOfCustomer.class);
//        //    intent.putExtra("idOrder",key_order);
//        startActivity(intent);
//
//    }

    @Override
    protected void onDestroy() {
        try {
            databaseReference = null;
        }catch (NullPointerException ex){

        }
        super.onDestroy();
    }

    private void initReferenceObject() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        intent = getIntent();
        listenerPriceOrderOfCart = new ListenerPriceOrderOfCart();
    }
    Button btnApDungVoucher;
    private void addControls() {
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.ActivityAddCart_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Giỏ hàng");
        btnApDungVoucher = (Button) findViewById(R.id.ActivityCart_btnApDungVoucher);
        recyclerViewDrinkOfOrderDetail = (RecyclerView) findViewById(R.id.ActivityCart_recyclerDrink);
        // txtIDBill = (TextView) findViewById(R.id.ActivityOrderDetailShipper_txtBill);
        txtStatusThanhToan = (TextView) findViewById(R.id.ActivityCart_txtThanhToan);
        txtName = (TextView) findViewById(R.id.ActivityCart_txtNameCustomer);
        txtPhone = (TextView) findViewById(R.id.ActivityCart_txtPhoneCustomer);
        txtAddress = (TextView) findViewById(R.id.ActivityCart_txtAddressCustomer);
        // imgPhone = (ImageView) findViewById(R.id.ActivityOrderDetailShipper_imgCallPhone);
        // imgMessage = (ImageView) findViewById(R.id.ActivityOrderDetailShipper_imgMessage);
        btnDatHang = (Button) findViewById(R.id.ActivityCart_btnDatHang);
        txtThanhToan = (TextView) findViewById(R.id.ActivityCart_txtThanhToan);
        txtKhuyenMai = (TextView) findViewById(R.id.ActivityCart_txtKhuyenMai);
        txtPhiVanChuyen = (TextView) findViewById(R.id.ActivityCart_txtPhiVanChuyen);
        txtTotalThanhToan = (TextView) findViewById(R.id.ActivityCart_txtTongThanhToan);
        txtMount = (TextView) findViewById(R.id.ActivityCart_txtMount);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    int mount;
    float price, khuyenMai, totalThanhToan;
    float phiVanChuyen;
    public static double distanceBetween2Points(double la1, double lo1, double la2, double lo2) {
        double R = 6371;
        double dLat = (la2 - la1) * (Math.PI / 180);
        double dLon = (lo2 - lo1) * (Math.PI / 180);
        double la1ToRad = la1 * (Math.PI / 180);
        double la2ToRad = la2 * (Math.PI / 180);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(la1ToRad)
                * Math.cos(la2ToRad) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c;
        return d;
    }
   static int giaTriKhuyenMai;
    private void getPricePromotion()
    {
        databaseReference.child("ListOrderDetail").child(firebaseUser.getUid().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    final OrderDetail orderDetail = dataSnapshot1.getValue(OrderDetail.class);
                    if (orderDetail.getIdOrder().toString().equals("")) {
                        try {
                            giaTriKhuyenMai = Integer.parseInt(dataSnapshot1.child("pricePromotion").getValue().toString());
                        }catch (NullPointerException ex)
                        {

                        }

                    }

                }
                //     khuyenMai = listenerPriceOrderOfCart.getGiaTriKhuyenMai();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void getAllDataInOrder() {

//         mount = Integer.parseInt(intent.getStringExtra("Mount"));
//         price  = Float.parseFloat(intent.getStringExtra("Price"));
        phiVanChuyen = (int) distanceBetween2Points(21.009550515590462, 105.82018764110443,location.getLatitude(),location.getLongitude())*7000;
//         khuyenMai =0;
//        txtMount.setText("Số lượng : " + intent.getStringExtra("Mount"));
//        txtThanhToan.setText("Thanh toán : "+intent.getStringExtra("Price"));
//        totalThanhToan = price+phiVanCHuyen+khuyenMai;
//        txtTotalThanhToan.setText("Tổng thanh toán : "+totalThanhToan);
//        txtKhuyenMai.setText("Khuyến mãi : " + khuyenMai);
        txtPhiVanChuyen.setText("Phí vận chuyển : " + phiVanChuyen);
        Log.d("Phí vận chuyển : ","" + phiVanChuyen);
        Log.d("Vị trí khách hàng : ","lat : " + location.getLatitude() + "log : " + location.getLongitude());
        orderDetailArrayList = new ArrayList<>();
        databaseReference.child("ListCustomer").child(firebaseUser.getUid().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Customer customer = dataSnapshot.getValue(Customer.class);
                txtName.setText("Họ tên : " + customer.getNameCustomer());
                txtPhone.setText("SĐT : " + customer.getPhoneCustomer());
                txtAddress.setText("Địa chỉ : " + customer.getAddressCustomer());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //Lấy ra thông tin đơn hàng
        databaseReference.child("ListOrderDetail").child(firebaseUser.getUid().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mount = 0;
                price = 0;
                orderDetailArrayList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    final OrderDetail orderDetail = dataSnapshot1.getValue(OrderDetail.class);
                    if (orderDetail.getIdOrder().toString().equals("")) {
                        mount += orderDetail.getMount();
                        price += orderDetail.getPrice();
                        //    Drink drink = dataSnapshot.getValue(Drink.class);
                        orderDetailArrayList.add(orderDetail);
//                                totalPriceOrder += drink.getPriceDrink()*orderDetail.getMount();
//                                txtPriceOfOrder.setText(""+totalPriceOrder);

                    }

                }
                khuyenMai = (price*giaTriKhuyenMai)/100;
                txtKhuyenMai.setText("Khuyến mãi : " + khuyenMai);
                txtMount.setText("Số lượng : " + mount);
                txtThanhToan.setText("Thanh toán : " + price);
                listenerPriceOrderOfCart.setPriceOrder(price);
                totalThanhToan = price + phiVanChuyen - khuyenMai;
                txtTotalThanhToan.setText("Tổng thanh toán : " + totalThanhToan);
                detailOrderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        DividerItemDecoration dividerHorizontal =
                new DividerItemDecoration(ActivityCart.this, DividerItemDecoration.VERTICAL);

        recyclerViewDrinkOfOrderDetail.addItemDecoration(dividerHorizontal);
        detailOrderAdapter = new FragmentUpdateDrinkOfCart_OrderDetailAdapter(orderDetailArrayList, this, this);
        recyclerViewDrinkOfOrderDetail.setAdapter(detailOrderAdapter);
        recyclerViewDrinkOfOrderDetail.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        recyclerViewDrinkOfOrderDetail.setLayoutManager(new LinearLayoutManager(this));
    }
    public void setPricePromotion(float pricePromotion)
    {
        txtKhuyenMai = (TextView) findViewById(R.id.ActivityCart_txtKhuyenMai);
        khuyenMai = (price*pricePromotion)/100;
        khuyenMai = listenerPriceOrderOfCart.getGiaTriKhuyenMai();
        txtKhuyenMai.setText("Khuyến mãi : "+khuyenMai);
    }

    @Override
    protected void onResume() {
        khuyenMai = listenerPriceOrderOfCart.getGiaTriKhuyenMai();
        super.onResume();
    }

    private void getListOrderDetail() {
        databaseReference.child("ListOrderDetail").child(firebaseUser.getUid().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orderDetailArrayList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    final OrderDetail orderDetail = dataSnapshot1.getValue(OrderDetail.class);
                    if (orderDetail.getIdOrder().toString().equals("")) {

                        //    Drink drink = dataSnapshot.getValue(Drink.class);
                        orderDetailArrayList.add(orderDetail);
//                                totalPriceOrder += drink.getPriceDrink()*orderDetail.getMount();
//                                txtPriceOfOrder.setText(""+totalPriceOrder);

                    }

                }
                detailOrderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        DividerItemDecoration dividerHorizontal =
                new DividerItemDecoration(ActivityCart.this, DividerItemDecoration.VERTICAL);

        recyclerViewDrinkOfOrderDetail.addItemDecoration(dividerHorizontal);
        detailOrderAdapter = new FragmentUpdateDrinkOfCart_OrderDetailAdapter(orderDetailArrayList, this, this);
        recyclerViewDrinkOfOrderDetail.setAdapter(detailOrderAdapter);
        recyclerViewDrinkOfOrderDetail.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        recyclerViewDrinkOfOrderDetail.setLayoutManager(new LinearLayoutManager(this));
    }

    private void Dilaog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.fragment_dialog_update_drink_of_cart);
        dialog.setCanceledOnTouchOutside(true);
        //Khi click vào màn hình thì dialog sẽ k bị tắt trừ khi bấm nút hủy
        initReferenceObject();
        addControlsDialog(dialog);
        listenerIdOrderDetail = new ListenerIdOrderDetail();
        initData();
        addEventsDialog(dialog);
        dialog.setTitle("Cập nhật giỏ hàng");
        dialog.show();
    }

    ImageView imgDrink, imgPlus, imgMinus;
    Button btnUpdateMountDrink, btnCancel, btnDeleteDrink;
    TextView txtNameDrink, txtMountDrink, txtBonus, txtMountDrink2;
    //    ListenerTypeNews listenerTypeNews;
    Boolean checkChooseImageMenuDrink = false;
    FragmentUpdateDrinkOfCart_OrderDetailAdapter fragmentUpdateDrinkOfCart_OrderDetailAdapter;
    Boolean checkUpdateImage = false;
    String nameMenuDrink;
    ListenerIdOrderDetail listenerIdOrderDetail;

    private void addEventsDialog(final Dialog dialog) {
        btnUpdateMountDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateMountDrink();
                getListOrderDetail();
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnDeleteDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDrinkOfCart();
                getListOrderDetail();
                dialog.dismiss();
            }
        });
        imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minusMountDrink();
            }
        });
        imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plusMountDrink();
            }
        });
    }

    private void deleteDrinkOfCart() {
        databaseReference.child("ListOrderDetail").child(firebaseUser.getUid().toString()).
                child(listenerIdOrderDetail.getIdOrderDetail().toString()).removeValue();
    }

    private void addControlsDialog(Dialog dialog) {
        imgDrink = (ImageView) dialog.findViewById(R.id.FragmentDialogUpdateDrinkOfCart_imgDrink);
        imgPlus = (ImageView) dialog.findViewById(R.id.FragmentDialogUpdateDrinkOfCart_imgPlus);
        imgMinus = (ImageView) dialog.findViewById(R.id.FragmentDialogUpdateDrinkOfCart_imgMinus);
        btnCancel = (Button) dialog.findViewById(R.id.FragmentDialogUpdateDrinkOfCart_btnCancel);
        btnUpdateMountDrink = (Button) dialog.findViewById(R.id.FragmentDialogUpdateDrinkOfCart_btnUpdate);
        btnDeleteDrink = (Button) dialog.findViewById(R.id.FragmentDialogUpdateDrinkOfCart_btnDelete);
        txtNameDrink = (TextView) dialog.findViewById(R.id.FragmentDialogUpdateDrinkOfCart_txtNameDrink);
        txtMountDrink = (TextView) dialog.findViewById(R.id.FragmentDialogUpdateDrinkOfCart_txtMountDrink);
        txtMountDrink2 = (TextView) dialog.findViewById(R.id.FragmentDialogUpdateDrinkOfCart_txtMount2);
        txtBonus = (TextView) dialog.findViewById(R.id.FragmentDialogUpdateDrinkOfCart_txtBonus);
        // recyclerViewDrinkOfOrderDetail = (RecyclerView) dialog.findViewById(R.id.FragmentDialogUpdateDrinkOfCart_recycleDrink);
    }

    private void updateMountDrink() {
        databaseReference.child("ListOrderDetail").child(firebaseUser.getUid().toString()).
                child(listenerIdOrderDetail.getIdOrderDetail().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    OrderDetail orderDetail = dataSnapshot.getValue(OrderDetail.class);
                    totalPriceBanDau = orderDetail.getPrice();
                    priceGoiThem = orderDetail.getPriceBonus();
                    databaseReference.child("ListDrink").child(orderDetail.getIdDrink().toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Drink drink = dataSnapshot.getValue(Drink.class);
                            Picasso.get()
                                    .load(drink.getImgUriDrink())
                                    .fit()
                                    .into(imgDrink);
                            txtNameDrink.setText(drink.getNameDrink().toString());
                            priceDrink = drink.getPriceDrink();
                            totalPrice = (mount2 * priceDrink) + (priceGoiThem * mount2);
//                nameDrink = drink.getNameDrink().toString();
//                priceDrink = drink.getPriceDrink();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
//                if(Integer.parseInt(txtMountDrink2.getText().toString())==orderDetail.getMount())
//                {
//                    Toast.makeText(getActivity(), "Bạn đã không thay đổi gì", Toast.LENGTH_SHORT).show();
//                }
//                else {
                    databaseReference.child("ListOrderDetail").child(firebaseUser.getUid().toString()).
                            child(listenerIdOrderDetail.getIdOrderDetail().toString()).child("mount").setValue(mount2);
                    databaseReference.child("ListOrderDetail").child(firebaseUser.getUid().toString()).
                            child(listenerIdOrderDetail.getIdOrderDetail().toString()).child("price").setValue(totalPrice);

                } catch (NullPointerException ex) {

                }

                // }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
    }

    int mount2;
    float priceDrink, priceGoiThem;
    float totalPrice = (mount2 * priceDrink) + (priceGoiThem * mount2), totalPriceBanDau;


    public void initData() {
        mount2 = Integer.parseInt(txtMountDrink2.getText().toString());
        databaseReference.child("ListOrderDetail").child(firebaseUser.getUid().toString()).
                child(listenerIdOrderDetail.getIdOrderDetail().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    OrderDetail orderDetail = dataSnapshot.getValue(OrderDetail.class);
                    mount2 = orderDetail.getMount();
                    txtMountDrink.setText("Số lượng : " + mount2);
                    txtBonus.setText("Gọi thêm : " + orderDetail.getBonus().toString());
                    txtMountDrink2.setText("" + mount2);
                    priceGoiThem = orderDetail.getPriceBonus();
                    // totalPriceBanDau = orderDetail.getPrice();
                    databaseReference.child("ListDrink").child(orderDetail.getIdDrink().toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Drink drink = dataSnapshot.getValue(Drink.class);
                            Picasso.get()
                                    .load(drink.getImgUriDrink())
                                    .fit()
                                    .into(imgDrink);
                            txtNameDrink.setText(drink.getNameDrink().toString());
                            priceDrink = drink.getPriceDrink();
                            //  totalPrice = (mount*priceDrink)+(priceGoiThem*mount);
//                nameDrink = drink.getNameDrink().toString();
//                priceDrink = drink.getPriceDrink();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } catch (NullPointerException ex) {

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //  Toast.makeText(getActivity(), "" + totalPriceBanDau, Toast.LENGTH_SHORT).show();
    }

    private void minusMountDrink() {
        if (mount2 > 1) {
            mount2--;
        }

        txtMountDrink.setText("Số lượng : " + mount2);
        txtMountDrink2.setText("" + mount2);
        totalPrice = (mount2 * priceDrink) + (priceGoiThem * mount2);
        Toast.makeText(this, "" + totalPrice, Toast.LENGTH_SHORT).show();
        // btnTotalMoney.setText("+ " + totalPrice +" đ");
    }

    private void plusMountDrink() {
        mount2++;
        txtMountDrink.setText("Số lượng : " + mount2);
        txtMountDrink2.setText("" + mount2);
        totalPrice = (mount2 * priceDrink) + (priceGoiThem * mount2);
        Toast.makeText(this, "" + totalPrice, Toast.LENGTH_SHORT).show();
        // btnTotalMoney.setText("+ " + totalPrice +" đ");
    }

    @Override
    public void onItemClickListener(MenuDrink menuDrink) {

    }

    @Override
    public void onItemClickListener(Customer customer) {

    }

    @Override
    public void onItemClickListener(Shipper shipper) {

    }

    @Override
    public void onItemClickListener(OrderDetail orderDetail) {
        Dilaog();
//        FragmentDialogUpdateDrinkOfCart fragmentDialogUpdateDrinkOfCart = new FragmentDialogUpdateDrinkOfCart();
//        fragmentDialogUpdateDrinkOfCart.show(getSupportFragmentManager(),"fragmentDialogUpdateDrinkOfCart");
    }


    @Override
    public void getDataVoucher(Voucher voucher) {
       // khuyenMai = (price*pricePromotion)/100;
       // khuyenMai = listenerPriceOrderOfCart.getGiaTriKhuyenMai();
        txtKhuyenMai.setText("Khuyến mãi : " + voucher.getPricePromotion());
    }
}