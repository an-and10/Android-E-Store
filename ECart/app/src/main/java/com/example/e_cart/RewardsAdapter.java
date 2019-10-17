package com.example.e_cart;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RewardsAdapter extends RecyclerView.Adapter<RewardsAdapter.ViewHolder> {
    public RewardsAdapter(List<RewardModel> rewardModelList) {
        this.rewardModelList = rewardModelList;
    }

    List<RewardModel> rewardModelList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rewards_item_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String title = rewardModelList.get(i).getTitle();
        String expiry = rewardModelList.get(i).getExpirydate();
        String body = rewardModelList.get(i).getCoupon_body();

        viewHolder.setRewardDetails(title,body,expiry);

    }

    @Override
    public int getItemCount() {
        return  rewardModelList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
       private  TextView coupons_title;

        private TextView couponsbody;
        private TextView expiryDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           coupons_title =  itemView.findViewById(R.id.reward_title);
            couponsbody = itemView.findViewById(R.id.coupons__body);
            expiryDate = itemView.findViewById(R.id.coupons_validity);

        }
        private void  setRewardDetails( String Ctitle, String Cbody, String Cexpiry)
        {
            coupons_title.setText(Ctitle);
            couponsbody.setText(Cbody);
            expiryDate.setText(Cexpiry);
        }
    }
}
