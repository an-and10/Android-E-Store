package com.example.e_cart;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static com.example.e_cart.DeliveryActivity.SELECT_ADDRESS;
import static com.example.e_cart.MyAccountFragment.MANAGE_ADDRESS;
import static com.example.e_cart.MyAddressesActivity.refreshItem;


public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    private int preSelectedPosition;
    private int MODE;

    public AddressAdapter(List<AddressModel> addressModelList, int MODE) {
        this.addressModelList = addressModelList;
        this.MODE = MODE;
        preSelectedPosition = DBQueries.selectedAddress;

    }

    private List<AddressModel> addressModelList ;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.address_item_layout, viewGroup,false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String a_name = addressModelList.get(i).getName();
        String a_add = addressModelList.get(i).getAddress();
        String a_pincode = addressModelList.get(i).getPincode();
        Boolean selected = addressModelList.get(i).getSelected();

        viewHolder.setAddressData(a_name,a_add,a_pincode, selected,i);

    }

    @Override
    public int getItemCount() {
        return addressModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
      private TextView name;
        private TextView address;
        private TextView pincode;
        private ImageView icons;
        private LinearLayout optionContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            pincode = itemView.findViewById(R.id.pincode);
            icons = itemView.findViewById(R.id.icon_view);
            optionContainer = itemView.findViewById(R.id.option_container);

        }
        private  void setAddressData(String add_name, String add_address, String add_pincode, Boolean selected, final int position)
        {

            name.setText(add_name);
            address.setText(add_address);
            pincode.setText(add_pincode);
            if(MODE == SELECT_ADDRESS)
            {
                icons.setImageResource(R.drawable.ic_check_black_24dp);
                if(selected ==true)
                    {
                        preSelectedPosition = position;
                        icons.setVisibility(View.VISIBLE);
                    }else {
                            icons.setVisibility(View.GONE);
                        }
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(preSelectedPosition!=position) {
                            addressModelList.get(position).setSelected(true);
                            addressModelList.get(preSelectedPosition).setSelected(false);
                            refreshItem(preSelectedPosition, position);
                            preSelectedPosition = position;
                            DBQueries.selectedAddress = position;
                        }
                    }
                });
            }else if( MODE == MANAGE_ADDRESS)
            {
                optionContainer.setVisibility(View.GONE);
                icons.setImageResource(R.drawable.ic_more_vert_black_24dp);
                icons.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        optionContainer.setVisibility(View.VISIBLE);
                        refreshItem(preSelectedPosition,preSelectedPosition);
                        preSelectedPosition = position;
                        Toast.makeText(itemView.getContext(), "Enter into item click ", Toast.LENGTH_SHORT).show();
                    }
                });
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        refreshItem(preSelectedPosition,preSelectedPosition);
                        preSelectedPosition = -1;

                    }
                });

            }
        }
    }
}
