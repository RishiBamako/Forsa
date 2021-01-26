package com.bartering.forsa.buySubscriptionPlanProcess;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;

import com.bartering.forsa.AppCompactActivity;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.ActivitySubscriptionPlanBinding;
import com.bartering.forsa.recyclerViewAdapter.SubscriptionPlans_RecyclerViewAdapter;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.SubscriptionPackages_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.SharedPreferences_Util;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class SubscriptionPlansActivity extends AppCompactActivity implements ClickListener, Observer<Object> {

    ActivitySubscriptionPlanBinding activitySubscriptionPlanBinding;
    SubscriptionPlans_RecyclerViewAdapter subscriptionPlans_recyclerViewAdapter;

    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;

    SubscriptionPackages_ServiceModel subscriptionPackages_serviceModel;
    int clickedPackage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySubscriptionPlanBinding = DataBindingUtil.setContentView(this, R.layout.activity_subscription_plan);
        activitySubscriptionPlanBinding.setClickListener(this::onClick);
        AlphaHolder.stackList.add(this);
        getSubscriptionPackages();

    }
    private void getSubscriptionPackages() {
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        Map<String, String> param = new HashMap<>();
        param.put("token", SharedPreferences_Util.getToken(SubscriptionPlansActivity.this));
        viewModel.loadData("SUBSCRIPTION_PACKAGES", param, true, SubscriptionPlansActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        if (resultData.getTag().equals("SUBSCRIPTION_PACKAGES")) {
            subscriptionPackages_serviceModel = (SubscriptionPackages_ServiceModel) resultData.getRootData().getValue();
            if (subscriptionPackages_serviceModel != null) {
                if (subscriptionPackages_serviceModel.isStatus().equals("true")) {
                    subscriptionPackages_serviceModel.getData().get(0).setShoworSelected(true);

                    subscriptionPlans_recyclerViewAdapter = new SubscriptionPlans_RecyclerViewAdapter(SubscriptionPlansActivity.this, subscriptionPackages_serviceModel.getData(), this::onClick);
                    ((DefaultItemAnimator) activitySubscriptionPlanBinding.silverPackage.getItemAnimator()).setSupportsChangeAnimations(false);
                    activitySubscriptionPlanBinding.silverPackage.setAdapter(subscriptionPlans_recyclerViewAdapter);

                } else {
                    AlphaHolder.customToast(SubscriptionPlansActivity.this, subscriptionPackages_serviceModel.getMessage());
                }
            }
        }
    }
    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event3")) {
            for (int c = 0; c < subscriptionPackages_serviceModel.getData().size(); c++) {
                if (position == c) {
                    clickedPackage = position;
                    subscriptionPackages_serviceModel.getData().get(c).setShoworSelected(true);
                } else {
                    subscriptionPackages_serviceModel.getData().get(c).setShoworSelected(false);
                }
            }
            subscriptionPlans_recyclerViewAdapter.notifyDataSetChanged();
        }
        if (callerIdentity.equals("event2")) {
            Intent intent = new Intent(SubscriptionPlansActivity.this, OrderDetailActivity.class);
            intent.putExtra("PACKAGE_INFO", subscriptionPackages_serviceModel.getData().get(clickedPackage));
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
        if (callerIdentity.equals("event1")) {
            SubscriptionPlansActivity.this.finish();
        }
    }
}