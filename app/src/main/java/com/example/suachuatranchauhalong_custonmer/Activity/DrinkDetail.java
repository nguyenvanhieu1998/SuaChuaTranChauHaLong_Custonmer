package com.example.suachuatranchauhalong_custonmer.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suachuatranchauhalong_custonmer.Object.Customer;
import com.example.suachuatranchauhalong_custonmer.Object.Drink;
import com.example.suachuatranchauhalong_custonmer.Object.ListenerIdDrink;
import com.example.suachuatranchauhalong_custonmer.Object.OrderDetail;
import com.example.suachuatranchauhalong_custonmer.Object.OrderDetail2;
import com.example.suachuatranchauhalong_custonmer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DrinkDetail extends AppCompatActivity implements View.OnClickListener {
    ImageView imgDrink, imgLove, imgPlus, imgMinus;
    Button btnTotalMoney;
    TextView txtNameDrink, txtPriceDrink, txtMountDrink;
    CheckBox cbTranChau, cbThachRauCau, cbDuaKho, cbChuoiKho, cbNhoKho;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    Intent intent;
    int mount = 1;
    String key_push_listorderdetail;
    float price;
    SharedPreferences shap;
    Toolbar toolbar;
    LinearLayout linearDatHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_detail);
        initReferencesObject();
        addControls();
        initData();
        // getPriceOfDrink();
        addEvents();
        checkAdmin();
        setCheckedChangeCheckbox();
        //   shap = getSharedPreferences("drinkLove",MODE_PRIVATE);
        //  getDataSharedPreferences();
        initImageLove();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void initReferencesObject() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        intent = getIntent();
        //    listenerIdDrink = new ListenerIdDrink();
        // Toast.makeText(this, "" + intent.getStringExtra("IDMenuDrink"), Toast.LENGTH_SHORT).show();
    }

    String nameDrink, imgDrink2;

    private void initData() {
        //  Log.d("Type News " ,listenerTypeNews.getTypeNews().toString());
        //  Log.d("ID News " ,listenerTypeNews.getUidNews().toString());
        databaseReference.child("ListDrink").child(intent.getStringExtra("IdDrink").toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Drink drink = dataSnapshot.getValue(Drink.class);
                Picasso.get()
                        .load(drink.getImgUriDrink())
                        .fit()
                        .into(imgDrink);
                imgDrink2 = drink.getImgUriDrink();
                txtNameDrink.setText(drink.getNameDrink().toString());
                txtPriceDrink.setText("Giá : " + drink.getPriceDrink() + "đ");
                price = drink.getPriceDrink();
                totalPrice = mount * price;
                nameDrink = drink.getNameDrink().toString();
                btnTotalMoney.setText("+ " + totalPrice + " đ");
//                nameDrink = drink.getNameDrink().toString();
//                priceDrink = drink.getPriceDrink();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //    private void getNameMenuDrink()
//    {
//        databaseReference.child("ListMenuDrink").child(intent.getStringExtra("idMenuDrink").toString()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
    float priceGoiThem = 0;

    private String showResultCheckBox() {
        String message = null;
        if (this.cbTranChau.isChecked()) {
            if (message == null) {
                message = this.cbTranChau.getText().toString();
            } else {
                message += ", " + this.cbTranChau.getText().toString();
            }
        }
        if (this.cbThachRauCau.isChecked()) {
            // priceGoiThem+=5;
            if (message == null) {
                message = this.cbThachRauCau.getText().toString();
            } else {
                message += ", " + this.cbThachRauCau.getText().toString();
            }
        }
        if (this.cbDuaKho.isChecked()) {
            //     priceGoiThem+=5;
            if (message == null) {
                message = this.cbDuaKho.getText().toString();
            } else {
                message += ", " + this.cbDuaKho.getText().toString();
            }
        }
        if (this.cbChuoiKho.isChecked()) {
            //      priceGoiThem+=5;
            if (message == null) {
                message = this.cbChuoiKho.getText().toString();
            } else {
                message += ", " + this.cbChuoiKho.getText().toString();
            }
        }
        if (this.cbNhoKho.isChecked()) {
            //      priceGoiThem+=5;
            if (message == null) {
                message = this.cbNhoKho.getText().toString();
            } else {
                message += ", " + this.cbNhoKho.getText().toString();
            }
        }

        message = message == null ? "" : "" + message;
        // Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        return message;
    }

    private float totalPrice = mount * price;

    private void setCheckedChangeCheckbox() {
        cbTranChau.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    priceGoiThem += 5000;
                    totalPrice = totalPrice + (5000 * mount);
                    btnTotalMoney.setText("+ " + totalPrice + " đ");
                } else {
                    priceGoiThem -= 5000;
                    totalPrice = totalPrice - (5000 * mount);
                    btnTotalMoney.setText("+ " + totalPrice + " đ");
                }
            }
        });
        cbThachRauCau.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    priceGoiThem += 5000;
                    totalPrice = totalPrice + (5000 * mount);
                    btnTotalMoney.setText("+ " + totalPrice + " đ");
                } else {
                    priceGoiThem -= 5000;
                    totalPrice = totalPrice - (5000 * mount);
                    btnTotalMoney.setText("+ " + totalPrice + " đ");
                }
            }
        });
        cbNhoKho.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    priceGoiThem += 5000;
                    totalPrice = totalPrice + (5000 * mount);
                    btnTotalMoney.setText("+ " + totalPrice + " đ");
                } else {
                    priceGoiThem -= 5000;
                    totalPrice = totalPrice - (5000 * mount);
                    btnTotalMoney.setText("+ " + totalPrice + " đ");
                }
            }
        });
        cbDuaKho.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    priceGoiThem += 5000;
                    totalPrice = totalPrice + (5000 * mount);
                    btnTotalMoney.setText("+ " + totalPrice + " đ");
                } else {
                    priceGoiThem -= 5000;
                    totalPrice = totalPrice - (5000 * mount);
                    btnTotalMoney.setText("+ " + totalPrice + " đ");
                }
            }
        });
        cbChuoiKho.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    priceGoiThem += 5000;
                    totalPrice = totalPrice + (5000 * mount);
                    btnTotalMoney.setText("+ " + totalPrice + " đ");
                } else {
                    priceGoiThem -= 5000;
                    totalPrice = totalPrice - (5000 * mount);
                    btnTotalMoney.setText("+ " + totalPrice + " đ");
                }
            }
        });
        Log.d("PriceGoiThem1", "" + priceGoiThem);
    }

    private void addEvents() {

        imgLove.setOnClickListener(this);
        imgMinus.setOnClickListener(this);
        imgPlus.setOnClickListener(this);
        btnTotalMoney.setOnClickListener(this);
    }

    private void checkAdmin() {
        Thread thread6 = new Thread() {
            @Override
            public void run() {
                super.run();
                databaseReference.child("ListCustomer").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Customer customer = dataSnapshot.getValue(Customer.class);
                        if (customer.getPermission().equals("admin")) {
                            linearDatHang.setVisibility(View.INVISIBLE);
//                    txtName.setText(mb.getName());
//                    Picasso.with(TrangChuActivity.this).load(mb.getPhotoURL()).into(imgAdmin);
                            //    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                            //   drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        };
        thread6.start();

    }

    private void addControls() {
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.ActivityDrinkDetail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Chi tiết đồ uống");
        linearDatHang = (LinearLayout) findViewById(R.id.ACtivityDrinkDetail_frameDatHang);
        imgDrink = (ImageView) findViewById(R.id.ACtivityDrinkDetail_imgDrink);
        imgLove = (ImageView) findViewById(R.id.ACtivityDrinkDetail_imgLove);
        imgPlus = (ImageView) findViewById(R.id.ACtivityDrinkDetail_imgPlus);
        imgMinus = (ImageView) findViewById(R.id.ACtivityDrinkDetail_imgMinus);
        txtNameDrink = (TextView) findViewById(R.id.ACtivityDrinkDetail_txtNameDrink);
        txtPriceDrink = (TextView) findViewById(R.id.ACtivityDrinkDetail_txtPriceDrink);
        txtMountDrink = (TextView) findViewById(R.id.ACtivityDrinkDetail_txtMountDrink);
        btnTotalMoney = (Button) findViewById(R.id.ACtivityDrinkDetail_btnTotalMoney);
        cbChuoiKho = (CheckBox) findViewById(R.id.ACtivityDrinkDetail_checkBoxChuoiKho);
        cbTranChau = (CheckBox) findViewById(R.id.ACtivityDrinkDetail_checkBoxTranChau);
        cbThachRauCau = (CheckBox) findViewById(R.id.ACtivityDrinkDetail_checkBoxThachRauCau);
        cbDuaKho = (CheckBox) findViewById(R.id.ACtivityDrinkDetail_checkBoxDuaKho);
        cbNhoKho = (CheckBox) findViewById(R.id.ACtivityDrinkDetail_checkBoxNhoKho);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ACtivityDrinkDetail_imgLove:
                addLoveDrink();
                break;
            case R.id.ACtivityDrinkDetail_imgPlus:
                plusMountDrink();
                break;
            case R.id.ACtivityDrinkDetail_imgMinus:
                minusMountDrink();
                break;
            case R.id.ACtivityDrinkDetail_btnTotalMoney:
                DialogAddToCart();
                //     showResultCheckBox();
                break;
        }
    }

    //    private void getPriceOfDrink()
//    {
//        databaseReference.child("ListDrink").child(intent.getStringExtra("IdDrink").toString()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Drink drink = dataSnapshot.getValue(Drink.class);
//                price = drink.getPriceDrink();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
    static int check = 2;
    static int mountBonus;
    static float priceBonus;
    static String idOrderDetail;

    //    private void checkStatusOrder()
//    {
//        databaseReference.child("ListOrder").child(firebaseUser.getUid().toString())
//    }
    private void addDrinkToCart() {
//        if(databaseReference.child("ListOrderDetail").child(firebaseUser.getUid().toString()).getRef()== null);
//        {
//            check =2;
//        }
        databaseReference.child("ListOrderDetail").child(firebaseUser.getUid().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //    mountBonus = mount;
                mountBonus = 0;
                priceBonus=0;
                if(dataSnapshot.exists())
                {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if (dataSnapshot1.exists()) {
                        OrderDetail orderDetail = dataSnapshot1.getValue(OrderDetail.class);
                        if (orderDetail.getIdDrink().toString().
                                equals(intent.getStringExtra("IdDrink").toString()) &&
                                orderDetail.getBonus().toString().equals(showResultCheckBox())) {
                            mountBonus = mount + orderDetail.getMount();
                            priceBonus = totalPrice + orderDetail.getPrice();
                            idOrderDetail = orderDetail.getIdOrderDetail().toString();
                            Log.w("mountBonus1", "" + mountBonus);
                            check = 1;
                            break;

                            // break;
                        } else {
                            check = 2;
                            // break;
                        }
                    }
                }
//                    else
//                    {
//                        check=2;
//                    }

                }
//                else
//                {
//                    check = 2;
//                }

              Log.w("check11 : ", "" + check);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.w("idOrderDetail2 : ", "" + idOrderDetail);
        Log.w("mountBonus2 : ", "" + mountBonus);
        Log.w("check2 : ", "" + check);
        if (check == 1) {
            databaseReference.child("ListOrderDetail").child(firebaseUser.getUid().toString()).
                    child(idOrderDetail).child("mount").setValue(mountBonus);
            databaseReference.child("ListOrderDetail").child(firebaseUser.getUid().toString()).
                    child(idOrderDetail).child("mount").setValue(priceBonus);
            Log.w("check3 : ", "" + check);
            // check = 0;
        }
        else if (check == 2) {
            Log.w("check4 : ", "" + check);
            Log.d("PriceGoiThem2", "" + priceGoiThem);
            key_push_listorderdetail = databaseReference.child("ListOrderDetail").push().getKey();
            OrderDetail orderDetail = new OrderDetail(key_push_listorderdetail, "",
                    intent.getStringExtra("IdDrink").toString(), imgDrink2,
                    nameDrink, mount, totalPrice, showResultCheckBox(), priceGoiThem);
            databaseReference.child("ListOrderDetail").child(firebaseUser.getUid().toString()).
                    child(key_push_listorderdetail).setValue(orderDetail, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError == null) {
                        //  DialogAddToCart();

                        Toast.makeText(DrinkDetail.this, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                        //    Toast.makeText(DrinkDetail.this, "Bạn đã thêm đồ uống thành công", Toast.LENGTH_SHORT).show();
                        // dialog.dismiss();
                    } else {
                        Toast.makeText(DrinkDetail.this, "Bạn đã thêm đồ uống thất bại", Toast.LENGTH_SHORT).show();
                        //  dialog.dismiss();
                        // Toast.makeText(getActivity(), "Đăng tin thất bại !", Toast.LENGTH_SHORT).show();
                    }
                }
            });
          //  check = 0;
            //    Log.w("check: ", ": " + check);
            //  }
            //  Log.w("check0001 : ", "" + check);
        }
        Log.d("check tottal : " , "" + check);
    }
//    private void addDrinkToCart2() {
////        if(databaseReference.child("ListOrderDetail").child(firebaseUser.getUid().toString()).getRef()== null);
////        {
////            check =2;
////        }
//        databaseReference.child("ListOrderDetail").child(firebaseUser.getUid().toString()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                //    mountBonus = mount;
//                mountBonus = 0;
//                check=2;
//                if(dataSnapshot.exists())
//                {
//                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                            OrderDetail orderDetail = dataSnapshot1.getValue(OrderDetail.class);
//                            if (orderDetail.getIdDrink().toString().
//                                    equals(intent.getStringExtra("IdDrink").toString()) &&
//                                     orderDetail.getBonus().toString().equals(showResultCheckBox())) {
//                                    Log.w("mountBonus1", "" + mountBonus);
//                                    mountBonus = mount + orderDetail.getMount();
//                                   idOrderDetail = orderDetail.getIdOrderDetail().toString();
//                                    break;
//                            }
//                        check=1;
//
//                    }
//
////                    else
////                    {
////                        check=2;
////                    }
//
//                }
////                else
////                {
////                    check = 2;
////                }
//
//                Log.w("check11 : ", "" + check);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//        Log.w("idOrderDetail2 : ", "" + idOrderDetail);
//        Log.w("mountBonus2 : ", "" + mountBonus);
//        Log.w("check2 : ", "" + check);
//        if (check == 1) {
//            databaseReference.child("ListOrderDetail").child(firebaseUser.getUid().toString()).
//                    child(idOrderDetail).child("mount").setValue(mountBonus);
//            Log.w("check3 : ", "" + check);
//            // check = 0;
//        }
//        else if (check == 2) {
//            Log.w("check4 : ", "" + check);
//            Log.d("PriceGoiThem2", "" + priceGoiThem);
//            key_push_listorderdetail = databaseReference.child("ListOrderDetail").push().getKey();
//            OrderDetail orderDetail = new OrderDetail(key_push_listorderdetail, "",
//                    intent.getStringExtra("IdDrink").toString(), imgDrink2,
//                    nameDrink, mount, totalPrice, showResultCheckBox(), priceGoiThem);
//            databaseReference.child("ListOrderDetail").child(firebaseUser.getUid().toString()).
//                    child(key_push_listorderdetail).setValue(orderDetail, new DatabaseReference.CompletionListener() {
//                @Override
//                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                    if (databaseError == null) {
//                        //  DialogAddToCart();
//
//                        Toast.makeText(DrinkDetail.this, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
//                        //    Toast.makeText(DrinkDetail.this, "Bạn đã thêm đồ uống thành công", Toast.LENGTH_SHORT).show();
//                        // dialog.dismiss();
//                    } else {
//                        Toast.makeText(DrinkDetail.this, "Bạn đã thêm đồ uống thất bại", Toast.LENGTH_SHORT).show();
//                        //  dialog.dismiss();
//                        // Toast.makeText(getActivity(), "Đăng tin thất bại !", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//            //  check = 0;
//            //    Log.w("check: ", ": " + check);
//            //  }
//            //  Log.w("check0001 : ", "" + check);
//        }
//        Log.d("check tottal : " , "" + check);
//    }
//    private int getCheck()
//    {
//        databaseReference.child("ListOrderDetail").child(firebaseUser.getUid().toString()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                //    mountBonus = mount;
//                mountBonus = 0;
//                check=0;
//                if(dataSnapshot.exists())
//                {
//                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                            OrderDetail orderDetail = dataSnapshot1.getValue(OrderDetail.class);
//                            if (orderDetail.getIdDrink().toString().
//                                    equals(intent.getStringExtra("IdDrink").toString()) &&
//                                    orderDetail.getBonus().toString().equals(showResultCheckBox())) {
//                                mountBonus = mount + orderDetail.getMount();
//                                idOrderDetail = orderDetail.getIdOrderDetail().toString();
//                                Log.w("mountBonus1", "" + mountBonus);
//                                check = 1;
//
//                                // break;
//                            } else {
//                                check = 2;
//                                // break;
//                            }
//                    }
////                    else
////                    {
////                        check=2;
////                    }
//
//                }
////                else
////                {
////                    check = 2;
////                }
//
//                Log.w("check11 : ", "" + check);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//        Log.w("idOrderDetail2 : ", "" + idOrderDetail);
//        Log.w("mountBonus2 : ", "" + mountBonus);
//        Log.w("check2 : ", "" + check);
//        return check;
//    }
//    private void addDrinkToCart2() {
//        int checkkk = getCheck();
//        if (checkkk == 1) {
//            databaseReference.child("ListOrderDetail").child(firebaseUser.getUid().toString()).
//                    child(idOrderDetail).child("mount").setValue(mountBonus);
//            Log.w("check3 : ", "" + check);
//            // check = 0;
//        }
//        else if (checkkk == 2) {
//            Log.w("check4 : ", "" + check);
//            Log.d("PriceGoiThem2", "" + priceGoiThem);
//            key_push_listorderdetail = databaseReference.child("ListOrderDetail").push().getKey();
//            OrderDetail orderDetail = new OrderDetail(key_push_listorderdetail, "",
//                    intent.getStringExtra("IdDrink").toString(), imgDrink2,
//                    nameDrink, mount, totalPrice, showResultCheckBox(), priceGoiThem);
//            databaseReference.child("ListOrderDetail").child(firebaseUser.getUid().toString()).
//                    child(key_push_listorderdetail).setValue(orderDetail, new DatabaseReference.CompletionListener() {
//                @Override
//                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                    if (databaseError == null) {
//                        //  DialogAddToCart();
//
//                        Toast.makeText(DrinkDetail.this, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
//                        //    Toast.makeText(DrinkDetail.this, "Bạn đã thêm đồ uống thành công", Toast.LENGTH_SHORT).show();
//                        // dialog.dismiss();
//                    } else {
//                        Toast.makeText(DrinkDetail.this, "Bạn đã thêm đồ uống thất bại", Toast.LENGTH_SHORT).show();
//                        //  dialog.dismiss();
//                        // Toast.makeText(getActivity(), "Đăng tin thất bại !", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//            //  check = 0;
//            //    Log.w("check: ", ": " + check);
//            //  }
//            //  Log.w("check0001 : ", "" + check);
//        }
//        Log.d("check tottal : " , "" + check);
//    }
    public void DialogAddToCart() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Thông báo");
        alert.setIcon(R.drawable.ic_baseline_info_24);
        alert.setMessage("Bạn có chắc muốn thêm sản phẩm này vào giỏ hàng ? ");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addDrinkToCart();
                // Intent intent = new Intent(DrinkDetail.this,MenuDrinkDetail.class);
                // startActivity(intent);
                finish();
            }
        });
        alert.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog a = alert.create();
        a.show();
    }

    private void minusMountDrink() {
        if (mount > 1) {
            mount--;
        }
        txtMountDrink.setText("" + mount);
        totalPrice = (mount * price) + (priceGoiThem * mount);
        btnTotalMoney.setText("+ " + totalPrice + " đ");
    }

    private void plusMountDrink() {
        mount++;
        txtMountDrink.setText("" + mount);
        totalPrice = (mount * price) + (priceGoiThem * mount);
        btnTotalMoney.setText("+ " + totalPrice + " đ");
    }

    private void initImageLove() {

        databaseReference.child("ListCustomer").child(firebaseUser.getUid().toString()).
                child("ListLoveDrink").child(intent.getStringExtra("IdDrink").toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    if (Integer.parseInt(dataSnapshot.child("statusLove").getValue().toString()) == 1) {
                        imgLove.setImageResource(R.drawable.ic_baseline_favorite_24);
                        checkDrinkLove = true;
                    }
                } catch (NullPointerException ex) {
                    Log.w("tezzz123", " : null" + ex);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    boolean checkDrinkLove = false;

    private void addLoveDrink() {
        if (checkDrinkLove == false) {
            imgLove.setImageResource(R.drawable.ic_baseline_favorite_24);
            checkDrinkLove = true;
//            SharedPreferences.Editor edit = shap.edit();
//            edit.putString("idDrink",intent.getStringExtra("IdDrink").toString());
//            //gán user vào bộ lưu trữ shap đã khai báo ở trên
//            edit.putBoolean("checkLoveDrink",true);
            //gán pass vào bộ lưu trữ shap đã khai báo ở trên
            //   edit.commit();
            databaseReference.child("ListCustomer").child(firebaseUser.getUid().toString()).
                    child("ListLoveDrink").child(intent.getStringExtra("IdDrink").toString()).
                    child("IdDrink").setValue(intent.getStringExtra("IdDrink").toString());

            databaseReference.child("ListCustomer").child(firebaseUser.getUid().toString()).
                    child("ListLoveDrink").child(intent.getStringExtra("IdDrink").toString()).child("statusLove").setValue(1);
            Toast.makeText(this, "Bạn đã chọn yêu thích ảnh này", Toast.LENGTH_SHORT).show();
        } else {
            imgLove.setImageResource(R.drawable.ic_baseline_favorite_border_24_fragmentorder);
            checkDrinkLove = false;
//            SharedPreferences.Editor edit = shap.edit();
//            edit.remove("idDrink");
//            edit.remove("checkLoveDrink");
//            edit.commit();
//            databaseReference.child("ListCustomer").child(firebaseUser.getUid().toString()).
//                    child("ListLoveDrink").child(intent.getStringExtra("IdDrink").toString()).
//                    child("IdDrink").setValue(intent.getStringExtra("IdDrink").toString());
            databaseReference.child("ListCustomer").child(firebaseUser.getUid().toString()).
                    child("ListLoveDrink").child(intent.getStringExtra("IdDrink").toString()).child("statusLove").setValue(0);
            Toast.makeText(this, "Bạn không thích ảnh này", Toast.LENGTH_SHORT).show();
        }
    }
//    public void getDataSharedPreferences()
//    {
//        if(shap.getBoolean("checkLoveDrink",false) == true)
//        {
//            imgLove.setImageResource(R.drawable.ic_baseline_favorite_24);
//            checkDrinkLove = true;
//        }
//        //edtEmail.setText(shap.getString("key_email",""));
//    }
}