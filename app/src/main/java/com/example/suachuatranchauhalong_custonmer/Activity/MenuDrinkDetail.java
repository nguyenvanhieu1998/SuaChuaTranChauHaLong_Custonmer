package com.example.suachuatranchauhalong_custonmer.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suachuatranchauhalong_custonmer.Adapter.DrinkAdapter;
import com.example.suachuatranchauhalong_custonmer.Adapter.FragmentUpdateDrinkOfCart_OrderDetailAdapter;
import com.example.suachuatranchauhalong_custonmer.Adapter.NewAdapter;
import com.example.suachuatranchauhalong_custonmer.FragmentDialog.FragmentDialogAddDrinkForAdmin;
import com.example.suachuatranchauhalong_custonmer.FragmentDialog.FragmentDialogAddNewsForAdmin;
import com.example.suachuatranchauhalong_custonmer.FragmentDialog.FragmentDialogUpdateDrinkForAdmin;
import com.example.suachuatranchauhalong_custonmer.FragmentDialog.FragmentDialogUpdateDrinkOfCart;
import com.example.suachuatranchauhalong_custonmer.FragmentDialog.FragmentDialogUpdateNewsForAdmin;
import com.example.suachuatranchauhalong_custonmer.InterfaceOnClick.UpdateAndHiddenDrink;
import com.example.suachuatranchauhalong_custonmer.Object.Customer;
import com.example.suachuatranchauhalong_custonmer.Object.Drink;
import com.example.suachuatranchauhalong_custonmer.Object.ListenerCheckAdmin;
import com.example.suachuatranchauhalong_custonmer.Object.ListenerIDMenuDrink;
import com.example.suachuatranchauhalong_custonmer.Object.ListenerIdDrink;
import com.example.suachuatranchauhalong_custonmer.Object.MenuDrink;
import com.example.suachuatranchauhalong_custonmer.Object.News;
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

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.LandingAnimator;

public class MenuDrinkDetail extends AppCompatActivity implements View.OnClickListener, UpdateAndHiddenDrink {
    EditText searchViewDrink;
    ImageView imgBackActivity,imgAddDrink;
    RecyclerView recyclerViewDrink;
    DrinkAdapter drinkAdapter;
    ArrayList<Drink> arrayListDrink;
    TextView txtTittle,txtMountOfOrder,txtPriceOfOrder;
    Button btnPay;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    Intent intent;
    Toolbar toolbar;
    ListenerIDMenuDrink listenerIDMenuDrink;
    LinearLayout linearCart;
    FrameLayout frameLine;
    Animation FadeIn, FadeOut, SlideUp, SlideDown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_drink_detail);
//        finish();
//        overridePendingTransition(0, 0);
//        startActivity(getIntent());
//        overridePendingTransition(0, 0);
        initReferencesObject();
        addControls();
        addEvents();
        setListDrink();
        getNameMenuDrink();
        checkAdmin();
        setDataCart();
        Search2();
             //  searchViewDrink.setFocusable(true);

        //setDataCart2();
    }
    boolean check = false;
    private void addEvents() {
       btnPay.setOnClickListener(this);
      // imgAddDrink.setOnClickListener(this);
        linearCart.setOnClickListener(this);
    }
    ListenerCheckAdmin listenerCheckAdmin;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(listenerCheckAdmin.getCheck()==1)
        {
            getMenuInflater().inflate(R.menu.menu_add, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_add:
               // addDrink();
                listenerIDMenuDrink = new ListenerIDMenuDrink();
                 listenerIDMenuDrink.setIdMenuDrink(intent.getStringExtra("IDMenuDrink"));
                 addDrink();
                 Toast.makeText(this, "Bạn đã chọn thêm 1 đồ uống", Toast.LENGTH_SHORT).show();
             //   Toast.makeText(this, "Add", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addControls() {
        SlideUp = AnimationUtils.loadAnimation(MenuDrinkDetail.this, R.anim.slide_up);
        FadeIn = AnimationUtils.loadAnimation(MenuDrinkDetail.this, R.anim.fade_in);
        FadeOut = AnimationUtils.loadAnimation(MenuDrinkDetail.this, R.anim.fade_out);
        SlideDown = AnimationUtils.loadAnimation(MenuDrinkDetail.this, R.anim.slide_down);
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.ActivityMenuDrink_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        searchViewDrink = (EditText) findViewById(R.id.ActivityMenuDrinkDetail_searchDrink);
        //SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
     //   searchViewDrink.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        //txtTittle = (TextView) findViewById(R.id.ActivityMenuDrinkDetail_txtTittle);
        txtMountOfOrder = (TextView) findViewById(R.id.ActivityMenuDrinkDetail_txtMountOfOrder);
        txtPriceOfOrder = (TextView) findViewById(R.id.ActivityMenuDrinkDetail_txtPriceOfOrder);
        recyclerViewDrink = (RecyclerView) findViewById(R.id.ActivityMenuDrinkDetail_recycleViewDrink);
        btnPay = (Button) findViewById(R.id.ActivityMenuDrinkDetail_btnPay);
        //imgAddDrink = (ImageView) findViewById(R.id.ActivityMenuDrinkDetail_imgAddDrink);
        linearCart = (LinearLayout) findViewById(R.id.ActivityMenuDrinkDetail_linearCart);
        frameLine = (FrameLayout) findViewById(R.id.ActivityMenuDrinkDetail_line);
    }

    private void Search2()
    {
        final ArrayList<Drink> drinkFillterArrayList = new ArrayList<>();
        final  DrinkAdapter drinkFillterAdapter = new DrinkAdapter(this,drinkFillterArrayList,this);
        final  DrinkAdapter drinkAdapter2 = new DrinkAdapter(this,arrayListDrink,this);
        recyclerViewDrink.setAdapter(drinkFillterAdapter);
        if(searchViewDrink.getText().toString().isEmpty())
        {
            // baiDangAdapter = new Custom_Dong_BaiDang_Adapter(R.layout.custom_dong_baidang,getActivity(),baiDangArrayList);
            recyclerViewDrink.setAdapter(drinkAdapter);
            drinkAdapter.notifyDataSetChanged();
        }
        searchViewDrink.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                drinkFillterArrayList.clear();
               // Toast.makeText(MenuDrinkDetail.this, "" + searchViewDrink.getText().toString().toLowerCase().trim(), Toast.LENGTH_SHORT).show();
             //   final String getStringSearch = searchViewDrink.getText().toString().toLowerCase().trim();
                if(!searchViewDrink.getText().toString().trim().isEmpty())
                {
                    recyclerViewDrink.setAdapter(drinkFillterAdapter);
                        for (Drink drink1 : drinkFull)
                        {

                                String s2 = searchViewDrink.getText().toString().toLowerCase().trim();
                                String ten = drink1.getNameDrink();
                                ten = ten.toLowerCase();
                                Log.e("Search", "chuoi1 :" + s2 + "chuoi2 :" + ten);
                                if(ten.contains(s2)){
                                    Log.e("Search", "chuoi3 :" + s2 + "chuoi4 :" + ten);
                                    drinkFillterArrayList.add(drink1);

                                                }
                }
                    drinkFillterAdapter.notifyDataSetChanged();
                  //  recyclerViewDrink.setAdapter(drinkFillterAdapter);
                }
                else
                {
                    recyclerViewDrink.setAdapter(drinkAdapter2);
                    drinkAdapter2.notifyDataSetChanged();
                }
            }
        });
    }

     ArrayList<Drink> drinkFillterArrayList2;
    static ArrayList<Drink> drinkFull = new ArrayList<>();
//    private void Search()
//    {
//        final ArrayList<Drink> drinkFillterArrayList = new ArrayList<>();
//        final  DrinkAdapter drinkFillterAdapter = new DrinkAdapter(this,drinkFillterArrayList,this);
//        final  DrinkAdapter drinkAdapter2 = new DrinkAdapter(this,arrayListDrink,this);
//        recyclerViewDrink.setAdapter(drinkFillterAdapter);
//        if(searchViewDrink.getText().toString().isEmpty())
//        {
//            // baiDangAdapter = new Custom_Dong_BaiDang_Adapter(R.layout.custom_dong_baidang,getActivity(),baiDangArrayList);
//            recyclerViewDrink.setAdapter(drinkAdapter);
//            drinkAdapter2.notifyDataSetChanged();
//        }
//        searchViewDrink.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                // Log.e("Search", "chuoi :" + s);
//                // Toast.makeText(getActivity(), "" + edtSearch.getText().toString(), Toast.LENGTH_SHORT).show();
//                // Search();
//               // drinkFillterAdapter = new DrinkAdapter(MenuDrinkDetail.this,drinkFillterArrayList,this);
//                drinkFillterArrayList2 = new ArrayList<>();
//                if(! searchViewDrink.getText().toString().trim().isEmpty())
//                {
//                    databaseReference.child("ListDrink").addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            drinkFillterArrayList.clear();
//                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
//                            {
//                               Drink drink = dataSnapshot1.getValue(Drink.class);
//                               if(drink.getIdMenuDrink().equals(intent.getStringExtra("idMenuDrink").toString()))
//                               {
//                                   drinkFillterArrayList2.add(drink);
//
//                                   for (Drink drink1 : drinkFillterArrayList2)
//                                   {
//                                       if(drink1.getStatus()==1)
//                                       {
//                                           final String getStringSearch = searchViewDrink.getText().toString().toLowerCase().trim();
//                                           String ten = drink1.getNameDrink();
//                                           ten = ten.toLowerCase();
//                                           Log.e("Search", "chuoi1 :" + getStringSearch + "chuoi2 :" + ten);
//                                           if(ten.contains(getStringSearch)){
//                                               Log.e("Search", "chuoi3 :" + getStringSearch + "chuoi4 :" + ten);
//                                               drinkFillterArrayList.add(drink1);
////                                           Log.e("size arraylist fillte ","" + drinkFillterArrayList.size());
////                                           Log.e(" arraylist  ","" +drinkFillterArrayList.get(0) + " - " + drinkFillterArrayList.get(1));
//                                           }
//
//                                       }
//                                   }
//
//                               }
//                                 //   baiDang bd = dataSnapshot2.getValue(baiDang.class);
//
//                            }
//
//                            drinkFillterAdapter.notifyDataSetChanged();
//                           // recyclerViewDrink.setAdapter(drinkFillterAdapter);
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
//                }
//                else
//                {
//                    recyclerViewDrink.setAdapter(drinkAdapter);
//                    drinkAdapter.notifyDataSetChanged();
//                }
//            }
//        });
//    }
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
        listenerIdDrink = new ListenerIdDrink();
        listenerCheckAdmin = new ListenerCheckAdmin();
       // Toast.makeText(this, "" + intent.getStringExtra("IDMenuDrink"), Toast.LENGTH_SHORT).show();
    }
     float totalPriceOrder=0;
     int totalMountOrder=0;
    private void setDataCart()
    {
        final ArrayList<Integer> arrayList = new ArrayList<>();

        databaseReference.child("ListOrderDetail").child(firebaseUser.getUid().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    totalMountOrder=0;
                    totalPriceOrder=0;

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                    {
                        OrderDetail orderDetail = dataSnapshot1.getValue(OrderDetail.class);
                        try {
                        if(orderDetail.getIdOrder().toString().equals(""))
                        {
                            totalMountOrder+=orderDetail.getMount();
                            totalPriceOrder+=orderDetail.getPrice();
                        }
                        }catch (NullPointerException ex){

                        }

                    }

                txtMountOfOrder.setText(""+totalMountOrder);
                txtPriceOfOrder.setText(""+totalPriceOrder);
                /*OrderDetail or = new OrderDetail();
                if(or.getIdOrder().toString().equals(""))
                {
                    totalMountOrder += or.getMount();
                    totalPriceOrder += or.getPrice();
                    Log.w("12345", ": " + totalMountOrder);
                }*/


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
//    private void setDataCart2()
//    {
//        final ArrayList<Integer> arrayList = new ArrayList<>();
//
//        databaseReference.child("ListOrderDetail").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                totalMountOrder=0;
//                totalPriceOrder=0;
//
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
//                {
//                    OrderDetail2 orderDetail2 = dataSnapshot1.getValue(OrderDetail2.class);
//                    try {
//                        if(orderDetail2.getIdOrder().toString().equals("")&&firebaseUser.getUid().toString().equals(orderDetail2.getIdCustomer().toString()))
//                        {
//                            totalMountOrder+=orderDetail2.getMount();
//                            totalPriceOrder+=orderDetail2.getPrice();
//                        }
//                    }catch (NullPointerException ex){
//
//                    }
//
//                }
//
//                txtMountOfOrder.setText(""+totalMountOrder);
//                txtPriceOfOrder.setText(""+totalPriceOrder);
//                /*OrderDetail or = new OrderDetail();
//                if(or.getIdOrder().toString().equals(""))
//                {
//                    totalMountOrder += or.getMount();
//                    totalPriceOrder += or.getPrice();
//                    Log.w("12345", ": " + totalMountOrder);
//                }*/
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//    }
    private void getNameMenuDrink()
    {
        databaseReference.child("ListMenuDrink").child(intent.getStringExtra("idMenuDrink").toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                     MenuDrink menuDrink = dataSnapshot.getValue(MenuDrink.class);
                   //  txtTittle.setText(menuDrink.getNameMenuDrink().toString());
                     toolbar.setTitle(menuDrink.getNameMenuDrink().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void checkAdmin() {
        databaseReference.child("ListCustomer").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Customer customer = dataSnapshot.getValue(Customer.class);
                if(customer.getPermission().equals("admin"))
                {
//                    imgAddDrink.setVisibility(View.GONE);
                    linearCart.setVisibility(View.GONE);
                    frameLine.setVisibility(View.GONE);
                    //btnUpdate.setVisibility(View.VISIBLE);
//                    txtName.setText(mb.getName());
//                    Picasso.with(TrangChuActivity.this).load(mb.getPhotoURL()).into(imgAdmin);
//                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                }
//                if(customer.getPermission().equals("customer"))
//                {
//                    linearCart.setVisibility(View.VISIBLE);
//                    frameLine.setVisibility(View.VISIBLE);
//                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void setListDrink() {
        arrayListDrink = new ArrayList<>();
        drinkAdapter = new DrinkAdapter(this,arrayListDrink,this);
        recyclerViewDrink.setAdapter(drinkAdapter);
        databaseReference.child("ListDrink").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayListDrink.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    Drink drink = dataSnapshot1.getValue(Drink.class);
                    if(drink.getIdMenuDrink().equals(intent.getStringExtra("IDMenuDrink")))
                    {
                        if(drink.getStatus()==1)
                        {
                            arrayListDrink.add(drink);
                            drinkFull = arrayListDrink;

                        }

                    }
                    //  name = news.getTittle();
                    //    Toast.makeText(getActivity() , ""+  name, Toast.LENGTH_SHORT).show();

                }
                Log.e("size arraylist 1",""+ arrayListDrink.size());
                Log.e("size arraylist 2",""+ drinkFull.size());
                drinkAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        recyclerViewDrink.setHasFixedSize(true);
        int spanCount = 2;//Số cột nếu thiết lập lưới đứng, số dòng nếu lưới ngang
        int orientation = GridLayoutManager.VERTICAL;//Lưới ngang
//int orientation = GridLayoutManager.HORIZONTAL;//Lưới đứng

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
// Gắn vào RecylerView
        recyclerViewDrink.setItemAnimator(new LandingAnimator());
        recyclerViewDrink.setLayoutManager(gridLayoutManager);
    }
    @Override
    public void onClick(View view) {
         switch (view.getId())
         {
             case R.id.ActivityMenuDrinkDetail_btnPay :
                 if(Float.parseFloat(txtPriceOfOrder.getText().toString())==0)
                 {
                     Toast.makeText(this, "Hãy thêm sản phẩm trước khi thanh toán", Toast.LENGTH_SHORT).show();
                 }
                 else
                 {
                     intent = new Intent(MenuDrinkDetail.this,ActivityCart.class);
                     intent.putExtra("Mount",txtMountOfOrder.getText().toString());
                     intent.putExtra("Price",txtPriceOfOrder.getText().toString());
                     //   intent.putExtra("Mount",Integer.parseInt(txtMountOfOrder.getText().toString()));
                     startActivity(intent);
                   //  finish();
                 }

                // Toast.makeText(this, "Bạn đã chọn thanh toán", Toast.LENGTH_SHORT).show();
                 break;
//             case R.id.ActivityMenuDrinkDetail_imgAddDrink :
//                 listenerIDMenuDrink = new ListenerIDMenuDrink();
//                 listenerIDMenuDrink.setIdMenuDrink(intent.getStringExtra("IDMenuDrink"));
//                 addDrink();
//                 Toast.makeText(this, "Bạn đã chọn thêm 1 đồ uống", Toast.LENGTH_SHORT).show();
//                 break;
             case R.id.ActivityMenuDrinkDetail_linearCart :
//                 FragmentDialogUpdateDrinkOfCart fragmentDialogUpdateDrinkOfCart = new FragmentDialogUpdateDrinkOfCart();
//                 fragmentDialogUpdateDrinkOfCart.show(getSupportFragmentManager(),"fragmentDialogUpdateDrinkOfCart");
                 break;
         }
    }

    ListenerIdDrink listenerIdDrink;
    private void addDrink() {
        FragmentDialogAddDrinkForAdmin fragmentDialogAddDrink = new FragmentDialogAddDrinkForAdmin();
        fragmentDialogAddDrink.show(getSupportFragmentManager(),"fragmentDialogAdđrink");
    }

    @Override
    public void update(String idDrink,String idMenuDrink) {
        listenerIdDrink.setIdDrink(idDrink);
        listenerIdDrink.setIdMenuDrink(idMenuDrink);
        FragmentDialogUpdateDrinkForAdmin fragmentDialogUpdaterink = new FragmentDialogUpdateDrinkForAdmin();
        fragmentDialogUpdaterink.show(getSupportFragmentManager(),"fragmentDialogUpdaterink");
       // Toast.makeText(this, "Bạn đã update " + idDrink, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hidden(final String idDrink,String idMenuDrink) {
        listenerIdDrink.setIdDrink(idDrink);
        listenerIdDrink.setIdMenuDrink(idMenuDrink);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        //Thiết lập tiêu đề cho Dialog
        dialog.setTitle("Xác nhận");
        //Thiết lập nội dung cho Dialog
        dialog.setMessage("Bạn có chắc muốn ẩn đồ uống này không ?");
        //Thiết lập icon cho Dialog
        dialog.setIcon(R.drawable.ic_baseline_info_24);
        //Thiết lập 1 button cho Dialog.setPositiveButton() để tạo 1 nút CÓ sự kiện còn setNegativeButton() ể tạo 1 nút không CÓ sự kiện
        dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            //Thực hiện khi button Có được Click
            public void onClick(DialogInterface dialog, int which) {
                databaseReference.child("ListDrink").child(idDrink).child("status").setValue(0);
                dialog.dismiss();
                Toast.makeText(getApplication(), "Đã ẩn đồ uống thành công", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               dialog.dismiss();
            }
        });
        //Show dialog
        dialog.show();
      //  Toast.makeText(this, "Bạn đã hidden " + idDrink, Toast.LENGTH_SHORT).show();
    }
}