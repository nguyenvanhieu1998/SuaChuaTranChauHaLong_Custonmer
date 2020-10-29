package com.example.suachuatranchauhalong_custonmer.Fagment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suachuatranchauhalong_custonmer.Activity.MenuDrinkDetail;
import com.example.suachuatranchauhalong_custonmer.Adapter.DrinkAdapter;
import com.example.suachuatranchauhalong_custonmer.Adapter.MenuDrinkAdapter;
import com.example.suachuatranchauhalong_custonmer.Adapter.NewAdapter;
import com.example.suachuatranchauhalong_custonmer.Convert.ImageConverter;
import com.example.suachuatranchauhalong_custonmer.FragmentDialog.FragmentDialogAddMenuDrinkForAdmin;
import com.example.suachuatranchauhalong_custonmer.FragmentDialog.FragmentDialogAddNewsForAdmin;
import com.example.suachuatranchauhalong_custonmer.FragmentDialog.FragmentDialogHiddenMenuDrinkForAdmin;
import com.example.suachuatranchauhalong_custonmer.FragmentDialog.FragmentDialogUpdateMenuDrinkForAdmin;
import com.example.suachuatranchauhalong_custonmer.Object.Drink;
import com.example.suachuatranchauhalong_custonmer.Object.MenuDrink;
import com.example.suachuatranchauhalong_custonmer.Object.News;
import com.example.suachuatranchauhalong_custonmer.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

public class Fragment_Order extends Fragment implements View.OnClickListener{
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    MenuDrinkAdapter menuDrinkAdapter;
    ArrayList<MenuDrink> menuDrinkArrayList;
    View convertiew;
    ViewHolder holder = null;
    private ArrayList<MenuDrink> listMenuDrink;
    //private MenuDrinkAdapter menudrinkAdapter;
    Boolean checkShowFab =  false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(convertiew==null)
        {
            convertiew = inflater.inflate(R.layout.activity_fragment__order,container,false);
            addControls();
        }
        else
        {
            holder = (ViewHolder) convertiew.getTag();
        }
       // addInitData();
        initReferenceObject();
        setMenuDrink();
        addEvents();
        return convertiew;
    }
    private void initReferenceObject()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        //String uid = firebaseUser.getUid().toString();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.FragmentOrder_floatingActionButtonListener :
                if(checkShowFab == false)
                {
                    showFab();
                    checkShowFab = true;
                }
                else
                {
                    hiddenFab();
                    checkShowFab = false;
                }
                break;
            case R.id.FragmentOrder_fabAddMenuDrink :
                FragmentDialogAddMenuDrinkForAdmin fragmentDialogAdd = new FragmentDialogAddMenuDrinkForAdmin();
                fragmentDialogAdd.show(getFragmentManager(),"FragmentDialogAddMenuDrink");
                hiddenFab();
                checkShowFab = false;
                break;
            case R.id.FragmentOrder_fabUpdateMenuDrink :
                FragmentDialogUpdateMenuDrinkForAdmin fragmentDialogUpdate = new FragmentDialogUpdateMenuDrinkForAdmin();
                fragmentDialogUpdate.show(getFragmentManager(),"FragmentDialogUpdateMenuDrink");
               // Toast.makeText(getActivity(), "Bạn đã chọn update menu drink", Toast.LENGTH_SHORT).show();
                hiddenFab();
                checkShowFab = false;
                break;
            case R.id.FragmentOrder_fabHiddenMenuDrink :
                FragmentDialogHiddenMenuDrinkForAdmin fragmentDialogHidden = new FragmentDialogHiddenMenuDrinkForAdmin();
                fragmentDialogHidden.show(getFragmentManager(),"FragmentDialogHiddenMenuDrink");
               // Toast.makeText(getActivity(), "Bạn đã chọn ẩn menu drink", Toast.LENGTH_SHORT).show();
                hiddenFab();
                checkShowFab = false;
                break;
        }
    }

    public class ViewHolder
    {
        private RecyclerView recyclerMenuDrink;
        private ImageView imgSuaChuaSinhTo,imgSuaChua,imgCafe,imgCacMonKhac;
        private FloatingActionButton fabListener,fabAddMenuDrink,fabUpdateMenuDrink,fabHiddenMenuDrink;
    }
    public void addControls()
    {
        holder = new ViewHolder();
        holder.fabListener = (FloatingActionButton) convertiew.findViewById(R.id.FragmentOrder_floatingActionButtonListener);
        holder.fabAddMenuDrink = (FloatingActionButton) convertiew.findViewById(R.id.FragmentOrder_fabAddMenuDrink);
        holder.fabUpdateMenuDrink = (FloatingActionButton) convertiew.findViewById(R.id.FragmentOrder_fabUpdateMenuDrink);
        holder.fabHiddenMenuDrink = (FloatingActionButton) convertiew.findViewById(R.id.FragmentOrder_fabHiddenMenuDrink);
         holder.recyclerMenuDrink = (RecyclerView) convertiew.findViewById(R.id.FragmentOrder_recycleViewMenuDrink);
        convertiew.setTag(holder);
    }
    private void setMenuDrink() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        menuDrinkArrayList = new ArrayList<>();
        databaseReference.child("ListMenuDrink").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                menuDrinkArrayList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    MenuDrink menuDrink = dataSnapshot1.getValue(MenuDrink.class);
                    if(menuDrink.getStatus()==1)
                    {
                        menuDrinkArrayList.add(menuDrink);
                    }
                    //  name = news.getTittle();
                    //    Toast.makeText(getActivity() , ""+  name, Toast.LENGTH_SHORT).show();

                }
                menuDrinkAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        menuDrinkAdapter = new MenuDrinkAdapter(getActivity(),menuDrinkArrayList);
        holder.recyclerMenuDrink.setAdapter(menuDrinkAdapter);
        holder.recyclerMenuDrink.setHasFixedSize(true);
        int orientation = LinearLayoutManager.VERTICAL; //Cuộn ngang
        boolean reverse = false; //true thì bắt đầu từ phần tử cuối
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        layoutManager.scrollToPosition(0);//Thiết lập phần tử mặc định nếu muốn
// Gắn vào RecylerView
        holder.recyclerMenuDrink.setLayoutManager(layoutManager);
    }
    public void addEvents()
    {
        holder.fabListener.setOnClickListener(this);
        holder.fabAddMenuDrink.setOnClickListener(this);
        holder.fabUpdateMenuDrink.setOnClickListener(this);
        holder.fabHiddenMenuDrink.setOnClickListener(this);
    }
    private void showFab()
    {
        holder.fabAddMenuDrink.show();
        holder.fabUpdateMenuDrink.show();
        holder.fabHiddenMenuDrink.show();
    }
    private void hiddenFab()
    {
        holder.fabAddMenuDrink.hide();
        holder.fabUpdateMenuDrink.hide();
        holder.fabHiddenMenuDrink.hide();
    }

}