package com.bartering.forsa.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.bartering.forsa.databinding.FragmentSimilarProductsTabBinding;
import com.bartering.forsa.recyclerViewAdapter.SimilarProducts_Tabs_RecyclerViewAdapter;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.BaseFragment;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.ProductLike_ServiceModel;
import com.bartering.forsa.retrofit.service_model.Product_Add_WiLit;
import com.bartering.forsa.retrofit.service_model.SimilarProducts_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.SharedPreferences_Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;


public class SimilarProducts_TabFragment extends BaseFragment implements Observer<Object>, ClickListener {

    FragmentSimilarProductsTabBinding fragmentSimilarProductsTabBinding;
    SimilarProducts_Tabs_RecyclerViewAdapter similarProducts_recyclerViewAdapter;
    Activity activity;

    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;

    SimilarProducts_ServiceModel similarProducts_serviceModel;
    ProductLike_ServiceModel productLike_serviceModel;
    Product_Add_WiLit product_add_wiLit;

    String product_Id;
    ClickListener clickListener;

    int clickedLike, clickedWish;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentSimilarProductsTabBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_similar_products__tab, container, false);
        fragmentSimilarProductsTabBinding.setIsNoRecord(false);
        clickListener = this::onClick;
        getProductId();
        listener();
        getSimilarProducts();
        return fragmentSimilarProductsTabBinding.getRoot();
    }

    private void getProductId() {
        if (activity.getIntent().getExtras() != null) {
            Bundle bundle = activity.getIntent().getExtras();
            if (bundle != null) {
                product_Id = bundle.getString("product_id");
            }
        }
    }

    public void getSimilarProducts() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("prd_id", product_Id);
        paramMap.put("token", SharedPreferences_Util.getToken(activity));
        if (!TextUtils.isEmpty(SharedPreferences_Util.getUser_Id(activity)))
            paramMap.put("userid", SharedPreferences_Util.getUser_Id(activity));

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("SIMILAR_PRODUCTS_LIST", paramMap, false, activity);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    private void listener() {
        fragmentSimilarProductsTabBinding.viewAllProductsTVId.setPaintFlags(fragmentSimilarProductsTabBinding.viewAllProductsTVId.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        fragmentSimilarProductsTabBinding.viewAllProductsTVId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (similarProducts_serviceModel != null && similarProducts_serviceModel.getData().size() > 0) {
                    similarProducts_recyclerViewAdapter = new SimilarProducts_Tabs_RecyclerViewAdapter(activity, similarProducts_serviceModel.getData(), clickListener);
                    fragmentSimilarProductsTabBinding.productsRecyclerViewId.setLayoutManager(new GridLayoutManager(activity, 2));
                    fragmentSimilarProductsTabBinding.productsRecyclerViewId.setHasFixedSize(true);
                    fragmentSimilarProductsTabBinding.productsRecyclerViewId.setAdapter(similarProducts_recyclerViewAdapter);
                    fragmentSimilarProductsTabBinding.viewAllProductsTVId.setVisibility(View.GONE);
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
        if (resultData.getTag().equals("SIMILAR_PRODUCTS_LIST")) {
            similarProducts_serviceModel = (SimilarProducts_ServiceModel) resultData.getRootData().getValue();
            if (similarProducts_serviceModel.getData().size() > 0) {
                fragmentSimilarProductsTabBinding.setIsNoRecord(false);

                if (similarProducts_serviceModel.getData().size() < 2)
                    fragmentSimilarProductsTabBinding.viewAllProductsTVId.setVisibility(View.GONE);

                AlphaHolder.similarDataProductsList = new ArrayList<>();
                AlphaHolder.similarDataProductsList = AlphaHolder.getNoProducts(similarProducts_serviceModel.getData());
                similarProducts_recyclerViewAdapter = new SimilarProducts_Tabs_RecyclerViewAdapter(activity, AlphaHolder.similarDataProductsList, this::onClick);
                fragmentSimilarProductsTabBinding.productsRecyclerViewId.setLayoutManager(new GridLayoutManager(activity, 2));
                fragmentSimilarProductsTabBinding.productsRecyclerViewId.setAdapter(similarProducts_recyclerViewAdapter);

            } else {
                fragmentSimilarProductsTabBinding.setIsNoRecord(true);
                fragmentSimilarProductsTabBinding.viewAllProductsTVId.setVisibility(View.GONE);
            }
        }
        if (resultData.getTag().equals("PRODUCT_LIKE")) {
            productLike_serviceModel = (ProductLike_ServiceModel) resultData.getRootData().getValue();
            if (productLike_serviceModel.isStatus().equals("true")) {
                if (similarProducts_serviceModel.getData().get(clickedLike).getLikeornot().equals("Like")) {
                    similarProducts_serviceModel.getData().get(clickedLike).setLikeornot("Not Liked");
                    manipulaTotalLike(false);
                } else {
                    similarProducts_serviceModel.getData().get(clickedLike).setLikeornot("Like");
                    manipulaTotalLike(true);

                }
            } else {
                AlphaHolder.customToast(activity, productLike_serviceModel.getMessage());
            }
            similarProducts_recyclerViewAdapter.notifyItemChanged(clickedLike);
        }
        if (resultData.getTag().equals("PRODUCT_ADD_WISHLIST")) {
            product_add_wiLit = (Product_Add_WiLit) resultData.getRootData().getValue();
            if (product_add_wiLit.isStatus().equals("true")) {
                if (similarProducts_serviceModel.getData().get(clickedWish).getWishstatus().equals("Wished")) {
                    similarProducts_serviceModel.getData().get(clickedWish).setWishstatus("Not Wished");
                } else {
                    similarProducts_serviceModel.getData().get(clickedWish).setWishstatus("Wished");
                }

            } else {
                AlphaHolder.customToast(activity, product_add_wiLit.getMessage());
            }
            similarProducts_recyclerViewAdapter.notifyItemChanged(clickedWish);
        }
    }

    private void manipulaTotalLike(boolean isPlus) {
        try {
            String toLikeString = similarProducts_serviceModel.getData().get(clickedLike).getTotal_like();
            if (toLikeString != null && !toLikeString.equals("")) {
                int totatLikeInt = Integer.parseInt(toLikeString);
                if (isPlus)
                    totatLikeInt++;
                else
                    totatLikeInt--;

                similarProducts_serviceModel.getData().get(clickedLike).setTotal_like(String.valueOf(totatLikeInt));
            }
        } catch (Exception EE) {

        }
    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event2")) { ///wish
            clickedWish = position;

            SimilarProducts_ServiceModel.DataBean dataBean = (SimilarProducts_ServiceModel.DataBean) object;
            if (dataBean != null)
                addProductToWishlist(dataBean.getId());
        }
        if (callerIdentity.equals("event3")) {//like
            clickedLike = position;

            SimilarProducts_ServiceModel.DataBean dataBean = (SimilarProducts_ServiceModel.DataBean) object;
            if (dataBean != null)
                likeProduct(dataBean.getId());

        }
        if (callerIdentity.equals("event4")) {//parent productview
            SimilarProducts_ServiceModel.DataBean dataBean = (SimilarProducts_ServiceModel.DataBean) object;
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