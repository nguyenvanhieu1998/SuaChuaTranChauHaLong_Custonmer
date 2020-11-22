package com.example.suachuatranchauhalong_custonmer.InterfaceOnClick;

import com.example.suachuatranchauhalong_custonmer.Object.Customer;
import com.example.suachuatranchauhalong_custonmer.Object.MenuDrink;
import com.example.suachuatranchauhalong_custonmer.Object.News;
import com.example.suachuatranchauhalong_custonmer.Object.OrderDetail;
import com.example.suachuatranchauhalong_custonmer.Object.Shipper;

public interface OnItemClickListener {
    void onItemClickListener(MenuDrink menuDrink);
    void onItemClickListener(Customer customer);
    void onItemClickListener(Shipper shipper);
    void onItemClickListener(OrderDetail orderDetail);
}
