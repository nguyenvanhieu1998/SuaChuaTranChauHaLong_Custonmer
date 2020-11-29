package com.example.suachuatranchauhalong_custonmer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suachuatranchauhalong_custonmer.InterfaceOnClick.VoucherOfCartOnItemClick;
import com.example.suachuatranchauhalong_custonmer.InterfaceOnClick.VoucherOnItemClick;
import com.example.suachuatranchauhalong_custonmer.Object.Voucher;
import com.example.suachuatranchauhalong_custonmer.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VoucherOfCartAdapter extends RecyclerView.Adapter<VoucherOfCartAdapter.ViewHolder> {
    private List<Voucher> listVoucher;
    private Context context;
    //   private UpdateAndHiddenDrink listener;
    VoucherOfCartOnItemClick listener;
    public VoucherOfCartAdapter(Context context,List<Voucher> listVoucher,VoucherOfCartOnItemClick listener)
    {
        this.context = context;
        this.listVoucher = listVoucher;
        this.listener =listener;
    }
    @NonNull
    @Override
    public VoucherOfCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_voucher_of_cart,parent,false);
        return new VoucherOfCartAdapter.ViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherOfCartAdapter.ViewHolder holder, int position) {
        final   Voucher voucher = listVoucher.get(position);
//        Picasso.get()
////                .load(voucher.getImgVoucher())
////                .fit()
////                .into(holder.imgVoucher);
        holder.txtGiaTriKhuyenMai.setText("Khuyến mãi : "+voucher.getPricePromotion()+"%");
        holder.txtGiaTriDonHangApDung.setText("Đơn hàng áp dụng : "+ voucher.getPriceApply() + "đ");
        holder.btnApDung.setOnClickListener(new View.OnClickListener() {
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
        ImageView imgVoucher;
         TextView txtGiaTriKhuyenMai,txtGiaTriDonHangApDung;
         Button btnApDung;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgVoucher = (ImageView) itemView.findViewById(R.id.itemVoucherOfCart_imgVoucher);
            txtGiaTriKhuyenMai = (TextView) itemView.findViewById(R.id.itemVoucherOfCart_txtGiaTriKhuyenMai);
            txtGiaTriDonHangApDung = (TextView) itemView.findViewById(R.id.itemVoucherOfCart_txtGiaTriDonHangApDung);
            btnApDung  = (Button) itemView.findViewById(R.id.itemVoucherOfCart_btnApDung);
//            imgUpdate = (ImageView) itemView.findViewById(R.id.itemDrink_imgUpdate);
//            imgHidden = (ImageView) itemView.findViewById(R.id.itemDrink_imgHidden);
        }
    }
}