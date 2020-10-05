package com.example.suachuatranchauhalong_custonmer.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suachuatranchauhalong_custonmer.Object.Drink;
import com.example.suachuatranchauhalong_custonmer.Object.MenuDrink;
import com.example.suachuatranchauhalong_custonmer.Object.MenuDrink;
import com.example.suachuatranchauhalong_custonmer.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MenuDrinkAdapter extends RecyclerView.Adapter<MenuDrinkAdapter.ViewHolder> {
    private List<MenuDrink> listMenuDrink;
    public MenuDrinkAdapter(List<MenuDrink> listMenuDrink)
    {
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
        MenuDrink menuDrink = listMenuDrink.get(position);
        Picasso.get()
                .load(menuDrink.getImageUri())
                .fit()
                .into(holder.imgMenuDrink);
        //  Picasso.with(context).load(mb.getPhotoURL()).into(holder.imgFace);
        //  holder.imgDrink.setImageResource(drink.getImageUri());
        holder.txtNameMenuDrink.setText(menuDrink.getName());

    }

    @Override
    public int getItemCount() {
        return listMenuDrink.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMenuDrink;
        TextView txtNameMenuDrink;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMenuDrink = (ImageView) itemView.findViewById(R.id.itemMenuDrink_imageMenu);
            txtNameMenuDrink = (TextView) itemView.findViewById(R.id.itemMenuDrink_txtNameDrinkInMenu);
        }
    }
}
