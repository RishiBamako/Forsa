package com.bartering.forsa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bartering.forsa.ACRA_Slack.application.AcraSlackSample;
import com.bartering.forsa.AppCompactActivity;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.ActivityChooseLanguageBinding;
import com.bartering.forsa.mutableViewModel.ParamOptimizer_ViewModel;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.Comman_ServiceModel;
import com.bartering.forsa.retrofit.service_model.Countries_ServiceModel;
import com.bartering.forsa.retrofit.service_model.Languages_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.SharedPreferences_Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class ChooseLanguageActivity extends AppCompactActivity implements Observer<Object>, AdapterView.OnItemSelectedListener, ClickListener {

    ActivityChooseLanguageBinding activityChooseLanguageBinding;

    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;


    @Inject
    ParamOptimizer_ViewModel paramOptimizer_viewModel;
    Languages_ServiceModel languages_serviceModel;
    Countries_ServiceModel countries_serviceModel = null;
    Comman_ServiceModel comman_serviceModel;
    String selectedCountry, selectedLanguage;

    //int spinnerCheck = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityChooseLanguageBinding = DataBindingUtil.setContentView(this, R.layout.activity_choose_language);
        activityChooseLanguageBinding.setClickListener(this::onClick);
        AlphaHolder.stackList.add(this);
        serviceLoadLanguages();
        spinnerItemSelectedListener();
    }

    private void spinnerItemSelectedListener() {
        activityChooseLanguageBinding.countrySpinner.setOnItemSelectedListener(this);
        activityChooseLanguageBinding.languageSpinner.setOnItemSelectedListener(this);
    }

    public void chooseCountry(View view) {
        Intent intent = new Intent(ChooseLanguageActivity.this, ChooseCategoryActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void backPressed(View view) {
        this.finish();
    }

    public void category(View view) {
        Intent intent = new Intent(ChooseLanguageActivity.this, ChooseCategoryActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position > 0) {
            if (parent == activityChooseLanguageBinding.countrySpinner) {
                selectedCountry = countries_serviceModel.getData().get(position-1).getId();
            }
            if (parent == activityChooseLanguageBinding.languageSpinner) {
                AlphaHolder.saveEventStatus(languages_serviceModel.getData().get(position-1).getId(), "SELECTED_LANGUAGE", ChooseLanguageActivity.this);
                selectedLanguage = languages_serviceModel.getData().get(position-1).getId();
            }
        } else {
            if (parent == activityChooseLanguageBinding.countrySpinner)
                selectedCountry = "";
            else {
                selectedLanguage = "";
                AlphaHolder.saveEventStatus("", "SELECTED_LANGUAGE", ChooseLanguageActivity.this);
            }

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //////SERVICE PART
    private void serviceLoadLanguages() {
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("LANGUAGES_CALLER", null, true, ChooseLanguageActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    private void serviceLoadCountries() {
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("COUNTRIES_CALLER", null, true, ChooseLanguageActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    private void serviceUpdateLanguageAndCountry() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("country_id", selectedCountry);
        paramMap.put("language_id", selectedLanguage);
        paramMap.put("token", SharedPreferences_Util.getToken(ChooseLanguageActivity.this));

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("UPDATE_COUNTRY_LANGUAGE", paramMap, true, ChooseLanguageActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event1")) {
            if (TextUtils.isEmpty(selectedCountry))
                AlphaHolder.customToast(ChooseLanguageActivity.this, getString(R.string.countrynotselected));
            else if (TextUtils.isEmpty(selectedLanguage))
                AlphaHolder.customToast(ChooseLanguageActivity.this, getString(R.string.languagenotselected));
            else
                serviceUpdateLanguageAndCountry();
        }
    }

    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        if (resultData.getTag().equals("LANGUAGES_CALLER")) {
            languages_serviceModel = (Languages_ServiceModel) resultData.getRootData().getValue();
            if (languages_serviceModel.getData().size() > 0) {
                List languageList = new ArrayList<>();
                languageList.add(getResources().getString(R.string.selectlanguage));
                for (int i = 0; i < languages_serviceModel.getData().size(); i++) {
                    String languageName = languages_serviceModel.getData().get(i).getTitle();
                    languageList.add(languageName);
                    if (i == languages_serviceModel.getData().size() - 1) {
                        activityChooseLanguageBinding.setLanguageDataList(languageList);
                    }
                }
            }
            serviceLoadCountries();
        }
        if (resultData.getTag().equals("COUNTRIES_CALLER")) {
            countries_serviceModel = (Countries_ServiceModel) resultData.getRootData().getValue();
            if (countries_serviceModel.getData().size() > 0) {
                List countriesList = new ArrayList<>();
                countriesList.add(getResources().getString(R.string.selectcountry_header));
                for (int i = 0; i < countries_serviceModel.getData().size(); i++) {
                    String countriesName = countries_serviceModel.getData().get(i).getName();
                    countriesList.add(countriesName);
                    if (i == countries_serviceModel.getData().size() - 1) {
                        activityChooseLanguageBinding.setCountriesDataList(countriesList);

                    }
                }
            }
        }
        if (resultData.getTag().equals("UPDATE_COUNTRY_LANGUAGE")) {
            comman_serviceModel = (Comman_ServiceModel) resultData.getRootData().getValue();
            if (comman_serviceModel.isStatus().equals("true")) {
                ((AcraSlackSample) this.getApplication()).switcher(ChooseLanguageActivity.this, ChooseCategoryActivity.class, 0);
            } else {
                AlphaHolder.customToast(ChooseLanguageActivity.this, comman_serviceModel.getMessage());
            }
        }
    }


}