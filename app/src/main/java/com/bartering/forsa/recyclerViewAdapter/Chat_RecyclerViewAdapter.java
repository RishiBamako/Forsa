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
import com.bartering.forsa.databinding.ChatLayoutBinding;
import com.bartering.forsa.retrofit.service_model.ChatData_ServiceModel;

import java.util.List;

public class Chat_RecyclerViewAdapter extends RecyclerView.Adapter<Chat_RecyclerViewAdapter.CustomViewHolder> {

    Context context;
    List<ChatData_ServiceModel.DataBean> categoryDataArrayList;
    ClickListener clickListener;
    OnItemEventListener onItemEventListener;


    public Chat_RecyclerViewAdapter(Context context, List<ChatData_ServiceModel.DataBean> categoryDataArrayList, ClickListener clickListener, OnItemEventListener onItemEventListener) {
        this.context = context;
        this.categoryDataArrayList = categoryDataArrayList;
        this.clickListener = clickListener;
        this.onItemEventListener = onItemEventListener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ChatLayoutBinding chatLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.chat_layout, parent, false);
        return new CustomViewHolder(chatLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        ChatData_ServiceModel.DataBean dataBean = (ChatData_ServiceModel.DataBean) getItem(position);
        holder.notifyViewBinding(dataBean, holder, position);

    }

    public List<ChatData_ServiceModel.DataBean> getChatData() {
        return categoryDataArrayList;
    }
    public void addMessages(ChatData_ServiceModel.DataBean dataBean) {
        categoryDataArrayList.add(dataBean);
        notifyItemInserted(categoryDataArrayList.size()-1);
        notifyItemRangeInserted(categoryDataArrayList.size()-1,categoryDataArrayList.size());
    }

    @Override
    public int getItemCount() {
        return categoryDataArrayList.size();
    }

    public Object getItem(int position) {
        return categoryDataArrayList.get(position);
    }

    ///// SHOW MENU
    public interface OnItemEventListener {
        void onMoreClicked(View v, int position, ChatData_ServiceModel.DataBean dataBean);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements ClickListener {
        ChatLayoutBinding chatLayoutBinding;

        public CustomViewHolder(ChatLayoutBinding chatLayoutBinding) {
            super(chatLayoutBinding.getRoot());
            this.chatLayoutBinding = chatLayoutBinding;
        }

        public void notifyViewBinding(ChatData_ServiceModel.DataBean dataBean, CustomViewHolder customViewHolder, int position) {
            customViewHolder.chatLayoutBinding.setChatData(dataBean);
            customViewHolder.chatLayoutBinding.setPosition(position);
            customViewHolder.chatLayoutBinding.setIsMessageSeen(true);
            customViewHolder.chatLayoutBinding.setClickListener(this::onClick);
            if (dataBean.getMsg_rprt().equals("loginuser"))
                customViewHolder.chatLayoutBinding.setIsFromLoggedInUser(true);
            else
                customViewHolder.chatLayoutBinding.setIsFromLoggedInUser(false);

            customViewHolder.chatLayoutBinding.myChatRelaLayoutId.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemEventListener.onMoreClicked(customViewHolder.chatLayoutBinding.myChatRelaLayoutId, customViewHolder.getAdapterPosition(), dataBean);
                    return false;
                }
            });


        }

        @Override
        public void onClick(int position, Object object, String callerIdentity) {
            clickListener.onClick(position, object, callerIdentity);
        }
    }
}
