package com.bartering.forsa.activities.bartering_detail;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.activities.camera_section.BaseCameraActivity;
import com.bartering.forsa.activities.camera_section.MediaData_HolderModel;
import com.bartering.forsa.databinding.ActivityChooseBarteringCameraItemBinding;
import com.bartering.forsa.databinding.PickvideoimageBinding;
import com.bartering.forsa.mediapicker.Image.ImagePicker;
import com.bartering.forsa.mediapicker.video.VideoPicker;
import com.bartering.forsa.recyclerViewAdapter.ChooseBarteringImages_RecyclerViewAdapter;
import com.bartering.forsa.utils.AlphaHolder;
import com.bartering.forsa.utils.SharedPreferences_Util;

import java.util.ArrayList;
import java.util.List;

public class ChooseBarteringItemCameraActivity extends BaseCameraActivity implements ClickListener {

    private static final int CODE_PICTURE = 200;
    ActivityChooseBarteringCameraItemBinding activityChooseBarteringCameraItemBinding;
    ChooseBarteringImages_RecyclerViewAdapter chooseBarteringImages_recyclerViewAdapter;
    ArrayList<MediaData_HolderModel> mediaData_holderModels;
    LinearLayoutManager linearLayoutManager;
    PickvideoimageBinding pickvideoimageBinding;
    Dialog dialogBottom;
    private List<String> imagesPath;
    private List<String> videosPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_bartering_camera_item);
        activityChooseBarteringCameraItemBinding = DataBindingUtil.setContentView(this, R.layout.activity_choose_bartering_camera_item);
        activityChooseBarteringCameraItemBinding.setClickListener(this::onClick);
        AlphaHolder.stackList.add(this);

        mediaData_holderModels = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        linearLayoutManager.setReverseLayout(true);
        mediaData_holderModels.add(null);

        cameraInitiation();
        listener();

        if (AlphaHolder.isFromCalled.equals("PRODUCT_OVERVIEW") || AlphaHolder.isFromCalled.equals("PRODUCT_EDIT")) {
            mediaData_holderModels = new SharedPreferences_Util(ChooseBarteringItemCameraActivity.this).getMediaData();
        }

        activityChooseBarteringCameraItemBinding.recyclerviewId.setHasFixedSize(true);
        activityChooseBarteringCameraItemBinding.recyclerviewId.setLayoutManager(linearLayoutManager);
        if(mediaData_holderModels==null || mediaData_holderModels.size()==0){
            mediaData_holderModels = new ArrayList<>();
            mediaData_holderModels.add(null);
        }
        chooseBarteringImages_recyclerViewAdapter = new ChooseBarteringImages_RecyclerViewAdapter(ChooseBarteringItemCameraActivity.this, mediaData_holderModels, this);
        activityChooseBarteringCameraItemBinding.recyclerviewId.setAdapter(chooseBarteringImages_recyclerViewAdapter);

    }

    private void cameraInitiation() {
        onCreateActivity(this);
        videoWidth = 720;
        videoHeight = 1280;
        cameraWidth = 1280;
        cameraHeight = 720;
    }

    private void listener() {
        activityChooseBarteringCameraItemBinding.backLLId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseBarteringItemCameraActivity.this.finish();
            }
        });
    }

    private void chooseMedia() {
        dialogBottom = new Dialog(ChooseBarteringItemCameraActivity.this, R.style.DialogAnimationtwo);
        dialogBottom.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBottom.setCancelable(false);
        pickvideoimageBinding = DataBindingUtil.inflate(LayoutInflater.from(ChooseBarteringItemCameraActivity.this), R.layout.pickvideoimage, null, false);
        dialogBottom.setContentView(pickvideoimageBinding.getRoot());
        pickvideoimageBinding.setClickListener(this::onClick);

        pickvideoimageBinding.closeDialogLinLayoutId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBottom.dismiss();
            }
        });

        dialogBottom.getWindow().setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogBottom.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        lp.windowAnimations = R.style.DialogAnimationtwo;
        dialogBottom.getWindow().setAttributes(lp);
        dialogBottom.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Log.d(TAG, "onActivityResult() called with: requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + data + "]");
        if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            imagesPath = data.getStringArrayListExtra(ImagePicker.EXTRA_IMAGE_PATH);
            if (imagesPath.size() > 0) {
                for (int i = 0; i < imagesPath.size(); i++) {
                    Bitmap bitmap = AlphaHolder.getBitmap(imagesPath.get(i));
                    MediaData_HolderModel mediaData_holderModel = new MediaData_HolderModel("IMAGE", imagesPath.get(i), bitmap, false);
                    mediaData_holderModels.add(1, mediaData_holderModel);
                }
                chooseBarteringImages_recyclerViewAdapter.notifyDataSetChanged();
                activityChooseBarteringCameraItemBinding.recyclerviewId.scrollToPosition(0);
            }
        }
        if (requestCode == VideoPicker.VIDEO_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            videosPath = data.getStringArrayListExtra(VideoPicker.EXTRA_VIDEO_PATH);
            if (videosPath.size() > 0) {
                for (int i = 0; i < videosPath.size(); i++) {
                    MediaData_HolderModel mediaData_holderModel = new MediaData_HolderModel("VIDEO", videosPath.get(i), null, false);
                    mediaData_holderModels.add(1, mediaData_holderModel);
                }
                chooseBarteringImages_recyclerViewAdapter.notifyDataSetChanged();
                activityChooseBarteringCameraItemBinding.recyclerviewId.scrollToPosition(0);
            }
        }
    }

    public void next(View view) {
        Intent intent = new Intent(ChooseBarteringItemCameraActivity.this, ProductDetail_UploadActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }


    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event3")) {
            /*MediaData_HolderModel mediaData_holderModel = (MediaData_HolderModel) object;
            if (mediaData_holderModel.getMediaType().equals("IMAGE")) {
                activityChooseBarteringCameraItemBinding.setImagePusher(mediaData_holderModel.getMediaImage());
                activityChooseBarteringCameraItemBinding.setVideoPusher(null);
                dataModelUpdator(position);
            }
            if (mediaData_holderModel.getMediaType().equals("VIDEO")) {
                Log.e("CLICKED ON", "VIDEO " + position);
                activityChooseBarteringCameraItemBinding.setVideoPusher(mediaData_holderModel.getUriPath());
                activityChooseBarteringCameraItemBinding.setImagePusher(null);
                dataModelUpdator(position);
            }*/
            new SharedPreferences_Util(ChooseBarteringItemCameraActivity.this).saveProductData_ForShow(mediaData_holderModels);
            Intent intent = new Intent(ChooseBarteringItemCameraActivity.this, ViewBarteringImageVideoActivity.class);
            intent.putExtra("POSITION", "" + position);
            startActivity(intent);
            overridePendingTransition(0, 0);

        }
        if (callerIdentity.equals("event4")) {
            activityChooseBarteringCameraItemBinding.setImagePusher(null);
            activityChooseBarteringCameraItemBinding.setVideoPusher(null);
            dataModelUpdator(-1);
        }
        if (callerIdentity.equals("event6")) {
            dialogBottom.dismiss();
            pickImage();
        }
        if (callerIdentity.equals("event7")) {
            dialogBottom.dismiss();
            pickVideo();

        }
        if (callerIdentity.equals("event10")) { ////remove Image
            MediaData_HolderModel mediaData_holderModel = (MediaData_HolderModel) object;

            mediaData_holderModels.remove(position);
            chooseBarteringImages_recyclerViewAdapter.notifyItemRemoved(position);
            chooseBarteringImages_recyclerViewAdapter.notifyItemRangeRemoved(position, mediaData_holderModels.size());

        }
        if (callerIdentity.equals("event8")) {
            uploadDataToServer();
        }
        if (callerIdentity.equals("event9")) {
            uploadDataToServer();
        }
        if (callerIdentity.equals("event5")) {
            chooseMedia();
        }
        if (callerIdentity.equals("IMAGE_DONE")) {
            MediaData_HolderModel mediaData_holderModel = (MediaData_HolderModel) object;
            mediaData_holderModels.add(1, mediaData_holderModel);
            chooseBarteringImages_recyclerViewAdapter.notifyItemInserted(position);
            chooseBarteringImages_recyclerViewAdapter.notifyItemRangeChanged(1, mediaData_holderModels.size());
            activityChooseBarteringCameraItemBinding.recyclerviewId.scrollToPosition(0);
        }
        if (callerIdentity.equals("VIDEO_DONE")) {
            MediaData_HolderModel mediaData_holderModel = (MediaData_HolderModel) object;
            mediaData_holderModels.add(1, mediaData_holderModel);
            chooseBarteringImages_recyclerViewAdapter.notifyItemInserted(position);
            chooseBarteringImages_recyclerViewAdapter.notifyItemRangeChanged(1, mediaData_holderModels.size());
            activityChooseBarteringCameraItemBinding.recyclerviewId.scrollToPosition(0);

        }
    }

    private void uploadDataToServer() {
        new SharedPreferences_Util(ChooseBarteringItemCameraActivity.this).saveProductData(mediaData_holderModels);
        if (AlphaHolder.isFromCalled.equals("PRODUCT_OVERVIEW") || AlphaHolder.isFromCalled.equals("PRODUCT_EDIT")) {
            ChooseBarteringItemCameraActivity.this.finish();
        } else {
            Intent intent = new Intent(ChooseBarteringItemCameraActivity.this, ProductDetail_UploadActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
    }

    private void pickVideo() {
        new VideoPicker.Builder(ChooseBarteringItemCameraActivity.this)
                .mode(VideoPicker.Mode.GALLERY)
                .directory(VideoPicker.Directory.DEFAULT)
                .extension(VideoPicker.Extension.MP4)
                .enableDebuggingMode(true)
                .build();
    }

    private void pickImage() {

        new ImagePicker.Builder(ChooseBarteringItemCameraActivity.this)
                .mode(ImagePicker.Mode.GALLERY)
                .allowMultipleImages(true)
                .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
                .directory(ImagePicker.Directory.DEFAULT)
                .extension(ImagePicker.Extension.PNG)
                .allowOnlineImages(true)
                .scale(600, 600)
                .allowMultipleImages(true)
                .enableDebuggingMode(true)
                .build();
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
        chooseBarteringImages_recyclerViewAdapter.notifyDataSetChanged();
    }


}