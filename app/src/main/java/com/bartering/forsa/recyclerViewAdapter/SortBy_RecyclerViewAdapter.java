package com.bartering.forsa.recyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.SortbyLayoutBinding;
import com.bartering.forsa.retrofit.service_model.HomeFilter_ServiceModel;

import java.util.List;

public class SortBy_RecyclerViewAdapter extends RecyclerView.Adapter<SortBy_RecyclerViewAdapter.CustomViewHolder> {

    Context context;
    ClickListener clickListener;
    List<HomeFilter_ServiceModel.DataBean.SortbyrecBean> categoryDataArrayList;

    public SortBy_RecyclerViewAdapter(Context context, List<HomeFilter_ServiceModel.DataBean.SortbyrecBean> categoryDataArrayList, ClickListener clickListener) {
        this.context = context;
        this.categoryDataArrayList = categoryDataArrayList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SortbyLayoutBinding sortbyLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.sortby_layout, parent, false);
        return new CustomViewHolder(sortbyLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        HomeFilter_ServiceModel.DataBean.SortbyrecBean data = (HomeFilter_ServiceModel.DataBean.SortbyrecBean) getItem(position);
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
        SortbyLayoutBinding sortbyLayoutBinding;

        public CustomViewHolder(SortbyLayoutBinding sortbyLayoutBinding) {
            super(sortbyLayoutBinding.getRoot());
            this.sortbyLayoutBinding = sortbyLayoutBinding;
        }

        public void notifyViewBinding(HomeFilter_ServiceModel.DataBean.SortbyrecBean sortbyrecBean, CustomViewHolder customViewHolder, int position) {
            customViewHolder.sortbyLayoutBinding.setData(sortbyrecBean);
            customViewHolder.sortbyLayoutBinding.setPosition(position);
            customViewHolder.sortbyLayoutBinding.setClickListener(this::onClick);
        }

        @Override
        public void onClick(int position, Object object, String callerIdentity) {
            clickListener.onClick(position, object, callerIdentity);
        }
    }
}
