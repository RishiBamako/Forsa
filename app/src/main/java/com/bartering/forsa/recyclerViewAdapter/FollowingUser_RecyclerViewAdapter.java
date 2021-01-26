package com.bartering.forsa.recyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.FollowinguserLayoutBinding;
import com.bartering.forsa.retrofit.service_model.FollowingUser_ServiceModel;

import java.util.List;

public class FollowingUser_RecyclerViewAdapter extends RecyclerView.Adapter<FollowingUser_RecyclerViewAdapter.CustomViewHolder> {

    Context context;
    List<FollowingUser_ServiceModel.DataBean> categoryDataArrayList;
    ClickListener clickListener;

    public FollowingUser_RecyclerViewAdapter(Context context, List<FollowingUser_ServiceModel.DataBean> categoryDataArrayList, ClickListener clickListener) {
        this.context = context;
        this.categoryDataArrayList = categoryDataArrayList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FollowinguserLayoutBinding followinguserLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.followinguser_layout, parent, false);
        return new CustomViewHolder(followinguserLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        FollowingUser_ServiceModel.DataBean dataBean = (FollowingUser_ServiceModel.DataBean) getItem(position);
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
        FollowinguserLayoutBinding followinguserLayoutBinding;

        public CustomViewHolder(FollowinguserLayoutBinding followinguserLayoutBinding) {
            super(followinguserLayoutBinding.getRoot());
            this.followinguserLayoutBinding = followinguserLayoutBinding;
        }

        public void notifyViewBinding(FollowingUser_ServiceModel.DataBean dataBean, CustomViewHolder customViewHolder, int position) {
            customViewHolder.followinguserLayoutBinding.setData(dataBean);
            customViewHolder.followinguserLayoutBinding.setPosition(position);
            customViewHolder.followinguserLayoutBinding.setClickListener(this::onClick);
        }

        @Override
        public void onClick(int position, Object object, String callerIdentity) {
            clickListener.onClick(position,object,callerIdentity);
        }
    }
}
