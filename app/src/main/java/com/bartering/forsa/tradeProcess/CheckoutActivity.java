package com.bartering.forsa.tradeProcess;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.bartering.forsa.AppCompactActivity;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.activities.MainActivity;
import com.bartering.forsa.databinding.ActivityCheckoutBinding;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.AllAddress_ServiceModel;
import com.bartering.forsa.retrofit.service_model.Comman_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;

import javax.inject.Inject;

public class CheckoutActivity extends AppCompactActivity implements ClickListener, Observer<Object> {

    ActivityCheckoutBinding activityCheckoutBinding;

    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;
    Comman_ServiceModel comman_serviceModel;
    int SELECT_ADDRESS_IDENTITY = 998;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCheckoutBinding = DataBindingUtil.setContentView(this, R.layout.activity_checkout);
        activityCheckoutBinding.setClickListener(this::onClick);
    }

    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        if (resultData.getTag().equals("CHECKOUT_ACTIVITY")) {

        }
    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event1")) { ///back click
            CheckoutActivity.this.finish();
            finishStack();
        }
        if (callerIdentity.equals("event2")) { ///change address
            Intent authActivity = new Intent(CheckoutActivity.this, AddressActivity.class);
            startActivityForResult(authActivity, SELECT_ADDRESS_IDENTITY);

        }
        if (callerIdentity.equals("event3")) { ///PAYPAL
            if (TextUtils.isEmpty(activityCheckoutBinding.selectedAddressTextViewId.getText().toString())) {
                AlphaHolder.customToast(CheckoutActivity.this, getResources().getString(R.string.pleaseselect_shippingaddress));
            } else {
                AlphaHolder.customToast(CheckoutActivity.this, getResources().getString(R.string.orderplacedsuccessfully));
                finishStack();
            }
        }
        if (callerIdentity.equals("event2")) { ///STRIPE
            if (TextUtils.isEmpty(activityCheckoutBinding.selectedAddressTextViewId.getText().toString())) {
                AlphaHolder.customToast(CheckoutActivity.this, getResources().getString(R.string.pleaseselect_shippingaddress));
            } else {
                AlphaHolder.customToast(CheckoutActivity.this, getResources().getString(R.string.orderplacedsuccessfully));
                finishStack();
            }
        }
    }

    private void finishStack() {
        CheckoutActivity.this.finish();
        Intent intent = new Intent(CheckoutActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_ADDRESS_IDENTITY && resultCode == RESULT_OK) {
            AllAddress_ServiceModel.DataBean addressData = (AllAddress_ServiceModel.DataBean) data.getSerializableExtra("SELECTED_ADDRESS");
            if (addressData != null) {
                activityCheckoutBinding.setAddressData(addressData);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishStack();
    }
}