package com.bartering.forsa.recyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.activities.camera_section.MediaData_HolderModel;
import com.bartering.forsa.databinding.ProductOverviewImagesLayoutBinding;

import java.util.List;

public class ProductOverviewImages_RecyclerViewAdapter extends RecyclerView.Adapter<ProductOverviewImages_RecyclerViewAdapter.CustomViewHolder> {

    Context context;
    List<MediaData_HolderModel> mediaData_holderModels;
    ClickListener clickListener;
    boolean isThisFromEdit;

    public ProductOverviewImages_RecyclerViewAdapter(Context context, List<MediaData_HolderModel> mediaData_holderModels, ClickListener clickListener,boolean isThisFromEdit) {
        this.context = context;
        this.mediaData_holderModels = mediaData_holderModels;
        this.clickListener = clickListener;
        this.isThisFromEdit = isThisFromEdit;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductOverviewImagesLayoutBinding productOverviewImagesLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.product_overview_images_layout, parent, false);
        return new CustomViewHolder(productOverviewImagesLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        MediaData_HolderModel mediaData_holderModel = (MediaData_HolderModel) getItem(position);
        holder.notifyViewBinding(mediaData_holderModel, holder, position);

        if (position == 0) {
            holder.productOverviewImagesLayoutBinding.captureImageLinLayoutId.setVisibility(View.VISIBLE);
            holder.productOverviewImagesLayoutBinding.imageViewLinLayoutId.setVisibility(View.GONE);
        } else {
            holder.productOverviewImagesLayoutBinding.captureImageLinLayoutId.setVisibility(View.GONE);
            holder.productOverviewImagesLayoutBinding.imageViewLinLayoutId.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return mediaData_holderModels.size();
    }

    public Object getItem(int position) {
        return mediaData_holderModels.get(position);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements ClickListener {
        ProductOverviewImagesLayoutBinding productOverviewImagesLayoutBinding;

        public CustomViewHolder(ProductOverviewImagesLayoutBinding productOverviewImagesLayoutBinding) {
            super(productOverviewImagesLayoutBinding.getRoot());
            this.productOverviewImagesLayoutBinding = productOverviewImagesLayoutBinding;
        }
        public void notifyViewBinding(MediaData_HolderModel mediaData_holderModel, CustomViewHolder customViewHolder, int position) {
            customViewHolder.productOverviewImagesLayoutBinding.setData(mediaData_holderModel);
            customViewHolder.productOverviewImagesLayoutBinding.setPosition(position);
            customViewHolder.productOverviewImagesLayoutBinding.setClickListener(this::onClick);
            customViewHolder.productOverviewImagesLayoutBinding.setIsThisFromEdit(isThisFromEdit);

        }

        @Override
        public void onClick(int position, Object object, String callerIdentity) {
            clickListener.onClick(position, object, callerIdentity);
        }
    }
}
