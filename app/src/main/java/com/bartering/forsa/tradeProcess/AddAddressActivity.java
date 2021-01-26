package com.bartering.forsa.tradeProcess;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bartering.forsa.AppCompactActivity;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.ActivityAddAddressBinding;
import com.bartering.forsa.mutableViewModel.ParamOptimizer_ViewModel;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.Comman_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.SharedPreferences_Util;

import java.util.HashMap;

import javax.inject.Inject;

public class AddAddressActivity extends AppCompactActivity implements ClickListener, Observer<Object> {

    ActivityAddAddressBinding activityAddAddressBinding;

    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;
    Comman_ServiceModel comman_serviceModel;


    @Inject
    ParamOptimizer_ViewModel paramOptimizer_viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        paramOptimizer_viewModel = new ParamOptimizer_ViewModel();
        activityAddAddressBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_address);
        activityAddAddressBinding.setData(paramOptimizer_viewModel);
        activityAddAddressBinding.setClickListener(this::onClick);
    }

    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        if (resultData.getTag().equals("ADD_ADDRESS")) {
            Comman_ServiceModel comman_serviceModel = (Comman_ServiceModel) resultData.getRootData().getValue();
            if (comman_serviceModel.isStatus().equals("true")) {
                AddAddressActivity.this.finish();
                AlphaHolder.customToast(AddAddressActivity.this, comman_serviceModel.getMessage());
            } else {
                AlphaHolder.customToast(AddAddressActivity.this, comman_serviceModel.getMessage());
            }
        }
    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event1")) {
            AddAddressActivity.this.finish();
        }
        if (callerIdentity.equals("event2")) {
            addAddress();
        }
    }

    private void addAddress() {
        if (TextUtils.isEmpty(paramOptimizer_viewModel.getHouseNoFlatBlockNo().getValue())) {
            AlphaHolder.customToast(AddAddressActivity.this, getString(R.string.housefaltcannotbenull));
        } else if (!AlphaHolder.isValidDigitString(paramOptimizer_viewModel.getHouseNoFlatBlockNo().getValue())) {
            AlphaHolder.customToast(AddAddressActivity.this, getString(R.string.Houseandflatshouldcontain_digit));
        }else if (TextUtils.isEmpty(paramOptimizer_viewModel.getAddress().getValue())) {
            AlphaHolder.customToast(AddAddressActivity.this, getString(R.string.addresscannotbenull));
        } else if (TextUtils.isEmpty(paramOptimizer_viewModel.getLandmark().getValue())) {
            AlphaHolder.customToast(AddAddressActivity.this, getString(R.string.landmarkcannotbenull));
        } else if (TextUtils.isEmpty(paramOptimizer_viewModel.getCity().getValue())) {
            AlphaHolder.customToast(AddAddressActivity.this, getString(R.string.citycannotbenull));
        } else if (TextUtils.isEmpty(paramOptimizer_viewModel.getStateProvinceReligion().getValue())) {
            AlphaHolder.customToast(AddAddressActivity.this, getString(R.string.stateprovincecannotbenull));
        } else if (TextUtils.isEmpty(paramOptimizer_viewModel.getZipCodePostalCode().getValue())) {
            AlphaHolder.customToast(AddAddressActivity.this, getString(R.string.ziporpostalcannotbenull));
        } else if (TextUtils.isEmpty(paramOptimizer_viewModel.getCountry().getValue())) {
            AlphaHolder.customToast(AddAddressActivity.this, getString(R.string.countrycannotbenull));
        } else {

            HashMap<String, String> paramMap = new HashMap<>();
            paramMap.put("house_no", paramOptimizer_viewModel.getHouseNoFlatBlockNo().getValue());
            paramMap.put("address", paramOptimizer_viewModel.getAddress().getValue());
            paramMap.put("landmark", paramOptimizer_viewModel.getLandmark().getValue());
            paramMap.put("city", paramOptimizer_viewModel.getCity().getValue());
            paramMap.put("state", paramOptimizer_viewModel.getStateProvinceReligion().getValue());
            paramMap.put("zip_code", paramOptimizer_viewModel.getZipCodePostalCode().getValue());
            paramMap.put("country", paramOptimizer_viewModel.getCountry().getValue());
            paramMap.put("token", SharedPreferences_Util.getToken(AddAddressActivity.this));

            viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
            viewModel.loadData("ADD_ADDRESS", paramMap, true, AddAddressActivity.this);
            viewModel.getRootData().observe(this, this::onChanged);
        }


    }
}