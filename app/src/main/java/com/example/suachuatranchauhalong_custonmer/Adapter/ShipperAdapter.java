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
import com.example.suachuatranchauhalong_custonmer.Object.Customer;
import com.example.suachuatranchauhalong_custonmer.Object.Shipper;
import com.example.suachuatranchauhalong_custonmer.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShipperAdapter extends RecyclerView.Adapter<ShipperAdapter.ViewHolder> {
    private List<Shipper> listShipper;
    private Context context;
    private OnItemClickListener listener;
    public ShipperAdapter(Context context, List<Shipper> listShipper,OnItemClickListener listener)
    {
        this.context = context;
        this.listShipper = listShipper;
        this.listener = listener;
    }
    @NonNull
    @Override
    public ShipperAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shipper,parent,false);
        return new ShipperAdapter.ViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ShipperAdapter.ViewHolder holder, int position) {
        final Shipper shipper = listShipper.get(position);
        Picasso.get()
                .load(shipper.getAvtShipperURL())
                .fit()
                .into(holder.imgShipper);
//        //  Picasso.with(context).load(mb.getPhotoURL()).into(holder.imgFace);
//        //  holder.imgDrink.setImageResource(drink.getImageUri());
        holder.txtNameShipper.setText(shipper.getNameShipper().toString());
        if(shipper.isSexShipper() ==true)
        {
            holder.txtSexShipper.setText("Giới tính : Nam");
        }
        else
        {
            holder.txtSexShipper.setText("Giới tính : Nữ");
        }
        //holder.txtOrderOfShipper.setText("Đã giao : "+ shipper.getMountOrder());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClickListener(shipper);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listShipper.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgShipper;
        TextView txtNameShipper,txtSexShipper,txtOrderOfShipper;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgShipper = (CircleImageView) itemView.findViewById(R.id.itemShipper_imageShipper);
            txtNameShipper = (TextView) itemView.findViewById(R.id.itemShipper_txtNameShipper);
            txtSexShipper = (TextView) itemView.findViewById(R.id.itemShipper_txtSexShipper);
           // txtOrderOfShipper = (TextView) itemView.findViewById(R.id.itemShipper_txtOrderOfShipper);
        }
    }
}
