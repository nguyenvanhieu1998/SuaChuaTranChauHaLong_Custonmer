package com.example.suachuatranchauhalong_custonmer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suachuatranchauhalong_custonmer.Object.Drink;
import com.example.suachuatranchauhalong_custonmer.R;
import com.example.suachuatranchauhalong_custonmer.Object.Drink;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.ViewHolder> {
    private List<Drink> listDrink;
    public DrinkAdapter(List<Drink> listDrink)
    {
        this.listDrink = listDrink;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drink,parent,false);
        return new ViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
           Drink drink = listDrink.get(position);
        Picasso.get()
                .load(drink.imageUri)
                .fit()
                .into(holder.imgDrink);
         //  Picasso.with(context).load(mb.getPhotoURL()).into(holder.imgFace);
         //  holder.imgDrink.setImageResource(drink.getImageUri());
            holder.txtNameDrink.setText(drink.getName());
            holder.txtPriceDrink.setText("" + drink.getPrice());
    }

    @Override
    public int getItemCount() {
        return listDrink.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgDrink;
        TextView txtNameDrink,txtPriceDrink;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDrink = (ImageView) itemView.findViewById(R.id.itemDrink_imageDrink);
            txtNameDrink = (TextView) itemView.findViewById(R.id.itemDrink_txtNameDrink);
            txtPriceDrink = (TextView) itemView.findViewById(R.id.itemDrink_txtPriceDrink);
        }
    }
}
