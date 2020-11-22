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

import com.example.suachuatranchauhalong_custonmer.InterfaceOnClick.ListenerIdFragmentAccount;
import com.example.suachuatranchauhalong_custonmer.InterfaceOnClick.ListenerIdFragmentPoint;
import com.example.suachuatranchauhalong_custonmer.Object.FragmentAccount;
import com.example.suachuatranchauhalong_custonmer.Object.FragmentPoint;
import com.example.suachuatranchauhalong_custonmer.R;

import java.util.List;

public class FragmentPointAdapter extends RecyclerView.Adapter<FragmentPointAdapter.ViewHolder> {
    private List<FragmentPoint> listFragmentPoint;
    private Context context;
    ListenerIdFragmentPoint listener;
    public FragmentPointAdapter(Context context,List<FragmentPoint> listFragmentPoint,ListenerIdFragmentPoint listener)
    {
        this.context = context;
        this.listFragmentPoint = listFragmentPoint;
        this.listener = listener;
    }
    @NonNull
    @Override
    public FragmentPointAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_point,parent,false);
        return new FragmentPointAdapter.ViewHolder(viewItem);
    }
    Intent intent;
    @Override
    public void onBindViewHolder(@NonNull FragmentPointAdapter.ViewHolder holder, int position) {
        final FragmentPoint fragmentPoint = listFragmentPoint.get(position);
//        Picasso.get()
//                .load(drink.getImgUriDrink())
//                .fit()
//                .into(holder.imgDrink);

        //  Picasso.with(context).load(mb.getPhotoURL()).into(holder.imgFace);
        //  holder.imgDrink.setImageResource(drink.getImageUri());
        holder.img1.setImageResource(fragmentPoint.getImg1());
        holder.img2.setImageResource(fragmentPoint.getImg2());
        holder.txtName.setText(fragmentPoint.getName());
//        holder.txtNameDrink.setText(drink.getNameDrink());
//        holder.txtPriceDrink.setText("" + drink.getPriceDrink());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "" + fragmentAccount.getId(), Toast.LENGTH_SHORT).show();
                listener.onItemClick(fragmentPoint);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listFragmentPoint.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img1,img2;
        TextView txtName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img1 = (ImageView) itemView.findViewById(R.id.itemFragmentPoint_img1);
            txtName = (TextView) itemView.findViewById(R.id.itemFragmentPoint_txtName);
            img2 = (ImageView) itemView.findViewById(R.id.itemFragmentPoint_img2);
        }
    }
}