package com.bartering.forsa.activities.bartering_detail;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

import com.bartering.forsa.ACRA_Slack.application.AcraSlackSample;
import com.bartering.forsa.AppCompactActivity;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.Fragment.SimilarProducts_TabFragment;
import com.bartering.forsa.Fragment.WishList_Products_TabFragment;
import com.bartering.forsa.R;
import com.bartering.forsa.activities.seller.SellerProfileActivity;
import com.bartering.forsa.databinding.ActivityProductDetailBinding;
import com.bartering.forsa.databinding.ReportAdLayoutBinding;
import com.bartering.forsa.databinding.SorryLayoutBinding;
import com.bartering.forsa.infiniteindicator.GlideLoader;
import com.bartering.forsa.recyclerViewAdapter.ProductDescriptionHead_RecyclerViewAdapter;
import com.bartering.forsa.recyclerViewAdapter.ReviewsProducts_RecyclerViewAdapter;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.Comman_ServiceModel;
import com.bartering.forsa.retrofit.service_model.HomeProducts_ServiceModel;
import com.bartering.forsa.retrofit.service_model.ImproveList_ServiceModel;
import com.bartering.forsa.retrofit.service_model.ProductDetails_ServiceModel;
import com.bartering.forsa.retrofit.service_model.ProductReviewsList_ServiceModel;
import com.bartering.forsa.retrofit.service_model.SimilarProducts_ServiceModel;
import com.bartering.forsa.tradeProcess.CartTotalActivity;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.SharedPreferences_Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import cn.lightsky.infiniteindicator.IndicatorConfiguration;
import cn.lightsky.infiniteindicator.InfiniteIndicator;
import cn.lightsky.infiniteindicator.OnPageClickListener;
import cn.lightsky.infiniteindicator.Page;

public class ProductDetailActivity extends AppCompactActivity implements View.OnClickListener, OnPageClickListener, Observer<Object>, ClickListener {

    public static String product_Id;
    ActivityProductDetailBinding activityProductDetailBinding;
    ProductDescriptionHead_RecyclerViewAdapter subscriptionPlans_recyclerViewAdapter;
    ReviewsProducts_RecyclerViewAdapter reviewsProducts_recyclerViewAdapter;
    Dialog dialogBottom;
    ReportAdLayoutBinding reportAdLayoutBinding;
    SorryLayoutBinding sorryLayoutBinding;
    HomeProducts_ServiceModel.DataBean productInfo;

    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;

    Activity activity;
    ProductDetails_ServiceModel productDetails_serviceModel;
    ImproveList_ServiceModel improveList_serviceModel;
    Comman_ServiceModel comman_serviceModel;
    SimilarProducts_ServiceModel similarProducts_serviceModel;
    ProductReviewsList_ServiceModel productReviewsList_serviceModel;
    String improvement_Id = "";
    String reportDetails = "";
    Bundle bundle;
    //slider
    private ArrayList<Page> pageViews;
    private InfiniteIndicator mAnimCircleIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProductDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail);
        activityProductDetailBinding.setClickListener(this::onClick);
        activityProductDetailBinding.setIsGuestUser(AlphaHolder.isGuestUser(this));

        getProductId();
        listener();
        prepareTabSection();
        //productDescription();
    }

    private void getProductId() {
        if (getIntent().getExtras() != null) {
            product_Id = getIntent().getExtras().getString("product_id");
            /*productInfo = (HomeProducts_ServiceModel.DataBean) getIntent().getExtras().getSerializable("product_data");
            if (productInfo != null && !TextUtils.isEmpty(productInfo.getId())) {
                product_Id = productInfo.getId();
            }*/
        }
    }

    private void reportAd_Dialog() {
        dialogBottom = new Dialog(ProductDetailActivity.this, R.style.DialogAnimationtwo);
        dialogBottom.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBottom.setCancelable(false);
        reportAdLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(ProductDetailActivity.this), R.layout.report_ad_layout, null, false);
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

    private void sorry_Dialog() {

        dialogBottom = new Dialog(ProductDetailActivity.this, R.style.DialogAnimationtwo);
        dialogBottom.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBottom.setCancelable(false);
        sorryLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(ProductDetailActivity.this), R.layout.sorry_layout, null, false);
        dialogBottom.setContentView(sorryLayoutBinding.getRoot());
        sorryLayoutBinding.closeDialogLinLayoutId.setOnClickListener(new View.OnClickListener() {
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
        dialogBottom.show();
    }

    private void manipulateSuggestionData() {
        List<String> strings = new ArrayList<>();
        strings.add("Choose one");
        for (int i = 0; i < improveList_serviceModel.getData().size(); i++) {
            strings.add(improveList_serviceModel.getData().get(i).getTitle());
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(ProductDetailActivity.this, android.R.layout.simple_dropdown_item_1line, strings);
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
                    AlphaHolder.customToast(ProductDetailActivity.this, getString(R.string.pleaseselectimproveid));
                } else if (TextUtils.isEmpty(reportDetails)) {
                    AlphaHolder.customToast(ProductDetailActivity.this, getString(R.string.pleaseenterdesi));
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

        if (!TextUtils.isEmpty(product_Id))
            getProductDetail();
        else
            AlphaHolder.customToast(ProductDetailActivity.this, getResources().getString(R.string.somethingwentwrong));
    }

    private void prepareTabSection() {
        if (AlphaHolder.isGuestUser(ProductDetailActivity.this)) {
            activityProductDetailBinding.tabOneTextViewId.setVisibility(View.VISIBLE);
            activityProductDetailBinding.tabTwoTextViewId.setVisibility(View.GONE);
            activityProductDetailBinding.secondTabParentViewId.setVisibility(View.GONE);
            activityProductDetailBinding.lineParentViewId.setVisibility(View.GONE);
        }

        callFragement(new SimilarProducts_TabFragment());
    }

    @Override
    public void onClick(View view) {
        if (view == activityProductDetailBinding.backLLId) {
            ProductDetailActivity.this.finish();
        }
        if (view == activityProductDetailBinding.tabOneTextViewId) {

            activityProductDetailBinding.tabOneIndicatorLineId.setVisibility(View.VISIBLE);
            activityProductDetailBinding.tabTwoIndicatorLineId.setVisibility(View.GONE);

            activityProductDetailBinding.secondGrayLineId.setBackgroundColor(getResources().getColor(R.color.gray));
            activityProductDetailBinding.oneGrayLineId.setBackgroundColor(getResources().getColor(R.color.black));

            callFragement(new SimilarProducts_TabFragment());

        }
        if (view == activityProductDetailBinding.tabTwoTextViewId) {

            activityProductDetailBinding.tabOneIndicatorLineId.setVisibility(View.GONE);
            activityProductDetailBinding.tabTwoIndicatorLineId.setVisibility(View.VISIBLE);

            activityProductDetailBinding.secondGrayLineId.setBackgroundColor(getResources().getColor(R.color.black));
            activityProductDetailBinding.oneGrayLineId.setBackgroundColor(getResources().getColor(R.color.gray));

            callFragement(new WishList_Products_TabFragment());

        }
        if (view == activityProductDetailBinding.reportAddLinLayoutId) {
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

        subscriptionPlans_recyclerViewAdapter = new ProductDescriptionHead_RecyclerViewAdapter(ProductDetailActivity.this, list);
        activityProductDetailBinding.setLayoutManager(new LinearLayoutManager(ProductDetailActivity.this));
        activityProductDetailBinding.setProductDescriptionRV(subscriptionPlans_recyclerViewAdapter);
    }

    private void listener() {
        activityProductDetailBinding.backLLId.setOnClickListener(this::onClick);
        activityProductDetailBinding.tabOneTextViewId.setOnClickListener(this::onClick);
        activityProductDetailBinding.tabTwoTextViewId.setOnClickListener(this::onClick);
        activityProductDetailBinding.reportAddLinLayoutId.setOnClickListener(this::onClick);

    }

    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        if (resultData.getTag().equals("PRODUCT_DETAILS")) {
            productDetails_serviceModel = (ProductDetails_ServiceModel) resultData.getRootData().getValue();
            activityProductDetailBinding.setMainData(productDetails_serviceModel.getData().getPrdrec().get(0));

            String userId = SharedPreferences_Util.getUser_Id(ProductDetailActivity.this);
            String seller_id = productDetails_serviceModel.getData().getPrdrec().get(0).getSeller_id();

            if (productDetails_serviceModel.getData().getPrdrec().get(0).getTrade().equals("yes") && !userId.equals(seller_id)) {
                activityProductDetailBinding.setIsForTrade(true);
                activityProductDetailBinding.setBthShouldBeHide(false);
            } else if (productDetails_serviceModel.getData().getPrdrec().get(0).getBatering().equals("yes") && userId.equals(seller_id)) {
                activityProductDetailBinding.setIsForTrade(false);
                activityProductDetailBinding.setBthShouldBeHide(false);
            } else if (productDetails_serviceModel.getData().getPrdrec().get(0).getBatering().equals("yes") && !userId.equals(seller_id)) {
                activityProductDetailBinding.setBthShouldBeHide(true);
                activityProductDetailBinding.setIsForTrade(false);
            } else {
                activityProductDetailBinding.setBthShouldBeHide(true);
                activityProductDetailBinding.setIsForTrade(false);
            }

            getAllReviews();
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
                AlphaHolder.customToast(ProductDetailActivity.this, comman_serviceModel.getMessage());
                dialogBottom.dismiss();
            }
        }
        if (resultData.getTag().equals("BUY_NOW")) {
            comman_serviceModel = (Comman_ServiceModel) resultData.getRootData().getValue();
            if (comman_serviceModel.isStatus().equals("true")) {
                ((AcraSlackSample) getApplication()).switcher(ProductDetailActivity.this, CartTotalActivity.class, 0);
            }
        }
        if (resultData.getTag().equals("SIMILAR_PRODUCTS_LIST")) {
            similarProducts_serviceModel = (SimilarProducts_ServiceModel) resultData.getRootData().getValue();
            if (similarProducts_serviceModel.isStatus().equals("true")) {

            }
        }
        if (resultData.getTag().equals("PRODUCT_REVIEWS")) {
            productReviewsList_serviceModel = (ProductReviewsList_ServiceModel) resultData.getRootData().getValue();
            if (productReviewsList_serviceModel.isStatus().equals("true")) {
                if (productReviewsList_serviceModel.getData().size() > 0) {
                    activityProductDetailBinding.setReviewsSize(getResources().getString(R.string.reviews) + " ( " + productReviewsList_serviceModel.getData().size() + " )");
                    reviewsProducts_recyclerViewAdapter = new ReviewsProducts_RecyclerViewAdapter(ProductDetailActivity.this, productReviewsList_serviceModel.getData(), this::onClick);
                    activityProductDetailBinding.setReviewsDataRecyclerView(reviewsProducts_recyclerViewAdapter);
                }
            } else {
                activityProductDetailBinding.setReviewsSize(getResources().getString(R.string.reviews) + " ( 0 )");
            }
            ////setting scrollerview here because review refreshing view so scrollview just losting remain
            if (productDetails_serviceModel.getData().getMediarec().size() > 0) {
                addSlidersTest(productDetails_serviceModel.getData().getMediarec());
            }
        }
    }

    private void addSlidersTest(List<ProductDetails_ServiceModel.DataBean.MediarecBean> mediarecBeansList) {
        mAnimCircleIndicator = (InfiniteIndicator) findViewById(R.id.infinite_anim_circle);
        pageViews = new ArrayList<Page>();
        if (productDetails_serviceModel.getData().getMediarec().size() > 0) {
            for (int m = 0; m < mediarecBeansList.size(); m++) {
                ProductDetails_ServiceModel.DataBean.MediarecBean mediarecBean = mediarecBeansList.get(m);
                pageViews.add(new Page("Page A", mediarecBean.getProduct_image()));

                if (m == mediarecBeansList.size() - 1) {
                    IndicatorConfiguration configuration = new IndicatorConfiguration.Builder()
                            .imageLoader(new GlideLoader())
                            .position(IndicatorConfiguration.IndicatorPosition.Center_Bottom)
                            .build();
                    mAnimCircleIndicator.init(configuration);
                    mAnimCircleIndicator.notifyDataChange(pageViews);
                    mAnimCircleIndicator.start();
                }
            }
        }
    }

    public void getProductDetail() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("prd_id", product_Id);
        paramMap.put("token", SharedPreferences_Util.getToken(ProductDetailActivity.this));
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("PRODUCT_DETAILS", paramMap, true, this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    public void getAllReviews() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("prd_id", product_Id);
        paramMap.put("token", SharedPreferences_Util.getToken(ProductDetailActivity.this));
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("PRODUCT_REVIEWS", paramMap, false, this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    public void submitReport() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("prd_id", product_Id);
        paramMap.put("improvement_id", improvement_Id);
        paramMap.put("details", reportDetails);
        paramMap.put("token", SharedPreferences_Util.getToken(ProductDetailActivity.this));
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("REPORT_AD", paramMap, true, this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    public void getImprovementList() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("prd_id", product_Id);
        paramMap.put("token", SharedPreferences_Util.getToken(ProductDetailActivity.this));
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("IMPROVEMENT_LIST", paramMap, true, this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    public void buyNow() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("prd_id", product_Id);
        paramMap.put("token", SharedPreferences_Util.getToken(ProductDetailActivity.this));
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("BUY_NOW", paramMap, true, this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event1")) {
            sorry_Dialog();
        }
        if (callerIdentity.equals("event2")) {///seller profile
            ProductDetails_ServiceModel.DataBean.PrdrecBean prdrecBean = (ProductDetails_ServiceModel.DataBean.PrdrecBean) object;
            if (!TextUtils.isEmpty(prdrecBean.getSeller_id())) {
                Intent intent = new Intent(ProductDetailActivity.this, SellerProfileActivity.class);
                intent.putExtra("SELLER_ID", prdrecBean.getSeller_id());
                startActivity(intent);
                overridePendingTransition(0, 0);
            } else {
                AlphaHolder.customToast(ProductDetailActivity.this, getResources().getString(R.string.makeacakktoprogrammer));
            }

        }
        if (callerIdentity.equals("event3")) {///ReviewClick

        }
        if (callerIdentity.equals("event4")) {///Buy
            buyNow();
        }
        if (callerIdentity.equals("event5")) {///Bartering
            if (!TextUtils.isEmpty(product_Id)) {
                Intent intent = new Intent(ProductDetailActivity.this, ChooseBarteringItemSuggestionActivity.class);
                intent.putExtra("product_data", productDetails_serviceModel);
                startActivity(intent);
                overridePendingTransition(0, 0);
            } else
                AlphaHolder.customToast(ProductDetailActivity.this, getResources().getString(R.string.somethingwentwrong));
        }
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