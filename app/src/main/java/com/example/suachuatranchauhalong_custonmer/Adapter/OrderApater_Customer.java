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

import com.example.suachuatranchauhalong_custonmer.Activity.ActivityOrderDetail;
import com.example.suachuatranchauhalong_custonmer.Activity.ActivityOrderDetail_MyOrder_Customer;
import com.example.suachuatranchauhalong_custonmer.Object.Order;
import com.example.suachuatranchauhalong_custonmer.R;

import java.util.List;

public class OrderApater_Customer extends RecyclerView.Adapter<OrderApater_Customer.ViewHolder> {
    private List<Order> listOrder;
    private Context context;
    public OrderApater_Customer(List<Order> listOrder, Context context ) {
        this.listOrder = listOrder;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderApater_Customer.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_customer,parent,false);
        return new OrderApater_Customer.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull OrderApater_Customer.ViewHolder holder, final int position) {
        final Order order = listOrder.get(position);
        holder.txtMaOrder.setText(order.getIdOrder());
        holder.txtPriceInOrder.setText(""+ order.getPrice() +"đ");
        holder.txtMaOrder.setText("#"+order.getIdOrder());
        holder.txtMount.setText("Số lượng : " + order.getMount());
        if(order.getStatus()==0)
        {
            holder.txtStatus.setText("Trạng thái : Đã hủy" );
        }
        if(order.getStatus()==1)
        {
            holder.txtStatus.setText("Trạng thái : Chờ xác nhận" );
        }
        if(order.getStatus()==2)
        {
            holder.txtStatus.setText("Trạng thái : Đã xác nhận" );
        }
        if(order.getStatus()==3)
        {
            holder.txtStatus.setText("Trạng thái : Đang chuẩn bị đồ" );
        }
        if(order.getStatus()==4)
        {
            holder.txtStatus.setText("Trạng thái : Đang giao hàng" );
        }
        if(order.getStatus()==5)
        {
            holder.txtStatus.setText("Trạng thái : Đã giao hàng" );
        }
        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityOrderDetail_MyOrder_Customer.class);
                intent.putExtra("idOrderSendActivityOrderDetailOfCustomer_MyOrder",order.getIdOrder().toString());
                intent.putExtra("idCustomerSendActivityOrderDetailOfCustomer_MyOrder",order.getIdCustomer().toString());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return listOrder.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgDrinkInOrder;
        TextView txtMaOrder,txtStatus,txtMount,txtPriceInOrder;
        Button btnDetail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDrinkInOrder = (ImageView) itemView.findViewById(R.id.itemOrderCustomer_imgOrder);
//            frameStatusConfirmed = (FrameLayout) itemView.findViewById(R.id.item_ordershipping_frameStatusConfirmed);
//            frameStatusSetUpDrink = (FrameLayout) itemView.findViewById(R.id.item_ordershipping_frameStatusSetUpDrink);
            txtMaOrder = (TextView) itemView.findViewById(R.id.itemOrderCustomer_txtMaOrder);
            txtPriceInOrder = (TextView) itemView.findViewById(R.id.itemOrderCustomer_txtPriceOrder);
            txtStatus = (TextView) itemView.findViewById(R.id.itemOrderCustomer_txtStatusOrder);
            txtMount = (TextView) itemView.findViewById(R.id.itemOrderCustomer_txtMountOrder);
            btnDetail = (Button) itemView.findViewById(R.id.itemOrderCustomer_btnDetail);
        }
    }
}