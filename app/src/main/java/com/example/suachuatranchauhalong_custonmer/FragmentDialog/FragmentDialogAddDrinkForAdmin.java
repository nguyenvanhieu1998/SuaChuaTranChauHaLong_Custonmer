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

import com.example.suachuatranchauhalong_custonmer.Object.Drink;
import com.example.suachuatranchauhalong_custonmer.Object.ListenerIDMenuDrink;
import com.example.suachuatranchauhalong_custonmer.Object.ListenerTypeNews;
import com.example.suachuatranchauhalong_custonmer.Object.MenuDrink;
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

public class FragmentDialogAddDrinkForAdmin extends DialogFragment implements View.OnClickListener{
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    StorageReference mStorageRef;
    Dialog dialog;
    ImageView imgDrink,imgCamera,imgFolder;
    EditText edtNameDrink,edtPriceDrink;
    Button btnAddDrink,btnCancel;
    int REQUEST_CODE = 1;
    int READ_REQUEST_CODE = 2;
    Calendar calen;
    String dateCreateDrink,key_push_drink;
    ListenerTypeNews listenerTypeNews;
    Boolean checkChooseImageDrink = false;
    ListenerIDMenuDrink listenerIDMenuDrink;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //Khai báo 1 đối tượng Dialog khoogn sử dụng giao diện mặc định của hệ thống.Tham số truyền vào là context
        dialog = new Dialog(getActivity());
        //Thiết lập giao diện cho đối tượng Dialog qua phương thức setContentView().Tham số truyền vào ResourceLayout
        dialog.setContentView(R.layout.fragment_dialog_adddrink_for_admin);
        //Khi click vào màn hình thì dialog sẽ k bị tắt trừ khi bấm nút hủy
        dialog.setTitle("Thêm đồ uống");
        initReferencesObject();
        addControlsDialog();
        addEventsDialog();
        return dialog;
    }
    private void initReferencesObject() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        listenerIDMenuDrink = new ListenerIDMenuDrink();
       // Toast.makeText(getActivity(), "" + listenerIDMenuDrink.getIdMenuDrink().toString(), Toast.LENGTH_SHORT).show();
    }
    private void addEventsDialog() {
        btnAddDrink.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        imgCamera.setOnClickListener(this);
        imgFolder.setOnClickListener(this);
    }

    private void addControlsDialog() {
        imgDrink = (ImageView) dialog.findViewById(R.id.FragmentDialogAddDrink_imgDrink);
        imgDrink.setImageResource(R.drawable.ic_launcher_background);
        imgCamera = (ImageView) dialog.findViewById(R.id.FragmentDialogAddDrink_imgCamera);
        imgFolder = (ImageView) dialog.findViewById(R.id.FragmentDialogAddDrink_imgFolder);
        edtNameDrink = (EditText) dialog.findViewById(R.id.FragmentDialogAddDrink_edtNameDrink);
        edtPriceDrink = (EditText) dialog.findViewById(R.id.FragmentDialogAddDrink_edtPriceDrink);
        btnAddDrink = (Button) dialog.findViewById(R.id.FragmentDialogAddDrink_btnAddDrink);
        btnCancel = (Button) dialog.findViewById(R.id.FragmentDialogAddDrink_btnCancel);
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
            imgDrink.setImageBitmap(bitmap);
            checkChooseImageDrink = true;
        }
        if(requestCode == READ_REQUEST_CODE && resultCode == RESULT_OK && data != null)
        {
//            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//            imgFace.setImageBitmap(bitmap);
            Uri uri = null;
            uri = data.getData();
            Log.i("AAAA", "Uri: " + uri.toString());
            imgDrink.setImageURI(uri);
            checkChooseImageDrink = true;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.FragmentDialogAddDrink_btnAddDrink :
                addDrink();
                break;
            case R.id.FragmentDialogAddDrink_btnCancel :
                dialog.dismiss();
                break;
            case R.id.FragmentDialogAddDrink_imgCamera :
                getImageCamera();
                break;
            case R.id.FragmentDialogAddDrink_imgFolder :
                getImageFolder();
                break;
        }

    }

    private void addDrink() {
        if(checkChooseImageDrink == false)
        {
            Toast.makeText(getActivity(), "Bạn phải chọn ảnh đồ uống trước khi thêm", Toast.LENGTH_SHORT).show();
        }
        else if(edtNameDrink.getText().toString().trim().equals("") || edtPriceDrink.getText().toString().trim().equals(""))
        {
            Toast.makeText(getActivity(), "Bạn phải nhập tên và đơn giá của đồ uống ", Toast.LENGTH_SHORT).show();
        }
        else
        {
            addDrinkToFirebase();
        }
    }

    private void addDrinkToFirebase() {
        calen = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateCreateDrink = "" + simpleDateFormat.format(calen.getTime());
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
//        String email2 = user.getEmail();
        String userCurrentID = firebaseUser.getUid().toString();
        listenerTypeNews = new ListenerTypeNews();
        key_push_drink = databaseReference.child("ListDrink").push().getKey();
        // progressDialog.setMessage("Đang cập nhật.........");
        // progressDialog.show();
        StorageReference mountainsRef = mStorageRef.child("Drink").child(key_push_drink+".png");

// Create a reference to 'images/mountains.jpg'
        StorageReference mountainImagesRef = mStorageRef.child("images/" + key_push_drink + ".png\"");

// While the file names are the same, the references point to different files
        mountainsRef.getName().equals(mountainImagesRef.getName());    // true
        mountainsRef.getPath().equals(mountainImagesRef.getPath());    // false

        // Get the data from an ImageView as bytes
        imgDrink.setDrawingCacheEnabled(true);
        imgDrink.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imgDrink.getDrawable()).getBitmap();
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
                Drink drink = new Drink(key_push_drink,listenerIDMenuDrink.getIdMenuDrink().toString(),edtNameDrink.getText().toString(),photoURL,Integer.parseInt(edtPriceDrink.getText().toString()),dateCreateDrink,1);
                databaseReference.child("ListDrink").child(key_push_drink).setValue(drink, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if(databaseError == null)                        {
                            Toast.makeText(getActivity(), "Bạn đã thêm đồ uống thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Bạn đã thêm đồ uống thất bại", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            // Toast.makeText(getActivity(), "Đăng tin thất bại !", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }

}
