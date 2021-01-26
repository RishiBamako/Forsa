package com.bartering.forsa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bartering.forsa.R;
import com.bartering.forsa.databinding.ActivityChooseCountryBinding;

import java.util.ArrayList;
import java.util.List;

public class ChooseCountryActivity extends AppCompatActivity {

    ActivityChooseCountryBinding activityChooseCountryBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_country);
        activityChooseCountryBinding = DataBindingUtil.setContentView(this, R.layout.activity_choose_country);
        countrySpinner();
    }
    public void category(View view){
        Intent intent = new Intent(ChooseCountryActivity.this,ChooseCategoryActivity.class);
        startActivity(intent);
        overridePendingTransition(0,0);
    }
    private void countrySpinner() {
        List languageSpinner = new ArrayList();
        languageSpinner.add("India");
        languageSpinner.add("Kuwait");
        languageSpinner.add("Qatar");
        languageSpinner.add("Dubai");
        ArrayAdapter arrayAdapter = new ArrayAdapter(ChooseCountryActivity.this, android.R.layout.simple_dropdown_item_1line, languageSpinner);
        activityChooseCountryBinding.countrySpinner.setAdapter(arrayAdapter);
    }
}