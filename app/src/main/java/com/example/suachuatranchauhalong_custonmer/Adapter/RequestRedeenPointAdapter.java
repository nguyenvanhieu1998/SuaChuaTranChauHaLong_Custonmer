package com.example.suachuatranchauhalong_custonmer.Adapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suachuatranchauhalong_custonmer.Object.Customer;
import com.example.suachuatranchauhalong_custonmer.Object.Order;
import com.example.suachuatranchauhalong_custonmer.Object.Voucher;
import com.example.suachuatranchauhalong_custonmer.Object.Voucher_Customer;
import com.example.suachuatranchauhalong_custonmer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class RequestRedeenPointAdapter extends RecyclerView.Adapter<RequestRedeenPointAdapter.ViewHolder> {
    private List<Voucher_Customer> listVoucherCustomer;
    private Context context;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    public RequestRedeenPointAdapter(List<Voucher_Customer> listVoucherCustomer,Context context ) {
        this.listVoucherCustomer = listVoucherCustomer;
        this.context = context;
    }

    @NonNull
    @Override
    public RequestRedeenPointAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_confirm_redeenpoint,parent,false);
        return new RequestRedeenPointAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RequestRedeenPointAdapter.ViewHolder holder, int position) {
        initReference();
        final Voucher_Customer voucher_customer = listVoucherCustomer.get(position);

        holder.txtIdRequestRedeenPoint.setText(voucher_customer.getIdVoucherCustomer());
        databaseReference.child("ListCustomer").child(voucher_customer.getIdCustomer()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Customer customer = dataSnapshot.getValue(Customer.class);
                holder.txtNameCustomer.setText(customer.getNameCustomer().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference.child("ListVoucher").child(voucher_customer.getIdVoucher()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Voucher voucher = dataSnapshot.getValue(Voucher.class);
                holder.txtNameVoucher.setText("Giảm giá "+ voucher.getPricePromotion() + "% cho đơn hàng "+ voucher.getPriceApply() +"đ");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
    private void initReference()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getInstance().getCurrentUser();
    }
    @Override
    public int getItemCount() {
        return listVoucherCustomer.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNameCustomer,txtIdRequestRedeenPoint,txtNameVoucher;
        ImageView imgVoucher;
        Button btnDuyet,btnCancel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameCustomer = (TextView) itemView.findViewById(R.id.itemRequestRedeenPoint_txtNameCustomer);
            txtIdRequestRedeenPoint = (TextView) itemView.findViewById(R.id.itemRequestRedeenPoint_txtIdRequestRedeenPoint);
            txtNameVoucher = (TextView) itemView.findViewById(R.id.itemRequestRedeenPoint_txtNameVoucher);
            btnDuyet = (Button) itemView.findViewById(R.id.itemRequestRedeenPoint_btnDuyet);
            btnCancel = (Button) itemView.findViewById(R.id.itemRequestRedeenPoint_btnCancel);
            imgVoucher = (ImageView) itemView.findViewById(R.id.itemRequestRedeenPoint_imgVoucher);
//            txtMaOrder = (TextView) itemView.findViewById(R.id.item_ordershipped_txtIDOrder);
//            txtPriceInOrder = (TextView) itemView.findViewById(R.id.item_ordershipped_txtPriceOrder);
        }
    }
}
