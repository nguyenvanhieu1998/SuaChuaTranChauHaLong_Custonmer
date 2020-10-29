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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewAdapter extends RecyclerView.Adapter<NewAdapter.ViewHolder> {
    private List<News> listNews;
    private Context context;
    DatabaseReference databaseReference;
 //   private OnItemClickListener listener;
    News news;
    String idNewCurrent,typeNewsCurent;
    public NewAdapter(Context context,List<News> listNews)
    {
        this.context =context;
        this.listNews = listNews;
    }
    @NonNull
    @Override
    public NewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tintuc,parent,false);
        return new NewAdapter.ViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder,final int position) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
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
        private ImageView imgNews;
        private TextView txtTittle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTittle = (TextView) itemView.findViewById(R.id.itemTinTuc_txtTittle);
            imgNews = (ImageView) itemView.findViewById(R.id.itemTinTuc_imgNews);
        }

    }
}
