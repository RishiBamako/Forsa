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
import com.bartering.forsa.databinding.WishlistProfileProductsLayoutBinding;
import com.bartering.forsa.retrofit.service_model.HomeProducts_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;

import java.util.List;

public class WishList_Profile_RecyclerViewAdapter extends RecyclerView.Adapter<WishList_Profile_RecyclerViewAdapter.CustomViewHolder> {

    Context context;
    List<HomeProducts_ServiceModel.DataBean> categoryDataArrayList;
    ClickListener clickListener;

    public WishList_Profile_RecyclerViewAdapter(Context context, List<HomeProducts_ServiceModel.DataBean> categoryDataArrayList, ClickListener clickListener) {
        this.context = context;
        this.categoryDataArrayList = categoryDataArrayList;
        this.clickListener = clickListener;
    }
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        WishlistProfileProductsLayoutBinding wishlistLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.wishlist_profile_products_layout, parent, false);
        return new CustomViewHolder(wishlistLayoutBinding);

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
        WishlistProfileProductsLayoutBinding wishlistLayoutBinding;

        public CustomViewHolder(WishlistProfileProductsLayoutBinding wishlistLayoutBinding) {
            super(wishlistLayoutBinding.getRoot());
            this.wishlistLayoutBinding = wishlistLayoutBinding;
        }

        public void notifyViewBinding(HomeProducts_ServiceModel.DataBean dataBean, CustomViewHolder customViewHolder, int position) {
            this.wishlistLayoutBinding.setClickListener(this::onClick);
            this.wishlistLayoutBinding.setPosition(position);
            this.wishlistLayoutBinding.setData(dataBean);
            this.wishlistLayoutBinding.setIsGuestUser(AlphaHolder.isGuestUser((Activity) context));

        }

        @Override
        public void onClick(int position, Object object, String callerIdentity) {
            clickListener.onClick(position, object, callerIdentity);
        }
    }
}
