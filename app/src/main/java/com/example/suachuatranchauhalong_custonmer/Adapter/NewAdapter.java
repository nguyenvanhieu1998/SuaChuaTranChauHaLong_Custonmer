package com.example.suachuatranchauhalong_custonmer.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suachuatranchauhalong_custonmer.Object.Drink;
import com.example.suachuatranchauhalong_custonmer.Object.News;
import com.example.suachuatranchauhalong_custonmer.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewAdapter extends RecyclerView.Adapter<NewAdapter.ViewHolder> {
    private List<News> listNews;
    public NewAdapter(List<News> listNews)
    {
        this.listNews = listNews;
    }
    @NonNull
    @Override
    public NewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tintuc,parent,false);
        return new NewAdapter.ViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News news = listNews.get(position);
        Picasso.get()
                .load(news.getImgTittle())
                .fit()
                .into(holder.imgNews);
        //  Picasso.with(context).load(mb.getPhotoURL()).into(holder.imgFace);
        //  holder.imgDrink.setImageResource(drink.getImageUri());
        holder.txtTittle.setText(news.getTittle());
    }

    @Override
    public int getItemCount() {
        return listNews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgNews;
        TextView txtTittle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTittle = (TextView) itemView.findViewById(R.id.itemTinTuc_txtTittle);
            imgNews = (ImageView) itemView.findViewById(R.id.itemTinTuc_imgNews);
        }
    }
}
