package com.example.suachuatranchauhalong_custonmer.Fagment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.suachuatranchauhalong_custonmer.Activity.ActivityCart;
import com.example.suachuatranchauhalong_custonmer.Adapter.LoveDrinkAdapter;
import com.example.suachuatranchauhalong_custonmer.Adapter.NewAdapter;
import com.example.suachuatranchauhalong_custonmer.Object.Drink;
import com.example.suachuatranchauhalong_custonmer.Object.News;
import com.example.suachuatranchauhalong_custonmer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Fragment_LoveDrink extends Fragment {
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    Drink drink;
    LoveDrinkAdapter loveDrinkAdapter;
    List<Drink> arrayListDrink;
    View convertiew;
    ViewHolder holder = null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(convertiew==null)
        {
            convertiew = inflater.inflate(R.layout.activity_fragment__love_drink,container,false);
            addControls();

        }
        else
        {
            holder = (ViewHolder) convertiew.getTag();
        }
        initReferenceObject();
        addControls();
      //  addInitData();
       getListLoveDrink();
        addEvents();
        return convertiew;
    }
    private void initReferenceObject()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        //String uid = firebaseUser.getUid().toString();
    }
    public class ViewHolder
    {
        RecyclerView recyclerLoveDrink;
    }
    public void addControls()
    {
        holder = new ViewHolder();
        holder.recyclerLoveDrink = (RecyclerView) convertiew.findViewById(R.id.FragmentLoveDrink_RecyclerViewLoveDrink);
        convertiew.setTag(holder);
    }
    private void addInitData() {
//        String dateCreateDrink;
//        Calendar calen = Calendar.getInstance();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy");
//        dateCreateDrink = "" + simpleDateFormat.format(calen.getTime());

//        int orientation = LinearLayoutManager.HORIZONTAL; //Cuộn ngang
//        boolean reverse = false; //true thì bắt đầu từ phần tử cuối
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//        layoutManager.scrollToPosition(0);
        //holder.recyclerLoveDrink.setLayoutManager(layoutManager);
        //     holder.recyclerKhuyenMai.setAdapter(newAdapter);

    }
    private void getListLoveDrink()
    {
        arrayListDrink = new ArrayList<>();
    //    arrayListDrink.clear();

        databaseReference.child("ListCustomer").child(firebaseUser.getUid().toString()).
                child("ListLoveDrink").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                try{
                arrayListDrink.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    try {
                        String idDrink = dataSnapshot1.child("IdDrink").getValue().toString();
                        int status = Integer.parseInt(dataSnapshot1.child("statusLove").getValue().toString());
                        Log.d("idDrink",idDrink);
                        Log.d("statusLove",""+status);
                        if(status==1)
                        {
                            Log.d("idDrink1",""+idDrink);
                            //      Toast.makeText(getActivity(), "ID : " + idDrink + "status :" + status, Toast.LENGTH_SHORT).show();
                            databaseReference.child("ListDrink").child(idDrink).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot3) {
                                    Drink drink = dataSnapshot3.getValue(Drink.class);
                                    arrayListDrink.add(drink);
                                    loveDrinkAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }
                    }
                    catch (NullPointerException ex){

                    }


                }

            }
//                catch(NullPointerException ex){
//
//                }
//            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        DividerItemDecoration dividerHorizontal =
                new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);

        holder.recyclerLoveDrink.addItemDecoration(dividerHorizontal);
        holder.recyclerLoveDrink.setHasFixedSize(true);
        holder.recyclerLoveDrink.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true));
        holder.recyclerLoveDrink.setLayoutManager(new LinearLayoutManager(getContext()));
        loveDrinkAdapter = new LoveDrinkAdapter(arrayListDrink,getContext());
        holder.recyclerLoveDrink.setAdapter(loveDrinkAdapter);
    }
    public void addEvents()
    {

    }
}