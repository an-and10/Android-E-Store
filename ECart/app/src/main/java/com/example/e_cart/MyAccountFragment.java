package com.example.e_cart;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyAccountFragment extends Fragment {
    public static final int MANAGE_ADDRESS =1;
    private Button viewAllAddress;




    public MyAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_my_account, container, false);
        viewAllAddress = view.findViewById(R.id.address_viewall_btn);
        viewAllAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myAddresses = new Intent(getContext(), MyAddressesActivity.class);
                myAddresses.putExtra("MODE",MANAGE_ADDRESS);
                startActivity(myAddresses);
            }
        });

        return view;
    }

}
