package com.bartering.forsa.recyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.AdsBottomLayoutBinding;
import com.bartering.forsa.models.AdsFilter_DataModel;

import java.util.List;

public class AdsBottomFilters_RecyclerViewAdapter extends RecyclerView.Adapter<AdsBottomFilters_RecyclerViewAdapter.CustomViewHolder> {

    Context context;
    List<AdsFilter_DataModel> categoryDataArrayList;
    ClickListener clickListener;

    public AdsBottomFilters_RecyclerViewAdapter(Context context, List<AdsFilter_DataModel> categoryDataArrayList, ClickListener clickListener) {
        this.context = context;
        this.categoryDataArrayList = categoryDataArrayList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdsBottomLayoutBinding adsBottomLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.ads_bottom_layout, parent, false);
        return new CustomViewHolder(adsBottomLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        AdsFilter_DataModel data = (AdsFilter_DataModel) getItem(position);
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
        AdsBottomLayoutBinding adsBottomLayoutBinding;

        public CustomViewHolder(AdsBottomLayoutBinding adsBottomLayoutBinding) {
            super(adsBottomLayoutBinding.getRoot());
            this.adsBottomLayoutBinding = adsBottomLayoutBinding;
        }

        public void notifyViewBinding(AdsFilter_DataModel data, CustomViewHolder customViewHolder, int position) {
            customViewHolder.adsBottomLayoutBinding.setData(data);
            customViewHolder.adsBottomLayoutBinding.setPosition(position);
            customViewHolder.adsBottomLayoutBinding.setClickListener(this);

        }

        @Override
        public void onClick(int position, Object object, String callerIdentity) {
            clickListener.onClick(position, object, callerIdentity);
        }
    }
}
