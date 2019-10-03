package com.example.e_store;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.io.LineReader;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MywishListFragment extends Fragment {
    private RecyclerView myWishListRecylerView;


    public MywishListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_mywish_list, container, false);
        myWishListRecylerView = view.findViewById(R.id.mywishlist_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myWishListRecylerView.setLayoutManager(layoutManager);

        List<WishlistModel> wishlistModelList = new ArrayList<>();
        wishlistModelList.add(new WishlistModel(R.drawable.phone1, "Google Anand Phone", 3, "4.3", 1200,"12000","15000", "Cash on Delivery aAvailable" ));
        wishlistModelList.add(new WishlistModel(R.drawable.phone2, "Google Anand Phone", 3, "4.3", 1200,"12000","15000", "Cash on Delivery aAvailable" ));
        wishlistModelList.add(new WishlistModel(R.drawable.phone1, "Google Anand Phone", 3, "4.3", 1200,"12000","15000", "Cash on Delivery aAvailable" ));
        wishlistModelList.add(new WishlistModel(R.drawable.phone3, "Google Pixel Phone", 3, "4.3", 1200,"12000","15000", "Cash on Delivery aAvailable" ));

        WishlistAdapter wishlistAdapter = new WishlistAdapter(wishlistModelList);
        myWishListRecylerView.setAdapter(wishlistAdapter);
        wishlistAdapter.notifyDataSetChanged();
        return  view;


    }

}
