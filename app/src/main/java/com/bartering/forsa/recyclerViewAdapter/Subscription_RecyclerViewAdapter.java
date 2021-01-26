package com.bartering.forsa.recyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bartering.forsa.R;
import com.bartering.forsa.databinding.SubscriptionLayoutBinding;
import com.bartering.forsa.retrofit.service_model.SubscriptionPackage_ServiceModel;

import java.util.List;

public class Subscription_RecyclerViewAdapter extends RecyclerView.Adapter<Subscription_RecyclerViewAdapter.CustomViewHolder> {

    Context context;
    List<SubscriptionPackage_ServiceModel.DataBean> categoryDataArrayList;

    public Subscription_RecyclerViewAdapter(Context context, List<SubscriptionPackage_ServiceModel.DataBean> categoryDataArrayList) {
        this.context = context;
        this.categoryDataArrayList = categoryDataArrayList;

    }
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SubscriptionLayoutBinding subscriptionLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.subscription_layout, parent, false);
        return new CustomViewHolder(subscriptionLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        SubscriptionPackage_ServiceModel.DataBean dataBean = (SubscriptionPackage_ServiceModel.DataBean) getItem(position);
        holder.notifyViewBinding(dataBean, holder, position);

    }

    @Override
    public int getItemCount() {
        return categoryDataArrayList.size();
    }

    public Object getItem(int position) {
        return categoryDataArrayList.get(position);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        SubscriptionLayoutBinding subscriptionLayoutBinding;

        public CustomViewHolder(SubscriptionLayoutBinding subscriptionLayoutBinding) {
            super(subscriptionLayoutBinding.getRoot());
            this.subscriptionLayoutBinding = subscriptionLayoutBinding;
        }

        public void notifyViewBinding(SubscriptionPackage_ServiceModel.DataBean dataBean, CustomViewHolder customViewHolder, int position) {
            customViewHolder.subscriptionLayoutBinding.setData(dataBean);

        }
    }
}
