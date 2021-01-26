package com.bartering.forsa.recyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.FolloweruserLayoutBinding;
import com.bartering.forsa.retrofit.service_model.FollowerData_ServiceModel;

import java.util.List;

public class FollowerUser_RecyclerViewAdapter extends RecyclerView.Adapter<FollowerUser_RecyclerViewAdapter.CustomViewHolder> {

    Context context;
    List<FollowerData_ServiceModel.DataBean> categoryDataArrayList;
    ClickListener clickListener;

    public FollowerUser_RecyclerViewAdapter(Context context, List<FollowerData_ServiceModel.DataBean> categoryDataArrayList, ClickListener clickListener) {
        this.context = context;
        this.categoryDataArrayList = categoryDataArrayList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FolloweruserLayoutBinding followeruserLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.followeruser_layout, parent, false);
        return new CustomViewHolder(followeruserLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        FollowerData_ServiceModel.DataBean dataBean = (FollowerData_ServiceModel.DataBean) getItem(position);
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
        FolloweruserLayoutBinding followeruserLayoutBinding;

        public CustomViewHolder(FolloweruserLayoutBinding followeruserLayoutBinding) {
            super(followeruserLayoutBinding.getRoot());
            this.followeruserLayoutBinding = followeruserLayoutBinding;
        }

        public void notifyViewBinding(FollowerData_ServiceModel.DataBean dataBean, CustomViewHolder customViewHolder, int position) {
            customViewHolder.followeruserLayoutBinding.setData(dataBean);
            customViewHolder.followeruserLayoutBinding.setClickListener(this::onClick);
            customViewHolder.followeruserLayoutBinding.setPosition(position);


        }

        @Override
        public void onClick(int position, Object object, String callerIdentity) {
            clickListener.onClick(position, object, callerIdentity);
        }
    }
}
