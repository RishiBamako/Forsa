package com.bartering.forsa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bartering.forsa.ACRA_Slack.application.AcraSlackSample;
import com.bartering.forsa.AppCompactActivity;
import com.bartering.forsa.BR;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.mutableViewModel.ParamOptimizer_ViewModel;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.Comman_ServiceModel;
import com.bartering.forsa.retrofit.service_model.SignIn_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.SharedPreferences_Util;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class SignInWithEmailActivity extends AppCompactActivity implements ClickListener, Observer<Object> {

    TextView textView;
    SpannableString content;

    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;


    @Inject
    ParamOptimizer_ViewModel paramOptimizer_viewModel;
    ViewDataBinding parentBinding;
    SignIn_ServiceModel signIn_serviceModel;
    Comman_ServiceModel comman_serviceModel;


    @Inject
    SharedPreferences_Util sharedPreferences_util;

    public SignInWithEmailActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in_email);
        paramOptimizer_viewModel = new ParamOptimizer_ViewModel();
        sharedPreferences_util = new SharedPreferences_Util(this);
        AlphaHolder.stackList.add(this);


        parentBinding.setVariable(BR.signInViewModel, paramOptimizer_viewModel);
        parentBinding.setVariable(BR.clickListener, this);

        underLine();
    }

    private void underLine() {
        textView = findViewById(R.id.forgotPassword);
        content = new SpannableString(getString(R.string.forget_password));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        textView.setText(content);
    }

    public void otpVerification(View view) {
        Intent intent = new Intent(SignInWithEmailActivity.this, MobileNoVerificationActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event2")) {
            String emailString = paramOptimizer_viewModel.getEmail().getValue();
            String password = paramOptimizer_viewModel.getPasssword().getValue();

            if (TextUtils.isEmpty(emailString)) {
                AlphaHolder.customToast(SignInWithEmailActivity.this, getString(R.string.emailmustrequire));
            } else if (TextUtils.isEmpty(password)) {
                AlphaHolder.customToast(SignInWithEmailActivity.this, getString(R.string.passwordmustrequire));
            } else {
                Map<String, String> paramMap = new HashMap<>();
                paramMap.put("email", emailString);
                paramMap.put("password", password);

                viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
                viewModel.loadData("SIGNIN_EMAIL", paramMap, true, SignInWithEmailActivity.this);
                viewModel.getRootData().observe(this, this::onChanged);

            }
        }
        if (callerIdentity.equals("event1")) {
            if (TextUtils.isEmpty(paramOptimizer_viewModel.getEmail().getValue())) {
                AlphaHolder.customToast(SignInWithEmailActivity.this, getString(R.string.enteremailforgetresetlink));
            } else if (!AlphaHolder.isEmailValid(paramOptimizer_viewModel.getEmail().getValue())) {
                AlphaHolder.customToast(SignInWithEmailActivity.this, getString(R.string.emailmustvalid));
            } else {
                forgotPasssword();
            }
        }
        if (callerIdentity.equals("event4")) {
            SignInWithEmailActivity.this.finish();
        }
        if (callerIdentity.equals("event3")) {
            SignInWithEmailActivity.this.finish();
            Intent intent = new Intent(SignInWithEmailActivity.this, SignInWithPhoneActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
    }

    @Override
    public void onChanged(Object object) {
        ResultData resultData = (ResultData) object;
        if ("SIGNIN_EMAIL".equals(resultData.getTag())) {
            signIn_serviceModel = (SignIn_ServiceModel) resultData.getRootData().getValue();
            if (null != signIn_serviceModel.getData() && signIn_serviceModel.isStatus().equals("true")) {
                sharedPreferences_util.saveLoginModel(signIn_serviceModel);
                Log.e("response", new Gson().toJson(paramOptimizer_viewModel));
                ((AcraSlackSample) this.getApplication()).switcher(SignInWithEmailActivity.this, ChooseLanguageActivity.class, 0);
                AlphaHolder.clearStack();
            }
            AlphaHolder.customToast(SignInWithEmailActivity.this, signIn_serviceModel.getMessage());
        }
        if ("FORGOT_PASSWORD".equals(resultData.getTag())) {
            comman_serviceModel = (Comman_ServiceModel) resultData.getRootData().getValue();
            if (null != comman_serviceModel) {
                if (comman_serviceModel.isStatus().equals("true")) {
                    Intent intent = new Intent(SignInWithEmailActivity.this, ForgotPasswordLinkActivity.class);
                    intent.putExtra("email", paramOptimizer_viewModel.getEmail().getValue());
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                } else {
                    AlphaHolder.customToast(SignInWithEmailActivity.this, comman_serviceModel.getMessage());
                }
            }
        }
    }

    public void forgotPasssword() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("email", paramOptimizer_viewModel.getEmail().getValue());
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("FORGOT_PASSWORD", paramMap, true, SignInWithEmailActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }
}