package com.bartering.forsa.recyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.ProductSubcategoriesLayoutBinding;
import com.bartering.forsa.retrofit.service_model.SubCategoriesData_ServiceModel;

import java.util.List;

public class ProductSubCategories_RecyclerViewAdapter extends RecyclerView.Adapter<ProductSubCategories_RecyclerViewAdapter.CustomViewHolder> {

    Context context;
    List<SubCategoriesData_ServiceModel.DataBean> categoryDataArrayList;
    ClickListener clickListener;

    public ProductSubCategories_RecyclerViewAdapter(Context context, List<SubCategoriesData_ServiceModel.DataBean> categoryDataArrayList, ClickListener clickListener) {
        this.context = context;
        this.categoryDataArrayList = categoryDataArrayList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductSubcategoriesLayoutBinding productSubcategoriesLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.product_subcategories_layout, parent, false);
        return new CustomViewHolder(productSubcategoriesLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        SubCategoriesData_ServiceModel.DataBean dataBean = (SubCategoriesData_ServiceModel.DataBean) getItem(position);
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
        ProductSubcategoriesLayoutBinding productSubcategoriesLayoutBinding;

        public CustomViewHolder(ProductSubcategoriesLayoutBinding productSubcategoriesLayoutBinding) {
            super(productSubcategoriesLayoutBinding.getRoot());
            this.productSubcategoriesLayoutBinding = productSubcategoriesLayoutBinding;
        }

        public void notifyViewBinding(SubCategoriesData_ServiceModel.DataBean dataBean, CustomViewHolder customViewHolder, int position) {
            customViewHolder.productSubcategoriesLayoutBinding.setClickListener(this::onClick);
            customViewHolder.productSubcategoriesLayoutBinding.setPosition(position);
            customViewHolder.productSubcategoriesLayoutBinding.setData(dataBean);

        }

        @Override
        public void onClick(int position, Object object, String callerIdentity) {
            clickListener.onClick(position,object,callerIdentity);
        }
    }
}
