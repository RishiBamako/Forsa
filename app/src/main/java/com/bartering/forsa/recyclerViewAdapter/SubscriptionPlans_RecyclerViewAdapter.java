package com.bartering.forsa.recyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.ProductDescriptionHeadLayoutBinding;
import com.bartering.forsa.retrofit.service_model.SubscriptionPackages_ServiceModel;

import java.util.List;

public class SubscriptionPlans_RecyclerViewAdapter extends RecyclerView.Adapter<SubscriptionPlans_RecyclerViewAdapter.CustomViewHolder> {

    Context context;
    List<SubscriptionPackages_ServiceModel.DataBean> categoryDataArrayList;
    ClickListener clickListener;

    public SubscriptionPlans_RecyclerViewAdapter(Context context, List<SubscriptionPackages_ServiceModel.DataBean> categoryDataArrayList, ClickListener clickListener) {
        this.context = context;
        this.categoryDataArrayList = categoryDataArrayList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductDescriptionHeadLayoutBinding productDescriptionHeadLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.product_description_head_layout, parent, false);
        return new CustomViewHolder(productDescriptionHeadLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        SubscriptionPackages_ServiceModel.DataBean dataBean = (SubscriptionPackages_ServiceModel.DataBean) getItem(position);
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
        ProductDescriptionHeadLayoutBinding productDescriptionHeadLayoutBinding;

        public CustomViewHolder(ProductDescriptionHeadLayoutBinding productDescriptionHeadLayoutBinding) {
            super(productDescriptionHeadLayoutBinding.getRoot());
            this.productDescriptionHeadLayoutBinding = productDescriptionHeadLayoutBinding;
        }

        public void notifyViewBinding(SubscriptionPackages_ServiceModel.DataBean dataBean, CustomViewHolder customViewHolder, int position) {
            customViewHolder.productDescriptionHeadLayoutBinding.setData(dataBean);
            customViewHolder.productDescriptionHeadLayoutBinding.setPosition(position);
            customViewHolder.productDescriptionHeadLayoutBinding.setClickListener(this::onClick);

        }

        @Override
        public void onClick(int position, Object object, String callerIdentity) {
            clickListener.onClick(position, object, callerIdentity);
        }
    }
}
