package com.bartering.forsa;

import android.content.Context;
import android.widget.Toast;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class AppCompactActivity extends DaggerAppCompatActivity {
    protected void observerErrorStatus(){}
    protected void observeLoadStatus(){}

    protected void onError(Context context, String message){
        Toast.makeText(context, "Error: " + message, Toast.LENGTH_SHORT).show();
    }

}
