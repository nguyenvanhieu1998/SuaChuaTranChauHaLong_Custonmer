package com.example.suachuatranchauhalong_custonmer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suachuatranchauhalong_custonmer.Object.ListenerIDOrder;
import com.example.suachuatranchauhalong_custonmer.Object.OrderDetail;
import com.example.suachuatranchauhalong_custonmer.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailOrderAdapter extends RecyclerView.Adapter<DetailOrderAdapter.ViewHolder>  {
    List<OrderDetail> listOrderDetail ;
    Context context;
    private ListenerIDOrder send ;
    DatabaseReference databaseReference;
    public DetailOrderAdapter(List<OrderDetail> listOrderDetail, Context context)
    {
        this.listOrderDetail = listOrderDetail;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_orderdetail,parent,false);
        return new DetailOrderAdapter.ViewHolder(view);
    }
    String idBill;
    int mount;
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        initReference();
        send = new ListenerIDOrder();
        final OrderDetail orderDetail = listOrderDetail.get(position);
         holder.txtNameDrink.setText(orderDetail.getNameDrink());
        Picasso.get()
                .load(orderDetail.getImgDrink())
                .fit()
                .into(holder.imgDrink);
         holder.txtBonus.setText(orderDetail.getBonus().toString());
         holder.txtMountDrink.setText("Số lượng : "+ orderDetail.getMount());
         holder.txtTotalPriceDrink.setText("Giá : " + orderDetail.getPrice());

    }
    private void initReference()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference();
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
            txtBonus = (TextView) itemView.findViewById(R.id.itemOrderDetail_txtBonus);
            imgDrink = (ImageView) itemView.findViewById(R.id.itemOrderDetail_imgOrder);
            txtNameDrink = (TextView) itemView.findViewById(R.id.itemOrderDetail_txtNameDrink);
            txtTotalPriceDrink = (TextView) itemView.findViewById(R.id.itemOrderDetail_txtTotalPriceDrink);
            txtMountDrink = (TextView) itemView.findViewById(R.id.itemOrderDetail_txtMountDrink);
        }
    }
}