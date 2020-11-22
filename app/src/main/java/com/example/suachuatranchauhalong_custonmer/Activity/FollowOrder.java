package com.example.suachuatranchauhalong_custonmer.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.example.suachuatranchauhalong_custonmer.Object.ListenerIdShipperGiaoHang;
import com.example.suachuatranchauhalong_custonmer.Object.Shipper;
import com.example.suachuatranchauhalong_custonmer.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FollowOrder extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap map;
    TextView txtName, txtPhone, txtAddress;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    Intent intent;
    String idCustomer;
    Toolbar toolbar;
    ListenerIdShipperGiaoHang listenerIdShipperGiaoHang;
    static MarkerOptions Shipper;
    Bitmap smallMarker;
    Location myLocation, shipperLocation;
    Intent intentCallPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_order);
        initReferenceObject();
        addControls();
        intiDataCustomer();
        //TuiDangODau();
         initDataGiaoHang();
        addEvents();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    LocationManager locationManager;
    Criteria criteria;
    private void TuiDangODau() {
//        map = ((MapFragment)getFragmentManager().
//                findFragmentById(R.id.myMap)).getMapAsync(this);
         locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
      //   criteria = new Criteria();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            return;
        }

    }

    private void addControls() {
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.ActivityFollowOrder_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Theo dõi đơn hàng");
        txtName = (TextView) findViewById(R.id.ActivityFollowOrder_txtName);
        txtPhone = (TextView) findViewById(R.id.ActivityFollowOrder_txtPhone);
        //   txtAddress = (TextView) findViewById(R.id.ActivityFollowOrder_txtAddress);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.myMap);
        mapFragment.getMapAsync(this);
          Shipper = new MarkerOptions();
          CreateMarker();
    }

    void CreateMarker() {
        int height = 80;
        int width = 80;
        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.shipper);
        Bitmap b = bitmapdraw.getBitmap();
        smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
    }

    private void addEvents() {
        txtPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivityCallPhone(phoneCustomer);
            }
        });
    }
    private void redirectActivityCallPhone(String shipper) {
        intentCallPhone  = new Intent();
        intentCallPhone.setAction(Intent.ACTION_VIEW);
        intentCallPhone.setData(Uri.parse("tel:" + shipper));
        startActivity(intentCallPhone);
    }

    private void initReferenceObject() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        intent = getIntent();
        listenerIdShipperGiaoHang = new ListenerIdShipperGiaoHang();
        // idCustomer = intent.getStringExtra("idCustomerLocation");
    }

    private void initDataGiaoHang() {
        if (listenerIdShipperGiaoHang.getIdOrder().toString() != null) {
            databaseReference.child("ListOrder").child(firebaseUser.getUid().toString()).
                    child(listenerIdShipperGiaoHang.getIdOrder().toString()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    double shipperLatitude, shipperlongitude;
                    try {
                        shipperLatitude = Double.parseDouble(snapshot.child("Shipper").child("Location").child("latitude").getValue().toString());
                        shipperlongitude = Double.parseDouble(snapshot.child("Shipper").child("Location").child("longitude").getValue().toString());
                    } catch (Exception e) {
                        shipperLatitude = 20.985101;
                        shipperlongitude = 105.838750;
                    }
                    shipperLocation = new Location("ShipperLocation");
                    shipperLocation.setLongitude(shipperlongitude);
                    shipperLocation.setLatitude(shipperLatitude);

                    double myLatitude = Double.parseDouble(snapshot.child("Location").child("latitude").getValue().toString());
                    double myLongitude = Double.parseDouble(snapshot.child("Location").child("longitude").getValue().toString());
                    myLocation = new Location("MyLocation");
                    myLocation.setLongitude(myLongitude);
                    myLocation.setLatitude(myLatitude);

                    LatLng position = new LatLng(shipperLocation.getLatitude(), shipperLocation.getLongitude());
                    Shipper = new MarkerOptions()
                            .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                            .title("Shipper")
                            .snippet("ĐANG GIAO")
                            .position(position);

                    //map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));
                    map.clear();
                    map.addMarker(Shipper);

                    LatLng myPosition = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                    map.addMarker(new MarkerOptions()
                            .title("My Location")
                            .snippet("Pháp Vân")
                            .position(myPosition));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
    static String phoneCustomer;
    private void intiDataCustomer() {
        try {
            databaseReference.child("ListShipper").child(listenerIdShipperGiaoHang.getIdShipper().toString()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        Shipper shipper = dataSnapshot.getValue(Shipper.class);
                        txtName.setText("Họ tên : " + shipper.getNameShipper().toString());
                        txtPhone.setText("" + shipper.getPhoneShipper().toString());
                        phoneCustomer= shipper.getPhoneShipper().toString();
                    } catch (NullPointerException ex) {

                    }
                    //  txtAddress.setText("Địa chỉ : " + customer.getAddressCustomer().toString());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (NullPointerException ex) {

        }

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.getUiSettings().setZoomControlsEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);
        if(listenerIdShipperGiaoHang.getIdOrder().toString()!=null)
        {
            databaseReference.child("ListOrder").child(firebaseUser.getUid().toString()).
                    child(listenerIdShipperGiaoHang.getIdOrder().toString()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    double myLatitude = Double.parseDouble(snapshot.child("Location").child("latitude").getValue().toString());
                    double myLongitude =  Double.parseDouble(snapshot.child("Location").child("longitude").getValue().toString());
                    myLocation = new Location("MyLocation");
                    myLocation.setLongitude(myLongitude);
                    myLocation.setLatitude(myLatitude);


                    if(snapshot.child("Shipper").child("Location").child("latitude").exists() && snapshot.child("Shipper").child("Location").child("longitude").exists()) {
                        Location ShipperLocation = new Location("Shipper");
                        ShipperLocation.setLongitude(Double.parseDouble(snapshot.child("Shipper").child("Location").child("longitude").getValue().toString()));
                        ShipperLocation.setLatitude(Double.parseDouble(snapshot.child("Shipper").child("Location").child("latitude").getValue().toString()));
                        LatLng ShipperPosition = new LatLng(ShipperLocation.getLatitude(), ShipperLocation.getLongitude());
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ShipperPosition, 13));
                    }

                    else{
                        LatLng position = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));
                        googleMap.addMarker(new MarkerOptions()
                                .title("Shipper")
                                .snippet("ĐANG GIAO")
                                .position(position));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}