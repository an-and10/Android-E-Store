package com.example.e_store;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter {
    List<CartItemModel> cartItemModelsList;

    public CartAdapter(List<CartItemModel> cartItemModelsList) {
        this.cartItemModelsList = cartItemModelsList;
    }



    @Override
    public int getItemViewType(int position) {
        switch(cartItemModelsList.get(position).getType())
        {
            case 0:
                return CartItemModel.CART_ITEM;
            case 1:
                return CartItemModel.TOTAL_AMOUNT;

            default:
                return -1;


        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch(viewType)
        {
            case CartItemModel.CART_ITEM:
                View Cartview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_item_layout,viewGroup,false);
                return new CartItemViewHolder(Cartview);

            case CartItemModel.TOTAL_AMOUNT:
                View CartAmountView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_total_amount_layout,viewGroup,false);
                return new CartTotalAmountViewHolder(CartAmountView);
            default :
                return null;


        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch (cartItemModelsList.get(position).getType())
        {
            case CartItemModel.CART_ITEM:
                int resource = cartItemModelsList.get(position).getProduct_images();
                String title = cartItemModelsList.get(position).getProduct_title();
                int freeCoupons = cartItemModelsList.get(position).getFree_coupons();
                String productPrice = cartItemModelsList.get(position).getProduct_price();
                String cuttedPrice = cartItemModelsList.get(position).getCutted_product_price();
                int offerAppliedNo = cartItemModelsList.get(position).getOffer_applied();
                ((CartItemViewHolder)viewHolder).setProductDetails(resource,title,freeCoupons,productPrice,cuttedPrice,offerAppliedNo);

                break;
            case CartItemModel.TOTAL_AMOUNT:
                String totalItems = cartItemModelsList.get(position).getTotatItems();
                String totalItemsPrice = cartItemModelsList.get(position).getTotalItemPrice();
                String deliveryPrice = cartItemModelsList.get(position).getDeliveryPrice();
                String totalAmount = cartItemModelsList.get(position).getTotalAmount();
                String savedPrice = cartItemModelsList.get(position).getSavedAmount();
                ((CartTotalAmountViewHolder)viewHolder).setCartTotalAmount(totalItems,totalItemsPrice,totalAmount,deliveryPrice,savedPrice);

                break;
            default:
                return ;


        }

    }

    @Override
    public int getItemCount() {
        return cartItemModelsList.size();
    }

    public class CartItemViewHolder extends  RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productTitle;
        private TextView free_coupons;
        private TextView product_price;
        private TextView cutted_price;
        private TextView offers_applied;
        private TextView coupons_applied;
        private  TextView quantity;


        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_cart_image);
            productTitle = itemView.findViewById(R.id.product_title);
            free_coupons  =itemView.findViewById(R.id.tv_free_coupon);
            product_price = itemView.findViewById(R.id.product_cart_price);
            cutted_price = itemView.findViewById(R.id.original_price);
            offers_applied = itemView.findViewById(R.id.offer_applied);
            coupons_applied = itemView.findViewById(R.id.coupons_applied);
            quantity = itemView.findViewById(R.id.product_quantity);

        }
        private  void setProductDetails(int resource,String title,int freeCouponsNo,String productPrice, String cutted_product_price, int offers_applies_no)
        {
            productImage.setImageResource(resource);
            productTitle.setText(title);
            if(freeCouponsNo>0)
            {
                if(freeCouponsNo==1)
                free_coupons.setText(freeCouponsNo+ "Free Coupon");
                else
                    free_coupons.setText(freeCouponsNo+ "Free Coupons");

            }else
                free_coupons.setVisibility(View.INVISIBLE);
            product_price.setText(productPrice);
            cutted_price.setText((cutted_product_price));
           if(offers_applies_no>0){
               offers_applied.setVisibility(View.VISIBLE);
               offers_applied.setText(offers_applies_no+" Offers Applied");
           }

           else
               offers_applied.setVisibility(View.INVISIBLE);



        }
    }
    public  class CartTotalAmountViewHolder extends  RecyclerView.ViewHolder {
        private TextView totalItems;
        private  TextView totalItemPrice;
        private  TextView totalAmount;
        private TextView deliveryPrice;
        private TextView savePrice;
        public CartTotalAmountViewHolder(@NonNull View itemView) {
            super(itemView);
            totalItems = itemView.findViewById(R.id.total_items);
            totalItemPrice = itemView.findViewById(R.id.total_items_prices);
            totalAmount = itemView.findViewById(R.id.total_price);
            deliveryPrice = itemView.findViewById(R.id.delivery_charge_price);
            savePrice = itemView.findViewById(R.id.saved_amount);

        }

        public void setCartTotalAmount(String totalItems1, String totalItemPrice1, String totalAmount1,String deliveryPrice1, String savedPrice1)
        {
            totalItems.setText(totalItems1);
            totalItemPrice.setText(totalItemPrice1);
            totalAmount.setText(totalAmount1);
            deliveryPrice.setText(deliveryPrice1);
            savePrice.setText("You saved Rs."+savedPrice1+ " on this purchase");
        }
    }
}
