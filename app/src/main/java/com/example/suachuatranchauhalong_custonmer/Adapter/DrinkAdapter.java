package com.example.suachuatranchauhalong_custonmer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suachuatranchauhalong_custonmer.Activity.DrinkDetail;
import com.example.suachuatranchauhalong_custonmer.InterfaceOnClick.UpdateAndHiddenDrink;
import com.example.suachuatranchauhalong_custonmer.Object.Customer;
import com.example.suachuatranchauhalong_custonmer.Object.Drink;
import com.example.suachuatranchauhalong_custonmer.R;
import com.example.suachuatranchauhalong_custonmer.Object.Drink;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.ViewHolder> {
    private List<Drink> listDrink;
    private Context context;
    private UpdateAndHiddenDrink listener;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    public DrinkAdapter(Context context,List<Drink> listDrink,UpdateAndHiddenDrink listener)
    {
        this.context = context;
        this.listDrink = listDrink;
        this.listener =listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drink,parent,false);
        return new ViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        initReferencesObject();
        checkAdmin(holder);
        final   Drink drink = listDrink.get(position);
        Picasso.get()
                .load(drink.getImgUriDrink())
                .fit()
                .into(holder.imgDrink);
         //  Picasso.with(context).load(mb.getPhotoURL()).into(holder.imgFace);
         //  holder.imgDrink.setImageResource(drink.getImageUri());
            holder.txtNameDrink.setText(drink.getNameDrink());
            holder.txtPriceDrink.setText("Giá: " + drink.getPriceDrink()+"đ");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DrinkDetail.class);
                    intent.putExtra("IdDrink",drink.getIdDrink());
                    context.startActivity(intent);
                }
            });
            holder.imgUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.update(drink.getIdDrink(),drink.getIdMenuDrink());
                }
            });
        holder.imgHidden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.hidden(drink.getIdDrink(),drink.getIdMenuDrink());
            }
        });
    }
    private void initReferencesObject() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        //   intent = getIntent();
        //    listenerIdDrink = new ListenerIdDrink();
        // Toast.makeText(this, "" + intent.getStringExtra("IDMenuDrink"), Toast.LENGTH_SHORT).show();
    }
    private void checkAdmin(final ViewHolder viewHolder) {
        databaseReference.child("ListCustomer").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Customer customer = dataSnapshot.getValue(Customer.class);
                if(customer.getPermission().equals("admin"))
                {
                    viewHolder.imgUpdate.setVisibility(View.VISIBLE);
                    viewHolder.imgHidden.setVisibility(View.VISIBLE);

                    //btnUpdate.setVisibility(View.VISIBLE);
//                    txtName.setText(mb.getName());
//                    Picasso.with(TrangChuActivity.this).load(mb.getPhotoURL()).into(imgAdmin);
//                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public int getItemCount() {
        int count = 0 ;
        try {
            count =  listDrink.size();
        }catch (NullPointerException ex)
        {

        }
        return count;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgDrink,imgUpdate,imgHidden;
        TextView txtNameDrink,txtPriceDrink;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDrink = (ImageView) itemView.findViewById(R.id.itemDrink_imageDrink);
            txtNameDrink = (TextView) itemView.findViewById(R.id.itemDrink_txtNameDrink);
            txtPriceDrink = (TextView) itemView.findViewById(R.id.itemDrink_txtPriceDrink);
            imgUpdate = (ImageView) itemView.findViewById(R.id.itemDrink_imgUpdate);
            imgHidden = (ImageView) itemView.findViewById(R.id.itemDrink_imgHidden);
        }
    }
}
