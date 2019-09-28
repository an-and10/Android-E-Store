package com.example.e_store;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

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
                int resource = homePageModelList.get(i).getResource();
                String color = homePageModelList.get(i).getSetBackground();
                ((StripAdViewHolder)viewHolder).setStripAd(resource,color);
                break;
            case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                String title = homePageModelList.get(i).getTitle();
                List<HorizontalScrollProductModel> horizontalScrollProductModelList = homePageModelList.get(i).getHorizontalScrollProductModelList();
                ((HorizontalProductViewHolder)viewHolder).setHorizontalProductLayout(title, horizontalScrollProductModelList);
                break;
            case HomePageModel.GRID_PRODUCT_LAYOUT:
                String grid_title = homePageModelList.get(i).getTitle();
                List<HorizontalScrollProductModel> grid_horizontalScrollProductModelList = homePageModelList.get(i).getHorizontalScrollProductModelList();
                ((GridProductViewHolder)viewHolder).setGridProductLayout( grid_horizontalScrollProductModelList, grid_title);
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

        private  int current_page =2;
        private Timer timer;
        final private  long Delay_Time = 3000;
        final private  long Period_Time =3000;

        public BannerSliderViewHolder(@NonNull View itemView) {
            super(itemView);

            viewPager = itemView.findViewById(R.id.view_pager_banner_slider);

        }
        private void setBannerSliderPager(final List<sliderModel> sliderModelList)
        {

            SliderAdapter sliderAdapter = new SliderAdapter(sliderModelList);
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
                        Looper(sliderModelList);
                    }

                }

            };
            viewPager.addOnPageChangeListener(onPageChangeListener);

            startBannerSliderShow(sliderModelList);
            viewPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Looper(sliderModelList);
                    stopBannerSlideShow(sliderModelList);
                    if (event.getAction() == MotionEvent.ACTION_UP)
                        startBannerSliderShow(sliderModelList);

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
        private void setStripAd(int resource,String color)
        {
            stripAdImage.setImageResource(resource);
            stripAdContainer.setBackgroundColor(Color.parseColor(color));
            /* code for  Ends here for Strip Add image */
        }

    }

    public  class HorizontalProductViewHolder extends  RecyclerView.ViewHolder{

        private Button horizontalProductViewAllBtn;
        private TextView horizontalProductTitle;
        private  RecyclerView horizontalProductRecycler;

        public HorizontalProductViewHolder(@NonNull View itemView) {
            super(itemView);
            horizontalProductViewAllBtn = itemView.findViewById(R.id.horizontal_scroll_layout_btn);
            horizontalProductTitle = itemView.findViewById(R.id.horizontal_scroll_layout_title);
            horizontalProductRecycler = itemView.findViewById(R.id.horizontal_scroll_product_recyclerview);


        }
        public  void setHorizontalProductLayout(String title,List<HorizontalScrollProductModel> horizontalScrollProductModelList)
        {
            horizontalProductTitle.setText(title);
            if(horizontalScrollProductModelList.size() > 8)
            {
                horizontalProductViewAllBtn.setVisibility(View.VISIBLE);
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
        private  TextView grid_title;
        private  Button grid_btn;
        GridView gridView ;
        public GridProductViewHolder(@NonNull View itemView) {
            super(itemView);
             grid_title = itemView.findViewById(R.id.grid_lproduct_layout_title);
             grid_btn = itemView.findViewById(R.id.grid_product_layout_view_all_btn);
             gridView = itemView.findViewById(R.id.grid_product_layout_gridView);
        }
        public  void setGridProductLayout(List<HorizontalScrollProductModel> horizontalScrollProductModelList,String title)
        {
            grid_title.setText(title);
            gridView.setAdapter(new GridProductLayoutAdapter(horizontalScrollProductModelList));

        }
    }
 }


