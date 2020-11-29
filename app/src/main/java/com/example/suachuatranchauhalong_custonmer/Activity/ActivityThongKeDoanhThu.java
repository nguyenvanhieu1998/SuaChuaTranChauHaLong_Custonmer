package com.example.suachuatranchauhalong_custonmer.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.suachuatranchauhalong_custonmer.Object.Order;
import com.example.suachuatranchauhalong_custonmer.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ActivityThongKeDoanhThu extends AppCompatActivity {
    BarChart barChart;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    Spinner spinnerYear;
    Toolbar toolbar;
    static ArrayList<Integer> arrayListYear = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke_doanh_thu);
        initReferenceObject();
        addControls();
        initSpinner();
        getArrayYear();
    }
    private void getArrayYear()
    {

          databaseReference.child("ListOrder").addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot snapshot) {
                  for (DataSnapshot dataSnapshot : snapshot.getChildren())
                  {
                      for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                      {
                          Order order = dataSnapshot1.getValue(Order.class);

                              Date date1 = new Date(order.getDateOrder());
                          Log.w("size arraylist year 0", ""+ date1.getYear() );
                              if(date1.getYear() == yearOfSpinner)
                              {
                                  arrayListYear.add(date1.getYear());
                                  Log.w("size arraylist year 1", ""+ arrayListYear.size() );

                                  Log.w("yearOfSpinner", ""+ yearOfSpinner);
                              }

                      }
                  }
              }


              @Override
              public void onCancelled(@NonNull DatabaseError error) {

              }
          });
          Log.w("size arraylist year2 ", ""+ arrayListYear.size());
    }
    static int yearOfSpinner;
    private void initReferenceObject() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getInstance().getCurrentUser();
       // intent = getIntent();
    }
    String arr[]={
            "2020",
            "2021",
            "2022"};
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
        spinnerYear.setAdapter(adapter);
        spinnerYear.setOnItemSelectedListener(new MyProcessEvent());
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
            yearOfSpinner = Integer.parseInt(arr[i]);
            Toast.makeText(ActivityThongKeDoanhThu.this, "" + yearOfSpinner, Toast.LENGTH_SHORT).show();
        }

        //Nếu không chọn gì cả
        public void onNothingSelected(AdapterView<?> arg0) {
          //  selection.setText("");
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void addControls()
    {
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.ActivityThongKeDoanhThu_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Thống kê doanh thu");
        spinnerYear = (Spinner) findViewById(R.id.ActivityThongKeDoanhThu_spinnerYear);
        barChart = (BarChart) findViewById(R.id.ActivityThongKeDoanhThu_barChart);
        ArrayList<BarEntry> visitor = new ArrayList<>();
        visitor.add(new BarEntry(1,250));
        visitor.add(new BarEntry(2,222));
        visitor.add(new BarEntry(3,100));
        visitor.add(new BarEntry(4,350));
        visitor.add(new BarEntry(5,290));
        visitor.add(new BarEntry(6,220));
        visitor.add(new BarEntry(7,250));
        visitor.add(new BarEntry(8,100));
        visitor.add(new BarEntry(9,100));
        visitor.add(new BarEntry(10,150));
        visitor.add(new BarEntry(11,390));
        visitor.add(new BarEntry(12,220));

        BarDataSet barDataSet = new BarDataSet(visitor,"Doanh thu");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(11f);


        BarData barData = new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("");
        barChart.animateY(2000);
    }
}