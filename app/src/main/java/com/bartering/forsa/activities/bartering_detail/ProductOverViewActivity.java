package com.bartering.forsa.activities.bartering_detail;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.URLUtil;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bartering.forsa.AppCompactActivity;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.activities.MainActivity;
import com.bartering.forsa.activities.boots_Section.MyAdsActivity;
import com.bartering.forsa.activities.camera_section.MediaData_HolderModel;
import com.bartering.forsa.databinding.ActivityProductOverviewBinding;
import com.bartering.forsa.databinding.CongratualtionLayoutBinding;
import com.bartering.forsa.recyclerViewAdapter.ProductOverviewImages_RecyclerViewAdapter;
import com.bartering.forsa.recyclerViewAdapter.ShowBarteringImages_RecyclerViewAdapter;
import com.bartering.forsa.recyclerViewAdapter.SubscriptionPlans_RecyclerViewAdapter;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.ProductDetails_ServiceModel;
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

public class ProductOverViewActivity extends AppCompactActivity implements View.OnClickListener, ClickListener, Observer<Object> {

    ActivityProductOverviewBinding activityProductOverviewBinding;
    SubscriptionPlans_RecyclerViewAdapter subscriptionPlans_recyclerViewAdapter;
    ShowBarteringImages_RecyclerViewAdapter showBarteringImages_recyclerViewAdapter;

    ProductOverviewImages_RecyclerViewAdapter productOverviewImages_recyclerViewAdapter;
    ArrayList<MediaData_HolderModel> mediaData_holderModels;
    LinearLayoutManager linearLayoutManager;

    @Inject
    ViewModelFactory vmFactory;
    ApiCaller viewModel;
    ArrayList<MediaData_HolderModel> imageData;
    ArrayList<MediaData_HolderModel> videoData;
    String category_id, product_id;
    String barteringStatus = null, tradeStatus = null, price = "";

    Dialog bottomSliderDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProductOverviewBinding = DataBindingUtil.setContentView(this, R.layout.activity_product_overview);
        activityProductOverviewBinding.setClickListener(this::onClick);
        activityProductOverviewBinding.setLoggedInUserImage(URLUtil.isValidUrl(SharedPreferences_Util.getUser_Image(ProductOverViewActivity.this)) ? SharedPreferences_Util.getUser_Image(ProductOverViewActivity.this) : "http://dev.rglabs.net/forsa/uploads/user/" + SharedPreferences_Util.getUser_Image(ProductOverViewActivity.this));

        getProductData();
        //cameraInitiation(); ////for integrate camera un comment it
        listener();


    }

    @Override
    protected void onResume() {
        super.onResume();

        AlphaHolder.isFromCalled = "";
        getProductsData();
        manipulate();
        captureDataShowInitiation();
        //////if from bartering then bartering product would be add so we just make compulsory for it
        if (AlphaHolder.isFromMyAds.equals("BARTERING_PROCESS")) {
            activityProductOverviewBinding.barteringCheckBoxId.setChecked(true);
            barteringStatus = "yes";
            tradeStatus = "no";
            price = "";
            activityProductOverviewBinding.priceEditTextId.setVisibility(View.GONE);
            activityProductOverviewBinding.barteringCheckBoxId.setEnabled(false);
            activityProductOverviewBinding.tradeCheckBoxId.setEnabled(false);
        }
    }

    private void getProductData() {
        if (getIntent().getExtras() != null) {
            category_id = getIntent().getExtras().getString("category_id");
            product_id = getIntent().getExtras().getString("product_id");
        }
    }

    private void getProductsData() {
        mediaData_holderModels = new SharedPreferences_Util(ProductOverViewActivity.this).getMediaData();
        manipulateFirstImage();
    }

    void manipulateFirstImage() {
        boolean isImageFound = false;
        if (mediaData_holderModels.size() > 0) {
            for (int image = 0; image < mediaData_holderModels.size(); image++) {
                if (mediaData_holderModels.get(image) != null) {
                    if (mediaData_holderModels.get(image).getMediaType().equals("image") || mediaData_holderModels.get(image).getMediaType().equals("IMAGE")) {
                        activityProductOverviewBinding.setFirstImageToShow(mediaData_holderModels.get(image).getMediaImage());
                        isImageFound = true;
                    }
                }
                if (image == mediaData_holderModels.size() - 1) {
                    if (!isImageFound) {
                        instructionDialog();
                    }
                }
            }
        }

    }

    private void instructionDialog() {
        bottomSliderDialog = new Dialog(ProductOverViewActivity.this, R.style.DialogAnimation);
        bottomSliderDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        bottomSliderDialog.setCancelable(false);
        CongratualtionLayoutBinding congratualtionLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(ProductOverViewActivity.this), R.layout.congratualtion_layout, null, false);
        bottomSliderDialog.setContentView(congratualtionLayoutBinding.getRoot());

        congratualtionLayoutBinding.headingDialogTextViewId.setText(getResources().getString(R.string.instruction));
        congratualtionLayoutBinding.textDialogTextViewId.setText(getResources().getString(R.string.instruction_text));

        congratualtionLayoutBinding.okTextViewId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSliderDialog.dismiss();
            }
        });


        bottomSliderDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(bottomSliderDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        lp.windowAnimations = R.style.DialogAnimation;
        bottomSliderDialog.getWindow().setAttributes(lp);
    }

    private void captureDataShowInitiation() {
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        linearLayoutManager.setReverseLayout(true);

        activityProductOverviewBinding.recyclerviewId.setHasFixedSize(true);
        activityProductOverviewBinding.recyclerviewId.setLayoutManager(linearLayoutManager);
        productOverviewImages_recyclerViewAdapter = new ProductOverviewImages_RecyclerViewAdapter(ProductOverViewActivity.this, mediaData_holderModels, this, false);
        activityProductOverviewBinding.recyclerviewId.setAdapter(productOverviewImages_recyclerViewAdapter);

    }

   /* private void cameraInitiation() {  ///un comment it and extand camera class like choose bartering item camera activity
        onCreateActivity(this);
        videoWidth = 720;
        videoHeight = 1280;
        cameraWidth = 1280;
        cameraHeight = 720;
    }*/

    private void manipulate() {
        activityProductOverviewBinding.setCategoryText(ProductDetail_UploadActivity.paramOptimizer_viewModel.getProductCategory().getValue() + " > " + ProductDetail_UploadActivity.paramOptimizer_viewModel.getProductSubCategory().getValue() + " > " + ProductDetail_UploadActivity.paramOptimizer_viewModel.getProductName().getValue());
        activityProductOverviewBinding.setParamOptimizer(ProductDetail_UploadActivity.paramOptimizer_viewModel);
    }

    @Override
    public void onClick(View view) {
        if (view == activityProductOverviewBinding.backLLId) {
            ProductOverViewActivity.this.finish();
        }
    }

    private void listener() {
        activityProductOverviewBinding.backLLId.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("IMAGE_DONE")) {
            MediaData_HolderModel mediaData_holderModel = (MediaData_HolderModel) object;
            mediaData_holderModels.add(1, mediaData_holderModel);
            productOverviewImages_recyclerViewAdapter.notifyItemInserted(position);
            productOverviewImages_recyclerViewAdapter.notifyItemRangeChanged(1, mediaData_holderModels.size());
            activityProductOverviewBinding.recyclerviewId.scrollToPosition(0);
        }
        if (callerIdentity.equals("VIDEO_DONE")) {
            MediaData_HolderModel mediaData_holderModel = (MediaData_HolderModel) object;
            mediaData_holderModels.add(1, mediaData_holderModel);
            productOverviewImages_recyclerViewAdapter.notifyItemInserted(position);
            productOverviewImages_recyclerViewAdapter.notifyItemRangeChanged(1, mediaData_holderModels.size());
            activityProductOverviewBinding.recyclerviewId.scrollToPosition(0);
        }
        if (callerIdentity.equals("event3")) {
            new SharedPreferences_Util(ProductOverViewActivity.this).saveProductData_ForShow(mediaData_holderModels);
            Intent intent = new Intent(ProductOverViewActivity.this, ViewBarteringImageVideoActivity.class);
            intent.putExtra("POSITION", "" + position);
            startActivity(intent);
            overridePendingTransition(0, 0);
            /*MediaData_HolderModel mediaData_holderModel = (MediaData_HolderModel) object;
            if (mediaData_holderModel.getMediaType().equals("IMAGE")) {
                activityProductOverviewBinding.setImagePusher(mediaData_holderModel.getMediaImage());
                activityProductOverviewBinding.setVideoPusher(null);
                dataModelUpdator(position);
            }
            if (mediaData_holderModel.getMediaType().equals("VIDEO")) {
                Log.e("CLICKED ON", "VIDEO " + position);
                activityProductOverviewBinding.setVideoPusher(mediaData_holderModel.getUriPath());
                activityProductOverviewBinding.setImagePusher(null);
                dataModelUpdator(position);
            }*/
        }
        if (callerIdentity.equals("event4")) {
            /*activityProductOverviewBinding.setImagePusher(null);
            activityProductOverviewBinding.setVideoPusher(null);
            dataModelUpdator(-1);*/
            new SharedPreferences_Util(ProductOverViewActivity.this).saveProductData_ForShow(mediaData_holderModels);
            AlphaHolder.isFromCalled = "PRODUCT_OVERVIEW";
            Intent intent = new Intent(ProductOverViewActivity.this, ChooseBarteringItemCameraActivity.class);
            startActivity(intent);

        }
        if (callerIdentity.equals("event9")) {
            price = activityProductOverviewBinding.priceEditTextId.getText().toString();
            if (TextUtils.isEmpty(price) && tradeStatus.equals("yes")) {
                AlphaHolder.customToast(ProductOverViewActivity.this, getString(R.string.pricecannotbeempty));
            } else {
                uploadProductData();
            }
        }
        if (callerIdentity.equals("event5")) { ///Bartering Checkbox
            if (!AlphaHolder.isFromMyAds.equals("BARTERING_PROCESS")) {
                boolean isChecked = activityProductOverviewBinding.barteringCheckBoxId.isChecked();
                if (isChecked) {

                    activityProductOverviewBinding.barteringCheckBoxId.setChecked(true);
                    activityProductOverviewBinding.tradeCheckBoxId.setChecked(false);
                    barteringStatus = "yes";
                    tradeStatus = "no";
                    price = "";
                    activityProductOverviewBinding.priceEditTextId.setVisibility(View.GONE);

                } else {

                }

            }
        }
        if (callerIdentity.equals("event6")) { ///Trade Checkbox
            if (!AlphaHolder.isFromMyAds.equals("BARTERING_PROCESS")) {
                boolean isChecked = activityProductOverviewBinding.tradeCheckBoxId.isChecked();
                if (isChecked) {
                    activityProductOverviewBinding.barteringCheckBoxId.setChecked(false);
                    activityProductOverviewBinding.tradeCheckBoxId.setChecked(true);

                    barteringStatus = "no";
                    tradeStatus = "yes";
                    activityProductOverviewBinding.priceEditTextId.setVisibility(View.VISIBLE);

                } else {

                }
            }
        }
        if (callerIdentity.equals("event7")) { ///remove button
            if (mediaData_holderModels.size() > 0) {
                mediaData_holderModels.remove(position);
                productOverviewImages_recyclerViewAdapter.notifyItemRemoved(position);
                productOverviewImages_recyclerViewAdapter.notifyItemRangeRemoved(position, mediaData_holderModels.size());
            }
        }
        if (callerIdentity.equals("event10")) { ///remove button
            AlphaHolder.redirectToProfile(ProductOverViewActivity.this);
        }
    }

    private void dataModelUpdator(int position) {
        if (position == -1) {
            for (int i = 0; i < mediaData_holderModels.size(); i++) {
                if (mediaData_holderModels.get(i) != null)
                    mediaData_holderModels.get(i).setInFocus(false);
            }
        } else {
            for (int i = 0; i < mediaData_holderModels.size(); i++) {
                if (i == position) {
                    if (mediaData_holderModels.get(i) != null) {
                        mediaData_holderModels.get(i).setInFocus(true);
                    }
                } else {
                    if (mediaData_holderModels.get(i) != null)
                        mediaData_holderModels.get(i).setInFocus(false);
                }
            }
        }
        productOverviewImages_recyclerViewAdapter.notifyDataSetChanged();
    }

    private void uploadProductData() {
        RequestBody tradeRB = null;
        RequestBody priceRB = null;
        RequestBody bateringRB = null;

        RequestBody token = RequestBody.create(MediaType.parse("text/plain"), SharedPreferences_Util.getToken(ProductOverViewActivity.this));
        RequestBody prd_id = RequestBody.create(MediaType.parse("text/plain"), product_id);
        tradeRB = RequestBody.create(MediaType.parse("text/plain"), tradeStatus);
        priceRB = RequestBody.create(MediaType.parse("text/plain"), price);
        bateringRB = RequestBody.create(MediaType.parse("text/plain"), barteringStatus);

        HashMap<String, RequestBody> paramMap = new HashMap<>();
        paramMap.put("trade", tradeRB);
        paramMap.put("price", priceRB);
        paramMap.put("batering", bateringRB);
        paramMap.put("token", token);
        paramMap.put("prd_id", prd_id);


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
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadDataRequestBody("UPLAOD_PRODUCT_DATA", paramMap, imagesListToSend, videoListToSend, true, ProductOverViewActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        if (resultData.getTag().equals("UPLAOD_PRODUCT_DATA")) {
            ProductDetails_ServiceModel productDetails_serviceModel = (ProductDetails_ServiceModel) resultData.getRootData().getValue();
            if (productDetails_serviceModel != null) {
                ProductOverViewActivity.this.finish();

                if (!TextUtils.isEmpty(AlphaHolder.isFromMyAds) && AlphaHolder.isFromMyAds.equals("MY_ADS")) {
                    Intent intent = new Intent(ProductOverViewActivity.this, MyAdsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(0, 0);

                } else if (!TextUtils.isEmpty(AlphaHolder.isFromMyAds) && AlphaHolder.isFromMyAds.equals("HOME")) {
                    Intent intent = new Intent(ProductOverViewActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                } else if (!TextUtils.isEmpty(AlphaHolder.isFromMyAds) && AlphaHolder.isFromMyAds.equals("BARTERING_PROCESS")) {

                    ProductOverViewActivity.this.finish();
                    Intent intent = new Intent(ProductOverViewActivity.this, ChooseBarteringItemSuggestionActivity.class);
                    intent.putExtra("ADDED_DATA", productDetails_serviceModel);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
                AlphaHolder.customToast(ProductOverViewActivity.this, productDetails_serviceModel.getMessage());

            }
        }
    }
}