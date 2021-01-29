package com.bartering.forsa.activities;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bartering.forsa.AppCompactActivity;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.ActivityFollowerUserBinding;
import com.bartering.forsa.recyclerViewAdapter.FollowerUser_RecyclerViewAdapter;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.Comman_ServiceModel;
import com.bartering.forsa.retrofit.service_model.FollowerData_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.SharedPreferences_Util;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class FollowerUserActivity extends AppCompactActivity implements ClickListener, Observer<Object> {

    ActivityFollowerUserBinding parentDataBinding;
    FollowerUser_RecyclerViewAdapter followerUser_recyclerViewAdapter;

    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;

    FollowerData_ServiceModel followerData_serviceModel;
    Comman_ServiceModel comman_serviceModel;
    int follow_ClickedPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_follower_user);
        parentDataBinding.setClickListener(this::onClick);
        parentDataBinding.setIsNoRecord(false);

        getFollowerData();
    }
    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        if (resultData.getTag().equals("FOLLOWER_USER")) {
            followerData_serviceModel = (FollowerData_ServiceModel) resultData.getRootData().getValue();
            if (followerData_serviceModel.getData().size() > 0) {
                parentDataBinding.setIsNoRecord(false);
                followerUser_recyclerViewAdapter = new FollowerUser_RecyclerViewAdapter(FollowerUserActivity.this, followerData_serviceModel.getData(), this::onClick);
                parentDataBinding.setFollowingUserRV(followerUser_recyclerViewAdapter);
            } else {
                parentDataBinding.setIsNoRecord(true);
                AlphaHolder.customToast(FollowerUserActivity.this, followerData_serviceModel.getMessage());
            }
        }
        if (resultData.getTag().equals("UN_FOLLOW")) {
            comman_serviceModel = (Comman_ServiceModel) resultData.getRootData().getValue();
            if (comman_serviceModel.isStatus().equals("true")) {
                followerData_serviceModel.getData().remove(follow_ClickedPosition);
                followerUser_recyclerViewAdapter.notifyItemRemoved(follow_ClickedPosition);
                followerUser_recyclerViewAdapter.notifyItemRangeChanged(follow_ClickedPosition, followerData_serviceModel.getData().size() - follow_ClickedPosition);
                AlphaHolder.customToast(FollowerUserActivity.this, comman_serviceModel.getMessage());

            }

        }
        if (resultData.getTag().equals("FOLLOW")) {
            comman_serviceModel = (Comman_ServiceModel) resultData.getRootData().getValue();
            if (comman_serviceModel.isStatus().equals("true")) {
                followerData_serviceModel.getData().get(follow_ClickedPosition).setIamfollowing("yes");
                followerUser_recyclerViewAdapter.notifyItemChanged(follow_ClickedPosition);
                followerUser_recyclerViewAdapter.notifyItemRangeChanged(follow_ClickedPosition, followerData_serviceModel.getData().size() - follow_ClickedPosition);
                AlphaHolder.customToast(FollowerUserActivity.this, comman_serviceModel.getMessage());
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
            FollowerData_ServiceModel.DataBean dataBean = (FollowerData_ServiceModel.DataBean) object;
            startFollow(dataBean.getFollowing_id());
        }
    }

    public void getFollowerData() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", SharedPreferences_Util.getToken(FollowerUserActivity.this));

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("FOLLOWER_USER", paramMap, true, FollowerUserActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    public void unFollow(String id) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", SharedPreferences_Util.getToken(FollowerUserActivity.this));
        paramMap.put("userid", id);

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("UN_FOLLOW", paramMap, true, FollowerUserActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    public void startFollow(String id) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", SharedPreferences_Util.getToken(FollowerUserActivity.this));
        paramMap.put("list", "yes");
        paramMap.put("userid", id);

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("FOLLOW", paramMap, true, FollowerUserActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }
}