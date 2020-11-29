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

import com.example.suachuatranchauhalong_custonmer.Object.ListenerTypeNews;
import com.example.suachuatranchauhalong_custonmer.Object.News;
import com.example.suachuatranchauhalong_custonmer.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

public class FragmentDialogAddNewsForAdmin extends DialogFragment implements View.OnClickListener{
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    StorageReference mStorageRef;
    Dialog dialog;
    ImageView imgTittleNews,imgCamera,imgFolder;
    EditText edtTittle,edtContent;
    Button btnAddNews,btnCancel;
    int REQUEST_CODE = 1;
    int READ_REQUEST_CODE = 2;
    Calendar calen;
    String dateCreateNews,key_push_news;
    ListenerTypeNews listenerTypeNews;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //Khai báo 1 đối tượng Dialog khoogn sử dụng giao diện mặc định của hệ thống.Tham số truyền vào là context
        dialog = new Dialog(getActivity());
        //Thiết lập giao diện cho đối tượng Dialog qua phương thức setContentView().Tham số truyền vào ResourceLayout
        dialog.setContentView(R.layout.fragment_dialog_addnews_for_admin);
        //Khi click vào màn hình thì dialog sẽ k bị tắt trừ khi bấm nút hủy
        dialog.setTitle("Thiết lập thông tin của tin tức");
        addControlsDialog();
        addEventsDialog();
        return dialog;
    }

    private void addEventsDialog() {
        btnAddNews.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        imgCamera.setOnClickListener(this);
        imgFolder.setOnClickListener(this);
    }

    private void addControlsDialog() {
        imgTittleNews = (ImageView) dialog.findViewById(R.id.FragmentDialogAddNews_imgTittleNews);
        imgTittleNews.setImageResource(R.drawable.anhtintucbt4);
        imgCamera = (ImageView) dialog.findViewById(R.id.FragmentDialogAddNews_imgCamera);
        imgFolder = (ImageView) dialog.findViewById(R.id.FragmentDialogAddNews_imgFolder);
        edtTittle = (EditText) dialog.findViewById(R.id.FragmentDialogAddNews_edtTittleNews);
        edtContent = (EditText) dialog.findViewById(R.id.FragmentDialogAddNews_edtContentNews);
        btnAddNews = (Button) dialog.findViewById(R.id.FragmentDialogAddNews_btnAddNews);
        btnCancel = (Button) dialog.findViewById(R.id.FragmentDialogAddNews_btnCancel);
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
        }
        if(requestCode == READ_REQUEST_CODE && resultCode == RESULT_OK && data != null)
        {
//            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//            imgFace.setImageBitmap(bitmap);
            Uri uri = null;
            uri = data.getData();
            Log.i("AAAA", "Uri: " + uri.toString());
            imgTittleNews.setImageURI(uri);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.FragmentDialogAddNews_btnAddNews :
                addNews();
                break;
            case R.id.FragmentDialogAddNews_btnCancel :
                dialog.dismiss();
                break;
            case R.id.FragmentDialogAddNews_imgCamera :
                getImageCamera();
                break;
            case R.id.FragmentDialogAddNews_imgFolder :
                getImageFolder();
                break;
        }

    }

    private void addNews() {
        if( imgTittleNews.getDrawable().equals("") || edtTittle.getText().toString().trim().equals("") || edtContent.getText().toString().trim().equals(""))
        {
            Toast.makeText(getActivity(), "Bạn phải nhập đầy đủ tiêu đề và nội dung của tin tức ", Toast.LENGTH_SHORT).show();
        }
        else
        {
            addNewsToFirebase();
        }
    }
    String photoURL = "";
    ProgressDialog progressDialog;
    private void addNewsToFirebase() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Đang thêm....");
        progressDialog.show();
        calen = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateCreateNews = "" + simpleDateFormat.format(calen.getTime());
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
//        String email2 = user.getEmail();
        String userCurrentID = firebaseUser.getUid().toString();
        listenerTypeNews = new ListenerTypeNews();
        key_push_news = databaseReference.child("ListNews").child(listenerTypeNews.getTypeNews().toString()).push().getKey();
        // progressDialog.setMessage("Đang cập nhật.........");
        // progressDialog.show();
        final StorageReference mountainsRef = mStorageRef.child("News").child(key_push_news+".png");

// Create a reference to 'images/mountains.jpg'
        StorageReference mountainImagesRef = mStorageRef.child("images/" + key_push_news + ".png\"");

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
                News news = new News(key_push_news,photoURL,edtTittle.getText().toString(),edtContent.getText().toString(),dateCreateNews,listenerTypeNews.getTypeNews().toString(),1);
                databaseReference.child("ListNews").child(listenerTypeNews.getTypeNews().toString()).child(key_push_news).setValue(news, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if(databaseError == null)                        {
                            Toast.makeText(getActivity(), "Bạn đã thêm tin tức thành công", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            dialog.dismiss();
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Bạn đã thêm tin tức thất bại", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            dialog.dismiss();
                            // Toast.makeText(getActivity(), "Đăng tin thất bại !", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                    }
                    });

                        Log.d("photoURL",photoURL);

            }
        });
    }
}
