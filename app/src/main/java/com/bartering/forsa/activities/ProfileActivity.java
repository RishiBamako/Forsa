package com.bartering.forsa.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bartering.forsa.AppCompactActivity;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.Croperino;
import com.bartering.forsa.CroperinoConfig;
import com.bartering.forsa.CroperinoFileUtil;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.ActivityProfileBinding;
import com.bartering.forsa.mutableViewModel.ParamOptimizer_ViewModel;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.Comman_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.SharedPreferences_Util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfileActivity extends AppCompactActivity implements ClickListener, Observer<Object> {

    ActivityProfileBinding activityProfileBinding;
    ParamOptimizer_ViewModel paramOptimizer_viewModel;
    String genderString, token, isFrom;

    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;

    File profileFile = null;
    int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        paramOptimizer_viewModel = new ParamOptimizer_ViewModel();
        activityProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        activityProfileBinding.setClickListrener(this::onClick);
        activityProfileBinding.setConfirmPasswordShouldHide(false);
        try {
            if (getIntent().getExtras() != null) {
                isFrom = getIntent().getExtras().getString("IS_FROM");
                paramOptimizer_viewModel.setFullName(new MutableLiveData<>(getIntent().getExtras().getString("USER_NAME")));
                paramOptimizer_viewModel.setEmail(new MutableLiveData<>(getIntent().getExtras().getString("EMAIL_ADDRESS")));
                token = getIntent().getExtras().getString("TOKEN");

                if (!isFrom.equals("REGISTER"))
                    activityProfileBinding.setConfirmPasswordShouldHide(false);
                else if (isFrom.equals("REGISTER"))
                    activityProfileBinding.setConfirmPasswordShouldHide(false);

            }
        } catch (Exception EE) {

        }
        activityProfileBinding.setParamOptimizer(paramOptimizer_viewModel);
        listener();
        languageSpinner();

    }

    private void listener() {
        activityProfileBinding.backLLId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileActivity.this.finish();
            }
        });
    }

    private void languageSpinner() {
        List genderList = new ArrayList();
        genderList.add(getString(R.string.selectgender));
        genderList.add("Male");
        genderList.add("Female");
        activityProfileBinding.setGenderDataList(genderList);

        activityProfileBinding.genderSpinnerId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                genderString = activityProfileBinding.genderSpinnerId.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        if (resultData.getTag().equals("ADD_PROFILE")) {
            Comman_ServiceModel comman_serviceModel = (Comman_ServiceModel) resultData.getRootData().getValue();
            if (comman_serviceModel.isStatus().equals("true")) {
                AlphaHolder.customToast(ProfileActivity.this,comman_serviceModel.getMessage());

                if (!isFrom.equals("REGISTER")) {
                    Intent intent = new Intent(ProfileActivity.this, ChooseLanguageActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                } else {
                    Intent intent = new Intent(ProfileActivity.this, SignInActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
                ProfileActivity.this.finish();
            }
        }
    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event1")) {

            if (TextUtils.isEmpty(paramOptimizer_viewModel.getFullName().getValue())) {
                AlphaHolder.customToast(ProfileActivity.this, getResources().getString(R.string.pleaseenter_fullname));
            } else if (TextUtils.isEmpty(paramOptimizer_viewModel.getEmail().getValue())) {
                AlphaHolder.customToast(ProfileActivity.this, getResources().getString(R.string.emailcantbeempty));
            } else if (TextUtils.isEmpty(genderString)) {
                AlphaHolder.customToast(ProfileActivity.this, getResources().getString(R.string.gendercannotbeempty));
            } else if (genderString.equals(getString(R.string.selectgender))) {
                AlphaHolder.customToast(ProfileActivity.this, getResources().getString(R.string.gendercannotbeempty));
            } else if (TextUtils.isEmpty(paramOptimizer_viewModel.getDateOfBirth().getValue())) {
                AlphaHolder.customToast(ProfileActivity.this, getResources().getString(R.string.dobcannotbeempty));
            } /*else if (isFrom.equals("REGISTER")) {
                if (TextUtils.isEmpty(paramOptimizer_viewModel.getConfirmPassword().getValue())) {
                    AlphaHolder.customToast(ProfileActivity.this, getResources().getString(R.string.confirmpasswordcantbesame));
                }
                else{
                    sendDataInProfile();
                }
            }*/
            else {
                sendDataInProfile();
            }
        }
        if (callerIdentity.equals("event2")) {
            if (CroperinoFileUtil.verifyStoragePermissions(ProfileActivity.this)) {
                prepareChooser();
            }
        }
        if (callerIdentity.equals("event3")) {
            openPicker();
        }
    }

    public void sendDataInProfile() {
        MultipartBody.Part partImage = null;
        String gender = "";
        RequestBody tokenValue = null;

        if (profileFile != null) {
            partImage = MultipartBody.Part.createFormData("image", profileFile.getName(), RequestBody.create(MediaType.parse("image/*"), profileFile));
        }
        List<MultipartBody.Part> image = new ArrayList<>();
        image.add(partImage);
        gender = activityProfileBinding.genderSpinnerId.getSelectedItem().toString();

        RequestBody nameRb = RequestBody.create(MediaType.parse("text/plain"), paramOptimizer_viewModel.getFullName().getValue());
        RequestBody emailRb = RequestBody.create(MediaType.parse("text/plain"), paramOptimizer_viewModel.getEmail().getValue());
        RequestBody genderRb = RequestBody.create(MediaType.parse("text/plain"), gender.toLowerCase());
        RequestBody dobRb = RequestBody.create(MediaType.parse("text/plain"), paramOptimizer_viewModel.getDateOfBirth().getValue());

        HashMap<String, RequestBody> paramMap = new HashMap<>();
        paramMap.put("user_name", nameRb);
        paramMap.put("dob", dobRb);
        paramMap.put("gender", genderRb);
        paramMap.put("email", emailRb);

        if (isFrom.equals("REGISTER")) {
            tokenValue = RequestBody.create(MediaType.parse("text/plain"), token);
        } else
            tokenValue = RequestBody.create(MediaType.parse("text/plain"), SharedPreferences_Util.getToken(ProfileActivity.this));

        paramMap.put("token", tokenValue);


        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadDataRequestBody("ADD_PROFILE", paramMap, image, null, true, ProfileActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);

    }

    private void prepareChooser() {
        Croperino.prepareChooser(ProfileActivity.this, "Capture photo...", ContextCompat.getColor(ProfileActivity.this, android.R.color.background_dark));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CroperinoConfig.REQUEST_TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    Croperino.runCropImage(CroperinoFileUtil.getTempFile(), ProfileActivity.this, true, 1, 1, R.color.gray, R.color.gray);
                }
                break;
            case CroperinoConfig.REQUEST_PICK_FILE:
                if (resultCode == Activity.RESULT_OK) {
                    CroperinoFileUtil.newGalleryFile(data, ProfileActivity.this);
                    Croperino.runCropImage(CroperinoFileUtil.getTempFile(), ProfileActivity.this, true, 1, 1, R.color.gray, R.color.gray);
                }
                break;
            case CroperinoConfig.REQUEST_CROP_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    Uri i = Uri.fromFile(CroperinoFileUtil.getTempFile());
                    profileFile = new File(i.getPath());
                    activityProfileBinding.profileImageViewId.setImageURI(i);
                }
                break;
            default:
                break;
        }

    }

    private void openPicker() {
        Calendar mcurrentDate = Calendar.getInstance();
        mYear = mcurrentDate.get(Calendar.YEAR);
        mMonth = mcurrentDate.get(Calendar.MONTH);
        mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker = new DatePickerDialog(ProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                Calendar myCalendar = Calendar.getInstance();
                myCalendar.set(Calendar.YEAR, selectedyear);
                myCalendar.set(Calendar.MONTH, selectedmonth);
                myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);
                String myFormat = "yyyy-MM-dd"; //Change as you need
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                paramOptimizer_viewModel.setDateOfBirth(new MutableLiveData<>(sdf.format(myCalendar.getTime())));
                activityProfileBinding.setDateOfBirth(sdf.format(myCalendar.getTime()));
                mDay = selectedday;
                mMonth = selectedmonth;
                mYear = selectedyear;
            }
        }, mYear, mMonth, mDay);
        mDatePicker.show();
    }


}