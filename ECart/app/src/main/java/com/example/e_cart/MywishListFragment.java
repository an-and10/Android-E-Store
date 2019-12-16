package com.example.e_cart;


import android.app.Dialog;
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
public class MywishListFragment extends Fragment {
    private RecyclerView myWishListRecylerView;
    public static  WishlistAdapter wishlistAdapter;

    private Dialog LoadingDialog ;


    public MywishListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_mywish_list, container, false);
        myWishListRecylerView = view.findViewById(R.id.mywishlist_recycler_view);

        LoadingDialog = new Dialog(getContext());
        LoadingDialog.setContentView(R.layout.loading_progress_dialog);
        LoadingDialog.setCancelable(false);
        LoadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        LoadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
        LoadingDialog.show();


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myWishListRecylerView.setLayoutManager(layoutManager);


            if(DBQueries.wishlistModelList.size() == 0)
            {
                DBQueries.wishList.clear();
                DBQueries.loadWishList(getContext(),LoadingDialog,true );
            }else
                LoadingDialog.dismiss();

         wishlistAdapter = new WishlistAdapter(DBQueries.wishlistModelList, true);
        myWishListRecylerView.setAdapter(wishlistAdapter);
        wishlistAdapter.notifyDataSetChanged();
        return  view;


    }

}
