package com.example.e_cart;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1beta1.StructuredQuery;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DeliveryActivity extends AppCompatActivity {
    private RecyclerView deliveryRecyclerView;
    private Button changeaddressbtn;
    public static final int SELECT_ADDRESS  =0;
    private TextView totalAmount ;
    private  TextView fullName ;
    private  String name,mobile;
    private TextView fullAddress ;
     private  TextView pincode;
     private  Button contineBtn;
     private Dialog LoadingDialog;
     private  Dialog paymentMethodDialog;
     private ImageButton paytm, CODBtn;
     private ConstraintLayout orderConfirmationLayout;
     private  ImageButton continueShoppingBtn;
     private TextView orderIdTextView;
     private   String orderId;
     public  static boolean codOrderConfirmed = false;
     private boolean successResponse = false;
     public  static boolean fromCart;
     private boolean allProductAvailable = true;
     public static boolean getQuantityIds = true;



     private  FirebaseFirestore firebaseFirestore;



     public static  List<CartItemModel> cartItemModelList ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Delivery Address");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        orderConfirmationLayout = findViewById(R.id.order_confirmation_layout);
        continueShoppingBtn = findViewById(R.id.homeshoppingBtn);
        orderIdTextView = findViewById(R.id.order_id);
        firebaseFirestore = FirebaseFirestore.getInstance();

        getQuantityIds = true;

        LoadingDialog = new Dialog(DeliveryActivity.this);
        LoadingDialog.setContentView(R.layout.loading_progress_dialog);
        LoadingDialog.setCancelable(false);
        LoadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        LoadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);


        paymentMethodDialog = new Dialog(DeliveryActivity.this);
        paymentMethodDialog.setContentView(R.layout.payment_method);
        paymentMethodDialog.setCancelable(true);
        paymentMethodDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        paymentMethodDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        paytm = paymentMethodDialog.findViewById(R.id.paytmBtn);
        CODBtn  =paymentMethodDialog.findViewById(R.id.codBtn);
        orderId = UUID.randomUUID().toString().substring(0,20);




        deliveryRecyclerView = findViewById(R.id.delivery_recyclerview);
        changeaddressbtn = findViewById(R.id.change_or_add_address_btn);
        totalAmount = findViewById(R.id.total_cart_price);
        fullName   = findViewById(R.id.full_name);
        fullAddress = findViewById(R.id.shipping_address);
        pincode = findViewById(R.id.pin);
        contineBtn = findViewById(R.id.cart_continue_btn);



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        deliveryRecyclerView.setLayoutManager(linearLayoutManager);


        CartAdapter cartAdapter = new CartAdapter(cartItemModelList,totalAmount, false );
        deliveryRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        changeaddressbtn.setVisibility(View.VISIBLE);
        changeaddressbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getQuantityIds = false;
                Intent myAddresses = new Intent(DeliveryActivity.this, MyAddressesActivity.class);
                myAddresses.putExtra("MODE",SELECT_ADDRESS);
                startActivity(myAddresses);
            }
        });



        contineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(allProductAvailable){
                    paymentMethodDialog.show();
                } else{

                }



            }
        });
        CODBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getQuantityIds = false;
                paymentMethodDialog.dismiss();
                Intent otpActivity = new Intent(DeliveryActivity.this, OTPVerificationActivity.class);
                otpActivity.putExtra("MOBILE",mobile.substring(0,10) );

                startActivity(otpActivity);

            }
        });

        paytm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                getQuantityIds =false;
                paymentMethodDialog.dismiss();
                LoadingDialog.show();
                if (ContextCompat.checkSelfPermission(DeliveryActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(DeliveryActivity.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
                }


                final String M_Id = "yOZGwI26305810896150";
                final String customer_id = FirebaseAuth.getInstance().getUid();

                String url = "https://knowaboutus.000webhostapp.com/paytm/generateChecksum.php";
                final String callback = "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp";

                RequestQueue requestQueue = Volley.newRequestQueue(DeliveryActivity.this);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.has("CHECKSUMHASH"))
                            {
                                String CHECKSUMHASH = jsonObject.getString("CHECKSUMHASH");

                                PaytmPGService paytmPGService = PaytmPGService.getStagingService();

                                HashMap<String, String> paramMap = new HashMap<String,String>();

                                paramMap.put( "MID" , M_Id);
                                paramMap.put( "ORDER_ID" , orderId);
                                paramMap.put( "CUST_ID" , customer_id);
                                paramMap.put( "CHANNEL_ID" , "WAP");
                                paramMap.put( "TXN_AMOUNT" , totalAmount.getText().toString().substring(3,totalAmount.getText().length()-2));
                                paramMap.put( "WEBSITE" , "WEBSTAGING");
                                paramMap.put( "INDUSTRY_TYPE_ID" , "Retail");
                                paramMap.put( "CALLBACK_URL", callback);
                                paramMap.put("CHECKSUMHASH", CHECKSUMHASH);

                                PaytmOrder paytmOrder = new PaytmOrder(paramMap);

                                paytmPGService.initialize(paytmOrder, null);


                                paytmPGService.startPaymentTransaction(DeliveryActivity.this, true, true, new PaytmPaymentTransactionCallback() {
                                    @Override
                                    public void onTransactionResponse(Bundle inResponse) {
                                       // Toast.makeText(getApplicationContext(), "Payment Transaction response " + inResponse.toString(), Toast.LENGTH_LONG).show();
                                        if(inResponse.getString("STATUS").equals("TXN_SUCCESS")) {
                                           showConfirmationLayout();
                                        }


                                    }

                                    @Override
                                    public void networkNotAvailable() {
                                        Toast.makeText(getApplicationContext(), "Network connection error: Check your internet connectivity", Toast.LENGTH_LONG).show();

                                    }

                                    @Override
                                    public void clientAuthenticationFailed(String inErrorMessage) {
                                        Toast.makeText(getApplicationContext(), "Authentication failed: Server error" + inErrorMessage.toString(), Toast.LENGTH_LONG).show();

                                    }

                                    @Override
                                    public void someUIErrorOccurred(String inErrorMessage) {
                                        Toast.makeText(getApplicationContext(), "UI Error " + inErrorMessage , Toast.LENGTH_LONG).show();

                                    }

                                    @Override
                                    public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
                                        Toast.makeText(getApplicationContext(), "Unable to load webpage " + inErrorMessage.toString(), Toast.LENGTH_LONG).show();


                                    }

                                    @Override
                                    public void onBackPressedCancelTransaction() {
                                        Toast.makeText(getApplicationContext(), "Transaction cancelled" , Toast.LENGTH_LONG).show();

                                    }

                                    @Override
                                    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                                        Toast.makeText(getApplicationContext(), "Transaction cancelled" , Toast.LENGTH_LONG).show();


                                    }
                                });


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        LoadingDialog.dismiss();
                        Toast.makeText(DeliveryActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> paramMap = new HashMap<String,String>();
                        paramMap.put( "MID" , M_Id);

                        paramMap.put( "ORDER_ID" , orderId);
                        paramMap.put( "CUST_ID" , customer_id);

                        paramMap.put( "CHANNEL_ID" , "WAP");
                        paramMap.put( "TXN_AMOUNT" , totalAmount.getText().toString().substring(3,totalAmount.getText().length()-2));
                        paramMap.put( "WEBSITE" , "WEBSTAGING");
                        paramMap.put( "INDUSTRY_TYPE_ID" , "Retail");
                        paramMap.put( "CALLBACK_URL", callback);
                        return paramMap;
                    }
                };

                requestQueue.add(stringRequest);
            }
        });



//        fullName.setText(DBQueries.addressModelList.get(DBQueries.selectedAddress).getName());
//        fullAddress.setText(DBQueries.addressModelList.get(DBQueries.selectedAddress).getAddress());
//        pincode.setText(DBQueries.addressModelList.get(DBQueries.selectedAddress).getPincode());


    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
       if(id == android.R.id.home)
       {
           finish();
           return true;
       }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {

         super.onStart();


        // Accesing quantity//
            if(getQuantityIds) {


                for (int x = 0; x < cartItemModelList.size() - 1; x++) {
                    final int finalX = x;
                    final int finalX1 = x;
                    firebaseFirestore.collection("PRODUCTS").document(cartItemModelList.get(x).getProductId()).collection("QUANTITY").orderBy("available", Query.Direction.DESCENDING).limit(cartItemModelList.get(x).getQuantity()).get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {

                                        for (final QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                                            if ((boolean) queryDocumentSnapshot.get("available")) {

                                                firebaseFirestore.collection("PRODUCTS").document(cartItemModelList.get(finalX1).getProductId()).collection("QUANTITY").document(queryDocumentSnapshot.getId()).update("available", false)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {

                                                                    cartItemModelList.get(finalX).getQtyIds().add(queryDocumentSnapshot.getId());

                                                                } else {
                                                                    Toast.makeText(DeliveryActivity.this, "Error Task Fail " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                                                }
                                                            }
                                                        });


                                            } else {
                                                allProductAvailable = false;
                                                Toast.makeText(DeliveryActivity.this, "Error in Purchasing, All Product may not available", Toast.LENGTH_SHORT).show();
                                                break;

                                                //Not Available
                                            }

                                        }
                                    } else {

                                        Toast.makeText(DeliveryActivity.this, "Error Task Fail " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }else{
                getQuantityIds = true;
            }
         name = DBQueries.addressModelList.get(DBQueries.selectedAddress).getName();
         mobile  = DBQueries.addressModelList.get(DBQueries.selectedAddress).getMobile();
         fullName.setText(name + "-" + mobile);

     //   Toast.makeText(this, "value: "+DBQueries.addressModelList.get(DBQueries.selectedAddress).getName(), Toast.LENGTH_SHORT).show();

        fullAddress.setText(DBQueries.addressModelList.get(DBQueries.selectedAddress).getAddress());
        pincode.setText(DBQueries.addressModelList.get(DBQueries.selectedAddress).getPincode());

        if(codOrderConfirmed)
        {
            showConfirmationLayout();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        LoadingDialog.dismiss();
        if(getQuantityIds){

        for(int i=0;i<cartItemModelList.size()-1;i++){

           if(!successResponse) {
               for (String quantitYd : cartItemModelList.get(i).getQtyIds()) {
                   firebaseFirestore.collection("PRODUCTS").document(cartItemModelList.get(i).getProductId()).collection("QUANTITY").document(quantitYd).update("available", true);


               }
           }
            cartItemModelList.get(i).getQtyIds().clear();
            }
        }
    }

    private void showConfirmationLayout()
    {
        successResponse = true;
        codOrderConfirmed =false;
        getQuantityIds = false;
        String SMS_API =  "https://www.fast2sms.com/dev/bulk";

        for(int i=0;i<cartItemModelList.size()-1;i++){
            for(String quantitYd : cartItemModelList.get(i).getQtyIds()){
                firebaseFirestore.collection("PRODUCTS").document(cartItemModelList.get(i).getProductId()).collection("QUANTITY") .document(quantitYd).update("user_ID", FirebaseAuth.getInstance().getUid());


            }
        }

        if(MainActivity.mainActivity != null)
        {
            MainActivity.mainActivity.finish();
            MainActivity.mainActivity = null;
            MainActivity.showCart =false;
        }else{
            MainActivity.resetMainActivity = true;
        }
        if(ProductDetailsActivity.productDetailsActivity != null)
        {
            ProductDetailsActivity.productDetailsActivity.finish();
            ProductDetailsActivity.productDetailsActivity  = null;

        }

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, SMS_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // Notjhing to do
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
        /// nothing to do
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> header = new HashMap<>();
                header.put("authorization", "WoITlYuwrOP6dyFC8gZteN79RxbscAEpanqvkDh4L5SK0zfUQmcCqMTgObZKit2eNRu6X14Q3Er0mP5f");
                return  header;

            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> body = new HashMap<>();
                body.put("sender_id", "FSTSMS");
                body.put("language", "english");
                body.put("route", "qt");
                body.put("numbers", mobile);
                body.put("message", "19304");
                body.put("variables", "{#FF#}");
                body.put("variables_values", orderId);

                return body;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        RequestQueue requestQueue = Volley.newRequestQueue(DeliveryActivity.this);
        requestQueue.add(stringRequest);


        if(fromCart){
            LoadingDialog.show();

            Map<String, Object> updateCartList = new HashMap<>();
            final List<Integer> indexList = new ArrayList<>();

            long cartListSize =0;

            for (int i = 0; i < DBQueries.cartList.size(); i++) {
                if( !cartItemModelList.get(i).isInStock()){
                    updateCartList.put("product_ID_"+ cartListSize, cartItemModelList.get(i).getProductId());
                    cartListSize++;

                }else{
                    indexList.add(i);

                }

            }
            updateCartList.put("list_size", cartListSize);
            FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_CART")
                    .set(updateCartList).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        for(int i=0;i<indexList.size();i++)
                        {
                            DBQueries.cartList.remove(indexList.get(i).intValue());
                            DBQueries.cartItemModelList.remove(indexList.get(i).intValue());
                            DBQueries.cartItemModelList.remove(DBQueries.cartItemModelList.size()-1);

                        }

                    }else{
                        Toast.makeText(DeliveryActivity.this, "Error in Updating", Toast.LENGTH_SHORT).show();

                    }
                    LoadingDialog.dismiss();
                }
            });
        }

        contineBtn.setEnabled(false);
        orderIdTextView.setText("Order ID : "+ orderId);
        orderConfirmationLayout.setVisibility(View.VISIBLE);
        continueShoppingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        if(successResponse)
        {
            finish();
            return;
        }
        super.onBackPressed();
    }
}
