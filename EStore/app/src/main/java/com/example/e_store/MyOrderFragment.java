package com.example.e_store;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrderFragment extends Fragment {
    private RecyclerView myOrderRecyclerView;



    public MyOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_my_order, container, false);
        myOrderRecyclerView = view.findViewById(R.id.myOrder_recycler_View);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myOrderRecyclerView.setLayoutManager(layoutManager);

        List<MyOrderItemModel>myOrderItemModelList = new ArrayList<>();
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.phone3, 1, "Google Pixel-5", "Delivered on 12th Jan,19"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.phone2, 2, "Google Pixel-5", "Delivered on 12th Jan,19"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.phone1, 3, "Google Pixel-5", "Cancelled"));

        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.phone3, 4, "Google Pixel-5", "Delivered on 12th Jan,19"));

        MyOrderAdapter myOrderAdapter = new MyOrderAdapter(myOrderItemModelList);
        myOrderRecyclerView.setAdapter(myOrderAdapter);
        myOrderAdapter.notifyDataSetChanged();
        return  view;
    }

}
