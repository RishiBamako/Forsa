package com.bartering.forsa.recyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.BoostPlanLayoutBinding;
import com.bartering.forsa.retrofit.service_model.BoostPlans_ServiceModel;

import java.util.List;

public class BoostPlans_RecyclerViewAdapter extends RecyclerView.Adapter<BoostPlans_RecyclerViewAdapter.CustomViewHolder> {

    Context context;
    List<BoostPlans_ServiceModel.DataBean> boostDataArrayList;
    ClickListener clickListener;

    public BoostPlans_RecyclerViewAdapter(Context context, List<BoostPlans_ServiceModel.DataBean> boostDataArrayList, ClickListener clickListener) {
        this.context = context;
        this.boostDataArrayList = boostDataArrayList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BoostPlanLayoutBinding boostPlanLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.boost_plan_layout, parent, false);
        return new CustomViewHolder(boostPlanLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        BoostPlans_ServiceModel.DataBean dataBean = (BoostPlans_ServiceModel.DataBean) getItem(position);
        holder.notifyViewBinding(dataBean, holder, position);

    }

    @Override
    public int getItemCount() {
        return boostDataArrayList.size();
    }

    public Object getItem(int position) {
        return boostDataArrayList.get(position);
    }

   /* private void notifyListData(int adapterPosition) {
        for (int b = 0; b < boostDataArrayList.size(); b++) {
            boostDataArrayList.get(b).setSelected(false);
            if (b == boostDataArrayList.size() - 1) {
                boostDataArrayList.get(adapterPosition).setSelected(true);
                notifyDataSetChanged();
            }
        }
    }*/

    public class CustomViewHolder extends RecyclerView.ViewHolder implements ClickListener {
        BoostPlanLayoutBinding boostPlanLayoutBinding;

        public CustomViewHolder(BoostPlanLayoutBinding boostPlanLayoutBinding) {
            super(boostPlanLayoutBinding.getRoot());
            this.boostPlanLayoutBinding = boostPlanLayoutBinding;
        }

        public void notifyViewBinding(BoostPlans_ServiceModel.DataBean dataBean, CustomViewHolder customViewHolder, int position) {
            customViewHolder.boostPlanLayoutBinding.setData(dataBean);
            customViewHolder.boostPlanLayoutBinding.setPosition(position);
            customViewHolder.boostPlanLayoutBinding.setIsShowBoostDetail(dataBean.isSelected());
            customViewHolder.boostPlanLayoutBinding.setClickListener(this::onClick);

        }

        @Override
        public void onClick(int position, Object object, String callerIdentity) {
            clickListener.onClick(position, object, callerIdentity);
        }
    }
}
