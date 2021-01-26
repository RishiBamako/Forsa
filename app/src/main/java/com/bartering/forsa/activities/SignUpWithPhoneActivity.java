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
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bartering.forsa.AppCompactActivity;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.ActivitySignUpMobilenoBinding;
import com.bartering.forsa.databinding.TermandconditionBinding;
import com.bartering.forsa.mutableViewModel.ParamOptimizer_ViewModel;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.Comman_ServiceModel;
import com.bartering.forsa.retrofit.service_model.Privacy_Policy_Terms_DataModel;
import com.bartering.forsa.retrofit.service_model.SignUpMobile_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.SharedPreferences_Util;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class SignUpWithPhoneActivity extends AppCompactActivity implements ClickListener, Observer<Object> {

    SpannableString content;
    TextView termAndCoCheckBoxId, termsTextViewId, conditionAndPolicyTextView;

    @Inject
    ParamOptimizer_ViewModel paramOptimizer_viewModel;
    ActivitySignUpMobilenoBinding parentBinding;

    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;

    Comman_ServiceModel comman_serviceModel;
    SignUpMobile_ServiceModel signUpMobile_serviceModel;

    Privacy_Policy_Terms_DataModel privacy_policy_terms_dataModel;
    Dialog bottomSliderDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up_mobileno);
        parentBinding.setClickListener(this::onClick);
        paramOptimizer_viewModel = new ParamOptimizer_ViewModel();
        parentBinding.setSinUpModel(paramOptimizer_viewModel);
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

    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        if (resultData.getTag().equals("SIGN_UP_WITH_MOBILE")) {
            signUpMobile_serviceModel = (SignUpMobile_ServiceModel) resultData.getRootData().getValue();
            if (signUpMobile_serviceModel.isStatus().equals("true")) {

                Intent intent = new Intent(SignUpWithPhoneActivity.this, MobileNoVerificationActivity.class);
                intent.putExtra("OTP", signUpMobile_serviceModel.getData().getOtp());
                intent.putExtra("MOBILE_NO", paramOptimizer_viewModel.getMobileNo().getValue());
                intent.putExtra("TOKEN", signUpMobile_serviceModel.getToken());
                intent.putExtra("FROM", "SIGN_UP");
                startActivity(intent);

            } else {
                AlphaHolder.customToast(SignUpWithPhoneActivity.this, signUpMobile_serviceModel.getMessage());
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

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event1")) { ///back
            SignUpWithPhoneActivity.this.finish();
        }
        if (callerIdentity.equals("event2")) { /// next
            serviceManipulation();
        }
        if (callerIdentity.equals("event3")) { ///signUpWith Email
            this.finish();
            Intent intent = new Intent(SignUpWithPhoneActivity.this, SignUpWithEmailActivity.class);
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

    private void serviceManipulation() {
        String mobileString = paramOptimizer_viewModel.getMobileNo().getValue();
        if (TextUtils.isEmpty(mobileString)) {
            AlphaHolder.customToast(SignUpWithPhoneActivity.this, getString(R.string.mobilenomustrequire));
        } else if (!parentBinding.termAndCoCheckBoxId.isChecked()) {
            AlphaHolder.customToast(SignUpWithPhoneActivity.this, getString(R.string.pleaseaccepttermandcondition));
        } else {
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("token", SharedPreferences_Util.getToken(SignUpWithPhoneActivity.this));
            paramMap.put("mobile", paramOptimizer_viewModel.getMobileNo().getValue());
            paramMap.put("register_type", "1");

            viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
            viewModel.loadData("SIGN_UP_WITH_MOBILE", paramMap, true, SignUpWithPhoneActivity.this);
            viewModel.getRootData().observe(this, this::onChanged);

        }
    }
    public void getPrivacyPolicy() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", "");

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("PRIVACY_POLICY", paramMap, true, SignUpWithPhoneActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    public void getTermAndCondition() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", "");

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("TERM_CONDITION", paramMap, true, SignUpWithPhoneActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }
    private void privacy_Policy(Privacy_Policy_Terms_DataModel privacy_policy_terms_dataModel) {
        bottomSliderDialog = new Dialog(SignUpWithPhoneActivity.this, R.style.DialogAnimation);
        bottomSliderDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        bottomSliderDialog.setCancelable(false);

        TermandconditionBinding termandconditionBinding = DataBindingUtil.inflate(LayoutInflater.from(SignUpWithPhoneActivity.this), R.layout.termandcondition, null, false);
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