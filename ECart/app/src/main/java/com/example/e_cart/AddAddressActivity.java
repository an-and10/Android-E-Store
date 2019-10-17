package com.example.e_cart;

import android.app.Dialog;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class AddAddressActivity extends AppCompatActivity {
    private Button saveBtn;
    private EditText city;
    private EditText locality;
    private EditText flatNo;
    private EditText pincode;
    private EditText landmark;
    private EditText name;
    private EditText mobile;
    private EditText alternatemobile;
    private Spinner stateSpinner;
    private  String[]  stateList;
    private  String stateSelected ;
    private  Dialog LoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Add New Address");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LoadingDialog = new Dialog(this);
        LoadingDialog.setContentView(R.layout.loading_progress_dialog);
        LoadingDialog.setCancelable(false);
        LoadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        LoadingDialog.getWindow().setBackgroundDrawable(this.getDrawable(R.drawable.slider_background));
         stateList= getResources().getStringArray(R.array.india_states);
        city = findViewById(R.id.city);
        locality = findViewById(R.id.locality);
        flatNo = findViewById(R.id.flatno);
        pincode =findViewById(R.id.pincode);
        landmark = findViewById(R.id.landmark);
        name = findViewById(R.id.name);
        mobile = findViewById(R.id.mobile);
        alternatemobile = findViewById(R.id.alternatemobile);
        stateSpinner = findViewById(R.id.stateSpinner);

        ArrayAdapter spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,stateList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        stateSpinner.setAdapter(spinnerAdapter);

        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                stateSelected = stateList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        saveBtn = findViewById(R.id.address_save_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(city.getText()))
                {
                    if(!TextUtils.isEmpty(locality.getText()))
                    {
                        if((!TextUtils.isEmpty(pincode.getText())) && pincode.getText().toString().length() == 6)
                        {
                            if(!TextUtils.isEmpty(name.getText()))
                            {
                                if(!TextUtils.isEmpty(mobile.getText()) && mobile.getText().length() ==10 )
                                {
                                    LoadingDialog.show();
                                    final String full_address = flatNo.getText().toString() + " " + landmark.getText().toString() +
                                            " " + city.getText().toString() + " - " + stateSelected;

                                    Map<String,Object> updateAddress = new HashMap<>();
                                    updateAddress.put("list_size", (long)DBQueries.addressModelList.size()+1);

                                    updateAddress.put("full_name_"+String.valueOf((long)DBQueries.addressModelList.size()+1), name.getText().toString() + " - "+ mobile.getText().toString());
                                    updateAddress.put("address_"+String.valueOf((long)DBQueries.addressModelList.size()+1), full_address);
                                    updateAddress.put("pincode_"+String.valueOf((long)DBQueries.addressModelList.size()+1), pincode.getText().toString());
                                    updateAddress.put("selected_"+String.valueOf(DBQueries.addressModelList.size()+1),true);
                                    if(DBQueries.addressModelList.size() > 0) {
                                        updateAddress.put("selected_" + DBQueries.selectedAddress + 1, false);
                                    }

                                    FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid())
                                            .collection("USER_DATA").document("MY_ADDRESS")
                                            .update(updateAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                if(DBQueries.addressModelList.size() > 0) {

                                                    DBQueries.addressModelList.get(DBQueries.selectedAddress).setSelected(false);
                                                }
                                                DBQueries.addressModelList.add(new AddressModel(name.getText().toString() + " - " + mobile.getText().toString(), full_address, pincode.getText().toString(), true));

                                                if(getIntent().getStringExtra("INTENT").equals("DELIVERYINTENT"))
                                                {
                                                Intent delivery = new Intent(AddAddressActivity.this, DeliveryActivity.class);
                                                startActivity(delivery);
                                                }else{
                                                    MyAddressesActivity.refreshItem(DBQueries.selectedAddress, DBQueries.addressModelList.size()-1);

                                                }

                                                DBQueries.selectedAddress  = DBQueries.addressModelList.size()-1;
                                                finish();
                                            }else
                                            {
                                                Toast.makeText(AddAddressActivity.this, "Error:"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                            LoadingDialog.dismiss();
                                        }
                                    });
                                }else {
                                    mobile.requestFocus();
                                    Toast.makeText(AddAddressActivity.this, "Provide Valid phone number", Toast.LENGTH_SHORT).show();
                                }
                            }else
                                name.requestFocus();
                        }else
                        {
                            pincode.requestFocus();
                            Toast.makeText(AddAddressActivity.this, "Please Provide Valid code", Toast.LENGTH_SHORT).show();
                        }
                    }else
                        locality.requestFocus();
                }else
                {
                    city.requestFocus();

                }


            }
        });

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
