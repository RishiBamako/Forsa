package com.bartering.forsa.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bartering.forsa.AppCompactActivity;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.activities.bartering_detail.BarteringTransactionDetailActivity;
import com.bartering.forsa.databinding.ActivityMyTransactionBinding;
import com.bartering.forsa.recyclerViewAdapter.MyTransaction_RecyclerViewAdapter;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.Transaction_ServiceModel;
import com.bartering.forsa.utils.SharedPreferences_Util;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class MyTransactionActivity extends AppCompactActivity implements ClickListener, Observer<Object> {

    ActivityMyTransactionBinding activityMyTransactionBinding;
    MyTransaction_RecyclerViewAdapter myTransaction_recyclerViewAdapter;

    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;
    Transaction_ServiceModel transaction_serviceModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMyTransactionBinding = DataBindingUtil.setContentView(this, R.layout.activity_my_transaction);
        activityMyTransactionBinding.setClickListener(this::onClick);
        activityMyTransactionBinding.setIsNoRecord(false);
        getAllTransaction();
    }

    private void getAllTransaction() {
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        Map<String, String> param = new HashMap<>();
        param.put("token", SharedPreferences_Util.getToken(MyTransactionActivity.this));
        viewModel.loadData("MY_TRANSACTION", param, true, MyTransactionActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event1")) {
            Transaction_ServiceModel.DataBean dataBean = (Transaction_ServiceModel.DataBean) object;
            if (dataBean.getTrans_type().equals("trading")) {
                Intent intent = new Intent(MyTransactionActivity.this, TransactionDetailActivity.class);
                intent.putExtra("transaction_data", dataBean);
                startActivity(intent);
                overridePendingTransition(0, 0);
            } else {
                Intent intent = new Intent(MyTransactionActivity.this, BarteringTransactionDetailActivity.class);
                intent.putExtra("transaction_data", dataBean);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }

        }
        if (callerIdentity.equals("event2")) {
            MyTransactionActivity.this.finish();
        }
    }

    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        if (resultData.getTag().equals("MY_TRANSACTION")) {
            transaction_serviceModel = (Transaction_ServiceModel) resultData.getRootData().getValue();
            if (transaction_serviceModel != null && transaction_serviceModel.getData().size()>0) {
                activityMyTransactionBinding.setIsNoRecord(false);
                myTransaction_recyclerViewAdapter = new MyTransaction_RecyclerViewAdapter(MyTransactionActivity.this, transaction_serviceModel.getData(), this::onClick);
                activityMyTransactionBinding.transactionRecyclerViewId.setAdapter(myTransaction_recyclerViewAdapter);
            }
            else{
                activityMyTransactionBinding.setIsNoRecord(true);
            }
        }
    }
}