package com.bartering.forsa.activities.bartering_detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.AdapterView;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bartering.forsa.AppCompactActivity;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.activities.camera_section.MediaData_HolderModel;
import com.bartering.forsa.databinding.ActivityProductEditBinding;
import com.bartering.forsa.models.Download_Media;
import com.bartering.forsa.recyclerViewAdapter.ProductOverviewImages_RecyclerViewAdapter;
import com.bartering.forsa.recyclerViewAdapter.ProductSubCategories_RecyclerViewAdapter;
import com.bartering.forsa.recyclerViewAdapter.ShowBarteringImages_RecyclerViewAdapter;
import com.bartering.forsa.recyclerViewAdapter.SubscriptionPlans_RecyclerViewAdapter;
import com.bartering.forsa.retrofit.ApiCaller;
import com.bartering.forsa.retrofit.ResultData;
import com.bartering.forsa.retrofit.ViewModelFactory;
import com.bartering.forsa.retrofit.service_model.Comman_ServiceModel;
import com.bartering.forsa.retrofit.service_model.MyAds_ServiceModel;
import com.bartering.forsa.retrofit.service_model.ProductCategory_ServiceModel;
import com.bartering.forsa.retrofit.service_model.ProductDetails_ServiceModel;
import com.bartering.forsa.retrofit.service_model.SubCategoriesData_ServiceModel;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.SharedPreferences_Util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EditProductActivity extends AppCompactActivity /*BaseCameraActivity*/ implements View.OnClickListener, ClickListener, Observer<Object>, AdapterView.OnItemSelectedListener {

    ActivityProductEditBinding activityProductEditBinding;
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
    String category_id, subCategory_id;
    String barteringStatus = null, tradeStatus = null, price = null;
    MyAds_ServiceModel.DataBean productInformation;
    ProductDetails_ServiceModel productDetails_serviceModel;

    ProductCategory_ServiceModel productCategory_serviceModel;

    SubCategoriesData_ServiceModel subCategoriesData_serviceModel;

    ProductSubCategories_RecyclerViewAdapter productSubCategories_recyclerViewAdapter;
    int spinnerCheck = 0;

    List<Download_Media> download_mediaList;
    int removePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProductEditBinding = DataBindingUtil.setContentView(this, R.layout.activity_product_edit);
        activityProductEditBinding.setClickListener(this::onClick);
        activityProductEditBinding.setLoggedInUserImage(URLUtil.isValidUrl(SharedPreferences_Util.getUser_Image(EditProductActivity.this)) ? SharedPreferences_Util.getUser_Image(EditProductActivity.this) : "http://dev.rglabs.net/forsa/uploads/user/" + SharedPreferences_Util.getUser_Image(EditProductActivity.this));


        mediaData_holderModels = new ArrayList<>();
        mediaData_holderModels.add(null);


        getProductData();
        //     cameraInitiation();
        listener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(AlphaHolder.isFromCalled)) {
            AlphaHolder.isFromCalled = "";
            mediaData_holderModels = new SharedPreferences_Util(EditProductActivity.this).getMediaData();
            if (mediaData_holderModels != null) {
                if (mediaData_holderModels.size() > 0)
                    addToRecyclerView(mediaData_holderModels);
            }
        }
    }

    private void getProductData() {
        download_mediaList = new ArrayList<>();
        if (getIntent().getExtras() != null) {
            productInformation = (MyAds_ServiceModel.DataBean) getIntent().getSerializableExtra("ADS_DATA");
            if (!TextUtils.isEmpty(productInformation.getId()))
                getProductDetail();
        }
    }

    public void getProductDetail() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("prd_id", productInformation.getId());
        paramMap.put("token", SharedPreferences_Util.getToken(EditProductActivity.this));
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("PRODUCT_DETAILS", paramMap, true, this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    private void cametaInitiation() {
        activityProductEditBinding.recyclerviewId.setHasFixedSize(true);
        activityProductEditBinding.recyclerviewId.setLayoutManager(linearLayoutManager);
        productOverviewImages_recyclerViewAdapter = new ProductOverviewImages_RecyclerViewAdapter(EditProductActivity.this, mediaData_holderModels, this, true);
        activityProductEditBinding.recyclerviewId.setAdapter(productOverviewImages_recyclerViewAdapter);

    }

  /*  private void cameraInitiation() {
        onCreateActivity(this);
        videoWidth = 720;
        videoHeight = 1280;
        cameraWidth = 1280;
        cameraHeight = 720;
    }*/

    private void manipulate() {
        activityProductEditBinding.setCategoryText(ProductDetail_UploadActivity.paramOptimizer_viewModel.getProductCategory().getValue() + " > " + ProductDetail_UploadActivity.paramOptimizer_viewModel.getProductSubCategory().getValue() + " > " + ProductDetail_UploadActivity.paramOptimizer_viewModel.getProductName().getValue());
        activityProductEditBinding.setParamOptimizer(ProductDetail_UploadActivity.paramOptimizer_viewModel);
    }

    @Override
    public void onClick(View view) {
        if (view == activityProductEditBinding.backLLId) {
            EditProductActivity.this.finish();
        }
    }

    private void listener() {
        activityProductEditBinding.backLLId.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("IMAGE_DONE")) {
            MediaData_HolderModel mediaData_holderModel = (MediaData_HolderModel) object;
            mediaData_holderModels.add(1, mediaData_holderModel);
            productOverviewImages_recyclerViewAdapter.notifyItemInserted(position);
            productOverviewImages_recyclerViewAdapter.notifyItemRangeChanged(1, mediaData_holderModels.size());
            activityProductEditBinding.recyclerviewId.scrollToPosition(0);
        }
        if (callerIdentity.equals("VIDEO_DONE")) {
            MediaData_HolderModel mediaData_holderModel = (MediaData_HolderModel) object;
            mediaData_holderModels.add(1, mediaData_holderModel);
            productOverviewImages_recyclerViewAdapter.notifyItemInserted(position);
            productOverviewImages_recyclerViewAdapter.notifyItemRangeChanged(1, mediaData_holderModels.size());
            activityProductEditBinding.recyclerviewId.scrollToPosition(0);

        }
        if (callerIdentity.equals("event3")) {
            new SharedPreferences_Util(EditProductActivity.this).saveProductData_ForShow(mediaData_holderModels);
            Intent intent = new Intent(EditProductActivity.this, ViewBarteringImageVideoActivity.class);
            intent.putExtra("POSITION", "" + position);
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
        if (callerIdentity.equals("event4")) {

            new SharedPreferences_Util(EditProductActivity.this).saveProductData_ForShow(mediaData_holderModels);
            AlphaHolder.isFromCalled = "PRODUCT_EDIT";
            Intent intent = new Intent(EditProductActivity.this, ChooseBarteringItemCameraActivity.class);
            startActivity(intent);

            /*activityProductEditBinding.setImagePusher(null);
            activityProductEditBinding.setVideoPusher(null);
            dataModelUpdator(-1);*/
        }
        if (callerIdentity.equals("event9")) {
            price = activityProductEditBinding.priceEditTextId.getText().toString();
            if (TextUtils.isEmpty(productDetails_serviceModel.getData().getPrdrec().get(0).getTitle())) {
                AlphaHolder.customToast(EditProductActivity.this, getString(R.string.productnamemustrequire));
            } else if (TextUtils.isEmpty(productDetails_serviceModel.getData().getPrdrec().get(0).getDescription())) {
                AlphaHolder.customToast(EditProductActivity.this, getString(R.string.productdescriptionmustrequire));
            } else if (TextUtils.isEmpty(category_id)) {
                AlphaHolder.customToast(EditProductActivity.this, getString(R.string.product_category_mustrequire));
            } else if (TextUtils.isEmpty(subCategory_id)) {
                AlphaHolder.customToast(EditProductActivity.this, getString(R.string.product_subcategory_mustrequire));
            } else if (TextUtils.isEmpty(barteringStatus) && TextUtils.isEmpty(tradeStatus)) {
                AlphaHolder.customToast(EditProductActivity.this, getString(R.string.please_select_product_tradetype));
            } else if (TextUtils.isEmpty(price) && tradeStatus.equals("yes")) {
                AlphaHolder.customToast(EditProductActivity.this, getString(R.string.pricecannotbeempty));
            } else {
                uploadProductData();
            }
        }
        if (callerIdentity.equals("event5")) { ///Bartering Checkbox
            boolean isChecked = activityProductEditBinding.barteringCheckBoxId.isChecked();
            if (isChecked) {

                activityProductEditBinding.barteringCheckBoxId.setChecked(true);
                activityProductEditBinding.tradeCheckBoxId.setChecked(false);
                barteringStatus = "yes";
                tradeStatus = "no";
                price = null;
                activityProductEditBinding.priceEditTextId.setVisibility(View.GONE);

            } else {

            }

        }
        if (callerIdentity.equals("event6")) { ///Trade Checkbox
            boolean isChecked = activityProductEditBinding.tradeCheckBoxId.isChecked();
            if (isChecked) {
                activityProductEditBinding.barteringCheckBoxId.setChecked(false);
                activityProductEditBinding.tradeCheckBoxId.setChecked(true);

                barteringStatus = "no";
                tradeStatus = "yes";
                activityProductEditBinding.priceEditTextId.setVisibility(View.VISIBLE);

            } else {

            }
        }
        if (callerIdentity.equals("event7")) { ///Remove image or video
            removePosition = position;
            MediaData_HolderModel mediaData_holderModel = (MediaData_HolderModel) object;
            if (URLUtil.isValidUrl(mediaData_holderModel.getUriPath())) {
                removeImageOrVideo(mediaData_holderModel.getMedia_id());
            } else {
                mediaData_holderModels.remove(removePosition);
                productOverviewImages_recyclerViewAdapter.notifyItemRemoved(removePosition);
                productOverviewImages_recyclerViewAdapter.notifyItemRangeRemoved(removePosition, mediaData_holderModels.size());
            }

        }
        if (callerIdentity.equals("event10")) { ///Remove image or video

         /*   Intent intent = new Intent(EditProductActivity.this, ProductsCategoriesActivity.class);
            intent.putExtra("FROM", "EDIT_PRODUCT");
            intent.putExtra("product_id", productDetails_serviceModel.getData().getPrdrec().get(0).getPrd_id());

            if (productDetails_serviceModel.getData().getCategory() != null && productDetails_serviceModel.getData().getCategory().size() > 0)
                intent.putExtra("CATEGORY_DATE", productDetails_serviceModel.getData().getCategory().get(0));

            if (productDetails_serviceModel.getData().getCategory() != null && productDetails_serviceModel.getData().getCategory().size() > 0)
                intent.putExtra("SUB_CATEGORY_DATE", productDetails_serviceModel.getData().getSubcategory().get(0));

            startActivity(intent);
            overridePendingTransition(0, 0);*/
        }if (callerIdentity.equals("event11")) { ///Remove image or video
            AlphaHolder.redirectToProfile(EditProductActivity.this);

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
        RequestBody imageIdRB = null;
        RequestBody videoIdIdRB = null;
        RequestBody priceRB = null;
        RequestBody bateringRB = null, subcategoryRB = null, thirdcategoryRB = null, productNameRB = null, productdescriptionRB = null;

        RequestBody token = RequestBody.create(MediaType.parse("text/plain"), SharedPreferences_Util.getToken(EditProductActivity.this));
        RequestBody prd_id = RequestBody.create(MediaType.parse("text/plain"), productInformation.getId());
        tradeRB = RequestBody.create(MediaType.parse("text/plain"), tradeStatus);
        priceRB = RequestBody.create(MediaType.parse("text/plain"), price);
        bateringRB = RequestBody.create(MediaType.parse("text/plain"), barteringStatus);
        subcategoryRB = RequestBody.create(MediaType.parse("text/plain"), category_id);
        thirdcategoryRB = RequestBody.create(MediaType.parse("text/plain"), subCategory_id);
        productNameRB = RequestBody.create(MediaType.parse("text/plain"), productDetails_serviceModel.getData().getPrdrec().get(0).getTitle());
        productdescriptionRB = RequestBody.create(MediaType.parse("text/plain"), productDetails_serviceModel.getData().getPrdrec().get(0).getDescription());
        RequestBody lang = RequestBody.create(MediaType.parse("text/plain"), SharedPreferences_Util.getToken(EditProductActivity.this));

        HashMap<String, RequestBody> paramMap = new HashMap<>();
        paramMap.put("trade", tradeRB);
        paramMap.put("name", productNameRB);
        paramMap.put("description", productdescriptionRB);
        paramMap.put("subcat_id", subcategoryRB);
        paramMap.put("third_category", thirdcategoryRB);
        paramMap.put("price", priceRB);
        paramMap.put("bartering", bateringRB);
        paramMap.put("token", token);
        paramMap.put("prd_id", prd_id);
        paramMap.put("lang", lang);

        List<MultipartBody.Part> imagesListToSend = new ArrayList<>();
        List<MultipartBody.Part> videoListToSend = new ArrayList<>();


        imageData = new ArrayList<>();
        videoData = new ArrayList<>();

        HashMap<String, RequestBody> imageIdData = new HashMap<>();
        HashMap<String, RequestBody> videoIdData = new HashMap<>();


        for (int i = 0; i < mediaData_holderModels.size(); i++) {
            MediaData_HolderModel mediaData_holderModel = mediaData_holderModels.get(i); //// on 0 index we have added null value because we just need to show camera view
            if (mediaData_holderModel != null) {
                if (mediaData_holderModel.getMediaType().equals("IMAGE") || mediaData_holderModel.getMediaType().equals("image")) {
                    imageData.add(mediaData_holderModel);
                } else {
                    videoData.add(mediaData_holderModel);
                }
                if (i == mediaData_holderModels.size() - 1) {
                    if (imageData.size() > 0) {
                        for (int j = 0; j < imageData.size(); j++) {
                            MediaData_HolderModel imageModel = imageData.get(j);
                            if (!URLUtil.isValidUrl(imageModel.getUriPath())) {
                                File profileFile = new File(getImagePath(imageModel.getUriPath()));
                                MultipartBody.Part partImage = MultipartBody.Part.createFormData("prd_image[" + j + "]", profileFile.getName(), RequestBody.create(MediaType.parse("image/*"), profileFile));
                                imageIdRB = RequestBody.create(MediaType.parse("text/plain"), "");

                                /*if (TextUtils.isEmpty(imageData.get(j).getMedia_id())) {
                                    imageIdRB = RequestBody.create(MediaType.parse("text/plain"), "");
                                } else {
                                    imageIdRB = RequestBody.create(MediaType.parse("text/plain"), imageData.get(j).getMedia_id());
                                }*/
                                imageIdData.put("image_id[" + j + "]", imageIdRB);
                                imagesListToSend.add(partImage);

                            }
                        }
                    }
                    if (videoData.size() > 0) {
                        for (int k = 0; k < videoData.size(); k++) {
                            MediaData_HolderModel videoModel = videoData.get(k);
                            if (!URLUtil.isValidUrl(videoModel.getUriPath())) {
                                File profileFile = new File(getImagePath(videoModel.getUriPath()));
                                MultipartBody.Part partImage = MultipartBody.Part.createFormData("prd_video[" + k + "]", profileFile.getName(), RequestBody.create(MediaType.parse("video/mp4"), profileFile));
                                videoIdIdRB = RequestBody.create(MediaType.parse("text/plain"), "");

                              /*  if (TextUtils.isEmpty(imageData.get(k).getMedia_id())) {
                                    videoIdIdRB = RequestBody.create(MediaType.parse("text/plain"), "");
                                } else {
                                    videoIdIdRB = RequestBody.create(MediaType.parse("text/plain"), imageData.get(k).getMedia_id());
                                }*/
                                videoIdData.put("video_id[" + k + "]", videoIdIdRB);
                                videoListToSend.add(partImage);
                            }
                        }
                    }
                }
            }
        }
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.editProduct("EDIT_PRODUCT_DATA", paramMap, imageIdData, videoIdData, imagesListToSend, videoListToSend, true, EditProductActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @Override
    public void onChanged(Object o) {
        ResultData resultData = (ResultData) o;
        if (resultData.getTag().equals("EDIT_PRODUCT_DATA")) {
            Comman_ServiceModel comman_serviceModel = (Comman_ServiceModel) resultData.getRootData().getValue();
            if (comman_serviceModel != null) {
                AlphaHolder.customToast(EditProductActivity.this, comman_serviceModel.getMessage());
            }
        }
        if (resultData.getTag().equals("REMOVE_IMAGE")) {
            Comman_ServiceModel comman_serviceModel = (Comman_ServiceModel) resultData.getRootData().getValue();
            if (comman_serviceModel != null) {
                mediaData_holderModels.remove(removePosition);
                productOverviewImages_recyclerViewAdapter.notifyItemRemoved(removePosition);
                productOverviewImages_recyclerViewAdapter.notifyItemRangeRemoved(removePosition, mediaData_holderModels.size());
            }
        }
        if (resultData.getTag().equals("PRODUCT_DETAILS")) {
            getCategoryData();
            productDetails_serviceModel = (ProductDetails_ServiceModel) resultData.getRootData().getValue();
            activityProductEditBinding.setMainData(productDetails_serviceModel.getData().getPrdrec().get(0));

            String userId = SharedPreferences_Util.getUser_Id(EditProductActivity.this);
            String seller_id = productDetails_serviceModel.getData().getPrdrec().get(0).getSeller_id();

            if (productDetails_serviceModel.getData().getPrdrec().get(0).getTrade().equals("yes")) {
                barteringStatus = "no";
                tradeStatus = "yes";

                activityProductEditBinding.priceEditTextId.setVisibility(View.VISIBLE);
                activityProductEditBinding.priceEditTextId.setText(productDetails_serviceModel.getData().getPrdrec().get(0).getPrice());
                activityProductEditBinding.setIsForTrade(true);
                activityProductEditBinding.setBthShouldBeHide(false);

            } else if (productDetails_serviceModel.getData().getPrdrec().get(0).getBatering().equals("yes")) {
                barteringStatus = "yes";
                tradeStatus = "no";
                price = null;

                activityProductEditBinding.priceEditTextId.setVisibility(View.GONE);
                activityProductEditBinding.setIsForTrade(false);
                activityProductEditBinding.setBthShouldBeHide(false);
            } else if (productDetails_serviceModel.getData().getPrdrec().get(0).getBatering().equals("yes") && !userId.equals(seller_id)) {
                activityProductEditBinding.setBthShouldBeHide(true);
                activityProductEditBinding.setIsForTrade(false);
                activityProductEditBinding.priceEditTextId.setVisibility(View.GONE);

            }
            if (productDetails_serviceModel.getData().getCategory() != null && productDetails_serviceModel.getData().getCategory().size() > 0) {
                /*String categoryName = productDetails_serviceModel.getData().getCategory().get(0).getCat_name();
                String subCategoryName = productDetails_serviceModel.getData().getSubcategory().get(0).getSubcat_name();
                String productName = productDetails_serviceModel.getData().getPrdrec().get(0).getTitle();
                activityProductEditBinding.setCategoryName(productDetails_serviceModel.getData().getCategory());*/
            }
            try {
                nowAddImageForShow();
            } catch (Exception EE) {

            }
        }

        if (resultData.getTag().equals("PRODUCT_CATEGORY")) {
            String cat_parent_id = "";
            productCategory_serviceModel = (ProductCategory_ServiceModel) resultData.getRootData().getValue();
            if (productDetails_serviceModel.getData().getSubcategory() != null && productDetails_serviceModel.getData().getCategory().size() > 0) {
                category_id = productDetails_serviceModel.getData().getSubcategory().get(0).getSubcat_id();
                if (productDetails_serviceModel.getData().getThirdcategory() != null && productDetails_serviceModel.getData().getThirdcategory().size() > 0) {
                    subCategory_id = productDetails_serviceModel.getData().getThirdcategory().get(0).getSubcat_id();
                }
                if (productDetails_serviceModel.getData().getSubcategory() != null) {
                    cat_parent_id = productDetails_serviceModel.getData().getSubcategory().get(0).getSubcat_id();
                }

                if (TextUtils.isEmpty(cat_parent_id)) {
                    activityProductEditBinding.setCategoryModel(productCategory_serviceModel);
                    category_id = productCategory_serviceModel.getData().get(0).getId();
                } else {
                    int categoryParentInt = Integer.parseInt(cat_parent_id);
                    for (int i = 0; i < productCategory_serviceModel.getData().size(); i++) {
                        String category_id = productCategory_serviceModel.getData().get(i).getId();
                        int categoryInt = Integer.parseInt(category_id);

                        if (categoryInt == categoryParentInt) {
                            productCategory_serviceModel.getData().get(0).setSelectedPosition(i + 1);
                        }
                        if (i == productCategory_serviceModel.getData().size() - 1) {
                            activityProductEditBinding.setCategoryModel(productCategory_serviceModel);
                        }
                    }
                }
                getSubCategoryData(false);
            }

        }
        if (resultData.getTag().equals("PRODUCT_SUB_CATEGORY")) {
            String cat_parent_id = "";
            subCategoriesData_serviceModel = (SubCategoriesData_ServiceModel) resultData.getRootData().getValue();
            productSubCategories_recyclerViewAdapter = new ProductSubCategories_RecyclerViewAdapter(EditProductActivity.this, subCategoriesData_serviceModel.getData(), this);

            if (subCategoriesData_serviceModel != null && subCategoriesData_serviceModel.getData().size() > 0) {
                activityProductEditBinding.setIsSubCategoryNotFound(false);
                if (productDetails_serviceModel.getData().getThirdcategory() != null && productDetails_serviceModel.getData().getThirdcategory().size() > 0) {
                    cat_parent_id = productDetails_serviceModel.getData().getThirdcategory().get(0).getSubcat_id();
                }
                if (TextUtils.isEmpty(cat_parent_id)) {
                    activityProductEditBinding.setSubCategoryModel(subCategoriesData_serviceModel);
                } else {
                    int categoryParentInt = Integer.parseInt(cat_parent_id);
                    for (int i = 0; i < subCategoriesData_serviceModel.getData().size(); i++) {
                        String category_id = subCategoriesData_serviceModel.getData().get(i).getSubcat_id();
                        int categoryInt = Integer.parseInt(category_id);

                        if (categoryInt == categoryParentInt) {
                            subCategoriesData_serviceModel.getData().get(0).setSelectedPosition(i + 1);
                        }
                        if (i == subCategoriesData_serviceModel.getData().size() - 1) {
                            activityProductEditBinding.setSubCategoryModel(subCategoriesData_serviceModel);
                            spinnerListener();
                        }
                    }
                }
            } else {
                activityProductEditBinding.setIsSubCategoryNotFound(true);
                activityProductEditBinding.setSubCategoryModel(subCategoriesData_serviceModel);

                spinnerListener();
            }

        }
    }

    void spinnerListener() {
        activityProductEditBinding.productCategorySpinnerId.setOnItemSelectedListener(this);
        activityProductEditBinding.productSubCategorySpinnerId.setOnItemSelectedListener(this);
    }

    private void nowAddImageForShow() {
        mediaData_holderModels = new ArrayList<>();
        mediaData_holderModels.add(null);
        if (productDetails_serviceModel != null && productDetails_serviceModel.getData().getMediarec() != null && productDetails_serviceModel.getData().getMediarec().size() > 0) {
            for (int m = 0; m < productDetails_serviceModel.getData().getMediarec().size(); m++) {                                                                                                                                  /* getBitmap(productDetails_serviceModel.getData().getMediarec().get(m).getProduct_image()*/
                mediaData_holderModels.add(new MediaData_HolderModel(productDetails_serviceModel.getData().getMediarec().get(m).getMedia_type(), productDetails_serviceModel.getData().getMediarec().get(m).getProduct_image(), null, false, productDetails_serviceModel.getData().getMediarec().get(m).getMedia_id()));

                if (m == productDetails_serviceModel.getData().getMediarec().size() - 1) {
                    activityProductEditBinding.recyclerviewId.setHasFixedSize(true);
                    addToRecyclerView(mediaData_holderModels);
                    manipulateFirstImage();
                }
            }
        }
    }

    void manipulateFirstImage() {
        boolean isImageFound = false;
        if (mediaData_holderModels.size() > 0) {
            for (int image = 0; image < mediaData_holderModels.size(); image++) {
                if (mediaData_holderModels.get(image) != null) {
                    if (mediaData_holderModels.get(image).getMediaType().equals("image") || mediaData_holderModels.get(image).getMediaType().equals("IMAGE")) {
                        activityProductEditBinding.setFirstImageToShow(mediaData_holderModels.get(image).getUriPath());
                        isImageFound = true;
                    }
                }
                if (image == mediaData_holderModels.size() - 1) {
                    if (!isImageFound) {
                        //instructionDialog();
                    }
                }
            }
        }

    }

    private void addToRecyclerView(ArrayList<MediaData_HolderModel> mediaData_holderModels) {
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        linearLayoutManager.setReverseLayout(true);

        activityProductEditBinding.recyclerviewId.setLayoutManager(linearLayoutManager);
        productOverviewImages_recyclerViewAdapter = new ProductOverviewImages_RecyclerViewAdapter(EditProductActivity.this, mediaData_holderModels, this, true);
        activityProductEditBinding.recyclerviewId.setAdapter(productOverviewImages_recyclerViewAdapter);
    }

    private String getImagePath(String product_image_url) {
        URI uri = null;
        try {
            uri = new URI(product_image_url);
            Log.d("MALINGAAAA", "" + uri.getPath());
        } catch (URISyntaxException e) {
            Log.e("URI Syntax Error: ", e.getMessage());
        }
        if (uri != null)
            return uri.getPath();
        else
            return "";
    }

    private Bitmap getBitmap(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    private void getCategoryData() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", SharedPreferences_Util.getToken(EditProductActivity.this));
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("PRODUCT_CATEGORY", paramMap, true, EditProductActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    private void getSubCategoryData(boolean showLoader) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", SharedPreferences_Util.getToken(EditProductActivity.this));
        paramMap.put("cat_id", category_id);
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("PRODUCT_SUB_CATEGORY", paramMap, showLoader, EditProductActivity.this);
        viewModel.getRootData().observe(this, this::onChanged);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position > 0) {
            if (parent == activityProductEditBinding.productCategorySpinnerId) {
                try {
                    if (productCategory_serviceModel.getData().size() > 0) {
                        category_id = productCategory_serviceModel.getData().get(position - 1).getId();
                        getSubCategoryData(true);
                    }
                } catch (Exception EE) {

                }

            }
            if (parent == activityProductEditBinding.productSubCategorySpinnerId) {
                try {
                    if (subCategoriesData_serviceModel.getData().size() > 0) {
                        subCategory_id = subCategoriesData_serviceModel.getData().get(position - 1).getSubcat_id();
                    }
                } catch (Exception EE) {

                }
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /*public byte[] readBytes(InputStream inputStream) throws IOException {
        // this dynamically extends to take the bytes you read
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

        // this is storage overwritten on each iteration with bytes
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        // we need to know how may bytes were read to write them to the byteBuffer
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }

        // and then we can return your byte array.
        return byteBuffer.toByteArray();
    }

    class DownloadMusicFile extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... musicURL) {
            int count;
            try {
                URL url = new URL(musicURL[0]);
                URLConnection connection = url.openConnection();
                connection.connect();
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC), "filename.mp3");
                OutputStream output = new FileOutputStream(file);

                byte data[] = new byte[1024];

                while ((count = input.read(data)) != -1) {
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            //Toast.makeText(getContext(), "Music Download complete.", Toast.LENGTH_SHORT).show();
        }
    }

    public class getBitmapFromUrl extends AsyncTask<Void, InputStream, InputStream> {
        String imageUrl;
        String id;
        String mediaType;

        public getBitmapFromUrl(String imageUrl, String id, String mediaType) {
            this.imageUrl = imageUrl;
            this.id = id;
            this.mediaType = mediaType;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected InputStream doInBackground(Void... voids) {

            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                return input;
            } catch (Exception EE) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            super.onPostExecute(inputStream);
            if (mediaType.equals("image")) {
                Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);
                download_mediaList.add(new Download_Media(id, myBitmap, null, mediaType, null));
                Log.e("DOWNLADED_DATA", id);
            } else {


            }

        }
    }

    public class getVideoFromUrl extends AsyncTask<Void, File, File> {
        private final int TIMEOUT_CONNECTION = 5000;//5sec
        private final int TIMEOUT_SOCKET = 30000;//30sec
        File file = null;
        String videoUrl;
        String id;
        String mediaType;

        public getVideoFromUrl(String videoUrl, String id, String mediaType) {
            this.videoUrl = videoUrl;
            this.id = id;
            this.mediaType = mediaType;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected File doInBackground(Void... voids) {

            try {

                URL url = new URL(videoUrl);
                // long startTime = System.currentTimeMillis();
                //Log.i(TAG, "image download beginning: "+imageURL);
                file = new File(mediaType);
                //Open a connection to that URL.
                URLConnection ucon = url.openConnection();

                //this timeout affects how long it takes for the app to realize there's a connection problem
                ucon.setReadTimeout(TIMEOUT_CONNECTION);
                ucon.setConnectTimeout(TIMEOUT_SOCKET);


                //Define InputStreams to read from the URLConnection.
                // uses 3KB download buffer
                InputStream is = ucon.getInputStream();
                BufferedInputStream inStream = new BufferedInputStream(is, 1024 * 5);
                FileOutputStream outStream = new FileOutputStream(file);
                byte[] buff = new byte[5 * 1024];
                //Read bytes (and store them) until there is nothing more to read(-1)
                int len;
                while ((len = inStream.read(buff)) != -1) {
                    outStream.write(buff, 0, len);
                }

                String s = new String(buff, "UTF-8");
                Uri uri = Uri.parse(s);
                Log.e("DOWNLADED_DATA", uri.getPath());

                //clean up
                outStream.flush();
                outStream.close();
                inStream.close();
            } catch (Exception EE) {
                return null;
            }
            return file;
        }

        @Override
        protected void onPostExecute(File file) {
            super.onPostExecute(file);
            //Log.e("DOWNLADED_DATA", String.valueOf(Uri.fromFile(file)));

        }
    }*/
    public void removeImageOrVideo(String mediaId) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("prd_id", productInformation.getId());
        paramMap.put("media_id", mediaId);
        paramMap.put("token", SharedPreferences_Util.getToken(EditProductActivity.this));
        viewModel = ViewModelProviders.of(this, vmFactory).get(ApiCaller.class);
        viewModel.loadData("REMOVE_IMAGE", paramMap, true, this);
        viewModel.getRootData().observe(this, this::onChanged);
    }
}