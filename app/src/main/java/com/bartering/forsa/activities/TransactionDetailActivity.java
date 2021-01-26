package com.bartering.forsa.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.bartering.forsa.AppCompactActivity;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.ActivityTransactionDetailBinding;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.Tran_Detail_ServiceModel;
import com.bartering.forsa.retrofit.service_model.Transaction_ServiceModel;

import javax.inject.Inject;

public class TransactionDetailActivity extends AppCompactActivity implements ClickListener, Observer<Object> {

    ActivityTransactionDetailBinding activityTransactionDetailBinding;

    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;
    Transaction_ServiceModel.DataBean transaction_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTransactionDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_transaction_detail);
        activityTransactionDetailBinding.setClickListerner(this::onClick);
        getTransactionData();
        //getAllTransaction();
    }

    private void getTransactionData() {
        if (getIntent().getExtras() != null) {
            transaction_data = (Transaction_ServiceModel.DataBean) getIntent().getSerializableExtra("transaction_data");
            activityTransactionDetailBinding.setDatail(transaction_data);
        }
    }

    /*private void getAllTransaction() {
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        Map<String, String> param = new HashMap<>();
        param.put("token", SharedPreferences_Util.getToken(TransactionDetailActivity.this));
        param.put("trans_id", transaction_data);
        viewModel.loadData("TRANSACTION_DETAIL", param, true, TransactionDetailActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }*/

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event1")) {
            this.finish();
        }
        if (callerIdentity.equals("event2")) {
            Intent intent = new Intent(TransactionDetailActivity.this, ProductReviewActivity.class);
            intent.putExtra("DETAILS", transaction_data);
            startActivity(intent);
        }
    }

    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        /*if (resultData.getTag().equals("TRANSACTION_DETAIL")) {
            tranDetailServiceModel = (Tran_Detail_ServiceModel) resultData.getRootData().getValue();
            activityTransactionDetailBinding.setDatail(tranDetailServiceModel.getData().get(0));
            if (tranDetailServiceModel.isStatus().equals("true")) {
            } else
                AlphaHolder.customToast(TransactionDetailActivity.this, tranDetailServiceModel.getMessage());
        }*/
    }
}