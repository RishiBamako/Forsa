package com.bartering.forsa.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bartering.forsa.AppCompactActivity;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.ActivitySignUpEmailBinding;
import com.bartering.forsa.databinding.TermandconditionBinding;
import com.bartering.forsa.mutableViewModel.ParamOptimizer_ViewModel;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.Privacy_Policy_Terms_DataModel;
import com.bartering.forsa.retrofit.service_model.SignUpEmail_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.SharedPreferences_Util;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class SignUpWithEmailActivity extends AppCompactActivity implements ClickListener, Observer<Object> {

    SpannableString content;
    TextView termAndCoCheckBoxId, termsTextViewId, conditionAndPolicyTextView;
    ParamOptimizer_ViewModel paramOptimizer_viewModel;
    ActivitySignUpEmailBinding parentBinding;

    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;
    SignUpEmail_ServiceModel signUpEmail_serviceModel;
    Privacy_Policy_Terms_DataModel privacy_policy_terms_dataModel;

    Dialog bottomSliderDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        paramOptimizer_viewModel = new ParamOptimizer_ViewModel();
        parentBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up_email);
        parentBinding.setParamOptimizer(paramOptimizer_viewModel);
        parentBinding.setClickListener(this::onClick);

        underLine();
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
        Intent intent = new Intent(SignUpWithEmailActivity.this, MobileNoVerificationActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }


    public void backPressed(View view) {
        this.finish();
    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event2")) {
            if (TextUtils.isEmpty(paramOptimizer_viewModel.getEmail().getValue())) {
                AlphaHolder.customToast(SignUpWithEmailActivity.this, getString(R.string.emailcantbeempty));
            } else if (!AlphaHolder.isEmailValid(paramOptimizer_viewModel.getEmail().getValue())) {
                AlphaHolder.customToast(SignUpWithEmailActivity.this, getString(R.string.emailmustvalid));
            } else if (TextUtils.isEmpty(paramOptimizer_viewModel.getPasssword().getValue())) {
                AlphaHolder.customToast(SignUpWithEmailActivity.this, getString(R.string.passwordcantbeempty));
            } else if (!paramOptimizer_viewModel.getIsTermAndConditionAccepted().getValue()) {
                AlphaHolder.customToast(SignUpWithEmailActivity.this, getString(R.string.pleaseaccepttermandcondition));
            } else
                getAllAdverstisement();

        }
        if (callerIdentity.equals("event1")) {
            SignUpWithEmailActivity.this.finish();
        }
        if (callerIdentity.equals("event3")) {
            this.finish();
            Intent intent = new Intent(SignUpWithEmailActivity.this, SignUpWithPhoneActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
        if (callerIdentity.equals("event4")) {
            getTermAndCondition();
        }
        if (callerIdentity.equals("event5")) {
            getPrivacyPolicy();
        }
        if (callerIdentity.equals("event6")) {
            bottomSliderDialog.dismiss();
        }
        if (callerIdentity.equals("event7")) {
            bottomSliderDialog.dismiss();

        }
    }

    public void getAllAdverstisement() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", SharedPreferences_Util.getToken(SignUpWithEmailActivity.this));
        paramMap.put("email", paramOptimizer_viewModel.getEmail().getValue());
        paramMap.put("password", paramOptimizer_viewModel.getPasssword().getValue());
        paramMap.put("register_type", "2");

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("SIGN_UP_WITH_EMAIL", paramMap, true, SignUpWithEmailActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    public void getPrivacyPolicy() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", "");

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("PRIVACY_POLICY", paramMap, true, SignUpWithEmailActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    public void getTermAndCondition() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", "");

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("TERM_CONDITION", paramMap, true, SignUpWithEmailActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        if (resultData.getTag().equals("SIGN_UP_WITH_EMAIL")) {
            signUpEmail_serviceModel = (SignUpEmail_ServiceModel) resultData.getRootData().getValue();
            if (signUpEmail_serviceModel.isStatus().equals("true")) {
                Intent intent = new Intent(SignUpWithEmailActivity.this, ProfileActivity.class);
                intent.putExtra("IS_FROM", "REGISTER");
                intent.putExtra("EMAIL_ADDRESS", paramOptimizer_viewModel.getEmail().getValue());
                intent.putExtra("USER_NAME", "");
                intent.putExtra("TOKEN", signUpEmail_serviceModel.getToken());
                startActivity(intent);
                SignUpWithEmailActivity.this.finish();
            } else {
                AlphaHolder.customToast(SignUpWithEmailActivity.this, signUpEmail_serviceModel.getMessage());
            }
        }
        if (resultData.getTag().equals("PRIVACY_POLICY")) {
            privacy_policy_terms_dataModel = (Privacy_Policy_Terms_DataModel) resultData.getRootData().getValue();
            privacy_Policy(privacy_policy_terms_dataModel);

        }
        if (resultData.getTag().equals("TERM_CONDITION")) {
            privacy_policy_terms_dataModel = (Privacy_Policy_Terms_DataModel) resultData.getRootData().getValue();
            privacy_Policy(privacy_policy_terms_dataModel);

        }
    }

    private void privacy_Policy(Privacy_Policy_Terms_DataModel privacy_policy_terms_dataModel) {
        bottomSliderDialog = new Dialog(SignUpWithEmailActivity.this, R.style.DialogAnimation);
        bottomSliderDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        bottomSliderDialog.setCancelable(false);

        TermandconditionBinding termandconditionBinding = DataBindingUtil.inflate(LayoutInflater.from(SignUpWithEmailActivity.this), R.layout.termandcondition, null, false);
        bottomSliderDialog.setContentView(termandconditionBinding.getRoot());
        termandconditionBinding.setClickListener(this::onClick);
        termandconditionBinding.setData(privacy_policy_terms_dataModel.getData().get(0));

        bottomSliderDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        lp.copyFrom(bottomSliderDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
        lp.windowAnimations = R.style.DialogAnimation;
        bottomSliderDialog.getWindow().setAttributes(lp);
        bottomSliderDialog.show();
    }
}