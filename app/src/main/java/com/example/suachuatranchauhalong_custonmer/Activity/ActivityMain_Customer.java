package com.example.suachuatranchauhalong_custonmer.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.suachuatranchauhalong_custonmer.Adapter.DrinkAdapter;
import com.example.suachuatranchauhalong_custonmer.Fagment.Fragment_Account;
import com.example.suachuatranchauhalong_custonmer.Fagment.Fragment_LoveDrink;
import com.example.suachuatranchauhalong_custonmer.Fagment.Fragment_News;
import com.example.suachuatranchauhalong_custonmer.Fagment.Fragment_Order;
import com.example.suachuatranchauhalong_custonmer.Fagment.Fragment_Point;
import com.example.suachuatranchauhalong_custonmer.Object.Customer;
import com.example.suachuatranchauhalong_custonmer.Object.Drink;
import com.example.suachuatranchauhalong_custonmer.Object.ListenerCheckAdmin;
import com.example.suachuatranchauhalong_custonmer.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ActivityMain_Customer extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FrameLayout framMainContent;
    private TextView txtTittleSuaChua;
    private RecyclerView recyclerSuaChua;
    private ArrayList<Drink> listDrink;
    private DrinkAdapter drinkAdapter;
    private DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    Toolbar toolbar;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__customer);
        initReferencesObject();
        initData();
        addControls();
       // initDataFormAdmin();
        //   initListDrink();
        addEventsFormAdmin();
        addEvents();
        checkAdmin();
    }
    private void initReferencesObject() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
     //   intent = getIntent();
        //    listenerIdDrink = new ListenerIdDrink();
        // Toast.makeText(this, "" + intent.getStringExtra("IDMenuDrink"), Toast.LENGTH_SHORT).show();
    }
    private void initDataFormAdmin() {
                drawerLayout.addDrawerListener(actionBarDrawerToggle);
                actionBarDrawerToggle.syncState();
             //   getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);



    }
    ListenerCheckAdmin listenerCheckAdmin;
    private void checkAdmin() {
         listenerCheckAdmin = new ListenerCheckAdmin();
                databaseReference.child("ListCustomer").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Customer customer = dataSnapshot.getValue(Customer.class);
                        if(customer.getPermission().equals("admin"))
                        {
//                    txtName.setText(mb.getName());
//                    Picasso.with(TrangChuActivity.this).load(mb.getPhotoURL()).into(imgAdmin);
                            listenerCheckAdmin.setCheck(1);
                            navigationView.setVisibility(View.INVISIBLE);
                            drawerLayout.addDrawerListener(actionBarDrawerToggle);
                            actionBarDrawerToggle = new ActionBarDrawerToggle(ActivityMain_Customer.this,drawerLayout,R.string.open,R.string.close);
                            actionBarDrawerToggle.syncState();
                            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        }
                        else
                        {
                            listenerCheckAdmin.setCheck(0);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }
    public void initListDrink()
    {
                String dateCreateDrink;
                Calendar calen = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy");
                dateCreateDrink = "" + simpleDateFormat.format(calen.getTime());
                listDrink = new ArrayList<Drink>();
//        listDrink.add(new Drink(1,"Sữa chua xoài",R.drawable.suachuaxoai,1,20000,"Sữa chua",dateCreateDrink));
//        listDrink.add(new Drink(2,"Sữa chua xoài",R.drawable.suachuaxoai,1,20000,"Sữa chua",dateCreateDrink));
//        listDrink.add(new Drink(3,"Sữa chua xoài",R.drawable.suachuaxoai,1,20000,"Sữa chua",dateCreateDrink));
//        listDrink.add(new Drink(4,"Sữa chua xoài",R.drawable.suachuaxoai,1,20000,"Sữa chua",dateCreateDrink));
//        listDrink.add(new Drink(5,"Sữa chua xoài",R.drawable.suachuaxoai,1,20000,"Sữa chua",dateCreateDrink));
//        listDrink.add(new Drink(6,"Sữa chua xoài",R.drawable.suachuaxoai,1,20000,"Sữa chua",dateCreateDrink));
//        listDrink.add(new Drink(7,"Sữa chua xoài",R.drawable.suachuaxoai,1,20000,"Sữa chua",dateCreateDrink));
//        listDrink.add(new Drink(8,"Sữa chua xoài",R.drawable.suachuaxoai,1,20000,"Sữa chua",dateCreateDrink));
//        listDrink.add(new Drink(9,"Sữa chua xoài",R.drawable.suachuaxoai,1,20000,"Sữa chua",dateCreateDrink));
                recyclerSuaChua.setHasFixedSize(true);
                recyclerSuaChua.setLayoutManager(new GridLayoutManager(ActivityMain_Customer.this,2));
//        drinkAdapter = new DrinkAdapter(listDrink);
                recyclerSuaChua.setAdapter(drinkAdapter);

    }
    public void initData()
    {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.ActivityMainCustomer_frameMainContent,new Fragment_News(),"frgNews")
                        .addToBackStack(null)
                        .commit();
        // MainStartService();
//        getSupportActionBar().setTitle("Thông tin");


    }
    public void addControls()
    {
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.ActivityMainCustomer_toolbar);
        setSupportActionBar(toolbar);
        ///getSupportActionBar().setDisplayHomeAsUpEnabled(true);
     //   getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        drawerLayout = (DrawerLayout) findViewById(R.id.ActivityMainCustomer_drawerLayout);
      //  drawerLayout.setVisibility(View.GONE);
        navigationView = (NavigationView) findViewById(R.id.id_NaviView);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.ActivityMainCustomer_bottomNaviViewMenu);
        framMainContent = (FrameLayout) findViewById(R.id.ActivityMainCustomer_frameMainContent);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    int check;
    private void addEventsFormAdmin() {
                navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        //menuItem.setCheckable(true);
                        drawerLayout.closeDrawers();
                        Intent intent = null;
                        //  Toast.makeText(TrangChuActivity.this, "" + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        final int menuItemID = menuItem.getItemId();
                        switch (menuItemID)
                        {
                            case R.id.NavigationView_ChooseinformationCustomer :
                                check = 1;
                                break;
                            case R.id.NavigationView_ChooseinformationShipper:
                                check = 2;
                                break;
                            case R.id.NavigationView_ListVoucher:
                                check = 3;
                                break;
                            case R.id.NavigationView_ChooseOrderWWaitConfirm :
                                check = 4;
                                break;
                            case R.id.NavigationView_ChooseOrderConfirmed:
                                check =5;
                                break;
                            case R.id.NavigationView_ChooseOrderStatistical:
                                check = 7;
                                break;
                            case R.id.NavigationView_ChooseLogout:
                                check = 8;
                                break;
                        }
                        if(check == 1 || check==2)
                        {
                            intent= new Intent(ActivityMain_Customer.this,ActivityListCustomerOrShipper.class);
                            intent.putExtra("key_itemMenu",check);
                            startActivity(intent);
                            // overridePendingTransition(R.anim.anim_intent_trai_sang_phai,R.anim.anim_intent_exit);
                        }
                        if(check == 3)
                        {
                            intent= new Intent(ActivityMain_Customer.this,ActivityListVoucherForAdmin.class);
                            //    intent.putExtra("key_itemMenu",check);
                            startActivity(intent);
                            // overridePendingTransition(R.anim.anim_intent_trai_sang_phai,R.anim.anim_intent_exit);
                        }
                        if(check == 4)
                        {
                            intent= new Intent(ActivityMain_Customer.this,OrderChoXacNhan.class);
                        //    intent.putExtra("key_itemMenu",check);
                            startActivity(intent);
                            // overridePendingTransition(R.anim.anim_intent_trai_sang_phai,R.anim.anim_intent_exit);
                        }
                        if(check == 5)
                        {
                            intent= new Intent(ActivityMain_Customer.this,OrderDaXacNhan.class);
                            //    intent.putExtra("key_itemMenu",check);
                            startActivity(intent);
                            // overridePendingTransition(R.anim.anim_intent_trai_sang_phai,R.anim.anim_intent_exit);
                        }
//                        if(check == 6)
//                        {
//                            intent= new Intent(ActivityMain_Customer.this, ActivityListVoucherConfirm.class);
//                            //    intent.putExtra("key_itemMenu",check);
//                            startActivity(intent);
//                            // overridePendingTransition(R.anim.anim_intent_trai_sang_phai,R.anim.anim_intent_exit);
//                        }
                        if(check == 7)
                        {
                            intent= new Intent(ActivityMain_Customer.this,ActivityThongKeDonHang.class);
                            //    intent.putExtra("key_itemMenu",check);
                            startActivity(intent);
                            // overridePendingTransition(R.anim.anim_intent_trai_sang_phai,R.anim.anim_intent_exit);
                        }
                        if(check == 8)
                        {
                         DialogLogout();
                        }
                        return true;
                    }
                });

    }
    public void DialogLogout()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(ActivityMain_Customer.this);
        alert.setTitle("Thông báo");
        alert.setIcon(R.drawable.ic_baseline_info_24);
        alert.setMessage("Bạn có chắc muốn đăng xuất ? ");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(ActivityMain_Customer.this, Login_Customer.class);
                startActivity(intent);
            }
        });
        alert.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog a = alert.create();
        a.show();
    }

    @Override
public boolean onOptionsItemSelected(MenuItem item) {
    if(actionBarDrawerToggle.onOptionsItemSelected(item))
    {
        return true;
    }
    return super.onOptionsItemSelected(item);
}
    public void addEvents()
    {
//        toolbar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                drawerLayout.openDrawer(GravityCompat.START);
//            }
//        });
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
    }
    public BottomNavigationView.OnNavigationItemSelectedListener navListener;
    {
                navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        Fragment selectedFragment = null;
                        switch (menuItem.getItemId()) {
                            case R.id.bottomNavigation_News:
                                //  getSupportActionBar().setTitle("Tin tức");
                                selectedFragment = new Fragment_News();
                                loadFragment(selectedFragment, "frgNews");
                                return true;
                            case R.id.bottomNavigation_Point:
                                //  getSupportActionBar().setTitle("Điểm thưởng");
                                selectedFragment = new Fragment_Point();
                                loadFragment(selectedFragment, "frgPoint");
                                return true;
                            case R.id.bottomNavigation_Order:
                                // getSupportActionBar().setTitle("Menu");
                                selectedFragment = new Fragment_Order();
                                loadFragment(selectedFragment, "frgOrder");
                                return true;
                            case R.id.bottomNavigation_LoveDrink:
                                //getSupportActionBar().setTitle("Yêu thích");
                                selectedFragment = new Fragment_LoveDrink();
                                loadFragment(selectedFragment, "frgLoveDrink");
                                return true;
                            case R.id.bottomNavigation_Account :
                                //getSupportActionBar().setTitle("Tài khoản");
                                selectedFragment = new Fragment_Account();
                                loadFragment(selectedFragment, "frgAccount");
                                return true;
                        }
                        return true;
                    }
                };

    }
    private void loadFragment(final Fragment a,final String nameFragment) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.ActivityMainCustomer_frameMainContent,a,nameFragment)
                        .addToBackStack(null)
                        .commit();
    }
}