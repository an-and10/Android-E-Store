package com.example.e_cart;


import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import static com.example.e_cart.DBQueries.CategoryModelList;


import static com.example.e_cart.DBQueries.lists;
import static com.example.e_cart.DBQueries.loadCategories;
import static com.example.e_cart.DBQueries.loadFragmentFirebase;
import static com.example.e_cart.DBQueries.loadedCategoriesName;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private RecyclerView homePageRecyclerView;


    public HomeFragment() {
        // Required empty public constructor
    }

    private RecyclerView categoryRecyclerView;
    private  CategoryAdapter categoryAdapter;
    private    HomePageAdapter adapter;
    private ImageView internet;
    public  static  SwipeRefreshLayout swipeRefreshLayout;
    private NetworkInfo networkInfo;
    private  ConnectivityManager connectivityManager;
    private Button reTry;


    private List<HomePageModel> homePageModelFakeList = new ArrayList<>();

    private List<CategoryModel> categoryModelFakeList = new ArrayList<>();

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        internet =view.findViewById(R.id.no_internet_connections);
        swipeRefreshLayout =view.findViewById(R.id.refreshlayout);
        reTry = view.findViewById(R.id.refresh_button);



        categoryRecyclerView = view.findViewById(R.id.category_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(linearLayoutManager);

        homePageRecyclerView = view.findViewById(R.id.home_fragment_recycle_view);
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        homePageRecyclerView.setLayoutManager(testingLayoutManager);

        loadedCategoriesName.add("Home");
        lists.add(new ArrayList<HomePageModel>());
        loadFragmentFirebase(homePageRecyclerView, getContext(),0, "Home");
 /* Category Fake List  */

        categoryModelFakeList.add(new CategoryModel("null", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));


        /* Category End

         hiome*/
        List<sliderModel> sliderModelFakeList =  new ArrayList<>();
        sliderModelFakeList.add(new sliderModel("null", "#000000"));
        sliderModelFakeList.add(new sliderModel("null", "#000000"));
        sliderModelFakeList.add(new sliderModel("null", "#000000"));
        sliderModelFakeList.add(new sliderModel("null", "#000000"));
        sliderModelFakeList.add(new sliderModel("null", "#000000"));

        List<HorizontalScrollProductModel> horizontalScrollProductModelFakeList = new ArrayList<>();
        horizontalScrollProductModelFakeList.add(new HorizontalScrollProductModel("", "null","","",""));
        horizontalScrollProductModelFakeList.add(new HorizontalScrollProductModel("", "","","",""));
        horizontalScrollProductModelFakeList.add(new HorizontalScrollProductModel("", "","","",""));
        horizontalScrollProductModelFakeList.add(new HorizontalScrollProductModel("", "","","",""));
        horizontalScrollProductModelFakeList.add(new HorizontalScrollProductModel("", "","","",""));
        horizontalScrollProductModelFakeList.add(new HorizontalScrollProductModel("", "","","",""));
        horizontalScrollProductModelFakeList.add(new HorizontalScrollProductModel("", "","","",""));

        homePageModelFakeList.add(new HomePageModel(sliderModelFakeList,0));
        homePageModelFakeList.add(new HomePageModel(1,"", "#aaccdd"));
        homePageModelFakeList.add(new HomePageModel(2,"","#accbdd",horizontalScrollProductModelFakeList,new ArrayList<WishlistModel>()));
        homePageModelFakeList.add(new HomePageModel(3,"","",horizontalScrollProductModelFakeList));



        categoryAdapter = new CategoryAdapter(categoryModelFakeList);
        adapter = new HomePageAdapter(homePageModelFakeList);


        if(networkInfo!=null && networkInfo.isConnected()==true) {
            MainActivity.drawer.setDrawerLockMode(0);
            internet.setVisibility(View.GONE);
            reTry.setVisibility(View.INVISIBLE);
            categoryRecyclerView.setVisibility(View.VISIBLE);
            homePageRecyclerView.setVisibility(View.VISIBLE);
            if(CategoryModelList.size() == 0 )
            {
                loadCategories(categoryRecyclerView, getContext());
            }else
            {
                categoryAdapter = new CategoryAdapter(CategoryModelList);
                categoryAdapter.notifyDataSetChanged();
            }
            categoryRecyclerView.setAdapter(categoryAdapter);

            if(lists.size() == 0 )
            {
                loadedCategoriesName.add("Home");
                lists.add(new ArrayList<HomePageModel>());
                loadFragmentFirebase(homePageRecyclerView, getContext(),0, "Home");
              //  homePageRecyclerView.setAdapter(homePageAdapter);

            }else
            {
                adapter = new HomePageAdapter(lists.get(0));
                adapter.notifyDataSetChanged();
            }
            homePageRecyclerView.setAdapter(adapter);
            }
        else {
            MainActivity.drawer.setDrawerLockMode(1);
            categoryRecyclerView.setVisibility(View.GONE);
            homePageRecyclerView.setVisibility(View.GONE);
            Glide.with(this).load(R.drawable.no_internet_connections).into(internet);
            internet.setVisibility(View.VISIBLE);
            reTry.setVisibility(View.VISIBLE);
        }
        // Refresh Layout//

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                swipeRefreshLayout.setRefreshing(true);
                refreshPage();
            }
        });

       reTry.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               refreshPage();
           }
       });
        return view;
    }

    private void refreshPage()
    {

        networkInfo = connectivityManager.getActiveNetworkInfo();
        CategoryModelList.clear();
        lists.clear();
        loadedCategoriesName.clear();
        if(networkInfo!=null && networkInfo.isConnected()==true) {
            MainActivity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            internet.setVisibility(View.GONE);
            reTry.setVisibility(View.INVISIBLE);
            categoryRecyclerView.setVisibility(View.VISIBLE);
            homePageRecyclerView.setVisibility(View.VISIBLE);
            categoryAdapter = new CategoryAdapter(categoryModelFakeList);
            adapter = new HomePageAdapter(homePageModelFakeList);

            categoryRecyclerView.setAdapter(categoryAdapter);
            homePageRecyclerView.setAdapter(adapter);
            loadCategories(categoryRecyclerView, getContext());

            loadedCategoriesName.add("Home");
            lists.add(new ArrayList<HomePageModel>());
            loadFragmentFirebase(homePageRecyclerView, getContext(),0, "Home");
        }else
        {
            MainActivity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            Toast.makeText(getContext(), "No Internet Connections", Toast.LENGTH_SHORT).show();
            categoryRecyclerView.setVisibility(View.GONE);
            homePageRecyclerView.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            Glide.with(getContext()).load(R.drawable.no_internet_connections).into(internet);
            internet.setVisibility(View.VISIBLE);
            reTry.setVisibility(View.VISIBLE);


        }

    }
}




