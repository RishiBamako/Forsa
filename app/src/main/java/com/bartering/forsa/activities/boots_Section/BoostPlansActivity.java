package com.bartering.forsa.activities.boots_Section;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;

import com.bartering.forsa.ACRA_Slack.application.AcraSlackSample;
import com.bartering.forsa.AppCompactActivity;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.ActivityBoostPlanBinding;
import com.bartering.forsa.recyclerViewAdapter.BoostPlans_RecyclerViewAdapter;
import com.bartering.forsa.recyclerViewAdapter.ProductDescriptionHead_RecyclerViewAdapter;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.BoostPlans_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.SharedPreferences_Util;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class BoostPlansActivity extends AppCompactActivity implements ClickListener, Observer<Object> {

    ActivityBoostPlanBinding activityBoostPlanBinding;
    ProductDescriptionHead_RecyclerViewAdapter subscriptionPlans_recyclerViewAdapter;

    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;
    BoostPlans_ServiceModel boostPlans_serviceModel;
    BoostPlans_RecyclerViewAdapter boostPlans_recyclerViewAdapter;

    BoostPlans_ServiceModel.DataBean dataBean;
    int SELECT_PACKAGE_POSITION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBoostPlanBinding = DataBindingUtil.setContentView(this, R.layout.activity_boost_plan);
        activityBoostPlanBinding.setClickListener(this::onClick);
        AlphaHolder.stackList.add(this);
        getBoostPackages();


    }

    private void getBoostPackages() {
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        Map<String, String> param = new HashMap<>();
        param.put("token", SharedPreferences_Util.getToken(BoostPlansActivity.this));
        viewModel.loadData("BOOST_PLANS", param, true, BoostPlansActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    public void next(View view) {

    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event1")) {
            this.finish();
        }
        if (callerIdentity.equals("event2")) {
            ((AcraSlackSample) this.getApplication()).switcher(BoostPlansActivity.this, Order_detailActivity.class, 0);
        }
        if (callerIdentity.equals("event4")) {
            dataBean = (BoostPlans_ServiceModel.DataBean) object;
            SELECT_PACKAGE_POSITION = position;
            for (int b = 0; b < boostPlans_serviceModel.getData().size(); b++) {
                boostPlans_serviceModel.getData().get(b).setSelected(false);
                if (b == boostPlans_serviceModel.getData().size() - 1) {
                    boostPlans_serviceModel.getData().get(position).setSelected(true);
                    boostPlans_recyclerViewAdapter.notifyDataSetChanged();
                }
            }
        }
        if (callerIdentity.equals("event5")) {
            if (boostPlans_serviceModel.getData().get(SELECT_PACKAGE_POSITION).isSelected()) {
                Intent intent = new Intent(BoostPlansActivity.this, Order_detailActivity.class);
                intent.putExtra("DATA", dataBean);
                startActivity(intent);
            } else {
                AlphaHolder.customToast(BoostPlansActivity.this, getResources().getString(R.string.please_select_valid_package));
            }

        }

    }


    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        if (resultData.getTag().equals("BOOST_PLANS")) {
            boostPlans_serviceModel = (BoostPlans_ServiceModel) resultData.getRootData().getValue();

            if (boostPlans_serviceModel != null && boostPlans_serviceModel.isStatus().equals("true")) {

                boostPlans_serviceModel.getData().get(0).setSelected(true);
                boostPlans_recyclerViewAdapter = new BoostPlans_RecyclerViewAdapter(BoostPlansActivity.this, boostPlans_serviceModel.getData(), this::onClick);
                ((DefaultItemAnimator) activityBoostPlanBinding.boostPlanRecyclerViewId.getItemAnimator()).setSupportsChangeAnimations(false);
                activityBoostPlanBinding.boostPlanRecyclerViewId.setAdapter(boostPlans_recyclerViewAdapter);
                dataBean = boostPlans_serviceModel.getData().get(0);

            } else {
                AlphaHolder.customToast(BoostPlansActivity.this, boostPlans_serviceModel.getMessage());
            }
        }
    }
}