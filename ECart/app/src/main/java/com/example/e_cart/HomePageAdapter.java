package com.example.e_cart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomePageAdapter extends RecyclerView.Adapter {

    private List<HomePageModel> homePageModelList;

    public HomePageAdapter(List<HomePageModel> homePageModelList) {
        this.homePageModelList = homePageModelList;
    }

    @Override
    public int getItemViewType(int position) {
        switch (homePageModelList.get(position).getType()) {
            case 0:
                return HomePageModel.BANNER_SLIDER;
            case 1:
                return HomePageModel.STRIP_AD_BANNER;
            case 2 :
                return  HomePageModel.HORIZONTAL_PRODUCT_VIEW;
            case 3:
                return  HomePageModel.GRID_PRODUCT_LAYOUT;




            default:
                return -1;

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i){
            case HomePageModel.BANNER_SLIDER:
                View banner_slider_view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sliding_banner_layout,viewGroup,false);
                return  new BannerSliderViewHolder(banner_slider_view);
            case HomePageModel.STRIP_AD_BANNER:
                View strip_ad_view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.strip_add_layout,viewGroup,false);
                return  new StripAdViewHolder(strip_ad_view);
            case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                View horizontal_product_view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.horizontal_scroll_layout, viewGroup,false);
                return new HorizontalProductViewHolder(horizontal_product_view);
            case  HomePageModel.GRID_PRODUCT_LAYOUT:
                View gridProductView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid_product_layout,viewGroup,false);
                return  new GridProductViewHolder(gridProductView);


            default: return  null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        switch (homePageModelList.get(i).getType())
        {
            case HomePageModel.BANNER_SLIDER:
                List<sliderModel>sliderModelList = homePageModelList.get(i).getSliderModelList();
                ((BannerSliderViewHolder)viewHolder).setBannerSliderPager(sliderModelList);
               break;
            case  HomePageModel.STRIP_AD_BANNER:
                String resource = homePageModelList.get(i).getResource();
                String color = homePageModelList.get(i).getSetBackground();
                ((StripAdViewHolder)viewHolder).setStripAd(resource,color);
                break;
            case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                String title = homePageModelList.get(i).getTitle();
                List<WishlistModel> viewProductList = homePageModelList.get(i).getViewAllProductList();
                String  layoutColor = homePageModelList.get(i).getSetBackground();
                List<HorizontalScrollProductModel> horizontalScrollProductModelList = homePageModelList.get(i).getHorizontalScrollProductModelList();
                ((HorizontalProductViewHolder)viewHolder).setHorizontalProductLayout(title, horizontalScrollProductModelList,layoutColor , viewProductList);
                break;
            case HomePageModel.GRID_PRODUCT_LAYOUT:
                String grid_title = homePageModelList.get(i).getTitle();
                String gridColor = homePageModelList.get(i).getSetBackground();
                List<HorizontalScrollProductModel> grid_horizontalScrollProductModelList = homePageModelList.get(i).getHorizontalScrollProductModelList();
                ((GridProductViewHolder)viewHolder).setGridProductLayout( grid_horizontalScrollProductModelList, grid_title, gridColor);
                break;

                 default:
                break;

        }
    }

    @Override
    public int getItemCount() {
        if(homePageModelList.size()> 8)
        {
            return 8;
        }else
        {
            return homePageModelList.size();

        }
    }

    public class BannerSliderViewHolder extends RecyclerView.ViewHolder {

        private ViewPager viewPager;

        private  int current_page;
        private Timer timer;
        final private  long Delay_Time = 3000;
        final private  long Period_Time =3000;
        private  List<sliderModel> arrangeList;


        public BannerSliderViewHolder(@NonNull View itemView) {
            super(itemView);

            viewPager = itemView.findViewById(R.id.view_pager_banner_slider);

        }
        private void setBannerSliderPager(final List<sliderModel> sliderModelList)
        {
            current_page =2;
            if(timer !=null)
            {
                timer.cancel();
            }
            arrangeList  =new ArrayList<>();
            for(int x = 0;x<sliderModelList.size();x++)
            {
            arrangeList.add(x,sliderModelList.get(x));

            }
            arrangeList.add(0,sliderModelList.get(sliderModelList.size()-2));
            arrangeList.add(1,sliderModelList.get(sliderModelList.size()-1));
            arrangeList.add(sliderModelList.get(0));
            arrangeList.add(sliderModelList.get(1));


            SliderAdapter sliderAdapter = new SliderAdapter(arrangeList);
            viewPager.setAdapter(sliderAdapter);
            viewPager.setClipToPadding(false);
            viewPager.setPageMargin(20);
            viewPager.setCurrentItem(current_page);

            ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }

                @Override
                public void onPageSelected(int i) {
                    current_page = i;

                }

                @Override
                public void onPageScrollStateChanged(int i) {
                    if (i == ViewPager.SCREEN_STATE_OFF) {
                        Looper(arrangeList);
                    }

                }

            };
            viewPager.addOnPageChangeListener(onPageChangeListener);

            startBannerSliderShow(arrangeList);
            viewPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Looper(arrangeList);
                    stopBannerSlideShow(arrangeList);
                    if (event.getAction() == MotionEvent.ACTION_UP)
                        startBannerSliderShow(arrangeList);

                    return false;
                }
            });

        }
        private  void Looper(List<sliderModel> sliderModelList)
        {
            if(current_page == sliderModelList.size()-2){
                current_page =2;
                viewPager.setCurrentItem(current_page,false);
            }
            if(current_page == 1){
                current_page =sliderModelList.size()-1;
                viewPager.setCurrentItem(current_page,false);
            }
        }
        private  void startBannerSliderShow(final List<sliderModel> sliderModelList)
        {
            final Handler handler = new Handler();
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if(current_page >= sliderModelList.size())
                    {
                        current_page =1;
                    }
                    viewPager.setCurrentItem(current_page++,true);
                }
            };
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(runnable);

                }
            }, Delay_Time, Period_Time);
        }
        private  void stopBannerSlideShow(List<sliderModel> sliderModelList)
        {
            timer.cancel();
        }
    }

    public  class StripAdViewHolder extends  RecyclerView.ViewHolder{

        private ImageView stripAdImage;
        private ConstraintLayout stripAdContainer;

        public StripAdViewHolder(@NonNull View itemView) {
            super(itemView);
            stripAdContainer = itemView.findViewById(R.id.strip_ad_container);
            stripAdImage = itemView.findViewById(R.id.strip_ad_image);

        }
        private void setStripAd(String resource,String color)
        {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.mipmap.home)).into(stripAdImage);
            stripAdContainer.setBackgroundColor(Color.parseColor(color));
            /* code for  Ends here for Strip Add image */
        }

    }

    public  class HorizontalProductViewHolder extends  RecyclerView.ViewHolder{

        private  ConstraintLayout container;
        private Button horizontalProductViewAllBtn;
        private TextView horizontalProductTitle;
        private  RecyclerView horizontalProductRecycler;

        public HorizontalProductViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.hcontainer);
            horizontalProductViewAllBtn = itemView.findViewById(R.id.horizontal_scroll_layout_btn);
            horizontalProductTitle = itemView.findViewById(R.id.horizontal_scroll_layout_title);
            horizontalProductRecycler = itemView.findViewById(R.id.horizontal_scroll_product_recyclerview);



        }
        public  void setHorizontalProductLayout(final String title, List<HorizontalScrollProductModel> horizontalScrollProductModelList, String color, final List<WishlistModel>viewAllProductsList)
        {
            container.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
            horizontalProductTitle.setText(title);

            if(horizontalScrollProductModelList.size() > 8)
            {

                horizontalProductViewAllBtn.setVisibility(View.VISIBLE);
                horizontalProductViewAllBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent viewAllIntent = new Intent(itemView.getContext(), ViewAllActivity.class);
                        ViewAllActivity.wishlistModelList = viewAllProductsList;
                        viewAllIntent.putExtra("LAYOUTCODE", 0);
                        viewAllIntent.putExtra("Title", title);

                        itemView.getContext().startActivity(viewAllIntent);

                    }
                });
            }else
            {
                horizontalProductViewAllBtn.setVisibility(View.INVISIBLE);
            }
            HorizontalScrollProductAdapter horizontalScrollProductAdapter = new HorizontalScrollProductAdapter(horizontalScrollProductModelList);

            LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(itemView.getContext());
            linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
            horizontalProductRecycler.setLayoutManager(linearLayoutManager1);

            horizontalProductRecycler.setAdapter(horizontalScrollProductAdapter);
            horizontalScrollProductAdapter.notifyDataSetChanged();
        }
    }

    public  class  GridProductViewHolder extends RecyclerView.ViewHolder{
        private  ConstraintLayout container;
        private  TextView grid_title;
        private  Button grid_btn;
       private GridLayout gridProductLayout;


        public GridProductViewHolder(@NonNull View itemView) {
            super(itemView);
             grid_title = itemView.findViewById(R.id.grid_lproduct_layout_title);
             grid_btn = itemView.findViewById(R.id.grid_product_layout_view_all_btn);
             gridProductLayout = itemView.findViewById(R.id.gridLayout);
             container = itemView.findViewById(R.id.Gridcontainer);

        }
        @SuppressLint("ResourceAsColor")
        public  void setGridProductLayout(final List<HorizontalScrollProductModel> horizontalScrollProductModelList, final String title, String color)
        {
                container.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
            grid_title.setText(title);
            for(int i=0;i<4;i++)
            {
                ImageView productImage = gridProductLayout.getChildAt(i).findViewById(R.id.h_s_product_images);
                TextView productTitle =    gridProductLayout.getChildAt(i).findViewById(R.id.h_s_product_name);
                TextView productPrice =    gridProductLayout.getChildAt(i).findViewById(R.id.h_s_product_price);
                TextView productDescription =    gridProductLayout.getChildAt(i).findViewById(R.id.h_s_product_descriptions);

              Glide.with(itemView.getContext()).load(horizontalScrollProductModelList.get(i).getProductImage()).apply(new RequestOptions()).placeholder(R.mipmap.home).into(productImage);

                productTitle.setText(horizontalScrollProductModelList.get(i).getProduct_name());
                productPrice.setText("Rs. "+ horizontalScrollProductModelList.get(i).getProduct_price() + " /-");
                productDescription.setText(horizontalScrollProductModelList.get(i).getProduct_desc());
               // gridProductLayout.getChildAt(i).setBackgroundColor(R.color.colorPrimary);
                final int finalI = i;
                gridProductLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent ProductDetailsIntent = new Intent(itemView.getContext(), ProductDetailsActivity.class);
                        ProductDetailsIntent.putExtra("PRODUCT_ID", horizontalScrollProductModelList.get(finalI).getProductID());
                        itemView.getContext().startActivity(ProductDetailsIntent);

                    }
                });

            }
            grid_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewAllActivity.horizontalScrollProductModelList = horizontalScrollProductModelList;
                    Intent viewAllIntent = new Intent(itemView.getContext(), ViewAllActivity.class);
                    viewAllIntent.putExtra("LAYOUTCODE", 1);
                    viewAllIntent.putExtra("Title", title);
                    itemView.getContext().startActivity(viewAllIntent);

                }
            });

        }
    }
 }


