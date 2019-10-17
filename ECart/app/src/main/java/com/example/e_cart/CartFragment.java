package com.example.e_cart;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {
    private RecyclerView cartItemsRecyclerView;
    private Button contineBtn;
    private  Dialog LoadingDialog;
    private TextView totalAmount;
    public  static  CartAdapter cartAdapter;




    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_cart, container, false);
        totalAmount = view.findViewById(R.id.total_cart_price);
        LoadingDialog = new Dialog(getContext());
        LoadingDialog.setContentView(R.layout.loading_progress_dialog);
        LoadingDialog.setCancelable(false);
        LoadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        LoadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
        LoadingDialog.show();

        cartItemsRecyclerView = view.findViewById(R.id.cart_itmes_fragment);
        contineBtn = view.findViewById(R.id.cart_continue_btn);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartItemsRecyclerView.setLayoutManager(linearLayoutManager);

        if(DBQueries.cartItemModelList.size() == 0)
        {
            DBQueries.cartList.clear();
            DBQueries.loadCart(getContext(),LoadingDialog,true ,new TextView(getContext()), totalAmount);
        }else {
            if(DBQueries.cartItemModelList.get(DBQueries.cartItemModelList.size()-1).getType()==CartItemModel.TOTAL_AMOUNT){
                LinearLayout parent = (LinearLayout) totalAmount.getParent().getParent();
                parent.setVisibility(View.VISIBLE);
            }
            LoadingDialog.dismiss();
        }
        cartAdapter = new CartAdapter(DBQueries.cartItemModelList, totalAmount, true);
        cartItemsRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();
        contineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeliveryActivity.cartItemModelList = new ArrayList<>();
                for(int i=0;i< DBQueries.cartItemModelList.size() ; i++)
                {
                    CartItemModel cartItemModel = DBQueries.cartItemModelList.get(i);
                    if(cartItemModel.isInStock())
                    {
                        DeliveryActivity.cartItemModelList.add(cartItemModel);

                    }
                }
                DeliveryActivity.cartItemModelList.add(new CartItemModel(CartItemModel.TOTAL_AMOUNT));
                LoadingDialog.show();
                if(DBQueries.addressModelList.size()==0)
                {
                    DBQueries.loadAddress(getContext(),LoadingDialog);
                }else{
                    LoadingDialog.dismiss();
                    Intent deliveryIntent = new Intent(getActivity(), DeliveryActivity.class);
                    startActivity(deliveryIntent);
                }
            }
        });
        return view;
    }

}
