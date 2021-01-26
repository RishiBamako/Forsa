package com.bartering.forsa.recyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.CategorybyNllayoutBinding;
import com.bartering.forsa.retrofit.service_model.HomeFilter_NL_ServiceModel;

import java.util.List;

public class CategoryBy_RecyclerView_NLAdapter extends RecyclerView.Adapter<CategoryBy_RecyclerView_NLAdapter.CustomViewHolder> {

    Context context;
    ClickListener clickListener;
    List<HomeFilter_NL_ServiceModel.DataBean.CategrecBean> categoryDataArrayList;

    public CategoryBy_RecyclerView_NLAdapter(Context context, List<HomeFilter_NL_ServiceModel.DataBean.CategrecBean> categoryDataArrayList, ClickListener clickListener) {
        this.context = context;
        this.categoryDataArrayList = categoryDataArrayList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CategorybyNllayoutBinding categorybyLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.categoryby_nllayout, parent, false);
        return new CustomViewHolder(categorybyLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        HomeFilter_NL_ServiceModel.DataBean.CategrecBean data = (HomeFilter_NL_ServiceModel.DataBean.CategrecBean) getItem(position);
        holder.notifyViewBinding(data, holder, position);

    }

    @Override
    public int getItemCount() {
        return categoryDataArrayList.size();
    }

    public Object getItem(int position) {
        return categoryDataArrayList.get(position);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements ClickListener {
        CategorybyNllayoutBinding categorybyLayoutBinding;

        public CustomViewHolder(CategorybyNllayoutBinding categorybyLayoutBinding) {
            super(categorybyLayoutBinding.getRoot());
            this.categorybyLayoutBinding = categorybyLayoutBinding;
        }

        public void notifyViewBinding(HomeFilter_NL_ServiceModel.DataBean.CategrecBean sortbyrecBean, CustomViewHolder customViewHolder, int position) {
            customViewHolder.categorybyLayoutBinding.setData(sortbyrecBean);
            customViewHolder.categorybyLayoutBinding.setPosition(position);
            customViewHolder.categorybyLayoutBinding.setClickListener(this::onClick);
        }

        @Override
        public void onClick(int position, Object object, String callerIdentity) {
            clickListener.onClick(position, object, callerIdentity);
        }
    }
}
