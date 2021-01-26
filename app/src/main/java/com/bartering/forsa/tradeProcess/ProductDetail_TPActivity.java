package com.bartering.forsa.tradeProcess;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bartering.forsa.AppCompactActivity;
import com.bartering.forsa.Fragment.SimilarProducts_TabFragment;
import com.bartering.forsa.Fragment.WishList_Products_TabFragment;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.ActivityProductDetailTpBinding;
import com.bartering.forsa.databinding.ReportAdLayoutBinding;
import com.bartering.forsa.infiniteindicator.GlideLoader;
import com.bartering.forsa.recyclerViewAdapter.ProductDescriptionHead_RecyclerViewAdapter;
import com.bartering.forsa.recyclerViewAdapter.ReviewsProducts_RecyclerViewAdapter;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.Comman_ServiceModel;
import com.bartering.forsa.retrofit.service_model.ImproveList_ServiceModel;
import com.bartering.forsa.retrofit.service_model.ProductDetails_ServiceModel;
import com.bartering.forsa.retrofit.service_model.SimilarProducts_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.SharedPreferences_Util;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import cn.lightsky.infiniteindicator.IndicatorConfiguration;
import cn.lightsky.infiniteindicator.InfiniteIndicator;
import cn.lightsky.infiniteindicator.OnPageClickListener;
import cn.lightsky.infiniteindicator.Page;

public class ProductDetail_TPActivity extends AppCompactActivity implements View.OnClickListener, OnPageClickListener, Observer<Object> {

    ActivityProductDetailTpBinding activityProductDetailTPBinding;
    ProductDescriptionHead_RecyclerViewAdapter subscriptionPlans_recyclerViewAdapter;
    ReviewsProducts_RecyclerViewAdapter reviewsProducts_recyclerViewAdapter;
    Dialog dialogBottom;
    ReportAdLayoutBinding reportAdLayoutBinding;
    String product_Id;

    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;

    Activity activity;
    ProductDetails_ServiceModel productDetails_serviceModel;
    ImproveList_ServiceModel improveList_serviceModel;
    Comman_ServiceModel comman_serviceModel;
    SimilarProducts_ServiceModel similarProducts_serviceModel;
    String improvement_Id = "";
    String reportDetails = "";
    Bundle bundle;
    //slider
    private ArrayList<Page> pageViews;
    private InfiniteIndicator mAnimCircleIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProductDetailTPBinding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail_tp);

        getProductId();
        listener();
        prepareTabSection();
        productDescription();
        //prepareCommentSection();
        getProductDetail();
    }

    private void getProductId() {
        if (getIntent().getExtras() != null) {
            product_Id = getIntent().getExtras().getString("product_id");
        }
    }

    private void reportAd_Dialog() {
        dialogBottom = new Dialog(ProductDetail_TPActivity.this, R.style.DialogAnimationtwo);
        dialogBottom.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBottom.setCancelable(false);
        reportAdLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(ProductDetail_TPActivity.this), R.layout.report_ad_layout, null, false);
        dialogBottom.setContentView(reportAdLayoutBinding.getRoot());

        reportAdLayoutBinding.closeDialogLinLayoutId.setOnClickListener(new View.OnClickListener() {
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
        lp.gravity = Gravity.CENTER;
        lp.windowAnimations = R.style.DialogAnimationtwo;
        dialogBottom.getWindow().setAttributes(lp);
        manipulateSuggestionData();
        dialogBottom.show();
    }

    private void manipulateSuggestionData() {
        List<String> strings = new ArrayList<>();
        strings.add("Choose one");
        for (int i = 0; i < improveList_serviceModel.getData().size(); i++) {
            strings.add(improveList_serviceModel.getData().get(i).getTitle());
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(ProductDetail_TPActivity.this, android.R.layout.simple_dropdown_item_1line, strings);
        reportAdLayoutBinding.setSuggestionSpinnerAdapter(arrayAdapter);

        reportAdLayoutBinding.suggestionSpinnerId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    if (improveList_serviceModel != null && improveList_serviceModel.getData().size() > 0) {
                        improvement_Id = improveList_serviceModel.getData().get(position - 1).getId();
                    }
                } else {
                    improvement_Id = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        reportAdLayoutBinding.submitTextViewId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportDetails = reportAdLayoutBinding.detailsEditTextId.getText().toString();
                if (TextUtils.isEmpty(improvement_Id)) {
                    AlphaHolder.customToast(ProductDetail_TPActivity.this, getString(R.string.pleaseselectimproveid));
                } else if (TextUtils.isEmpty(reportDetails)) {
                    AlphaHolder.customToast(ProductDetail_TPActivity.this, getString(R.string.pleaseenterdesi));
                } else {
                    submitReport();
                }

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

  /*  private void prepareCommentSection() {
        List<String> list = new ArrayList();
        list.add("- Drone Dji Pro");
        list.add("- One year old, Rarely used");
        list.add("- One year old, Rarely used");
        list.add("- One year old, Rarely used");
        list.add("- One year old, Rarely used");
        list.add("- One year old, Rarely used");
        list.add("- One year old, Rarely used");
        list.add("- One year old, Rarely used");

        reviewsProducts_recyclerViewAdapter = new ReviewsProducts_RecyclerViewAdapter(ProductDetail_TPActivity.this, list);
        activityProductDetailTPBinding.setReviewsDataRecyclerView(reviewsProducts_recyclerViewAdapter);
    }*/


    private void prepareTabSection() {
        bundle = new Bundle();
        bundle.putString("product_id", product_Id);
        SimilarProducts_TabFragment similarProducts_tabFragment = new SimilarProducts_TabFragment();
        similarProducts_tabFragment.setArguments(bundle);
        callFragement(similarProducts_tabFragment);
    }

    @Override
    public void onClick(View view) {
        if (view == activityProductDetailTPBinding.backLLId) {
            ProductDetail_TPActivity.this.finish();
        }
        if (view == activityProductDetailTPBinding.tabOneTextViewId) {

            activityProductDetailTPBinding.tabOneIndicatorLineId.setVisibility(View.VISIBLE);
            activityProductDetailTPBinding.tabTwoIndicatorLineId.setVisibility(View.GONE);

            activityProductDetailTPBinding.secondGrayLineId.setBackgroundColor(getResources().getColor(R.color.gray));
            activityProductDetailTPBinding.oneGrayLineId.setBackgroundColor(getResources().getColor(R.color.black));

            bundle = new Bundle();
            bundle.putString("product_id", product_Id);
            SimilarProducts_TabFragment similarProducts_tabFragment = new SimilarProducts_TabFragment();
            similarProducts_tabFragment.setArguments(bundle);
            callFragement(similarProducts_tabFragment);

        }
        if (view == activityProductDetailTPBinding.tabTwoTextViewId) {

            activityProductDetailTPBinding.tabOneIndicatorLineId.setVisibility(View.GONE);
            activityProductDetailTPBinding.tabTwoIndicatorLineId.setVisibility(View.VISIBLE);

            activityProductDetailTPBinding.secondGrayLineId.setBackgroundColor(getResources().getColor(R.color.black));
            activityProductDetailTPBinding.oneGrayLineId.setBackgroundColor(getResources().getColor(R.color.gray));

            bundle = new Bundle();
            bundle.putString("product_id", product_Id);
            WishList_Products_TabFragment wishList_products_tabFragment = new WishList_Products_TabFragment();
            wishList_products_tabFragment.setArguments(bundle);
            callFragement(wishList_products_tabFragment);

        }
        if (view == activityProductDetailTPBinding.reportAddLinLayoutId) {
            getImprovementList();
        }
    }

    private void callFragement(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.tabRepalcerId, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onPageClick(int position, Page page) {
        Toast.makeText(this, "position = " + position, Toast.LENGTH_SHORT).show();
    }

    private void productDescription() {
        List<String> list = new ArrayList();
        list.add("- Drone Dji Pro");
        list.add("- One year old, Rarely used");
        subscriptionPlans_recyclerViewAdapter = new ProductDescriptionHead_RecyclerViewAdapter(ProductDetail_TPActivity.this, list);
        activityProductDetailTPBinding.setLayoutManager(new LinearLayoutManager(ProductDetail_TPActivity.this));
        activityProductDetailTPBinding.setProductDescriptionRV(subscriptionPlans_recyclerViewAdapter);
    }
    private void listener() {
        activityProductDetailTPBinding.backLLId.setOnClickListener(this::onClick);
        activityProductDetailTPBinding.tabOneTextViewId.setOnClickListener(this::onClick);
        activityProductDetailTPBinding.tabTwoTextViewId.setOnClickListener(this::onClick);
        activityProductDetailTPBinding.reportAddLinLayoutId.setOnClickListener(this::onClick);
    }

    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        if (resultData.getTag().equals("PRODUCT_DETAILS")) {
            productDetails_serviceModel = (ProductDetails_ServiceModel) resultData.getRootData().getValue();
            activityProductDetailTPBinding.setMainData(productDetails_serviceModel.getData().getPrdrec().get(0));

            if (productDetails_serviceModel != null) {
                Log.e("result", new Gson().toJson(productDetails_serviceModel));
                addSlidersTest(productDetails_serviceModel);
            }

        }
        if (resultData.getTag().equals("IMPROVEMENT_LIST")) {
            improveList_serviceModel = (ImproveList_ServiceModel) resultData.getRootData().getValue();
            if (improveList_serviceModel != null && improveList_serviceModel.getData().size() > 0) {
                reportAd_Dialog();
            }
        }
        if (resultData.getTag().equals("REPORT_AD")) {
            comman_serviceModel = (Comman_ServiceModel) resultData.getRootData().getValue();
            if (comman_serviceModel.isStatus().equals("true")) {
                AlphaHolder.customToast(ProductDetail_TPActivity.this, comman_serviceModel.getMessage());
                dialogBottom.dismiss();
            }
        }
        if (resultData.getTag().equals("SIMILAR_PRODUCTS_LIST")) {
            similarProducts_serviceModel = (SimilarProducts_ServiceModel) resultData.getRootData().getValue();
            if (similarProducts_serviceModel.isStatus().equals("true")) {

            }
        }
    }

    private void addSlidersTest(ProductDetails_ServiceModel productDetails_serviceModel) {
        mAnimCircleIndicator = (InfiniteIndicator) findViewById(R.id.infinite_anim_circle);
        pageViews = new ArrayList<Page>();
        if (productDetails_serviceModel.getData().getMediarec().size() > 0) {
            for (int m = 0; m < productDetails_serviceModel.getData().getMediarec().size(); m++) {
                ProductDetails_ServiceModel.DataBean.MediarecBean mediarecBean = productDetails_serviceModel.getData().getMediarec().get(m);
                pageViews.add(new Page("Page A", mediarecBean.getProduct_image()));
            }
        }
        IndicatorConfiguration configuration = new IndicatorConfiguration.Builder()
                .imageLoader(new GlideLoader())
                .position(IndicatorConfiguration.IndicatorPosition.Center_Bottom)
                .build();
        mAnimCircleIndicator.init(configuration);
        mAnimCircleIndicator.notifyDataChange(pageViews);
        mAnimCircleIndicator.start();
    }

    public void getProductDetail() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("prd_id", product_Id);
        paramMap.put("token", SharedPreferences_Util.getToken(ProductDetail_TPActivity.this));
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("PRODUCT_DETAILS", paramMap, true, this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    public void submitReport() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("prd_id", product_Id);
        paramMap.put("improvement_id", improvement_Id);
        paramMap.put("details", reportDetails);
        paramMap.put("token", SharedPreferences_Util.getToken(ProductDetail_TPActivity.this));
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("REPORT_AD", paramMap, true, this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    public void getImprovementList() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("prd_id", product_Id);
        paramMap.put("token", SharedPreferences_Util.getToken(ProductDetail_TPActivity.this));
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("IMPROVEMENT_LIST", paramMap, true, this);
        viewModel.getRootData().observe(this, this::onChanged);
    }


    public class Pager extends FragmentStatePagerAdapter {

        //integer to count number of tabs
        int tabCount;

        //Constructor to the class
        public Pager(FragmentManager fm, int tabCount) {
            super(fm);
            //Initializing tab count
            this.tabCount = tabCount;
        }

        //Overriding method getItem
        @Override
        public Fragment getItem(int position) {
            //Returning the current tabs
            switch (position) {
                case 0:
                    SimilarProducts_TabFragment tab1 = new SimilarProducts_TabFragment();
                    bundle = new Bundle();
                    bundle.putString("product_id", product_Id);
                    tab1.setArguments(bundle);
                    return tab1;
                case 1:
                    WishList_Products_TabFragment tab2 = new WishList_Products_TabFragment();
                    bundle = new Bundle();
                    bundle.putString("product_id", product_Id);
                    tab2.setArguments(bundle);
                    return tab2;

                default:
                    return null;
            }
        }

        //Overriden method getCount to get the number of tabs
        @Override
        public int getCount() {
            return tabCount;
        }
    }

}