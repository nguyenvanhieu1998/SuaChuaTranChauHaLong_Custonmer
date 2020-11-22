package com.example.suachuatranchauhalong_custonmer.Fagment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.suachuatranchauhalong_custonmer.Activity.InformationUserCurrent;
import com.example.suachuatranchauhalong_custonmer.Activity.ListOrderOfCustomerCurrent;
import com.example.suachuatranchauhalong_custonmer.Activity.Login_Customer;
import com.example.suachuatranchauhalong_custonmer.Activity.OrderCurrentOfCustomer;
import com.example.suachuatranchauhalong_custonmer.Activity.ActivityChangePassword;
import com.example.suachuatranchauhalong_custonmer.Adapter.FragmentAccountAdapter;
import com.example.suachuatranchauhalong_custonmer.InterfaceOnClick.ListenerIdFragmentAccount;
import com.example.suachuatranchauhalong_custonmer.Object.Customer;
import com.example.suachuatranchauhalong_custonmer.Object.FragmentAccount;
import com.example.suachuatranchauhalong_custonmer.Object.ListenerCheckAdmin;
import com.example.suachuatranchauhalong_custonmer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Fragment_Account extends Fragment implements ListenerIdFragmentAccount {
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    View convertiew;
    ViewHolder holder = null;
    ArrayList<FragmentAccount> fragmentAccountArrayList;
    FragmentAccountAdapter fragmentAccountAdapter;
    ListenerCheckAdmin listenerCheckAdmin;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(convertiew==null)
        {
            convertiew = inflater.inflate(R.layout.activity_fragment__account,container,false);
            addControls();

        }
        else
        {
            holder = (ViewHolder) convertiew.getTag();
        }

        initReferenceObject();
        addControls();
        if(listenerCheckAdmin.getCheck()==1)
        {
            holder.linearContent.setVisibility(View.GONE);
            holder.linearInformationCustomer.setVisibility(View.GONE);
            holder.linearSetting.setVisibility(View.GONE);
            holder.txtThongBao.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.linearContent.setVisibility(View.VISIBLE);
            holder.linearInformationCustomer.setVisibility(View.VISIBLE);
            holder.linearSetting.setVisibility(View.VISIBLE);
            holder.txtThongBao.setVisibility(View.GONE);
        }
//        if(checkAdmin()==true)
//        {
//            holder.linearContent.setVisibility(View.GONE);
//            holder.linearInformationCustomer.setVisibility(View.GONE);
//            holder.linearSetting.setVisibility(View.GONE);
//            holder.txtThongBao.setVisibility(View.VISIBLE);
//
//        }
//        else
//        {
//            holder.linearContent.setVisibility(View.VISIBLE);
//            holder.linearInformationCustomer.setVisibility(View.VISIBLE);
//            holder.linearSetting.setVisibility(View.VISIBLE);
//            holder.txtThongBao.setVisibility(View.GONE);
//        }
        initData();
        //checkAdmin();
        setUserCurrent();
        addEvents();
        return convertiew;
    }
    private void initReferenceObject()
    {
        listenerCheckAdmin = new ListenerCheckAdmin();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        //String uid = firebaseUser.getUid().toString();
    }
    Intent intent;
    @Override
    public void onItemClick(FragmentAccount fragmentAccount) {
        if(fragmentAccount.getId()==1)
        {
            intent = new Intent(getActivity(), InformationUserCurrent.class);
            startActivity(intent);
        }
        if(fragmentAccount.getId()==2)
        {
            intent = new Intent(getActivity(), OrderCurrentOfCustomer.class);
            startActivity(intent);
        }
        if(fragmentAccount.getId()==3)
        {
            intent = new Intent(getActivity(), ListOrderOfCustomerCurrent.class);
            startActivity(intent);
        }
        if(fragmentAccount.getId()==4)
        {
            intent = new Intent(getActivity(), ActivityChangePassword.class);
            startActivity(intent);
        }
        if(fragmentAccount.getId()==5)
        {
            DialogLogout();
        }
    }
    public void DialogLogout()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Thông báo");
        alert.setIcon(R.drawable.ic_baseline_info_24);
        alert.setMessage("Bạn có chắc muốn đăng xuất ? ");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getActivity(), Login_Customer.class);
                startActivity(intent);
                firebaseAuth.signOut();
            }
        });
        alert.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog a = alert.create();
        a.show();
    }

    public class ViewHolder
    {
        LinearLayout linearInformationCustomer,linearSetting,linearContent;
        RecyclerView recyclerFragmentAccount;
        CircleImageView imgUserCurrent;
        TextView txtNameUserCurrent,txtPointUserCurrent,txtMountOrderUserCurrent,txtThongBao;
    }
    public void addControls()
    {
        holder = new ViewHolder();
        holder.linearContent = (LinearLayout) convertiew.findViewById(R.id.FragmentAccount_linearContent);
        holder.txtThongBao = (TextView) convertiew.findViewById(R.id.FragmentAccount_txtThongBao);
        holder.linearInformationCustomer = (LinearLayout) convertiew.findViewById(R.id.FragmentAccount_frameInformationCustomer);
        holder.linearSetting = (LinearLayout) convertiew.findViewById(R.id.FragmentAccount_frameSetting);
        holder.recyclerFragmentAccount = (RecyclerView) convertiew.findViewById(R.id.FragmentAccount_recycleFragmentAccount);
        holder.imgUserCurrent = (CircleImageView) convertiew.findViewById(R.id.FragmentAccount_imgUserCurrent);
        holder.txtNameUserCurrent = (TextView) convertiew.findViewById(R.id.FragmentAccount_txtNameUserCurrent);
        holder.txtPointUserCurrent = (TextView) convertiew.findViewById(R.id.FragmentAccount_txtPointUserCurrent);
        holder.txtMountOrderUserCurrent = (TextView) convertiew.findViewById(R.id.FragmentAccount_txtMountOrderUserCurrent);
        convertiew.setTag(holder);
    }
//    static boolean checkAdmin=false;
//    private void checkAdmin() {
//                databaseReference.child("ListCustomer").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        Customer customer = dataSnapshot.getValue(Customer.class);
//                        if(customer.getPermission().equals("admin"))
//                        {
//                         //   checkAdmin = true;
////                            holder.linearContent.setVisibility(View.GONE);
////                            holder.linearInformationCustomer.setVisibility(View.GONE);
////                            holder.linearSetting.setVisibility(View.GONE);
////                            holder.txtThongBao.setVisibility(View.VISIBLE);
//                           // linearDatHang.setVisibility(View.INVISIBLE);
////                    txtName.setText(mb.getName());
////                    Picasso.with(TrangChuActivity.this).load(mb.getPhotoURL()).into(imgAdmin);
//                            //    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//                            //   drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//                        }
//
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
//             //   return checkAdmin;
//
//    }
    private void initData()
    {
        fragmentAccountArrayList = new ArrayList<>();
        fragmentAccountArrayList.add(new FragmentAccount(1 ,R.drawable.ic_baseline_person_24_fragmentaccount,"Thông tin cá nhân",R.drawable.ic_baseline_navigate_next_24));
        fragmentAccountArrayList.add(new FragmentAccount(2,R.drawable.ic_baseline_motorcycle_24,"Đơn hàng hiện tại",R.drawable.ic_baseline_navigate_next_24));
        fragmentAccountArrayList.add(new FragmentAccount(3,R.drawable.ic_baseline_local_shipping_24,"Đơn hàng của tôi",R.drawable.ic_baseline_navigate_next_24));
        fragmentAccountArrayList.add(new FragmentAccount(4 ,R.drawable.ic_baseline_lock_24_fragmentaccount,"Đổi mật khẩu",R.drawable.ic_baseline_navigate_next_24));
      //  fragmentAccountArrayList.add(new FragmentAccount(5 ,R.drawable.ic_baseline_info_24_fragmentaccount,"Chính sách hỗ trợ",R.drawable.ic_baseline_navigate_next_24));
        fragmentAccountArrayList.add(new FragmentAccount(5 ,R.drawable.ic_baseline_archive_24,"Đăng xuất",R.drawable.ic_baseline_navigate_next_24));
        DividerItemDecoration dividerHorizontal =
                new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        holder.recyclerFragmentAccount.addItemDecoration(dividerHorizontal);
        fragmentAccountAdapter = new FragmentAccountAdapter(getActivity(),fragmentAccountArrayList,this);
        holder.recyclerFragmentAccount.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true));
        holder.recyclerFragmentAccount.setLayoutManager(new LinearLayoutManager(getActivity()));
        holder.recyclerFragmentAccount.setAdapter(fragmentAccountAdapter);
    }
    private void setUserCurrent() {
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
    }
    public void addEvents()
    {

    }
}