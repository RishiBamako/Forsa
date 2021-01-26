package com.bartering.forsa.activities.bartering_detail;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bartering.forsa.AppCompactActivity;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.ActivityBarteringTransactionDetailBinding;
import com.bartering.forsa.retrofit.service_model.Transaction_ServiceModel;

public class BarteringTransactionDetailActivity extends AppCompactActivity implements ClickListener {

    ActivityBarteringTransactionDetailBinding activityBarteringTransactionDetailBinding;
    Transaction_ServiceModel.DataBean transaction_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBarteringTransactionDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_bartering_transaction_detail);
        activityBarteringTransactionDetailBinding.setClickListener(this::onClick);
        getTransactionData();
    }

    private void getTransactionData() {
        if (getIntent().getExtras() != null) {
            transaction_data = (Transaction_ServiceModel.DataBean) getIntent().getSerializableExtra("transaction_data");
            activityBarteringTransactionDetailBinding.setDatail(transaction_data);
        }
    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event1")) {
            BarteringTransactionDetailActivity.this.finish();
        }
    }
}