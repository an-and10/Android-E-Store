package com.example.e_store;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {
    public WishlistAdapter(List<WishlistModel> wishlistModelList) {
        this.wishlistModelList = wishlistModelList;
    }

    List<WishlistModel> wishlistModelList;

    @NonNull
    @Override
    public WishlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wishlist_items_layout,viewGroup,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistAdapter.ViewHolder viewHolder, int i) {
        int resource = wishlistModelList.get(i).getProductImg();
        String title = wishlistModelList.get(i).getProducttitle();
        int freeCoupons  = wishlistModelList.get(i).getFreeCoupomns();
        String price  =wishlistModelList.get(i).getProductPrice();
        String cuttedPrice = wishlistModelList.get(i).getProductCuttedPrice();
        String method = wishlistModelList.get(i).getPaymentMethod();
        int total_rating = wishlistModelList.get(i).getTotal_rating();
        String rating = wishlistModelList.get(i).getRating();
        viewHolder.setWishListDetails(resource,freeCoupons,title,cuttedPrice,price,method,total_rating,rating);




    }

    @Override
    public int getItemCount() {
        return wishlistModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImg;
        private TextView FreeCoupons;
        private TextView productTitle;
        private TextView productprice;
        private TextView CuttedPrice;
        private TextView paymentMethod;
        private TextView totalRating;
        private TextView rating;
        private View priceCut;
        private  ImageView deleteImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImg = itemView.findViewById(R.id.product_img);
            FreeCoupons= itemView.findViewById(R.id.product_free_coupons);
            productTitle = itemView.findViewById(R.id.product_title);
            CuttedPrice = itemView.findViewById(R.id.cutted_price);
            productprice = itemView.findViewById(R.id.product_price);
            paymentMethod = itemView.findViewById(R.id.cash_on_delivery);
            totalRating = itemView.findViewById(R.id.total_ratings_tv);
            rating = itemView.findViewById(R.id.product_rating_minview);
            priceCut = itemView.findViewById(R.id.price_cut);
            deleteImg = itemView.findViewById(R.id.delete_btn);


        }

        private  void setWishListDetails(int resource, int freeCoupons, String title, String cuttedPrice, String price, String method, int totalRatings, String ratings )
        {
            productImg.setImageResource(resource);
            if (freeCoupons!=0)
            {
                FreeCoupons.setVisibility(View.VISIBLE);
                FreeCoupons.setText(freeCoupons + " Free Coupons Avaliable");
            }else
            {
                FreeCoupons.setVisibility(View.INVISIBLE);
                FreeCoupons.setText(freeCoupons + " Free Coupons Avaliable");
            }
            productTitle.setText(title);
            CuttedPrice.setText(cuttedPrice);
            productprice.setText(price);
            paymentMethod.setText(method);
            totalRating.setText("Total Ratings: " +totalRatings);
            rating.setText(ratings);
            deleteImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "Deleted Items", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
