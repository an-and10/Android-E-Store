package com.example.e_store;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class DeliveryActivity extends AppCompatActivity {
    private RecyclerView deliveryRecyclerView;
    private Button changeaddressbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Delivery Address");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        deliveryRecyclerView = findViewById(R.id.delivery_recyclerview);
        changeaddressbtn = findViewById(R.id.change_or_add_address_btn);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        deliveryRecyclerView.setLayoutManager(linearLayoutManager);

        List<CartItemModel> cartItemModelList = new ArrayList<>();
        cartItemModelList.add(new CartItemModel(0,"Google Pixel", "Rs. 78000", "Rs. 89999", R.drawable.phone1,2,2,2,2 ));
        cartItemModelList.add(new CartItemModel(0,"CoolPad X-5", "Rs. 56000", "Rs. 39999", R.drawable.phone2,3,1,2,1 ));
        cartItemModelList.add(new CartItemModel(0,"Nokia Ms-23", "Rs. 78000", "Rs. 89999", R.drawable.phone1,2,2,2,2 ));
        cartItemModelList.add(new CartItemModel(1, "Price(3 items)", "Rs.120345", "Free", "Rs.120345", "45%"));

        CartAdapter cartAdapter = new CartAdapter(cartItemModelList);
        deliveryRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        changeaddressbtn.setVisibility(View.VISIBLE);
        changeaddressbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myAddresses = new Intent(DeliveryActivity.this, MyAddressesActivity.class);
                startActivity(myAddresses);
            }
        });

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
}
