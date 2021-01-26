package com.bartering.forsa.recyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.MytransactionLayoutBinding;
import com.bartering.forsa.retrofit.service_model.Transaction_ServiceModel;

import java.util.List;

public class MyTransaction_RecyclerViewAdapter extends RecyclerView.Adapter<MyTransaction_RecyclerViewAdapter.CustomViewHolder> {

    Context context;
    List<Transaction_ServiceModel.DataBean> categoryDataArrayList;
    ClickListener clickListener;

    public MyTransaction_RecyclerViewAdapter(Context context, List<Transaction_ServiceModel.DataBean> categoryDataArrayList, ClickListener clickListener) {
        this.context = context;
        this.categoryDataArrayList = categoryDataArrayList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MytransactionLayoutBinding mytransactionLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.mytransaction_layout, parent, false);
        return new CustomViewHolder(mytransactionLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Transaction_ServiceModel.DataBean dataBean = (Transaction_ServiceModel.DataBean) getItem(position);
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
        MytransactionLayoutBinding mytransactionLayoutBinding;

        public CustomViewHolder(MytransactionLayoutBinding mytransactionLayoutBinding) {
            super(mytransactionLayoutBinding.getRoot());
            this.mytransactionLayoutBinding = mytransactionLayoutBinding;
        }

        public void notifyViewBinding(Transaction_ServiceModel.DataBean dataBean, CustomViewHolder customViewHolder, int position) {
            mytransactionLayoutBinding.setClickListener(this::onClick);
            mytransactionLayoutBinding.setDataBean(dataBean);
            mytransactionLayoutBinding.setPosition(position);
        }

        @Override
        public void onClick(int position, Object object, String callerIdentity) {
            clickListener.onClick(position, object, callerIdentity);
        }
    }
}
