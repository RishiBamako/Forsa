package com.bartering.forsa.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bartering.forsa.AppCompactActivity;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.ActivityMobilenoVerificationBinding;
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

public class MobileNoVerificationActivity extends AppCompactActivity implements TextWatcher, Observer<Object>, ClickListener {

    ActivityMobilenoVerificationBinding parentBinding;


    @Inject
    ParamOptimizer_ViewModel paramOptimizer_viewModel;

    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;

    Comman_ServiceModel comman_serviceModel;
    String otp, mobileNo, calledFrom, token;

    private Handler mWaitHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentBinding = DataBindingUtil.setContentView(this, R.layout.activity_mobileno_verification);
        AlphaHolder.stackList.add(this);
        if (getIntent().getExtras() != null) {

            token = getIntent().getExtras().getString("TOKEN");
            otp = getIntent().getExtras().getString("OTP");
            mobileNo = getIntent().getExtras().getString("MOBILE_NO");
            calledFrom = getIntent().getExtras().getString("FROM");
        }
        underLine();
        listener();
    }

    private void listener() {
        parentBinding.digitOneEditTextId.addTextChangedListener(this);
        parentBinding.digitTwoEditTextId.addTextChangedListener(this);
        parentBinding.digitThreeEditTextId.addTextChangedListener(this);
        parentBinding.digitFourEditTextId.addTextChangedListener(this);
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(parentBinding.digitOneEditTextId.getWindowToken(), 1);

        parentBinding.digitOneEditTextId.setText(otp.substring(0, 1));
        parentBinding.digitTwoEditTextId.setText(otp.substring(1, 2));
        parentBinding.digitThreeEditTextId.setText(otp.substring(2, 3));
        parentBinding.digitFourEditTextId.setText(otp.substring(3, 4));
        parentBinding.digitFourEditTextId.requestFocus();


    }

    private void underLine() {
        parentBinding.otpSendTextViewId.setText(Html.fromHtml(getResources().getString(R.string.mobileNo_one) + " <b>" + mobileNo + "</b>" + getResources().getString(R.string.mobileNo_two)));
    }

    public void backPressed(View view) {
        this.finish();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (parentBinding.digitOneEditTextId.length() == 1) {
            parentBinding.digitOneEditTextId.clearFocus();
            parentBinding.digitTwoEditTextId.requestFocus();
            parentBinding.digitTwoEditTextId.setCursorVisible(true);
        }
        if (parentBinding.digitTwoEditTextId.length() == 1) {
            parentBinding.digitTwoEditTextId.clearFocus();
            parentBinding.digitThreeEditTextId.requestFocus();
            parentBinding.digitThreeEditTextId.setCursorVisible(true);
        }
        if (parentBinding.digitThreeEditTextId.length() == 1) {
            parentBinding.digitThreeEditTextId.clearFocus();
            parentBinding.digitFourEditTextId.requestFocus();
            parentBinding.digitFourEditTextId.setCursorVisible(true);
        }
        if (parentBinding.digitFourEditTextId.length() == 1) {
            parentBinding.digitFourEditTextId.clearFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(parentBinding.digitFourEditTextId.getWindowToken(), 0);
            delay();
        }
    }

    private void delay() {
        mWaitHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    serviceManipulation();
                } catch (Exception ignored) {
                    ignored.printStackTrace();
                }
            }
        }, 1000);  // Give a 5 seconds delay.
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void serviceManipulation() {
        String digit1 = parentBinding.digitOneEditTextId.getText().toString();
        String digit2 = parentBinding.digitTwoEditTextId.getText().toString();
        String digit3 = parentBinding.digitThreeEditTextId.getText().toString();
        String digit4 = parentBinding.digitFourEditTextId.getText().toString();

        String otp = digit1 + digit2 + digit3 + digit4;
        if (otp.length() == 4) {
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("otp", otp);
            paramMap.put("token", token);

            viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
            viewModel.loadData("OTP_VERIFICATION", paramMap, true, MobileNoVerificationActivity.this);
            viewModel.getRootData().observe(this, this::onChanged);
        } else {
            AlphaHolder.customToast(MobileNoVerificationActivity.this, getString(R.string.invalidotp));
        }

    }

    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        if (resultData.getTag().equals("OTP_VERIFICATION")) {
            comman_serviceModel = (Comman_ServiceModel) resultData.getRootData().getValue();
            if (comman_serviceModel.isStatus().equals("true")) {
                if (calledFrom.equals("SIGN_UP")) {
                    MobileNoVerificationActivity.this.finish();
                    Intent intent = new Intent(MobileNoVerificationActivity.this, SignInActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                } else {
                    Intent intent = new Intent(MobileNoVerificationActivity.this, ChooseLanguageActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    MobileNoVerificationActivity.this.finish();

                    /*if (TextUtils.isEmpty(SharedPreferences_Util.getUser_Image(MobileNoVerificationActivity.this))) {
                        MobileNoVerificationActivity.this.finish();
                        Intent intent = new Intent(MobileNoVerificationActivity.this, ProfileActivity.class);
                        intent.putExtra("TOKEN", "");
                        intent.putExtra("IS_FROM", "OTP_LOGIN");
                        intent.putExtra("USER_NAME", "");
                        intent.putExtra("EMAIL_ADDRESS", "");
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(MobileNoVerificationActivity.this, ChooseCategoryActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        MobileNoVerificationActivity.this.finish();
                    }*/
                }

            }
            //AlphaHolder.customToast(MobileNoVerificationActivity.this, comman_serviceModel.getMessage());
        }
    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {

    }
}