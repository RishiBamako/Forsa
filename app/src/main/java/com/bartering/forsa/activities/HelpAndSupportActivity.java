package com.bartering.forsa.activities;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bartering.forsa.AppCompactActivity;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.ActivityHelpandSupportBinding;
import com.bartering.forsa.mutableViewModel.ParamOptimizer_ViewModel;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.Comman_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.SharedPreferences_Util;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class HelpAndSupportActivity extends AppCompactActivity implements ClickListener, Observer<Object> {

    ActivityHelpandSupportBinding activityHelpandSupportBinding;

    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;

    ParamOptimizer_ViewModel paramOptimizer_viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        paramOptimizer_viewModel = new ParamOptimizer_ViewModel();
        activityHelpandSupportBinding = DataBindingUtil.setContentView(this, R.layout.activity_helpand_support);
        activityHelpandSupportBinding.setClickListener(this::onClick);
        activityHelpandSupportBinding.setParam(paramOptimizer_viewModel);

    }

    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        if (resultData.getTag().equals("SEND_SUPPORT")) {
            Comman_ServiceModel comman_serviceModel = (Comman_ServiceModel) resultData.getRootData().getValue();
            if (comman_serviceModel.isStatus().equals("true")) {
                this.finish();
            }
            AlphaHolder.customToast(HelpAndSupportActivity.this, comman_serviceModel.getMessage());
        }
    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event1")) {
            HelpAndSupportActivity.this.finish();
        }
        if (callerIdentity.equals("event2")) {
            if (TextUtils.isEmpty(paramOptimizer_viewModel.getFullName().getValue())) {
                AlphaHolder.customToast(HelpAndSupportActivity.this, getResources().getString(R.string.usernamemustrequire));
            } else if (TextUtils.isEmpty(paramOptimizer_viewModel.getEmail().getValue())) {
                AlphaHolder.customToast(HelpAndSupportActivity.this, getResources().getString(R.string.emailcantbeempty));
            } else if (!AlphaHolder.isEmailValid(paramOptimizer_viewModel.getEmail().getValue())) {
                AlphaHolder.customToast(HelpAndSupportActivity.this, getResources().getString(R.string.emailmustvalid));
            } else if (TextUtils.isEmpty(paramOptimizer_viewModel.getMessage().getValue())) {
                AlphaHolder.customToast(HelpAndSupportActivity.this, getResources().getString(R.string.messagecannotbeempty));
            } else {
                sendRequest();
            }
        }
    }

    void sendRequest() {
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        Map<String, String> param = new HashMap<>();
        param.put("token", SharedPreferences_Util.getToken(HelpAndSupportActivity.this));
        param.put("name", paramOptimizer_viewModel.getFullName().getValue());
        param.put("email", paramOptimizer_viewModel.getEmail().getValue());
        param.put("message", paramOptimizer_viewModel.getMessage().getValue());

        viewModel.loadData("SEND_SUPPORT", param, true, HelpAndSupportActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }
}