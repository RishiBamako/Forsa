package com.bartering.forsa.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bartering.forsa.ACRA_Slack.application.AcraSlackSample;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.GlobalDialog;
import com.bartering.forsa.R;
import com.bartering.forsa.activities.ChangePasswordActivity;
import com.bartering.forsa.activities.ChooseCategoryActivity;
import com.bartering.forsa.activities.EditProfileActivity;
import com.bartering.forsa.activities.FollowerUserActivity;
import com.bartering.forsa.activities.FollowingUserActivity;
import com.bartering.forsa.activities.HelpAndSupportActivity;
import com.bartering.forsa.activities.MyTransactionActivity;
import com.bartering.forsa.activities.SettingsEditProfileActivity;
import com.bartering.forsa.activities.SignInActivity;
import com.bartering.forsa.activities.WishListProfileActivity;
import com.bartering.forsa.activities.boots_Section.MyAdsActivity;
import com.bartering.forsa.buySubscriptionPlanProcess.SubscriptionPlansActivity;
import com.bartering.forsa.databinding.FragmentProfileBMBinding;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.BaseFragment;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.Comman_ServiceModel;
import com.bartering.forsa.retrofit.service_model.ProfileData_ServiceModel;
import com.bartering.forsa.tradeProcess.AddressActivity;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.SharedPreferences_Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;


public class Profile_BM_Fragment extends BaseFragment implements Observer<Object>, ClickListener {

    FragmentProfileBMBinding fragmentProfileBMBinding;
    Activity activity;

    ////Service Part

    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;

    ProfileData_ServiceModel profileData_serviceModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentProfileBMBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile__b_m_, container, false);
        fragmentProfileBMBinding.setClickListener(this::onClick);


        return fragmentProfileBMBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!AlphaHolder.isGuestUser(activity)) {
            fragmentProfileBMBinding.setIsGuest(false);
            getProfileData();

        } else {
            AlphaHolder.customToast(activity, getString(R.string.guestusercannotaccessit));
            fragmentProfileBMBinding.setIsGuest(true);
        }
        fragmentProfileBMBinding.setNoOfAds(AlphaHolder.getEventStatus("0", "FREE_ADS", activity));

    }

    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        if (resultData.getTag().equals("LOGOUT")) {
            Comman_ServiceModel comman_serviceModel = (Comman_ServiceModel) resultData.getRootData().getValue();
            if (comman_serviceModel.isStatus().equals("true")) {
                AlphaHolder.customToast(activity, activity.getResources().getString(R.string.logoutsuccessfully));
                new SharedPreferences_Util(activity).logout();
                AlphaHolder.saveEventStatus("", "KEEP_ALWAYS_SIGNED_IN", activity);

                Intent intent = new Intent(activity, SignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                activity.overridePendingTransition(0, 0);
            }
        }
        if (resultData.getTag().equals("GET_PROFILE")) {
            profileData_serviceModel = (ProfileData_ServiceModel) resultData.getRootData().getValue();
            fragmentProfileBMBinding.setProfileData(profileData_serviceModel);

            if (profileData_serviceModel.getData().getTotal_followers() == null)
                fragmentProfileBMBinding.setFollowers("0");
            else
                fragmentProfileBMBinding.setFollowers(profileData_serviceModel.getData().getTotal_followers());

            if (profileData_serviceModel.getData().getTotal_following() == null)
                fragmentProfileBMBinding.setFollowing("0");
            else
                fragmentProfileBMBinding.setFollowing(profileData_serviceModel.getData().getTotal_following());


        }
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event1")) {
            if (!AlphaHolder.isGuestUser(activity))
                serviceLogout();
            else {
                Intent intent = new Intent(activity, SignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                activity.overridePendingTransition(0, 0);
            }
        }
        if (callerIdentity.equals("event2")) {

        }
        if (callerIdentity.equals("event3")) {
            if (AlphaHolder.isGuestUser(activity)) {
                GlobalDialog.showDialog(activity);
            } else {
                ((AcraSlackSample) getActivity().getApplication()).switcher(getActivity(), FollowingUserActivity.class, 0);
            }
        }
        if (callerIdentity.equals("event4")) {
            if (AlphaHolder.isGuestUser(activity)) {
                GlobalDialog.showDialog(activity);
            } else {
                ((AcraSlackSample) getActivity().getApplication()).switcher(getActivity(), FollowerUserActivity.class, 0);
            }
        }
        if (callerIdentity.equals("event5")) {
            Intent intent = new Intent(activity, EditProfileActivity.class);
            intent.putExtra("profile_data", profileData_serviceModel);
            startActivity(intent);
        }
        if (callerIdentity.equals("event6")) {
            Intent intent = new Intent(activity, WishListProfileActivity.class);
            intent.putExtra("profile_data", profileData_serviceModel);
            startActivity(intent);
        }
        if (callerIdentity.equals("event7")) {
            Intent intent = new Intent(activity, ChooseCategoryActivity.class);
            intent.putExtra("profile_data", profileData_serviceModel);
            startActivity(intent);
        }
        if (callerIdentity.equals("event8")) {
            Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
            startActivity(intent);
            ((Activity) getActivity()).overridePendingTransition(0, 0);
        }
        if (callerIdentity.equals("event9")) {
            Intent intent = new Intent(getActivity(), MyTransactionActivity.class);
            startActivity(intent);
            ((Activity) getActivity()).overridePendingTransition(0, 0);
        }
        if (callerIdentity.equals("event10")) {
            Intent intent = new Intent(getActivity(), SettingsEditProfileActivity.class);
            startActivity(intent);
            ((Activity) getActivity()).overridePendingTransition(0, 0);
        }
        if (callerIdentity.equals("event13")) {
            AlphaHolder.stackList = new ArrayList<>();
            Intent intent = new Intent(getActivity(), SubscriptionPlansActivity.class);
            startActivity(intent);
            ((Activity) getActivity()).overridePendingTransition(0, 0);
        }
        if (callerIdentity.equals("event14")) {
            Intent intent = new Intent(getActivity(), MyAdsActivity.class);
            startActivity(intent);
            ((Activity) getActivity()).overridePendingTransition(0, 0);
        }
        if (callerIdentity.equals("event15")) {
            Intent intent = new Intent(getActivity(), HelpAndSupportActivity.class);
            startActivity(intent);
            ((Activity) getActivity()).overridePendingTransition(0, 0);
        }
        if (callerIdentity.equals("event16")) {
            Intent intent = new Intent(getActivity(), AddressActivity.class);
            startActivity(intent);
            ((Activity) getActivity()).overridePendingTransition(0, 0);
        }
    }

    //////SERVICE PART
    private void serviceLogout() {
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        Map<String, String> param = new HashMap<>();
        param.put("token", SharedPreferences_Util.getToken(activity));

        viewModel.loadData("LOGOUT", param, true, activity);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    private void getProfileData() {
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        Map<String, String> param = new HashMap<>();
        param.put("token", SharedPreferences_Util.getToken(activity));
        viewModel.loadData("GET_PROFILE", param, true, activity);
        viewModel.getRootData().observe(this, this::onChanged);
    }

}