package com.example.suachuatranchauhalong_custonmer.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.suachuatranchauhalong_custonmer.Adapter.DrinkAdapter;
import com.example.suachuatranchauhalong_custonmer.Fagment.Fragment_Account;
import com.example.suachuatranchauhalong_custonmer.Fagment.Fragment_LoveDrink;
import com.example.suachuatranchauhalong_custonmer.Fagment.Fragment_News;
import com.example.suachuatranchauhalong_custonmer.Fagment.Fragment_Order;
import com.example.suachuatranchauhalong_custonmer.Fagment.Fragment_Point;
import com.example.suachuatranchauhalong_custonmer.Object.Drink;
import com.example.suachuatranchauhalong_custonmer.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__customer);
        initData();
        addControls();
        initDataFormAdmin();
        //   initListDrink();
        addEvents();
    }
    private void initDataFormAdmin() {
       // drawerLayout = (DrawerLayout) findViewById(R.id.ActivityMainCustomer_drawerLayout);
       // actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
       // navigationView = (NavigationView) findViewById(R.id.id_NaviView);
       drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
       // setSupportActionBar(toolbar);
       // toolbar.setNavigationIcon(R.drawable.ic_baseline_dehaze_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(false);
       // drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        //checkAdmin();

    }
    public void initListDrink()
    {
        String dateCreateDrink;
        Calendar calen = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy");
        dateCreateDrink = "" + simpleDateFormat.format(calen.getTime());
        listDrink = new ArrayList<Drink>();
        listDrink.add(new Drink(1,"Sữa chua xoài",R.drawable.suachuaxoai,1,20000,"Sữa chua",dateCreateDrink));
        listDrink.add(new Drink(2,"Sữa chua xoài",R.drawable.suachuaxoai,1,20000,"Sữa chua",dateCreateDrink));
        listDrink.add(new Drink(3,"Sữa chua xoài",R.drawable.suachuaxoai,1,20000,"Sữa chua",dateCreateDrink));
        listDrink.add(new Drink(4,"Sữa chua xoài",R.drawable.suachuaxoai,1,20000,"Sữa chua",dateCreateDrink));
        listDrink.add(new Drink(5,"Sữa chua xoài",R.drawable.suachuaxoai,1,20000,"Sữa chua",dateCreateDrink));
        listDrink.add(new Drink(6,"Sữa chua xoài",R.drawable.suachuaxoai,1,20000,"Sữa chua",dateCreateDrink));
        listDrink.add(new Drink(7,"Sữa chua xoài",R.drawable.suachuaxoai,1,20000,"Sữa chua",dateCreateDrink));
        listDrink.add(new Drink(8,"Sữa chua xoài",R.drawable.suachuaxoai,1,20000,"Sữa chua",dateCreateDrink));
        listDrink.add(new Drink(9,"Sữa chua xoài",R.drawable.suachuaxoai,1,20000,"Sữa chua",dateCreateDrink));
        recyclerSuaChua.setHasFixedSize(true);
        recyclerSuaChua.setLayoutManager(new GridLayoutManager(this,2));
        drinkAdapter = new DrinkAdapter(listDrink);
        recyclerSuaChua.setAdapter(drinkAdapter);
    }
    public void initData()
    {
        // MainStartService();
//        getSupportActionBar().setTitle("Thông tin");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.ActivityMainCustomer_frameMainContent,new Fragment_News(),"frgNews")
                .addToBackStack(null)
                .commit();

    }
    public void addControls()
    {
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.ActivityMainCustomer_toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.ActivityMainCustomer_drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        navigationView = (NavigationView) findViewById(R.id.id_NaviView);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.ActivityMainCustomer_bottomNaviViewMenu);
        framMainContent = (FrameLayout) findViewById(R.id.ActivityMainCustomer_frameMainContent);
    }
//    private void addEventsFormAdmin() {
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                //menuItem.setCheckable(true);
//                drawerLayout.closeDrawers();
//                Intent intent = null;
//                //  Toast.makeText(TrangChuActivity.this, "" + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
//                final int menuItemID = menuItem.getItemId();
//                switch (menuItemID)
//                {
//                    case R.id.id_menu_baiVietChoDuyet :
//                        check = 1;
//                        break;
//                    case R.id.id_menu_baiVietKhongDuyet:
//                        check = 2;
//                        break;
//                    case R.id.id_menu_DSStartUp :
//                        check = 3;
//                        break;
//                    case R.id.id_menu_DSCompany:
//                        check = 4;
//                        break;
//                    case R.id.id_menu_DSPerson:
//                        check = 5;
//                        break;
//                }
//                if(check==1 || check==2)
//                {
//                    intent= new Intent(TrangChuActivity.this,BaiVietChoPheDuyetActivity.class);
//                    intent.putExtra("key_itemMenu",check);
//                    startActivity(intent);
//                    overridePendingTransition(R.anim.anim_intent_trai_sang_phai,R.anim.anim_intent_exit);
//                }
//                else
//                {
//                    intent= new Intent(TrangChuActivity.this,DanhSachUserFilter.class);
//                    intent.putExtra("key_itemMenu",check);
//                    startActivity(intent);
//                    overridePendingTransition(R.anim.anim_intent_trai_sang_phai,R.anim.anim_intent_exit);
//                }
//                return true;
//            }
//        });
//
//    }
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
    private void loadFragment(Fragment a, String nameFragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.ActivityMainCustomer_frameMainContent,a,nameFragment)
                .addToBackStack(null)
                .commit();
    }
}