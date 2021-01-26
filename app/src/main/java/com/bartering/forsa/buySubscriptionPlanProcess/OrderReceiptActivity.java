package com.bartering.forsa.buySubscriptionPlanProcess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.activities.MainActivity;
import com.bartering.forsa.activities.bartering_detail.ProductOverViewActivity;
import com.bartering.forsa.activities.boots_Section.MyAdsActivity;
import com.bartering.forsa.databinding.ActivityOrderReceiptBinding;
import com.bartering.forsa.utils.AlphaHolder;

public class OrderReceiptActivity extends AppCompatActivity implements ClickListener {

    ActivityOrderReceiptBinding activityOrderReceiptBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOrderReceiptBinding = DataBindingUtil.setContentView(this, R.layout.activity_order_receipt);
        activityOrderReceiptBinding.setClickListner(this::onClick);
        AlphaHolder.stackList.add(this);
    }
    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event1")) {
            try {

                Intent intent = new Intent(OrderReceiptActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(0, 0);
                OrderReceiptActivity.this.finish();

            } catch (Exception EE) {

            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(OrderReceiptActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(0, 0);
        OrderReceiptActivity.this.finish();

    }
}