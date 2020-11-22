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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suachuatranchauhalong_custonmer.Adapter.DetailOrderAdapter;
import com.example.suachuatranchauhalong_custonmer.Adapter.FragmentUpdateDrinkOfCart_OrderDetailAdapter;
import com.example.suachuatranchauhalong_custonmer.Adapter.MenuDrink_FragmentDialogUpdateAndHiddenMenuDrink;
import com.example.suachuatranchauhalong_custonmer.InterfaceOnClick.OnItemClickListener;
import com.example.suachuatranchauhalong_custonmer.Object.Customer;
import com.example.suachuatranchauhalong_custonmer.Object.Drink;
import com.example.suachuatranchauhalong_custonmer.Object.ListenerIdOrderDetail;
import com.example.suachuatranchauhalong_custonmer.Object.ListenerTypeNews;
import com.example.suachuatranchauhalong_custonmer.Object.MenuDrink;
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
import java.util.ArrayList;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

public class FragmentDialogUpdateDrinkOfCart extends DialogFragment implements View.OnClickListener {
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    StorageReference mStorageRef;
    Dialog dialog;
    ImageView imgDrink,imgPlus,imgMinus;
    Button btnUpdateMountDrink,btnCancel,btnDeleteDrink;
    TextView txtNameDrink,txtMountDrink,txtBonus,txtMountDrink2;
//    ListenerTypeNews listenerTypeNews;
    Boolean checkChooseImageMenuDrink = false;
    RecyclerView recyclerViewDrinkOfOrderDetail;
    FragmentUpdateDrinkOfCart_OrderDetailAdapter fragmentUpdateDrinkOfCart_OrderDetailAdapter;
    ArrayList<OrderDetail> orderDetailArrayList;
    Boolean checkUpdateImage = false;
    String nameMenuDrink;
    ListenerIdOrderDetail listenerIdOrderDetail;
  //  MenuDrink_FragmentDialogUpdateAndHiddenMenuDrink menuDrinkAdapter;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //Khai báo 1 đối tượng Dialog khoogn sử dụng giao diện mặc định của hệ thống.Tham số truyền vào là context
        dialog = new Dialog(getActivity());
        //Thiết lập giao diện cho đối tượng Dialog qua phương thức setContentView().Tham số truyền vào ResourceLayout
        dialog.setContentView(R.layout.fragment_dialog_update_drink_of_cart);
        //Khi click vào màn hình thì dialog sẽ k bị tắt trừ khi bấm nút hủy
        dialog.setTitle("Cập nhật giỏ hàng");
        initReferencesObject();
        addControlsDialog();
     //   initDataRecycleViewDrinkOfCart();
        addEventsDialog();
        initData ();
        return dialog;
    }
    private void initReferencesObject() {
        listenerIdOrderDetail = new ListenerIdOrderDetail();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
    }
//    private void initDataRecycleViewDrinkOfCart() {
//        orderDetailArrayList = new ArrayList<>();
//        databaseReference.child("ListOrderDetail").child(firebaseUser.getUid().toString()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    final OrderDetail orderDetail = dataSnapshot1.getValue(OrderDetail.class);
//                    if (orderDetail.getIdOrder().toString().equals("")) {
//
//                        //    Drink drink = dataSnapshot.getValue(Drink.class);
//                        orderDetailArrayList.add(orderDetail);
////                                totalPriceOrder += drink.getPriceDrink()*orderDetail.getMount();
////                                txtPriceOfOrder.setText(""+totalPriceOrder);
//
//                    }
//
//                }
//                fragmentUpdateDrinkOfCart_OrderDetailAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//                DividerItemDecoration dividerHorizontal =
//                new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
//
//        recyclerViewDrinkOfOrderDetail.addItemDecoration(dividerHorizontal);
//        fragmentUpdateDrinkOfCart_OrderDetailAdapter = new FragmentUpdateDrinkOfCart_OrderDetailAdapter(orderDetailArrayList,getActivity(),this);
//        recyclerViewDrinkOfOrderDetail.setAdapter(fragmentUpdateDrinkOfCart_OrderDetailAdapter);
//        recyclerViewDrinkOfOrderDetail.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, true));
//        recyclerViewDrinkOfOrderDetail.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//    }
    private void addEventsDialog() {
        btnUpdateMountDrink.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnDeleteDrink.setOnClickListener(this);
        imgMinus.setOnClickListener(this);
        imgPlus.setOnClickListener(this);
    }

    private void addControlsDialog() {
        imgDrink = (ImageView) dialog.findViewById(R.id.FragmentDialogUpdateDrinkOfCart_imgDrink);
        imgPlus = (ImageView) dialog.findViewById(R.id.FragmentDialogUpdateDrinkOfCart_imgPlus);
        imgMinus = (ImageView) dialog.findViewById(R.id.FragmentDialogUpdateDrinkOfCart_imgMinus);
        btnCancel = (Button) dialog.findViewById(R.id.FragmentDialogUpdateDrinkOfCart_btnCancel);
        btnUpdateMountDrink = (Button) dialog.findViewById(R.id.FragmentDialogUpdateDrinkOfCart_btnUpdate);
        btnDeleteDrink = (Button) dialog.findViewById(R.id.FragmentDialogUpdateDrinkOfCart_btnDelete);
        txtNameDrink = (TextView) dialog.findViewById(R.id.FragmentDialogUpdateDrinkOfCart_txtNameDrink);
        txtMountDrink = (TextView) dialog.findViewById(R.id.FragmentDialogUpdateDrinkOfCart_txtMountDrink);
        txtMountDrink2 = (TextView) dialog.findViewById(R.id.FragmentDialogUpdateDrinkOfCart_txtMount2);
        txtBonus = (TextView) dialog.findViewById(R.id.FragmentDialogUpdateDrinkOfCart_txtBonus);
       // recyclerViewDrinkOfOrderDetail = (RecyclerView) dialog.findViewById(R.id.FragmentDialogUpdateDrinkOfCart_recycleDrink);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.FragmentDialogUpdateDrinkOfCart_imgPlus :
                plusMountDrink();
                break;
            case R.id.FragmentDialogUpdateDrinkOfCart_imgMinus :
                minusMountDrink();
                break;
            case R.id.FragmentDialogUpdateDrinkOfCart_btnUpdate :
                updateMountDrink();
               // updateMountDrink();
                break;
            case R.id.FragmentDialogUpdateDrinkOfCart_btnCancel :
                dialog.dismiss();
                break;
            case R.id.FragmentDialogUpdateDrinkOfCart_btnDelete :
                break;
        }

    }
    static int mount1;
    private void updateMountDrink() {
        databaseReference.child("ListOrderDetail").child(firebaseUser.getUid().toString()).
                child(listenerIdOrderDetail.getIdOrderDetail().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                OrderDetail orderDetail = dataSnapshot.getValue(OrderDetail.class);
                totalPriceBanDau = orderDetail.getPrice();
                priceGoiThem = orderDetail.getPriceBonus();
                databaseReference.child("ListDrink").child(orderDetail.getIdDrink().toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Drink drink = dataSnapshot.getValue(Drink.class);
                        Picasso.get()
                                .load(drink.getImgUriDrink())
                                .fit()
                                .into(imgDrink);
                        txtNameDrink.setText(drink.getNameDrink().toString());
                        priceDrink = drink.getPriceDrink();
                        totalPrice = (mount*priceDrink)+(priceGoiThem*mount);
//                nameDrink = drink.getNameDrink().toString();
//                priceDrink = drink.getPriceDrink();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
//                if(Integer.parseInt(txtMountDrink2.getText().toString())==orderDetail.getMount())
//                {
//                    Toast.makeText(getActivity(), "Bạn đã không thay đổi gì", Toast.LENGTH_SHORT).show();
//                }
//                else {
                    databaseReference.child("ListOrderDetail").child(firebaseUser.getUid().toString()).
                            child(listenerIdOrderDetail.getIdOrderDetail().toString()).child("mount").setValue(mount);
                    databaseReference.child("ListOrderDetail").child(firebaseUser.getUid().toString()).
                            child(listenerIdOrderDetail.getIdOrderDetail().toString()).child("price").setValue(totalPrice);
//                    Toast.makeText(getActivity(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
               // }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    int mount ;
    float priceDrink,priceGoiThem;
    float totalPrice = (mount*priceDrink)+(priceGoiThem*mount),totalPriceBanDau;


    public void initData () {
        mount = Integer.parseInt(txtMountDrink2.getText().toString());
        databaseReference.child("ListOrderDetail").child(firebaseUser.getUid().toString()).
                child(listenerIdOrderDetail.getIdOrderDetail().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                OrderDetail orderDetail = dataSnapshot.getValue(OrderDetail.class);
                mount = orderDetail.getMount();
                txtMountDrink.setText("Số lượng : "+ mount);
                txtBonus.setText("Gọi thêm : " + orderDetail.getBonus().toString());
                txtMountDrink2.setText(""+mount);
                priceGoiThem = orderDetail.getPriceBonus();
               // totalPriceBanDau = orderDetail.getPrice();
                databaseReference.child("ListDrink").child(orderDetail.getIdDrink().toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Drink drink = dataSnapshot.getValue(Drink.class);
                        Picasso.get()
                                .load(drink.getImgUriDrink())
                                .fit()
                                .into(imgDrink);
                        txtNameDrink.setText(drink.getNameDrink().toString());
                        priceDrink = drink.getPriceDrink();
                      //  totalPrice = (mount*priceDrink)+(priceGoiThem*mount);
//                nameDrink = drink.getNameDrink().toString();
//                priceDrink = drink.getPriceDrink();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
      //  Toast.makeText(getActivity(), "" + totalPriceBanDau, Toast.LENGTH_SHORT).show();
    }
    private void minusMountDrink() {
        if(mount>1)
        {
            mount--;
        }

        txtMountDrink.setText("Số lượng : "+mount);
        txtMountDrink2.setText(""+mount);
        totalPrice = (mount*priceDrink)+(priceGoiThem*mount);
        Toast.makeText(getActivity(), "" + totalPrice, Toast.LENGTH_SHORT).show();
       // btnTotalMoney.setText("+ " + totalPrice +" đ");
    }

    private void plusMountDrink() {
        mount++;
        txtMountDrink.setText("Số lượng : "+mount);
        txtMountDrink2.setText(""+mount);
        totalPrice = (mount*priceDrink)+(priceGoiThem*mount);
        Toast.makeText(getActivity(), "" + totalPrice, Toast.LENGTH_SHORT).show();
       // btnTotalMoney.setText("+ " + totalPrice +" đ");
    }

//    private void updateMenuDrink() {
////        if(checkChooseImageMenuDrink == false)
////        {
////            Toast.makeText(getActivity(), "Bạn phải chọn ảnh menu trước khi thêm", Toast.LENGTH_SHORT).show();
////        }
////        else
//        if(edtNameMenuDrink.getText().toString().trim().equals(""))
//        {
//            Toast.makeText(getActivity(), "Bạn phải  nhập tên của menu ", Toast.LENGTH_SHORT).show();
//        }
//        else if(checkUpdateImage==false && nameMenuDrink.equals(edtNameMenuDrink.getText().toString().trim()))
//        {
//            Toast.makeText(getActivity(), "Bạn đã không thay đổi thông tin gì ", Toast.LENGTH_SHORT).show();
//        }
//        else
//        {
//            updateMenuDrinkToFirebase();
//        }
//    }
//
//    private void updateMenuDrinkToFirebase() {
//        StorageReference mountainsRef = mStorageRef.child("MenuDrink").child(idMenuDrinkCurrent +".png");
//        StorageReference mountainImagesRef = mStorageRef.child("images/" + idMenuDrinkCurrent + ".png\"");
//
//// While the file names are the same, the references point to different files
//        mountainsRef.getName().equals(mountainImagesRef.getName());    // true
//        mountainsRef.getPath().equals(mountainImagesRef.getPath());    // false
//
//        // Get the data from an ImageView as bytes
//        imgMenuDrink.setDrawingCacheEnabled(true);
//        imgMenuDrink.buildDrawingCache();
//        Bitmap bitmap = ((BitmapDrawable) imgMenuDrink.getDrawable()).getBitmap();
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
//                Uri downloadUrl = taskSnapshot.getDownloadUrl();
//                String photoURL = downloadUrl.toString();
//                Log.d("photoURL",photoURL);
//                databaseReference.child("ListMenuDrink").child(idMenuDrinkCurrent).child("imageUriMenuDrink").setValue(photoURL);
//                databaseReference.child("ListMenuDrink").child(idMenuDrinkCurrent).child("nameMenuDrink").setValue(edtNameMenuDrink.getText().toString());
//                Toast.makeText(getContext(), "Cập nhật menu thành công", Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//            }
//        });
//    }
//    String idMenuDrinkCurrent = "";
//
//    @Override
//    public void onItemClickListener(MenuDrink menuDrink) {
//        getMenuDrinkCurrent(menuDrink.getIdMenuDrink().toString());
//        idMenuDrinkCurrent= menuDrink.getIdMenuDrink().toString();
//        //  Toast.makeText(getActivity(), "" + menuDrink.getIdMenuDrink(), Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onItemClickListener(Customer customer) {
//
//    }
//
//    @Override
//    public void onItemClickListener(Shipper shipper) {
//
//    }
}