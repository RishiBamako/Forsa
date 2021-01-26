package com.bartering.forsa.recyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.activities.camera_section.MediaData_HolderModel;
import com.bartering.forsa.databinding.ChooseBarteringimagesLayoutBinding;

import java.util.List;

public class ChooseBarteringImages_RecyclerViewAdapter extends RecyclerView.Adapter<ChooseBarteringImages_RecyclerViewAdapter.CustomViewHolder> {

    Context context;
    List<MediaData_HolderModel> mediaData_holderModels;
    ClickListener clickListener;

    public ChooseBarteringImages_RecyclerViewAdapter(Context context, List<MediaData_HolderModel> mediaData_holderModels, ClickListener clickListener) {
        this.context = context;
        this.mediaData_holderModels = mediaData_holderModels;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ChooseBarteringimagesLayoutBinding chooseBarteringimagesLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.choose_barteringimages_layout, parent, false);
        return new CustomViewHolder(chooseBarteringimagesLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        MediaData_HolderModel mediaData_holderModel = (MediaData_HolderModel) getItem(position);
        holder.notifyViewBinding(mediaData_holderModel, holder, position);

        if (position == 0) {
            holder.chooseBarteringimagesLayoutBinding.setShouldShowCameraView(true);
        } else {
            holder.chooseBarteringimagesLayoutBinding.setShouldShowCameraView(false);
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
        ChooseBarteringimagesLayoutBinding chooseBarteringimagesLayoutBinding;

        public CustomViewHolder(ChooseBarteringimagesLayoutBinding chooseBarteringimagesLayoutBinding) {
            super(chooseBarteringimagesLayoutBinding.getRoot());
            this.chooseBarteringimagesLayoutBinding = chooseBarteringimagesLayoutBinding;
        }

        public void notifyViewBinding(MediaData_HolderModel mediaData_holderModel, CustomViewHolder customViewHolder, int position) {
            if (mediaData_holderModel != null) {
                customViewHolder.chooseBarteringimagesLayoutBinding.setData(mediaData_holderModel);
                customViewHolder.chooseBarteringimagesLayoutBinding.setPosition(position);
                customViewHolder.chooseBarteringimagesLayoutBinding.setClickListener(this::onClick);
            }
        }

        @Override
        public void onClick(int position, Object object, String callerIdentity) {
            clickListener.onClick(position, object, callerIdentity);
        }
    }
}
