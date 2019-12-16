package com.example.e_cart;

import android.app.Dialog;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.ColorStateList;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter {
    List<CartItemModel> cartItemModelsList;
    private TextView cartTotalAmount ;
    private  Boolean showDeleteBtn;

    public CartAdapter(List<CartItemModel> cartItemModelsList,TextView cartTotalAmount , boolean showDeleleteBtn ) {
        this.cartItemModelsList = cartItemModelsList;
        this.cartTotalAmount  = cartTotalAmount;
        this.showDeleteBtn = showDeleleteBtn;
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
                String productId = cartItemModelsList.get(position).getProductId();
                String  resource = cartItemModelsList.get(position).getProduct_images();
                String title = cartItemModelsList.get(position).getProduct_title();
                long freeCoupons = cartItemModelsList.get(position).getFree_coupons();
                String productPrice = cartItemModelsList.get(position).getProduct_price();
                String cuttedPrice = cartItemModelsList.get(position).getCutted_product_price();
                long  offerAppliedNo = cartItemModelsList.get(position).getOffer_applied();
                boolean inStock = cartItemModelsList.get(position).isInStock();
                Long maxQuantity = cartItemModelsList.get(position).getMaxQuantity();
                Long quantity = cartItemModelsList.get(position).getQuantity();


                ((CartItemViewHolder)viewHolder).setProductDetails(productId,resource,title,freeCoupons,productPrice,cuttedPrice,offerAppliedNo, position,inStock, String.valueOf(quantity), maxQuantity);

                break;
            case CartItemModel.TOTAL_AMOUNT:
                int totalItems = 0;
                int totalItemPrices = 0;
                int  totalAmount;
                String deliveryPrice ;
                int savedAmount =0;

                for(int i=0;i< DBQueries.cartItemModelList.size();i++)
                {
                    if(cartItemModelsList.get(i).getType() == CartItemModel.CART_ITEM  && cartItemModelsList.get(i).isInStock())
                    {
                            totalItems++;
                            totalItemPrices = totalItemPrices + Integer.parseInt(cartItemModelsList.get(i).getProduct_price());
                    }
                }
                if(totalItemPrices >=500)
                {
                    deliveryPrice = "Free";
                    totalAmount = totalItemPrices;
                }else {
                    deliveryPrice = "60";
                    totalAmount =totalItemPrices+60;
                }

                ((CartTotalAmountViewHolder)viewHolder).setCartTotalAmount(totalItems, totalItemPrices,totalAmount,deliveryPrice,savedAmount );

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
        private LinearLayout deleteBtn;
        private LinearLayout couponsLayout;

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
            deleteBtn = itemView.findViewById(R.id.remove_item_btn);
            couponsLayout = itemView.findViewById(R.id.coupon_redemtions_layout);

        }
        private  void setProductDetails(String productId, String  resource, String title, long freeCouponsNo, String productPrice, String cutted_product_price, long offers_applies_no, final int position, boolean inStock,
                                        String productQuantity, final long maxQuantity)
        {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions()).placeholder(R.drawable.ph).into(productImage);
            productTitle.setText(title);

            if(freeCouponsNo>0)
            {
                if(freeCouponsNo==1)
                free_coupons.setText(freeCouponsNo+ " Free Coupon Avaliable");
                else
                    free_coupons.setText(freeCouponsNo+ " Free Coupon Avaliable");

            }else
                free_coupons.setVisibility(View.INVISIBLE);

            if(inStock){
                couponsLayout.setVisibility(View.VISIBLE);
                product_price.setText("Rs: "+productPrice );
                product_price.setTextColor(itemView.getResources().getColor(R.color.black));
                cutted_price.setText(("Rs: "+ cutted_product_price));
                if(offers_applies_no>0){
                    offers_applied.setVisibility(View.VISIBLE);
                    offers_applied.setText(offers_applies_no+" Offers Applied");
                }
                quantity.setText("Qty: "+ productQuantity);

                quantity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog quantityDialog = new Dialog(itemView.getContext());
                        quantityDialog.setContentView(R.layout.quantity_dialog);
                        quantityDialog.setCancelable(false);
                        final EditText quantityNo = quantityDialog.findViewById(R.id.quantity_call);
                        Button cancelBtn = quantityDialog.findViewById(R.id.DialogcancelBtn);
                        Button confirmBtn = quantityDialog.findViewById(R.id.DialogConfirmBtn);
                        quantityNo.setHint("Max-Quantity:" + String.valueOf(maxQuantity));
                        cancelBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                quantityDialog.dismiss();

                            }
                        });
                        confirmBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(!TextUtils.isEmpty(quantityNo.getText())) {

                                    if (Long.valueOf(quantityNo.getText().toString()) <= maxQuantity && Long.valueOf(quantityNo.getText().toString()) != 0) {

                                        if(itemView.getContext() instanceof  MainActivity){

                                            DBQueries.cartItemModelList.get(position).setQuantity(Integer.parseInt((quantityNo.getText().toString())));

                                        }else{
                                            if (DeliveryActivity.fromCart) {
                                                DBQueries.cartItemModelList.get(position).setQuantity(Integer.parseInt((quantityNo.getText().toString())));

                                            } else {
                                                DeliveryActivity.cartItemModelList.get(position).setQuantity(Integer.parseInt((quantityNo.getText().toString())));
                                            }
                                        }


                                        quantity.setText("Qty: " + quantityNo.getText());

                                        Toast.makeText(itemView.getContext(), "Selected:" + quantityNo.getText().toString(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(itemView.getContext(), "Invalid Quantity Selection, Select min quantity", Toast.LENGTH_SHORT).show();

                                    }


                                }
                                quantityDialog.dismiss();
                            }
                        });
                        quantityDialog.show();

                    }
                });
            }else{
                coupons_applied.setVisibility(View.GONE);
                offers_applied.setVisibility(View.INVISIBLE);
                quantity.setText("Qty: "+ 0 );
                quantity.setCompoundDrawableTintList(ColorStateList.valueOf(itemView.getResources().getColor(R.color.colorFaint)));
                quantity.setTextColor(itemView.getResources().getColor(R.color.colorFaint));
                quantity.setBackgroundTintList(ColorStateList.valueOf(itemView.getResources().getColor(R.color.colorFaint)));
                couponsLayout.setVisibility(View.GONE);
                product_price.setText("Out of Stock");
                product_price.setTextColor(itemView.getResources().getColor(R.color.colorPrimary));
                cutted_price.setText( " ");
            }

               if(showDeleteBtn)
               {
                   deleteBtn.setVisibility(View.VISIBLE);
                   deleteBtn.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           if(!ProductDetailsActivity.running_cart_query)
                           {

                               ProductDetailsActivity.running_cart_query =true;
                               DBQueries.removeFromCart( itemView.getContext(),position, cartTotalAmount);
                               Toast.makeText(itemView.getContext(), "Position:"+position, Toast.LENGTH_SHORT).show();
                           }
                       }
                   });
               }else
               {
                   deleteBtn.setVisibility(View.GONE);
               }
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

        public void setCartTotalAmount(int totalItems1, int totalItemPrice1, int totalAmount1,String deliveryPrice1, int savedPrice1)
        {
            totalItems.setText("Price: "+ totalItems1+ " Items ");
            totalItemPrice.setText("Rs: "+totalItemPrice1+ " /-");
            if(deliveryPrice1.equals("Free"))
            {
                deliveryPrice.setText("Free");
            }else
                deliveryPrice.setText("Rs."+deliveryPrice1+ "/-");
            totalAmount.setText("Rs."+ totalAmount1 +"/-");
            savePrice.setText("You saved Rs."+savedPrice1+ " on this purchase");
            cartTotalAmount.setText("Rs."+ totalAmount1 +"/-");


            LinearLayout parent = (LinearLayout) cartTotalAmount.getParent().getParent();
            if(totalItemPrice1 == 0)

            {
                // DBQueries.cartItemModelList.remove(DBQueries.cartItemModelList.size());
                parent.setVisibility(View.GONE);
            }else
            {
                parent.setVisibility(View.VISIBLE);

            }
        }
    }
}
