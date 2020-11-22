package com.example.suachuatranchauhalong_custonmer.Fagment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.suachuatranchauhalong_custonmer.Activity.ActivityListVoucherOfCustomer;
import com.example.suachuatranchauhalong_custonmer.Activity.ActivityRedeenPoint;
import com.example.suachuatranchauhalong_custonmer.Activity.ActivityTutorialRedeenPoint;
import com.example.suachuatranchauhalong_custonmer.Adapter.FragmentAccountAdapter;
import com.example.suachuatranchauhalong_custonmer.Adapter.FragmentPointAdapter;
import com.example.suachuatranchauhalong_custonmer.InterfaceOnClick.ListenerIdFragmentPoint;
import com.example.suachuatranchauhalong_custonmer.Object.Customer;
import com.example.suachuatranchauhalong_custonmer.Object.FragmentAccount;
import com.example.suachuatranchauhalong_custonmer.Object.FragmentPoint;
import com.example.suachuatranchauhalong_custonmer.Object.ListenerCheckAdmin;
import com.example.suachuatranchauhalong_custonmer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Fragment_Point extends Fragment implements ListenerIdFragmentPoint {
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    View convertiew;
    ViewHolder holder = null;
    ListenerCheckAdmin listenerCheckAdmin;
    FragmentPointAdapter fragmentPointAdapter;
    ArrayList<FragmentPoint> fragmentPointArrayList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(convertiew==null)
        {
            convertiew = inflater.inflate(R.layout.activity_fragment__point,container,false);
            addControls();

        }
        else
        {
            holder = (ViewHolder) convertiew.getTag();
        }
        initReferenceObject();
        checkAdmin();
        initData();
        addEvents();
        return convertiew;
    }
  //  Intent intent;
    @Override
    public void onItemClick(FragmentPoint fragmentPoint) {
         if(fragmentPoint.getId()==1)
         {
             Intent intent1 = new Intent(getActivity(), ActivityRedeenPoint.class);
             startActivity(intent1);
         }
        else if(fragmentPoint.getId()==2)
        {
            Intent intent2 = new Intent(getActivity(), ActivityListVoucherOfCustomer.class);
            startActivity(intent2);
        }
        else if(fragmentPoint.getId()==3)
        {
            Intent intent3 = new Intent(getActivity(), ActivityTutorialRedeenPoint.class);
            startActivity(intent3);
        }
    }

    public class ViewHolder
    {
        TextView txtThongBao,txtPoint;
        LinearLayout linearContent;
        SeekBar seekBarPoint;
        RecyclerView recyclerViewFragmentPoint;
    }

    private void initReferenceObject()
    {
        listenerCheckAdmin = new ListenerCheckAdmin();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        //String uid = firebaseUser.getUid().toString();
    }
    private void checkAdmin() {
               if(listenerCheckAdmin.getCheck()==1)
               {
                   holder.linearContent.setVisibility(View.GONE);
                   holder.txtThongBao.setVisibility(View.VISIBLE);
               }
               else
               {
                   holder.linearContent.setVisibility(View.VISIBLE);
                   holder.txtThongBao.setVisibility(View.GONE);
               }

    }

    public void addControls()
    {
        holder = new ViewHolder();
        holder.linearContent = (LinearLayout) convertiew.findViewById(R.id.FragmentPoint_frameContent);
        holder.txtThongBao = (TextView) convertiew.findViewById(R.id.FragmentPoint_txtThongBao);
        holder.recyclerViewFragmentPoint = (RecyclerView) convertiew.findViewById(R.id.FragmentPoint_recycleViewPoint);
        convertiew.setTag(holder);
    }
    private void initData()
    {
        fragmentPointArrayList = new ArrayList<>();
        fragmentPointArrayList.add(new FragmentPoint(1 ,R.drawable.ic_baseline_person_24_fragmentaccount,"Đổi điểm thưởng",R.drawable.ic_baseline_navigate_next_24));
        fragmentPointArrayList.add(new FragmentPoint(2,R.drawable.ic_baseline_motorcycle_24,"Voucher của bạn",R.drawable.ic_baseline_navigate_next_24));
        //fragmentPointArrayList.add(new FragmentPoint(3,R.drawable.ic_baseline_local_shipping_24,"Đơn hàng của tôi",R.drawable.ic_baseline_navigate_next_24));
        fragmentPointArrayList.add(new FragmentPoint(3 ,R.drawable.ic_baseline_lock_24_fragmentaccount,"Hướng dẫn đổi điểm thưởng",R.drawable.ic_baseline_navigate_next_24));
        //  fragmentAccountArrayList.add(new FragmentAccount(5 ,R.drawable.ic_baseline_info_24_fragmentaccount,"Chính sách hỗ trợ",R.drawable.ic_baseline_navigate_next_24));
     //   fragmentPointArrayList.add(new FragmentPoint(5 ,R.drawable.ic_baseline_archive_24,"Đăng xuất",R.drawable.ic_baseline_navigate_next_24));
        DividerItemDecoration dividerHorizontal =
                new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        holder.recyclerViewFragmentPoint.addItemDecoration(dividerHorizontal);
        fragmentPointAdapter = new FragmentPointAdapter(getActivity(),fragmentPointArrayList,this);
        holder.recyclerViewFragmentPoint.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true));
        holder.recyclerViewFragmentPoint.setLayoutManager(new LinearLayoutManager(getActivity()));
        holder.recyclerViewFragmentPoint.setAdapter(fragmentPointAdapter);
    }
    public void addEvents()
    {

    }
}