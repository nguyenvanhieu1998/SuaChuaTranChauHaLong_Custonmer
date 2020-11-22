package com.example.suachuatranchauhalong_custonmer.FragmentDialog;

import android.app.Dialog;
import android.app.ProgressDialog;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.suachuatranchauhalong_custonmer.Activity.Login_Customer;
import com.example.suachuatranchauhalong_custonmer.Object.ListenerTypeNews;
import com.example.suachuatranchauhalong_custonmer.Object.News;
import com.example.suachuatranchauhalong_custonmer.R;
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

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;

public class FragmentDialogUpdateNewsForAdmin extends DialogFragment implements View.OnClickListener {
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    StorageReference mStorageRef;
    Dialog dialog;
    ImageView imgTittleNews,imgCamera,imgFolder;
    EditText edtTittle,edtContent;
    Button btnUpdateNews,btnCancel;
    int REQUEST_CODE = 1;
    int READ_REQUEST_CODE = 2;
    ListenerTypeNews listenerTypeNews;
    String idNews,typeNews,tittleNews,contentNews;
    Boolean checkUpdateImage = false;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        dialog = new Dialog(getActivity());
        //Thiết lập giao diện cho đối tượng Dialog qua phương thức setContentView().Tham số truyền vào ResourceLayout
        dialog.setContentView(R.layout.fragment_dialog_updatenews_for_admin);
        //Khi click vào màn hình thì dialog sẽ k bị tắt trừ khi bấm nút hủy
        dialog.setTitle("Cập nhật thông tin của tin tức");
        initReferencesObject();
        addControlsDialog();
        initData();
        addEventsDialog();
        return dialog;
    }

    private void initReferencesObject() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        listenerTypeNews = new ListenerTypeNews();
        idNews = listenerTypeNews.getUidNews().toString();
        typeNews = listenerTypeNews.getTypeNews().toString();
    }

    private void addEventsDialog() {
        btnUpdateNews.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        imgCamera.setOnClickListener(this);
        imgFolder.setOnClickListener(this);
    }

    private void addControlsDialog() {
        imgTittleNews = (ImageView) dialog.findViewById(R.id.FragmentDialogUpdateNews_imgTittleNews);
        imgCamera = (ImageView) dialog.findViewById(R.id.FragmentDialogUpdateNews_imgCamera);
        imgFolder = (ImageView) dialog.findViewById(R.id.FragmentDialogUpdateNews_imgFolder);
        edtTittle = (EditText) dialog.findViewById(R.id.FragmentDialogUpdateNews_edtTittleNews);
        edtContent = (EditText) dialog.findViewById(R.id.FragmentDialogUpdateNews_edtContentNews);
        btnUpdateNews = (Button) dialog.findViewById(R.id.FragmentDialogUpdateNews_btnUpdateNews);
        btnCancel = (Button) dialog.findViewById(R.id.FragmentDialogUpdateNews_btnCancel);
    }
    private void getImageFolder() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }
    private void getImageCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null)
        {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgTittleNews.setImageBitmap(bitmap);
            checkUpdateImage = true;
        }
        if(requestCode == READ_REQUEST_CODE && resultCode == RESULT_OK && data != null)
        {
//            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//            imgFace.setImageBitmap(bitmap);
            Uri uri = null;
            uri = data.getData();
            Log.i("AAAA", "Uri: " + uri.toString());
            imgTittleNews.setImageURI(uri);
            checkUpdateImage = true;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.FragmentDialogUpdateNews_btnUpdateNews :
               updateNews();
                break;
            case R.id.FragmentDialogUpdateNews_btnCancel :
                dialog.dismiss();
                break;
            case R.id.FragmentDialogUpdateNews_imgCamera :
                getImageCamera();
                break;
            case R.id.FragmentDialogUpdateNews_imgFolder :
                getImageFolder();
                break;
        }
    }
    private void initData()
    {
        Log.d("Type News " ,listenerTypeNews.getTypeNews().toString());
      //  Log.d("ID News " ,listenerTypeNews.getUidNews().toString());
        databaseReference.child("ListNews").child(listenerTypeNews.getTypeNews().toString()).child(listenerTypeNews.getUidNews().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                News news = dataSnapshot.getValue(News.class);
                Picasso.get()
                        .load(news.getImgTittle())
                        .fit()
                        .into(imgTittleNews);
                edtTittle.setText(news.getTittle().toString());
                edtContent.setText(news.getDescri().toString());
                tittleNews = news.getTittle().toString();
                contentNews = news.getDescri().toString();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void updateNews() {
        if( edtTittle.getText().toString().trim().equals("") || edtContent.getText().toString().trim().equals(""))
        {
            Toast.makeText(getActivity(), "Bạn phải nhập đầy đủ tiêu đề và nội dung của tin tức ", Toast.LENGTH_SHORT).show();
        }
        else if(checkUpdateImage == false && tittleNews.equals(edtTittle.getText().toString().trim()) && contentNews.equals(edtContent.getText().toString().trim()))
        {
            Toast.makeText(getActivity(), "Bạn đã không thay đổi thông tin gì ", Toast.LENGTH_SHORT).show();
        }
        else
        {
            updateNewsToFirebase();
        }
    }
    String photoURL = "";
    ProgressDialog progressDialog;
    private void updateNewsToFirebase() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Cập nhật....");
        progressDialog.show();
        final StorageReference mountainsRef = mStorageRef.child("News").child(listenerTypeNews.getUidNews().toString()+".png");

// Create a reference to 'images/mountains.jpg'
        StorageReference mountainImagesRef = mStorageRef.child("images/" + listenerTypeNews.getUidNews().toString() + ".png\"");

// While the file names are the same, the references point to different files
        mountainsRef.getName().equals(mountainImagesRef.getName());    // true
        mountainsRef.getPath().equals(mountainImagesRef.getPath());    // false

        // Get the data from an ImageView as bytes
        imgTittleNews.setDrawingCacheEnabled(true);
        imgTittleNews.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imgTittleNews.getDrawable()).getBitmap();
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
                mountainsRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        photoURL = uri.toString();
                        databaseReference.child("ListNews").child(typeNews).child(idNews).child("imgTittle").setValue(photoURL);
                        databaseReference.child("ListNews").child(typeNews).child(idNews).child("descri").setValue(edtContent.getText().toString());
                        databaseReference.child("ListNews").child(typeNews).child(idNews).child("tittle").setValue(edtTittle.getText().toString());
                        Toast.makeText(getContext(), "Cập nhật tin tức thành công", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        dialog.dismiss();
                    }
                });
                Log.d("Type News Dialog : ", typeNews);
                Log.d("id News Dialog : ", idNews);

//                HashMap<String,Object> hashMap = new HashMap<>();
//                hashMap.put("imgTittle",photoURL);
//                hashMap.put("descri",edtContent.getText().toString());
//                hashMap.put("tittle",edtTittle.getText().toString());
//                databaseReference.updateChildren(hashMap);
                Log.d("BimapUpdate : " , photoURL);
               // getBitmapFaceUserCurrent();

            }
        });
    }
}
