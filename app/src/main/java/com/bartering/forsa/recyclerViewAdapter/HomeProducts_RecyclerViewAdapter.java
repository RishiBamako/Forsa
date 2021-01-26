package com.bartering.forsa.recyclerViewAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.HomeProductsLayoutBinding;
import com.bartering.forsa.retrofit.service_model.HomeProducts_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;

import java.util.List;

public class HomeProducts_RecyclerViewAdapter extends RecyclerView.Adapter<HomeProducts_RecyclerViewAdapter.CustomViewHolder> {

    Context context;
    List<HomeProducts_ServiceModel.DataBean> categoryDataArrayList;
    ClickListener clickListener;

    public HomeProducts_RecyclerViewAdapter(Context context, List<HomeProducts_ServiceModel.DataBean> categoryDataArrayList, ClickListener clickListener) {
        this.context = context;
        this.categoryDataArrayList = categoryDataArrayList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HomeProductsLayoutBinding homeProductsLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.home_products_layout, parent, false);
        return new CustomViewHolder(homeProductsLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        HomeProducts_ServiceModel.DataBean dataBean = (HomeProducts_ServiceModel.DataBean) getItem(position);
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
        HomeProductsLayoutBinding homeProductsLayoutBinding;

        public CustomViewHolder(HomeProductsLayoutBinding homeProductsLayoutBinding) {
            super(homeProductsLayoutBinding.getRoot());
            this.homeProductsLayoutBinding = homeProductsLayoutBinding;
        }

        public void notifyViewBinding(HomeProducts_ServiceModel.DataBean dataBean, CustomViewHolder customViewHolder, int position) {
            this.homeProductsLayoutBinding.setClickListener(this::onClick);
            this.homeProductsLayoutBinding.setPosition(position);
            this.homeProductsLayoutBinding.setData(dataBean);
            this.homeProductsLayoutBinding.setIsGuestUser(AlphaHolder.isGuestUser((Activity) context));

        }

        @Override
        public void onClick(int position, Object object, String callerIdentity) {
            clickListener.onClick(position, object, callerIdentity);
        }
    }
}
