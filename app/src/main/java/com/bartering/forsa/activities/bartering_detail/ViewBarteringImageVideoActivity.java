package com.bartering.forsa.activities.bartering_detail;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.databinding.DataBindingUtil;

import com.bartering.forsa.AppCompactActivity;
import com.bartering.forsa.ClickListener;
import com.bartering.forsa.R;
import com.bartering.forsa.activities.camera_section.MediaData_HolderModel;
import com.bartering.forsa.databinding.ActivityViewBarteringImageVideoBinding;
import com.bartering.forsa.utils.SharedPreferences_Util;

import java.util.ArrayList;

public class ViewBarteringImageVideoActivity extends AppCompactActivity implements ClickListener {

    ArrayList<MediaData_HolderModel> mediaData_holderModels;
    ActivityViewBarteringImageVideoBinding parentBinder;
    int positionClick = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentBinder = DataBindingUtil.setContentView(this, R.layout.activity_view_bartering_image_video);
        parentBinder.setClickListener(this::onClick);
        getMediaData();
    }
    private void getMediaData() {
        if (getIntent().getExtras() != null) {
            mediaData_holderModels = new SharedPreferences_Util(ViewBarteringImageVideoActivity.this).getMediaData_ForShow();
            String positionValue = getIntent().getExtras().getString("POSITION");
            if (!TextUtils.isEmpty(positionValue)) {
                positionClick = Integer.parseInt(positionValue);
                Log.e("position", "" + positionClick);
                notifyAndShow(positionClick);
            }
        }
    }

    private void manipuateDatawithView(int position) {
        if (mediaData_holderModels.get(position) != null) {
            MediaData_HolderModel mediaData_holderModel = mediaData_holderModels.get(position);
            if (mediaData_holderModel.getMediaType().equals("IMAGE") || mediaData_holderModel.getMediaType().equals("image")) {
                parentBinder.setImagePusher(mediaData_holderModel.getUriPath());
                parentBinder.setVideoPusher(null);
            }
            if (mediaData_holderModel.getMediaType().equals("VIDEO") || mediaData_holderModel.getMediaType().equals("video")) {
                parentBinder.setVideoPusher(mediaData_holderModel.getUriPath());
                parentBinder.setImagePusher(null);
            }
        }

    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event1")) {
            ViewBarteringImageVideoActivity.this.finish();
        }
        if (callerIdentity.equals("event2")) {
            if (positionClick != mediaData_holderModels.size() - 1) {
                parentBinder.setNextBtnShouldBeHide(true);
                positionClick++;
                Log.e("position", "" + positionClick);
                notifyAndShow(positionClick);
            }
        }
        if (callerIdentity.equals("event3")) {
            if (positionClick > 0) {
                parentBinder.setPreviousBtnShouldBeHide(true);
                parentBinder.setNextBtnShouldBeHide(true);
                positionClick--;
                Log.e("position", "" + positionClick);
                notifyAndShow(positionClick);
            } else {
                parentBinder.setPreviousBtnShouldBeHide(false);
            }
        }
    }

    private void notifyAndShow(int position) {
        try {
            if (mediaData_holderModels.size() > 1 && position > 1) {
                parentBinder.setNextBtnShouldBeHide(true);
                parentBinder.setPreviousBtnShouldBeHide(true);
            }
            if (mediaData_holderModels.size() > 1 && position == 1) {
                parentBinder.setNextBtnShouldBeHide(false);
                parentBinder.setPreviousBtnShouldBeHide(true);
            }
            if (position == mediaData_holderModels.size() - 1) {
                parentBinder.setNextBtnShouldBeHide(true);
                parentBinder.setPreviousBtnShouldBeHide(false);
            }
            if (mediaData_holderModels.size() == 2) {
                parentBinder.setNextBtnShouldBeHide(false);
                parentBinder.setPreviousBtnShouldBeHide(false);
            }
            manipuateDatawithView(position);
        } catch (Exception EE) {

        }
    }
}