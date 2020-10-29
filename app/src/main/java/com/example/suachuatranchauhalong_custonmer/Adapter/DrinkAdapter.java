package com.example.suachuatranchauhalong_custonmer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suachuatranchauhalong_custonmer.InterfaceOnClick.UpdateAndHiddenDrink;
import com.example.suachuatranchauhalong_custonmer.Object.Drink;
import com.example.suachuatranchauhalong_custonmer.R;
import com.example.suachuatranchauhalong_custonmer.Object.Drink;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.ViewHolder> {
    private List<Drink> listDrink;
    private Context context;
    private UpdateAndHiddenDrink listener;
    public DrinkAdapter(Context context,List<Drink> listDrink,UpdateAndHiddenDrink listener)
    {
        this.context = context;
        this.listDrink = listDrink;
        this.listener =listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drink,parent,false);
        return new ViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final   Drink drink = listDrink.get(position);
        Picasso.get()
                .load(drink.getImgUriDrink())
                .fit()
                .into(holder.imgDrink);
         //  Picasso.with(context).load(mb.getPhotoURL()).into(holder.imgFace);
         //  holder.imgDrink.setImageResource(drink.getImageUri());
            holder.txtNameDrink.setText(drink.getNameDrink());
            holder.txtPriceDrink.setText("" + drink.getPriceDrink());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            holder.imgUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.update(drink.getIdDrink(),drink.getIdMenuDrink());
                }
            });
        holder.imgHidden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.hidden(drink.getIdDrink(),drink.getIdMenuDrink());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listDrink.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgDrink,imgUpdate,imgHidden;
        TextView txtNameDrink,txtPriceDrink;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDrink = (ImageView) itemView.findViewById(R.id.itemDrink_imageDrink);
            txtNameDrink = (TextView) itemView.findViewById(R.id.itemDrink_txtNameDrink);
            txtPriceDrink = (TextView) itemView.findViewById(R.id.itemDrink_txtPriceDrink);
            imgUpdate = (ImageView) itemView.findViewById(R.id.itemDrink_imgUpdate);
            imgHidden = (ImageView) itemView.findViewById(R.id.itemDrink_imgHidden);
        }
    }
}
