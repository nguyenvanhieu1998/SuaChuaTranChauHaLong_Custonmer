package com.example.suachuatranchauhalong_custonmer.Fagment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suachuatranchauhalong_custonmer.Adapter.NewAdapter;
import com.example.suachuatranchauhalong_custonmer.Adapter.PromotionAdapter;
import com.example.suachuatranchauhalong_custonmer.FragmentDialog.FragmentDialogAddNewsForAdmin;
import com.example.suachuatranchauhalong_custonmer.InterfaceOnClick.OnItemClickListener;
import com.example.suachuatranchauhalong_custonmer.Object.Customer;
import com.example.suachuatranchauhalong_custonmer.Object.ListenerTypeNews;
import com.example.suachuatranchauhalong_custonmer.Object.News;
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
import java.util.ArrayList;
import java.util.Calendar;

public class Fragment_News extends Fragment implements View.OnClickListener {
    String url = "";
    View convertiew;
    ViewHolder holder = null;
    ArrayList<News> arrayListNews,arrayListPromotion;
    NewAdapter newAdapter;
    PromotionAdapter promotionAdapter;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(convertiew==null)
        {
            convertiew = inflater.inflate(R.layout.activity_fragment__news,container,false);
            addControls();

        }
        else
        {
            holder = (ViewHolder) convertiew.getTag();
        }
        initReferenceObject();
        addControls();
        addInitData();
       // reSizeImage();
        addEvents();
        checkAdmin();
        return convertiew;
    }
    ListenerTypeNews listenerTypeNews;
    @Override
    public void onClick(View view) {
        listenerTypeNews = new ListenerTypeNews();
        switch (view.getId())
        {
            case R.id.FragmentNews_imgAddNews :
                        listenerTypeNews.setTypeNews("News");
                        viewFragmentDialogAddForAdmin();
                break;
            case R.id.FragmentNews_imgAddPromotion :
                        listenerTypeNews.setTypeNews("Promotion");
                        viewFragmentDialogAddForAdmin();
                break;
        }
    }
    private void initReferenceObject()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        //String uid = firebaseUser.getUid().toString();
    }
    private void viewFragmentDialogAddForAdmin() {
                FragmentDialogAddNewsForAdmin fragmentDialog = new FragmentDialogAddNewsForAdmin();
                fragmentDialog.show(getFragmentManager(),"FragmentDialogAddNews");

    }

    public class ViewHolder
    {
        ImageView imgUserCurrent,imgNotification,imgTittle,imgAddNews,imgAddPromotion;
        TextView txtNameUserCurrent;
        RecyclerView recyclerNews,recyclerPromotion;
    }
//    public void reSizeImage()
//    {
//        Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(),
//                R.drawable.anhtintuc2);
//
//        int width = bitmapOrg.getWidth();
//        int height = bitmapOrg.getHeight();
//        int newWidth = 200;
//        int newHeight = 200;
//        float scaleWidth = ((float) newWidth) / width;
//        float scaleHeight = ((float) newHeight) / height;
//
//        // createa matrix for the manipulation
//        Matrix matrix = new Matrix();
//        // resize the bit map
//        matrix.postScale(scaleWidth, scaleHeight);
//        // rotate the Bitmap
//        matrix.postRotate(45);
//
//        // recreate the new Bitmap
//        Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0,
//                newWidth, newHeight, matrix, true);
//
//        // make a Drawable from Bitmap to allow to set the BitMap
//        // to the ImageView, ImageButton or what ever
//        BitmapDrawable bmd = new BitmapDrawable(resizedBitmap);
//        holder.imgTittle.setImageDrawable(bmd);
//
//    }
    public void addControls()
    {
        holder = new ViewHolder();
        holder.imgTittle = (ImageView) convertiew.findViewById(R.id.FragmentNews_imgTittle);
        holder.imgUserCurrent = (ImageView) convertiew.findViewById(R.id.FragmentNews_imgUserCurrent);
        holder.imgNotification = (ImageView) convertiew.findViewById(R.id.FragmentNews_imgNotification);
        holder.txtNameUserCurrent = (TextView) convertiew.findViewById(R.id.FragmentNews_txtNameUserCurrent);
        holder.recyclerNews = (RecyclerView) convertiew.findViewById(R.id.FragmentNews_recyclerNews);
        holder.recyclerPromotion = (RecyclerView) convertiew.findViewById(R.id.FragmentNews_recyclerKhuyenMai);
        holder.imgAddNews = (ImageView) convertiew.findViewById(R.id.FragmentNews_imgAddNews);
        holder.imgAddPromotion = (ImageView) convertiew.findViewById(R.id.FragmentNews_imgAddPromotion);
        convertiew.setTag(holder);
    }
        private void addInitData() {
        setNews();
        setPromotion();
        setUserCurrent();
       //     holder.recyclerKhuyenMai.setAdapter(newAdapter);

    }

    private void setUserCurrent() {
//        Thread thread1 = new Thread(){
//            @Override
//            public void run() {
//                super.run();
                Log.d("Id user current : ",firebaseUser.getUid().toString() );
                databaseReference.child("ListCustomer").child(firebaseUser.getUid().toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Customer customer = dataSnapshot.getValue(Customer.class);
                        Picasso.get()
                                .load(customer.getAvtUriCustomer())
                                .fit()
                                .into(holder.imgUserCurrent);
                        holder.txtNameUserCurrent.setText(customer.getNameCustomer());
                        Log.d("Url image : ",customer.getAvtUriCustomer());
                        Log.d("Name user : ",customer.getNameCustomer());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
//            }
//        };
//        thread1.start();

    }
    private void checkAdmin() {
//        Thread thread2 = new Thread(){
//            @Override
//            public void run() {
//                super.run();
                databaseReference.child("ListCustomer").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Customer customer = dataSnapshot.getValue(Customer.class);
                        if(customer.getPermission().equals("admin"))
                        {
                            holder.imgAddNews.setVisibility(View.VISIBLE);
                            holder.imgAddPromotion.setVisibility(View.VISIBLE);
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
           // }
       // };
//        thread2.start();
    }
    String dateCreateNews;
    private String getDateNow()
    {
//        Thread thread3= new Thread(){
//            @Override
//            public void run() {
//                super.run();
                Calendar calen = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy");
                dateCreateNews = "" + simpleDateFormat.format(calen.getTime());
//            }
//        };
//        thread3.start();
        return dateCreateNews;
    }
    private void setPromotion() {
//        Thread thread4 = new Thread(){
//            @Override
//            public void run() {
//                super.run();
                getDateNow();
                arrayListPromotion = new ArrayList<>();
                databaseReference.child("ListNews").child("Promotion").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        arrayListPromotion.clear();
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                        {
                            News news = dataSnapshot1.getValue(News.class);
                            if(news.getStatus()==1)
                            {
                                arrayListPromotion.add(news);
                            }

                        }
                        promotionAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                holder.recyclerPromotion.setHasFixedSize(true);
                int orientation = LinearLayoutManager.HORIZONTAL;
                boolean reverse = false;
                LinearLayoutManager layoutManager =
                        new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                layoutManager.scrollToPosition(0);
                holder.recyclerPromotion.setLayoutManager(layoutManager);
                promotionAdapter = new PromotionAdapter(getActivity(),arrayListPromotion);
                holder.recyclerPromotion.setAdapter(promotionAdapter);
//            }
//        };
//        thread4.start();

    }

    private void setNews() {
//        Thread thread5 = new Thread(){
//            @Override
//            public void run() {
//                super.run();
                databaseReference = FirebaseDatabase.getInstance().getReference();
                arrayListNews = new ArrayList<>();
                databaseReference.child("ListNews").child("News").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        arrayListNews.clear();
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                        {
                            News news = dataSnapshot1.getValue(News.class);
                            if(news.getStatus()==1)
                            {
                                arrayListNews.add(news);
                            }
                            //  name = news.getTittle();
                            //    Toast.makeText(getActivity() , ""+  name, Toast.LENGTH_SHORT).show();

                        }
                        newAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                newAdapter = new NewAdapter(getActivity(),arrayListNews);
                holder.recyclerNews.setAdapter(newAdapter);
                holder.recyclerNews.setHasFixedSize(true);
                int orientation = LinearLayoutManager.HORIZONTAL; //Cuộn ngang
                boolean reverse = false; //true thì bắt đầu từ phần tử cuối
                LinearLayoutManager layoutManager =
                        new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                layoutManager.scrollToPosition(0);//Thiết lập phần tử mặc định nếu muốn
// Gắn vào RecylerView
                holder.recyclerNews.setLayoutManager(layoutManager);
           // }
//        };
//        thread5.start();

    }

    public void addEvents()
    {
       holder.imgAddNews.setOnClickListener(this);
       holder.imgAddPromotion.setOnClickListener(this);
    }
}