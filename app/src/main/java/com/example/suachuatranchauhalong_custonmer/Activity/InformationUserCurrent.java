package com.example.suachuatranchauhalong_custonmer.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suachuatranchauhalong_custonmer.Object.Customer;
import com.example.suachuatranchauhalong_custonmer.Presenter.UpdateInformationUser.PresenterUpdateInformationForUserCurrent;
import com.example.suachuatranchauhalong_custonmer.R;
import com.example.suachuatranchauhalong_custonmer.View.UpdateInformationUser.ViewUpdateInformationListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class InformationUserCurrent extends AppCompatActivity implements View.OnClickListener, ViewUpdateInformationListener {
    private CircleImageView imgFaceUserCurrent;
    private ImageView imgFolder,imgCamera;
    private EditText edtPhone,edtAddress;
    private TextView txtName,txtTottalBillOrder;
    private RadioGroup rdgSex;
    private RadioButton rdbGirl,rdbMen;
    private Button btnUpdate;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    StorageReference mStorageRef;
    int REQUEST_CODE = 1;
    int READ_REQUEST_CODE = 2;
    Toolbar toolbar;
    private PresenterUpdateInformationForUserCurrent presenterUpdateInformationForUserCurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_user_current);
        initReferenceObject();
        addControls();
        getDataOfUserCurrent();
        getBitmapFaceUserCurrent();
        addEvents();
        getMountOrderOfCustomer();
    }
    private void  initReferenceObject()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getInstance().getCurrentUser();
    }
    private void getDataOfUserCurrent()
    {
        databaseReference.child("ListCustomer").child(firebaseUser.getUid().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Customer customer = snapshot.getValue(Customer.class);
//                Picasso.get().load( shipper.getAvtShipperURL())
//                .fit()
//                .into(imgFaceShipperCurrent);
                txtName.setText(customer.getNameCustomer());
                edtPhone.setText(customer.getPhoneCustomer());
                edtAddress.setText(customer.getAddressCustomer());
                if(customer.isSexCustomer()==true)
                {
                    rdbMen.setChecked(true);
                    rdbGirl.setChecked(false);
                   // getSex = "Nam";
                }
                else
                {
                    rdbGirl.setChecked(true);
                    rdbMen.setChecked(false);
                    //getSex = "Nữ";
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getMountOrderOfCustomer()
    {
        databaseReference.child("ListOrder").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                txtTottalBillOrder.setText("Đã đặt : "+snapshot.getChildrenCount() + " đơn hàng");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void addEvents() {
        btnUpdate.setOnClickListener(this);
        imgFolder.setOnClickListener(this);
        imgCamera.setOnClickListener(this);
    }

    String getSex = "";
    public String getSexUser()
    {
        if(rdbGirl.isChecked()==true)
        {
            getSex = "Nữ";
        }
       else if(rdbMen.isChecked()==true)
        {
            getSex = "Nam";
        }
        //final String dataAccountType ;
//        rdgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//
//                switch (checkedId)
//                {
//                    case R.id.ActivityInformationUserCurrent_rdbGirl:
//                        getSex = rdbGirl.getText().toString();
//                        break;
//                    case R.id.ActivityInformationUserCurrent_rdbMen:
//                        getSex = rdbMen.getText().toString();
//                        break;
//                }
//
//            }
//        });
        return getSex;
    }

    private void addControls() {
        imgFaceUserCurrent = (CircleImageView) findViewById(R.id.ActivityInformationUserCurrent_imgFace);
        txtName = (TextView) findViewById(R.id.ActivityInformationUserCurrent_txtName);
        txtTottalBillOrder = (TextView) findViewById(R.id.ActivityInformationUserCurrent_txtTottalBillOrder);
        edtPhone = (EditText) findViewById(R.id.ActivityInformationUserCurrent_edtPhone);
        edtAddress = (EditText) findViewById(R.id.ActivityInformationUserCurrent_edtAddress);
        rdgSex = (RadioGroup) findViewById(R.id.ActivityInformationUserCurrent_rdgSex);
        rdbGirl = (RadioButton) findViewById(R.id.ActivityInformationUserCurrent_rdbGirl);
        rdbMen = (RadioButton) findViewById(R.id.ActivityInformationUserCurrent_rdbMen);
        btnUpdate = (Button) findViewById(R.id.ActivityInformationUserCurrent_btnUpdate);
        imgCamera = (ImageView) findViewById(R.id.ActivityInformationUserCurrent_imgCamera);
        imgFolder = (ImageView) findViewById(R.id.ActivityInformationUserCurrent_imgFolder);
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.ActivityInformation_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Thông tin cá nhân");
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ActivityInformationUserCurrent_btnUpdate :
                setDataForPresenter();
                break;
            case R.id.ActivityInformationUserCurrent_imgCamera :
                getImageCamera();
                break;
            case R.id.ActivityInformationUserCurrent_imgFolder :
                getImageFolder();
        }
    }

    private void getImageFolder() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    private void  getImageCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null)
        {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgFaceUserCurrent.setImageBitmap(bitmap);
            uploadImage();
        }
        if(requestCode == READ_REQUEST_CODE && resultCode == RESULT_OK && data != null)
        {
//            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//            imgFace.setImageBitmap(bitmap);
            Uri uri = null;
            uri = data.getData();
            Log.i("AAAA", "Uri: " + uri.toString());
            imgFaceUserCurrent.setImageURI(uri);
            uploadImage();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    String userCurrentID;
    String photoURL ="";
    public void uploadImage()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
//        String email2 = user.getEmail();
        userCurrentID = firebaseUser.getUid().toString();
        mStorageRef = FirebaseStorage.getInstance().getReference();
// Create a reference to 'images/mountains.jpg'
        final StorageReference mountainsRef = mStorageRef.child("Customer").child(userCurrentID +".png");

// Create a reference to 'images/mountains.jpg'
        StorageReference mountainImagesRef = mStorageRef.child("images/" + userCurrentID + ".png\"");

// While the file names are the same, the references point to different files
        mountainsRef.getName().equals(mountainImagesRef.getName());    // true
        mountainsRef.getPath().equals(mountainImagesRef.getPath());    // false

        // Get the data from an ImageView as bytes
        imgFaceUserCurrent.setDrawingCacheEnabled(true);
        imgFaceUserCurrent.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imgFaceUserCurrent.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(InformationUserCurrent.this, "Cập nhật ảnh cá nhân thất bại ", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mountainsRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            photoURL = uri.toString();
                            databaseReference.child("ListCustomer").child(userCurrentID).child("avtUriCustomer").setValue(photoURL);
                            getBitmapFaceUserCurrent();
                        }
                    });

                Toast.makeText(InformationUserCurrent.this, "Cập nhật ảnh cá nhân thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }
//              mountainsRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//        @Override
//        public void onSuccess(Uri uri) {
//            photoURL = uri.toString();
//        }
//    });
    String bitmapImgFaceUser = "";
    private void getBitmapFaceUserCurrent()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        databaseReference.child("ListCustomer").child(firebaseUser.getUid().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Customer customer = snapshot.getValue(Customer.class);
                //    bitmapImgFaceUser = shipper.getAvtShipperURL().toString();
                Picasso.get()
                        .load(customer.getAvtUriCustomer().toString())
                        .fit()
                        .into(imgFaceUserCurrent);
                // Bitmap bitmap = StringToBitMap(bitmapImgFaceUser);

                //     byte[] img = EntityUtils.toByteArray(resEntity);
                // imgFaceShipperCurrent.setImageBitmap(bitmap);
                Log.d("Bitmap Face : ", customer.getAvtUriCustomer().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public Bitmap StringToBitMap(String image){
        try{
            byte [] encodeByte= Base64.decode(image, Base64.DEFAULT);

            InputStream inputStream  = new ByteArrayInputStream(encodeByte);
            Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }
    private void setDataForPresenter() {
        presenterUpdateInformationForUserCurrent = new PresenterUpdateInformationForUserCurrent(this);
        String phone = edtPhone.getText().toString();
        String address = edtAddress.getText().toString();
        String sex = getSexUser();
        presenterUpdateInformationForUserCurrent.receivedHandleUpdateInformation(phone,address,sex);
    }

    @Override
    public void onUpdateInformationUserCurrentSuccess() {
        Toast.makeText(this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateInformationUserCurrentFaile() {

    }

    @Override
    public void onUpdateInformationUserCurrentEmrty() {
        Toast.makeText(this, "Bạn phải nhập đầy đủ dữ liệu", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateInformationUserCurrentDataNoChange() {
        Toast.makeText(this, "Bạn đã không thay đổi thông tin gì", Toast.LENGTH_SHORT).show();
    }
}