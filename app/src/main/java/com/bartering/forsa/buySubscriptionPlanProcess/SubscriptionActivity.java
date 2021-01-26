package com.bartering.forsa.buySubscriptionPlanProcess;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bartering.forsa.AppCompactActivity;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.ActivitySubscriptionBinding;
import com.bartering.forsa.recyclerViewAdapter.Subscription_RecyclerViewAdapter;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.SubscriptionPackage_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.SharedPreferences_Util;

import java.util.HashMap;

import javax.inject.Inject;

public class SubscriptionActivity extends AppCompactActivity implements ClickListener, Observer<Object> {

    ActivitySubscriptionBinding parentBinding;

    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;
    SubscriptionPackage_ServiceModel subscriptionPackage_serviceModel;

    Subscription_RecyclerViewAdapter subscription_recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentBinding = DataBindingUtil.setContentView(this, R.layout.activity_subscription);
        parentBinding.setClickListener(this::onClick);
        loadSubscription();
    }

    private void loadSubscription() {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("token", SharedPreferences_Util.getToken(SubscriptionActivity.this));
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("SUBSCRIPTION_PACKAGE", paramMap, true, SubscriptionActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        if (resultData.getTag().equals("SUBSCRIPTION_PACKAGE")) {
            subscriptionPackage_serviceModel = (SubscriptionPackage_ServiceModel) resultData.getRootData().getValue();
            if (subscriptionPackage_serviceModel != null) {
                if (subscriptionPackage_serviceModel.isStatus().equals("true")) {
                    subscription_recyclerViewAdapter = new Subscription_RecyclerViewAdapter(SubscriptionActivity.this, subscriptionPackage_serviceModel.getData());
                    parentBinding.setParentRV(subscription_recyclerViewAdapter);
                } else {
                    AlphaHolder.customToast(SubscriptionActivity.this, subscriptionPackage_serviceModel.getMessage());
                }
            }
        }
    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event1")) {

        }
        if (callerIdentity.equals("event2")) {
            SubscriptionActivity.this.finish();
        }
    }

}