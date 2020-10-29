package com.example.suachuatranchauhalong_custonmer.Fagment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.suachuatranchauhalong_custonmer.Adapter.LoveDrinkAdapter;
import com.example.suachuatranchauhalong_custonmer.Adapter.NewAdapter;
import com.example.suachuatranchauhalong_custonmer.Object.Drink;
import com.example.suachuatranchauhalong_custonmer.Object.News;
import com.example.suachuatranchauhalong_custonmer.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Fragment_LoveDrink extends Fragment {
    Drink drink;
    LoveDrinkAdapter loveDrinkAdapter;
    List<Drink> arrayListDrink;
    View convertiew;
    ViewHolder holder = null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(convertiew==null)
        {
            convertiew = inflater.inflate(R.layout.activity_fragment__love_drink,container,false);
            addControls();

        }
        else
        {
            holder = (ViewHolder) convertiew.getTag();
        }
        addControls();
        addInitData();
        addEvents();
        return convertiew;
    }
    public class ViewHolder
    {
        RecyclerView recyclerLoveDrink;
    }
    public void addControls()
    {
        holder = new ViewHolder();
        holder.recyclerLoveDrink = (RecyclerView) convertiew.findViewById(R.id.FragmentLoveDrink_RecyclerViewLoveDrink);
        convertiew.setTag(holder);
    }
    private void addInitData() {
        String dateCreateDrink;
        Calendar calen = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy");
        dateCreateDrink = "" + simpleDateFormat.format(calen.getTime());
        arrayListDrink = new ArrayList<Drink>();
        arrayListDrink.clear();
        holder.recyclerLoveDrink.setHasFixedSize(true);
//        int orientation = LinearLayoutManager.HORIZONTAL; //Cuộn ngang
//        boolean reverse = false; //true thì bắt đầu từ phần tử cuối
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//        layoutManager.scrollToPosition(0);
        //holder.recyclerLoveDrink.setLayoutManager(layoutManager);
        holder.recyclerLoveDrink.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true));
        holder.recyclerLoveDrink.setLayoutManager(new LinearLayoutManager(getContext()));
        loveDrinkAdapter = new LoveDrinkAdapter(arrayListDrink,getContext());
        holder.recyclerLoveDrink.setAdapter(loveDrinkAdapter);
        //     holder.recyclerKhuyenMai.setAdapter(newAdapter);

    }
    public void addEvents()
    {

    }
}