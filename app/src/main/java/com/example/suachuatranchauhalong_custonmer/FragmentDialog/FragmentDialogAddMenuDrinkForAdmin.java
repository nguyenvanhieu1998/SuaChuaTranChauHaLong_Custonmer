package com.example.suachuatranchauhalong_custonmer.FragmentDialog;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.suachuatranchauhalong_custonmer.Object.ListenerTypeNews;
import com.example.suachuatranchauhalong_custonmer.Object.MenuDrink;
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

public class FragmentDialogAddMenuDrinkForAdmin extends DialogFragment implements View.OnClickListener{
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    StorageReference mStorageRef;
    Dialog dialog;
    ImageView imgMenuDrink,imgCamera,imgFolder;
    EditText edtNameMenuDrink;
    Button btnAddMenuDrink,btnCancel;
    int REQUEST_CODE = 1;
    int READ_REQUEST_CODE = 2;
    Calendar calen;
    String dateCreateMenuDrink,key_push_menudrink;
    ListenerTypeNews listenerTypeNews;
    Boolean checkChooseImageMenuDrink = false;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //Khai báo 1 đối tượng Dialog khoogn sử dụng giao diện mặc định của hệ thống.Tham số truyền vào là context
        dialog = new Dialog(getActivity());
        //Thiết lập giao diện cho đối tượng Dialog qua phương thức setContentView().Tham số truyền vào ResourceLayout
        dialog.setContentView(R.layout.fragment_dialog_addmenudrink_for_admin);
        //Khi click vào màn hình thì dialog sẽ k bị tắt trừ khi bấm nút hủy
        dialog.setTitle("Thêm menu");
        addControlsDialog();
        addEventsDialog();
        return dialog;
    }

    private void addEventsDialog() {
        btnAddMenuDrink.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        imgCamera.setOnClickListener(this);
        imgFolder.setOnClickListener(this);
    }

    private void addControlsDialog() {
        imgMenuDrink = (ImageView) dialog.findViewById(R.id.FragmentDialogAddMenuDrink_imgTittleMenuDrink);
        imgMenuDrink.setImageResource(R.drawable.anhtintucbt4);
        imgCamera = (ImageView) dialog.findViewById(R.id.FragmentDialogAddMenuDrink_imgCamera);
        imgFolder = (ImageView) dialog.findViewById(R.id.FragmentDialogAddMenuDrink_imgFolder);
        edtNameMenuDrink = (EditText) dialog.findViewById(R.id.FragmentDialogAddMenuDrink_edtNameMenuDrink);
        btnAddMenuDrink = (Button) dialog.findViewById(R.id.FragmentDialogAddMenuDrink_btnAddMenuDrink);
        btnCancel = (Button) dialog.findViewById(R.id.FragmentDialogAddMenuDrink_btnCancel);
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
            imgMenuDrink.setImageBitmap(bitmap);
            checkChooseImageMenuDrink = true;
        }
        if(requestCode == READ_REQUEST_CODE && resultCode == RESULT_OK && data != null)
        {
//            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//            imgFace.setImageBitmap(bitmap);
            Uri uri = null;
            uri = data.getData();
            Log.i("AAAA", "Uri: " + uri.toString());
            imgMenuDrink.setImageURI(uri);
            checkChooseImageMenuDrink = true;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.FragmentDialogAddMenuDrink_btnAddMenuDrink :
                addMenuDrink();
                break;
            case R.id.FragmentDialogAddMenuDrink_btnCancel :
                dialog.dismiss();
                break;
            case R.id.FragmentDialogAddMenuDrink_imgCamera :
                getImageCamera();
                break;
            case R.id.FragmentDialogAddMenuDrink_imgFolder :
                getImageFolder();
                break;
        }

    }

    private void addMenuDrink() {
        if(checkChooseImageMenuDrink == false)
        {
            Toast.makeText(getActivity(), "Bạn phải chọn ảnh menu trước khi thêm", Toast.LENGTH_SHORT).show();
        }
        else if(edtNameMenuDrink.getText().toString().trim().equals(""))
        {
            Toast.makeText(getActivity(), "Bạn phải nhập tên của menu ", Toast.LENGTH_SHORT).show();
        }
        else
        {
            addMenuDrinkToFirebase();
        }
    }

    private void addMenuDrinkToFirebase() {
        calen = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateCreateMenuDrink = "" + simpleDateFormat.format(calen.getTime());
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
//        String email2 = user.getEmail();
        String userCurrentID = firebaseUser.getUid().toString();
        listenerTypeNews = new ListenerTypeNews();
        key_push_menudrink = databaseReference.child("ListMenuDrink").push().getKey();
        // progressDialog.setMessage("Đang cập nhật.........");
        // progressDialog.show();
        StorageReference mountainsRef = mStorageRef.child("MenuDrink").child(key_push_menudrink+".png");

// Create a reference to 'images/mountains.jpg'
        StorageReference mountainImagesRef = mStorageRef.child("images/" + key_push_menudrink + ".png\"");

// While the file names are the same, the references point to different files
        mountainsRef.getName().equals(mountainImagesRef.getName());    // true
        mountainsRef.getPath().equals(mountainImagesRef.getPath());    // false

        // Get the data from an ImageView as bytes
        imgMenuDrink.setDrawingCacheEnabled(true);
        imgMenuDrink.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imgMenuDrink.getDrawable()).getBitmap();
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
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                String photoURL = downloadUrl.toString();
                Log.d("photoURL",photoURL);
                MenuDrink menuDrink = new MenuDrink(key_push_menudrink,edtNameMenuDrink.getText().toString(),photoURL,dateCreateMenuDrink,1);
                databaseReference.child("ListMenuDrink").child(key_push_menudrink).setValue(menuDrink, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if(databaseError == null)                        {
                            Toast.makeText(getActivity(), "Bạn đã thêm menu thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Bạn đã thêm menu thất bại", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            // Toast.makeText(getActivity(), "Đăng tin thất bại !", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }
}