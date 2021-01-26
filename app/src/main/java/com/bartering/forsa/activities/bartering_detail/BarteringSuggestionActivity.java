package com.bartering.forsa.activities.bartering_detail;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bartering.forsa.AppCompactActivity;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.chat_module.ChatActivity;
import com.bartering.forsa.databinding.ActivityBarteringSuggestionBinding;
import com.bartering.forsa.databinding.BottomBarteringSuggesLayoutBinding;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.BarteringProducts_ServiceModel;
import com.bartering.forsa.retrofit.service_model.Comman_ServiceModel;
import com.bartering.forsa.retrofit.service_model.OfferBartering_ServiceModel;
import com.bartering.forsa.retrofit.service_model.ProductDetails_ServiceModel;
import com.bartering.forsa.retrofit.service_model.SignIn_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.SharedPreferences_Util;

import java.util.HashMap;

import javax.inject.Inject;

public class BarteringSuggestionActivity extends AppCompactActivity implements ClickListener, Observer<Object> {

    ActivityBarteringSuggestionBinding activityBarteringSuggestionBinding;
    Dialog dialogBottom;
    BottomBarteringSuggesLayoutBinding bottomBarteringSuggesLayoutBinding;
    BarteringProducts_ServiceModel.DataBean selectedProductData;
    ProductDetails_ServiceModel myProductData;
    SignIn_ServiceModel signIn_serviceModel = null;

    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBarteringSuggestionBinding = DataBindingUtil.setContentView(this, R.layout.activity_bartering_suggestion);
        activityBarteringSuggestionBinding.setClickListener(this::onClick);
        activityBarteringSuggestionBinding.setLoggedInUserName(SharedPreferences_Util.getLoggedInUserName(this));

        bottomSlider();
    }

    private void getProductsData() {
        if (getIntent().getExtras() != null) {
            selectedProductData = (BarteringProducts_ServiceModel.DataBean) getIntent().getSerializableExtra("SELECTED_PRODUCT");
            myProductData = (ProductDetails_ServiceModel) getIntent().getSerializableExtra("MY_PRODUCT");

            if (myProductData.getData().getMediarec().size() > 0)
                bottomBarteringSuggesLayoutBinding.setMyProductImage(myProductData.getData().getMediarec().get(0).getProduct_image());
            bottomBarteringSuggesLayoutBinding.setSelectedProductImage(selectedProductData.getPrd_img());
            bottomBarteringSuggesLayoutBinding.setEndUserImage(selectedProductData.getUser_img());

            signIn_serviceModel = new SharedPreferences_Util(BarteringSuggestionActivity.this).getLoginModel();
            if (signIn_serviceModel != null) {
                bottomBarteringSuggesLayoutBinding.setMyImage(signIn_serviceModel.getData().getImage());
            }
        }
    }

    private void bottomSlider() {
        dialogBottom = new Dialog(BarteringSuggestionActivity.this, R.style.DialogAnimation);
        dialogBottom.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBottom.setCancelable(false);
        bottomBarteringSuggesLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(BarteringSuggestionActivity.this), R.layout.bottom_bartering_sugges_layout, null, false);
        dialogBottom.setContentView(bottomBarteringSuggesLayoutBinding.getRoot());
        bottomBarteringSuggesLayoutBinding.setClickListener(this::onClick);

        dialogBottom.getWindow().setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogBottom.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        lp.windowAnimations = R.style.DialogAnimation;
        dialogBottom.getWindow().setAttributes(lp);
        dialogBottom.show();

        getProductsData();

    }

    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        if (resultData != null) {
            if (resultData.getTag().equals("PROCEED_BARTERING")) {
                OfferBartering_ServiceModel offerBartering_serviceModel = (OfferBartering_ServiceModel) resultData.getRootData().getValue();
                if(offerBartering_serviceModel.isStatus().equals("true")){
                    Intent intent = new Intent(BarteringSuggestionActivity.this, BarteringSuggestionChatActivity.class);
                    intent.putExtra("SELECTED_PRODUCT", selectedProductData);
                    intent.putExtra("BARTERING_ID", offerBartering_serviceModel);
                    intent.putExtra("MY_PRODUCT", myProductData);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
                else{
                    AlphaHolder.customToast(BarteringSuggestionActivity.this,offerBartering_serviceModel.getMessage());
                }

            }
        }
    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event1")) {
            BarteringSuggestionActivity.this.finish();
        }
        if (callerIdentity.equals("event2")) {
            if (dialogBottom.isShowing()) {
                dialogBottom.dismiss();
            } else {
                dialogBottom.show();
            }
        }
        if (callerIdentity.equals("event3")) { /// dialog back button
            BarteringSuggestionActivity.this.finish();
        }
        if (callerIdentity.equals("event4")) { ///start bartering button
            startBartering();
        }
    }

    private void startBartering() {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("offer_by_prd_id", myProductData.getData().getPrdrec().get(0).getPrd_id());
        paramMap.put("offer_to_prd_id", selectedProductData.getId());
        paramMap.put("token", SharedPreferences_Util.getToken(BarteringSuggestionActivity.this));

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("PROCEED_BARTERING", paramMap, true, BarteringSuggestionActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }
}