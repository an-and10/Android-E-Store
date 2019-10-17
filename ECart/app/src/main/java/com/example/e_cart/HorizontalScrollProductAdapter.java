package com.example.e_cart;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class HorizontalScrollProductAdapter  extends RecyclerView.Adapter<HorizontalScrollProductAdapter.ViewHolder> {

    private List<HorizontalScrollProductModel> horizontalScrollProductModelList;

    public HorizontalScrollProductAdapter(List<HorizontalScrollProductModel> horizontalScrollProductModels) {
        this.horizontalScrollProductModelList = horizontalScrollProductModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.horizontal_scroll_items_layout,viewGroup,false);
        view.setElevation(3);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String resource = horizontalScrollProductModelList.get(i).getProductImage();
        String p_title = horizontalScrollProductModelList.get(i).getProduct_name();
        String p_desc = horizontalScrollProductModelList.get(i).getProduct_desc();
        String p_price = horizontalScrollProductModelList.get(i).getProduct_price();
        String id = horizontalScrollProductModelList.get(i).getProductID();
        viewHolder.setData(id, resource,p_title,p_desc,p_price);
    }

    @Override
    public int getItemCount() {
        return horizontalScrollProductModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView product_image;
        private TextView product_title;
        private  TextView product_desc;
        private  TextView product_price;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            product_image = itemView.findViewById(R.id.h_s_product_images);
            product_title = itemView.findViewById(R.id.h_s_product_name);
            product_desc =  itemView.findViewById(R.id.h_s_product_descriptions);
            product_price = itemView.findViewById(R.id.h_s_product_price);

        }

        public void setData(final String id, String resource, String title, String desc, String price)
        {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions()).placeholder(R.mipmap.home).into(product_image);
            product_title.setText(title);
            product_desc.setText(desc);
            product_price.setText("Rs: "+ price +"/-");

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent pro = new Intent(itemView.getContext(), ProductDetailsActivity.class);
                    pro.putExtra("PRODUCT_ID", id);
                    itemView.getContext().startActivity(pro);
                }
            });

        }

    }

}
