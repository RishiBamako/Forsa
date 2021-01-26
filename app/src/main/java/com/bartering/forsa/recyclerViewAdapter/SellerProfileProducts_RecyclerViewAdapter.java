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
import com.bartering.forsa.databinding.SellerProfileProductsLayoutBinding;
import com.bartering.forsa.retrofit.service_model.SellerProfile_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;

import java.util.List;

public class SellerProfileProducts_RecyclerViewAdapter extends RecyclerView.Adapter<SellerProfileProducts_RecyclerViewAdapter.CustomViewHolder> {

    Context context;
    List<SellerProfile_ServiceModel.DataBean.PrdpecordBean> mainDataArrayList;
    ClickListener clickListener;

    public SellerProfileProducts_RecyclerViewAdapter(Context context, List<SellerProfile_ServiceModel.DataBean.PrdpecordBean> mainDataArrayList, ClickListener clickListener) {
        this.context = context;
        this.mainDataArrayList = mainDataArrayList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SellerProfileProductsLayoutBinding sellerProfileProductsLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.seller_profile_products_layout, parent, false);
        return new CustomViewHolder(sellerProfileProductsLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        SellerProfile_ServiceModel.DataBean.PrdpecordBean profileData = (SellerProfile_ServiceModel.DataBean.PrdpecordBean) getItem(position);
        holder.notifyViewBinding(profileData, holder, position);

    }

    @Override
    public int getItemCount() {
        return mainDataArrayList.size();
    }

    public Object getItem(int position) {
        return mainDataArrayList.get(position);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements ClickListener {
        SellerProfileProductsLayoutBinding sellerProfileProductsLayoutBinding;

        public CustomViewHolder(SellerProfileProductsLayoutBinding sellerProfileProductsLayoutBinding) {
            super(sellerProfileProductsLayoutBinding.getRoot());
            this.sellerProfileProductsLayoutBinding = sellerProfileProductsLayoutBinding;
        }

        public void notifyViewBinding(SellerProfile_ServiceModel.DataBean.PrdpecordBean profileData, CustomViewHolder customViewHolder, int position) {
            customViewHolder.sellerProfileProductsLayoutBinding.setData(profileData);
            customViewHolder.sellerProfileProductsLayoutBinding.setPosition(position);
            customViewHolder.sellerProfileProductsLayoutBinding.setClickListener(clickListener);
            customViewHolder.sellerProfileProductsLayoutBinding.setIsGuestUser(AlphaHolder.isGuestUser((Activity) context));

        }

        @Override
        public void onClick(int position, Object object, String callerIdentity) {
            clickListener.onClick(position, object, callerIdentity);
        }
    }
}
