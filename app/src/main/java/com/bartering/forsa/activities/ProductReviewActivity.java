package com.bartering.forsa.activities;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bartering.forsa.AppCompactActivity;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.ActivityProductReviewBinding;
import com.bartering.forsa.mutableViewModel.ParamOptimizer_ViewModel;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.Comman_ServiceModel;
import com.bartering.forsa.retrofit.service_model.Transaction_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.SharedPreferences_Util;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class ProductReviewActivity extends AppCompactActivity implements ClickListener, Observer<Object> {

    ActivityProductReviewBinding activityProductReviewBinding;
    Transaction_ServiceModel.DataBean transaction_data;
    ParamOptimizer_ViewModel paramOptimizer_viewModel;

    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;

    Comman_ServiceModel comman_serviceModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        paramOptimizer_viewModel = new ParamOptimizer_ViewModel();
        activityProductReviewBinding = DataBindingUtil.setContentView(this, R.layout.activity_product_review);
        activityProductReviewBinding.setClickListener(this::onClick);
        activityProductReviewBinding.setParamOptimizer(paramOptimizer_viewModel);
        getIntentData();
    }

    private void submitReview() {
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        Map<String, String> param = new HashMap<>();
        param.put("token", SharedPreferences_Util.getToken(ProductReviewActivity.this));
        param.put("prd_id", transaction_data.getId());
        param.put("rating", String.valueOf(paramOptimizer_viewModel.getRating().getValue()));
        param.put("comment", paramOptimizer_viewModel.getComment().getValue());
        viewModel.loadData("PRODUCT_RATING", param, true, ProductReviewActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    private void getIntentData() {
        transaction_data = (Transaction_ServiceModel.DataBean) getIntent().getSerializableExtra("DETAILS");
        if (transaction_data != null) {
            activityProductReviewBinding.setTransactionDetail(transaction_data);
        }
    }

    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        if (resultData.getTag().equals("PRODUCT_RATING")) {
            comman_serviceModel = (Comman_ServiceModel) resultData.getRootData().getValue();
            if (comman_serviceModel.isStatus().equals("true")) {
                ProductReviewActivity.this.finish();
            }
            AlphaHolder.customToast(ProductReviewActivity.this, comman_serviceModel.getMessage());
        }
    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event1")) {
            ProductReviewActivity.this.finish();
        }
        if (callerIdentity.equals("event2")) {

            if (TextUtils.isEmpty(paramOptimizer_viewModel.getComment().getValue())) {
                AlphaHolder.customToast(ProductReviewActivity.this, getResources().getString(R.string.pleaseentercomment));
            } else {
                submitReview();
            }
        }
    }
}