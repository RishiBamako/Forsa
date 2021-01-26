package com.bartering.forsa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bartering.forsa.AppCompactActivity;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.ActivityChooseCategoryBinding;
import com.bartering.forsa.recyclerViewAdapter.Category_RecyclerViewAdapter;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.CategoriesData_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.SharedPreferences_Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class ChooseCategoryActivity extends AppCompactActivity implements Observer<Object>, ClickListener {

    ActivityChooseCategoryBinding activityChooseCategoryBinding;

    ///Service Part

    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;

    CategoriesData_ServiceModel categoriesData_serviceModel;

    Category_RecyclerViewAdapter category_recyclerViewAdapter;
    List<String> selectedIdsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_category);
        activityChooseCategoryBinding = DataBindingUtil.setContentView(this, R.layout.activity_choose_category);
        activityChooseCategoryBinding.setClickListener(this::onClick);
        activityChooseCategoryBinding.setIsNoRecord(false);
        AlphaHolder.stackList.add(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        serviceLoadCategories();
    }

    //////SERVICE PART
    private void serviceLoadCategories() {
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        Map<String, String> param = new HashMap<>();
        param.put("token", SharedPreferences_Util.getToken(ChooseCategoryActivity.this));
        viewModel.loadData("CATEGORIES", param, true, ChooseCategoryActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    public void apply_skip(View view) {
        Intent intent = new Intent(ChooseCategoryActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void backPressed(View view) {
        this.finish();
    }

    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        if (resultData.getTag().equals("CATEGORIES")) {
            categoriesData_serviceModel = (CategoriesData_ServiceModel) resultData.getRootData().getValue();
            if (null != categoriesData_serviceModel.getData() && categoriesData_serviceModel.getData().size() > 0) {
                setActiveCategories();
                activityChooseCategoryBinding.setIsNoRecord(false);
                category_recyclerViewAdapter = new Category_RecyclerViewAdapter(ChooseCategoryActivity.this, categoriesData_serviceModel.getData(), this::onClick);
                activityChooseCategoryBinding.categoryRecyclerViewId.setHasFixedSize(true);
                activityChooseCategoryBinding.categoryRecyclerViewId.setLayoutManager(new GridLayoutManager(ChooseCategoryActivity.this, 3));
                activityChooseCategoryBinding.categoryRecyclerViewId.setAdapter(category_recyclerViewAdapter);
            } else {
                activityChooseCategoryBinding.setIsNoRecord(true);
            }
        }
    }

    public void setActiveCategories() {
        String categoryIds = PreferenceManager.getDefaultSharedPreferences(ChooseCategoryActivity.this).getString("selectedcategory", "");
        if (!TextUtils.isEmpty(categoryIds)) {
            List<String> selectedCategoryIdsList = AlphaHolder.arrayStringToArrayList(categoryIds.trim());
            for (int i = 0; i < categoriesData_serviceModel.getData().size(); i++) {
                if (selectedCategoryIdsList.contains(categoriesData_serviceModel.getData().get(i).getId().trim())) {
                    categoriesData_serviceModel.getData().get(i).setSelected(true);
                }
            }
        }
    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event1")) {
            CategoriesData_ServiceModel.DataBean dataBean = (CategoriesData_ServiceModel.DataBean) object;
            categoriesData_serviceModel.getData().get(position).setSelected(dataBean.isSelected() ? false : true);
            category_recyclerViewAdapter.notifyItemChanged(position);

        }
        if (callerIdentity.equals("event2")) {
            selectedIdsList = new ArrayList<>();
            try {
                for (int i = 0; i < categoriesData_serviceModel.getData().size(); i++) {
                    if (categoriesData_serviceModel.getData().get(i).isSelected())
                        selectedIdsList.add(categoriesData_serviceModel.getData().get(i).getId().trim());
                    if (i == categoriesData_serviceModel.getData().size() - 1) {
                        PreferenceManager.getDefaultSharedPreferences(ChooseCategoryActivity.this).edit().putString("selectedcategory", String.valueOf(selectedIdsList)).apply();

                        Intent intent = new Intent(ChooseCategoryActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                    }
                }
            } catch (Exception EE) {

            }
        }
    }
}