package com.example.suachuatranchauhalong_custonmer.Fagment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.suachuatranchauhalong_custonmer.Adapter.DrinkAdapter;
import com.example.suachuatranchauhalong_custonmer.Adapter.MenuDrinkAdapter;
import com.example.suachuatranchauhalong_custonmer.Convert.ImageConverter;
import com.example.suachuatranchauhalong_custonmer.Object.Drink;
import com.example.suachuatranchauhalong_custonmer.Object.MenuDrink;
import com.example.suachuatranchauhalong_custonmer.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Fragment_Order extends Fragment {
    View convertiew;
    ViewHolder holder = null;
    private ArrayList<MenuDrink> listMenuDrink;
    private MenuDrinkAdapter menudrinkAdapter;
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
        addEvents();
        return convertiew;
    }

//    private void addInitData() {
//        listMenuDrink = new ArrayList<MenuDrink>();
//        listMenuDrink.add(new MenuDrink(1,"Sữa chua",R.drawable.ic_baseline_cached_24));
//        listMenuDrink.add(new MenuDrink(2,"Sữa chua",R.drawable.menu_cafe));
//        listMenuDrink.add(new MenuDrink(3,"Sữa chua",R.drawable.menu_suachuasinhto2));
//        listMenuDrink.add(new MenuDrink(4,"Sữa chua",R.drawable.menu_suachua));
//        holder.recyclerMenuDrink.setHasFixedSize(true);
//        holder.recyclerMenuDrink.setLayoutManager(new GridLayoutManager(getContext(),2));
//        menudrinkAdapter = new MenuDrinkAdapter(listMenuDrink);
//        holder.recyclerMenuDrink.setAdapter(menudrinkAdapter);
//
//    }

    public class ViewHolder
    {
        RecyclerView recyclerMenuDrink;
    }
    public void addControls()
    {
        holder = new ViewHolder();
//        holder.recyclerMenuDrink = (RecyclerView) convertiew.findViewById(R.id.FragmentOrder_RecycleMenuSuaChua);
        convertiew.setTag(holder);
    }
    public void addEvents()
    {

    }

}