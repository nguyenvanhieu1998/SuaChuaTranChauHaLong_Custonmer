package com.example.suachuatranchauhalong_custonmer.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suachuatranchauhalong_custonmer.Object.Order;
import com.example.suachuatranchauhalong_custonmer.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityThongKeDonHang extends AppCompatActivity implements OnChartValueSelectedListener {
    private PieChart mChart;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    Toolbar toolbar;
    TextView txtTottalOrder,txtOrderCancel,txtOrderShipped;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke_don_hang);
        initReferenceObject();
        addControls();
        initChart();
    }
    private void initReferenceObject() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getInstance().getCurrentUser();
    }
    private void initChart()
    {
        mChart = (PieChart) findViewById(R.id.piechart);
        mChart.setRotationEnabled(true);
        mChart.setDescription(new Description());
        mChart.setHoleRadius(35f);
        mChart.setTransparentCircleAlpha(0);
        mChart.setCenterText("Đơn hàng");
        mChart.setCenterTextSize(10);
        mChart.setDrawEntryLabels(true);

        addDataSet(mChart);

        mChart.setOnChartValueSelectedListener(this);
    }
    private void addControls()
    {
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.ActivityThongKeHoaDon_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Thống kê đơn hàng");
        txtTottalOrder = (TextView) findViewById(R.id.ActivityThongKeHoaDon_txtTottalOrder);
        txtOrderCancel = (TextView) findViewById(R.id.ActivityThongKeHoaDon_txtOrderCancel);
        txtOrderShipped = (TextView) findViewById(R.id.ActivityThongKeHoaDon_txtOrderShipped);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void addEvents()
    {

    }
    private int countOrderCancel,countOrderShipped,tottalOrder;
    private void addDataSet(final PieChart pieChart) {
        final ArrayList<PieEntry> yEntrys = new ArrayList<>();
        final ArrayList<String> xEntrys = new ArrayList<>();
        databaseReference.child("ListOrder").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                countOrderCancel=0;
                countOrderShipped=0;
                tottalOrder=0;
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren())
                {
                    for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren())
                    {
                        Order order = dataSnapshot2.getValue(Order.class);
                        tottalOrder++;
                        if(order.getStatus()==0)
                        {
                            countOrderCancel++;
                        }
                        else if(order.getStatus()==5)
                        {
                            countOrderShipped++;
                        }
                    }
                }
                txtTottalOrder.setText("Tổng số đơn hàng : "+ tottalOrder);
                txtOrderCancel.setText("Đơn hàng bị hủy : " + countOrderCancel);
                txtOrderShipped.setText("Đơn hàng đã giao : "+ countOrderShipped);
                float[] yData = { countOrderShipped, countOrderCancel };
                String[] xData = {  "February", "January" };

                for (int i = 0; i < yData.length;i++){
                    yEntrys.add(new PieEntry(yData[i],i));
                }
                for (int i = 0; i < xData.length;i++){
                    xEntrys.add(xData[i]);
                }

                PieDataSet pieDataSet=new PieDataSet(yEntrys,"Employee Sales");
                pieDataSet.setSliceSpace(2);
                pieDataSet.setValueTextSize(12);

                ArrayList<Integer> colors=new ArrayList<>();
                colors.add(Color.BLUE);
                colors.add(Color.RED);

                pieDataSet.setColors(colors);

                Legend legend =pieChart.getLegend();
                legend.setForm(Legend.LegendForm.CIRCLE);
                //legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

                PieData pieData=new PieData(pieDataSet);
                pieChart.setData(pieData);
                pieChart.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Toast.makeText(this, "Value: "
                + e.getY()
                + ", index: "
                + h.getX()
                + ", DataSet index: "
                + h.getDataSetIndex(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected() {

    }
}