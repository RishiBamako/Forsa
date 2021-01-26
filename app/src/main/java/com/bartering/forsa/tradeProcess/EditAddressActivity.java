package com.bartering.forsa.tradeProcess;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bartering.forsa.AppCompactActivity;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.ActivityEditAddressBinding;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.AllAddress_ServiceModel;
import com.bartering.forsa.retrofit.service_model.EditAddress_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.SharedPreferences_Util;

import java.util.HashMap;

import javax.inject.Inject;

public class EditAddressActivity extends AppCompactActivity implements ClickListener, Observer<Object> {

    ActivityEditAddressBinding activityEditAddressBinding;

    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;
    AllAddress_ServiceModel.DataBean addressData = null;
    EditAddress_ServiceModel editAddress_serviceModel = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEditAddressBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_address);
        activityEditAddressBinding.setClickListener(this::onClick);
        getSpecificAddressDetail();
    }

    private void getSpecificAddressDetail() {
        if (getIntent().getExtras() != null) {
            addressData = (AllAddress_ServiceModel.DataBean) getIntent().getSerializableExtra("ADDRESS_INFO");
            if (addressData != null)
                activityEditAddressBinding.setData(addressData);
            else
                AlphaHolder.customToast(EditAddressActivity.this, getResources().getString(R.string.somethingwentwrong));
        }
    }

    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        if (resultData.getTag().equals("EDIT_ADDRESS")) {
            editAddress_serviceModel = (EditAddress_ServiceModel) resultData.getRootData().getValue();
            if (editAddress_serviceModel.isStatus().equals("true")) {
                EditAddressActivity.this.finish();
            } else {
                AlphaHolder.customToast(EditAddressActivity.this, editAddress_serviceModel.getMessage());
            }
        }
    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event1")) {
            EditAddressActivity.this.finish();

        }
        if (callerIdentity.equals("event2")) {
            addAddress();
        }
    }
    private void addAddress() {
        if (TextUtils.isEmpty(addressData.getHouse_no())) {
            AlphaHolder.customToast(EditAddressActivity.this, getString(R.string.housefaltcannotbenull));
        } else if (!AlphaHolder.isValidDigitString(addressData.getHouse_no())) {
            AlphaHolder.customToast(EditAddressActivity.this, getString(R.string.Houseandflatshouldcontain_digit));
        } else if (TextUtils.isEmpty(addressData.getAddress())) {
            AlphaHolder.customToast(EditAddressActivity.this, getString(R.string.addresscannotbenull));
        } else if (TextUtils.isEmpty(addressData.getLandmark())) {
            AlphaHolder.customToast(EditAddressActivity.this, getString(R.string.landmarkcannotbenull));
        } else if (TextUtils.isEmpty(addressData.getCity())) {
            AlphaHolder.customToast(EditAddressActivity.this, getString(R.string.citycannotbenull));
        } else if (TextUtils.isEmpty(addressData.getState())) {
            AlphaHolder.customToast(EditAddressActivity.this, getString(R.string.stateprovincecannotbenull));
        } else if (TextUtils.isEmpty(addressData.getPin_code())) {
            AlphaHolder.customToast(EditAddressActivity.this, getString(R.string.ziporpostalcannotbenull));
        } else if (TextUtils.isEmpty(addressData.getCountry())) {
            AlphaHolder.customToast(EditAddressActivity.this, getString(R.string.countrycannotbenull));
        } else {
            HashMap<String, String> paramMap = new HashMap<>();
            paramMap.put("address_id", addressData.getId());
            paramMap.put("house_no", addressData.getHouse_no());
            paramMap.put("address", addressData.getAddress());
            paramMap.put("landmark", addressData.getLandmark());
            paramMap.put("city", addressData.getCity());
            paramMap.put("state", addressData.getState());
            paramMap.put("zip_code", addressData.getPin_code());
            paramMap.put("country", addressData.getCountry());
            paramMap.put("token", SharedPreferences_Util.getToken(EditAddressActivity.this));

            viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
            viewModel.loadData("EDIT_ADDRESS", paramMap, true, EditAddressActivity.this);
            viewModel.getRootData().observe(this, this::onChanged);
        }

    }
}