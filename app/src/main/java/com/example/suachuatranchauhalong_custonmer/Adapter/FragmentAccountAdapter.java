package com.example.suachuatranchauhalong_custonmer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suachuatranchauhalong_custonmer.Activity.DrinkDetail;
import com.example.suachuatranchauhalong_custonmer.InterfaceOnClick.ListenerIdFragmentAccount;
import com.example.suachuatranchauhalong_custonmer.InterfaceOnClick.UpdateAndHiddenDrink;
import com.example.suachuatranchauhalong_custonmer.Object.Drink;
import com.example.suachuatranchauhalong_custonmer.Object.FragmentAccount;
import com.example.suachuatranchauhalong_custonmer.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FragmentAccountAdapter extends RecyclerView.Adapter<FragmentAccountAdapter.ViewHolder> {
    private List<FragmentAccount> listFragmentAccount;
    private Context context;
    ListenerIdFragmentAccount listener;
    public FragmentAccountAdapter(Context context,List<FragmentAccount> listFragmentAccount,ListenerIdFragmentAccount listener)
    {
        this.context = context;
        this.listFragmentAccount = listFragmentAccount;
        this.listener = listener;
    }
    @NonNull
    @Override
    public FragmentAccountAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragmentaccount,parent,false);
        return new FragmentAccountAdapter.ViewHolder(viewItem);
    }
    Intent intent;
    @Override
    public void onBindViewHolder(@NonNull FragmentAccountAdapter.ViewHolder holder, int position) {
          final FragmentAccount fragmentAccount = listFragmentAccount.get(position);
//        Picasso.get()
//                .load(drink.getImgUriDrink())
//                .fit()
//                .into(holder.imgDrink);

        //  Picasso.with(context).load(mb.getPhotoURL()).into(holder.imgFace);
        //  holder.imgDrink.setImageResource(drink.getImageUri());
        holder.img1.setImageResource(fragmentAccount.getImg1());
        holder.img2.setImageResource(fragmentAccount.getImg2());
        holder.txtName.setText(fragmentAccount.getName());
//        holder.txtNameDrink.setText(drink.getNameDrink());
//        holder.txtPriceDrink.setText("" + drink.getPriceDrink());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "" + fragmentAccount.getId(), Toast.LENGTH_SHORT).show();
                 listener.onItemClick(fragmentAccount);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listFragmentAccount.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img1,img2;
        TextView txtName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img1 = (ImageView) itemView.findViewById(R.id.itemFragmentAccount_img1);
            txtName = (TextView) itemView.findViewById(R.id.itemFragmentAccount_txtName);
            img2 = (ImageView) itemView.findViewById(R.id.itemFragmentAccount_img2);
        }
    }
}