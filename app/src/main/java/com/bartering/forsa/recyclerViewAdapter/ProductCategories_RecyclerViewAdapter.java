package com.bartering.forsa.recyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.ProductCategoriesLayoutBinding;
import com.bartering.forsa.retrofit.service_model.ProductCategory_ServiceModel;

import java.util.List;

public class ProductCategories_RecyclerViewAdapter extends RecyclerView.Adapter<ProductCategories_RecyclerViewAdapter.CustomViewHolder> {

    Context context;
    List<ProductCategory_ServiceModel.DataBean> categoryDataArrayList;
    ClickListener clickListener;

    public ProductCategories_RecyclerViewAdapter(Context context, List<ProductCategory_ServiceModel.DataBean> categoryDataArrayList, ClickListener clickListener) {
        this.context = context;
        this.categoryDataArrayList = categoryDataArrayList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductCategoriesLayoutBinding productCategoriesLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.product_categories_layout, parent, false);
        return new CustomViewHolder(productCategoriesLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        ProductCategory_ServiceModel.DataBean dataBean = (ProductCategory_ServiceModel.DataBean) getItem(position);
        holder.notifyViewBinding(dataBean, holder, position);

    }

    @Override
    public int getItemCount() {
        return categoryDataArrayList.size();
    }

    public Object getItem(int position) {
        return categoryDataArrayList.get(position);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements ClickListener {
        ProductCategoriesLayoutBinding productCategoriesLayoutBinding;

        public CustomViewHolder(ProductCategoriesLayoutBinding productCategoriesLayoutBinding) {
            super(productCategoriesLayoutBinding.getRoot());
            this.productCategoriesLayoutBinding = productCategoriesLayoutBinding;
        }

        public void notifyViewBinding(ProductCategory_ServiceModel.DataBean dataBean, CustomViewHolder customViewHolder, int position) {
            customViewHolder.productCategoriesLayoutBinding.setData(dataBean);
            customViewHolder.productCategoriesLayoutBinding.setClickListener(this::onClick);
            customViewHolder.productCategoriesLayoutBinding.setPosition(position);

        }

        @Override
        public void onClick(int position, Object object, String callerIdentity) {
            clickListener.onClick(position, object, callerIdentity);
        }
    }

}
