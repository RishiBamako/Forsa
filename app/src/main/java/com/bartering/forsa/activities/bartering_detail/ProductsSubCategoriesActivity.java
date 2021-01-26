package com.bartering.forsa.activities.bartering_detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bartering.forsa.AppCompactActivity;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.activities.MainActivity;
import com.bartering.forsa.databinding.ActivityProductSubcategoryBinding;
import com.bartering.forsa.recyclerViewAdapter.ProductSubCategories_RecyclerViewAdapter;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.Comman_ServiceModel;
import com.bartering.forsa.retrofit.service_model.ProductDetails_ServiceModel;
import com.bartering.forsa.retrofit.service_model.SubCategoriesData_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.SharedPreferences_Util;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class ProductsSubCategoriesActivity extends AppCompactActivity implements ClickListener, Observer<Object> {

    ActivityProductSubcategoryBinding activityProductSubcategoryBinding;
    ProductSubCategories_RecyclerViewAdapter productSubCategories_recyclerViewAdapter;
    String category_id, product_id;

    SubCategoriesData_ServiceModel subCategoriesData_serviceModel;

    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;

    SubCategoriesData_ServiceModel.DataBean subCategoryData;

    //String from;
    ProductDetails_ServiceModel.DataBean.CategoryBean categoryData;
    ProductDetails_ServiceModel.DataBean.SubcategoryBean subcategoryData_edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProductSubcategoryBinding = DataBindingUtil.setContentView(this, R.layout.activity_product_subcategory);
        getCategoryId();
        listener();
    }

    private void getCategoryData() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", SharedPreferences_Util.getToken(ProductsSubCategoriesActivity.this));
        paramMap.put("cat_id", category_id);
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("PRODUCT_SUB_CATEGORY", paramMap, true, ProductsSubCategoriesActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    private void getCategoryId() {
        if (getIntent().getExtras() != null) {
           // from = getIntent().getExtras().getString("FROM");
            category_id = getIntent().getExtras().getString("category_id");
            product_id = getIntent().getExtras().getString("product_id");

          /*  if (from.equals("EDIT_PRODUCT")) {
                categoryData = (ProductDetails_ServiceModel.DataBean.CategoryBean) getIntent().getSerializableExtra("CATEGORY_DATE");
                subcategoryData_edit = (ProductDetails_ServiceModel.DataBean.SubcategoryBean) getIntent().getSerializableExtra("SUB_CATEGORY_DATE");
            }*/
        }
        getCategoryData();

    }

    public void backClick(View view) {
        this.finish();
    }


    private void listener() {
        activityProductSubcategoryBinding.backLLId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductsSubCategoriesActivity.this.finish();
            }
        });
    }

    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        if (resultData.getTag().equals("PRODUCT_SUB_CATEGORY")) {
            subCategoriesData_serviceModel = (SubCategoriesData_ServiceModel) resultData.getRootData().getValue();
            productSubCategories_recyclerViewAdapter = new ProductSubCategories_RecyclerViewAdapter(ProductsSubCategoriesActivity.this, subCategoriesData_serviceModel.getData(), this);
            activityProductSubcategoryBinding.setProductData(productSubCategories_recyclerViewAdapter);

            /*if (from.equals("EDIT_PRODUCT")) {
                String id = subcategoryData_edit.getSubcat_id();
                for (int pc = 0; pc < subCategoriesData_serviceModel.getData().size(); pc++) {
                    String idExists = subCategoriesData_serviceModel.getData().get(pc).getSubcat_id();
                    if (idExists.equals(id))
                        subCategoriesData_serviceModel.getData().get(pc).setSelected(true);
                    else
                        subCategoriesData_serviceModel.getData().get(pc).setSelected(false);

                    if (pc == subCategoriesData_serviceModel.getData().size() - 1) {
                        productSubCategories_recyclerViewAdapter.notifyDataSetChanged();
                    }
                }
            }*/
        }
        if (resultData.getTag().equals("ADD_SUB_CATEGORY")) {
            Comman_ServiceModel comman_serviceModel = (Comman_ServiceModel) resultData.getRootData().getValue();
            if (comman_serviceModel.isStatus().equals("true")) {

                ProductDetail_UploadActivity.paramOptimizer_viewModel.setProductSubCategory(new MutableLiveData<>(subCategoryData.getSubcat_name()));
                ProductDetail_UploadActivity.paramOptimizer_viewModel.setProductSubCategoryId(new MutableLiveData<>(subCategoryData.getSubcat_id()));

             /*   if (from.equals("EDIT_PRODUCT")) {
                    Intent intent = new Intent(ProductsSubCategoriesActivity.this, EditProductActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                } else {
                    Intent intent = new Intent(ProductsSubCategoriesActivity.this, ProductOverViewActivity.class);
                    intent.putExtra("product_id", product_id);
                    intent.putExtra("category_id", category_id);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }*/
                Intent intent = new Intent(ProductsSubCategoriesActivity.this, ProductOverViewActivity.class);
                intent.putExtra("product_id", product_id);
                intent.putExtra("category_id", category_id);
                startActivity(intent);
                overridePendingTransition(0, 0);

            }
            AlphaHolder.customToast(ProductsSubCategoriesActivity.this, comman_serviceModel.getMessage());

        }
    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if ("event1".equals(callerIdentity)) {
            subCategoryData = (SubCategoriesData_ServiceModel.DataBean) object;
            addSubCategory();
        }
    }

    private void addSubCategory() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", SharedPreferences_Util.getToken(ProductsSubCategoriesActivity.this));
        paramMap.put("subcat_id", subCategoryData.getSubcat_id());
        paramMap.put("prd_id", product_id);
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("ADD_SUB_CATEGORY", paramMap, true, ProductsSubCategoriesActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }
}