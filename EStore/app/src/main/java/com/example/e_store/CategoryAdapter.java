package com.example.e_store;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        viewHolder.setCategoryName(category_name);
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

        private  void setCategoryName(String name){
            categoryName.setText(""+name);

        }
        private  void setCategoryIcons()
        {
            // todo: set the icxons from the database;
        }
    }



}
