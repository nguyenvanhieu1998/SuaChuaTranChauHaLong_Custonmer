package com.example.suachuatranchauhalong_custonmer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suachuatranchauhalong_custonmer.Activity.ActivityNewsDetail;
import com.example.suachuatranchauhalong_custonmer.Object.News;
import com.example.suachuatranchauhalong_custonmer.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PromotionAdapter extends RecyclerView.Adapter<PromotionAdapter.ViewHolder> {
    private List<News> listNews;
    private Context context;
  //  private OnItemClickListener listener;
    News news;
    String idNewCurrent,typeNewsCurent;
    public PromotionAdapter(Context context, List<News> listNews)
    {
        this.context = context;
        this.listNews = listNews;
      ///  this.listener = listener;
    }
    @NonNull
    @Override
    public PromotionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_promotion,parent,false);
        return new PromotionAdapter.ViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull PromotionAdapter.ViewHolder holder,final int position) {
         news = listNews.get(position);
        Picasso.get()
                .load(news.getImgTittle())
                .fit()
                .into(holder.imgNews);
        holder.txtTittle.setText(news.getTittle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idNewCurrent = listNews.get(position).getIdNews().toString();
                typeNewsCurent = listNews.get(position).getType().toString();
                Intent intent = new Intent(context, ActivityNewsDetail.class);
                intent.putExtra("IDNew",idNewCurrent);
                intent.putExtra("TypeNews",typeNewsCurent);
                context.startActivity(intent);
            }
        });
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
            txtTittle = (TextView) itemView.findViewById(R.id.itemPromotion_txtTittle);
            imgNews = (ImageView) itemView.findViewById(R.id.itemPromotion_imgNews);
        }
    }
}
