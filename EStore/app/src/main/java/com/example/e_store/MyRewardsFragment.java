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
public class MyRewardsFragment extends Fragment {
    private RecyclerView rewaredrecyclerview;



    public MyRewardsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_my_rewards, container, false);
        rewaredrecyclerview = view.findViewById(R.id.my_rewards_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rewaredrecyclerview.setLayoutManager(linearLayoutManager);

        List<RewardModel>  rewardModelslist = new ArrayList<>();
        rewardModelslist.add(new RewardModel("Cashback", "13Hab, 18", "23% off on shopping above Rs. 450. Get Flashback Cashback at your own Cost."));
        rewardModelslist.add(new RewardModel("Movie Browser", "13Hab, 18", "23% off on shopping above Rs. 450. Get Flashback Cashback at your own Cost."));
        rewardModelslist.add(new RewardModel("Shopping Brouceher", "13Hab, 18", "23% off on shopping above Rs. 450. Get Flashback Cashback at your own Cost."));
        rewardModelslist.add(new RewardModel("Cashback", "13Hab, 18", "23% off on shopping above Rs. 450. Get Flashback Cashback at your own Cost."));


        RewardsAdapter rewardsAdapter = new RewardsAdapter(rewardModelslist);
        rewaredrecyclerview.setAdapter(rewardsAdapter);
        rewardsAdapter.notifyDataSetChanged();

        return view;
    }

}
