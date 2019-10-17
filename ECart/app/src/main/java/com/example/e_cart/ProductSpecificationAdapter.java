package com.example.e_cart;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class ProductSpecificationAdapter extends RecyclerView.Adapter<ProductSpecificationAdapter.ViewHolder> {

    private List<ProductSpecificationModel> productSpecificationModelList;

    public ProductSpecificationAdapter(List<ProductSpecificationModel> productSpecificationModelList) {
        this.productSpecificationModelList = productSpecificationModelList;
    }


    @Override
    public int getItemViewType(int position) {
        switch (productSpecificationModelList.get(position).getType()) {
            case 0:

                return ProductSpecificationModel.SPECIFICATION_TITLE;
            case 1:

                return ProductSpecificationModel.SPECIFICATION_BODY;
            default:
                return -1;

        }
    }

    @SuppressLint("ResourceType")
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case ProductSpecificationModel.SPECIFICATION_TITLE:
                TextView title = new TextView(viewGroup.getContext());
                title.setTypeface(null, Typeface.BOLD);
               // title.setText(Color.parseColor("#000000"));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(setDP(16, viewGroup.getContext()), setDP(16, viewGroup.getContext()),
                        setDP(16, viewGroup.getContext()),
                        setDP(8, viewGroup.getContext()));

                title.setLayoutParams(layoutParams);
                return new ViewHolder(title);


            case ProductSpecificationModel.SPECIFICATION_BODY:
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_specification_itme_layout, viewGroup, false);
                return new ViewHolder(view);
            default:
                return null;

        }

    }

    private int setDP(int dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        switch (productSpecificationModelList.get(i).getType()) {
            case ProductSpecificationModel.SPECIFICATION_TITLE:
                viewHolder.SetTitle(productSpecificationModelList.get(i).getTitle());

                return;

            case ProductSpecificationModel.SPECIFICATION_BODY:
                String featureName = productSpecificationModelList.get(i).getFeature_name();
                String featureValue = productSpecificationModelList.get(i).getFeature_value();
                viewHolder.setFeatures(featureName, featureValue);
                return;



            default:
                return;

        }



    }

    @Override
    public int getItemCount() {
        return productSpecificationModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView feature_name;
        private TextView feature_value;
        private TextView title;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


        }

        private void setFeatures(String name, String value) {
            feature_name = itemView.findViewById(R.id.product_specification_feature_name);
            feature_value = itemView.findViewById(R.id.product_specification_feature_value);
            feature_value.setText(value);
            feature_name.setText(name);

        }

        private void SetTitle(String titleText) {
            title = (TextView) itemView;
            title.setText(titleText);

        }

    }


}
