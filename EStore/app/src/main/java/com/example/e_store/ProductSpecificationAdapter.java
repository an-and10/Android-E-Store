package com.example.e_store;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ProductSpecificationAdapter extends RecyclerView.Adapter<ProductSpecificationAdapter.ViewHolder> {

   private List<ProductSpecificationModel> productSpecificationModelList;

    public ProductSpecificationAdapter(List<ProductSpecificationModel> productSpecificationModelList) {
        this.productSpecificationModelList = productSpecificationModelList;
    }


    @NonNull
    @Override
    public ProductSpecificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_specification_itme_layout,viewGroup,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductSpecificationAdapter.ViewHolder viewHolder, int i) {

        String featurename = productSpecificationModelList.get(i).getFeature_name();
        String featurevalue = productSpecificationModelList.get(i).getFeature_value();
        viewHolder.setFeatures(featurename,featurevalue);

    }

    @Override
    public int getItemCount() {
        return productSpecificationModelList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView feature_name;
        private TextView feature_value;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            feature_name = itemView.findViewById(R.id.product_specification_feature_name);
            feature_value = itemView.findViewById(R.id.product_specification_feature_value);

        }
        private void setFeatures(String name, String value)
        {
            feature_value.setText(value);
            feature_name.setText(name);

        }

    }


}
