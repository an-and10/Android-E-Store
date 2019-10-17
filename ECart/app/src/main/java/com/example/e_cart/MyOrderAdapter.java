package com.example.e_cart;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {



    List<MyOrderItemModel>myOrderItemModelList ;
    public MyOrderAdapter(List<MyOrderItemModel> myOrderItemModelList) {
        this.myOrderItemModelList = myOrderItemModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_order_item_layout,viewGroup,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        int resource = myOrderItemModelList.get(position).getProductImage();
        int rating = myOrderItemModelList.get(position).getRating();
        String title = myOrderItemModelList.get(position).getProductTitle();
        String delieveryDate = myOrderItemModelList.get(position).getDeliveryStatus();
        viewHolder.setOrderDetails(resource,title,delieveryDate,rating);

    }

    @Override
    public int getItemCount() {
        return myOrderItemModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private ImageView orderIndicator;
        private TextView productTitle;
        private TextView deliveryStatus;
        private LinearLayout rateNowContainer;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_img);
            orderIndicator = itemView.findViewById(R.id.order_indicator_layout);
            deliveryStatus = itemView.findViewById(R.id.order_dilevery_date);
            productTitle = itemView.findViewById(R.id.product_title);
            rateNowContainer = itemView.findViewById(R.id.rate_now_container);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent orderDetails = new Intent(itemView.getContext(), OrderDetailsActivity.class);
                    itemView.getContext().startActivity(orderDetails);

                }
            });


        }

        private  void setOrderDetails(int resource,String title, String deliveryDate,int rating)
        {
        productImage.setImageResource(resource);
        productTitle.setText(title);
        if(deliveryDate.equals("Cancelled"))
        {
            orderIndicator.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.black)));
        }else
        {
            orderIndicator.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.colorPrimary)));
        }
            deliveryStatus.setText(deliveryDate);
        setRating(rating);
            for (int i =0;i<rateNowContainer.getChildCount();i++)
            {
                final int star_position = i;
                rateNowContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setRating(star_position);

                    }
                });

            }

        }
        private void setRating(int star_position)
        {
            for(int x =0;x <rateNowContainer.getChildCount();x++)
            {
                ImageView starbtn = (ImageView) rateNowContainer.getChildAt(x);
                starbtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
                if(x<=star_position)
                {
                    starbtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
                }

            }
        }



    }

}
