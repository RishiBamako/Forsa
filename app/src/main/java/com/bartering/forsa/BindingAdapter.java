package com.bartering.forsa;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.media.MediaPlayer;
import android.net.Uri;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.lifecycle.MutableLiveData;

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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BindingAdapter {
    @androidx.databinding.BindingAdapter("app:imageWithCustomRadius")
    public static void imageWithCustomRadius(ImageView imageView, String url) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(25));
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(R.drawable.image)
                .apply(requestOptions)
                .into(imageView);
    }

    @androidx.databinding.BindingAdapter("app:imageWithCustomRadius_home")
    public static void imageWithCustomRadius_home(ImageView imageView, String url) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(25));
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(R.drawable.image)
                .apply(requestOptions)
                .override(130, 130)
                .into(imageView);
    }


    @androidx.databinding.BindingAdapter("app:bindSpinnerAdapter")
    public static void bindSpinnerAdapter(AppCompatSpinner spinner, List list) {
        if (list != null) {
            ArrayAdapter arrayAdapter = new ArrayAdapter(spinner.getContext(), android.R.layout.simple_dropdown_item_1line, list);
            spinner.setAdapter(arrayAdapter);
        }
    }

    @androidx.databinding.BindingAdapter("app:genderSpinnerAdapter")
    public static void genderSpinnerAdapter(AppCompatSpinner spinner, ProfileData_ServiceModel profileDataServiceModel) {
        List genderList = new ArrayList();
        genderList.add("Male");
        genderList.add("Female");
        ArrayAdapter arrayAdapter = new ArrayAdapter(spinner.getContext(), R.layout.spinner_item, genderList);
        spinner.setAdapter(arrayAdapter);
        if (profileDataServiceModel.getData().getGender().equals("male")) {
            spinner.setSelection(0);
        } else if (profileDataServiceModel.getData().getGender().equals("female")) {
            spinner.setSelection(1);
        }
    }

    @androidx.databinding.BindingAdapter("app:EditPrd_CategotyAdapter")
    public static void bindSpinnerAdapter(AppCompatSpinner spinner, ProductCategory_ServiceModel productCategory_serviceModel) {
        List<String> dataList = new ArrayList<>();
        if (productCategory_serviceModel != null) {
            try {
                int selectedPosition = productCategory_serviceModel.getData().get(0).getSelectedPosition();
                dataList.add(spinner.getContext().getResources().getString(R.string.selectcategory));
                if (productCategory_serviceModel.getData().size() > 0) {
                    for (int pc = 0; pc < productCategory_serviceModel.getData().size(); pc++) {
                        dataList.add(productCategory_serviceModel.getData().get(pc).getCat_name());

                        if (pc == productCategory_serviceModel.getData().size() - 1) {
                            ArrayAdapter arrayAdapter = new ArrayAdapter(spinner.getContext(), R.layout.spinner_item, dataList);
                            spinner.setAdapter(arrayAdapter);

                            if (selectedPosition != -1)
                                spinner.setSelection(selectedPosition);
                        }
                    }
                }
            } catch (Exception EE) {
                if (productCategory_serviceModel.getData().size() > 0) {
                    for (int pc = 0; pc < productCategory_serviceModel.getData().size(); pc++) {
                        dataList = new ArrayList<>();
                        dataList.add(spinner.getContext().getResources().getString(R.string.selectcategory));
                        dataList.add(productCategory_serviceModel.getData().get(pc).getCat_name());
                        if (pc == productCategory_serviceModel.getData().size() - 1) {
                            ArrayAdapter arrayAdapter = new ArrayAdapter(spinner.getContext(), R.layout.spinner_item, dataList);
                            spinner.setAdapter(arrayAdapter);
                        }
                    }
                }

            }
        }
    }

    @androidx.databinding.BindingAdapter("app:EditPrd_SubCategotyAdapter")
    public static void bindSpinnerAdapter(AppCompatSpinner spinner, SubCategoriesData_ServiceModel subCategoriesData_serviceModel) {
        List<String> dataList = new ArrayList<>();
        if (subCategoriesData_serviceModel != null && subCategoriesData_serviceModel.getData().size() > 0) {
            try {
                int selectedPosition = subCategoriesData_serviceModel.getData().get(0).getSelectedPosition();
                dataList.add(spinner.getContext().getResources().getString(R.string.selectcategory));
                if (subCategoriesData_serviceModel.getData().size() > 0) {
                    for (int pc = 0; pc < subCategoriesData_serviceModel.getData().size(); pc++) {
                        dataList.add(subCategoriesData_serviceModel.getData().get(pc).getSubcat_name());
                        if (pc == subCategoriesData_serviceModel.getData().size() - 1) {
                            ArrayAdapter arrayAdapter = new ArrayAdapter(spinner.getContext(), R.layout.spinner_item, dataList);
                            spinner.setAdapter(arrayAdapter);
                            if (selectedPosition != -1)
                                spinner.setSelection(selectedPosition);
                        }
                    }
                }
            } catch (Exception EE) {
                if (subCategoriesData_serviceModel.getData().size() > 0) {
                    dataList = new ArrayList<>();
                    dataList.add(spinner.getContext().getResources().getString(R.string.selectcategory));
                    for (int pc = 0; pc < subCategoriesData_serviceModel.getData().size(); pc++) {
                        dataList.add(subCategoriesData_serviceModel.getData().get(pc).getSubcat_name());
                        if (pc == subCategoriesData_serviceModel.getData().size() - 1) {
                            ArrayAdapter arrayAdapter = new ArrayAdapter(spinner.getContext(), R.layout.spinner_item, dataList);
                            spinner.setAdapter(arrayAdapter);
                        }
                    }
                }
            }

        }
    }

    @androidx.databinding.BindingAdapter("app:bindSpinnerAdapter_BigFont")
    public static void bindSpinnerAdapter_BigFont(AppCompatSpinner spinner, List list) {
        if (list != null) {
            ArrayAdapter arrayAdapter = new ArrayAdapter(spinner.getContext(), R.layout.spinner_item, list);
            spinner.setAdapter(arrayAdapter);
        }
    }

    @androidx.databinding.BindingAdapter("app:loadImage")
    public static void loadImage(ImageView imageView, String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            Glide.with(imageView.getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.image)
                    .into(imageView);
        }
    }

    @androidx.databinding.BindingAdapter("app:loadImageBitmap")
    public static void loadImageBitmap(ImageView imageView, Bitmap imageUrl) {
        if (imageUrl != null) {
            Glide.with(imageView.getContext())
                    .load(imageUrl)
                    .fitCenter()
                    .into(imageView);
        } else {
            Glide.with(imageView.getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.image)
                    .into(imageView);
        }
    }

    @androidx.databinding.BindingAdapter("app:loadImageCircle")
    public static void loadImageCircle(CircleImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .placeholder(R.mipmap.user)
                .into(imageView);
    }

    //BINDING_FUNCTION
    @androidx.databinding.BindingAdapter("app:videoFromPath")
    public static void videoFromPath(VideoView videoView, String videoPath) {
        if (videoPath != null) {
            videoView.setMediaController(new MediaController(videoView.getContext()));
            Uri u = Uri.parse(videoPath);
            videoView.setVideoURI(u);
            videoView.start();
        }
        if (URLUtil.isValidUrl(videoPath)) {
            GoogleLoader googleLoader = new GoogleLoader((Activity) videoView.getContext());
            googleLoader.showLoader();

            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    googleLoader.dismiss();
                }
            });
        }


    }

    @androidx.databinding.BindingAdapter("app:profileCircle")
    public static void profileCircle(CircleImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .placeholder(R.mipmap.user)
                .into(imageView);
    }

    @androidx.databinding.BindingAdapter("app:isLikedOrNot")
    public static void isLikedOrNot(ImageView imageView, String dataBean) {
        if (!TextUtils.isEmpty(dataBean)) {
            if (dataBean.equals("Like") || dataBean.equals("1")) {
                imageView.setBackground(imageView.getContext().getResources().getDrawable(R.mipmap.like_active));
            } else {
                imageView.setBackground(imageView.getContext().getResources().getDrawable(R.mipmap.like));
            }
        }
    }

    @androidx.databinding.BindingAdapter("app:switchButtonBinder")
    public static void switchButtonBinder(Switch aSwitch, boolean b) {
        if (b) {
            aSwitch.setChecked(b);
            aSwitch.setBackground(aSwitch.getContext().getResources().getDrawable(R.drawable.round_swich_button_active));
        } else {
            aSwitch.setChecked(b);
            aSwitch.setBackground(aSwitch.getContext().getResources().getDrawable(R.drawable.round_swich_button));
        }
    }

    @androidx.databinding.BindingAdapter("app:addressBinder")
    public static void addressBinder(RadioButton radioButton, AllAddress_ServiceModel.DataBean dataBean) {
        if (dataBean != null) {
            radioButton.setChecked(dataBean.isSelected());
            radioButton.setText(dataBean.getHouse_no() + ", " + dataBean.getAddress() + ", " + dataBean.getLandmark() + ", " + dataBean.getCity() + " ( " + dataBean.getPin_code() + " ), " + dataBean.getState() + ", " + dataBean.getCountry());
        }
    }

    @androidx.databinding.BindingAdapter("app:addressBinder")
    public static void addressBinder(TextView textView, AllAddress_ServiceModel.DataBean dataBean) {
        if (dataBean != null) {
            textView.setText(dataBean.getHouse_no() + ", " + dataBean.getAddress() + ", " + dataBean.getLandmark() + ", " + dataBean.getCity() + " ( " + dataBean.getPin_code() + " ), " + dataBean.getState() + ", " + dataBean.getCountry());
        }
    }

    @androidx.databinding.BindingAdapter("app:followerStatus")
    public static void followerStatus(TextView textView, String dataBean) {
        if (!TextUtils.isEmpty(dataBean)) {
            if (dataBean.equals("yes")) {
                textView.setText(textView.getContext().getResources().getString(R.string.unfollow));
            } else {
                textView.setText(textView.getContext().getResources().getString(R.string.follow));

            }
        }
    }

    @androidx.databinding.BindingAdapter("app:isWishedOrNot")
    public static void isWishedOrNot(ImageView imageView, String isText) {
        if (!TextUtils.isEmpty(isText)) {
            if (isText.equals("Wished") || isText.equals("1")) {
                imageView.setBackground(imageView.getContext().getResources().getDrawable(R.mipmap.heart_active));
            } else {
                imageView.setBackground(imageView.getContext().getResources().getDrawable(R.mipmap.heart_1));
            }
        }
    }

    @androidx.databinding.BindingAdapter("app:nameManipulation")
    public static void setText(TextView textView, SignIn_ServiceModel data) {
        if (data != null) {
            if (TextUtils.isEmpty(data.getData().getFull_name())) {
                textView.setText(textView.getContext().getString(R.string.welcometext));
            } else {
                textView.setText(textView.getContext().getString(R.string.welcometext) + " " + data.getData().getUser_name());
            }
        } else {
            textView.setText(textView.getContext().getString(R.string.welcometext));
        }
    }

    @androidx.databinding.BindingAdapter("app:htmlTextManipulator")
    public static void htmlTextManipulator(TextView textView, String value) {
        if (!TextUtils.isEmpty(value)) {
            textView.setText(Html.fromHtml(value));
        } else {
            textView.setText(textView.getContext().getString(R.string.somethingwentwrong));
        }
    }

    @androidx.databinding.BindingAdapter("app:dateManipulation")
    public static void setText(TextView textView, MyAds_ServiceModel.DataBean data) {
        if (data != null) {
            if (TextUtils.isEmpty(data.getCreated_at()) || TextUtils.isEmpty(data.getExpiry_date())) {
                textView.setText(textView.getContext().getString(R.string.norecordfound));
            } else {
                String value = textView.getContext().getString(R.string.from) + "<b> " + AlphaHolder.dateFormateChange(data.getCreated_at(), "yyyy-MM-dd HH:mm:ss", "dd MMM yyyy") + "</b>" + " - To - <b>" + AlphaHolder.dateFormateChange(data.getExpiry_date(), "yyyy-mm-dd", "dd MMM yyyy") + "</b>";
                textView.setText(Html.fromHtml(value));
            }
        } else {
            textView.setText(textView.getContext().getString(R.string.welcometext));
        }
    }

    @androidx.databinding.BindingAdapter("app:manipulateCheckBox")
    public static void setText(CheckBox checkBox, HomeFilter_ServiceModel.DataBean.SortbyrecBean data) {
        checkBox.setText(data.getTitle());
        checkBox.setChecked(data.isSelected());
    }

    @androidx.databinding.BindingAdapter("app:manipulateCheckBox")
    public static void setText(CheckBox checkBox, HomeFilter_ServiceModel.DataBean.CategrecBean data) {
        checkBox.setText(data.getCat_name());
        checkBox.setChecked(data.isSelected());
    }

    @androidx.databinding.BindingAdapter("app:manipulateCheckBox")
    public static void setText(CheckBox checkBox, HomeFilter_NL_ServiceModel.DataBean.SortbyrecBean data) {
        checkBox.setText(data.getTitle());
        checkBox.setChecked(data.isSelected());
    }

    @androidx.databinding.BindingAdapter("app:manipulateCheckBox")
    public static void setText(CheckBox checkBox, HomeFilter_NL_ServiceModel.DataBean.CategrecBean data) {
        checkBox.setText(data.getSlug());
        checkBox.setChecked(data.isSelected());
    }

    @androidx.databinding.BindingAdapter("app:followOrUnfollow")
    public static void followOrUnfollow(TextView textView, FollowerData_ServiceModel.DataBean data) {
        if (data.getIamfollowing().equals("yes")) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
        }
    }

    @androidx.databinding.BindingAdapter("app:isUserVerified")
    public static void isUserVerified(TextView textView, ProfileData_ServiceModel profileData_serviceModel) {
        if (null != profileData_serviceModel) {
            if (profileData_serviceModel.getData().getIs_verified().equals("1")) {
                textView.setText(textView.getContext().getString(R.string.verified));
            } else {
                textView.setText(textView.getContext().getString(R.string.notverified));

            }
        }
    }

    @androidx.databinding.BindingAdapter("app:locationFromLatLong")
    public static void locationFromLatLong(TextView editText, ProfileData_ServiceModel profileData_serviceModel) {
        try {
            if (!TextUtils.isEmpty(profileData_serviceModel.getData().getLatitude()) && !TextUtils.isEmpty(profileData_serviceModel.getData().getLatitude())) {
                String address = getAddress(editText.getContext(), profileData_serviceModel.getData().getLatitude(), profileData_serviceModel.getData().getLongitude());

                editText.setText(address);
            } else {
                editText.setText(editText.getContext().getString(R.string.locationnotfound));
            }
        } catch (Exception EE) {

        }
    }

    @androidx.databinding.BindingAdapter("app:dateOfBirth")
    public static void dateOfBirth(TextView textView, ProfileData_ServiceModel profileData_serviceModel) {
        try {
            if (!TextUtils.isEmpty(profileData_serviceModel.getData().getDob())) {
                textView.setText(profileData_serviceModel.getData().getDob());
            } else {
                textView.setText(textView.getContext().getString(R.string.dobnotfound));
            }
        } catch (Exception EE) {

        }
    }

    @androidx.databinding.BindingAdapter("app:ratingManipulate")
    public static void ratingManipulate(RatingBar ratingBar, ParamOptimizer_ViewModel paramOptimizer_viewModel) {
        try {
            ratingBar.setRating(paramOptimizer_viewModel.getRating().getValue());
        } catch (Exception EE) {

        }
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                paramOptimizer_viewModel.setRating(new MutableLiveData<>(rating));

            }
        });
    }

    @androidx.databinding.BindingAdapter("app:manipulateGender")
    public static void manipulateGender(Spinner spinner, ProfileData_ServiceModel profileData_serviceModel) {
        if (profileData_serviceModel != null) {
            if (profileData_serviceModel.getData().getGender().equalsIgnoreCase("male")) {
                spinner.setSelection(0, false);
            } else {
                spinner.setSelection(1, false);
            }
        }
    }


    @androidx.databinding.BindingAdapter("app:imageWithCustomRadius_bartering")
    public static void imageWithCustomRadius_bartering(ImageView imageView, String url) {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(20));
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(R.mipmap.question)
                .apply(requestOptions)
                .into(imageView);

        if (URLUtil.isValidUrl(url)) {
            imageView.setPadding(0, 0, 0, 0);
        } else {
            imageView.setPadding(15, 15, 15, 15);
        }

    }

    @androidx.databinding.BindingAdapter("app:imageWithCustomRadius_Bitmap")
    public static void imageWithCustomRadius_Bitmap(ImageView imageView, Bitmap url) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(16));
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(R.drawable.image)
                .apply(requestOptions)
                .into(imageView);
    }

    @androidx.databinding.BindingAdapter("app:imageWithCustomRadius_Bitmap_Overview")
    public static void imageWithCustomRadius_Bitmap_Overview(ImageView imageView, Bitmap url) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(21));
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(R.drawable.image)
                .apply(requestOptions)
                .into(imageView);
    }

    @androidx.databinding.BindingAdapter("app:imageWithCustomRadius_URL_Overview")
    public static void imageWithCustomRadius_URL_Overview(ImageView imageView, String url) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(21));
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(R.drawable.image)
                .apply(requestOptions)
                .into(imageView);
    }

    @androidx.databinding.BindingAdapter("app:mailManipulate")
    public static void mailManipulate(LinearLayout linearLayout, String mail) {
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!TextUtils.isEmpty(mail)) {
                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.setType("text/plain");
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{mail});
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
                        linearLayout.getContext().startActivity(Intent.createChooser(emailIntent, linearLayout.getContext().getString(R.string.sendmailwith)));
                    }
                } catch (Exception EE) {
                    AlphaHolder.customToast(linearLayout.getContext(), linearLayout.getContext().getString(R.string.sorrywefindingsomerestriction));
                }
            }
        });
    }

    @androidx.databinding.BindingAdapter("app:callToSeller")
    public static void callToSeller(LinearLayout linearLayout, String mobileNo) {
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mobileNo)) {
                    try {
                        Intent my_callIntent = new Intent(Intent.ACTION_DIAL);
                        my_callIntent.setData(Uri.parse("tel:+91" + mobileNo));
                        linearLayout.getContext().startActivity(my_callIntent);
                    } catch (ActivityNotFoundException e) {
                        AlphaHolder.customToast(linearLayout.getContext(), linearLayout.getContext().getString(R.string.cannotconnectcall));
                    }
                }
            }
        });
    }

    @androidx.databinding.BindingAdapter("app:openWhatsApp")
    public static void openWhatsApp(LinearLayout linearLayout, ProductDetails_ServiceModel.DataBean.PrdrecBean mobileNo) {
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mobileNo != null) {
                    try {
                        Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + "+" + mobileNo.getSellercountrycode() + mobileNo.getSellermobile() + "&text=" + "");
                        Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
                        linearLayout.getContext().startActivity(sendIntent);

                    } catch (ActivityNotFoundException e) {
                        AlphaHolder.customToast(linearLayout.getContext(), linearLayout.getContext().getString(R.string.cannotconnectcall));
                    }
                }
            }
        });
    }

    @androidx.databinding.BindingAdapter("app:imageWithCustomRadiusBitmap")
    public static void imageWithCustomRadiusBitmap(ImageView imageView, Bitmap url) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(16));
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(R.drawable.image)
                .apply(requestOptions)
                .into(imageView);
    }

    @androidx.databinding.BindingAdapter("app:radiusBitmap_ImageVideo")
    public static void radiusBitmap_ImageVideo(ImageView imageView, MediaData_HolderModel mediaData_holderModel) {
        if (mediaData_holderModel != null) {
            if (mediaData_holderModel.getMediaType().equals("VIDEO") || mediaData_holderModel.getMediaType().equals("video")) {
                RequestOptions requestOptions = new RequestOptions();
                requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(7));
                Glide.with(imageView.getContext())
                        .load(R.drawable.video)
                        .apply(requestOptions)
                        .into(imageView);
            } else {
                RequestOptions requestOptions = new RequestOptions();
                requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(7));
                Glide.with(imageView.getContext())
                        .load(mediaData_holderModel.getUriPath())
                        .placeholder(R.drawable.image)
                        .apply(requestOptions)
                        .into(imageView);

            }
        }
    }

    @androidx.databinding.BindingAdapter("app:loadImageFromUri")
    public static void radiusBitmap_Image(ImageView imageView, String uri) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(7));
        Glide.with(imageView.getContext())
                .load(uri)
                .placeholder(R.drawable.image)
                .apply(requestOptions)
                .into(imageView);
    }

    public static String getAddress(Context context, String LATITUDE, String LONGITUDE) {
        List<String> addressIs = new ArrayList<>();
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(Double.parseDouble(LATITUDE), Double.parseDouble(LONGITUDE), 1);
            if (addresses != null && addresses.size() > 0) {
                addressIs.add(addresses.get(0).getAddressLine(0));
                //          addressIs.add(addresses.get(0).getLocality());
                //            addressIs.add(addresses.get(0).getAdminArea());
                //               addressIs.add(addresses.get(0).getCountryName());
                //   addressIs.add(addresses.get(0).getPostalCode());
                // addressIs.add(addresses.get(0).getFeatureName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return TextUtils.join(",", addressIs);
    }

}
