package com.bartering.forsa.recyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bartering.forsa.R;
import com.bartering.forsa.databinding.ProductDescriptionHeadLayoutTwoBinding;

import java.util.List;

public class ProductDescriptionHead_RecyclerViewAdapter extends RecyclerView.Adapter<ProductDescriptionHead_RecyclerViewAdapter.CustomViewHolder> {

    Context context;
    List<String> categoryDataArrayList;

    public ProductDescriptionHead_RecyclerViewAdapter(Context context, List<String> categoryDataArrayList) {
        this.context = context;
        this.categoryDataArrayList = categoryDataArrayList;
    }
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductDescriptionHeadLayoutTwoBinding productDescriptionHeadLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.product_description_head_layout_two, parent, false);
        return new CustomViewHolder(productDescriptionHeadLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        String CategoryName = (String) getItem(position);
        holder.notifyViewBinding(CategoryName,holder,position);

    }

    @Override
    public int getItemCount() {
        return categoryDataArrayList.size();
    }

    public Object getItem(int position) {
        return categoryDataArrayList.get(position);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ProductDescriptionHeadLayoutTwoBinding productDescriptionHeadLayoutBinding;

        public CustomViewHolder(ProductDescriptionHeadLayoutTwoBinding productDescriptionHeadLayoutBinding) {
            super(productDescriptionHeadLayoutBinding.getRoot());
            this.productDescriptionHeadLayoutBinding = productDescriptionHeadLayoutBinding;
        }

        public void notifyViewBinding(String categoryName,CustomViewHolder customViewHolder,int position) {
            customViewHolder.productDescriptionHeadLayoutBinding.descriptionNameCheckboxId.setText(categoryName);

        }
    }
}
