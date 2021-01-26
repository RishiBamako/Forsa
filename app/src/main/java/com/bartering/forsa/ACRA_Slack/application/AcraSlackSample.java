package com.bartering.forsa.ACRA_Slack.application;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bartering.forsa.ACRA_Slack.acra.CrashSender;
import com.bartering.forsa.ACRA_Slack.network.ClientApi;
import com.bartering.forsa.DaggerAppComponent;
import com.bartering.forsa.utils.TypefaceUtil;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

/**
 * Created by rishiojha on 11/3/19.
 */
@ReportsCrashes()
public class AcraSlackSample extends DaggerApplication {

    Intent intent;

    @Override
    public void onCreate() {
        super.onCreate();
        ClientApi.initialize();
        ACRA.init(this);
        ACRA.getErrorReporter().setReportSender(new CrashSender());

        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/playfairdisplaybold.otf");
        setupActivityListener();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void forceRTLIfSupported(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            //activity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }

    public void switcher(Activity activity, Class aClass, int pendingTransaction) {
        intent = new Intent(activity, aClass);
        activity.startActivity(intent);
        if (pendingTransaction == 0) {
            activity.overridePendingTransition(0, 0);
        }
    }
    public void callFragement(AppCompatActivity activity, Fragment fragment, int containerView) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerView, fragment);
        fragmentTransaction.commit();
    }

    private void setupActivityListener() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                forceRTLIfSupported(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }
}