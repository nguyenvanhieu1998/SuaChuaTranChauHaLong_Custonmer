package com.example.suachuatranchauhalong_custonmer.Activity;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;

public class TrackGPS extends Service {
    String DonHangUID;
    DatabaseReference databaseReference;
    LocationManager locationManager;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    }

    LocationListener locationListenerGPS=new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            try{
                LatLng latLng= new LatLng(location.getLatitude(), location.getLongitude());



            }
            catch (Exception e){

            }
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
    @SuppressLint("MissingPermission")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        DonHangUID = intent.getStringExtra("DonHangUID");
        Log.d("DONHANGUID", DonHangUID);

        //Toast.makeText(TrackGPS.this, DonHangUID+"; vv",Toast.LENGTH_SHORT).show();

        @SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        try{
            LatLng latLng= new LatLng(location.getLatitude(), location.getLongitude());


        }
        catch (Exception e){};
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGPS);
        //return START_NOT_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        locationListenerGPS = null;
        databaseReference = null;
        locationManager = null;
        super.onDestroy();
    }
}