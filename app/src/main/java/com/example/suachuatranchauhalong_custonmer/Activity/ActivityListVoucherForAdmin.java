package com.example.suachuatranchauhalong_custonmer.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.suachuatranchauhalong_custonmer.Adapter.DrinkAdapter;
import com.example.suachuatranchauhalong_custonmer.Adapter.VoucherAdapter;
import com.example.suachuatranchauhalong_custonmer.InterfaceOnClick.VoucherOnItemClick;
import com.example.suachuatranchauhalong_custonmer.Object.Drink;
import com.example.suachuatranchauhalong_custonmer.Object.News;
import com.example.suachuatranchauhalong_custonmer.Object.Voucher;
import com.example.suachuatranchauhalong_custonmer.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import jp.wasabeef.recyclerview.animators.LandingAnimator;

public class ActivityListVoucherForAdmin extends AppCompatActivity implements VoucherOnItemClick {
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    Toolbar toolbar;
    RecyclerView recyclerViewVoucher;
    Calendar calen;
    String dateWrite;
    Button dialogUpdateVoucher_btnUpdate,dialogUpdateVoucher_btnCancel,dialogUpdateVoucher_btnHiden;
    EditText dialogUpdateVoucher_edtGiaTriApDung,dialogUpdateVoucher_edtPromotion,dialogUpdateVoucher_edtPoint;
    Button dialogAddVoucher_btnAdd,dialogAddVoucher_btnCancel;
    EditText dialogAddVoucher_edtGiaTriApDung,dialogAddVoucher_edtPromotion,dialogAddVoucher_edtPoint;
    Dialog dialogUpdateVoucher,dialogAddVoucher;
    ImageView dialogUpdateVoucher_imgVoucher,dialogUpdateVoucher_imgFolder,dialogUpdateVoucher_imgCamera;
    ImageView dialogAddVoucher_imgVoucher,dialogAddVoucher_imgFolder,dialogAddVoucher_imgCamera;
    FloatingActionButton floatAdd;
    int REQUEST_CODE_1 = 1;
    int READ_REQUEST_CODE_1 = 2;
    int REQUEST_CODE_2 = 3;
    int READ_REQUEST_CODE_2 = 4;
    StorageReference mStorageRef;
    ArrayList<Voucher> voucherArrayList;
    VoucherAdapter voucherAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_voucher_for_admin);
        initReferenceObject();
        addControls();
        addEvents();
        initDataRecycleVoucher();
    }
    private void initReferenceObject()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        mStorageRef = FirebaseStorage.getInstance().getReference();
    }
    private void initDataRecycleVoucher()
    {
        voucherArrayList = new ArrayList<>();
        databaseReference.child("ListVoucher").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                voucherArrayList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    Voucher voucher = dataSnapshot1.getValue(Voucher.class);
                    if(voucher.getStatus()==1)
                    {
                        voucherArrayList.add(voucher);
                    }

                }
                voucherAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        voucherAdapter = new VoucherAdapter(this,voucherArrayList,this);
        recyclerViewVoucher.setAdapter(voucherAdapter);
        recyclerViewVoucher.setHasFixedSize(true);
        int spanCount = 2;//Số cột nếu thiết lập lưới đứng, số dòng nếu lưới ngang
        int orientation = GridLayoutManager.VERTICAL;//Lưới ngang
//int orientation = GridLayoutManager.HORIZONTAL;//Lưới đứng

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
// Gắn vào RecylerView
        recyclerViewVoucher.setItemAnimator(new LandingAnimator());
        recyclerViewVoucher.setLayoutManager(gridLayoutManager);
    }
    private void addControls()
    {
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.ActivityListVoucherForAdmin_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Danh sách voucher");
        recyclerViewVoucher = (RecyclerView) findViewById(R.id.ActivityListVoucherForAdmin_recycleViewVoucher);
        floatAdd = (FloatingActionButton) findViewById(R.id.ActivityListVoucherForAdmin_floatingAdd);
    }
     private void addEvents()
     {
         floatAdd.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 DialogAddVoucher();
             }
         });
     }
    private void addControlsDialogAddVoucher()
    {
        dialogAddVoucher_btnAdd = (Button) dialogAddVoucher.findViewById(R.id.dialogAddVoucher_btnAddVoucher);
        dialogAddVoucher_btnCancel = (Button) dialogAddVoucher.findViewById(R.id.dialogAddVoucher_btnCancel);
       // dialogAddVoucher_btnHiden = (Button) dialogUpdateVoucher.findViewById(R.id.dialogUpdateVoucher_btnHiden);
        dialogAddVoucher_edtGiaTriApDung = (EditText) dialogAddVoucher.findViewById(R.id.dialogAddVoucher_edtGiaTriApDung);
        dialogAddVoucher_edtPromotion = (EditText) dialogAddVoucher.findViewById(R.id.dialogAddVoucher_edtPromotion);
        dialogAddVoucher_imgVoucher = (ImageView) dialogAddVoucher.findViewById(R.id.dialogAddVoucher_imgVoucher);
        dialogAddVoucher_imgCamera = (ImageView) dialogAddVoucher.findViewById(R.id.dialogAddVoucher_imgCamera);
        dialogAddVoucher_imgFolder = (ImageView) dialogAddVoucher.findViewById(R.id.dialogAddVoucher_imgFolder);
        dialogAddVoucher_edtPoint = (EditText) dialogAddVoucher.findViewById(R.id.dialogAddVoucher_edtDiemQuyDoi);
    }
     private void DialogAddVoucher()
     {
         dialogAddVoucher = new Dialog(this);
         dialogAddVoucher.setContentView(R.layout.custome_dialog_add_voucher);
         dialogAddVoucher.setTitle("Thêm voucher");
         initReferenceObject();
         addControlsDialogAddVoucher();
         addEventsDialogAddVoucher();
         dialogAddVoucher.show();
     }
    private void getImageFolder_DialogAddVoucher() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, READ_REQUEST_CODE_1);
    }
    private void getImageCamera_DialogAddVoucher() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,REQUEST_CODE_1);
    }

    private void addEventsDialogAddVoucher() {
        dialogAddVoucher_imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImageCamera_DialogAddVoucher();
            }
        });
        dialogAddVoucher_imgFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImageFolder_DialogAddVoucher();
        }
        });
        dialogAddVoucher_btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addVoucher();
            }
        });
        dialogAddVoucher_btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddVoucher.dismiss();
            }
        });
    }
    private boolean checkUpdateImageVoucher=false;
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_1 && resultCode == RESULT_OK && data != null)
        {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            dialogAddVoucher_imgVoucher.setImageBitmap(bitmap);
        }
        if(requestCode == READ_REQUEST_CODE_1 && resultCode == RESULT_OK && data != null)
        {
//            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//            imgFace.setImageBitmap(bitmap);
            Uri uri = null;
            uri = data.getData();
            Log.i("AAAA", "Uri: " + uri.toString());
            dialogAddVoucher_imgVoucher.setImageURI(uri);

        }
        if(requestCode == REQUEST_CODE_2 && resultCode == RESULT_OK && data != null)
        {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            dialogUpdateVoucher_imgVoucher.setImageBitmap(bitmap);
            checkUpdateImageVoucher=true;
            updateImageVoucher(idVoucher1);
        }
        if(requestCode == READ_REQUEST_CODE_2 && resultCode == RESULT_OK && data != null)
        {
//            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//            imgFace.setImageBitmap(bitmap);
            Uri uri = null;
            uri = data.getData();
            Log.i("AAAA", "Uri: " + uri.toString());
            dialogUpdateVoucher_imgVoucher.setImageURI(uri);
            checkUpdateImageVoucher=true;
            updateImageVoucher(idVoucher1);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private static String idVoucher1;
    private void addVoucher() {
//        dialogAddVoucher_imgVoucher.setImageResource(R.drawable.ic_launcher_background);
                if(dialogAddVoucher_edtGiaTriApDung.getText().toString().trim().equals("") || dialogAddVoucher_edtPromotion.getText().toString().trim().equals(""))
                {
                    Toast.makeText(ActivityListVoucherForAdmin.this, "Bạn phải nhập đầy đủ dữ liệu", Toast.LENGTH_SHORT).show();
                }
                else if(Integer.parseInt(dialogAddVoucher_edtPromotion.getText().toString().trim())>50)
                {
                    Toast.makeText(ActivityListVoucherForAdmin.this, "Giá trị khuyến mãi chỉ được dưới 50%", Toast.LENGTH_SHORT).show();
                }
                else {
                    addVoucherToFirebase();
                }
    }
    String photoURL;
    private void addVoucherToFirebase() {
        calen = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateWrite = "" + simpleDateFormat.format(calen.getTime());
        final String key_push_voucher = databaseReference.child("ListVoucher").push().getKey();
        // progressDialog.setMessage("Đang cập nhật.........");
        // progressDialog.show();
        final StorageReference mountainsRef = mStorageRef.child("Voucher").child(key_push_voucher+".png");

// Create a reference to 'images/mountains.jpg'
        StorageReference mountainImagesRef = mStorageRef.child("images/" + key_push_voucher + ".png\"");

// While the file names are the same, the references point to different files
        mountainsRef.getName().equals(mountainImagesRef.getName());    // true
        mountainsRef.getPath().equals(mountainImagesRef.getPath());    // false

        // Get the data from an ImageView as bytes
        dialogAddVoucher_imgVoucher.setDrawingCacheEnabled(true);
        dialogAddVoucher_imgVoucher.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) dialogAddVoucher_imgVoucher.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                mountainsRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                {
                    @Override
                    public void onSuccess(Uri downloadUrl)
                    {
                        //do something with downloadurl
                        photoURL = downloadUrl.toString();
                        Log.d("photoURL",photoURL);
                        Voucher voucher = new Voucher(key_push_voucher,photoURL,Float.parseFloat(dialogAddVoucher_edtGiaTriApDung.getText().toString().trim()),
                                Integer.parseInt(dialogAddVoucher_edtPromotion.getText().toString().trim()),
                                Integer.parseInt(dialogAddVoucher_edtPoint.getText().toString().trim()),1,dateWrite);
                        databaseReference.child("ListVoucher").child(key_push_voucher).setValue(voucher, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                if(databaseError == null)                        {
                                    Toast.makeText(ActivityListVoucherForAdmin.this, "Bạn đã thêm voucher thành công", Toast.LENGTH_SHORT).show();
                                    dialogAddVoucher.dismiss();
                                }
                                else
                                {
                                    Toast.makeText(ActivityListVoucherForAdmin.this, "Bạn đã thêm voucher thất bại", Toast.LENGTH_SHORT).show();
                                    dialogAddVoucher.dismiss();
                                    // Toast.makeText(getActivity(), "Đăng tin thất bại !", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                });
//                Task<Uri> downloadUri = taskSnapshot.getStorage().getDownloadUrl();
//                String photoURL = downloadUri.getResult().toString();
//                mStorageRef.child("Voucher").child(key_push_voucher+".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        Uri downloadUri = taskSnapshot.getMetadata().getD();
//                        generatedFilePath = downloadUri.toString();
//                    }
//                });
             //   String photoURL = "";
            }
        });
    }


    @Override
    public void onItemClickListener(Voucher voucher) {
        idVoucher1 = voucher.getIdVoucher().toString();
        DialogUpdateVoucher(voucher.getIdVoucher().toString());
    }
    private void addControlsDialogUpdateVoucher()
    {
        calen = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateWrite = "" + simpleDateFormat.format(calen.getTime());
        dialogUpdateVoucher_btnUpdate = (Button) dialogUpdateVoucher.findViewById(R.id.dialogUpdateVoucher_btnUpdateVoucher);
        dialogUpdateVoucher_btnCancel = (Button) dialogUpdateVoucher.findViewById(R.id.dialogUpdateVoucher_btnCancel);
        dialogUpdateVoucher_btnHiden = (Button) dialogUpdateVoucher.findViewById(R.id.dialogUpdateVoucher_btnHiden);
        dialogUpdateVoucher_edtGiaTriApDung = (EditText) dialogUpdateVoucher.findViewById(R.id.dialogUpdateVoucher_edtGiaTriApDung);
        dialogUpdateVoucher_edtPromotion = (EditText) dialogUpdateVoucher.findViewById(R.id.dialogUpdateVoucher_edtPromotion);
        dialogUpdateVoucher_imgVoucher = (ImageView) dialogUpdateVoucher.findViewById(R.id.dialogUpdateVoucher_imgVoucher);
        dialogUpdateVoucher_imgCamera = (ImageView) dialogUpdateVoucher.findViewById(R.id.dialogUpdateVoucher_imgCamera);
        dialogUpdateVoucher_imgFolder = (ImageView) dialogUpdateVoucher.findViewById(R.id.dialogUpdateVoucher_imgFolder);
        dialogUpdateVoucher_edtPoint = (EditText) dialogAddVoucher.findViewById(R.id.dialogUpdateVoucher_edtDiemQuyDoi);
    }
    private void addEventsDialogUpdateVoucher() {
        dialogUpdateVoucher_btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogUpdateVoucher.dismiss();
            }
        });
        dialogUpdateVoucher_btnHiden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    private void getImageFolder_DialogUpdateVoucher() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, READ_REQUEST_CODE_2);
    }
    private void getImageCamera_DialogUpdateVoucher() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,REQUEST_CODE_2);
    }
    int pricePromotion,point;
    float priceApple;
    private void DialogUpdateVoucher(final String idVoucher)
    {
        dialogUpdateVoucher = new Dialog(this);
        dialogUpdateVoucher.setContentView(R.layout.custome_dialog_update_voucher);
        dialogUpdateVoucher.setTitle("Cập nhật thông tin voucher");
        addControlsDialogUpdateVoucher();
        addEventsDialogUpdateVoucher();
        initData(idVoucher);
        dialogUpdateVoucher_imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImageCamera_DialogUpdateVoucher();
            }
        });
        dialogUpdateVoucher_imgFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImageFolder_DialogUpdateVoucher();
            }
        });
        dialogUpdateVoucher_btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  if(dialogUpdateVoucher_edtGiaTriApDung.getText().toString().trim().equals("")||dialogUpdateVoucher_edtPromotion.getText().toString().trim().equals(""))
                  {
                      Toast.makeText(ActivityListVoucherForAdmin.this, "Bạn phải nhập đầy đủ dữ liệu", Toast.LENGTH_SHORT).show();
                  }
                  else if(priceApple == Float.parseFloat(dialogUpdateVoucher_edtGiaTriApDung.getText().toString().trim())
                          && pricePromotion == Integer.parseInt(dialogUpdateVoucher_edtPromotion.getText().toString().trim())
                             && point ==Integer.parseInt(dialogUpdateVoucher_edtPoint.getText().toString().trim()))
                  {
                      Toast.makeText(ActivityListVoucherForAdmin.this, "Bạn đã không thay đổi thông tin gì ", Toast.LENGTH_SHORT).show();
                  }
                  else if(Integer.parseInt(dialogUpdateVoucher_edtPromotion.getText().toString().trim())>50)
                  {
                      Toast.makeText(ActivityListVoucherForAdmin.this, "Giá trị khuyến mãi chỉ được dưới 50%", Toast.LENGTH_SHORT).show();
                  }
                  else {
                      updateInfomationVoucher(idVoucher);
                  }
            }
        });
        dialogUpdateVoucher_btnHiden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child("ListVoucher").child(idVoucher).child("status").setValue(0);
                dialogUpdateVoucher.dismiss();
                Toast.makeText(ActivityListVoucherForAdmin.this, "Bạn đã ẩn voucher", Toast.LENGTH_SHORT).show();
            }
        });
        dialogUpdateVoucher.show();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void updateImageVoucher(final String idVoucher)
    {
        final StorageReference mountainsRef = mStorageRef.child("Voucher").child(idVoucher+".png");

// Create a reference to 'images/mountains.jpg'
        StorageReference mountainImagesRef = mStorageRef.child("images/" + idVoucher + ".png\"");

// While the file names are the same, the references point to different files
        mountainsRef.getName().equals(mountainImagesRef.getName());    // true
        mountainsRef.getPath().equals(mountainImagesRef.getPath());    // false

        // Get the data from an ImageView as bytes
        dialogUpdateVoucher_imgVoucher.setDrawingCacheEnabled(true);
        dialogUpdateVoucher_imgVoucher.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) dialogUpdateVoucher_imgVoucher.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Uri downloadUrl = taskSnapshot.getDownloadUrl();
//                // Uri downloadUrl = taskSnapshot.getStorage().getDownloadUrl().getResult();
//                String photoURL = downloadUrl.toString();
                mountainsRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                {
                    @Override
                    public void onSuccess(Uri downloadUrl)
                    {
                        photoURL = downloadUrl.toString();
                        databaseReference.child("ListVoucher").child(idVoucher).child("imgVoucher").setValue(photoURL);
                       // databaseReference.child("ListVoucher").child(idVoucher).child("priceApply").setValue(Float.parseFloat(dialogUpdateVoucher_edtGiaTriApDung.getText().toString()));
                      //  databaseReference.child("ListVoucher").child(idVoucher).child("pricePromotion").setValue(Integer.parseInt(dialogUpdateVoucher_edtPromotion.getText().toString()));
                    }
                });
                String photoURL = "";
//                Log.d("Type News Dialog : ", typeNews);
//                Log.d("id News Dialog : ", idNews);
//                HashMap<String,Object> hashMap = new HashMap<>();
//                hashMap.put("imgTittle",photoURL);
//                hashMap.put("descri",edtContent.getText().toString());
//                hashMap.put("tittle",edtTittle.getText().toString());
//                databaseReference.updateChildren(hashMap);
                Log.d("BimapUpdate : " , photoURL);
                // getBitmapFaceUserCurrent();
                Toast.makeText(ActivityListVoucherForAdmin.this, "Cập nhật ảnh voucher thành công", Toast.LENGTH_SHORT).show();
               // dialogUpdateVoucher.dismiss();
            }
        });
    }
    private void updateInfomationVoucher(final String idVoucher) {
//        final StorageReference mountainsRef = mStorageRef.child("Voucher").child(idVoucher+".png");
//
//// Create a reference to 'images/mountains.jpg'
//        StorageReference mountainImagesRef = mStorageRef.child("images/" + idVoucher + ".png\"");
//
//// While the file names are the same, the references point to different files
//        mountainsRef.getName().equals(mountainImagesRef.getName());    // true
//        mountainsRef.getPath().equals(mountainImagesRef.getPath());    // false
//
//        // Get the data from an ImageView as bytes
//        dialogUpdateVoucher_imgVoucher.setDrawingCacheEnabled(true);
//        dialogUpdateVoucher_imgVoucher.buildDrawingCache();
//        Bitmap bitmap = ((BitmapDrawable) dialogUpdateVoucher_imgVoucher.getDrawable()).getBitmap();
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        byte[] data = baos.toByteArray();
//
//        UploadTask uploadTask = mountainsRef.putBytes(data);
//        uploadTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
////                Uri downloadUrl = taskSnapshot.getDownloadUrl();
////                // Uri downloadUrl = taskSnapshot.getStorage().getDownloadUrl().getResult();
////                String photoURL = downloadUrl.toString();
//                mountainsRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
//                {
//                    @Override
//                    public void onSuccess(Uri downloadUrl)
//                    {
                        //photoURL = downloadUrl.toString();
                      //  databaseReference.child("ListVoucher").child(idVoucher).child("imgVoucher").setValue(photoURL);
                        databaseReference.child("ListVoucher").child(idVoucher).child("priceApply").setValue(Float.parseFloat(dialogUpdateVoucher_edtGiaTriApDung.getText().toString()));
                        databaseReference.child("ListVoucher").child(idVoucher).child("pricePromotion").setValue(Integer.parseInt(dialogUpdateVoucher_edtPromotion.getText().toString()));
        databaseReference.child("ListVoucher").child(idVoucher).child("point").setValue(Integer.parseInt(dialogUpdateVoucher_edtPoint.getText().toString()));
                        Toast.makeText(ActivityListVoucherForAdmin.this, "Cập nhật thông tin voucher thành công", Toast.LENGTH_SHORT).show();
                        dialogUpdateVoucher.dismiss();
//                    }
//                });
//                String photoURL = "";
////                Log.d("Type News Dialog : ", typeNews);
////                Log.d("id News Dialog : ", idNews);
////                HashMap<String,Object> hashMap = new HashMap<>();
////                hashMap.put("imgTittle",photoURL);
////                hashMap.put("descri",edtContent.getText().toString());
////                hashMap.put("tittle",edtTittle.getText().toString());
////                databaseReference.updateChildren(hashMap);
//                Log.d("BimapUpdate : " , photoURL);
//                // getBitmapFaceUserCurrent();
//                Toast.makeText(ActivityListVoucherForAdmin.this, "Cập nhật thông tin voucher thành công", Toast.LENGTH_SHORT).show();
//                dialogUpdateVoucher.dismiss();
//            }
//        });
    }

    private void initData(String idVoucher)
    {
        databaseReference.child("ListVoucher").child(idVoucher).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Voucher voucher = snapshot.getValue(Voucher.class);
                Picasso.get()
                        .load(voucher.getImgVoucher())
                        .fit()
                        .into(dialogUpdateVoucher_imgVoucher);
                dialogUpdateVoucher_edtGiaTriApDung.setText("" + voucher.getPriceApply());
                dialogUpdateVoucher_edtPromotion.setText("" + voucher.getPricePromotion());
                pricePromotion = voucher.getPricePromotion();
                priceApple = voucher.getPriceApply();
                point = voucher.getPoint();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}