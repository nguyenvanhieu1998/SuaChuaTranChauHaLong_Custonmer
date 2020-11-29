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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suachuatranchauhalong_custonmer.Adapter.MenuDrink_FragmentDialogUpdateAndHiddenMenuDrink;
import com.example.suachuatranchauhalong_custonmer.InterfaceOnClick.OnItemClickListener;
import com.example.suachuatranchauhalong_custonmer.Object.Customer;
import com.example.suachuatranchauhalong_custonmer.Object.ListenerTypeNews;
import com.example.suachuatranchauhalong_custonmer.Object.MenuDrink;
import com.example.suachuatranchauhalong_custonmer.Object.News;
import com.example.suachuatranchauhalong_custonmer.Object.OrderDetail;
import com.example.suachuatranchauhalong_custonmer.Object.Shipper;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

public class FragmentDialogHiddenMenuDrinkForAdmin extends DialogFragment implements View.OnClickListener, OnItemClickListener {
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    StorageReference mStorageRef;
    Dialog dialog;
    ImageView imgMenuDrink,imgCamera,imgFolder;
    EditText edtNameMenuDrink;
    Button btnHiddenMenuDrink,btnCancel;
    RecyclerView recyclerViewMenuDrink;
    int REQUEST_CODE = 1;
    int READ_REQUEST_CODE = 2;
    Calendar calen;
    String dateCreateMenuDrink,key_push_menudrink;
    ListenerTypeNews listenerTypeNews;
    Boolean checkChooseImageMenuDrink = false;
    ArrayList<MenuDrink> menuDrinkArrayList;
    Boolean checkUpdateImage = false;
    String nameMenuDrink;
    MenuDrink_FragmentDialogUpdateAndHiddenMenuDrink menuDrinkAdapter;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //Khai báo 1 đối tượng Dialog khoogn sử dụng giao diện mặc định của hệ thống.Tham số truyền vào là context
        dialog = new Dialog(getActivity());
        //Thiết lập giao diện cho đối tượng Dialog qua phương thức setContentView().Tham số truyền vào ResourceLayout
        dialog.setContentView(R.layout.fragment_dialog_hiddenmenudrink_for_admin);
        //Khi click vào màn hình thì dialog sẽ k bị tắt trừ khi bấm nút hủy
        dialog.setTitle("Ẩn thông tin menu");
        initReferencesObject();
        addControlsDialog();
        initDataRecycleViewMenuDrink();
        addEventsDialog();
        return dialog;
    }
    private void initReferencesObject() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
    }
    private void initDataRecycleViewMenuDrink() {
//        calen = Calendar.getInstance();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy");
//        dateOrder = "" + simpleDateFormat.format(calen.getTime());
        menuDrinkArrayList = new ArrayList<>();
        databaseReference.child("ListMenuDrink").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    MenuDrink menuDrink = dataSnapshot1.getValue(MenuDrink.class);
                    if (menuDrink.getStatus() ==1) {
                        menuDrinkArrayList.add(menuDrink);
                    }
                }
                menuDrinkAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        DividerItemDecoration dividerHorizontal =
                new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);

        recyclerViewMenuDrink.addItemDecoration(dividerHorizontal);
        menuDrinkAdapter = new MenuDrink_FragmentDialogUpdateAndHiddenMenuDrink(getActivity(),menuDrinkArrayList,this);
        recyclerViewMenuDrink.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true));
        recyclerViewMenuDrink.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewMenuDrink.setAdapter(menuDrinkAdapter);
    }
    private void getMenuDrinkCurrent(String idMenuDrink)
    {
        // Log.d("Type News " ,listenerTypeNews.getTypeNews().toString());
        //  Log.d("ID News " ,listenerTypeNews.getUidNews().toString());
        databaseReference.child("ListMenuDrink").child(idMenuDrink).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                MenuDrink menuDrink = dataSnapshot.getValue(MenuDrink.class);
                Picasso.get()
                        .load(menuDrink.getImageUriMenuDrink())
                        .fit()
                        .into(imgMenuDrink);
                edtNameMenuDrink.setText(menuDrink.getNameMenuDrink().toString());
                nameMenuDrink = menuDrink.getNameMenuDrink().toString();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void addEventsDialog() {
        btnHiddenMenuDrink.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
//        imgCamera.setOnClickListener(this);
//        imgFolder.setOnClickListener(this);
    }

    private void addControlsDialog() {
        imgMenuDrink = (ImageView) dialog.findViewById(R.id.FragmentDialogHiddenMenuDrink_imgTittleMenuDrink);
        imgMenuDrink.setImageResource(R.drawable.anhtintucbt4);
//        imgCamera = (ImageView) dialog.findViewById(R.id.FragmentDialogHiddenMenuDrink_imgCamera);
//        imgFolder = (ImageView) dialog.findViewById(R.id.FragmentDialogHiddenMenuDrink_imgFolder);
        edtNameMenuDrink = (EditText) dialog.findViewById(R.id.FragmentDialogHiddenMenuDrink_edtNameMenuDrink);
        btnHiddenMenuDrink = (Button) dialog.findViewById(R.id.FragmentDialogHiddenMenuDrink_btnHiddenMenuDrink);
        btnCancel = (Button) dialog.findViewById(R.id.FragmentDialogHiddenMenuDrink_btnCancel);
        recyclerViewMenuDrink = (RecyclerView) dialog.findViewById(R.id.FragmentDialogHiddenMenuDrink_recycleMenuDrink);
    }
//    private void getImageFolder() {
//        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.setType("image/*");
//        startActivityForResult(intent, READ_REQUEST_CODE);
//    }
//    private void getImageCamera() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent,REQUEST_CODE);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null)
//        {
//            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//            imgMenuDrink.setImageBitmap(bitmap);
//            checkUpdateImage = true;
//        }
//        if(requestCode == READ_REQUEST_CODE && resultCode == RESULT_OK && data != null)
//        {
////            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
////            imgFace.setImageBitmap(bitmap);
//            Uri uri = null;
//            uri = data.getData();
//            Log.i("AAAA", "Uri: " + uri.toString());
//            imgMenuDrink.setImageURI(uri);
//            checkUpdateImage = true;
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.FragmentDialogHiddenMenuDrink_btnHiddenMenuDrink :
                updateStatusMenuDrinkToFirebase();
                break;
            case R.id.FragmentDialogHiddenMenuDrink_btnCancel :
                dialog.dismiss();
                break;
        }

    }

    private void updateStatusMenuDrinkToFirebase() {
                databaseReference.child("ListMenuDrink").child(idMenuDrinkCurrent).child("status").setValue(0);
               // databaseReference.child("ListMenuDrink").child(idMenuDrinkCurrent).child("nameMenuDrink").setValue(edtNameMenuDrink.getText().toString());
                Toast.makeText(getContext(), "Ẩn menu thành công", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
//            }
//        });
    }
    String idMenuDrinkCurrent = "";


    @Override
    public void onItemClickListener(MenuDrink menuDrink) {
        getMenuDrinkCurrent(menuDrink.getIdMenuDrink().toString());
        idMenuDrinkCurrent= menuDrink.getIdMenuDrink().toString();
        //  Toast.makeText(getActivity(), "" + menuDrink.getIdMenuDrink(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClickListener(Customer customer) {

    }

    @Override
    public void onItemClickListener(Shipper shipper) {

    }

    @Override
    public void onItemClickListener(OrderDetail orderDetail) {

    }
}