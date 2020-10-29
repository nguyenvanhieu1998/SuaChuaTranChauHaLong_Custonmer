package com.example.suachuatranchauhalong_custonmer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suachuatranchauhalong_custonmer.InterfaceOnClick.OnItemClickListener;
import com.example.suachuatranchauhalong_custonmer.Object.MenuDrink;
import com.example.suachuatranchauhalong_custonmer.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MenuDrink_FragmentDialogUpdateAndHiddenMenuDrink extends RecyclerView.Adapter<MenuDrink_FragmentDialogUpdateAndHiddenMenuDrink.ViewHolder> {
    private List<MenuDrink> listMenuDrink;
    private Context context;
    private OnItemClickListener listener;
    public MenuDrink_FragmentDialogUpdateAndHiddenMenuDrink(Context context, List<MenuDrink> listMenuDrink,OnItemClickListener listener)
    {
        this.context = context;
        this.listMenuDrink = listMenuDrink;
        this.listener = listener;
    }
    @NonNull
    @Override
    public MenuDrink_FragmentDialogUpdateAndHiddenMenuDrink.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menudrink_in_fragmentdialog_update_and_hidden,parent,false);
        return new MenuDrink_FragmentDialogUpdateAndHiddenMenuDrink.ViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuDrink_FragmentDialogUpdateAndHiddenMenuDrink.ViewHolder holder, int position) {
        final MenuDrink menuDrink = listMenuDrink.get(position);
        Picasso.get()
                .load(menuDrink.getImageUriMenuDrink())
                .fit()
                .into(holder.imgMenuDrink);
//        //  Picasso.with(context).load(mb.getPhotoURL()).into(holder.imgFace);
//        //  holder.imgDrink.setImageResource(drink.getImageUri());
        holder.txtNameMenuDrink.setText(menuDrink.getNameMenuDrink());
        holder.txtDateCreateMenuDrink.setText("Ngày tạo : "+menuDrink.getDateCreateMenuDrink());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClickListener(menuDrink);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMenuDrink.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMenuDrink;
        TextView txtNameMenuDrink,txtDateCreateMenuDrink;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMenuDrink = (ImageView) itemView.findViewById(R.id.FragmentDialogUpdateAndHiddenMenuDrink_itemMenuDrink_imgMenuDrink);
            txtNameMenuDrink = (TextView) itemView.findViewById(R.id.FragmentDialogUpdateAndHiddenMenuDrink_itemMenuDrink_txtNameMenuDrink);
            txtDateCreateMenuDrink = (TextView) itemView.findViewById(R.id.FragmentDialogUpdateAndHiddenMenuDrink_itemMenuDrink_txtDateCreteMenuDrink);
        }
    }
}