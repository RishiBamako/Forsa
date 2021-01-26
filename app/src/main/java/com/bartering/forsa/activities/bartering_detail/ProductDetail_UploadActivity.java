package com.bartering.forsa.activities.bartering_detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bartering.forsa.AppCompactActivity;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.activities.camera_section.MediaData_HolderModel;
import com.bartering.forsa.databinding.ActivityUploadProductDetailBinding;
import com.bartering.forsa.mutableViewModel.ParamOptimizer_ViewModel;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.Comman_ServiceModel;
import com.bartering.forsa.retrofit.service_model.UploadProduct_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.SharedPreferences_Util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProductDetail_UploadActivity extends AppCompactActivity implements ClickListener, Observer<Object> {

    public static ParamOptimizer_ViewModel paramOptimizer_viewModel;
    ActivityUploadProductDetailBinding activityUploadProductDetailBinding;
    ArrayList<MediaData_HolderModel> mediaData_holderModels;
    ArrayList<MediaData_HolderModel> imageData;
    ArrayList<MediaData_HolderModel> videoData;
    String product_Id;

    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;
    Comman_ServiceModel comman_serviceModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        paramOptimizer_viewModel = new ParamOptimizer_ViewModel();
        activityUploadProductDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_upload_product_detail);
        activityUploadProductDetailBinding.setParamOptimizer(paramOptimizer_viewModel);
        activityUploadProductDetailBinding.setClickListener(this::onClick);
        AlphaHolder.stackList.add(this);


        getMediaData();
    }

    private void getMediaData() {
        mediaData_holderModels = new SharedPreferences_Util(ProductDetail_UploadActivity.this).getMediaData();
    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event1")) {
            if (TextUtils.isEmpty(paramOptimizer_viewModel.getProductName().getValue())) {
                AlphaHolder.customToast(ProductDetail_UploadActivity.this, getResources().getString(R.string.productnamemustrequire));
            } else if (TextUtils.isEmpty(paramOptimizer_viewModel.getProductDescription().getValue())) {
                AlphaHolder.customToast(ProductDetail_UploadActivity.this, getResources().getString(R.string.productdescriptionmustrequire));
            } else {
                initiateUploadProcess();
            }


        }
        if (callerIdentity.equals("event2")) {
            ProductDetail_UploadActivity.this.finish();
        }
    }

    private void initiateUploadProcess() {
        List<MultipartBody.Part> imagesListToSend = new ArrayList<>();
        List<MultipartBody.Part> videoListToSend = new ArrayList<>();
        imageData = new ArrayList<>();
        videoData = new ArrayList<>();

        for (int i = 0; i < mediaData_holderModels.size(); i++) {
            MediaData_HolderModel mediaData_holderModel = mediaData_holderModels.get(i); //// on 0 index we have added null value because we just need to show camera view
            if (mediaData_holderModel != null) {
                if (mediaData_holderModel.getMediaType().equals("IMAGE")) {
                    imageData.add(mediaData_holderModel);
                } else {
                    videoData.add(mediaData_holderModel);
                }
                if (i == mediaData_holderModels.size() - 1) {
                    if (imageData.size() > 0) {
                        for (int j = 0; j < imageData.size(); j++) {
                            MediaData_HolderModel imageModel = imageData.get(j);
                            File profileFile = new File(imageModel.getUriPath());
                            MultipartBody.Part partImage = MultipartBody.Part.createFormData("prd_image[" + j + "]", profileFile.getName(), RequestBody.create(MediaType.parse("image/*"), profileFile));
                            imagesListToSend.add(partImage);
                        }
                    }
                    if (videoData.size() > 0) {
                        for (int k = 0; k < videoData.size(); k++) {
                            MediaData_HolderModel videoModel = videoData.get(k);
                            File profileFile = new File(videoModel.getUriPath());
                            MultipartBody.Part partImage = MultipartBody.Part.createFormData("prd_video[" + k + "]", profileFile.getName(), RequestBody.create(MediaType.parse("video/mp4"), profileFile));
                            videoListToSend.add(partImage);
                        }
                    }
                }
            }
        }

        RequestBody productName = RequestBody.create(MediaType.parse("text/plain"), paramOptimizer_viewModel.getProductName().getValue());
        RequestBody productDescription = RequestBody.create(MediaType.parse("text/plain"), paramOptimizer_viewModel.getProductDescription().getValue());
        RequestBody token = RequestBody.create(MediaType.parse("text/plain"), SharedPreferences_Util.getToken(ProductDetail_UploadActivity.this));

        HashMap<String, RequestBody> paramMap = new HashMap<>();
        paramMap.put("name", productName);
        paramMap.put("description", productDescription);
        paramMap.put("token", token);

        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadDataRequestBody("UPLOAD_BARTERING_MEDIA", paramMap, imagesListToSend, videoListToSend, true, ProductDetail_UploadActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        if (resultData.getTag().equals("UPLOAD_BARTERING_MEDIA")) {
            UploadProduct_ServiceModel uploadProduct_serviceModel = (UploadProduct_ServiceModel) resultData.getRootData().getValue();
            if (uploadProduct_serviceModel.isStatus().equals("true")) {

                product_Id = uploadProduct_serviceModel.getData().getPrd_id();
                Intent intent = new Intent(ProductDetail_UploadActivity.this, ProductsCategoriesActivity.class);
                intent.putExtra("product_id", product_Id);
                intent.putExtra("FROM", "PRODUCT_UPLOAD");

                for (int s = 0; s < AlphaHolder.stackList.size(); s++) {
                    Activity activity = AlphaHolder.stackList.get(s);
                    activity.finish();
                }

                startActivity(intent);
                overridePendingTransition(0, 0);


            }
            AlphaHolder.customToast(ProductDetail_UploadActivity.this, uploadProduct_serviceModel.getMessage());

        }
    }
}