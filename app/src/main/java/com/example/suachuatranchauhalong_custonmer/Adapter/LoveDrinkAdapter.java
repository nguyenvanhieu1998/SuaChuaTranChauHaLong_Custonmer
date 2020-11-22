package com.example.suachuatranchauhalong_custonmer.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suachuatranchauhalong_custonmer.Activity.ActivityCart;
import com.example.suachuatranchauhalong_custonmer.Object.Drink;
import com.example.suachuatranchauhalong_custonmer.Object.ListenerIdDrink2;
import com.example.suachuatranchauhalong_custonmer.Object.News;
import com.example.suachuatranchauhalong_custonmer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LoveDrinkAdapter extends RecyclerView.Adapter<LoveDrinkAdapter.ViewHolder> {
    private List<Drink> listDrink;
    private Context context;
    ListenerIdDrink2 listenerIdDrink2;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    public LoveDrinkAdapter(List<Drink> listDrink, Context context ) {
        this.listDrink = listDrink;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lovedrink,parent,false);
        return new LoveDrinkAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        initReferenceObject();
        final Drink drink = listDrink.get(position);
   //     listenerIdDrink2 = new ListenerIdDrink2();
      //  holder.imgDrink.setImageResource(drink.getImgUriDrink());
                Picasso.get()
                .load(drink.getImgUriDrink())
                .fit()
                .into(holder.imgDrink);
        holder.txtNameDrink.setText(drink.getNameDrink());
        holder.txtPriceDrink.setText("Giá : "+drink.getPriceDrink()+"đ");
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUpdateStatusLoveDrink(drink.getIdDrink().toString());
            }
        });
    }
    AlertDialog a;
    private void initReferenceObject()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        //String uid = firebaseUser.getUid().toString();
    }
    public void DialogUpdateStatusLoveDrink(final String idDrink) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Thông báo");
        alert.setIcon(R.drawable.ic_baseline_info_24);
        alert.setMessage("Bạn muốn xóa đồ uống này khỏi danh sách yêu thích ? ");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Intent intent = new Intent(getActivity(), Login_Customer.class);
//                startActivity(intent);
                updateStatusLoveDrink(idDrink);
                //  Toast.makeText(ActivityCart.this, "Bạn đã đặt hàng thành công", Toast.LENGTH_SHORT).show();
            }
        });
        alert.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        a = alert.create();
        a.show();
    }

    private void updateStatusLoveDrink(String idDrink) {
//        databaseReference.child("ListCustomer").child(firebaseUser.getUid().toString()).
//                child("ListLoveDrink").child(idDrink).
//                child("IdDrink").setValue(intent.getStringExtra("IdDrink").toString());
        databaseReference.child("ListCustomer").child(firebaseUser.getUid().toString()).
                child("ListLoveDrink").child(idDrink).child("statusLove").setValue(0);
        Toast.makeText(context, "Bạn đã xóa thành công", Toast.LENGTH_SHORT).show();
        a.dismiss();
    }

    @Override
    public int getItemCount() {
        return listDrink.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgDrink,imgDelete;
        TextView txtNameDrink,txtPriceDrink;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDelete = (ImageView) itemView.findViewById(R.id.itemLoveDrink_imgDelete);
            imgDrink = (ImageView) itemView.findViewById(R.id.itemLoveDrink_imgDrink);
            txtNameDrink = (TextView) itemView.findViewById(R.id.itemLoveDrink_txtNameDrink);
            txtPriceDrink = (TextView) itemView.findViewById(R.id.itemLoveDrink_txtPriceDink);
        }
    }
}
