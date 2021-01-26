package com.bartering.forsa.activities.bartering_detail;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bartering.forsa.AppCompactActivity;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.ActivityChooseBarteringItemBinding;
import com.bartering.forsa.databinding.ChooseBarteringItemLayoutBinding;
import com.bartering.forsa.databinding.ChooseBarteringSuggestionLayoutBinding;
import com.bartering.forsa.recyclerViewAdapter.ChooseBarteringImages_Item_RecyclerViewAdapter;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.BarteringProducts_ServiceModel;
import com.bartering.forsa.retrofit.service_model.ProductDetails_ServiceModel;
import com.bartering.forsa.retrofit.service_model.ProductPreview_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.SharedPreferences_Util;

import java.util.HashMap;

import javax.inject.Inject;

public class ChooseBarteringItemSuggestionActivity extends AppCompactActivity implements ClickListener, Observer<Object> {

    ActivityChooseBarteringItemBinding activityChooseBarteringItemBinding;
    Dialog dialogBottom;
    Dialog suggestionBottom;

    ChooseBarteringItemLayoutBinding chooseBarteringItemLayoutBinding;
    ChooseBarteringSuggestionLayoutBinding barteringSuggestionLayoutBinding;
    ChooseBarteringImages_Item_RecyclerViewAdapter chooseBarteringImages_item_recyclerViewAdapter;

    BarteringProducts_ServiceModel barteringProducts_serviceModel;

    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;

    ProductDetails_ServiceModel productDetails_serviceModel = null;
    BarteringProducts_ServiceModel.DataBean selectedProduct;
    ProductPreview_ServiceModel productPreview_serviceModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityChooseBarteringItemBinding = DataBindingUtil.setContentView(this, R.layout.activity_choose_bartering_item);
        activityChooseBarteringItemBinding.setLoggedInUserName(SharedPreferences_Util.getLoggedInUserName(this));

        if (getIntent().getExtras() != null) {
            if (getIntent().hasExtra("ADDED_DATA")) {
                productDetails_serviceModel = (ProductDetails_ServiceModel) getIntent().getSerializableExtra("ADDED_DATA");
                /*if (productPreview_serviceModel != null && productDetails_serviceModel == null) {
                    productDetails_serviceModel = new ProductDetails_ServiceModel();

                    productDetails_serviceModel.setStatus("true");
                    productDetails_serviceModel.setMessage("success");


                    String prd_id = productPreview_serviceModel.getData().get(0).getDetail().get(0).getId();
                    List<ProductDetails_ServiceModel.DataBean.PrdrecBean> prdrecBeans = new ArrayList<>();
                    prdrecBeans.get(0).setPrd_id(prd_id);
                    productDetails_serviceModel.getData().setPrdrec(prdrecBeans); //// set product_id

                    ///set images below
                    if (productPreview_serviceModel.getData().get(0).getMedia().size() > 0) {
                        List<ProductDetails_ServiceModel.DataBean.MediarecBean> mediarecBeans = new ArrayList<>();
                        for (int image = 0; image < productPreview_serviceModel.getData().get(0).getMedia().size(); image++) {
                            ProductDetails_ServiceModel.DataBean.MediarecBean mediarecBean = new ProductDetails_ServiceModel.DataBean.MediarecBean();
                            mediarecBean.setMedia_type(productPreview_serviceModel.getData().get(0).getMedia().get(image).getMedia_type());
                            mediarecBean.setProduct_image(productPreview_serviceModel.getData().get(0).getMedia().get(image).getMedia_file());
                            mediarecBeans.add(mediarecBean);

                            if (image == productPreview_serviceModel.getData().get(0).getMedia().size() - 1) {
                                productDetails_serviceModel.getData().setMediarec(mediarecBeans);

                                listener();
                                bottomSlider();
                                guiderCentral();
                                getDataFromService();

                            }
                        }
                    }
                }*/

            } else {
                productDetails_serviceModel = (ProductDetails_ServiceModel) getIntent().getSerializableExtra("product_data");
            }
        }
        listener();
        bottomSlider();
        guiderCentral();
        getDataFromService();


    }

    private void getDataFromService() {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("token", SharedPreferences_Util.getToken(ChooseBarteringItemSuggestionActivity.this));
        paramMap.put("prd_id", productDetails_serviceModel.getData().getPrdrec().get(0).getPrd_id());
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("BARTERING_SUGGESTION_DATA", paramMap, true, ChooseBarteringItemSuggestionActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    private void listener() {
        activityChooseBarteringItemBinding.backLLId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseBarteringItemSuggestionActivity.this.finish();
            }
        });
        activityChooseBarteringItemBinding.menuLinLayoutId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBottom.show();
            }
        });
    }

    private void bottomSlider() {
        dialogBottom = new Dialog(ChooseBarteringItemSuggestionActivity.this, R.style.DialogAnimation);
        dialogBottom.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBottom.setCancelable(false);
        chooseBarteringItemLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(ChooseBarteringItemSuggestionActivity.this), R.layout.choose_bartering_item_layout, null, false);
        chooseBarteringItemLayoutBinding.setIsNoRecord(false);

        dialogBottom.setContentView(chooseBarteringItemLayoutBinding.getRoot());
        if (productDetails_serviceModel != null && productDetails_serviceModel.getData().getMediarec().size() > 0)
            chooseBarteringItemLayoutBinding.setProductsImage(productDetails_serviceModel.getData().getMediarec().get(0).getProduct_image());

        chooseBarteringItemLayoutBinding.closeDialogLinLayoutId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBottom.dismiss();
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

        dialogBottom.show();
    }

    private void guiderCentral() {
        suggestionBottom = new Dialog(ChooseBarteringItemSuggestionActivity.this);
        suggestionBottom.requestWindowFeature(Window.FEATURE_NO_TITLE);
        suggestionBottom.setCancelable(true);
        barteringSuggestionLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(ChooseBarteringItemSuggestionActivity.this), R.layout.choose_bartering_suggestion_layout, null, false);
        suggestionBottom.setContentView(barteringSuggestionLayoutBinding.getRoot());

        suggestionBottom.getWindow().setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(suggestionBottom.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        suggestionBottom.getWindow().setAttributes(lp);

        barteringSuggestionLayoutBinding.thanksBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                suggestionBottom.dismiss();
                // dialogBottom.show();
            }
        });
        suggestionBottom.show();
    }
    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        if (resultData.getTag().equals("BARTERING_SUGGESTION_DATA")) {
            barteringProducts_serviceModel = (BarteringProducts_ServiceModel) resultData.getRootData().getValue();
            if (barteringProducts_serviceModel.getData().size() > 0) {
                chooseBarteringItemLayoutBinding.setIsNoRecord(false);
                barteringProducts_serviceModel.getData().add(0, null);

                chooseBarteringImages_item_recyclerViewAdapter = new ChooseBarteringImages_Item_RecyclerViewAdapter(ChooseBarteringItemSuggestionActivity.this, barteringProducts_serviceModel.getData(), this::onClick);
                chooseBarteringItemLayoutBinding.barteringProductRecyclerViewId.setLayoutManager(new LinearLayoutManager(ChooseBarteringItemSuggestionActivity.this, LinearLayoutManager.HORIZONTAL, true));
                chooseBarteringItemLayoutBinding.barteringProductRecyclerViewId.setAdapter(chooseBarteringImages_item_recyclerViewAdapter);
            } else {
                chooseBarteringItemLayoutBinding.setIsNoRecord(true);
            }
        }
    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event1")) {

        }
        if (callerIdentity.equals("event2")) { ////camera
            AlphaHolder.isFromMyAds = "BARTERING_PROCESS";
            Intent intent = new Intent(ChooseBarteringItemSuggestionActivity.this, ChooseBarteringItemCameraActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
        if (callerIdentity.equals("event3")) { ///product clicked
            selectedProduct = (BarteringProducts_ServiceModel.DataBean) object;
            if (selectedProduct != null) {
                chooseBarteringItemLayoutBinding.setSelectedProduct(selectedProduct);
                Intent intent = new Intent(ChooseBarteringItemSuggestionActivity.this, BarteringSuggestionActivity.class);
                intent.putExtra("SELECTED_PRODUCT", selectedProduct);
                intent.putExtra("MY_PRODUCT", productDetails_serviceModel);
                startActivity(intent);
                overridePendingTransition(0, 0);

            }
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}