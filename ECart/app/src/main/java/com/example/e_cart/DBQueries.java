package com.example.e_cart;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.e_cart.HomeFragment.swipeRefreshLayout;
import static com.example.e_cart.ProductDetailsActivity.productId;


public class DBQueries {


    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static List<CategoryModel> CategoryModelList = new ArrayList<>();

    public static List<List<HomePageModel>> lists = new ArrayList<>();
    public static List<String> loadedCategoriesName = new ArrayList<>();
    public static List<String> wishList = new ArrayList<>();

    public static List<WishlistModel> wishlistModelList = new ArrayList<>();
    public static List<String> myRatedId = new ArrayList<>();
    public static List<Long> myRating = new ArrayList<>();

    public static List<String> cartList = new ArrayList<>();
    public static List<CartItemModel> cartItemModelList = new ArrayList<>();

    public  static  List<AddressModel> addressModelList = new ArrayList<>();
    public  static  int selectedAddress = -1;


    public static void loadCategories(final RecyclerView categoryRecylerview, final Context context) {
        CategoryModelList.clear();
        firebaseFirestore.collection("CATEGORIES").orderBy("index").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        CategoryModelList.add(new CategoryModel(documentSnapshot.get("icons").toString(), documentSnapshot.get("CategoryName").toString()));

                    }
                    CategoryAdapter categoryAdapter = new CategoryAdapter(CategoryModelList);
                    categoryRecylerview.setAdapter(categoryAdapter);

                    categoryAdapter.notifyDataSetChanged();
                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(context, "Error in Category" + error, Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public static void loadFragmentFirebase(final RecyclerView productRecycler, final Context context, final int index, String CategoryName) {
        // Toast.makeText(context, "Size: "+homePageModelList.size(), Toast.LENGTH_SHORT).show();
        firebaseFirestore.collection("CATEGORIES")
                .document(CategoryName.toUpperCase())
                .collection("TOP_DEALS").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                if ((long) documentSnapshot.get("view_type") == 0) {
                                    List<sliderModel> sliderModelList = new ArrayList<>();
                                    long no_of_banners = (long) documentSnapshot.get("No_of_Banner");

                                    sliderModelList.add(new sliderModel(documentSnapshot.get("banner_1").toString(),
                                            documentSnapshot.get("banner_1_background").toString()));
                                    sliderModelList.add(new sliderModel(documentSnapshot.get("banner_2").toString(),
                                            documentSnapshot.get("banner_2_background").toString()));
                                    sliderModelList.add(new sliderModel(documentSnapshot.get("banner_3").toString(),
                                            documentSnapshot.get("banner_3_background").toString()));
                                    sliderModelList.add(new sliderModel(documentSnapshot.get("banner_4").toString(),
                                            documentSnapshot.get("banner_4_background").toString()));
                                    sliderModelList.add(new sliderModel(documentSnapshot.get("banner_5").toString(),
                                            documentSnapshot.get("banner_5_background").toString()));
                                    sliderModelList.add(new sliderModel(documentSnapshot.get("banner_6").toString(),
                                            documentSnapshot.get("banner_6_background").toString()));

                                    lists.get(index).add(new HomePageModel(sliderModelList, 0));

                                } else if ((long) documentSnapshot.get("view_type") == 1) {
                                    lists.get(index).add(new HomePageModel(1,
                                            documentSnapshot.get("strip_ad_banner").toString(),
                                            documentSnapshot.get("strip_ad_background").toString()));


                                } else if ((long) documentSnapshot.get("view_type") == 2) {
                                    List<WishlistModel> viewAllProductList = new ArrayList<>();
                                    List<HorizontalScrollProductModel> horizontalScrollProductModelList = new ArrayList<>();

                                    long no_of_products = (long) documentSnapshot.get("No_of_Products");

                                    for (long x = 1; x < no_of_products + 1; x++) {
                                        //Toast.makeText(getActivity(), "Length: "+no_of_products, Toast.LENGTH_SHORT).show();
                                        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(documentSnapshot.get("product_ID_" + x).toString(),


                                                documentSnapshot.get("product_image_" + x).toString(),
                                                documentSnapshot.get("product_title_" + x).toString(),
                                                documentSnapshot.get("product_subtitle_" + x).toString(),
                                                documentSnapshot.get("product_price_" + x).toString()));

                                        viewAllProductList.add(new WishlistModel(documentSnapshot.get("product_ID_" + x).toString(), documentSnapshot.get("product_image_" + x).toString(),
                                                (long) documentSnapshot.get("product_coupons_" + x),
                                                documentSnapshot.get("Avg_rating_" + x).toString(),
                                                documentSnapshot.get("product_full_title_" + x).toString(),
                                                (long) documentSnapshot.get("total_ratings_" + x),
                                                documentSnapshot.get("product_price_" + x).toString(),
                                                documentSnapshot.get("product_cutted_price_" + x).toString(),
                                                (boolean) documentSnapshot.get("product_COD_" + x)
                                        ));


                                    }
                                    lists.get(index).add(new HomePageModel(2, documentSnapshot.get("layout_title").toString(),
                                            documentSnapshot.get("layout_background").toString(), horizontalScrollProductModelList, viewAllProductList));


                                } else if ((long) documentSnapshot.get("view_type") == 3) {
                                    List<HorizontalScrollProductModel> gridProductModelList = new ArrayList<>();

                                    long no_of_products = (long) documentSnapshot.get("No_of_Products");

                                    for (int x = 1; x < no_of_products + 1; x++) {
                                        gridProductModelList.add(new HorizontalScrollProductModel(documentSnapshot.get("product_ID_" + x).toString(),
                                                documentSnapshot.get("product_image_" + x).toString(),
                                                documentSnapshot.get("product_title_" + x).toString(),
                                                documentSnapshot.get("product_subtitle_" + x).toString(),
                                                documentSnapshot.get("product_price_" + x).toString()));
                                        //  Toast.makeText(context, "image url:"+x+":"+ documentSnapshot.get("product_image_"+x).toString() , Toast.LENGTH_SHORT).show();

                                    }
                                    lists.get(index).add(new HomePageModel(3, documentSnapshot.get("layout_title").toString(),
                                            documentSnapshot.get("layout_background").toString(), gridProductModelList));

                                }

                            }
                            HomePageAdapter homePageAdapter = new HomePageAdapter(lists.get(index));
                            productRecycler.setAdapter(homePageAdapter);
                            homePageAdapter.notifyDataSetChanged();
                            swipeRefreshLayout.setRefreshing(false);
                        } else {
                            String error = task.getException().getMessage();
                            // Toast.makeText(context, "Error in Category"+error, Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }

    public static void loadWishList(final Context context, final Dialog dialog, final boolean loadProductData) {
        wishList.clear();

        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_WISHLIST")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    for (long i = 0; i < (long) task.getResult().get("list_size"); i++) {
                        if (loadProductData) {
                            final String productid = task.getResult().get("Product_ID_" + i).toString();
                            wishlistModelList.clear();
                            wishList.add(productid);
                            if (DBQueries.wishList.contains(productId)) {
                                ProductDetailsActivity.ALREADYADDED_TO_WISHLIST = true;
                                if (ProductDetailsActivity.add_to_wishList_btn != null) {
                                    ProductDetailsActivity.add_to_wishList_btn.setSupportImageTintList(context.getResources().getColorStateList(R.color.colorPrimary));
                                }

                            } else {
                                ProductDetailsActivity.ALREADYADDED_TO_WISHLIST = false;
                                if (ProductDetailsActivity.add_to_wishList_btn != null) {
                                    ProductDetailsActivity.add_to_wishList_btn.setSupportImageTintList(context.getResources().getColorStateList(R.color.black));
                                }
                            }
                            firebaseFirestore.collection("PRODUCTS").document(productid).get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {

                                                wishlistModelList.add(new WishlistModel(productid, task.getResult().get("product_image_1").toString(),
                                                        (long) task.getResult().get("free_coupons"),
                                                        task.getResult().get("Avg_rating").toString(),
                                                        task.getResult().get("product_title").toString(),
                                                        (long) task.getResult().get("total_ratings"),
                                                        task.getResult().get("product_price").toString(),
                                                        task.getResult().get("cutted_price").toString(),
                                                        (boolean) task.getResult().get("COD")
                                                ));
                                                MywishListFragment.wishlistAdapter.notifyDataSetChanged();

                                            } else {

                                            }
                                        }
                                    });
                        }
                    }
                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(context, "Error:" + error, Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
    }

    public static void removeFromWishList(final Context context, final int index) {
        final String removeProdcutId = wishList.get(index);
        wishList.remove(index);
        Map<String, Object> updateWishList = new HashMap<>();
        for (int i = 0; i < wishList.size(); i++) {
            updateWishList.put("product_ID" + i, wishList.get(i));
        }
        updateWishList.put("list_size", (long) wishList.size());
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_WISHLIST")
                .set(updateWishList).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (wishlistModelList.size() != 0) {
                        wishlistModelList.remove(index);
                        MywishListFragment.wishlistAdapter.notifyDataSetChanged();
                    }
                    ProductDetailsActivity.ALREADYADDED_TO_WISHLIST = false;
                    Toast.makeText(context, "Removed SuccessFully", Toast.LENGTH_SHORT).show();

                } else {

                    if (ProductDetailsActivity.add_to_wishList_btn != null) {
                        ProductDetailsActivity.add_to_wishList_btn.setSupportImageTintList(context.getResources().getColorStateList(R.color.colorPrimary));
                    }
                    wishList.add(index, removeProdcutId);
                    Toast.makeText(context, "Error:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
                if (ProductDetailsActivity.add_to_wishList_btn != null)
//                    ProductDetailsActivity.add_to_wishList_btn.setEnabled(true);
                    ProductDetailsActivity.running_wishlist_query = false;
            }
        });
    }


    public static void loadRatingList(final Context context) {
        if (ProductDetailsActivity.running_rating_query) {
            ProductDetailsActivity.running_rating_query = true;
            myRatedId.clear();
            myRating.clear();

            firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_RATINGS")
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        long i = 0;
                        for (i = 0; i < (long) task.getResult().get("list_size"); i++) {

                            myRatedId.add(task.getResult().get("product_ID_" + i).toString());
                            myRating.add((long) task.getResult().get("rating_" + i));

                            if (task.getResult().get("product_ID_" + i).toString().equals(productId)) {
                                ProductDetailsActivity.intialRating = Integer.parseInt(String.valueOf((long) task.getResult().get("rating_" + i))) - 1;
                                if (ProductDetailsActivity.rateNowContainer != null)
                                    ProductDetailsActivity.setRating(ProductDetailsActivity.intialRating);
                            }
                        }
                    } else {

                        Toast.makeText(context, "Error:", Toast.LENGTH_SHORT).show();
                    }
                    ProductDetailsActivity.running_rating_query = false;
                }
            });

        }
    }

    public static  void loadCart(final Context context, final Dialog dialog, final Boolean loadProductData, final TextView badgeCount, final TextView cartTotalAmount)
    {
      //  cartList.clear();

        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_CART")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    for (long i = 0; i < (long) task.getResult().get("list_size"); i++) {
                        cartList.add(task.getResult().get("product_ID_"+i).toString());
                        if(DBQueries.cartList.contains(task.getResult().get("product_ID_"+i).toString()))
                            ProductDetailsActivity.ALREADY_ADDED_TO_CART = true;
                        else
                            ProductDetailsActivity.ALREADY_ADDED_TO_CART = false;
                        if (loadProductData) {
                            final String productid = task.getResult().get("product_ID_" + i).toString();
                            firebaseFirestore.collection("PRODUCTS").document(productid).get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {

                                                int index =0;
                                                if(cartList.size()>=2)
                                                {
                                                    index = cartList.size()-2;
                                                }
                                                cartItemModelList.add(index, new CartItemModel(CartItemModel.CART_ITEM,
                                                        productid,
                                                        task.getResult().get("product_title").toString(),
                                                        task.getResult().get("product_price").toString(),
                                                        task.getResult().get("cutted_price").toString(),
                                                        task.getResult().get("product_image_1").toString(),
                                                        (long)task.getResult().get("free_coupons"),
                                                        (long)1,
                                                        (long)1,
                                                        (long)1,
                                                        (boolean)task.getResult().get("in_stock")));
                                                if(cartList.size()==1)
                                                {
                                                        cartItemModelList.add(new CartItemModel(CartItemModel.TOTAL_AMOUNT));
                                                    LinearLayout parent = (LinearLayout) cartTotalAmount.getParent().getParent();
                                                    parent.setVisibility(View.VISIBLE);
                                                }
                                                if(cartList.size() ==0)
                                                {

                                                    cartItemModelList.clear();
                                                }
                                                CartFragment.cartAdapter.notifyDataSetChanged();
                                                Toast.makeText(context, "Size of cart:"+cartItemModelList.size(), Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(context, "Error in Cart:", Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });
                        }
                    }
                    if(cartList.size() !=0)
                    {
                        badgeCount.setVisibility(View.VISIBLE);
                        Toast.makeText(context, "Visible Now", Toast.LENGTH_SHORT).show();
                        badgeCount.setText(String.valueOf(DBQueries.cartList.size()));
                        Toast.makeText(context, "Count+ :"+(DBQueries.cartList.size()), Toast.LENGTH_SHORT).show();
                    }else
                    {
                       // badgeCount.setVisibility(View.INVISIBLE);
                    }
//                    if(DBQueries.cartList.size()<99)
//                    {
//                        badgeCount.setText(String.valueOf(DBQueries.cartList.size()));
//                    }else
//                    {
//                        badgeCount.setText("99");
//                    }

                } else {

                    String error = task.getException().getMessage();
                    Toast.makeText(context, "Error:" + error, Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
    }

    public static void removeFromCart(final Context context, final int index, final TextView cartTotalAmount) {
        final String removeProductId = cartList.get(index);
        cartList.remove(index);
        Map<String, Object> updateCartList = new HashMap<>();
        for (int i = 0; i < cartList.size(); i++) {
            updateCartList.put("product_ID_" + i, cartList.get(i));
        }
        updateCartList.put("list_size", (long) cartList.size());
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_CART")
                .set(updateCartList).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (cartItemModelList.size() != 0) {
                        cartItemModelList.remove(index);
                        CartFragment.cartAdapter.notifyDataSetChanged();
                    }
                        if(cartList.size() == 0)
                        {
                            cartItemModelList.clear();
                            LinearLayout parent = (LinearLayout) cartTotalAmount.getParent().getParent();
                            parent.setVisibility(View.GONE);
                        }
                    Toast.makeText(context, "Removed SuccessFully", Toast.LENGTH_SHORT).show();
                } else {
                    cartList.add(index, removeProductId);
                    Toast.makeText(context, "Error:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
                    ProductDetailsActivity.running_cart_query = false;
            }
        });
    }

    public static void loadAddress(final Context context, final Dialog LoadingDialog)
    {
            addressModelList.clear();
            firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_ADDRESS")
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        Intent deliveryIntent;

                        if((long )task.getResult().get("list_size") == 0)
                        {
                           deliveryIntent  = new Intent(context,AddAddressActivity.class);
                           deliveryIntent.putExtra("INTENT", "null");
                        }else
                        {
                            for(int i= 1 ; i<(long)task.getResult().get("list_size")+1 ;i++)
                            {
                                 addressModelList.add(new AddressModel(task.getResult().get("full_name_"+i).toString(),
                                        task.getResult().get("address_"+i).toString(),
                                        task.getResult().get("pincode_"+i).toString(),
                                        (boolean)task.getResult().get("selected_"+i)));
                                if((boolean)task.getResult().get("selected_"+i))
                                {
                                        selectedAddress  = Integer.parseInt(String.valueOf(i-1));
                                }
                            }
                            Toast.makeText(context, "Size of addressmodellist"+addressModelList.size(), Toast.LENGTH_SHORT).show();
                            deliveryIntent = new Intent(context, DeliveryActivity.class);
                        }
                        context.startActivity(deliveryIntent);

                    }else
                    {
                        Toast.makeText(context, "Error:"+task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                    }
                    LoadingDialog.dismiss();
                }
            });
    }

    public static void clearData() {
        CategoryModelList.clear();
        lists.clear();
        loadedCategoriesName.clear();
        wishlistModelList.clear();
        wishList.clear();
        cartList.clear();
        cartItemModelList.clear();
    }

}