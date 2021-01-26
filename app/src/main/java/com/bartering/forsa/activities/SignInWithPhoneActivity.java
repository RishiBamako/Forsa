package com.bartering.forsa.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bartering.forsa.AppCompactActivity;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.ActivitySignInMobilenoBinding;
import com.bartering.forsa.mutableViewModel.ParamOptimizer_ViewModel;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.SignIn_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.SharedPreferences_Util;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class SignInWithPhoneActivity extends AppCompactActivity implements Observer<Object>, ClickListener {

    SpannableString content;
    TextView termAndCoCheckBoxId, termsTextViewId, conditionAndPolicyTextView;

    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;

    @Inject
    ParamOptimizer_ViewModel paramOptimizer_viewModel;

    SignIn_ServiceModel signIn_serviceModel;

    ActivitySignInMobilenoBinding parentBinding;

    public SignInWithPhoneActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in_mobileno);
        parentBinding.setClickListener(this::onClick);
        paramOptimizer_viewModel = new ParamOptimizer_ViewModel();
        parentBinding.setSignInModel(paramOptimizer_viewModel);
        AlphaHolder.stackList.add(this);

        underLine();
        lsitener();
    }

    private void lsitener() {
        parentBinding.keepMeAlwaysCheckBoxId.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AlphaHolder.keepAlwaysSignedIn = isChecked;
            }
        });
    }

    private void underLine() {
        termAndCoCheckBoxId = findViewById(R.id.termAndCoCheckBoxId);
        termsTextViewId = findViewById(R.id.termsTextViewId);

        content = new SpannableString(getResources().getString(R.string.terms));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        content.setSpan(new StyleSpan(Typeface.BOLD), 0, content.length(), 0);
        termsTextViewId.setText(content);

        conditionAndPolicyTextView = findViewById(R.id.conditionAndPolicyTextViewId);

        content = new SpannableString(getResources().getString(R.string.conditionandpolicy));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        content.setSpan(new StyleSpan(Typeface.BOLD), 0, content.length(), 0);
        conditionAndPolicyTextView.setText(content);
    }

    public void otpVerification(View view) {
        Intent intent = new Intent(SignInWithPhoneActivity.this, MobileNoVerificationActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void signUpwithmail(View view) {

        this.finish();
        Intent intent = new Intent(SignInWithPhoneActivity.this, SignInWithEmailActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);

    }

    public void backPressed(View view) {
        this.finish();
    }

    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        if (resultData.getTag().equals("SIGNIN_MOBILE")) {
            signIn_serviceModel = (SignIn_ServiceModel) resultData.getRootData().getValue();
            if (signIn_serviceModel.isStatus().equals("true")) {
                if (AlphaHolder.keepAlwaysSignedIn) {
                    AlphaHolder.saveEventStatus("YES","KEEP_ALWAYS_SIGNED_IN",SignInWithPhoneActivity.this);
                } else {
                    AlphaHolder.saveEventStatus("NO","KEEP_ALWAYS_SIGNED_IN",SignInWithPhoneActivity.this);
                }
                new SharedPreferences_Util(SignInWithPhoneActivity.this).saveLoginModel(signIn_serviceModel);
                AlphaHolder.customToast(SignInWithPhoneActivity.this, signIn_serviceModel.getMessage());

                Intent intent = new Intent(SignInWithPhoneActivity.this, MobileNoVerificationActivity.class);
                intent.putExtra("OTP", signIn_serviceModel.getData().getOtp());
                intent.putExtra("MOBILE_NO", paramOptimizer_viewModel.getMobileNo().getValue());
                intent.putExtra("TOKEN", signIn_serviceModel.getData().getToken());
                intent.putExtra("FROM", "SIGN_IN");
                startActivity(intent);
                AlphaHolder.clearStack();
            } else {
                AlphaHolder.customToast(SignInWithPhoneActivity.this, signIn_serviceModel.getMessage());
            }
        }
    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event1")) {
            serviceMsnipulstion();
        }
    }

    private void serviceMsnipulstion() {
        String mobileNoString = paramOptimizer_viewModel.getMobileNo().getValue();
        if (TextUtils.isEmpty(mobileNoString)) {
            AlphaHolder.customToast(SignInWithPhoneActivity.this, getString(R.string.mobilenomustrequire));
        }
        /*if (!parentBinding.termAndCoCheckBoxId.isChecked()) {
            AlphaHolder.customToast(SignInWithPhoneActivity.this, getString(R.string.pleaseaccepttermandcondition));
        }*/
        else {
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("mobile", mobileNoString);

            viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
            viewModel.loadData("SIGNIN_MOBILE", paramMap, true, SignInWithPhoneActivity.this);
            viewModel.getRootData().observe(this, this::onChanged);


        }
    }
}