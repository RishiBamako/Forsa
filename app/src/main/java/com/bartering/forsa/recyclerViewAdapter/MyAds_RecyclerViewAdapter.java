package com.bartering.forsa.recyclerViewAdapter;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.MyadsLayoutBinding;
import com.bartering.forsa.retrofit.service_model.MyAds_ServiceModel;

import java.util.List;

public class MyAds_RecyclerViewAdapter extends RecyclerView.Adapter<MyAds_RecyclerViewAdapter.CustomViewHolder> {

    Context context;
    List<MyAds_ServiceModel.DataBean> categoryDataArrayList;
    OnItemEventListener onItemEventListener;
    ClickListener clickListener;

    public MyAds_RecyclerViewAdapter(Context context, List<MyAds_ServiceModel.DataBean> categoryDataArrayList, OnItemEventListener onItemEventListener, ClickListener clickListener) {
        this.context = context;
        this.categoryDataArrayList = categoryDataArrayList;
        this.onItemEventListener = onItemEventListener;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyadsLayoutBinding myadsLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.myads_layout, parent, false);
        return new CustomViewHolder(myadsLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        MyAds_ServiceModel.DataBean dataBean = (MyAds_ServiceModel.DataBean) getItem(position);
        holder.notifyViewBinding(dataBean, holder, position);
        holder.myadsLayoutBinding.menuLinLayoutId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemEventListener.onMoreClicked(holder.myadsLayoutBinding.menuLinLayoutId, holder.getAdapterPosition(),dataBean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryDataArrayList.size();
    }

    public Object getItem(int position) {
        return categoryDataArrayList.get(position);
    }

    public interface OnItemEventListener {
        void onMoreClicked(View v, int position,MyAds_ServiceModel.DataBean dataBean);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements ClickListener {
        MyadsLayoutBinding myadsLayoutBinding;

        public CustomViewHolder(MyadsLayoutBinding myadsLayoutBinding) {
            super(myadsLayoutBinding.getRoot());
            this.myadsLayoutBinding = myadsLayoutBinding;
        }

        public void notifyViewBinding(MyAds_ServiceModel.DataBean dataBean, CustomViewHolder customViewHolder, int position) {
            customViewHolder.myadsLayoutBinding.setData(dataBean);
            customViewHolder.myadsLayoutBinding.setTradeFor(context.getResources().getString(R.string.fora)+" <b>"+dataBean.getTradestatus()+"</b>");
            customViewHolder.myadsLayoutBinding.setPosition(position);
            customViewHolder.myadsLayoutBinding.setClickListener(this::onClick);

            if (dataBean.getProductstatus().equals("Active"))
                customViewHolder.myadsLayoutBinding.setIsMenuShow(true);
            else
                customViewHolder.myadsLayoutBinding.setIsMenuShow(false);

        }

        @Override
        public void onClick(int position, Object object, String callerIdentity) {
            clickListener.onClick(position, object, callerIdentity);
        }
    }
}
