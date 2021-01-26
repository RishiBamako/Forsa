package com.bartering.forsa.recyclerViewAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.activities.bartering_detail.ProductDetailActivity;
import com.bartering.forsa.databinding.CartLayoutBinding;
import com.bartering.forsa.retrofit.service_model.CartData_ServiceModel;
import com.bartering.forsa.retrofit.service_model.HomeProducts_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;

import java.util.List;

public class Cart_RecyclerViewAdapter extends RecyclerView.Adapter<Cart_RecyclerViewAdapter.CustomViewHolder> {

    Context context;
    List<CartData_ServiceModel.DataBean.PrdpecordBean> categoryDataArrayList;
    ClickListener clickListener;
    boolean isInEditMode;

    public Cart_RecyclerViewAdapter(Context context, List<CartData_ServiceModel.DataBean.PrdpecordBean> categoryDataArrayList, ClickListener clickListener, boolean isInEditMode) {
        this.context = context;
        this.categoryDataArrayList = categoryDataArrayList;
        this.clickListener = clickListener;
        this.isInEditMode = isInEditMode;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CartLayoutBinding cartLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.cart_layout, parent, false);
        return new CustomViewHolder(cartLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        CartData_ServiceModel.DataBean.PrdpecordBean dataBean = (CartData_ServiceModel.DataBean.PrdpecordBean) getItem(position);
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
        CartLayoutBinding cartLayoutBinding;

        public CustomViewHolder(CartLayoutBinding cartLayoutBinding) {
            super(cartLayoutBinding.getRoot());
            this.cartLayoutBinding = cartLayoutBinding;
        }

        public void notifyViewBinding(CartData_ServiceModel.DataBean.PrdpecordBean dataBean, CustomViewHolder customViewHolder, int position) {
            this.cartLayoutBinding.setClickListener(this::onClick);
            this.cartLayoutBinding.setPosition(position);
            this.cartLayoutBinding.setData(dataBean);
            this.cartLayoutBinding.setIsInEditMode(isInEditMode);
            this.cartLayoutBinding.setIsGuestUser(AlphaHolder.isGuestUser((Activity) context));

            cartLayoutBinding.parentLinLayoutId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ProductDetailActivity.class);
                    context.startActivity(intent);
                    ((Activity) context).overridePendingTransition(0, 0);
                }
            });
        }

        @Override
        public void onClick(int position, Object object, String callerIdentity) {
            clickListener.onClick(position, object, callerIdentity);
        }
    }
}
