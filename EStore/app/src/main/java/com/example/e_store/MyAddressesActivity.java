package com.example.e_store;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MyAddressesActivity extends AppCompatActivity {
    private RecyclerView MyAddressesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_addresses);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("My Address");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MyAddressesRecyclerView = findViewById(R.id.address_recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        MyAddressesRecyclerView.setLayoutManager(linearLayoutManager);

        List<AddressModel> addressModelList = new ArrayList<>();
        addressModelList.add(new AddressModel("Anand Maurya", "Mumbai, Maha", "123456"));
        addressModelList.add(new AddressModel("Anand Maurya", "Delhi, Maha", "123213"));
        addressModelList.add(new AddressModel("Anand Maurya", "Mumbai, Maha", "123456"));
        addressModelList.add(new AddressModel("Anand Maurya", "Mumbai, Maha", "123456"));
        addressModelList.add(new AddressModel("Anand Maurya", "Mumbai, Maha", "123456"));
        addressModelList.add(new AddressModel("Anand Maurya", "Mumbai, Maha", "123456"));
        addressModelList.add(new AddressModel("Anand Maurya", "Mumbai, Maha", "123456"));

        AddressAdapter addressAdapter = new AddressAdapter(addressModelList);
        MyAddressesRecyclerView.setAdapter(addressAdapter);
        addressAdapter.notifyDataSetChanged();




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id ==android.R.id.home)
        {
            finish();
            return true;
        }
        return true;
    }
}
