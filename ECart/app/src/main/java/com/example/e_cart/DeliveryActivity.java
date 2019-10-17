package com.example.e_cart;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
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
    private TextView fullAddress ;
     private  TextView pincode;
     private  Button contineBtn;
     private Dialog LoadingDialog;
     private  Dialog paymentMethodDialog;
     private ImageButton paytm;
     private ConstraintLayout orderConfirmationLayout;
     private  ImageButton continueShoppingBtn;
     private TextView orderIdTextView;

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
                Intent myAddresses = new Intent(DeliveryActivity.this, MyAddressesActivity.class);
                myAddresses.putExtra("MODE",SELECT_ADDRESS);
                startActivity(myAddresses);
            }
        });



        contineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    paymentMethodDialog.show();

            }
        });

        paytm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                paymentMethodDialog.dismiss();
                LoadingDialog.show();
                if (ContextCompat.checkSelfPermission(DeliveryActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(DeliveryActivity.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
                }

                final String M_Id = "yOZGwI26305810896150";
                final String customer_id = FirebaseAuth.getInstance().getUid();
                final String orderId = UUID.randomUUID().toString().substring(0,20);
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
                                        if(inResponse.getString("STATUS").equals("TXN_SUCCESS")){
                                            if(MainActivity.mainActivity != null)
                                            {
                                                MainActivity.mainActivity.finish();
                                                MainActivity.mainActivity = null;
                                                MainActivity.showCart =false;
                                            }
                                            if(ProductDetailsActivity.productDetailsActivity != null)
                                            {
                                                ProductDetailsActivity.productDetailsActivity.finish();
                                                ProductDetailsActivity.productDetailsActivity  = null;

                                            }
                                            orderIdTextView.setText("Order ID : "+ inResponse.getString("ORDERID"));

                                            orderConfirmationLayout.setVisibility(View.VISIBLE);

                                            continueShoppingBtn.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                finish();
                                                }
                                            });

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
     //   Toast.makeText(this, "value: "+DBQueries.addressModelList.get(DBQueries.selectedAddress).getName(), Toast.LENGTH_SHORT).show();
        fullName.setText(DBQueries.addressModelList.get(DBQueries.selectedAddress).getName());
        fullAddress.setText(DBQueries.addressModelList.get(DBQueries.selectedAddress).getAddress());
        pincode.setText(DBQueries.addressModelList.get(DBQueries.selectedAddress).getPincode());

    }

    @Override
    protected void onPause() {
        super.onPause();
        LoadingDialog.dismiss();
    }
}
