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
import com.example.suachuatranchauhalong_custonmer.Object.ListenerIDOrder;
import com.example.suachuatranchauhalong_custonmer.Object.ListenerIdOrderDetail;
import com.example.suachuatranchauhalong_custonmer.Object.OrderDetail;
import com.example.suachuatranchauhalong_custonmer.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FragmentUpdateDrinkOfCart_OrderDetailAdapter extends RecyclerView.Adapter<FragmentUpdateDrinkOfCart_OrderDetailAdapter.ViewHolder>  {
    List<OrderDetail> listOrderDetail ;
    Context context;
    private OnItemClickListener listener;
    public FragmentUpdateDrinkOfCart_OrderDetailAdapter(List<OrderDetail> listOrderDetail, Context context,OnItemClickListener listener)
    {
        this.listOrderDetail = listOrderDetail;
        this.context = context;
        this.listener = listener;
    }
    @NonNull
    @Override
    public FragmentUpdateDrinkOfCart_OrderDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_fragment_dialog_update_drink_of_cart_order_detail,parent,false);
        return new FragmentUpdateDrinkOfCart_OrderDetailAdapter.ViewHolder(view);
    }
    String idBill;
    int mount;
    @Override
    public void onBindViewHolder(@NonNull final FragmentUpdateDrinkOfCart_OrderDetailAdapter.ViewHolder holder, int position) {
        final OrderDetail orderDetail = listOrderDetail.get(position);
        holder.txtNameDrink.setText(orderDetail.getNameDrink());
        Picasso.get()
                .load(orderDetail.getImgDrink())
                .fit()
                .into(holder.imgDrink);
        holder.txtBonus.setText(orderDetail.getBonus().toString());
        holder.txtMountDrink.setText("Số lượng : "+ orderDetail.getMount());
        holder.txtTotalPriceDrink.setText("Giá : " + orderDetail.getPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListenerIdOrderDetail listenerIdOrderDetail = new ListenerIdOrderDetail();
                listenerIdOrderDetail.setIdOrderDetail(orderDetail.getIdOrderDetail());
                listener.onItemClickListener(orderDetail);
            }
        });

    }
    @Override
    public int getItemCount() {
        return listOrderDetail.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgDrink;
        TextView txtTotalPriceDrink,txtNameDrink,txtMountDrink,txtBonus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtBonus = (TextView) itemView.findViewById(R.id.itemOrderDetail_FragmentDialogUpdateDrinkOfCart_txtBonus);
            imgDrink = (ImageView) itemView.findViewById(R.id.itemOrderDetail_FragmentDialogUpdateDrinkOfCart_imgDrink);
            txtNameDrink = (TextView) itemView.findViewById(R.id.itemOrderDetail_FragmentDialogUpdateDrinkOfCart_txtNameDrink);
            txtTotalPriceDrink = (TextView) itemView.findViewById(R.id.itemOrderDetail_FragmentDialogUpdateDrinkOfCart_txtTotalPriceDrink);
            txtMountDrink = (TextView) itemView.findViewById(R.id.itemOrderDetail_FragmentDialogUpdateDrinkOfCart_txtMountDrink);
        }
    }
}