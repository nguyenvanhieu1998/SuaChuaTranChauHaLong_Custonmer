package com.example.suachuatranchauhalong_custonmer.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suachuatranchauhalong_custonmer.Adapter.DrinkAdapter;
import com.example.suachuatranchauhalong_custonmer.Adapter.NewAdapter;
import com.example.suachuatranchauhalong_custonmer.FragmentDialog.FragmentDialogAddDrinkForAdmin;
import com.example.suachuatranchauhalong_custonmer.FragmentDialog.FragmentDialogUpdateDrinkForAdmin;
import com.example.suachuatranchauhalong_custonmer.FragmentDialog.FragmentDialogUpdateNewsForAdmin;
import com.example.suachuatranchauhalong_custonmer.InterfaceOnClick.UpdateAndHiddenDrink;
import com.example.suachuatranchauhalong_custonmer.Object.Drink;
import com.example.suachuatranchauhalong_custonmer.Object.ListenerIDMenuDrink;
import com.example.suachuatranchauhalong_custonmer.Object.ListenerIdDrink;
import com.example.suachuatranchauhalong_custonmer.Object.MenuDrink;
import com.example.suachuatranchauhalong_custonmer.Object.News;
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
    SearchView searchViewDrink;
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
    ListenerIDMenuDrink listenerIDMenuDrink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_drink_detail);
        initReferencesObject();
        addControls();
        addEvents();
        setListDrink();
    }

    private void addEvents() {
       btnPay.setOnClickListener(this);
       imgAddDrink.setOnClickListener(this);
    }

    private void addControls() {
        searchViewDrink = (SearchView) findViewById(R.id.ActivityMenuDrinkDetail_searchDrink);
        txtTittle = (TextView) findViewById(R.id.ActivityMenuDrinkDetail_txtTittle);
        txtMountOfOrder = (TextView) findViewById(R.id.ActivityMenuDrinkDetail_txtMountOfOrder);
        txtPriceOfOrder = (TextView) findViewById(R.id.ActivityMenuDrinkDetail_txtPriceOfOrder);
        recyclerViewDrink = (RecyclerView) findViewById(R.id.ActivityMenuDrinkDetail_recycleViewDrink);
        btnPay = (Button) findViewById(R.id.ActivityMenuDrinkDetail_btnPay);
        imgAddDrink = (ImageView) findViewById(R.id.ActivityMenuDrinkDetail_imgAddDrink);
    }

    private void initReferencesObject() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        intent = getIntent();
        listenerIdDrink = new ListenerIdDrink();
       // Toast.makeText(this, "" + intent.getStringExtra("IDMenuDrink"), Toast.LENGTH_SHORT).show();
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
                        }

                    }
                    //  name = news.getTittle();
                    //    Toast.makeText(getActivity() , ""+  name, Toast.LENGTH_SHORT).show();

                }
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
                 Toast.makeText(this, "Bạn đã chọn thanh toán", Toast.LENGTH_SHORT).show();
                 break;
             case R.id.ActivityMenuDrinkDetail_imgAddDrink :
                 listenerIDMenuDrink = new ListenerIDMenuDrink();
                 listenerIDMenuDrink.setIdMenuDrink(intent.getStringExtra("IDMenuDrink"));
                 addDrink();
                 Toast.makeText(this, "Bạn đã chọn thêm 1 đồ uống", Toast.LENGTH_SHORT).show();
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