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
import com.bartering.forsa.databinding.WishlistProductsTabLayoutBinding;
import com.bartering.forsa.retrofit.service_model.WishlistData_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.AppConstant;

import java.util.List;

public class WishlistProducts_Tabs_RecyclerViewAdapter extends RecyclerView.Adapter<WishlistProducts_Tabs_RecyclerViewAdapter.CustomViewHolder> {

    Context context;
    List<WishlistData_ServiceModel.DataBean> categoryDataArrayList;
    ClickListener clickListener;

    public WishlistProducts_Tabs_RecyclerViewAdapter(Context context, List<WishlistData_ServiceModel.DataBean> categoryDataArrayList, ClickListener clickListener) {
        this.context = context;
        this.categoryDataArrayList = categoryDataArrayList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        WishlistProductsTabLayoutBinding wishlistProductsTabLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.wishlist_products_tab_layout, parent, false);
        return new CustomViewHolder(wishlistProductsTabLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        WishlistData_ServiceModel.DataBean data = (WishlistData_ServiceModel.DataBean) getItem(position);
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
        WishlistProductsTabLayoutBinding wishlistProductsTabLayoutBinding;

        public CustomViewHolder(WishlistProductsTabLayoutBinding wishlistProductsTabLayoutBinding) {
            super(wishlistProductsTabLayoutBinding.getRoot());
            this.wishlistProductsTabLayoutBinding = wishlistProductsTabLayoutBinding;
        }

        public void notifyViewBinding(WishlistData_ServiceModel.DataBean dataBean, CustomViewHolder customViewHolder, int position) {
            customViewHolder.wishlistProductsTabLayoutBinding.setPosition(position);
            customViewHolder.wishlistProductsTabLayoutBinding.setMainData(dataBean);
            customViewHolder.wishlistProductsTabLayoutBinding.setClicklistener(this);
            customViewHolder.wishlistProductsTabLayoutBinding.setIsGuestUser(AlphaHolder.isGuestUser((Activity) context));

        }

        @Override
        public void onClick(int position, Object object, String callerIdentity) {
            clickListener.onClick(position, object, callerIdentity);
        }
    }
}
