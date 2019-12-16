package com.example.e_cart;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static com.example.e_cart.RegisterActivity.setSignUpFragment;


public class ProductDetailsActivity extends AppCompatActivity {
    private ViewPager productImagesViewPager;
    private TabLayout viewPagerIndicator;


    public static FloatingActionButton add_to_wishList_btn;
    public static Boolean ALREADYADDED_TO_WISHLIST = false;
    public static Boolean ALREADY_ADDED_TO_CART = false;
    private ViewPager productDetailsViewPager;
    private TabLayout productDetailsTabLayout;
    private Button buyNowBtn;
    private TextView productTitle;
    private TextView product_avg_rating;
    private TextView total_rating;
    private TextView product_price;
    private TextView orginal_price;
    private ImageView cod_indicator;
    private TextView cod_text;
    private TextView reward_tite;
    private TextView reward_body;
    private TextView productDescriptionBody;
    private TextView AvgRating;

    private ConstraintLayout productDescriptionOnlyContainer;
    private ConstraintLayout productTabContainer;

    private String productDescriptions;
    private String otherDescription;


    private TextView totalRatings;
    private LinearLayout rating_number_container;
    private TextView totalRatingFigure;
    private LinearLayout ratingdProgrsContainer;
    private LinearLayout addToCartBtn;
    private FirebaseUser currentUser;

    public static List<ProductSpecificationModel> productSpecificationModelList = new ArrayList<>();

    private Dialog SignInDialog;
    private Dialog LoadingDialog;

    // Rating Layout//
    public static LinearLayout rateNowContainer;
    public static int intialRating;

    private FirebaseFirestore firebaseFirestore;
    private DocumentSnapshot documentSnapshot;
    public static String productId;
    public static boolean running_wishlist_query = false;
    public static boolean running_rating_query = false;
    public static boolean running_cart_query = false;
    public static  MenuItem cartItem;
    private  TextView  badgeCount;

    public static Activity productDetailsActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Loading Dialog //
        intialRating = -1;
        LoadingDialog = new Dialog(ProductDetailsActivity.this);
        LoadingDialog.setContentView(R.layout.loading_progress_dialog);
        LoadingDialog.setCancelable(false);
        LoadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        LoadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LoadingDialog.show();
        //Loading ends

        productImagesViewPager = findViewById(R.id.product_image_viewpager);
        viewPagerIndicator = findViewById(R.id.view_pager_indicator);
        add_to_wishList_btn = findViewById(R.id.add_to_wishlist_btn);
        productDetailsViewPager = findViewById(R.id.product_details_view_pager);
        productDetailsTabLayout = findViewById(R.id.product_details_tab_layout);

        buyNowBtn = findViewById(R.id.buy_now_btn);
        productTitle = findViewById(R.id.product_main_title);
        product_avg_rating = findViewById(R.id.product_rating_minview);
        total_rating = findViewById(R.id.total_rating_mini_view);
        product_price = findViewById(R.id.product_price);
        orginal_price = findViewById(R.id.original_price);
        cod_indicator = findViewById(R.id.cod_indicator_imageview);
        cod_text = findViewById(R.id.cod_indicator_textview);
        reward_tite = findViewById(R.id.reward_title);
        reward_body = findViewById(R.id.reward_body);
        totalRatings = findViewById(R.id.total_ratings_tv);
        rating_number_container = findViewById(R.id.rating_numbers_container);
        totalRatingFigure = findViewById(R.id.total_ratings_figure);
        ratingdProgrsContainer = findViewById(R.id.rating_progrss_bar_container);
        AvgRating = findViewById(R.id.average_rating_tv);
        addToCartBtn = findViewById(R.id.layout_cart);


        productTabContainer = findViewById(R.id.product_details_tab_container);
        productDescriptionOnlyContainer = findViewById(R.id.product_details_container);
        productDescriptionBody = findViewById(R.id.product_details_body);
        productId = getIntent().getStringExtra("PRODUCT_ID");
        //Toast.makeText(this, "ProductId: "+productId, Toast.LENGTH_SHORT).show();
        firebaseFirestore = FirebaseFirestore.getInstance();
        final List<String> productImages = new ArrayList<>();
        // Toast.makeText(this, "ProductId:"+productId, Toast.LENGTH_SHORT).show();
        firebaseFirestore.collection("PRODUCTS").document(productId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            getSupportActionBar().setTitle("Products");
                            documentSnapshot = task.getResult();
                            for (long x = 1; x < (long) documentSnapshot.get("no_of_products_images") + 1; x++) {
                                productImages.add(documentSnapshot.get("product_image_" + x).toString());
                            }
                            ProductImageAdapter productImageAdapter = new ProductImageAdapter(productImages);
                            productImagesViewPager.setAdapter(productImageAdapter);
                            productTitle.setText(documentSnapshot.get("product_title").toString());
                            product_avg_rating.setText(documentSnapshot.get("Avg_rating").toString());
                            total_rating.setText("(" + (long) documentSnapshot.get("total_ratings") + ")");
                            product_price.setText("Rs. " + documentSnapshot.get("product_price").toString() + " /-");
                            orginal_price.setText("Rs. " + documentSnapshot.get("cutted_price").toString());
                            if ((boolean) documentSnapshot.get("COD") == true) {
                                cod_indicator.setVisibility(View.VISIBLE);
                                cod_text.setVisibility(View.VISIBLE);

                            } else {
                                cod_text.setVisibility(View.INVISIBLE);
                                cod_text.setVisibility(View.INVISIBLE);

                            }
                            reward_tite.setText(documentSnapshot.get("free_coupon_title").toString());
                            reward_body.setText(documentSnapshot.get("free_coupons_body").toString());
                            if ((boolean) documentSnapshot.get("use_tab_layout")) {
                                productTabContainer.setVisibility(View.VISIBLE);
                                productDescriptionOnlyContainer.setVisibility(View.GONE);
                                productDescriptions = documentSnapshot.get("product_description").toString();
                                otherDescription = documentSnapshot.get("product_other_detail").toString();

                                for (long x = 1; x < (long) documentSnapshot.get("total_spec_title") + 1; x++) {

                                    //Toast.makeText(ProductDetailsActivity.this, "Total spec  Counts:"+ (x), Toast.LENGTH_SHORT).show();
                                    productSpecificationModelList.add(new ProductSpecificationModel(0, documentSnapshot.get("spec_title_" + x).toString()));
                                    //   Toast.makeText(ProductDetailsActivity.this, "Title:"+ documentSnapshot.get("spec_title_"+x).toString() , Toast.LENGTH_SHORT).show();

                                    for (long j = 1; j < (long) documentSnapshot.get("spec_title_" + x + "_total_fields") + 1; j++) {
                                        // Toast.makeText(ProductDetailsActivity.this, ""+(long)documentSnapshot.get("spec_title_"+x+"_total_fields"), Toast.LENGTH_SHORT).show();
                                        productSpecificationModelList.add(new ProductSpecificationModel(1, documentSnapshot.get("spec_title_" + x + "_field_" + j + "_Name").toString(), documentSnapshot.get("spec_title_" + x + "_field_" + j + "_Value").toString()));

                                    }
                                }
                            } else {
                                productDescriptionOnlyContainer.setVisibility(View.VISIBLE);
                                productTabContainer.setVisibility(View.INVISIBLE);
                                productDetailsTabLayout.setVisibility(View.GONE);
                                productDescriptionBody.setText(documentSnapshot.get("product_other_detail").toString());

                            }
                            totalRatings.setText("Total Review:" + (long) documentSnapshot.get("total_ratings"));
                            for (int i = 0; i < 5; i++) {
                                TextView ratings = (TextView) rating_number_container.getChildAt(i);
                                ratings.setText(String.valueOf(documentSnapshot.get((5 - i) + "_star")));
                                // Toast.makeText(ProductDetailsActivity.this, "Rating:"+(documentSnapshot.get((6-i)+"_star")), Toast.LENGTH_SHORT).show();
                                int max_profress = Integer.parseInt(String.valueOf((long) documentSnapshot.get("total_ratings")));
                                ProgressBar progressBar = (ProgressBar) ratingdProgrsContainer.getChildAt(i);
                                progressBar.setMax(max_profress);
                                progressBar.setProgress(Integer.parseInt(String.valueOf(documentSnapshot.get((5 - i) + "_star"))));


                            }
                            totalRatingFigure.setText(String.valueOf((long) documentSnapshot.get("total_ratings")));
                            AvgRating.setText(documentSnapshot.get("Avg_rating").toString());
                            productDetailsViewPager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(), productDetailsTabLayout.getTabCount(), productDescriptions, otherDescription, productSpecificationModelList));


                            if (currentUser != null) {
                                if (DBQueries.wishList.size() == 0)
                                    DBQueries.loadWishList(ProductDetailsActivity.this, LoadingDialog, false);
                                else
                                    LoadingDialog.dismiss();
                                if (DBQueries.myRating.size() == 0) {
                                    DBQueries.loadRatingList(ProductDetailsActivity.this);
                                } else
                                    LoadingDialog.dismiss();
                                if (DBQueries.cartList.size() == 0) {
                                    DBQueries.loadCart(ProductDetailsActivity.this, LoadingDialog, false, badgeCount, new TextView(ProductDetailsActivity.this));
                                }

                            }
                            else
                                LoadingDialog.dismiss();

                            if (DBQueries.myRatedId.contains(productId)) {
                                int index = DBQueries.myRatedId.indexOf(productId);
                                intialRating = Integer.parseInt(String.valueOf(DBQueries.myRating.get(index))) - 1;
                                setRating(intialRating);
                            }
                            if (DBQueries.cartList.contains(productId)) {
                                ALREADY_ADDED_TO_CART = true;
                            } else {
                                ALREADY_ADDED_TO_CART = false;
                            }


                            if (DBQueries.wishList.contains(productId)) {
                                ALREADYADDED_TO_WISHLIST = true;
                                add_to_wishList_btn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
                            } else {
                                add_to_wishList_btn.setSupportImageTintList(getResources().getColorStateList(R.color.black));
                                ALREADYADDED_TO_WISHLIST = false;
                            }
                            if((boolean)documentSnapshot.get("in_stock"))
                            {

                                addToCartBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (currentUser == null) {
                                            SignInDialog.show();
                                        } else {

                                            if (!running_cart_query) {
                                                running_cart_query = true;
                                                if (ALREADY_ADDED_TO_CART) {
                                                    running_cart_query = false;
                                                    int index = DBQueries.cartList.indexOf(productId);
                                                    Toast.makeText(ProductDetailsActivity.this, "Already Added to cart", Toast.LENGTH_SHORT).show();
                                                    ALREADY_ADDED_TO_CART = true;
                                                } else {

                                                    Map<String, Object> updateListSize = new HashMap<>();
                                                    updateListSize.put("product_ID_" + (DBQueries.cartList.size()), productId);
                                                    updateListSize.put("list_size", (long) DBQueries.cartList.size() + 1);
                                                    firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("MY_CART")
                                                            .update(updateListSize).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                if (DBQueries.cartItemModelList.size() != 0) {
                                                                    DBQueries.cartItemModelList.add(0, new CartItemModel(CartItemModel.CART_ITEM,
                                                                            productId,
                                                                            documentSnapshot.get("product_title").toString(),
                                                                            documentSnapshot.get("product_price").toString(),
                                                                            documentSnapshot.get("cutted_price").toString(),
                                                                            documentSnapshot.get("product_image_1").toString(),
                                                                            (long) documentSnapshot.get("free_coupons"),
                                                                            (long) 1,
                                                                            (long) 1,
                                                                            (long) 1,
                                                                            (boolean)documentSnapshot.get("in_stock"),
                                                                            (long)documentSnapshot.get("max-quantity")));
                                                                }
                                                                ALREADY_ADDED_TO_CART = true;
                                                                DBQueries.cartList.add(productId);
                                                                Toast.makeText(ProductDetailsActivity.this, "Added to Cart  Successfully", Toast.LENGTH_SHORT).show();

                                                                running_cart_query = false;
                                                                invalidateOptionsMenu();
                                                            } else {
                                                                invalidateOptionsMenu();
                                                                running_cart_query = false;
                                                                String error = task.getException().getMessage();
                                                                Toast.makeText(ProductDetailsActivity.this, "Error:" + error, Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });

                                                }
                                            }
                                        }
                                    }
                                });
                            }else
                            {
                                buyNowBtn.setVisibility(View.GONE);
                                TextView outOfStock  = (TextView) addToCartBtn.getChildAt(0);
                                outOfStock.setText("Out of Stock");
                                outOfStock.setTextColor(getResources().getColor(R.color.colorPrimary));
                                outOfStock.setCompoundDrawables(null, null,null,null);

                             }

                        } else {
                            LoadingDialog.dismiss();
                            String error = task.getException().getMessage();
                            Toast.makeText(ProductDetailsActivity.this, "Erro:" + error, Toast.LENGTH_SHORT).show();
                        }

                    }
                });


        viewPagerIndicator.setupWithViewPager(productImagesViewPager, true);
        productDetailsViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDetailsTabLayout));
        productDetailsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                productDetailsViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        // Rating Now Layout//
        rateNowContainer = findViewById(R.id.rate_now_container);
        for (int i = 0; i < rateNowContainer.getChildCount(); i++) {
            final int star_position = i;
            rateNowContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentUser == null) {
                        SignInDialog.show();
                    } else {
                        if (star_position != intialRating) {
                            if (!running_rating_query) {
                                running_rating_query = true;
                                setRating(star_position);
                                final Map<String, Object> updateRating = new HashMap<>();
                                if (DBQueries.myRatedId.contains(productId)) {
//                                    setRating(star_position);
                                    TextView oldRatings = (TextView) rating_number_container.getChildAt(5 - intialRating - 1);
                                    TextView finalRatings = (TextView) rating_number_container.getChildAt(5 - star_position - 1);
                                    updateRating.put(intialRating + 1 + "_star", Long.parseLong(oldRatings.getText().toString()) - 1);
                                    updateRating.put(star_position + 1 + "_star", Long.parseLong(finalRatings.getText().toString()) + 1);
                                    updateRating.put("Avg_rating", (calculateAvgRating((long) star_position - intialRating, true)));
                                } else {

                                    updateRating.put(star_position + 1 + "_star", (long) documentSnapshot.get(star_position + 1 + "_star") + 1);
                                    updateRating.put("Avg_rating", calculateAvgRating((long) star_position + 1, false));
                                    updateRating.put("total_ratings", (long) documentSnapshot.get("total_ratings") + 1);

                                }
                                firebaseFirestore.collection("PRODUCTS").document(productId)
                                        .update(updateRating).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {

                                            final Map<String, Object> Myrating = new HashMap<>();
                                            if (DBQueries.myRatedId.contains(productId)) {
                                                Myrating.put("rating_" + (long) DBQueries.myRatedId.indexOf(productId), (long) star_position + 1);

                                            } else {
                                                Myrating.put("list_size", (long) DBQueries.myRatedId.size() + 1);
                                                Myrating.put("PRODUCT_ID_" + DBQueries.myRatedId.size(), productId);
                                                Myrating.put("rating_" + DBQueries.myRatedId.size(), (long) star_position + 1);

                                            }
                                            firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("MY_RATINGS")
                                                    .update(Myrating).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        if (DBQueries.myRatedId.contains(productId)) {

                                                            DBQueries.myRating.set(DBQueries.myRatedId.indexOf(productId), (long) star_position + 1);
                                                            TextView oldRatings = (TextView) rating_number_container.getChildAt(5 - intialRating - 1);
                                                            TextView finalRatings = (TextView) rating_number_container.getChildAt(5 - star_position - 1);
                                                            oldRatings.setText(String.valueOf(Integer.parseInt(oldRatings.getText().toString()) - 1));
                                                            finalRatings.setText(String.valueOf(Integer.parseInt(finalRatings.getText().toString()) + 1));
                                                            updateRating.put("Avg_rating", calculateAvgRating((long) star_position - intialRating, true));

                                                        } else {
                                                            DBQueries.myRatedId.add(productId);
                                                            DBQueries.myRating.add((long) star_position + 1);
                                                            total_rating.setText("(" + ((long) documentSnapshot.get("total_ratings") + 1) + ")");
                                                            totalRatingFigure.setText(String.valueOf((long) documentSnapshot.get("total_ratings") + 1));
                                                            totalRatings.setText("Total Review: " + documentSnapshot.get("total_ratings"));
                                                            TextView ratings = (TextView) rating_number_container.getChildAt(5 - star_position - 1);
                                                            ratings.setText(String.valueOf(Integer.parseInt(ratings.getText().toString()) + 1));
                                                            Toast.makeText(ProductDetailsActivity.this, "ThankYou for Rating", Toast.LENGTH_SHORT).show();
                                                        }
                                                        intialRating = star_position;
                                                        AvgRating.setText(calculateAvgRating(0, true));
                                                        product_avg_rating.setText((calculateAvgRating(0, true)));

                                                        if (DBQueries.wishList.contains(productId) && DBQueries.wishlistModelList.size() != 0) {
                                                            int index = DBQueries.wishList.indexOf(productId);
                                                            WishlistModel change = DBQueries.wishlistModelList.get(index);
                                                            DBQueries.wishlistModelList.get(index).setRating(AvgRating.getText().toString());
                                                            DBQueries.wishlistModelList.get(index).setTotal_rating(Long.parseLong(totalRatingFigure.getText().toString()));


                                                        }
                                                        for (int i = 0; i < 5; i++) {
                                                            TextView ratingfigures = (TextView) rating_number_container.getChildAt(i);
                                                            ProgressBar progressBar = (ProgressBar) ratingdProgrsContainer.getChildAt(i);
                                                            if (DBQueries.myRatedId.contains(productId)) {
                                                                int max_profress = Integer.parseInt(String.valueOf((long) documentSnapshot.get("total_ratings") + 1));
                                                                progressBar.setMax(max_profress);
                                                            }
                                                            progressBar.setProgress(Integer.parseInt(ratingfigures.getText().toString()));
                                                        }
                                                        intialRating = star_position;
                                                    } else {
                                                        setRating(intialRating);
                                                        Toast.makeText(ProductDetailsActivity.this, "Error:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                                    }
                                                    running_rating_query = false;
                                                }
                                            });

                                        } else {
                                            running_rating_query = false;
                                            setRating(intialRating);
                                            Toast.makeText(ProductDetailsActivity.this, "Error:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });
                            }
                        }
                    }

                }
            });

        }

        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser == null) {
                    SignInDialog.show();
                } else {
                    DeliveryActivity.fromCart=false;
                    LoadingDialog.show();
                    productDetailsActivity = ProductDetailsActivity.this;
                    DeliveryActivity.cartItemModelList = new ArrayList<>();
                    DeliveryActivity.cartItemModelList.add(new CartItemModel(CartItemModel.CART_ITEM,
                            productId,
                            documentSnapshot.get("product_title").toString(),
                            documentSnapshot.get("product_price").toString(),
                            documentSnapshot.get("cutted_price").toString(),
                            documentSnapshot.get("product_image_1").toString(),
                            (long) documentSnapshot.get("free_coupons"),
                            (long) 1,
                            (long) 0,
                            (long) 0,
                            (boolean)documentSnapshot.get("in_stock"),
                            (long)documentSnapshot.get("max-quantity")));
                    DeliveryActivity.cartItemModelList.add(new CartItemModel(CartItemModel.TOTAL_AMOUNT));
                    if(DBQueries.addressModelList.size() ==0)
                    {
                        DBQueries.loadAddress(ProductDetailsActivity.this,LoadingDialog);
                    }else{
                        LoadingDialog.dismiss();
                        Intent deliveryIntent = new Intent(ProductDetailsActivity.this, DeliveryActivity.class);
                        startActivity(deliveryIntent);
                    }
                }

            }
        });

        add_to_wishList_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser == null) {
                    SignInDialog.show();
                } else {

                    // add_to_wishList_btn.setEnabled(true);
                    if (!running_wishlist_query) {
                        running_wishlist_query = true;
                        if (ALREADYADDED_TO_WISHLIST) {

                            int index = DBQueries.wishList.indexOf(productId);
                            DBQueries.removeFromWishList(ProductDetailsActivity.this, index);
                            add_to_wishList_btn.setSupportImageTintList(getResources().getColorStateList(R.color.black));
                            ALREADYADDED_TO_WISHLIST = false;

                        } else {

                            add_to_wishList_btn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
                            Map<String, Object> addProduct = new HashMap<>();
                            addProduct.put("Product_ID_"+ String.valueOf(DBQueries.wishList.size()), productId);

                            firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("MY_WISHLIST")
                                    .update(addProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        Map<String, Object> updateListSize  = new HashMap<>();
                                        updateListSize.put("list_size", DBQueries.wishList.size()+1);

                                        firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("MY_WISHLIST")
                                                .update(updateListSize).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){

                                                    if (DBQueries.wishlistModelList.size() != 0) {
                                                        DBQueries.wishlistModelList.add(new WishlistModel(productId, documentSnapshot.get("product_image_1").toString(),
                                                                (long) documentSnapshot.get("product_coupons"),
                                                                documentSnapshot.get("Avg_rating").toString(),
                                                                documentSnapshot.get("product_title").toString(),
                                                                (long) documentSnapshot.get("total_ratings"),
                                                                documentSnapshot.get("product_price").toString(),
                                                                documentSnapshot.get("product_cutted_price").toString(),
                                                                (boolean) documentSnapshot.get("product_COD"),
                                                                (boolean) documentSnapshot.get("in_stock")
                                                        ));
                                                    }

                                                    ALREADYADDED_TO_WISHLIST = true;
                                                    add_to_wishList_btn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
                                                    Toast.makeText(ProductDetailsActivity.this, "Added to WishList  Successfully", Toast.LENGTH_SHORT).show();
                                                    DBQueries.wishList.add(productId);
                                                }else
                                                {

                                                    String error = task.getException().getMessage();
                                                    Toast.makeText(ProductDetailsActivity.this, "Error:" + error, Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });

                                    }

                                   // add_to_wishList_btn.setEnabled(true);
                                    running_wishlist_query = false;
                                }
                            });

                        }
                    }

                }
            }
        });




        // SignUp Dialog//

        SignInDialog = new Dialog(ProductDetailsActivity.this);
        SignInDialog.setContentView(R.layout.signin_dialog);
        SignInDialog.setCancelable(true);
        SignInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button dialogsignIn = SignInDialog.findViewById(R.id.signInBtn);
        Button dialogsignUp = SignInDialog.findViewById(R.id.signUpBtn);
        final Intent registerIntent = new Intent(ProductDetailsActivity.this, RegisterActivity.class);

        dialogsignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInFragment.disabledBtn = true;
                SignUpFragment.disabledBtn = true;
                SignInDialog.dismiss();
                setSignUpFragment = false;
                startActivity(registerIntent);


            }
        });
        dialogsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpFragment.disabledBtn = true;
                SignInFragment.disabledBtn = true;
                SignInDialog.dismiss();
                setSignUpFragment = true;
                startActivity(registerIntent);


            }
        });

        // Sign Up Dialog//
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {

            if(DBQueries.cartList.size() ==0)
            {
                DBQueries.loadCart(ProductDetailsActivity.this, LoadingDialog, false, badgeCount, new TextView(ProductDetailsActivity.this));
                Toast.makeText(this, "Goes for menu update", Toast.LENGTH_SHORT).show();
            }

            if (DBQueries.myRating.size() == 0) {
                DBQueries.loadRatingList(ProductDetailsActivity.this);
            } else
                LoadingDialog.dismiss();
            if (DBQueries.wishList.size() == 0) {

                DBQueries.loadWishList(ProductDetailsActivity.this, LoadingDialog, false);
            }else
            {
                LoadingDialog.dismiss();
            }
        } else
            LoadingDialog.dismiss();

        if (DBQueries.myRatedId.contains(productId)) {
            int index = DBQueries.myRatedId.indexOf(productId);
            intialRating = Integer.parseInt(String.valueOf(DBQueries.myRating.get(index))) - 1;
            setRating(intialRating);
        }
        if (DBQueries.wishList.contains(productId)) {
            ALREADYADDED_TO_WISHLIST = true;
            add_to_wishList_btn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
        } else {
            ALREADYADDED_TO_WISHLIST = false;
            add_to_wishList_btn.setSupportImageTintList(getResources().getColorStateList(R.color.black));

        }
        if (DBQueries.cartList.contains(productId)) {
            ALREADY_ADDED_TO_CART = true;
        } else {
            ALREADY_ADDED_TO_CART = false;
        }
        invalidateOptionsMenu();



    }

    private String calculateAvgRating(long currentUserRating, boolean update) {
        Double totalStars = Double.valueOf(0);

        for (int i = 1; i < 6; i++) {
            TextView ratingNo = (TextView) rating_number_container.getChildAt(5 - i);

            totalStars = totalStars + (Long.parseLong(ratingNo.getText().toString()) * i);

        }
        totalStars = totalStars + currentUserRating;
        if (update) {
            return String.valueOf(totalStars / Long.parseLong(totalRatingFigure.getText().toString())).substring(0, 3);
        } else {
            return String.valueOf(totalStars / Long.parseLong(totalRatingFigure.getText().toString() + 1)).substring(0, 3);
        }


    }

    public static void setRating(int star_position) {
        for (int x = 0; x < rateNowContainer.getChildCount(); x++) {
            ImageView starbtn = (ImageView) rateNowContainer.getChildAt(x);
            starbtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
            if (x <= star_position) {
                starbtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.product_details_menu, menu);

           cartItem = menu.findItem(R.id.product_details_cart);

            cartItem.setActionView(R.layout.badge_layout);
            badgeCount = cartItem.getActionView().findViewById(R.id.badge_count);
            ImageView badgeIcon = cartItem.getActionView().findViewById(R.id.badge_icon);
            badgeIcon.setImageResource(R.mipmap.shopping_cart);

                if(currentUser!=null) {
                    if (DBQueries.cartList.size() == 0) {
                        DBQueries.loadCart(ProductDetailsActivity.this, LoadingDialog, false, badgeCount, new TextView(ProductDetailsActivity.this));
                    }
                }else
                {
                    badgeCount.setVisibility(View.VISIBLE);
                    if(DBQueries.cartList.size()<99)
                    {
                        badgeCount.setText(String.valueOf(DBQueries.cartList.size()));
                    }else
                    {
                        badgeCount.setText("99");
                    }
                }


            cartItem.getActionView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (currentUser == null) {
                        SignInDialog.show();
                    } else {
                        Intent cartIntent = new Intent(ProductDetailsActivity.this, MainActivity.class);
                        MainActivity.showCart = true;
                        startActivity(cartIntent);
                    }
                }
            });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            productDetailsActivity = null;
            finish();
            return true;
        } else if (id == R.id.product_details_search) {
            return true;
        } else if (id == R.id.product_details_cart) {
            if (currentUser == null) {
                SignInDialog.show();
            } else {

                Intent cartIntent = new Intent(ProductDetailsActivity.this, MainActivity.class);
                MainActivity.showCart = true;
                startActivity(cartIntent);

                return true;
            }

        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        productDetailsActivity = null;

        super.onBackPressed();
    }
}
