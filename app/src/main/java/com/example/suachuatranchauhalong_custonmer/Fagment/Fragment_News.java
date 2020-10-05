package com.example.suachuatranchauhalong_custonmer.Fagment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.suachuatranchauhalong_custonmer.Adapter.MenuDrinkAdapter;
import com.example.suachuatranchauhalong_custonmer.Adapter.NewAdapter;
import com.example.suachuatranchauhalong_custonmer.Object.News;
import com.example.suachuatranchauhalong_custonmer.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Fragment_News extends Fragment {

    View convertiew;
    ViewHolder holder = null;
    ArrayList<News> arrayListNews;
    NewAdapter newAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(convertiew==null)
        {
            convertiew = inflater.inflate(R.layout.activity_fragment__news,container,false);
            addControls();

        }
        else
        {
            holder = (ViewHolder) convertiew.getTag();
        }
       // addControls();
        addInitData();
        addEvents();
        return convertiew;
    }
    public class ViewHolder
    {
        ImageView imgUserCurrent,imgNotification;
        TextView txtNameUserCurrent;
        RecyclerView recyclerNews,recyclerKhuyenMai;
    }
    public void addControls()
    {
        holder = new ViewHolder();
        holder.imgUserCurrent = (ImageView) convertiew.findViewById(R.id.FragmentNews_imgUserCurrent);
        holder.imgNotification = (ImageView) convertiew.findViewById(R.id.FragmentNews_imgNotification);
        holder.txtNameUserCurrent = (TextView) convertiew.findViewById(R.id.FragmentNews_txtNameUserCurrent);
        holder.recyclerNews = (RecyclerView) convertiew.findViewById(R.id.FragmentNews_recyclerNews);
        holder.recyclerKhuyenMai = (RecyclerView) convertiew.findViewById(R.id.FragmentNews_recyclerKhuyenMai);
        convertiew.setTag(holder);
    }
        private void addInitData() {
            String dateCreateNews;
            Calendar calen = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy");
            dateCreateNews = "" + simpleDateFormat.format(calen.getTime());
        arrayListNews = new ArrayList<News>();
        arrayListNews.add(new News(1,R.drawable.ic_baseline_cached_24,"Tin khuyến mãi ngày 2/9","Khuyến mãi ngập tràn",dateCreateNews,"Tin tức"));
            arrayListNews.add(new News(1,R.drawable.ic_baseline_cached_24,"Tin khuyến mãi ngày 2/9","Khuyến mãi ngập tràn",dateCreateNews,"Tin tức"));
            arrayListNews.add(new News(1,R.drawable.ic_baseline_cached_24,"Tin khuyến mãi ngày 2/9","Khuyến mãi ngập tràn",dateCreateNews,"Tin tức"));
            arrayListNews.add(new News(1,R.drawable.ic_baseline_cached_24,"Tin khuyến mãi ngày 2/9","Khuyến mãi ngập tràn",dateCreateNews,"Tin tức"));
        holder.recyclerNews.setHasFixedSize(true);
           // Context context = this;
            int orientation = LinearLayoutManager.HORIZONTAL; //Cuộn ngang
// int orientation = LinearLayoutManager.VERTICAL; //cuốn đứng
            boolean reverse = false; //true thì bắt đầu từ phần tử cuối
            LinearLayoutManager layoutManager =
                    new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            layoutManager.scrollToPosition(0);//Thiết lập phần tử mặc định nếu muốn
// Gắn vào RecylerView
            holder.recyclerNews.setLayoutManager(layoutManager);
      //  holder.recyclerNews.setLayoutManager(HorizontalScrollView());
        newAdapter = new NewAdapter(arrayListNews);
        holder.recyclerNews.setAdapter(newAdapter);

    }
    public void addEvents()
    {

    }
}