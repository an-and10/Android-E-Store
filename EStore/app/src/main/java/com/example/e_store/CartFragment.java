package com.example.e_store;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {
    private RecyclerView cartItemsRecyclerView;
    private Button contineBtn;




    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_cart, container, false);

        cartItemsRecyclerView = view.findViewById(R.id.cart_itmes_fragment);
        contineBtn = view.findViewById(R.id.cart_continue_btn);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartItemsRecyclerView.setLayoutManager(linearLayoutManager);

        List<CartItemModel> cartItemModelList = new ArrayList<>();
        cartItemModelList.add(new CartItemModel(0,"Google Pixel", "Rs. 78000", "Rs. 89999", R.drawable.phone1,2,2,2,2 ));
        cartItemModelList.add(new CartItemModel(0,"CoolPad X-5", "Rs. 56000", "Rs. 39999", R.drawable.phone2,3,1,2,1 ));
        cartItemModelList.add(new CartItemModel(0,"Nokia Ms-23", "Rs. 78000", "Rs. 89999", R.drawable.phone1,2,2,2,2 ));
        cartItemModelList.add(new CartItemModel(1, "Price(3 items)", "Rs.120345", "Free", "Rs.120345", "45%"));

        CartAdapter cartAdapter = new CartAdapter(cartItemModelList);
        cartItemsRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();



        contineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AddAddress = new Intent(getContext(),AddAddressActivity.class);
                getContext().startActivity(AddAddress);
            }
        });
        return view;

    }

}
