package com.example.e_store;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    public AddressAdapter(List<AddressModel> addressModelList) {
        this.addressModelList = addressModelList;
    }

    private List<AddressModel> addressModelList ;
    @NonNull
    @Override
    public AddressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.address_item_layout, viewGroup,false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.ViewHolder viewHolder, int i) {
        String a_name = addressModelList.get(i).getName();
        String a_add = addressModelList.get(i).getAddress();
        String a_pincode = addressModelList.get(i).getPincode();
        viewHolder.setAddressData(a_name,a_add,a_pincode);

    }

    @Override
    public int getItemCount() {
        return addressModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
      private TextView name;
        private TextView address;
        private TextView pincode;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            pincode = itemView.findViewById(R.id.pincode);
        }
        private  void setAddressData(String add_name,String add_address, String add_pincode)
        {
            name.setText(add_name);
            address.setText(add_address);
            pincode.setText(add_pincode);
        }
    }
}
