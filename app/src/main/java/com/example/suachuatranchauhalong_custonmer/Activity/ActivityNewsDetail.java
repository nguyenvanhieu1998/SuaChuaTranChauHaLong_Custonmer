package com.example.suachuatranchauhalong_custonmer.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suachuatranchauhalong_custonmer.FragmentDialog.FragmentDialogAddNewsForAdmin;
import com.example.suachuatranchauhalong_custonmer.FragmentDialog.FragmentDialogUpdateNewsForAdmin;
import com.example.suachuatranchauhalong_custonmer.Object.Customer;
import com.example.suachuatranchauhalong_custonmer.Object.ListenerTypeNews;
import com.example.suachuatranchauhalong_custonmer.Object.News;
import com.example.suachuatranchauhalong_custonmer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ActivityNewsDetail extends AppCompatActivity implements View.OnClickListener{
    DatabaseReference databaseReference;
    Intent intent;
    TextView txtTittle,txtContent,txtDateCreate;
    ImageView imgTittle;
    Button btnUpdate,btnHidden;
    String idNews,typeNews;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        initReferencesObject();
        addControls();
        setData();
        addEvents();
        checkAdmin();
    }

    private void addEvents() {
        btnUpdate.setOnClickListener(this);
        btnHidden.setOnClickListener(this);
    }

    private void addControls() {
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.ActivityNewsDetail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Tin tức");
        txtTittle = (TextView) findViewById(R.id.ActivityNewsDetail_txtTittle);
        txtContent = (TextView) findViewById(R.id.ActivityNewsDetail_txtContent);
        txtDateCreate = (TextView) findViewById(R.id.ActivityNewsDetail_txtDateCreate);
        imgTittle = (ImageView) findViewById(R.id.ActivityNewsDetail_imgNewTittle);
        btnUpdate = (Button) findViewById(R.id.ActivityNewsDetail_btnUpdate);
        btnHidden = (Button) findViewById(R.id.ActivityNewsDetail_btnHidden);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void setData() {
         idNews = intent.getStringExtra("IDNew").toString();
         typeNews = intent.getStringExtra("TypeNews").toString();
        Log.d("idNews activity : ",idNews);
        Log.d("typeNews activity : ",typeNews);
        databaseReference.child("ListNews").child(typeNews).child(idNews).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                News news = dataSnapshot.getValue(News.class);
                txtDateCreate.setText("Ngày tạo : " + news.getDateCreate().toString());
                txtTittle.setText(news.getTittle().toString());
                txtContent.setText(news.getDescri().toString());
                Picasso.get()
                        .load(news.getImgTittle())
                        .fit()
                        .into(imgTittle);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void initReferencesObject() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        intent = getIntent();
        //   intent = getIntent();
        //    listenerIdDrink = new ListenerIdDrink();
        // Toast.makeText(this, "" + intent.getStringExtra("IDMenuDrink"), Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.ActivityNewsDetail_btnUpdate :
                ListenerTypeNews listenerTypeNews = new ListenerTypeNews();
                listenerTypeNews.setUidNews(idNews);
                listenerTypeNews.setTypeNews(typeNews);
                updateNews();
                break;
            case R.id.ActivityNewsDetail_btnHidden :
                hiddenNews();
                break;
        }
    }
    private void checkAdmin() {
        databaseReference.child("ListCustomer").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Customer customer = dataSnapshot.getValue(Customer.class);
                if(customer.getPermission().equals("admin"))
                {
                    btnHidden.setVisibility(View.VISIBLE);
                    btnUpdate.setVisibility(View.VISIBLE);
//                    txtName.setText(mb.getName());
//                    Picasso.with(TrangChuActivity.this).load(mb.getPhotoURL()).into(imgAdmin);
//                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void hiddenNews() {
        databaseReference.child("ListNews").child(intent.getStringExtra("TypeNews").toString())
                .child(intent.getStringExtra("IDNew").toString()).child("status").setValue(0);
//                HashMap<String,Object> hashMap = new HashMap<>();
//                hashMap.put("status",0);
//                databaseReference.updateChildren(hashMap);
       // Log.d("BimapUpdate : " , photoURL);
        // getBitmapFaceUserCurrent();
        Toast.makeText(ActivityNewsDetail.this, "Đã ẩn tin tức thành công", Toast.LENGTH_SHORT).show();
    }

    private void updateNews() {
        FragmentDialogUpdateNewsForAdmin fragmentDialog = new FragmentDialogUpdateNewsForAdmin();
        fragmentDialog.show(getSupportFragmentManager(),"FragmentDialogUpdateNews");
    }
}