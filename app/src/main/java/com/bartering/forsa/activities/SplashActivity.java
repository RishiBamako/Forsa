package com.bartering.forsa.activities;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;

import com.bartering.forsa.GoogleLoader;
import com.bartering.forsa.R;
import com.bartering.forsa.activities.camera_section.MediaData_HolderModel;
import com.bartering.forsa.mutableViewModel.ParamOptimizer_ViewModel;
import com.bartering.forsa.retrofit.service_model.AllAddress_ServiceModel;
import com.bartering.forsa.retrofit.service_model.FollowerData_ServiceModel;
import com.bartering.forsa.retrofit.service_model.HomeFilter_NL_ServiceModel;
import com.bartering.forsa.retrofit.service_model.HomeFilter_ServiceModel;
import com.bartering.forsa.retrofit.service_model.MyAds_ServiceModel;
import com.bartering.forsa.retrofit.service_model.ProductCategory_ServiceModel;
import com.bartering.forsa.retrofit.service_model.ProductDetails_ServiceModel;
import com.bartering.forsa.retrofit.service_model.ProfileData_ServiceModel;
import com.bartering.forsa.retrofit.service_model.SignIn_ServiceModel;
import com.bartering.forsa.retrofit.service_model.SubCategoriesData_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.CircleImageView;
import com.bartering.forsa.utils.SharedPreferences_Util;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SplashActivity extends AppCompatActivity {

    private Handler mWaitHandler = new Handler();
    //SharedPreferences_Util sharedPreferences_util;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initialization();
    }

    private void initialization() {
        mWaitHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    String keepAlwaysSignedIn = AlphaHolder.getEventStatus("", "KEEP_ALWAYS_SIGNED_IN", SplashActivity.this);
                    if (TextUtils.isEmpty(keepAlwaysSignedIn)) {
                        if (TextUtils.isEmpty(SharedPreferences_Util.getToken(SplashActivity.this))) {
                            Intent intent = new Intent(SplashActivity.this, SignInActivity.class);
                            startActivity(intent);
                            overridePendingTransition(0, 0);
                            SplashActivity.this.finish();
                        } else {
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(intent);
                            overridePendingTransition(0, 0);
                            SplashActivity.this.finish();
                        }
                    } else if (keepAlwaysSignedIn.equals("YES")) {
                        if (TextUtils.isEmpty(SharedPreferences_Util.getToken(SplashActivity.this))) {
                            Intent intent = new Intent(SplashActivity.this, SignInActivity.class);
                            startActivity(intent);
                            overridePendingTransition(0, 0);
                            SplashActivity.this.finish();
                        } else {
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(intent);
                            overridePendingTransition(0, 0);
                            SplashActivity.this.finish();
                        }
                    } else if (keepAlwaysSignedIn.equals("NO")) {
                        Intent intent = new Intent(SplashActivity.this, SignInActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        SplashActivity.this.finish();
                    }


                } catch (Exception ignored) {
                    ignored.printStackTrace();
                }
            }
        }, 2000);  // Give a 5 seconds delay.
    }


}