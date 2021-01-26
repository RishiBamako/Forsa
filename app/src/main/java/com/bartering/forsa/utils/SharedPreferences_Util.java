package com.bartering.forsa.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.bartering.forsa.activities.camera_section.MediaData_HolderModel;
import com.bartering.forsa.retrofit.service_model.SignIn_ServiceModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import javax.inject.Inject;


public class SharedPreferences_Util {

    Context context;
    SharedPreferences prefs;

    @Inject
    public SharedPreferences_Util(Context context) {
        this.context = context;
    }

    public static String getToken(Context context) {
        SharedPreferences_Util sharedPreferences_util = new SharedPreferences_Util(context);
        if (sharedPreferences_util.getLoginModel() != null)
            return sharedPreferences_util.getLoginModel().getData().getToken();
        else
            return "";
    }
    public static String getUser_Id(Context context) {
        SharedPreferences_Util sharedPreferences_util = new SharedPreferences_Util(context);
        if (sharedPreferences_util.getLoginModel() != null)
            return sharedPreferences_util.getLoginModel().getData().getId();
        else
            return "";
    }
    public static String getUser_Image(Context context) {
        SharedPreferences_Util sharedPreferences_util = new SharedPreferences_Util(context);
        if (sharedPreferences_util.getLoginModel() != null)
            return sharedPreferences_util.getLoginModel().getData().getImage();
        else
            return "";
    }
    public static String getLoggedInUserName(Context context) {
        SharedPreferences_Util sharedPreferences_util = new SharedPreferences_Util(context);
        if (sharedPreferences_util.getLoginModel() != null)
            return sharedPreferences_util.getLoginModel().getData().getFull_name();
        else
            return "";
    }

    public void saveLoginModel(SignIn_ServiceModel obj) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString("MYLABEL", "myStringToSave").apply();
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        editor.putString("LOGIN", json);
        editor.commit();     // This line is IMPORTANT !!!
    }

    public SignIn_ServiceModel getLoginModel() {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString("LOGIN", null);
        java.lang.reflect.Type type = new TypeToken<SignIn_ServiceModel>() {
        }.getType();
        // LinkedTreeMap linkedTreeMap = gson.fromJson(json, type);
        SignIn_ServiceModel loginModel = gson.fromJson(json, type);
        return loginModel;
    }

    public void saveProductData(ArrayList<MediaData_HolderModel> obj) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString("MYLABEL", "myStringToSave").apply();
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        editor.putString("MEDIA_DATA", json);
        editor.commit();     // This line is IMPORTANT !!!
    }

    public ArrayList<MediaData_HolderModel> getMediaData() {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString("MEDIA_DATA", null);
        java.lang.reflect.Type type = new TypeToken<ArrayList<MediaData_HolderModel>>() {
        }.getType();
        ArrayList<MediaData_HolderModel> mediaData = gson.fromJson(json, type);
        return mediaData;
    }
    public void saveProductData_ForShow(ArrayList<MediaData_HolderModel> obj) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString("MYLABEL", "myStringToSave").apply();
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        editor.putString("MEDIA_DATA_FOR_SHOW", json);
        editor.commit();     // This line is IMPORTANT !!!
    }

    public ArrayList<MediaData_HolderModel> getMediaData_ForShow() {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString("MEDIA_DATA_FOR_SHOW", null);
        java.lang.reflect.Type type = new TypeToken<ArrayList<MediaData_HolderModel>>() {
        }.getType();
        ArrayList<MediaData_HolderModel> mediaData = gson.fromJson(json, type);
        return mediaData;
    }

    public void logout() {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }
}
