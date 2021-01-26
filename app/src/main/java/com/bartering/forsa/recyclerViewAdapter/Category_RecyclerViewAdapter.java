package com.bartering.forsa.recyclerViewAdapter;

import android.content.Context;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.CategoryLayoutBinding;
import com.bartering.forsa.retrofit.service_model.CategoriesData_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;

import java.util.List;

public class Category_RecyclerViewAdapter extends RecyclerView.Adapter<Category_RecyclerViewAdapter.CustomViewHolder> {

    Context context;
    List<CategoriesData_ServiceModel.DataBean> categoryDataArrayList;
    ClickListener clickListener;

    public Category_RecyclerViewAdapter(Context context, List<CategoriesData_ServiceModel.DataBean> categoryDataArrayList, ClickListener clickListener) {
        this.context = context;
        this.categoryDataArrayList = categoryDataArrayList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CategoryLayoutBinding categoryLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.category_layout, parent, false);
        return new CustomViewHolder(categoryLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        CategoriesData_ServiceModel.DataBean dataBean = (CategoriesData_ServiceModel.DataBean) getItem(position);
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
        CategoryLayoutBinding categoryLayoutBinding;

        public CustomViewHolder(CategoryLayoutBinding categoryLayoutBinding) {
            super(categoryLayoutBinding.getRoot());
            this.categoryLayoutBinding = categoryLayoutBinding;
        }

        public void notifyViewBinding(CategoriesData_ServiceModel.DataBean dataBean, CustomViewHolder customViewHolder, int position) {
            customViewHolder.categoryLayoutBinding.setPosition(position);
            customViewHolder.categoryLayoutBinding.setData(dataBean);
            customViewHolder.categoryLayoutBinding.setClickListener(this::onClick);
        }

        @Override
        public void onClick(int position, Object object, String callerIdentity) {
            clickListener.onClick(position, object, callerIdentity);
        }
    }
}
