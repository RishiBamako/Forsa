package com.bartering.forsa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bartering.forsa.AppCompactActivity;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.ActivityForgetPasswordLinkBinding;
import com.bartering.forsa.mutableViewModel.ParamOptimizer_ViewModel;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.Comman_ServiceModel;
import com.bartering.forsa.retrofit.service_model.SignUpEmail_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class ForgotPasswordLinkActivity extends AppCompactActivity implements ClickListener, Observer<Object> {

    ActivityForgetPasswordLinkBinding parentBinding;
    ParamOptimizer_ViewModel paramOptimizer_viewModel;

    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;
    SignUpEmail_ServiceModel signUpEmail_serviceModel;
    String email;

    Comman_ServiceModel comman_serviceModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentBinding = DataBindingUtil.setContentView(this, R.layout.activity_forget_password_link);
        paramOptimizer_viewModel = new ParamOptimizer_ViewModel();
        parentBinding.setClickListener(this::onClick);
        getData();
    }

    private void getData() {
        if (getIntent().getExtras() != null) {
            email = getIntent().getExtras().getString("email");
            parentBinding.setMail("<u>" + email + "</u>");
            parentBinding.setResendEail("<u>" + getString(R.string.resendemail) + "</u>");
        }
    }

    public void signUpWithMobileNo(View view) {
        this.finish();
        Intent intent = new Intent(ForgotPasswordLinkActivity.this, SignUpWithPhoneActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void backPressed(View view) {
        this.finish();
    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event1")) {
          /*  Intent intent = new Intent(ForgotPasswordLinkActivity.this, RecoverPasswordActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);*/
        }
        if (callerIdentity.equals("event2")) {
            forgotPasssword();
        }
    }

    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        if (resultData.getTag().equals("FORGOT_PASSWORD")) {
            comman_serviceModel = (Comman_ServiceModel) resultData.getRootData().getValue();
            if (null != comman_serviceModel) {
                AlphaHolder.customToast(ForgotPasswordLinkActivity.this, comman_serviceModel.getMessage());
            }
        }
    }

    public void forgotPasssword() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("email", email);
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("FORGOT_PASSWORD", paramMap, true, ForgotPasswordLinkActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }
}