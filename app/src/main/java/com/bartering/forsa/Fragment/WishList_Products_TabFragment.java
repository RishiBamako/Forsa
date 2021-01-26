package com.bartering.forsa.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.activities.bartering_detail.ProductDetailActivity;
import com.bartering.forsa.databinding.FragmentWishlistProductsTabBinding;
import com.bartering.forsa.recyclerViewAdapter.WishlistProducts_Tabs_RecyclerViewAdapter;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.BaseFragment;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.ProductLike_ServiceModel;
import com.bartering.forsa.retrofit.service_model.Product_Add_WiLit;
import com.bartering.forsa.retrofit.service_model.WishlistData_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.SharedPreferences_Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;


public class WishList_Products_TabFragment extends BaseFragment implements Observer<Object>, ClickListener {

    FragmentWishlistProductsTabBinding fragmentWishlistProductsTabBinding;
    WishlistProducts_Tabs_RecyclerViewAdapter wishlistProducts_tabs_recyclerViewAdapter;
    Activity activity;
    List<String> stringList;


    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;


    ProductLike_ServiceModel productLike_serviceModel;
    Product_Add_WiLit product_add_wiLit;
    WishlistData_ServiceModel wishlistData_serviceModel;

    String product_Id;
    ClickListener clickListener;

    int clickedLike, clickedWish;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentWishlistProductsTabBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_wishlist_products__tab, container, false);
        fragmentWishlistProductsTabBinding.setIsNoRecord(false);
        clickListener = this::onClick;
        getProductId();
        listener();
        getWishlistProducts();
        return fragmentWishlistProductsTabBinding.getRoot();
    }

    private void getProductId() {
        if (activity.getIntent().getExtras() != null) {
            Bundle bundle = activity.getIntent().getExtras();
            if (bundle != null) {
                product_Id = bundle.getString("product_id");
            }
        }
    }

    public void getWishlistProducts() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("prd_id", product_Id);
        paramMap.put("token", SharedPreferences_Util.getToken(activity));
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("WISHLIST_PRODUCTS_LIST", paramMap, false, activity);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    private void listener() {
        fragmentWishlistProductsTabBinding.viewAllProductsTVId.setPaintFlags(fragmentWishlistProductsTabBinding.viewAllProductsTVId.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        fragmentWishlistProductsTabBinding.viewAllProductsTVId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentWishlistProductsTabBinding.viewAllProductsTVId.setVisibility(View.GONE);
                if (wishlistData_serviceModel.getData() != null && wishlistData_serviceModel.getData().size() > 0) {
                    fragmentWishlistProductsTabBinding.setIsNoRecord(false);
                    wishlistProducts_tabs_recyclerViewAdapter = new WishlistProducts_Tabs_RecyclerViewAdapter(activity, wishlistData_serviceModel.getData(), clickListener);
                    fragmentWishlistProductsTabBinding.productsRecyclerViewId.setLayoutManager(new GridLayoutManager(activity, 2));
                    fragmentWishlistProductsTabBinding.productsRecyclerViewId.setHasFixedSize(true);
                    fragmentWishlistProductsTabBinding.productsRecyclerViewId.setAdapter(wishlistProducts_tabs_recyclerViewAdapter);
                } else {
                    fragmentWishlistProductsTabBinding.setIsNoRecord(true);
                }
            }
        });
    }


    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        if (resultData.getTag().equals("WISHLIST_PRODUCTS_LIST")) {
            wishlistData_serviceModel = (WishlistData_ServiceModel) resultData.getRootData().getValue();
            if (wishlistData_serviceModel.isStatus().equals("true")) {
                if (wishlistData_serviceModel.getData() != null && wishlistData_serviceModel.getData().size() > 0) {
                    fragmentWishlistProductsTabBinding.setIsNoRecord(false);

                    if (wishlistData_serviceModel.getData().size() < 2)
                        fragmentWishlistProductsTabBinding.viewAllProductsTVId.setVisibility(View.GONE);

                    AlphaHolder.wishlistDataProductsList = new ArrayList<>();
                    AlphaHolder.wishlistDataProductsList = AlphaHolder.getNoSimiProducts(wishlistData_serviceModel.getData());

                    wishlistProducts_tabs_recyclerViewAdapter = new WishlistProducts_Tabs_RecyclerViewAdapter(activity, AlphaHolder.wishlistDataProductsList, this::onClick);
                    fragmentWishlistProductsTabBinding.productsRecyclerViewId.setLayoutManager(new GridLayoutManager(activity, 2));
                    fragmentWishlistProductsTabBinding.productsRecyclerViewId.setHasFixedSize(true);
                    fragmentWishlistProductsTabBinding.productsRecyclerViewId.setAdapter(wishlistProducts_tabs_recyclerViewAdapter);
                } else {
                    fragmentWishlistProductsTabBinding.setIsNoRecord(true);
                    fragmentWishlistProductsTabBinding.viewAllProductsTVId.setVisibility(View.GONE);
                }
            } else {
                AlphaHolder.customToast(activity, wishlistData_serviceModel.getMessage());
            }
        }
        if (resultData.getTag().equals("PRODUCT_LIKE")) {
            productLike_serviceModel = (ProductLike_ServiceModel) resultData.getRootData().getValue();
            if (productLike_serviceModel.isStatus().equals("true")) {
                if (wishlistData_serviceModel.getData().get(clickedLike).getLike().equals("1")) {
                    wishlistData_serviceModel.getData().get(clickedLike).setLike("0");
                    manipulaTotalLike(false);
                } else {
                    wishlistData_serviceModel.getData().get(clickedLike).setLike("1");
                    manipulaTotalLike(true);

                }
            } else {
                AlphaHolder.customToast(activity, productLike_serviceModel.getMessage());
            }
            wishlistProducts_tabs_recyclerViewAdapter.notifyItemChanged(clickedLike);
        }
        if (resultData.getTag().equals("PRODUCT_ADD_WISHLIST")) {
            product_add_wiLit = (Product_Add_WiLit) resultData.getRootData().getValue();
            if (product_add_wiLit.isStatus().equals("true")) {
                if (wishlistData_serviceModel.getData().get(clickedWish).getWish().equals("1")) {
                    wishlistData_serviceModel.getData().get(clickedWish).setWish("0");
                } else {
                    wishlistData_serviceModel.getData().get(clickedWish).setWish("1");
                    ;
                }

            } else {
                AlphaHolder.customToast(activity, product_add_wiLit.getMessage());
            }
            wishlistProducts_tabs_recyclerViewAdapter.notifyItemChanged(clickedWish);
        }
    }

    private void manipulaTotalLike(boolean isPlus) {
        try {
            String toLikeString = wishlistData_serviceModel.getData().get(clickedLike).getTotal_like();
            if (toLikeString != null && !toLikeString.equals("")) {
                int totatLikeInt = Integer.parseInt(toLikeString);
                if (isPlus)
                    totatLikeInt++;
                else
                    totatLikeInt--;

                wishlistData_serviceModel.getData().get(clickedLike).setTotal_like(String.valueOf(totatLikeInt));
            }
        } catch (Exception EE) {

        }
    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event2")) { ///wish
            clickedWish = position;
            WishlistData_ServiceModel.DataBean dataBean = (WishlistData_ServiceModel.DataBean) object;
            if (dataBean != null)
                addProductToWishlist(dataBean.getId());
        }
        if (callerIdentity.equals("event3")) {//like
            clickedLike = position;
            WishlistData_ServiceModel.DataBean dataBean = (WishlistData_ServiceModel.DataBean) object;
            if (dataBean != null)
                likeProduct(dataBean.getId());

        }
        if (callerIdentity.equals("event4")) {//parent productview
            WishlistData_ServiceModel.DataBean dataBean = (WishlistData_ServiceModel.DataBean) object;
            Intent intent = new Intent(activity, ProductDetailActivity.class);
            intent.putExtra("product_id", dataBean.getId());
            startActivity(intent);
            activity.overridePendingTransition(0, 0);
        }
    }

    public void likeProduct(String productId) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", SharedPreferences_Util.getToken(activity));
        paramMap.put("prd_id", productId);

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("PRODUCT_LIKE", paramMap, false, activity);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    public void addProductToWishlist(String productId) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", SharedPreferences_Util.getToken(activity));
        paramMap.put("prd_id", productId);

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("PRODUCT_ADD_WISHLIST", paramMap, false, activity);
        viewModel.getRootData().observe(this, this::onChanged);
    }
}