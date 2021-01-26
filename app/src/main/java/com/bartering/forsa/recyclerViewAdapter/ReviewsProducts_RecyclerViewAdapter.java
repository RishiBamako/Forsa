package com.bartering.forsa.recyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.ReviewsProductLayoutBinding;
import com.bartering.forsa.retrofit.service_model.ProductReviewsList_ServiceModel;

import java.util.List;

public class ReviewsProducts_RecyclerViewAdapter extends RecyclerView.Adapter<ReviewsProducts_RecyclerViewAdapter.CustomViewHolder> {

    Context context;
    List<ProductReviewsList_ServiceModel.DataBean> categoryDataArrayList;
    ClickListener clickListener;

    public ReviewsProducts_RecyclerViewAdapter(Context context, List<ProductReviewsList_ServiceModel.DataBean> categoryDataArrayList, ClickListener clickListener) {
        this.context = context;
        this.categoryDataArrayList = categoryDataArrayList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ReviewsProductLayoutBinding reviewsProductLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.reviews_product_layout, parent, false);
        return new CustomViewHolder(reviewsProductLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        ProductReviewsList_ServiceModel.DataBean dataBean = (ProductReviewsList_ServiceModel.DataBean) getItem(position);
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
        ReviewsProductLayoutBinding reviewsProductLayoutBinding;

        public CustomViewHolder(ReviewsProductLayoutBinding reviewsProductLayoutBinding) {
            super(reviewsProductLayoutBinding.getRoot());
            this.reviewsProductLayoutBinding = reviewsProductLayoutBinding;
        }

        public void notifyViewBinding(ProductReviewsList_ServiceModel.DataBean dataBean, CustomViewHolder customViewHolder, int position) {
            reviewsProductLayoutBinding.setClick(this::onClick);
            reviewsProductLayoutBinding.setData(dataBean);
            reviewsProductLayoutBinding.setPosition(position);
        }

        @Override
        public void onClick(int position, Object object, String callerIdentity) {
            clickListener.onClick(position, object, callerIdentity);
        }
    }
}
