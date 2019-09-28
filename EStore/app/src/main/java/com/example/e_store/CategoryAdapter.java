package com.example.e_store;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<CategoryModel> categoryModelList;

    public CategoryAdapter(List<CategoryModel> categoryModelList) {
        this.categoryModelList = categoryModelList;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_itmes,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder viewHolder, int i) {

        String category_name = categoryModelList.get(i).getCategoryName();
        String category_icons = categoryModelList.get(i).getCategoryIconLink();
        viewHolder.setCategory(category_name,i);
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(position!=0)
                    {


                    Intent  categoryIntent = new Intent(itemView.getContext(),CategoryActivity.class);
                    categoryIntent.putExtra("categoryName", name);
                    itemView.getContext().startActivity(categoryIntent);

                    }
                }
            });


        }
        private  void setCategoryIcons()
        {
            // todo: set the icxons from the database;
        }

    }



}
