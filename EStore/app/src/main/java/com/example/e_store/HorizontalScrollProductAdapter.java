package com.example.e_store;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class HorizontalScrollProductAdapter  extends RecyclerView.Adapter<HorizontalScrollProductAdapter.ViewHolder> {

    private List<HorizontalScrollProductModel> horizontalScrollProductModelList;

    public HorizontalScrollProductAdapter(List<HorizontalScrollProductModel> horizontalScrollProductModels) {
        this.horizontalScrollProductModelList = horizontalScrollProductModels;
    }

    @NonNull
    @Override
    public HorizontalScrollProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.horizontal_scroll_items_layout,viewGroup,false);
        view.setElevation(3);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalScrollProductAdapter.ViewHolder viewHolder, int i) {

        int resource = horizontalScrollProductModelList.get(i).getProductImage();
        String p_title = horizontalScrollProductModelList.get(i).getProduct_name();
        String p_desc = horizontalScrollProductModelList.get(i).getProduct_desc();
        String p_price = horizontalScrollProductModelList.get(i).getProduct_price();
        viewHolder.setProduct_image(resource);
        viewHolder.setProduct_desc(p_desc);
        viewHolder.setProduct_price(p_price);
        viewHolder.setProduct_title(p_title);

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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent pro = new Intent(itemView.getContext(), ProductDetailsActivity.class);
                    itemView.getContext().startActivity(pro);


                }
            });

        }

        public void setProduct_image(int resource) {
            product_image.setImageResource(resource);
        }

        public void setProduct_title(String title) {
            product_title.setText(title);
        }

        public void setProduct_desc(String desc) {
           product_desc.setText(desc);
        }

        public void setProduct_price(String price) {
           product_price.setText(price);
        }
    }

}
