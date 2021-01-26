package com.bartering.forsa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bartering.forsa.AppCompactActivity;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.ActivitySettingEditprofileBinding;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.Comman_ServiceModel;
import com.bartering.forsa.retrofit.service_model.Languages_ServiceModel;
import com.bartering.forsa.retrofit.service_model.SettingProfile_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.SharedPreferences_Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class SettingsEditProfileActivity extends AppCompactActivity implements ClickListener, Observer<Object> {

    ActivitySettingEditprofileBinding activitySettingEditprofileBinding;
    SettingProfile_ServiceModel settingProfile_serviceModel;

    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;

    Languages_ServiceModel languages_serviceModel = null;
    Comman_ServiceModel comman_serviceModel = null;
    int languageClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySettingEditprofileBinding = DataBindingUtil.setContentView(this, R.layout.activity_setting_editprofile);
        activitySettingEditprofileBinding.setClickListener(this::onClick);
        activitySettingEditprofileBinding.setNotificationStatus(AlphaHolder.getButtonStatus(SettingsEditProfileActivity.this));

        languageSpinnerListener();
        serviceLoadLanguages();
    }

    private void languageSpinnerListener() {
        activitySettingEditprofileBinding.languageSpinnerId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (languages_serviceModel != null && languages_serviceModel.getData().size() > 0 && position > 0) {
                    languageClicked = position - 1;
                    updateLanguage(languages_serviceModel.getData().get(languageClicked).getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void updateSettings() {
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        Map<String, String> param = new HashMap<>();
        param.put("token", SharedPreferences_Util.getToken(SettingsEditProfileActivity.this));
        viewModel.loadData("SETTING", param, true, SettingsEditProfileActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    private void updateLanguage(String languageId) {
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        Map<String, String> param = new HashMap<>();
        param.put("token", SharedPreferences_Util.getToken(SettingsEditProfileActivity.this));
        param.put("language_id", languageId);
        viewModel.loadData("UPDATE_LANGUAGE", param, true, SettingsEditProfileActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        if (resultData.getTag().equals("SETTING")) {
            settingProfile_serviceModel = (SettingProfile_ServiceModel) resultData.getRootData().getValue();
            if (settingProfile_serviceModel.isStatus().equals("true")) {
                if (settingProfile_serviceModel.getData() != null) {
                    if (settingProfile_serviceModel.getData().get(0).getNotification_status().equals("ON")) {
                        activitySettingEditprofileBinding.setNotificationStatus(true);
                        AlphaHolder.saveEventStatus("ON", "STATUS_ON_OFF", SettingsEditProfileActivity.this);
                    } else {
                        activitySettingEditprofileBinding.setNotificationStatus(false);
                        AlphaHolder.saveEventStatus("OFF", "STATUS_ON_OFF", SettingsEditProfileActivity.this);
                    }
                }
            } else {
                AlphaHolder.customToast(SettingsEditProfileActivity.this, settingProfile_serviceModel.getMessage());
            }
        }
        if (resultData.getTag().equals("LANGUAGES_CALLER")) {
            languages_serviceModel = (Languages_ServiceModel) resultData.getRootData().getValue();
            if (languages_serviceModel.getData().size() > 0) {
                List languageList = new ArrayList<>();
                languageList.add(getResources().getString(R.string.selectlanguage));

                for (int i = 0; i < languages_serviceModel.getData().size(); i++) {
                    String languageName = languages_serviceModel.getData().get(i).getTitle();
                    languageList.add(languageName);
                    if (i == languages_serviceModel.getData().size() - 1) {
                        activitySettingEditprofileBinding.setLanguageDataList(languageList);
                        activitySettingEditprofileBinding.setLanguageName(getSelectedPosition());
                    }
                }
            }
        }
        if (resultData.getTag().equals("UPDATE_LANGUAGE")) {
            comman_serviceModel = (Comman_ServiceModel) resultData.getRootData().getValue();
            if (comman_serviceModel != null && comman_serviceModel.isStatus().equals("true")) {
                AlphaHolder.saveEventStatus(languages_serviceModel.getData().get(languageClicked).getId(), "SELECTED_LANGUAGE", SettingsEditProfileActivity.this);
                activitySettingEditprofileBinding.setLanguageName(languages_serviceModel.getData().get(languageClicked).getTitle());

                Intent intent = new Intent(SettingsEditProfileActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        }
    }

    private String getSelectedPosition() {
        String id = AlphaHolder.getSelectedLanguage(SettingsEditProfileActivity.this);
        if (id.equals("NO")) {
            return getResources().getString(R.string.selectlanguage);
        } else {
            if (languages_serviceModel.getData() != null && languages_serviceModel.getData().size() > 0) {
                for (int l = 0; l < languages_serviceModel.getData().size(); l++) {
                    if (id.equals(languages_serviceModel.getData().get(l).getId())) {
                        return languages_serviceModel.getData().get(l).getTitle();
                    }
                }
            }
        }
        return getResources().getString(R.string.selectlanguage);
    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event1")) {
            SettingsEditProfileActivity.this.finish();
        }
        if (callerIdentity.equals("event2")) {
            activitySettingEditprofileBinding.languageSpinnerId.performClick();
        }
        if (callerIdentity.equals("event3")) {
            updateSettings();
        }

    }

    //////SERVICE PART
    private void serviceLoadLanguages() {
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("LANGUAGES_CALLER", null, true, SettingsEditProfileActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

}