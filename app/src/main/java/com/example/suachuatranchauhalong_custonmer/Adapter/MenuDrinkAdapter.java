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

import com.example.suachuatranchauhalong_custonmer.Activity.MenuDrinkDetail;
import com.example.suachuatranchauhalong_custonmer.Object.Drink;
import com.example.suachuatranchauhalong_custonmer.Object.MenuDrink;
import com.example.suachuatranchauhalong_custonmer.Object.MenuDrink;
import com.example.suachuatranchauhalong_custonmer.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MenuDrinkAdapter extends RecyclerView.Adapter<MenuDrinkAdapter.ViewHolder> {
    private List<MenuDrink> listMenuDrink;
    private Context context;
    public MenuDrinkAdapter(Context context, List<MenuDrink> listMenuDrink)
    {
        this.context = context;
        this.listMenuDrink = listMenuDrink;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menudrink,parent,false);
        return new ViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MenuDrink menuDrink = listMenuDrink.get(position);
        Picasso.get()
                .load(menuDrink.getImageUriMenuDrink())
                .fit()
                .into(holder.imgMenuDrink);
//        //  Picasso.with(context).load(mb.getPhotoURL()).into(holder.imgFace);
//        //  holder.imgDrink.setImageResource(drink.getImageUri());
        holder.txtIdMenuDrink.setText(menuDrink.getIdMenuDrink());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MenuDrinkDetail.class);
                intent.putExtra("IDMenuDrink",menuDrink.getIdMenuDrink());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listMenuDrink.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMenuDrink;
        TextView txtIdMenuDrink;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMenuDrink = (ImageView) itemView.findViewById(R.id.itemMenuDrink_imageMenu);
            txtIdMenuDrink = (TextView) itemView.findViewById(R.id.itemMenuDrink_txtIdMenuDrink);
        }
    }
}
