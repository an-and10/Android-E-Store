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

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<CategoryModel> categoryModelList;

    public CategoryAdapter(List<CategoryModel> categoryModelList) {
        this.categoryModelList = categoryModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_itmes,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        String category_name = categoryModelList.get(i).getCategoryName();
        String category_icons = categoryModelList.get(i).getCategoryIconLink();
        viewHolder.setCategory(category_name,i);
        viewHolder.setCategoryIcons(category_icons);
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    public  class  ViewHolder extends RecyclerView.ViewHolder{

        private TextView categoryName ;
        private ImageView categoryIcons;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryIcons = itemView.findViewById(R.id.category_icons);
            categoryName = itemView.findViewById(R.id.category_name);
        }

        private  void setCategory(final String name, final  int position){


            categoryName.setText(""+name);
            if(!name.equals("")) {


                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position != 0) {

                            Intent categoryIntent = new Intent(itemView.getContext(), CategoryActivity.class);
                            categoryIntent.putExtra("categoryName", name);
                            itemView.getContext().startActivity(categoryIntent);

                        }
                    }
                });
            }


        }
        private  void setCategoryIcons(String iconUrl)
        {
            if(!iconUrl.equals("null"))
            {
                Glide.with(itemView.getContext()).load(iconUrl).apply(new RequestOptions().placeholder(R.mipmap.ph_c)).into(categoryIcons);
            }else
            {
            categoryIcons.setImageResource(R.mipmap.home);
            }


        }

    }



}
