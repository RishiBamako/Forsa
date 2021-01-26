package com.bartering.forsa.recyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.ChooseBarteringimagesitemLayoutBinding;
import com.bartering.forsa.retrofit.service_model.BarteringProducts_ServiceModel;

import java.util.List;

public class ChooseBarteringImages_Item_RecyclerViewAdapter extends RecyclerView.Adapter<ChooseBarteringImages_Item_RecyclerViewAdapter.CustomViewHolder> {

    Context context;
    List<BarteringProducts_ServiceModel.DataBean> barteringDataArrayList;
    ClickListener clickListener;

    public ChooseBarteringImages_Item_RecyclerViewAdapter(Context context, List<BarteringProducts_ServiceModel.DataBean> barteringDataArrayList, ClickListener clickListener) {
        this.context = context;
        this.barteringDataArrayList = barteringDataArrayList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ChooseBarteringimagesitemLayoutBinding chooseBarteringimagesitemLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.choose_barteringimagesitem_layout, parent, false);
        return new CustomViewHolder(chooseBarteringimagesitemLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        BarteringProducts_ServiceModel.DataBean dataBean = (BarteringProducts_ServiceModel.DataBean) getItem(position);
        holder.notifyViewBinding(dataBean, holder, position);
        if (position == 0 && null == dataBean) {
            holder.chooseBarteringimagesitemLayoutBinding.setIsShowCameraView(true);
        } else {
            holder.chooseBarteringimagesitemLayoutBinding.setIsShowCameraView(false);
        }
    }

    @Override
    public int getItemCount() {
        return barteringDataArrayList.size();
    }

    public Object getItem(int position) {
        return barteringDataArrayList.get(position);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements ClickListener {
        ChooseBarteringimagesitemLayoutBinding chooseBarteringimagesitemLayoutBinding;

        public CustomViewHolder(ChooseBarteringimagesitemLayoutBinding chooseBarteringimagesitemLayoutBinding) {
            super(chooseBarteringimagesitemLayoutBinding.getRoot());
            this.chooseBarteringimagesitemLayoutBinding = chooseBarteringimagesitemLayoutBinding;
        }

        public void notifyViewBinding(BarteringProducts_ServiceModel.DataBean dataBean, CustomViewHolder customViewHolder, int position) {
            customViewHolder.chooseBarteringimagesitemLayoutBinding.setData(dataBean);
            customViewHolder.chooseBarteringimagesitemLayoutBinding.setPosition(position);
            customViewHolder.chooseBarteringimagesitemLayoutBinding.setClickListener(this::onClick);
        }

        @Override
        public void onClick(int position, Object object, String callerIdentity) {
            clickListener.onClick(position, object, callerIdentity);
        }
    }
}
