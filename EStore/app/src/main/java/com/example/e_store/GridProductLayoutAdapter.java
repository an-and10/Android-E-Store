package com.example.e_store;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class GridProductLayoutAdapter extends BaseAdapter
{
    public GridProductLayoutAdapter(List<HorizontalScrollProductModel> horizontalScrollProductModelList) {
        this.horizontalScrollProductModelList = horizontalScrollProductModelList;
    }

    List<HorizontalScrollProductModel> horizontalScrollProductModelList;

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

            View view;
        if(convertView ==null) {


           view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_items_layout,null);
           view.setElevation(0);
           view.setBackgroundColor(Color.parseColor("#FFFFFF"));
           view.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent productDetailsIntent = new Intent(parent.getContext(), ProductDetailsActivity.class);
                   parent.getContext().startActivity(productDetailsIntent);

               }
           });
           ImageView p_image = view.findViewById(R.id.h_s_product_images);
            TextView p_name = view.findViewById(R.id.h_s_product_name);
            TextView p_price = view.findViewById(R.id.h_s_product_price);
            TextView p_desc = view.findViewById(R.id.h_s_product_descriptions);

            p_image.setImageResource(horizontalScrollProductModelList.get(position).getProductImage());
            p_name.setText(horizontalScrollProductModelList.get(position).getProduct_name());
            p_desc.setText(horizontalScrollProductModelList.get(position).getProduct_desc());
            p_price.setText(horizontalScrollProductModelList.get(position).getProduct_price());

        }
        else
        {
            view = convertView;
        }
        return view;
    }
}
