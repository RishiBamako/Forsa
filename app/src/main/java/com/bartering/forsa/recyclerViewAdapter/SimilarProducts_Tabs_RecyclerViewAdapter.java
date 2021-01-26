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
import com.bartering.forsa.databinding.SimilarProductsTabLayoutBinding;
import com.bartering.forsa.retrofit.service_model.SimilarProducts_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.AppConstant;

import java.util.List;

public class SimilarProducts_Tabs_RecyclerViewAdapter extends RecyclerView.Adapter<SimilarProducts_Tabs_RecyclerViewAdapter.CustomViewHolder> {

    Context context;
    List<SimilarProducts_ServiceModel.DataBean> categoryDataArrayList;
    ClickListener clickListener;

    public SimilarProducts_Tabs_RecyclerViewAdapter(Context context, List<SimilarProducts_ServiceModel.DataBean> categoryDataArrayList, ClickListener clickListener) {
        this.context = context;
        this.categoryDataArrayList = categoryDataArrayList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SimilarProductsTabLayoutBinding similarProductsTabLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.similar_products_tab_layout, parent, false);
        return new CustomViewHolder(similarProductsTabLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        SimilarProducts_ServiceModel.DataBean dataBean = (SimilarProducts_ServiceModel.DataBean) getItem(position);
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
        SimilarProductsTabLayoutBinding similarProductsTabLayoutBinding;

        public CustomViewHolder(SimilarProductsTabLayoutBinding similarProductsTabLayoutBinding) {
            super(similarProductsTabLayoutBinding.getRoot());
            this.similarProductsTabLayoutBinding = similarProductsTabLayoutBinding;
        }

        public void notifyViewBinding(SimilarProducts_ServiceModel.DataBean dataBean, CustomViewHolder customViewHolder, int position) {
            customViewHolder.similarProductsTabLayoutBinding.setMainData(dataBean);
            customViewHolder.similarProductsTabLayoutBinding.setPosition(position);
            customViewHolder.similarProductsTabLayoutBinding.setClicklistener(this::onClick);
            customViewHolder.similarProductsTabLayoutBinding.setIsGuestUser(AlphaHolder.isGuestUser((Activity) context));

        }

        @Override
        public void onClick(int position, Object object, String callerIdentity) {
            clickListener.onClick(position, object, callerIdentity);
        }
    }
}
