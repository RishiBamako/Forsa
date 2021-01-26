package com.bartering.forsa.activities;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bartering.forsa.AppCompactActivity;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.Croperino;
import com.bartering.forsa.CroperinoConfig;
import com.bartering.forsa.CroperinoFileUtil;
import com.bartering.forsa.R;
import com.bartering.forsa.databinding.ActivityEditProfileBinding;
import com.bartering.forsa.mutableViewModel.ParamOptimizer_ViewModel;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.Comman_ServiceModel;
import com.bartering.forsa.retrofit.service_model.ProfileData_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.SharedPreferences_Util;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

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

public class EditProfileActivity extends AppCompactActivity implements ClickListener, LocationListener, Observer<Object>, AdapterView.OnItemSelectedListener {

    protected static final int REQUEST_CHECK_SETTINGS = 122;
    private static final int REQUEST_LOCATION = 1002;
    ActivityEditProfileBinding activityEditProfileBinding;

    //LOCATION
    ProfileData_ServiceModel profileData_serviceModel;
    int mYear, mMonth, mDay;
    LocationManager locationManager;
    String latitude, longitude;
    LocationListener locationListener;
    Location locationGPS;
    Location locationNetwork;
    LocationRequest locationRequest;
    LocationSettingsRequest.Builder builder;
    @Inject
    ParamOptimizer_ViewModel paramOptimizer_viewModel;
    ViewDataBinding parentBinding;
    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;
    Comman_ServiceModel comman_serviceModel;
    File profileFile = null;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEditProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile);
        activityEditProfileBinding.setClickListener(this::onClick);
        locationListener = this;

        itemSelectedListener();
        imagePickerListener();
        getProfileData();
        languageSpinner();

    }
    private void itemSelectedListener() {
        activityEditProfileBinding.genderSpinnerId.setOnItemSelectedListener(this);
    }
    private void imagePickerListener() {
        new CroperinoConfig("IMG_" + System.currentTimeMillis() + ".jpg", "/", "/sdcard");
        CroperinoFileUtil.setupDirectory(EditProfileActivity.this);
    }

    private void gpsSettings() {
        googleApiClient = getAPIClientInstance();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
        requestGPSSettings();
    }
    private GoogleApiClient getAPIClientInstance() {
        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API).build();
        return mGoogleApiClient;
    }
    @Override
    protected void onResume() {
        super.onResume();
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
    }
    private void getProfileData() {
        if (getIntent().getExtras() != null) {
            profileData_serviceModel = (ProfileData_ServiceModel) getIntent().getSerializableExtra("profile_data");
            activityEditProfileBinding.setProfileData(profileData_serviceModel);
        }
    }
    private void languageSpinner() {
        List genderList = new ArrayList();
        genderList.add("Male");
        genderList.add("Female");

        activityEditProfileBinding.setGenderDataList(genderList);
        if (profileData_serviceModel.getData().getGender().equals("male")) {
            activityEditProfileBinding.genderSpinnerId.setSelection(1);
        } else {
            activityEditProfileBinding.genderSpinnerId.setSelection(2);

        }
    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event1")) {
            EditProfileActivity.this.finish();
        }
        if (callerIdentity.equals("event2")) {
            if (CroperinoFileUtil.verifyStoragePermissions(EditProfileActivity.this)) {
                prepareChooser();
            }
        }
        if (callerIdentity.equals("event3")) {
            openPicker();
        }
        if (callerIdentity.equals("event4")) {
            gpsSettings();
        }
        if (callerIdentity.equals("event5")) {
            MultipartBody.Part partImage = null;
            String gender = "";

            if (profileFile != null) {
                partImage = MultipartBody.Part.createFormData("image", profileFile.getName(), RequestBody.create(MediaType.parse("image/*"), profileFile));
            }
            List<MultipartBody.Part> image = new ArrayList<>();
            image.add(partImage);
            gender = activityEditProfileBinding.genderSpinnerId.getSelectedItem().toString().toLowerCase();
            RequestBody nameRb = RequestBody.create(MediaType.parse("text/plain"), profileData_serviceModel.getData().getUser_name());
            RequestBody emailRb = RequestBody.create(MediaType.parse("text/plain"), profileData_serviceModel.getData().getEmail());
            RequestBody mobileRb = RequestBody.create(MediaType.parse("text/plain"), profileData_serviceModel.getData().getMobile());
            RequestBody genderRb = RequestBody.create(MediaType.parse("text/plain"), gender);
            RequestBody dobRb = RequestBody.create(MediaType.parse("text/plain"), profileData_serviceModel.getData().getDob());
            RequestBody locationRb = RequestBody.create(MediaType.parse("text/plain"), profileData_serviceModel.getData().getLatitude() + "," + profileData_serviceModel.getData().getLongitude());
            RequestBody token = RequestBody.create(MediaType.parse("text/plain"), SharedPreferences_Util.getToken(EditProfileActivity.this));

            HashMap<String, RequestBody> paramMap = new HashMap<>();
            paramMap.put("user_name", nameRb);
            paramMap.put("email", emailRb);
            paramMap.put("mobile", mobileRb);
            paramMap.put("gender", genderRb);
            paramMap.put("dob", dobRb);
            paramMap.put("location", locationRb);
            paramMap.put("token", token);

            viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
            viewModel.loadDataRequestBody("EDIT_PROFILE", paramMap, image, null, true, EditProfileActivity.this);
            viewModel.getRootData().observe(this, this::onChanged);
        }
    }

    private void prepareChooser() {
        Croperino.prepareChooser(EditProfileActivity.this, "Capture photo...", ContextCompat.getColor(EditProfileActivity.this, android.R.color.background_dark));
    }

    private void prepareCamera() {
        Croperino.prepareCamera(EditProfileActivity.this);
    }

    private void openPicker() {
        Calendar mcurrentDate = Calendar.getInstance();
        mYear = mcurrentDate.get(Calendar.YEAR);
        mMonth = mcurrentDate.get(Calendar.MONTH);
        mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker = new DatePickerDialog(EditProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                Calendar myCalendar = Calendar.getInstance();
                myCalendar.set(Calendar.YEAR, selectedyear);
                myCalendar.set(Calendar.MONTH, selectedmonth);
                myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);
                String myFormat = "yyyy-MM-dd"; //Change as you need
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                profileData_serviceModel.getData().setDob(sdf.format(myCalendar.getTime()));
                activityEditProfileBinding.setProfileData(profileData_serviceModel);
                mDay = selectedday;
                mMonth = selectedmonth;
                mYear = selectedyear;
            }
        }, mYear, mMonth, mDay);
        mDatePicker.show();
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(EditProfileActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(EditProfileActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
        } else {

            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 1, this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 1, this);

        }
    }

    private void requestGPSSettings() {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        locationRequest.setInterval(4000);
        locationRequest.setFastestInterval(4000);
        builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.i("", "All location settings are satisfied.");
                        getLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i("", "Location settings are not satisfied. Show the user a dialog to" + "upgrade location settings ");
                        try {
                            status.startResolutionForResult(EditProfileActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.e("Applicationsett", e.toString());
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i("", "Location settings are inadequate, and cannot be fixed here. Dialog " + "not created.");
                        Toast.makeText(getApplication(), "Location settings are inadequate, and cannot be fixed here", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                getLocation();
                break;
            case CroperinoConfig.REQUEST_TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    Croperino.runCropImage(CroperinoFileUtil.getTempFile(), EditProfileActivity.this, true, 1, 1, R.color.gray, R.color.gray);
                }
                break;
            case CroperinoConfig.REQUEST_PICK_FILE:
                if (resultCode == Activity.RESULT_OK) {
                    CroperinoFileUtil.newGalleryFile(data, EditProfileActivity.this);
                    Croperino.runCropImage(CroperinoFileUtil.getTempFile(), EditProfileActivity.this, true, 1, 1, R.color.gray, R.color.gray);
                }
                break;
            case CroperinoConfig.REQUEST_CROP_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    Uri uri = Uri.fromFile(CroperinoFileUtil.getTempFile());
                    profileFile = new File(uri.getPath());
                    profileData_serviceModel.getData().setId_photo_url("" + uri);
                    activityEditProfileBinding.setProfileData(profileData_serviceModel);
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        if (location != null) {
            double lat = location.getLatitude();
            double longi = location.getLongitude();
            latitude = String.valueOf(lat);
            longitude = String.valueOf(longi);
            Log.e("LOCATIONIS", "Your Location: " + "\n" + "Latitude: " + latitude + "\n" + "Longitude: " + longitude);

            profileData_serviceModel.getData().setLatitude(latitude);
            profileData_serviceModel.getData().setLongitude(longitude);
            activityEditProfileBinding.setProfileData(profileData_serviceModel);
        } else {
            AlphaHolder.customToast(EditProfileActivity.this, "LOCATION ERROR");
        }
        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION: {
                getLocation();
            }

        }
    }

    public void sendProfileData() {


    }

    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        if ("EDIT_PROFILE".equalsIgnoreCase(resultData.getTag())) {
            comman_serviceModel = (Comman_ServiceModel) resultData.getRootData().getValue();
            if (comman_serviceModel.isStatus().equals("true")) {
                EditProfileActivity.this.finish();
            }
            AlphaHolder.customToast(EditProfileActivity.this, comman_serviceModel.getMessage());
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent == activityEditProfileBinding.genderSpinnerId) {
            profileData_serviceModel.getData().setGender(activityEditProfileBinding.genderSpinnerId.getSelectedItem().toString());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}