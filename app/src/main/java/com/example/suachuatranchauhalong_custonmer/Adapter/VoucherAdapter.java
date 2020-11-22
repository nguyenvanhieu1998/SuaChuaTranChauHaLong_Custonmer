package com.example.suachuatranchauhalong_custonmer.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suachuatranchauhalong_custonmer.Activity.DrinkDetail;
import com.example.suachuatranchauhalong_custonmer.InterfaceOnClick.UpdateAndHiddenDrink;
import com.example.suachuatranchauhalong_custonmer.InterfaceOnClick.VoucherOnItemClick;
import com.example.suachuatranchauhalong_custonmer.Object.Customer;
import com.example.suachuatranchauhalong_custonmer.Object.Drink;
import com.example.suachuatranchauhalong_custonmer.Object.Voucher;
import com.example.suachuatranchauhalong_custonmer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static androidx.core.app.ActivityCompat.startActivityForResult;

public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.ViewHolder> {
    private List<Voucher> listVoucher;
    private Context context;
 //   private UpdateAndHiddenDrink listener;
    VoucherOnItemClick listener;
    public VoucherAdapter(Context context,List<Voucher> listVoucher,VoucherOnItemClick listener)
    {
        this.context = context;
        this.listVoucher = listVoucher;
        this.listener =listener;
    }
    @NonNull
    @Override
    public VoucherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_voucher,parent,false);
        return new VoucherAdapter.ViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherAdapter.ViewHolder holder, int position) {
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
