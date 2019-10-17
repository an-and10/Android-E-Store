package com.example.e_cart;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ViewAllActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GridView gridView;
    public static  List<HorizontalScrollProductModel>horizontalScrollProductModelList;
    public  static  List<WishlistModel> wishlistModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        String title = getIntent().getStringExtra("Title");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        int layoutCode = getIntent().getIntExtra("LAYOUTCODE", -1);
        recyclerView = findViewById(R.id.recycler_view);
        gridView = findViewById(R.id.grid_view);
       // layoutCode =1;
        Toast.makeText(this, "Layout Code: "+layoutCode, Toast.LENGTH_SHORT).show();

        if(layoutCode == 0) {

            recyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayout = new LinearLayoutManager(this);
            linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(linearLayout);


            WishlistAdapter adapter = new WishlistAdapter(wishlistModelList, false );
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }else if(layoutCode ==1)
        {

        gridView.setVisibility(View.VISIBLE);
        GridProductLayoutAdapter  adapter1 = new GridProductLayoutAdapter(horizontalScrollProductModelList);
        gridView.setAdapter(adapter1);

        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() ==android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
