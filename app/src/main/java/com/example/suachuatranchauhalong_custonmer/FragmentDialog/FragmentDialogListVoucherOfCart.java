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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suachuatranchauhalong_custonmer.Activity.ActivityCart;
import com.example.suachuatranchauhalong_custonmer.Adapter.MenuDrink_FragmentDialogUpdateAndHiddenMenuDrink;
import com.example.suachuatranchauhalong_custonmer.Adapter.VoucherOfCartAdapter;
import com.example.suachuatranchauhalong_custonmer.InterfaceOnClick.VoucherOfCartOnItemClick;
import com.example.suachuatranchauhalong_custonmer.Object.Drink;
import com.example.suachuatranchauhalong_custonmer.Object.ListenerIdDrink;
import com.example.suachuatranchauhalong_custonmer.Object.ListenerIdVoucher;
import com.example.suachuatranchauhalong_custonmer.Object.ListenerPriceOrderOfCart;
import com.example.suachuatranchauhalong_custonmer.Object.ListenerTypeNews;
import com.example.suachuatranchauhalong_custonmer.Object.MenuDrink;
import com.example.suachuatranchauhalong_custonmer.Object.OrderDetail;
import com.example.suachuatranchauhalong_custonmer.Object.Voucher;
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

public class FragmentDialogListVoucherOfCart extends DialogFragment implements VoucherOfCartOnItemClick {
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    StorageReference mStorageRef;
    Dialog dialog;
    RecyclerView recyclerViewVoucher;
    ArrayList<Voucher> voucherArrayList;
    VoucherOfCartAdapter voucherOfCartAdapter;
    float priceOrder;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //Khai báo 1 đối tượng Dialog khoogn sử dụng giao diện mặc định của hệ thống.Tham số truyền vào là context
        dialog = new Dialog(getActivity());
        //Thiết lập giao diện cho đối tượng Dialog qua phương thức setContentView().Tham số truyền vào ResourceLayout
        dialog.setContentView(R.layout.fragment_dialog_list_voucher_of_cart);
        //Khi click vào màn hình thì dialog sẽ k bị tắt trừ khi bấm nút hủy
        dialog.setTitle("Danh sách voucher");
        initReferencesObject();
        addControlsDialog();
        addEventsDialog();
        initDataRecycleViewVoucher();
        //initData();
        return dialog;
    }
    private void initReferencesObject() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        listenerPriceOrderOfCart = new ListenerPriceOrderOfCart();
        priceOrder = listenerPriceOrderOfCart.getPriceOrder();
    //    listenerIdDrink = new ListenerIdDrink();
        // Toast.makeText(getActivity(), "" + listenerIDMenuDrink.getIdMenuDrink().toString(), Toast.LENGTH_SHORT).show();
    }
    private void addEventsDialog() {

//        btnUpdateDrink.setOnClickListener(this);
//        btnCancel.setOnClickListener(this);
//        imgCamera.setOnClickListener(this);
//        imgFolder.setOnClickListener(this);
    }

    private void addControlsDialog() {
        recyclerViewVoucher = (RecyclerView) dialog.findViewById(R.id.FragmentDialogListVoucherOfCart_recycleVoucher);
//        imgDrink = (ImageView) dialog.findViewById(R.id.FragmentDialogUpdateDrink_imgDrink);
//        imgDrink.setImageResource(R.drawable.ic_launcher_background);
//        imgCamera = (ImageView) dialog.findViewById(R.id.FragmentDialogUpdateDrink_imgCamera);
//        imgFolder = (ImageView) dialog.findViewById(R.id.FragmentDialogUpdateDrink_imgFolder);
//        edtNameDrink = (EditText) dialog.findViewById(R.id.FragmentDialogUpdateDrink_edtNameDrink);
//        edtPriceDrink = (EditText) dialog.findViewById(R.id.FragmentDialogUpdateDrink_edtPriceDrink);
//        btnUpdateDrink = (Button) dialog.findViewById(R.id.FragmentDialogUpdateDrink_btnUpdateDrink);
//        btnCancel = (Button) dialog.findViewById(R.id.FragmentDialogUpdateDrink_btnCancel);
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

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null)
//        {
//            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//            imgDrink.setImageBitmap(bitmap);
//            checkChooseImageDrink = true;
//        }
//        if(requestCode == READ_REQUEST_CODE && resultCode == RESULT_OK && data != null)
//        {
////            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
////            imgFace.setImageBitmap(bitmap);
//            Uri uri = null;
//            uri = data.getData();
//            Log.i("AAAA", "Uri: " + uri.toString());
//            imgDrink.setImageURI(uri);
//            checkChooseImageDrink = true;
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    String nameDrink;
    float priceDrink;
    private void initDataRecycleViewVoucher() {
//        calen = Calendar.getInstance();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy");
//        dateOrder = "" + simpleDateFormat.format(calen.getTime());
        voucherArrayList = new ArrayList<>();
        databaseReference.child("ListCustomer").child(firebaseUser.getUid().toString()).child("ListVoucher")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    try {
                        Voucher voucher = dataSnapshot1.getValue(Voucher.class);
                        if (Integer.parseInt(dataSnapshot1.child("statusUse").getValue().toString())==1) {
                            voucherArrayList.add(voucher);
                        }
                    }catch (NullPointerException ex)
                    {

                    }

                }
                voucherOfCartAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        DividerItemDecoration dividerHorizontal =
                new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);

        recyclerViewVoucher.addItemDecoration(dividerHorizontal);
        voucherOfCartAdapter = new VoucherOfCartAdapter(getActivity(),voucherArrayList,this);
        recyclerViewVoucher.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true));
        recyclerViewVoucher.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewVoucher.setAdapter(voucherOfCartAdapter);
    }
    ListenerPriceOrderOfCart listenerPriceOrderOfCart = new ListenerPriceOrderOfCart();
    ActivityCart activityCart ;
    ListenerIdVoucher listenerIdVoucher;
    @Override
    public void onItemClickListener(final Voucher voucher) {
        if(priceOrder>voucher.getPriceApply())
        {
            Toast.makeText(getActivity(), "Giá trị đơn hàng hiện tại của bạn có giá trị lớn hơn giá trị mà voucher áp dụng. Hãy chọn voucher phù hợp !" , Toast.LENGTH_LONG).show();
        }
        else if(priceOrder<=voucher.getPriceApply())
        {
            databaseReference.child("ListOrderDetail").child(firebaseUser.getUid().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        final OrderDetail orderDetail = dataSnapshot1.getValue(OrderDetail.class);
                        if (orderDetail.getIdOrder().toString().equals("")) {
                            databaseReference.child("ListOrderDetail").child(firebaseUser.getUid().toString()).
                                    child(orderDetail.getIdOrderDetail()).child("idVoucher").setValue(voucher.getIdVoucher());
                        }

                    }
                    //     khuyenMai = listenerPriceOrderOfCart.getGiaTriKhuyenMai();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
           // listenerPriceOrderOfCart.setGiaTriKhuyenMai(voucher.getPricePromotion());
           // listenerIdVoucher.getDataVoucher(voucher);
            Toast.makeText(getActivity(), "Áp dụng voucher thành công " , Toast.LENGTH_SHORT).show();
            dismiss();
        }
       // Toast.makeText(getActivity(), "" + voucher.getIdVoucher(), Toast.LENGTH_SHORT).show();
    }
//    private void initData()
//    {
//        //  Log.d("Type News " ,listenerTypeNews.getTypeNews().toString());
//        //  Log.d("ID News " ,listenerTypeNews.getUidNews().toString());
//        databaseReference.child("ListDrink").child(listenerIdDrink.getIdDrink()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Drink drink = dataSnapshot.getValue(Drink.class);
//                Picasso.get()
//                        .load(drink.getImgUriDrink())
//                        .fit()
//                        .into(imgDrink);
//                edtNameDrink.setText(drink.getNameDrink().toString());
//                edtPriceDrink.setText("" +drink.getPriceDrink());
//                nameDrink = drink.getNameDrink().toString();
//                priceDrink = drink.getPriceDrink();
//            }
//
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
//    private void updateDrink() {
//        if(edtNameDrink.getText().toString().trim().equals("") || edtPriceDrink.getText().toString().trim().equals(""))
//        {
//            Toast.makeText(getActivity(), "Bạn phải nhập tên và đơn giá của đồ uống", Toast.LENGTH_SHORT).show();
//        }
//        else if(checkChooseImageDrink==false && edtNameDrink.getText().toString().trim().equals(nameDrink) && Integer.parseInt(edtPriceDrink.getText().toString().trim())==priceDrink)
//        {
//            Toast.makeText(getActivity(), "Bạn đã không thay đổi thông tin gì", Toast.LENGTH_SHORT).show();
//        }
//        else if( Float.parseFloat(edtPriceDrink.getText().toString().trim())>50000)
//        {
//            Toast.makeText(getActivity(), "Giá đồ uống tối đa là 50.000đ", Toast.LENGTH_SHORT).show();
//        }
//        else
//        {
//            updateDrinkToFirebase();
//        }
//    }
//    String photoURL = "";
//    ProgressDialog progressDialog;
//    private void updateDrinkToFirebase() {
//        progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setMessage("Cập nhật....");
//        progressDialog.show();
//        final StorageReference mountainsRef = mStorageRef.child("Drink").child(listenerIdDrink.getIdDrink()+".png");
//        StorageReference mountainImagesRef = mStorageRef.child("images/" + listenerIdDrink.getIdDrink() + ".png\"");
//        mountainsRef.getName().equals(mountainImagesRef.getName());    // true
//        mountainsRef.getPath().equals(mountainImagesRef.getPath());    // false
//        imgDrink.setDrawingCacheEnabled(true);
//        imgDrink.buildDrawingCache();
//        Bitmap bitmap = ((BitmapDrawable) imgDrink.getDrawable()).getBitmap();
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
//                mountainsRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        photoURL = uri.toString();
//                        databaseReference.child("ListDrink").child(listenerIdDrink.getIdDrink()).child("imgUriDrink").setValue(photoURL);
//                        databaseReference.child("ListDrink").child(listenerIdDrink.getIdDrink()).child("nameDrink").setValue(edtNameDrink.getText().toString().trim());
//                        databaseReference.child("ListDrink").child(listenerIdDrink.getIdDrink()).child("priceDrink").setValue(Float.parseFloat(edtPriceDrink.getText().toString().trim()));
//                        Toast.makeText(getActivity(), "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
//                        progressDialog.dismiss();
//                        dialog.dismiss();
//                    }
//                });
//                Log.d("photoURL",photoURL);
//                //  Drink drink = new Drink(listenerIdDrink.getIdDrink(),listenerIdDrink.getIdMenuDrink(),edtNameDrink.getText().toString(),photoURL,Integer.parseInt(edtPriceDrink.getText().toString()),dateCreateDrink,1);
//            }
//        });
//    }

}