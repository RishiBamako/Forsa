package com.bartering.forsa.activities;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bartering.forsa.AppCompactActivity;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.ActivityFollowingUserBinding;
import com.bartering.forsa.recyclerViewAdapter.FollowingUser_RecyclerViewAdapter;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.Comman_ServiceModel;
import com.bartering.forsa.retrofit.service_model.FollowingUser_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.SharedPreferences_Util;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class FollowingUserActivity extends AppCompactActivity implements ClickListener, Observer<Object> {

    ActivityFollowingUserBinding activityFollowingUserBinding;
    FollowingUser_RecyclerViewAdapter followingUser_recyclerViewAdapter;

    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;

    FollowingUser_ServiceModel followingUser_serviceModel;
    Comman_ServiceModel comman_serviceModel;
    int follow_ClickedPosition = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityFollowingUserBinding = DataBindingUtil.setContentView(this, R.layout.activity_following_user);
        activityFollowingUserBinding.setClickListener(this::onClick);
        activityFollowingUserBinding.setIsNoRecord(false);
        getFollowingData();
    }

    public void getFollowingData() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", SharedPreferences_Util.getToken(FollowingUserActivity.this));

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("FOLLOWING_USER", paramMap, true, FollowingUserActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        if (resultData.getTag().equals("FOLLOWING_USER")) {
            followingUser_serviceModel = (FollowingUser_ServiceModel) resultData.getRootData().getValue();
            if (followingUser_serviceModel.getData().size() > 0) {
                activityFollowingUserBinding.setIsNoRecord(false);
                followingUser_recyclerViewAdapter = new FollowingUser_RecyclerViewAdapter(FollowingUserActivity.this, followingUser_serviceModel.getData(), this);
                activityFollowingUserBinding.setFollowingUserRV(followingUser_recyclerViewAdapter);
            } else {
                activityFollowingUserBinding.setIsNoRecord(true);
            }
        }
        if (resultData.getTag().equals("UN_FOLLOW")) {
            comman_serviceModel = (Comman_ServiceModel) resultData.getRootData().getValue();
            if (comman_serviceModel.isStatus().equals("true")) {

                followingUser_serviceModel.getData().remove(follow_ClickedPosition);
                followingUser_recyclerViewAdapter.notifyItemRemoved(follow_ClickedPosition);
                followingUser_recyclerViewAdapter.notifyItemRangeChanged(follow_ClickedPosition, followingUser_serviceModel.getData().size() - follow_ClickedPosition);
                AlphaHolder.customToast(FollowingUserActivity.this, comman_serviceModel.getMessage());
            }
        }
    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event1")) {
            this.finish();
        }
        if (callerIdentity.equals("event2")) {
            follow_ClickedPosition = position;
            FollowingUser_ServiceModel.DataBean dataBean = (FollowingUser_ServiceModel.DataBean) object;
            unFollow(dataBean.getUserid());
        }
    }

    public void unFollow(String id) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", SharedPreferences_Util.getToken(FollowingUserActivity.this));
        paramMap.put("userid", id);

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("UN_FOLLOW", paramMap, true, FollowingUserActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }
}