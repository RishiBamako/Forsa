package com.bartering.forsa.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.URLUtil;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bartering.forsa.ClickListener;
import com.bartering.forsa.NavigationDrawer.FragmentDrawer;
import com.bartering.forsa.R;
import com.bartering.forsa.activities.bartering_detail.ProductDetailActivity;
import com.bartering.forsa.databinding.BottomSlideLayoutBinding;
import com.bartering.forsa.databinding.FragmentHomeBMBinding;
import com.bartering.forsa.databinding.WelcomeGiftLayoutBinding;
import com.bartering.forsa.recyclerViewAdapter.CategoryBy_RecyclerViewAdapter;
import com.bartering.forsa.recyclerViewAdapter.CategoryBy_RecyclerView_NLAdapter;
import com.bartering.forsa.recyclerViewAdapter.HomeProducts_RecyclerViewAdapter;
import com.bartering.forsa.recyclerViewAdapter.SortBy_RecyclerViewAdapter;
import com.bartering.forsa.recyclerViewAdapter.SortBy_RecyclerView_NLAdapter;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.BaseFragment;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.HomeFilter_NL_ServiceModel;
import com.bartering.forsa.retrofit.service_model.HomeFilter_ServiceModel;
import com.bartering.forsa.retrofit.service_model.HomeProducts_ServiceModel;
import com.bartering.forsa.retrofit.service_model.ProductLike_ServiceModel;
import com.bartering.forsa.retrofit.service_model.Product_Add_WiLit;
import com.bartering.forsa.retrofit.service_model.SignIn_ServiceModel;
import com.bartering.forsa.retrofit.service_model.TopFilter_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.SharedPreferences_Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;


public class Home_BM_Fragment extends BaseFragment implements ClickListener, Observer<Object> {

    FragmentHomeBMBinding fragmentHomeBMBinding;
    BottomSlideLayoutBinding bottomSlideLayoutBinding;

    HomeProducts_RecyclerViewAdapter homeProducts_recyclerViewAdapter;
    Activity activity;
    Dialog bottomSliderDialog;
    Dialog dialogBottom;

    SignIn_ServiceModel signIn_serviceModel;

    ///SERVICE PART

    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;

    int clickedLike, clickedWish;

    HomeFilter_ServiceModel homeFilter_serviceModel;
    HomeFilter_NL_ServiceModel homeFilter_nl_serviceModel;
    HomeProducts_ServiceModel homeProducts_serviceModel = null;
    ProductLike_ServiceModel productLike_serviceModel;
    Product_Add_WiLit product_add_wiLit;
    TopFilter_ServiceModel topFilter_serviceModel;

    SortBy_RecyclerViewAdapter sortBy_recyclerViewAdapter;
    CategoryBy_RecyclerViewAdapter categoryBy_recyclerViewAdapter;

    SortBy_RecyclerView_NLAdapter sortBy_recyclerView_nlAdapter;
    CategoryBy_RecyclerView_NLAdapter categoryBy_recyclerView_nlAdapter;
    SharedPreferences_Util sharedPreferences_util;
    List<String> selectedCategories;
    WelcomeGiftLayoutBinding welcomeGiftLayoutBinding;
    ClickListener clickListener;
    GridLayoutManager gridLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentHomeBMBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home__b_m_, container, false);
        fragmentHomeBMBinding.setClickListener(this::onClick);
        fragmentHomeBMBinding.setIsNoRecord(false);
        fragmentHomeBMBinding.setKdaFlag(false);
        fragmentHomeBMBinding.setUserImage(URLUtil.isValidUrl(SharedPreferences_Util.getUser_Image(activity)) ? SharedPreferences_Util.getUser_Image(activity) : "http://dev.rglabs.net/forsa/uploads/user/" + SharedPreferences_Util.getUser_Image(activity));
        clickListener = this::onClick;

        return fragmentHomeBMBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        gridLayoutManager = new GridLayoutManager(activity, 2);
        fragmentHomeBMBinding.productsRecyclerViewId.setNestedScrollingEnabled(true);


        homeDataManipulation();
        bottomSlider();
        listener();
        if (AlphaHolder.isGuestUser(activity)) {
            getHomeFilterNotLoggedIn();
        } else {
            getHomeFilter();
        }
        filterData();
    }

    private void filterData() {
        fragmentHomeBMBinding.filterEditTextId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String valueIs = s.toString();
                List<HomeProducts_ServiceModel.DataBean> dataBeans = new ArrayList<>();
                if (!TextUtils.isEmpty(valueIs)) {
                    if (homeProducts_serviceModel != null) {
                        for (int fil = 0; fil < homeProducts_serviceModel.getData().size(); fil++) {
                            HomeProducts_ServiceModel.DataBean dataBean = homeProducts_serviceModel.getData().get(fil);
                            if (dataBean != null) {
                                if (dataBean.getTitle().toLowerCase().contains(valueIs.toLowerCase())) {
                                    dataBeans.add(dataBean);
                                }

                            }
                            if (fil == homeProducts_serviceModel.getData().size() - 1) {
                                if (dataBeans.size() == 0) {
                                    fragmentHomeBMBinding.setIsNoRecord(true);
                                } else {
                                    fragmentHomeBMBinding.setIsNoRecord(false);
                                }
                                dataBeans.add(null);
                                manipulateDataWithRecyclerview(dataBeans);
                            }
                        }
                    }
                } else {
                    if (homeProducts_serviceModel != null) {
                        dataBeans = homeProducts_serviceModel.getData();
                        manipulateDataWithRecyclerview(dataBeans);
                    }
                }
            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void listener() {
        /*fragmentHomeBMBinding.kdaImageViewId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.kdakw.com/"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });*/
    }

    private void homeDataManipulation() {
        signIn_serviceModel = new SharedPreferences_Util(activity).getLoginModel();
        fragmentHomeBMBinding.setUserData(signIn_serviceModel);
    }

    private void bottomSlider() {
        bottomSliderDialog = new Dialog(activity, R.style.DialogAnimation);
        bottomSliderDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        bottomSliderDialog.setCancelable(false);
        bottomSlideLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.bottom_slide_layout, null, false);
        bottomSliderDialog.setContentView(bottomSlideLayoutBinding.getRoot());
        bottomSlideLayoutBinding.setClickListener(this::onClick);

        bottomSliderDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(bottomSliderDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        lp.windowAnimations = R.style.DialogAnimation;
        bottomSliderDialog.getWindow().setAttributes(lp);

        bottomSlideLayoutBinding.clearAllTextViewId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFilter_Guest_Logged_User();
            }
        });

    }

    private void clearFilter_Guest_Logged_User() {
        if (AlphaHolder.isGuestUser(activity)) {
            if (homeFilter_nl_serviceModel != null && homeFilter_nl_serviceModel.getData() != null) {
                if (homeFilter_nl_serviceModel.getData().getCategrec() != null && homeFilter_nl_serviceModel.getData().getCategrec().size() > 0) {
                    for (int c = 0; c < homeFilter_nl_serviceModel.getData().getCategrec().size(); c++) {
                        homeFilter_nl_serviceModel.getData().getCategrec().get(c).setSelected(false);
                    }
                }
                if (homeFilter_nl_serviceModel.getData().getSortbyrec() != null && homeFilter_nl_serviceModel.getData().getSortbyrec().size() > 0) {
                    for (int s = 0; s < homeFilter_nl_serviceModel.getData().getSortbyrec().size(); s++) {
                        homeFilter_nl_serviceModel.getData().getSortbyrec().get(s).setSelected(false);
                    }
                }
                sortBy_recyclerView_nlAdapter.notifyDataSetChanged();
                categoryBy_recyclerView_nlAdapter.notifyDataSetChanged();
            }
        } else {
            if (homeFilter_serviceModel != null && homeFilter_serviceModel.getData() != null) {
                if (homeFilter_serviceModel.getData().getCategrec() != null && homeFilter_serviceModel.getData().getCategrec().size() > 0) {
                    for (int c = 0; c < homeFilter_serviceModel.getData().getCategrec().size(); c++) {
                        homeFilter_serviceModel.getData().getCategrec().get(c).setSelected(false);
                    }
                }
                if (homeFilter_serviceModel.getData().getSortbyrec() != null && homeFilter_serviceModel.getData().getSortbyrec().size() > 0) {
                    for (int s = 0; s < homeFilter_serviceModel.getData().getSortbyrec().size(); s++) {
                        homeFilter_serviceModel.getData().getSortbyrec().get(s).setSelected(false);
                    }
                }
                sortBy_recyclerViewAdapter.notifyDataSetChanged();
                categoryBy_recyclerViewAdapter.notifyDataSetChanged();
            }
        }
    }

    private void welcomeGift_Dialog(String noOfFreeAds) {
        dialogBottom = new Dialog(activity, R.style.DialogAnimationtwo);
        dialogBottom.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBottom.setCancelable(false);
        welcomeGiftLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.welcome_gift_layout, null, false);
        dialogBottom.setContentView(welcomeGiftLayoutBinding.getRoot());
        welcomeGiftLayoutBinding.setNoOfFreeAds(noOfFreeAds);

        welcomeGiftLayoutBinding.continueTextViewId.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event1")) {
            AlphaHolder.redirectToProfile(activity);
        }
        if (callerIdentity.equals("event2")) {
            boolean isSelected = homeFilter_serviceModel.getData().getSortbyrec().get(position).isSelected();
            homeFilter_serviceModel.getData().getSortbyrec().get(position).setSelected(isSelected ? false : true);
            if (sortBy_recyclerViewAdapter != null) {
                sortBy_recyclerViewAdapter.notifyItemChanged(position);
            }
        }
        if (callerIdentity.equals("event3")) {
            boolean isSelected = homeFilter_serviceModel.getData().getCategrec().get(position).isSelected();
            homeFilter_serviceModel.getData().getCategrec().get(position).setSelected(isSelected ? false : true);
            if (categoryBy_recyclerViewAdapter != null) {
                categoryBy_recyclerViewAdapter.notifyItemChanged(position);
            }
        }
        if (callerIdentity.equals("event4")) {
            bottomSliderDialog.dismiss();
            if (AlphaHolder.isGuestUser(activity)) {
                selectedCategories = new ArrayList<>();
                if (homeFilter_nl_serviceModel != null && homeFilter_nl_serviceModel.getData().getCategrec().size() > 0) {
                    for (int i = 0; i < homeFilter_nl_serviceModel.getData().getCategrec().size(); i++) {
                        HomeFilter_NL_ServiceModel.DataBean.CategrecBean categrecBean = homeFilter_nl_serviceModel.getData().getCategrec().get(i);
                        if (categrecBean.isSelected())
                            selectedCategories.add(categrecBean.getId().trim());
                    }
                }
                if (homeFilter_nl_serviceModel != null && homeFilter_nl_serviceModel.getData().getSortbyrec().size() > 0) {
                    for (int i = 0; i < homeFilter_nl_serviceModel.getData().getSortbyrec().size(); i++) {
                        HomeFilter_NL_ServiceModel.DataBean.SortbyrecBean sortbyrecBean = homeFilter_nl_serviceModel.getData().getSortbyrec().get(i);
                        if (sortbyrecBean.isSelected())
                            selectedCategories.add(sortbyrecBean.getId().trim());
                    }
                }
                PreferenceManager.getDefaultSharedPreferences(activity).edit().putString("selectedcategory", String.valueOf(selectedCategories)).apply();
                getGuestHomeProduct(true);
            } else {
                selectedCategories = new ArrayList<>();
                if (homeFilter_serviceModel != null && homeFilter_serviceModel.getData().getCategrec().size() > 0) {
                    for (int i = 0; i < homeFilter_serviceModel.getData().getCategrec().size(); i++) {
                        HomeFilter_ServiceModel.DataBean.CategrecBean categrecBean = homeFilter_serviceModel.getData().getCategrec().get(i);
                        if (categrecBean.isSelected())
                            selectedCategories.add(categrecBean.getId().trim());
                    }
                }
                if (homeFilter_serviceModel != null && homeFilter_serviceModel.getData().getSortbyrec().size() > 0) {
                    for (int i = 0; i < homeFilter_serviceModel.getData().getSortbyrec().size(); i++) {
                        HomeFilter_ServiceModel.DataBean.SortbyrecBean sortbyrecBean = homeFilter_serviceModel.getData().getSortbyrec().get(i);
                        if (sortbyrecBean.isSelected())
                            selectedCategories.add(sortbyrecBean.getId().trim());
                    }
                }
                PreferenceManager.getDefaultSharedPreferences(activity).edit().putString("selectedcategory", String.valueOf(selectedCategories)).apply();
                getHomeProduct(true);
            }
        }
        if (callerIdentity.equals("event5")) {
            //NOTUSER
            boolean isSelected = homeFilter_nl_serviceModel.getData().getSortbyrec().get(position).isSelected();
            homeFilter_nl_serviceModel.getData().getSortbyrec().get(position).setSelected(isSelected ? false : true);
            if (sortBy_recyclerView_nlAdapter != null) {
                sortBy_recyclerView_nlAdapter.notifyItemChanged(position);
            }
        }
        if (callerIdentity.equals("event6")) {
            //NOTUSER
            boolean isSelected = homeFilter_nl_serviceModel.getData().getCategrec().get(position).isSelected();
            homeFilter_nl_serviceModel.getData().getCategrec().get(position).setSelected(isSelected ? false : true);
            if (categoryBy_recyclerView_nlAdapter != null) {
                categoryBy_recyclerView_nlAdapter.notifyItemChanged(position);
            }
        }
        if (callerIdentity.equals("event7")) {
            //DIALOGE bottom slider
            bottomSliderDialog.dismiss();
        }
        if (callerIdentity.equals("event8")) {
            clickedWish = position;
            HomeProducts_ServiceModel.DataBean dataBean = (HomeProducts_ServiceModel.DataBean) object;
            addProductToWishlist(dataBean.getId());

        }
        if (callerIdentity.equals("event9")) {
            clickedLike = position;
            HomeProducts_ServiceModel.DataBean dataBean = (HomeProducts_ServiceModel.DataBean) object;
            likeProduct(dataBean.getId());
        }
        if (callerIdentity.equals("event10")) {
            HomeProducts_ServiceModel.DataBean dataBean = (HomeProducts_ServiceModel.DataBean) object;
            if (dataBean != null) {
                Intent intent = new Intent(activity, ProductDetailActivity.class);
                intent.putExtra("product_id", dataBean.getId());
                startActivity(intent);
                activity.overridePendingTransition(0, 0);
            }
        }
        if (callerIdentity.equals("event11")) { //open drawer
            FragmentDrawer.openDrawer();
        }
        if (callerIdentity.equals("event12")) { ///filter layout
            ///manipulate selected category data
            String categoryIds = PreferenceManager.getDefaultSharedPreferences(activity).getString("selectedcategory", "");
            if (!TextUtils.isEmpty(categoryIds)) {
                List<String> selectedCategoryIdsList = AlphaHolder.arrayStringToArrayList(categoryIds.trim());

                for (int sc = 0; sc < selectedCategoryIdsList.size(); sc++) {
                    String selected_id = selectedCategoryIdsList.get(sc);

                    if (AlphaHolder.isGuestUser(activity)) {
                        for (int c = 0; c < homeFilter_nl_serviceModel.getData().getCategrec().size(); c++) {
                            String filterCategoryId = homeFilter_nl_serviceModel.getData().getCategrec().get(c).getId();
                            if (filterCategoryId.trim().equals(selected_id.trim())) {
                                homeFilter_nl_serviceModel.getData().getCategrec().get(c).setSelected(true);
                            }
                        }
                        if (sc == selectedCategoryIdsList.size() - 1) {
                            bottomSliderDialog.show();
                            categoryBy_recyclerView_nlAdapter.notifyDataSetChanged();

                        }
                    } else {
                        for (int c = 0; c < homeFilter_serviceModel.getData().getCategrec().size(); c++) {
                            String filterCategoryId = homeFilter_serviceModel.getData().getCategrec().get(c).getId();
                            if (filterCategoryId.trim().equals(selected_id.trim())) {
                                homeFilter_serviceModel.getData().getCategrec().get(c).setSelected(true);
                            }
                        }
                        if (sc == selectedCategoryIdsList.size() - 1) {
                            bottomSliderDialog.show();
                            categoryBy_recyclerViewAdapter.notifyDataSetChanged();

                        }
                    }

                }
            } else {
                bottomSliderDialog.show();
            }
        }
        if (callerIdentity.equals("event13")) { //All Filter --remove filter
            PreferenceManager.getDefaultSharedPreferences(activity).edit().putString("selectedcategory", "").apply();
            clearFilter_Guest_Logged_User();

            if (AlphaHolder.isGuestUser(activity))
                getGuestHomeProduct(true);
            else
                getHomeProduct(true);

        }
        if (callerIdentity.equals("event14")) { /// Fashion Filter
            PreferenceManager.getDefaultSharedPreferences(activity).edit().putString("selectedcategory", String.valueOf(topFilter_serviceModel.getData().get(0).getId())).apply();
            if (AlphaHolder.isGuestUser(activity))
                getGuestHomeProduct(true);
            else
                getHomeProduct(true);
        }
        if (callerIdentity.equals("event15")) {  // Home decor filter
            PreferenceManager.getDefaultSharedPreferences(activity).edit().putString("selectedcategory", String.valueOf(topFilter_serviceModel.getData().get(1).getId())).apply();
            if (AlphaHolder.isGuestUser(activity))
                getGuestHomeProduct(true);
            else
                getHomeProduct(true);

        }
        if (callerIdentity.equals("event16")) {  // KDA IMAGE
            Uri uri = Uri.parse("http://www.kdakw.com/"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);

        }
    }

    public void likeProduct(String productId) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", SharedPreferences_Util.getToken(activity));
        paramMap.put("prd_id", productId);

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("PRODUCT_LIKE", paramMap, false, activity);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    public void getHomeTopFilter() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", SharedPreferences_Util.getToken(activity));

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("TOP_FILTER", paramMap, false, activity);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    public void addProductToWishlist(String productId) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", SharedPreferences_Util.getToken(activity));
        paramMap.put("prd_id", productId);

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("PRODUCT_ADD_WISHLIST", paramMap, false, activity);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        if (resultData.getTag().equals("HOME_FILTER")) {
            homeFilter_serviceModel = (HomeFilter_ServiceModel) resultData.getRootData().getValue();

            ///SORT CATEGORIES
            sortBy_recyclerViewAdapter = new SortBy_RecyclerViewAdapter(activity, homeFilter_serviceModel.getData().getSortbyrec(), this::onClick);
            bottomSlideLayoutBinding.sortByRecyclerViewId.setLayoutManager(new GridLayoutManager(activity, 2));
            bottomSlideLayoutBinding.setSortByRecyclerView(sortBy_recyclerViewAdapter);

            ///SORT CATEGORIES
            categoryBy_recyclerViewAdapter = new CategoryBy_RecyclerViewAdapter(activity, homeFilter_serviceModel.getData().getCategrec(), this::onClick);
            bottomSlideLayoutBinding.categoriesRecyclerViewId.setLayoutManager(new GridLayoutManager(activity, 2));
            bottomSlideLayoutBinding.setCategoryRecyclerView(categoryBy_recyclerViewAdapter);

           /* if (isGuestUser()) {
                getGuestHomeProduct();
            } else {
                getHomeProduct();
            }*/
            getHomeProduct(true);
        }
        if (resultData.getTag().equals("HOME_FILTER_NOT_LOGGEDIN")) {
            homeFilter_nl_serviceModel = (HomeFilter_NL_ServiceModel) resultData.getRootData().getValue();
            ///SORT CATEGORIES
            sortBy_recyclerView_nlAdapter = new SortBy_RecyclerView_NLAdapter(activity, homeFilter_nl_serviceModel.getData().getSortbyrec(), this::onClick);
            bottomSlideLayoutBinding.sortByRecyclerViewId.setLayoutManager(new GridLayoutManager(activity, 2));
            bottomSlideLayoutBinding.setSortByRecyclerView(sortBy_recyclerView_nlAdapter);

            ///SORT CATEGORIES
            categoryBy_recyclerView_nlAdapter = new CategoryBy_RecyclerView_NLAdapter(activity, homeFilter_nl_serviceModel.getData().getCategrec(), this::onClick);
            bottomSlideLayoutBinding.categoriesRecyclerViewId.setLayoutManager(new GridLayoutManager(activity, 2));
            bottomSlideLayoutBinding.setCategoryRecyclerView(categoryBy_recyclerView_nlAdapter);

            //getHomeProduct();
            getGuestHomeProduct(true);
        }
        if (resultData.getTag().equals("HOME_PRODUCTS")) {
            homeProducts_serviceModel = (HomeProducts_ServiceModel) resultData.getRootData().getValue();
            if (homeFilter_serviceModel.isStatus().equals("true")) {
                if (homeProducts_serviceModel.getData().size() > 0) {
                    fragmentHomeBMBinding.setIsNoRecord(false);
                    fragmentHomeBMBinding.setKdaFlag(true);
                    homeProducts_serviceModel.getData().add(null);
                    manipulateDataWithRecyclerview(homeProducts_serviceModel.getData());
                } else {
                    fragmentHomeBMBinding.setIsNoRecord(true);
                    fragmentHomeBMBinding.setKdaFlag(false);
                    homeProducts_serviceModel.getData().add(null);
                    manipulateDataWithRecyclerview(homeProducts_serviceModel.getData());


                }
                AlphaHolder.saveEventStatus(homeProducts_serviceModel.getFree_ads(), "FREE_ADS", activity);
                if (!AlphaHolder.isFreeAdsDialog) {
                    AlphaHolder.isFreeAdsDialog = true;
                    welcomeGift_Dialog(homeProducts_serviceModel.getFree_ads());
                }

            } else {
                AlphaHolder.customToast(activity, homeFilter_nl_serviceModel.getMessage());
            }
            getHomeTopFilter();
        }
        if (resultData.getTag().equals("GUEST_HOME_PRODUCTS")) {
            homeProducts_serviceModel = (HomeProducts_ServiceModel) resultData.getRootData().getValue();
            if (homeProducts_serviceModel.getData().size() > 0) {
                fragmentHomeBMBinding.setIsNoRecord(false);
                homeProducts_serviceModel.getData().add(null);
                manipulateDataWithRecyclerview(homeProducts_serviceModel.getData());

            } else {
                fragmentHomeBMBinding.setIsNoRecord(true);
                homeProducts_serviceModel.getData().add(null);
                manipulateDataWithRecyclerview(homeProducts_serviceModel.getData());

            }
            getHomeTopFilter();
        }
        if (resultData.getTag().equals("PRODUCT_LIKE")) {
            productLike_serviceModel = (ProductLike_ServiceModel) resultData.getRootData().getValue();
            if (productLike_serviceModel.isStatus().equals("true")) {
                if (homeProducts_serviceModel.getData().get(clickedLike).getLikeornot().equals("Like")) {
                    homeProducts_serviceModel.getData().get(clickedLike).setLikeornot("Not Liked");
                    manipulaTotalLike(false);
                } else {
                    homeProducts_serviceModel.getData().get(clickedLike).setLikeornot("Like");
                    manipulaTotalLike(true);
                }
            } else {
                AlphaHolder.customToast(activity, productLike_serviceModel.getMessage());
            }
            homeProducts_recyclerViewAdapter.notifyItemChanged(clickedLike);
        }
        if (resultData.getTag().equals("PRODUCT_ADD_WISHLIST")) {
            product_add_wiLit = (Product_Add_WiLit) resultData.getRootData().getValue();
            if (product_add_wiLit.isStatus().equals("true")) {
                if (homeProducts_serviceModel.getData().get(clickedWish).getWishstatus().equals("Wished")) {
                    homeProducts_serviceModel.getData().get(clickedWish).setWishstatus("Not Wished");
                } else {
                    homeProducts_serviceModel.getData().get(clickedWish).setWishstatus("Wished");
                }

            } else {
                AlphaHolder.customToast(activity, product_add_wiLit.getMessage());
            }
            homeProducts_recyclerViewAdapter.notifyItemChanged(clickedWish);
        }
        if (resultData.getTag().equals("TOP_FILTER")) {
            topFilter_serviceModel = (TopFilter_ServiceModel) resultData.getRootData().getValue();
            if (topFilter_serviceModel.getData().size() == 2) {
                fragmentHomeBMBinding.setCategotyOne(topFilter_serviceModel.getData().get(0).getSlug());
                fragmentHomeBMBinding.setCategoryTwo(topFilter_serviceModel.getData().get(1).getSlug());
            }
        }
    }

    private void manipulaTotalLike(boolean isPlus) {
        try {
            String toLikeString = homeProducts_serviceModel.getData().get(clickedLike).getTotal_like();
            if (toLikeString != null && !toLikeString.equals("")) {
                int totatLikeInt = Integer.parseInt(toLikeString);

                if (isPlus)
                    totatLikeInt++;
                else
                    totatLikeInt--;

                homeProducts_serviceModel.getData().get(clickedLike).setTotal_like(String.valueOf(totatLikeInt));
            }
            Log.e("LIKECOMING", "YES");

        } catch (Exception EE) {

        }
    }

    public void getHomeFilter() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", SharedPreferences_Util.getToken(activity));

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("HOME_FILTER", paramMap, true, activity);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    public void getHomeFilterNotLoggedIn() {
        Map<String, String> paramMap = new HashMap<>();
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("HOME_FILTER_NOT_LOGGEDIN", paramMap, true, activity);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    public void getHomeProduct(boolean showLoader) {
        String categoryIds = PreferenceManager.getDefaultSharedPreferences(activity).getString("selectedcategory", "");
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", SharedPreferences_Util.getToken(activity));
        paramMap.put("categories_id", categoryIds.replace("[", "").replace("]", ""));
        //paramMap.put("nearestme", homeFilter_serviceModel.getData().getSortbyrec().get(0).isSelected() ? "nearestme" : "");
        paramMap.put("recentlyadded", homeFilter_serviceModel.getData().getSortbyrec().get(1).isSelected() ? "recentlyadded" : "");
        paramMap.put("wishlist", homeFilter_serviceModel.getData().getSortbyrec().get(2).isSelected() ? "wishlist" : "");
        paramMap.put("mosttrending", homeFilter_serviceModel.getData().getSortbyrec().get(3).isSelected() ? "mosttrending" : "");
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("HOME_PRODUCTS", paramMap, showLoader, activity);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    public void getGuestHomeProduct(boolean showLoader) {
        String categoryIds = PreferenceManager.getDefaultSharedPreferences(activity).getString("selectedcategory", "");
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("categories_id", categoryIds.toString().replace("[", "").replace("]", ""));
        //paramMap.put("nearestme", homeFilter_nl_serviceModel.getData().getSortbyrec().get(0).isSelected() ? "nearestme" : "");
        paramMap.put("recentlyadded", homeFilter_nl_serviceModel.getData().getSortbyrec().get(1).isSelected() ? "recentlyadded" : "");
        paramMap.put("wishlist", homeFilter_nl_serviceModel.getData().getSortbyrec().get(2).isSelected() ? "wishlist" : "");
        paramMap.put("mosttrending", homeFilter_nl_serviceModel.getData().getSortbyrec().get(3).isSelected() ? "mosttrending" : "");
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("GUEST_HOME_PRODUCTS", paramMap, showLoader, activity);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    public void manipulateDataWithRecyclerview(List<HomeProducts_ServiceModel.DataBean> dataBeans) {
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == dataBeans.size() - 1 ? gridLayoutManager.getSpanCount() : 1;
            }
        });

        fragmentHomeBMBinding.productsRecyclerViewId.setLayoutManager(gridLayoutManager);
        homeProducts_recyclerViewAdapter = new HomeProducts_RecyclerViewAdapter(activity, dataBeans, this::onClick);
        fragmentHomeBMBinding.productsRecyclerViewId.setLayoutManager(gridLayoutManager);
        fragmentHomeBMBinding.productsRecyclerViewId.setAdapter(homeProducts_recyclerViewAdapter);
    }
}