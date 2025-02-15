package com.example.e_cart;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class  ProductSpecificationsFragment extends Fragment {

    private RecyclerView productSpecificationRecyclerView;
    public  List<ProductSpecificationModel> productSpecificationModelList;



    public ProductSpecificationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =  inflater.inflate(R.layout.fragment_product_specification, container, false);
        productSpecificationRecyclerView = view.findViewById(R.id.product_specification_recycler_view);

        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        productSpecificationRecyclerView.setLayoutManager(linearLayoutManager);


//        productSpecificationModelList.add(new ProductSpecificationModel("RAM", "4GB"));
//        productSpecificationModelList.add(new ProductSpecificationModel("RAM", "4GB"));
//        productSpecificationModelList.add(new ProductSpecificationModel("RAM", "4GB"));
//        productSpecificationModelList.add(new ProductSpecificationModel("RAM", "4GB"));
//        productSpecificationModelList.add(new ProductSpecificationModel("RAM", "4GB"));
//        productSpecificationModelList.add(new ProductSpecificationModel("RAM", "4GB"));
        ProductSpecificationAdapter productSpecificationAdapter = new ProductSpecificationAdapter(productSpecificationModelList);
        productSpecificationRecyclerView.setAdapter(productSpecificationAdapter);
        productSpecificationAdapter.notifyDataSetChanged();

            return view;


    }

}
