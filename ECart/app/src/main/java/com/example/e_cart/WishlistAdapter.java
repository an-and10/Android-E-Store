package com.example.e_cart;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {
    private Boolean wishList;


    public WishlistAdapter(List<WishlistModel> wishlistModelList, Boolean wishList) {
        this.wishlistModelList = wishlistModelList;
        this.wishList =wishList;
    }

    List<WishlistModel> wishlistModelList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wishlist_items_layout,viewGroup,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String productId = wishlistModelList.get(i).getProductId();
        String  resource = wishlistModelList.get(i).getProductImg();
        String title = wishlistModelList.get(i).getProducttitle();
        long freeCoupons  = wishlistModelList.get(i).getFreeCoupons();
        String price  =wishlistModelList.get(i).getProductPrice();
        String cuttedPrice = wishlistModelList.get(i).getProductCuttedPrice();
        boolean method = wishlistModelList.get(i).isCOD();
        long  total_rating = wishlistModelList.get(i).getTotal_rating();
        String rating = wishlistModelList.get(i).getRating();
        viewHolder.setWishListDetails(productId, resource,freeCoupons,title,cuttedPrice,price,method,total_rating,rating, i);
//        if(lastPosition < i)
//        {
//            Animation animation  = AnimationUtils.loadAnimation(viewHolder.itemView.getContext(),lastPosition);
//            viewHolder.itemView.setAnimation(animation);
//            lastPosition = i;
//        }


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

        private  void setWishListDetails(final String productid, String  resource, long freeCoupons, String title, String cuttedPrice, String price, Boolean method, long totalRatings, String ratings , final int index)
        {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions()).placeholder(R.mipmap.home).into(productImg);
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
            if(method==true)
            {
                paymentMethod.setVisibility(View.VISIBLE);
            }else
            {
                paymentMethod.setVisibility(View.INVISIBLE);
            }
           // paymentMethod.setText();
            totalRating.setText("Total Ratings: " +totalRatings);
            rating.setText(ratings);
            if(wishList)
            {
                deleteImg.setVisibility(View.VISIBLE);
            }else
            {
                    deleteImg.setVisibility(View.GONE);
            }
            deleteImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!ProductDetailsActivity.running_wishlist_query ) {
                        ProductDetailsActivity.running_wishlist_query = true;
                        DBQueries.removeFromWishList(itemView.getContext(), index);
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(itemView.getContext(), "Productid:"+productid, Toast.LENGTH_SHORT).show();
                    Intent productDetails = new Intent(itemView.getContext(), ProductDetailsActivity.class);
                    productDetails.putExtra("PRODUCT_ID", productid);
                    itemView.getContext().startActivity(productDetails);
                }
            });

        }
    }
}
