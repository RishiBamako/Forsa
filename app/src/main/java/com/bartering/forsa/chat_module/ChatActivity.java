package com.bartering.forsa.chat_module;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bartering.forsa.AppCompactActivity;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.ActivityChatBinding;
import com.bartering.forsa.recyclerViewAdapter.Chat_RecyclerViewAdapter;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.ChatData_ServiceModel;
import com.bartering.forsa.utils.SharedPreferences_Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class ChatActivity extends AppCompactActivity implements ClickListener, Observer<Object> {

    ActivityChatBinding activityChatBinding;

    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;


    Chat_RecyclerViewAdapter chat_recyclerViewAdapter;
    List<String> messagesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityChatBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
        activityChatBinding.setClickListener(this::onClick);
        activityChatBinding.setIsRecordsNotFound(false);
        callChat();
        chatIntegration();
    }

    private void chatIntegration() {
        messagesList = new ArrayList<>();
        messagesList.add("");
        messagesList.add("");
        messagesList.add("");
        messagesList.add("");
        messagesList.add("");
        messagesList.add("");
        messagesList.add("");
        messagesList.add("");


    }

    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        if (resultData.getTag().equals("CHAT_DATA")) {
            ChatData_ServiceModel chatData_serviceModel = (ChatData_ServiceModel) resultData.getRootData().getValue();
          /*  if (chatData_serviceModel != null && chatData_serviceModel.getData().size() > 0) {
                activityChatBinding.setIsRecordsNotFound(false);
                chat_recyclerViewAdapter = new Chat_RecyclerViewAdapter(ChatActivity.this, chatData_serviceModel.getData(), this::onClick);
                activityChatBinding.setChatAdapter(chat_recyclerViewAdapter);
            } else {
                activityChatBinding.setIsRecordsNotFound(true);
            }*/
        }
    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event1")) {
            ChatActivity.this.finish();
        }
        if (callerIdentity.equals("event2")) {
            ChatActivity.this.finish();
        }
    }

    private void callChat() {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("token", SharedPreferences_Util.getToken(ChatActivity.this));
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("CHAT_DATA", paramMap, true, ChatActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }
}