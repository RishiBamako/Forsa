package com.bartering.forsa.recyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.AddressLayoutBinding;
import com.bartering.forsa.retrofit.service_model.AllAddress_ServiceModel;

import java.util.List;

public class Address_RecyclerViewAdapter extends RecyclerView.Adapter<Address_RecyclerViewAdapter.CustomViewHolder> {

    Context context;
    List<AllAddress_ServiceModel.DataBean> AllAdressDataArrayList;
    ClickListener clickListener;

    public Address_RecyclerViewAdapter(Context context, List<AllAddress_ServiceModel.DataBean> AllAdressDataArrayList, ClickListener clickListener) {
        this.context = context;
        this.AllAdressDataArrayList = AllAdressDataArrayList;
        this.clickListener = clickListener;

    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AddressLayoutBinding addressLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.address_layout, parent, false);
        return new CustomViewHolder(addressLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        AllAddress_ServiceModel.DataBean dataBean = (AllAddress_ServiceModel.DataBean) getItem(position);
        holder.notifyViewBinding(dataBean, holder, position);

    }

    @Override
    public int getItemCount() {
        return AllAdressDataArrayList.size();
    }

    public Object getItem(int position) {
        return AllAdressDataArrayList.get(position);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements ClickListener {
        AddressLayoutBinding addressLayoutBinding;

        public CustomViewHolder(AddressLayoutBinding addressLayoutBinding) {
            super(addressLayoutBinding.getRoot());
            this.addressLayoutBinding = addressLayoutBinding;
        }
        public void notifyViewBinding(AllAddress_ServiceModel.DataBean dataBean, CustomViewHolder customViewHolder, int position) {
            customViewHolder.addressLayoutBinding.setData(dataBean);
            customViewHolder.addressLayoutBinding.setPosition(position);
            customViewHolder.addressLayoutBinding.setClickListener(this::onClick);
        }

        @Override
        public void onClick(int position, Object object, String callerIdentity) {
            clickListener.onClick(position, object, callerIdentity);
        }
    }
}
