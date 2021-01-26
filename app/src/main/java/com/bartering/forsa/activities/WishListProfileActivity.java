package com.bartering.forsa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bartering.forsa.AppCompactActivity;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.activities.bartering_detail.ProductDetailActivity;
import com.bartering.forsa.databinding.ActivityWishlistProfileBinding;
import com.bartering.forsa.recyclerViewAdapter.WishList_Profile_RecyclerViewAdapter;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.HomeProducts_ServiceModel;
import com.bartering.forsa.retrofit.service_model.ProductLike_ServiceModel;
import com.bartering.forsa.retrofit.service_model.Product_Add_WiLit;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.SharedPreferences_Util;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class WishListProfileActivity extends AppCompactActivity implements ClickListener, Observer<Object> {

    ActivityWishlistProfileBinding activityWishlistProfileBinding;
    WishList_Profile_RecyclerViewAdapter wishList_profile_recyclerViewAdapter;

    int clickedWish,clickedLike;

    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;
    HomeProducts_ServiceModel homeProducts_serviceModel;

    Product_Add_WiLit product_add_wiLit;
    ProductLike_ServiceModel productLike_serviceModel;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWishlistProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_wishlist_profile);
        activityWishlistProfileBinding.setIsNoRecord(false);
        activityWishlistProfileBinding.setClickListener(this::onClick);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWishListData();

    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event1")) { ///back
            WishListProfileActivity.this.finish();
        }
        if (callerIdentity.equals("event8")) {
            clickedWish = position;
            HomeProducts_ServiceModel.DataBean dataBean = (HomeProducts_ServiceModel.DataBean) object;
            addProductToWishlist(dataBean.getId());

        }
        if (callerIdentity.equals("event9")) {
            clickedLike = position;
            HomeProducts_ServiceModel.DataBean dataBean = (HomeProducts_ServiceModel.DataBean) object;
            likeProduct(dataBean.getId());
        }
        if (callerIdentity.equals("event10")) {
            HomeProducts_ServiceModel.DataBean dataBean = (HomeProducts_ServiceModel.DataBean) object;
            if (dataBean != null) {
                Intent intent = new Intent(WishListProfileActivity.this, ProductDetailActivity.class);
                intent.putExtra("product_id", dataBean.getId());
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        }
    }

    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        if (resultData.getTag().equals("HOME_PRODUCTS")) {
            homeProducts_serviceModel = (HomeProducts_ServiceModel) resultData.getRootData().getValue();
            if (homeProducts_serviceModel.getData().size() > 0) {
                activityWishlistProfileBinding.setIsNoRecord(false);

                wishList_profile_recyclerViewAdapter = new WishList_Profile_RecyclerViewAdapter(WishListProfileActivity.this, homeProducts_serviceModel.getData(), this::onClick);
                activityWishlistProfileBinding.productsRecyclerViewId.setLayoutManager(new GridLayoutManager(WishListProfileActivity.this, 2));
                activityWishlistProfileBinding.productsRecyclerViewId.setAdapter(wishList_profile_recyclerViewAdapter);

            } else {
                activityWishlistProfileBinding.setIsNoRecord(true);
                wishList_profile_recyclerViewAdapter = new WishList_Profile_RecyclerViewAdapter(WishListProfileActivity.this, homeProducts_serviceModel.getData(), this::onClick);
                activityWishlistProfileBinding.productsRecyclerViewId.setLayoutManager(new GridLayoutManager(WishListProfileActivity.this, 2));
                activityWishlistProfileBinding.productsRecyclerViewId.setAdapter(wishList_profile_recyclerViewAdapter);
            }
        }
        if (resultData.getTag().equals("PRODUCT_ADD_WISHLIST")) {
            product_add_wiLit = (Product_Add_WiLit) resultData.getRootData().getValue();
            if (product_add_wiLit.isStatus().equals("true")) {
                if (homeProducts_serviceModel.getData().get(clickedWish).getWishstatus().equals("Wished")) {
                    homeProducts_serviceModel.getData().remove(clickedWish);
                    wishList_profile_recyclerViewAdapter.notifyItemRemoved(clickedWish);
                    wishList_profile_recyclerViewAdapter.notifyItemRangeChanged(clickedWish, homeProducts_serviceModel.getData().size());

                    if(homeProducts_serviceModel.getData().size()==0){
                        activityWishlistProfileBinding.setIsNoRecord(true);
                    }
                }
            } else {
                AlphaHolder.customToast(WishListProfileActivity.this, product_add_wiLit.getMessage());
            }
            wishList_profile_recyclerViewAdapter.notifyItemChanged(clickedWish);
        }
        if (resultData.getTag().equals("PRODUCT_LIKE")) {
            productLike_serviceModel = (ProductLike_ServiceModel) resultData.getRootData().getValue();
            if (productLike_serviceModel.isStatus().equals("true")) {
                if (homeProducts_serviceModel.getData().get(clickedLike).getLikeornot().equals("Like")) {
                    homeProducts_serviceModel.getData().get(clickedLike).setLikeornot("Not Liked");
                    manipulaTotalLike(false);
                } else {
                    homeProducts_serviceModel.getData().get(clickedLike).setLikeornot("Like");
                    manipulaTotalLike(true);

                }
            } else {
                AlphaHolder.customToast(WishListProfileActivity.this, productLike_serviceModel.getMessage());
            }
            wishList_profile_recyclerViewAdapter.notifyItemChanged(clickedLike);
        }
    }

    public void addProductToWishlist(String productId) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", SharedPreferences_Util.getToken(WishListProfileActivity.this));
        paramMap.put("prd_id", productId);

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("PRODUCT_ADD_WISHLIST", paramMap, false, WishListProfileActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    private void getWishListData() {
        // String categoryIds = PreferenceManager.getDefaultSharedPreferences(WishListProfileActivity.this).getString("selectedcategory", "");
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", SharedPreferences_Util.getToken(WishListProfileActivity.this));
        paramMap.put("wishlist", "wishlist");
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("HOME_PRODUCTS", paramMap, true, WishListProfileActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }
    public void likeProduct(String productId) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", SharedPreferences_Util.getToken(WishListProfileActivity.this));
        paramMap.put("prd_id", productId);

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("PRODUCT_LIKE", paramMap, false, WishListProfileActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }
    private void manipulaTotalLike(boolean isPlus) {
        try {
            String toLikeString = homeProducts_serviceModel.getData().get(clickedLike).getTotal_like();
            if (toLikeString != null && !toLikeString.equals("")) {
                int totatLikeInt = Integer.parseInt(toLikeString);

                if (isPlus)
                    totatLikeInt++;
                else
                    totatLikeInt--;

                homeProducts_serviceModel.getData().get(clickedLike).setTotal_like(String.valueOf(totatLikeInt));
            }
            Log.e("LIKECOMING", "YES");

        } catch (Exception EE) {

        }
    }
}