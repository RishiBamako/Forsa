package com.bartering.forsa.buySubscriptionPlanProcess;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bartering.forsa.ACRA_Slack.application.AcraSlackSample;
import com.bartering.forsa.AppCompactActivity;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.ActivityOrderDetailBinding;
import com.bartering.forsa.databinding.TermandconditionBinding;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.Comman_ServiceModel;
import com.bartering.forsa.retrofit.service_model.Privacy_Policy_Terms_DataModel;
import com.bartering.forsa.retrofit.service_model.SubscriptionPackages_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.SharedPreferences_Util;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class OrderDetailActivity extends AppCompactActivity implements ClickListener, Observer<Object> {

    ActivityOrderDetailBinding activityOrderDetailBinding;
    SpannableString content;

    SubscriptionPackages_ServiceModel.DataBean dataBean = null;

    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;
    boolean isChecked = false;
    Privacy_Policy_Terms_DataModel privacy_policy_terms_dataModel;

    Dialog bottomSliderDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOrderDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_order_detail);
        activityOrderDetailBinding.setClickListener(this::onClick);
        AlphaHolder.stackList.add(this);

        underLine();
        getData();
        listener();
    }

    private void listener() {
        activityOrderDetailBinding.termAndCoCheckBoxId.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                OrderDetailActivity.this.isChecked = isChecked;
            }
        });
    }

    private void getData() {
        if (getIntent().getExtras() != null) {
            dataBean = (SubscriptionPackages_ServiceModel.DataBean) getIntent().getExtras().getSerializable("PACKAGE_INFO");
            activityOrderDetailBinding.setData(dataBean);
        }
    }

    private void underLine() {

        content = new SpannableString(getResources().getString(R.string.terms));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        content.setSpan(new StyleSpan(Typeface.BOLD), 0, content.length(), 0);
        activityOrderDetailBinding.termsTextViewId.setText(content);


        content = new SpannableString(getResources().getString(R.string.conditionandpolicy));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        content.setSpan(new StyleSpan(Typeface.BOLD), 0, content.length(), 0);
        activityOrderDetailBinding.conditionAndPolicyTextViewId.setText(content);
    }

    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        if (resultData.getTag().equals("PLACE_ORDER")) {
            Comman_ServiceModel comman_serviceModel = (Comman_ServiceModel) resultData.getRootData().getValue();
            if (comman_serviceModel.isStatus().equals("true")) {
                ((AcraSlackSample) getApplication()).switcher(OrderDetailActivity.this, OrderReceiptActivity.class, 0);
            } else {
                AlphaHolder.customToast(OrderDetailActivity.this, comman_serviceModel.getMessage());
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
        if (callerIdentity.equals("event2")) {
            if (!isChecked) {
                AlphaHolder.customToast(OrderDetailActivity.this, getString(R.string.pleaseaccepttermandcondition));
            } else {
                getSubscriptionPackages();
            }
        }
        if (callerIdentity.equals("event3")) {

        }
        if (callerIdentity.equals("event1")) {
            OrderDetailActivity.this.finish();
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

    private void getSubscriptionPackages() {
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        Map<String, String> param = new HashMap<>();
        param.put("token", SharedPreferences_Util.getToken(OrderDetailActivity.this));
        param.put("subscription_id", dataBean.getId());
        viewModel.loadData("PLACE_ORDER", param, true, OrderDetailActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    public void getPrivacyPolicy() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", "");

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("PRIVACY_POLICY", paramMap, true, OrderDetailActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    public void getTermAndCondition() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", "");

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("TERM_CONDITION", paramMap, true, OrderDetailActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    private void privacy_Policy(Privacy_Policy_Terms_DataModel privacy_policy_terms_dataModel) {
        bottomSliderDialog = new Dialog(OrderDetailActivity.this, R.style.DialogAnimation);
        bottomSliderDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        bottomSliderDialog.setCancelable(false);

        TermandconditionBinding termandconditionBinding = DataBindingUtil.inflate(LayoutInflater.from(OrderDetailActivity.this), R.layout.termandcondition, null, false);
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