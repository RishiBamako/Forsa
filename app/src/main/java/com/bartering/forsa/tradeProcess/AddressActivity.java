package com.bartering.forsa.tradeProcess;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bartering.forsa.ACRA_Slack.application.AcraSlackSample;
import com.bartering.forsa.AppCompactActivity;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.ActivityAddressBinding;
import com.bartering.forsa.recyclerViewAdapter.Address_RecyclerViewAdapter;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.AllAddress_ServiceModel;
import com.bartering.forsa.retrofit.service_model.Comman_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.SharedPreferences_Util;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class AddressActivity extends AppCompactActivity implements ClickListener, Observer<Object> {

    ActivityAddressBinding activityAddressBinding;
    Address_RecyclerViewAdapter address_recyclerViewAdapter;

    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;

    AllAddress_ServiceModel allAddress_serviceModel;
    Comman_ServiceModel comman_serviceModel;
    int selectedAddressPosition = 0;
    AllAddress_ServiceModel.DataBean slectedAddressDataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAddressBinding = DataBindingUtil.setContentView(this, R.layout.activity_address);
        activityAddressBinding.setClickListener(this::onClick);
        activityAddressBinding.setIsNoRecord(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAddressData();

    }

    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        if (resultData.getTag().equals("ADDRESS_LIST")) {
            allAddress_serviceModel = (AllAddress_ServiceModel) resultData.getRootData().getValue();
            if (allAddress_serviceModel.getData().size() > 0) {
                activityAddressBinding.setIsNoRecord(false);
                address_recyclerViewAdapter = new Address_RecyclerViewAdapter(AddressActivity.this, allAddress_serviceModel.getData(), this);
                activityAddressBinding.setAddressRV(address_recyclerViewAdapter);
            } else {
                activityAddressBinding.setIsNoRecord(true);
            }

        }
        if (resultData.getTag().equals("SELECT_ADDRESS")) {
            comman_serviceModel = (Comman_ServiceModel) resultData.getRootData().getValue();
            if (comman_serviceModel.isStatus().equals("true")) {
                for (int a = 0; a < allAddress_serviceModel.getData().size(); a++) {
                    allAddress_serviceModel.getData().get(a).setSelected(false);
                    if (a == allAddress_serviceModel.getData().size() - 1) {
                        allAddress_serviceModel.getData().get(selectedAddressPosition).setSelected(true);
                        address_recyclerViewAdapter.notifyDataSetChanged();

                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("SELECTED_ADDRESS", slectedAddressDataBean);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }
                }
            } else {
                AlphaHolder.customToast(AddressActivity.this, comman_serviceModel.getMessage());
            }
        }
        if (resultData.getTag().equals("DELETE_ADDRESS")) {
            comman_serviceModel = (Comman_ServiceModel) resultData.getRootData().getValue();
            if (comman_serviceModel.isStatus().equals("true")) {
                AlphaHolder.customToast(AddressActivity.this, comman_serviceModel.getMessage());
                if (allAddress_serviceModel.getData() != null && allAddress_serviceModel.getData().size() > 0) {
                    allAddress_serviceModel.getData().remove(selectedAddressPosition);
                    address_recyclerViewAdapter.notifyItemRemoved(selectedAddressPosition);
                    address_recyclerViewAdapter.notifyItemRangeChanged(selectedAddressPosition, allAddress_serviceModel.getData().size());
                    if (allAddress_serviceModel.getData().size() == 0) {
                        activityAddressBinding.setIsNoRecord(true);
                    }
                }
            } else {
                AlphaHolder.customToast(AddressActivity.this, comman_serviceModel.getMessage());
            }

        }
    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event3")) { ////Add address button click
            ((AcraSlackSample) getApplication()).switcher(AddressActivity.this, AddAddressActivity.class, 0);
        }
        if (callerIdentity.equals("event1")) {//// back button click
            AddressActivity.this.finish();
        }
        if (callerIdentity.equals("event2")) {////main address view click
            selectedAddressPosition = position;
            slectedAddressDataBean = (AllAddress_ServiceModel.DataBean) object;
            if (slectedAddressDataBean != null) {
                selectAddress(slectedAddressDataBean);
            }
        }
        if (callerIdentity.equals("event4")) { ////EDIT ADDRESS view click
            AllAddress_ServiceModel.DataBean dataBean = (AllAddress_ServiceModel.DataBean) object;
            if (dataBean != null) {
                Intent intent = new Intent(AddressActivity.this, EditAddressActivity.class);
                intent.putExtra("ADDRESS_INFO", dataBean);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        }
        if (callerIdentity.equals("event5")) { ////DELETE ADDRESS
            selectedAddressPosition = position;
            AllAddress_ServiceModel.DataBean dataBean = (AllAddress_ServiceModel.DataBean) object;
            if (dataBean != null) {
                deleteAddress(dataBean.getId());
            }
        }

    }

    public void getAddressData() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", SharedPreferences_Util.getToken(AddressActivity.this));
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("ADDRESS_LIST", paramMap, true, this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    public void deleteAddress(String address_Id) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", SharedPreferences_Util.getToken(AddressActivity.this));
        paramMap.put("address_id", address_Id);
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("DELETE_ADDRESS", paramMap, true, this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    public void selectAddress(AllAddress_ServiceModel.DataBean dataBean) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("address_id", dataBean.getId());
        paramMap.put("token", SharedPreferences_Util.getToken(AddressActivity.this));
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("SELECT_ADDRESS", paramMap, true, this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

}