package com.bartering.forsa.activities;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bartering.forsa.AppCompactActivity;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.ActivityChangePasswordBinding;
import com.bartering.forsa.mutableViewModel.ParamOptimizer_ViewModel;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.Comman_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.SharedPreferences_Util;

import java.util.HashMap;

import javax.inject.Inject;

public class ChangePasswordActivity extends AppCompactActivity implements ClickListener, Observer<Object> {

    ActivityChangePasswordBinding activityChangePasswordBinding;

    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;
    Comman_ServiceModel comman_serviceModel;


    @Inject
    ParamOptimizer_ViewModel paramOptimizer_viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        paramOptimizer_viewModel = new ParamOptimizer_ViewModel();
        activityChangePasswordBinding = DataBindingUtil.setContentView(this, R.layout.activity_change_password);
        activityChangePasswordBinding.setData(paramOptimizer_viewModel);
        activityChangePasswordBinding.setClickListener(this::onClick);
    }

    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        if (resultData.getTag().equals("CHANGE_PASSWORD")) {
            Comman_ServiceModel comman_serviceModel = (Comman_ServiceModel) resultData.getRootData().getValue();
            if (comman_serviceModel.isStatus().equals("true")) {
                ChangePasswordActivity.this.finish();
            }
            AlphaHolder.customToast(ChangePasswordActivity.this, comman_serviceModel.getMessage());
        }
    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event1")) {
            if (TextUtils.isEmpty(paramOptimizer_viewModel.getCurrentPassword().getValue())) {
                AlphaHolder.customToast(ChangePasswordActivity.this, getResources().getString(R.string.currentpasswordcannotbeempty));
            } else if (TextUtils.isEmpty(paramOptimizer_viewModel.getNewPassword().getValue())) {
                AlphaHolder.customToast(ChangePasswordActivity.this, getResources().getString(R.string.newpasswordcannotbeempty));
            } else if (TextUtils.isEmpty(paramOptimizer_viewModel.getConfirmPassword().getValue())) {
                AlphaHolder.customToast(ChangePasswordActivity.this, getResources().getString(R.string.confirmpasswordcantbesame));
            } else {
                changePassword();
            }
        }
        if (callerIdentity.equals("event2")) {
            ChangePasswordActivity.this.finish();
        }
    }

    private void changePassword() {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("old_password", paramOptimizer_viewModel.getCurrentPassword().getValue());
        paramMap.put("new_password", paramOptimizer_viewModel.getNewPassword().getValue());
        paramMap.put("confirm_password", paramOptimizer_viewModel.getConfirmPassword().getValue());
        paramMap.put("token", SharedPreferences_Util.getToken(ChangePasswordActivity.this));

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("CHANGE_PASSWORD", paramMap, true, ChangePasswordActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }
}