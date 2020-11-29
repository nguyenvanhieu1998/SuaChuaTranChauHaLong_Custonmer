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
import com.example.suachuatranchauhalong_custonmer.Object.MenuDrink;
import com.example.suachuatranchauhalong_custonmer.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {
    private List<Customer> listCustomer;
    private Context context;
    private OnItemClickListener listener;
    public CustomerAdapter(Context context, List<Customer> listCustomer,OnItemClickListener listener)
    {
        this.context = context;
        this.listCustomer = listCustomer;
        this.listener = listener;
    }
    @NonNull
    @Override
    public CustomerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer,parent,false);
        return new CustomerAdapter.ViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerAdapter.ViewHolder holder, int position) {
        final Customer customer = listCustomer.get(position);
        Picasso.get()
                .load(customer.getAvtUriCustomer())
                .fit()
                .into(holder.imgCustomer);
//        //  Picasso.with(context).load(mb.getPhotoURL()).into(holder.imgFace);
//        //  holder.imgDrink.setImageResource(drink.getImageUri());
        holder.txtNameCustomer.setText(customer.getNameCustomer().toString());
        if(customer.isSexCustomer()==true)
        {
            holder.txtSexCustomer.setText("Giới tính : Nam");
        }
        else
        {
            holder.txtSexCustomer.setText("Giới tính : Nữ");
        }
     //   holder.txtOrderOfCustomer.setText("Đã mua : "+ customer.getMountOrder());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClickListener(customer);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCustomer.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgCustomer;
        TextView txtNameCustomer,txtSexCustomer,txtOrderOfCustomer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCustomer = (CircleImageView) itemView.findViewById(R.id.itemCustomer_imageCustomer);
            txtSexCustomer = (TextView) itemView.findViewById(R.id.itemCustomer_txtSexCustomer);
            txtNameCustomer = (TextView) itemView.findViewById(R.id.itemCustomer_txtNameCustomer);
          //  txtOrderOfCustomer = (TextView) itemView.findViewById(R.id.itemCustomer_txtOrderOfCustomer);
        }
    }
}
