package com.bartering.forsa.activities.boots_Section;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
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
import com.bartering.forsa.buySubscriptionPlanProcess.OrderReceiptActivity;
import com.bartering.forsa.databinding.ActivityOrderDetailBoostBinding;
import com.bartering.forsa.databinding.TermandconditionBinding;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.BoostPlans_ServiceModel;
import com.bartering.forsa.retrofit.service_model.Comman_ServiceModel;
import com.bartering.forsa.retrofit.service_model.Privacy_Policy_Terms_DataModel;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.SharedPreferences_Util;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class Order_detailActivity extends AppCompactActivity implements ClickListener, Observer<Object> {

    ActivityOrderDetailBoostBinding parentBinding;
    BoostPlans_ServiceModel.DataBean dataBean;

    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;
    boolean isAccepted;
    Privacy_Policy_Terms_DataModel privacy_policy_terms_dataModel;
    Dialog bottomSliderDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentBinding = DataBindingUtil.setContentView(this, R.layout.activity_order_detail_boost);
        parentBinding.setClickListener(this);
        AlphaHolder.stackList.add(this);
        getDescriptionData();
        listener();

    }
    private void listener() {
        parentBinding.termAndCoCheckBoxId.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isAccepted = isChecked;
            }
        });
    }
    private void getDescriptionData() {
        if (getIntent().getExtras() != null) {
            dataBean = (BoostPlans_ServiceModel.DataBean) getIntent().getExtras().getSerializable("DATA");
            parentBinding.setData(dataBean);
        }
    }

    public void next(View view) {

    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event1")) {
            this.finish();
        }
        if (callerIdentity.equals("event2")) {
            if (!isAccepted) {
                AlphaHolder.customToast(Order_detailActivity.this, getString(R.string.pleaseaccepttermandcondition));
            } else {
                makeOrderPackage();
            }

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

    private void makeOrderPackage() {
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        Map<String, String> param = new HashMap<>();
        param.put("token", SharedPreferences_Util.getToken(Order_detailActivity.this));
        param.put("pkg_id", dataBean.getId());
        viewModel.loadData("ORDER_PACKAGE", param, true, Order_detailActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        if (resultData.getTag().equals("ORDER_PACKAGE")) {
            Comman_ServiceModel comman_serviceModel = (Comman_ServiceModel) resultData.getRootData().getValue();
            if (comman_serviceModel.isStatus().equals("true")) {
                ((AcraSlackSample) getApplication()).switcher(Order_detailActivity.this, OrderReceiptActivity.class, 0);
            } else {
                AlphaHolder.customToast(Order_detailActivity.this, comman_serviceModel.getMessage());
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
    public void getPrivacyPolicy() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", "");

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("PRIVACY_POLICY", paramMap, true, Order_detailActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    public void getTermAndCondition() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", "");

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("TERM_CONDITION", paramMap, true, Order_detailActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }
    private void privacy_Policy(Privacy_Policy_Terms_DataModel privacy_policy_terms_dataModel) {
        bottomSliderDialog = new Dialog(Order_detailActivity.this, R.style.DialogAnimation);
        bottomSliderDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        bottomSliderDialog.setCancelable(false);

        TermandconditionBinding termandconditionBinding = DataBindingUtil.inflate(LayoutInflater.from(Order_detailActivity.this), R.layout.termandcondition, null, false);
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