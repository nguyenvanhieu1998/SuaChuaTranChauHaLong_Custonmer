package com.example.suachuatranchauhalong_custonmer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suachuatranchauhalong_custonmer.InterfaceOnClick.VoucherOfCustomerOnItemClick;
import com.example.suachuatranchauhalong_custonmer.InterfaceOnClick.VoucherOnItemClick;
import com.example.suachuatranchauhalong_custonmer.Object.Voucher;
import com.example.suachuatranchauhalong_custonmer.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VoucherOfCustomerAdapter extends RecyclerView.Adapter<VoucherOfCustomerAdapter.ViewHolder> {
    private List<Voucher> listVoucher;
    private Context context;
    //   private UpdateAndHiddenDrink listener;
    VoucherOfCustomerOnItemClick listener;
    public VoucherOfCustomerAdapter(Context context,List<Voucher> listVoucher,VoucherOfCustomerOnItemClick listener)
    {
        this.context = context;
        this.listVoucher = listVoucher;
        this.listener =listener;
    }
    @NonNull
    @Override
    public VoucherOfCustomerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_voucher,parent,false);
        return new VoucherOfCustomerAdapter.ViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherOfCustomerAdapter.ViewHolder holder, int position) {
        final   Voucher voucher = listVoucher.get(position);
        Picasso.get()
                .load(voucher.getImgVoucher())
                .fit()
                .into(holder.imgVoucher);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClickListener(voucher);
            }
        });
    }

    private void updateToFirebase() {

    }


    @Override
    public int getItemCount() {
        return listVoucher.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgVoucher,imgUpdate,imgHidden;
        // TextView txtNameDrink,txtPriceDrink;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgVoucher = (ImageView) itemView.findViewById(R.id.itemVoucher_imgVoucher);
//            txtNameDrink = (TextView) itemView.findViewById(R.id.itemDrink_txtNameDrink);
//            txtPriceDrink = (TextView) itemView.findViewById(R.id.itemDrink_txtPriceDrink);
//            imgUpdate = (ImageView) itemView.findViewById(R.id.itemDrink_imgUpdate);
//            imgHidden = (ImageView) itemView.findViewById(R.id.itemDrink_imgHidden);
        }
    }
}
