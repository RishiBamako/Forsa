package com.bartering.forsa.recyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bartering.forsa.R;
import com.bartering.forsa.databinding.ShowBarteringimagesLayoutBinding;

import java.util.List;

public class ShowBarteringImages_RecyclerViewAdapter extends RecyclerView.Adapter<ShowBarteringImages_RecyclerViewAdapter.CustomViewHolder> {

    Context context;
    List<String> categoryDataArrayList;

    public ShowBarteringImages_RecyclerViewAdapter(Context context, List<String> categoryDataArrayList) {
        this.context = context;
        this.categoryDataArrayList = categoryDataArrayList;
    }
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ShowBarteringimagesLayoutBinding showBarteringimagesLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.show_barteringimages_layout, parent, false);
        return new CustomViewHolder(showBarteringimagesLayoutBinding);
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
        ShowBarteringimagesLayoutBinding showBarteringimagesLayoutBinding;

        public CustomViewHolder(ShowBarteringimagesLayoutBinding showBarteringimagesLayoutBinding) {
            super(showBarteringimagesLayoutBinding.getRoot());
            this.showBarteringimagesLayoutBinding = showBarteringimagesLayoutBinding;
        }

        public void notifyViewBinding(String categoryName,CustomViewHolder customViewHolder,int position) {
           // customViewHolder.chooseBarteringimagesLayoutBinding.categoryNameCheckboxId.setText(categoryName);

        }
    }
}
