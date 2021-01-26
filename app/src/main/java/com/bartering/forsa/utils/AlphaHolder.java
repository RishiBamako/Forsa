package com.bartering.forsa.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bartering.forsa.R;
import com.bartering.forsa.activities.MainActivity;
import com.bartering.forsa.retrofit.service_model.SimilarProducts_ServiceModel;
import com.bartering.forsa.retrofit.service_model.WishlistData_ServiceModel;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Retrofit;

public class AlphaHolder {
    public static boolean isShowing = false;
    public static boolean isFreeAdsDialog = false;
    public static String isFromMyAds = "";
    public static List<Activity> stackList = new ArrayList<>();
    public static Bundle bundle = new Bundle();
    public static Retrofit retrofit = null;
    public static List languageList = null;
    public static boolean keepAlwaysSignedIn = false;
    public static String isFromCalled = "";
    public static List<SimilarProducts_ServiceModel.DataBean> similarDataProductsList = new ArrayList<>();
    public static List<WishlistData_ServiceModel.DataBean> wishlistDataProductsList = new ArrayList<>();


    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static void saveEventStatus(String value, String key, Activity activity) {
        PreferenceManager.getDefaultSharedPreferences(activity).edit().putString(key, value).apply();
    }

    public static String getEventStatus(String defaultValue, String key, Activity activity) {
        String status = PreferenceManager.getDefaultSharedPreferences(activity).getString(key, defaultValue);
        return status;
    }

    public static Boolean getButtonStatus(Activity activity) {
        String status = PreferenceManager.getDefaultSharedPreferences(activity).getString("STATUS_ON_OFF", "OFF");
        if (status.equals("OFF"))
            return false;
        else
            return true;
    }

    public static void clearStack() {
        for (int stack = 0; stack < AlphaHolder.stackList.size(); stack++) {
            Activity activity = AlphaHolder.stackList.get(stack);
            if (activity != null) {
                activity.finish();
            }
        }
    }

    public static String getSelectedLanguage(Activity activity) {
        String status = PreferenceManager.getDefaultSharedPreferences(activity).getString("SELECTED_LANGUAGE", "NO");

        return status;
    }

    public static void hideKeyboard(Activity activity) {
        View v = activity.getCurrentFocus();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null && v != null;
        imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static List<String> arrayStringToArrayList(String value) {
        List<String> dataToSend = new ArrayList<>();
        String commaaSaparatedString = value.replace("[", "").replace("]", "");
        String[] elements = commaaSaparatedString.split(",");

        List<String> selectedCategoryIdsList = Arrays.asList(elements);
        if (selectedCategoryIdsList.size() > 0) {
            for (int k = 0; k < selectedCategoryIdsList.size(); k++) {
                dataToSend.add(selectedCategoryIdsList.get(k).trim());
            }
        }
        return dataToSend;
    }

    public static boolean isGuestUser(Activity activity) {
        SharedPreferences_Util sharedPreferences_util = new SharedPreferences_Util(activity);
        if (sharedPreferences_util.getLoginModel() == null) {
            return true;
        }
        return false;
    }

    public static void showKeyboard(Activity activity) {
        View v = activity.getCurrentFocus();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null && v != null;
        imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void customToast(Context context, String textToShow) {
        View layout = LayoutInflater.from(context).inflate(R.layout.custom_toast, null);
        TextView text = layout.findViewById(R.id.text);
        text.setText(textToShow);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 50);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    public static boolean isValidDigitString(String value) {
        if (value.matches("[0-9]+")) {
            return true;
        }
        return false;
    }

    public static Bitmap getBitmap(String path) {
        Bitmap bitmap = null;
        try {
            File f = new File(path);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static String dateFormateChange(String dateToFormat, String inputFormat, String outputFormat) {
        String result = "";
        SimpleDateFormat formatterOld = new SimpleDateFormat(inputFormat, Locale.getDefault());
        SimpleDateFormat formatterNew = new SimpleDateFormat(outputFormat, Locale.getDefault());
        Date date = null;
        try {
            date = formatterOld.parse(dateToFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null) {
            result = formatterNew.format(date);
        }
        return result;

    }

    public static void saveRecyclerviewState(RecyclerView recyclerView, String key) {
        Parcelable listState = recyclerView.getLayoutManager().onSaveInstanceState();
        bundle.putParcelable(key, listState);
    }

    public static void manipulateRecyclerviewState(RecyclerView recyclerView, String key) {
        if (bundle != null) {
            Parcelable listState = bundle.getParcelable(key);
            recyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }
    }

    public static void redirectToProfile(Activity activity) {
        if (activity != null) {
            Intent intent = new Intent(activity, MainActivity.class);
            intent.putExtra("PROFILE", "YES");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
            activity.overridePendingTransition(0, 0);
            activity.finish();
        }
    }

    public static List<SimilarProducts_ServiceModel.DataBean> getNoProducts(List<SimilarProducts_ServiceModel.DataBean> data) {
        List<SimilarProducts_ServiceModel.DataBean> dataToSend = new ArrayList<>();
        if (data.size() == 1) {
            return data;
        } else {
            dataToSend.add(data.get(0));
            dataToSend.add(data.get(1));
        }
        return dataToSend;
    }

    public static List<WishlistData_ServiceModel.DataBean> getNoSimiProducts(List<WishlistData_ServiceModel.DataBean> data) {
        List<WishlistData_ServiceModel.DataBean> dataToSend = new ArrayList<>();
        if (data.size() == 1) {
            return data;
        } else {
            dataToSend.add(data.get(0));
            dataToSend.add(data.get(1));
        }
        return dataToSend;
    }

    public static Bitmap uriToBitmap(Uri selectedFileUri, Context context) {
        try {
            ParcelFileDescriptor parcelFileDescriptor = context.getContentResolver().openFileDescriptor(selectedFileUri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            parcelFileDescriptor.close();
            return image;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
