package com.example.e_cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.e_cart.DeliveryActivity.SELECT_ADDRESS;

public class MyAddressesActivity extends AppCompatActivity {
    private RecyclerView MyAddressesRecyclerView;
    private static  AddressAdapter addressAdapter;
    private Button deliverHereBtn;
    private LinearLayout addAddressBtn;
    private TextView totalAddress;
    private int previousAddress ;
    private Dialog LoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_addresses);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("My Address");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LoadingDialog = new Dialog(this);
        LoadingDialog.setContentView(R.layout.loading_progress_dialog);
        LoadingDialog.setCancelable(false);
        LoadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        LoadingDialog.getWindow().setBackgroundDrawable(this.getDrawable(R.drawable.slider_background));


        MyAddressesRecyclerView = findViewById(R.id.address_recycler_view);
        deliverHereBtn = findViewById(R.id.deliver_here_btn);
       addAddressBtn = findViewById(R.id.add_addressnew_btn);
       totalAddress = findViewById(R.id.total_address);

        previousAddress = DBQueries.selectedAddress;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        MyAddressesRecyclerView.setLayoutManager(linearLayoutManager);



        int mode = getIntent().getIntExtra("MODE",-1);
        if(mode == SELECT_ADDRESS)
        {
            deliverHereBtn.setVisibility(View.VISIBLE);
        }else
        {
            deliverHereBtn.setVisibility(View.GONE);
        }
        deliverHereBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(DBQueries.selectedAddress!= previousAddress)
                {
                    LoadingDialog.show();
                    final int previousAddressIndex = previousAddress;

                    Map<String, Object> updateSelection = new HashMap<>();
                    updateSelection.put("selected_"+String.valueOf(previousAddress+1), false);
                    updateSelection.put("selected_"+String.valueOf(DBQueries.selectedAddress+1), true);
                    previousAddress = DBQueries.selectedAddress;
                   FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid())
                           .collection("USER_DATA").document("MY_ADDRESS").update(updateSelection)
                           .addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        finish();
                                    }   else
                                    {
                                        previousAddress =  previousAddressIndex;
                                        Toast.makeText(MyAddressesActivity.this, "Error:"+task.getException().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                    LoadingDialog.dismiss();
                               }
                           });
                }else
                {
                    finish();
                }
            }
        });
        addressAdapter  = new AddressAdapter(DBQueries.addressModelList,mode );
        MyAddressesRecyclerView.setAdapter(addressAdapter);
        ((SimpleItemAnimator)MyAddressesRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        addressAdapter.notifyDataSetChanged();


        addAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addAddressIntent = new Intent(MyAddressesActivity.this, AddAddressActivity.class);
                addAddressIntent.putExtra("INTENT", "NULL");
                startActivity(addAddressIntent);
            }
        });

    }

    public  static void refreshItem(int deselected, int select)
    {
        addressAdapter.notifyItemChanged(deselected);
        addressAdapter.notifyItemChanged(select);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id ==android.R.id.home)
        {
            if(DBQueries.selectedAddress != previousAddress)
            {
                DBQueries.addressModelList.get(DBQueries.selectedAddress).setSelected(false);
                DBQueries.addressModelList.get(previousAddress).setSelected(true);
                DBQueries.selectedAddress = previousAddress;
            }
            finish();
            return true;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if(DBQueries.selectedAddress != previousAddress)
        {
            DBQueries.addressModelList.get(DBQueries.selectedAddress).setSelected(false);
            DBQueries.addressModelList.get(previousAddress).setSelected(true);
            DBQueries.selectedAddress = previousAddress;
        }
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
        totalAddress.setText(String.valueOf(DBQueries.addressModelList.size()) + " - Total Address Saved");

    }
}
