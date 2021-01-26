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
import com.bartering.forsa.databinding.ActivityProductCategoryBinding;
import com.bartering.forsa.recyclerViewAdapter.ProductCategories_RecyclerViewAdapter;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.Comman_ServiceModel;
import com.bartering.forsa.retrofit.service_model.ProductCategory_ServiceModel;
import com.bartering.forsa.retrofit.service_model.ProductDetails_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.SharedPreferences_Util;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class ProductsCategoriesActivity extends AppCompactActivity implements ClickListener, Observer<Object> {

    ActivityProductCategoryBinding activityProductCategoryBinding;
    ProductCategories_RecyclerViewAdapter productCategories_recyclerViewAdapter;

    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;

    ProductCategory_ServiceModel productCategory_serviceModel;
    Comman_ServiceModel comman_serviceModel;
    String product_id, category_id;
    ProductCategory_ServiceModel.DataBean categoryObject;

    String from;
    ProductDetails_ServiceModel.DataBean.CategoryBean categoryData;
    ProductDetails_ServiceModel.DataBean.SubcategoryBean subcategoryData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProductCategoryBinding = DataBindingUtil.setContentView(this, R.layout.activity_product_category);
        listener();
        getProductId();
    }

    private void getProductId() {
        if (getIntent().getExtras() != null) {
            from = getIntent().getExtras().getString("FROM");
            product_id = getIntent().getExtras().getString("product_id");

            /*if (from.equals("EDIT_PRODUCT")) {
                categoryData = (ProductDetails_ServiceModel.DataBean.CategoryBean) getIntent().getSerializableExtra("CATEGORY_DATE");
                subcategoryData = (ProductDetails_ServiceModel.DataBean.SubcategoryBean) getIntent().getSerializableExtra("SUB_CATEGORY_DATE");
                category_id = categoryData.getCat_id();
            }*/
            getCategoryData();
        }
    }

    private void getCategoryData() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", SharedPreferences_Util.getToken(ProductsCategoriesActivity.this));
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("PRODUCT_CATEGORY", paramMap, true, ProductsCategoriesActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    private void listener() {
        activityProductCategoryBinding.backLLId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductsCategoriesActivity.this.finish();
            }
        });
    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event2")) {
            categoryObject = (ProductCategory_ServiceModel.DataBean) object;
            /*if (from.equals("EDIT_PRODUCT")) {
                categoryData.setCat_id(categoryObject.getId());
                categoryData.setCat_name(categoryObject.getCat_name());
            }

            for (int pc = 0; pc < productCategory_serviceModel.getData().size(); pc++) {
                String idExists = productCategory_serviceModel.getData().get(pc).getId();
                if (pc==position)
                    productCategory_serviceModel.getData().get(pc).setSelected(true);
                else
                    productCategory_serviceModel.getData().get(pc).setSelected(false);

                if (pc == productCategory_serviceModel.getData().size() - 1) {
                    productCategories_recyclerViewAdapter.notifyDataSetChanged();
                }
            }*/

            category_id = categoryObject.getId();
            addCategoryData();
        }
    }

    private void addCategoryData() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", SharedPreferences_Util.getToken(ProductsCategoriesActivity.this));
        paramMap.put("cat_id", category_id); ///// set data here for get data in last which one is selected
        paramMap.put("prd_id", product_id);

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("ADD_CATEGORY", paramMap, true, ProductsCategoriesActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }
    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        if (resultData.getTag().equals("PRODUCT_CATEGORY")) {
            productCategory_serviceModel = (ProductCategory_ServiceModel) resultData.getRootData().getValue();
            productCategories_recyclerViewAdapter = new ProductCategories_RecyclerViewAdapter(ProductsCategoriesActivity.this, productCategory_serviceModel.getData(), this);
            activityProductCategoryBinding.setProductCategoryData(productCategories_recyclerViewAdapter);

            /*if (from.equals("EDIT_PRODUCT")) {
                String id = categoryData.getCat_id();
                for (int pc = 0; pc < productCategory_serviceModel.getData().size(); pc++) {
                    String idExists = productCategory_serviceModel.getData().get(pc).getId();
                    if (idExists.equals(id))
                        productCategory_serviceModel.getData().get(pc).setSelected(true);
                    else
                        productCategory_serviceModel.getData().get(pc).setSelected(false);

                    if (pc == productCategory_serviceModel.getData().size() - 1) {
                        productCategories_recyclerViewAdapter.notifyDataSetChanged();
                    }
                }
            }*/

        }
        if (resultData.getTag().equals("ADD_CATEGORY")) {
            comman_serviceModel = (Comman_ServiceModel) resultData.getRootData().getValue();
            if (comman_serviceModel.isStatus().equals("true")) {

                ProductDetail_UploadActivity.paramOptimizer_viewModel.setProductCategory(new MutableLiveData<>(categoryObject.getCat_name()));
                ProductDetail_UploadActivity.paramOptimizer_viewModel.setProductCategoryId(new MutableLiveData<>(categoryObject.getId()));

                Intent intent = new Intent(ProductsCategoriesActivity.this, ProductsSubCategoriesActivity.class);
                intent.putExtra("FROM", from);
                intent.putExtra("category_id", category_id);
                intent.putExtra("product_id", product_id);

               /* if (from.equals("EDIT_PRODUCT")) {
                    if (categoryData != null)
                        intent.putExtra("CATEGORY_DATE", categoryData);
                    if (subcategoryData != null)
                        intent.putExtra("SUB_CATEGORY_DATE", subcategoryData);
                }
*/
                startActivity(intent);
                overridePendingTransition(0, 0);

            }
            AlphaHolder.customToast(ProductsCategoriesActivity.this, comman_serviceModel.getMessage());
        }
    }
}