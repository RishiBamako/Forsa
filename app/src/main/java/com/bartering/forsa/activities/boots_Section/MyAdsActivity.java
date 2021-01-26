package com.bartering.forsa.activities.boots_Section;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;

import com.bartering.forsa.ACRA_Slack.application.AcraSlackSample;
import com.bartering.forsa.AppCompactActivity;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.activities.bartering_detail.ChooseBarteringItemCameraActivity;
import com.bartering.forsa.activities.bartering_detail.EditProductActivity;
import com.bartering.forsa.databinding.ActivityMyAdsBinding;
import com.bartering.forsa.databinding.AdsFilterSlideLayoutBinding;
import com.bartering.forsa.models.AdsFilter_DataModel;
import com.bartering.forsa.recyclerViewAdapter.AdsBottomFilters_RecyclerViewAdapter;
import com.bartering.forsa.recyclerViewAdapter.MyAds_RecyclerViewAdapter;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.Comman_ServiceModel;
import com.bartering.forsa.retrofit.service_model.MyAds_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.SharedPreferences_Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class MyAdsActivity extends AppCompactActivity implements MyAds_RecyclerViewAdapter.OnItemEventListener, PopupMenu.OnMenuItemClickListener, ClickListener, Observer<Object> {

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 88;
    ActivityMyAdsBinding activityMyAdsBinding;
    MyAds_RecyclerViewAdapter myAds_recyclerViewAdapter;
    Dialog dialogBottom;
    AdsFilterSlideLayoutBinding adsFilterSlideLayoutBinding;
    List<AdsFilter_DataModel> categoryList;
    String selectedStatus;
    int selectedPosition;
    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;

    MyAds_ServiceModel myAdsServiceModel;
    Comman_ServiceModel comman_serviceModel;
    AdsBottomFilters_RecyclerViewAdapter adsBottomFilters_recyclerViewAdapter;
    MyAds_ServiceModel.DataBean dataBean;
    String permissionFrom = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMyAdsBinding = DataBindingUtil.setContentView(this, R.layout.activity_my_ads);
        activityMyAdsBinding.setClickListener(this::onClick);
        activityMyAdsBinding.setNoOfAds(AlphaHolder.getEventStatus("0", "FREE_ADS", MyAdsActivity.this));
        activityMyAdsBinding.setIsGuest(AlphaHolder.isGuestUser(MyAdsActivity.this));
        activityMyAdsBinding.setIsNoRecord(false);
        activityMyAdsBinding.setTotalProducts("0");

        listener();
        bottomSlider();
        manipulateFilterRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllAdverstisement();
    }

    private void bottomSlider() {
        dialogBottom = new Dialog(MyAdsActivity.this, R.style.DialogAnimation);
        dialogBottom.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBottom.setCancelable(false);
        adsFilterSlideLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(MyAdsActivity.this), R.layout.ads_filter_slide_layout, null, false);
        dialogBottom.setContentView(adsFilterSlideLayoutBinding.getRoot());
        adsFilterSlideLayoutBinding.setClickListener(this::onClick);
        adsFilterSlideLayoutBinding.closeDialogLinLayoutId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBottom.dismiss();
            }
        });
        adsFilterSlideLayoutBinding.applyTextViewId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllAdverstisement();
            }
        });
        dialogBottom.getWindow().setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogBottom.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        lp.windowAnimations = R.style.DialogAnimation;
        dialogBottom.getWindow().setAttributes(lp);
    }

    private void manipulateFilterRecyclerView() {
        categoryList = new ArrayList<>();
        categoryList.add(new AdsFilter_DataModel("Active Ads", false));
        categoryList.add(new AdsFilter_DataModel("Deactive Ads", false));
        categoryList.add(new AdsFilter_DataModel("Pending Ads", false));
        categoryList.add(new AdsFilter_DataModel("Sold Ads", false));
        categoryList.add(new AdsFilter_DataModel("Expired Ads", false));

        adsBottomFilters_recyclerViewAdapter = new AdsBottomFilters_RecyclerViewAdapter(MyAdsActivity.this, categoryList, this::onClick);
        adsFilterSlideLayoutBinding.adsFilterRecyclerViewId.setHasFixedSize(true);
        ((DefaultItemAnimator) adsFilterSlideLayoutBinding.adsFilterRecyclerViewId.getItemAnimator()).setSupportsChangeAnimations(false);
        adsFilterSlideLayoutBinding.adsFilterRecyclerViewId.setAdapter(adsBottomFilters_recyclerViewAdapter);

    }

    private void listener() {
        activityMyAdsBinding.backLLId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAdsActivity.this.finish();
            }
        });
        activityMyAdsBinding.filterLinearLayoutId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBottom.show();
            }
        });
    }

    @Override
    public void onMoreClicked(View v, int position, MyAds_ServiceModel.DataBean dataBean) {
        selectedPosition = position;
        this.dataBean = dataBean;
        PopupMenu popup = new PopupMenu(MyAdsActivity.this, v);
        popup.setOnMenuItemClickListener(this::onMenuItemClick);
        popup.inflate(R.menu.ads_option_menu);
        popup.setGravity(Gravity.END);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.edit:
                        permissionFrom = "EDIT";
                        checkPermission();


                        break;
                    case R.id.deactivate:
                        changeStatusApi(getResources().getString(R.string.deactivate_status), dataBean);
                        break;
                    case R.id.delete:
                        changeStatusApi(getResources().getString(R.string.delete_status), dataBean);
                        break;
                    case R.id.markAsSold:
                        changeStatusApi(getResources().getString(R.string.sold_status), dataBean);
                        break;
                }
                return false;
            }
        });
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event1")) {
            AlphaHolder.stackList = new ArrayList<>();
            ((AcraSlackSample) this.getApplication()).switcher(MyAdsActivity.this, BoostPlansActivity.class, 0);
        }
        if (callerIdentity.equals("event3")) { //upload button
            permissionFrom = "UPLOAD";
            checkPermission();
        }
        if (callerIdentity.equals("event4")) {
            adsFilterSlideLayoutBinding.viewAllCheckboxId.setChecked(false);
            if (categoryList != null) {
                if (categoryList.get(position).isSelected()) {
                    categoryList.get(position).setSelected(false);
                } else {
                    categoryList.get(position).setSelected(true);
                }
                adsBottomFilters_recyclerViewAdapter.notifyItemChanged(position);
            }
        }
        if (callerIdentity.equals("event5")) {
            if (categoryList != null) {
                if (adsFilterSlideLayoutBinding.viewAllCheckboxId.isChecked()) {
                    for (int j = 0; j < categoryList.size(); j++) {
                        categoryList.get(j).setSelected(true);
                    }
                    adsBottomFilters_recyclerViewAdapter.notifyDataSetChanged();
                } else {
                    for (int j = 0; j < categoryList.size(); j++) {
                        categoryList.get(j).setSelected(false);
                    }
                    adsBottomFilters_recyclerViewAdapter.notifyDataSetChanged();
                }
            }
        }
    }
    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        if (resultData.getTag().equals("ADS_DATA")) {
            myAdsServiceModel = (MyAds_ServiceModel) resultData.getRootData().getValue();
            if (myAdsServiceModel.getMessage().equals("Success")) {
                if(myAdsServiceModel.getData().size()>0){
                    activityMyAdsBinding.setIsNoRecord(false);
                    activityMyAdsBinding.setTotalProducts("" + myAdsServiceModel.getData().size());
                    myAds_recyclerViewAdapter = new MyAds_RecyclerViewAdapter(MyAdsActivity.this, myAdsServiceModel.getData(), this::onMoreClicked, this);
                    activityMyAdsBinding.transactionRecyclerViewId.setAdapter(myAds_recyclerViewAdapter);
                }
                else{
                    activityMyAdsBinding.setIsNoRecord(true);
                }

            } else {
                AlphaHolder.customToast(MyAdsActivity.this, myAdsServiceModel.getMessage());
            }
            if (dialogBottom != null && dialogBottom.isShowing())
                dialogBottom.dismiss();
        }
        if (resultData.getTag().equals("CHANGE_STATUS")) {
            comman_serviceModel = (Comman_ServiceModel) resultData.getRootData().getValue();
            if (comman_serviceModel.isStatus().equals("true")) {

                if (selectedStatus.equals(getResources().getString(R.string.delete_status)))
                    myAdsServiceModel.getData().get(selectedPosition).setProductstatus(getResources().getString(R.string.delete_status_value));
                else if (selectedStatus.equals(getResources().getString(R.string.deactivate_status)))
                    myAdsServiceModel.getData().get(selectedPosition).setProductstatus(getResources().getString(R.string.deactivate_status_value));
                else if (selectedStatus.equals(getResources().getString(R.string.sold_status)))
                    myAdsServiceModel.getData().get(selectedPosition).setProductstatus(getResources().getString(R.string.sold_status_value));

                myAds_recyclerViewAdapter.notifyItemChanged(selectedPosition);


            } else {
                AlphaHolder.customToast(MyAdsActivity.this, comman_serviceModel.getMessage());
            }
        }
    }

    public void getAllAdverstisement() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", SharedPreferences_Util.getToken(MyAdsActivity.this));
        paramMap.put("active", categoryList.get(0).isSelected() ? "1" : "0");
        paramMap.put("deactive", categoryList.get(1).isSelected() ? "1" : "0");
        paramMap.put("pending", categoryList.get(2).isSelected() ? "1" : "0");
        paramMap.put("sold", categoryList.get(3).isSelected() ? "1" : "0");
        paramMap.put("expired", categoryList.get(4).isSelected() ? "1" : "0");

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("ADS_DATA", paramMap, true, MyAdsActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }
    public void changeStatusApi(String status, MyAds_ServiceModel.DataBean dataBean) {
        selectedStatus = status;
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", SharedPreferences_Util.getToken(MyAdsActivity.this));
        paramMap.put("prd_id", dataBean.getId());
        paramMap.put("prd_status", status);

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("CHANGE_STATUS", paramMap, true, MyAdsActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSION_REQUEST_CODE);
            return false;
        } else {
            if(permissionFrom.equals("EDIT")){
                Intent intent = new Intent(MyAdsActivity.this, EditProductActivity.class);
                intent.putExtra("ADS_DATA", dataBean);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
            else{
                AlphaHolder.isFromMyAds = "MY_ADS";
                Intent intent = new Intent(MyAdsActivity.this, ChooseBarteringItemCameraActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);

            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CAMERA_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(permissionFrom.equals("EDIT")){
                        Intent intent = new Intent(MyAdsActivity.this, EditProductActivity.class);
                        intent.putExtra("ADS_DATA", dataBean);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                    }
                    else{
                        AlphaHolder.isFromMyAds = "MY_ADS";
                        Intent intent = new Intent(MyAdsActivity.this, ChooseBarteringItemCameraActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);

                    }
                } else {
                    Toast.makeText(MyAdsActivity.this, "[WARN] camera permission is not grunted.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}