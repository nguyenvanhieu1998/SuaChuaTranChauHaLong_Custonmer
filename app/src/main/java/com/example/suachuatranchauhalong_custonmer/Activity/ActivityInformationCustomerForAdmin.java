package com.example.suachuatranchauhalong_custonmer.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.suachuatranchauhalong_custonmer.Object.Customer;
import com.example.suachuatranchauhalong_custonmer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityInformationCustomerForAdmin extends AppCompatActivity {
    CircleImageView circleImageViewCustomer;
    TextView txtEmail,txtPhone,txtSex,txtAddress,txtName,txtMountOrder;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    Intent intent;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_customer_for_admin);
        initReferenceObject();
        addControls();
        initData();
    }
    private void initReferenceObject() {
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
    private void addControls()
    {
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.ActivityInformationCustomerForAdmin_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Thông tin khách hàng");
        circleImageViewCustomer = (CircleImageView) findViewById(R.id.ActivityInformationCustomerForAdmin_imgCustomer);
        txtEmail = (TextView) findViewById(R.id.ActivityInformationCustomerForAdmin_emailCustomer);
        txtPhone = (TextView) findViewById(R.id.ActivityInformationCustomerForAdmin_phoneCustomer);
        txtSex = (TextView) findViewById(R.id.ActivityInformationCustomerForAdmin_sexCustomer);
        txtAddress = (TextView) findViewById(R.id.ActivityInformationCustomerForAdmin_addressCustomer);
        txtName = (TextView) findViewById(R.id.ActivityInformationCustomerForAdmin_nameCustomer);
        txtMountOrder = (TextView) findViewById(R.id.ActivityInformationCustomerForAdmin_mountOrder);
    }
    private void initData()
    {
        databaseReference.child("ListCustomer").child(intent.getStringExtra("idCustomerSendActivityInformationCustomer")).
                addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Customer customer = snapshot.getValue(Customer.class);
                Picasso.get()
                        .load(customer.getAvtUriCustomer())
                        .fit()
                        .into(circleImageViewCustomer);
                txtName.setText(customer.getNameCustomer());
                txtEmail.setText(customer.getEmailCustomer());
                txtPhone.setText(customer.getPhoneCustomer());
                txtAddress.setText(customer.getAddressCustomer());
                txtSex.setText(customer.isSexCustomer()? "Nam":"Nữ");
                databaseReference.child("ListOrder").child(customer.getIdCustomer()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        txtMountOrder.setText("Đã mua : "+ snapshot.getChildrenCount());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void addEvents()
    {

    }
}