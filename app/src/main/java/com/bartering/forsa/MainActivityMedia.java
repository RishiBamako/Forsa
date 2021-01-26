package com.bartering.forsa;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bartering.forsa.databinding.PickvideoimageBinding;
import com.bartering.forsa.mediapicker.Image.ImagePicker;
import com.bartering.forsa.mediapicker.ImageFragment;
import com.bartering.forsa.mediapicker.VideoFragment;
import com.bartering.forsa.mediapicker.video.VideoPicker;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class MainActivityMedia extends AppCompatActivity implements ClickListener {

    private Fragment videoFragment = new VideoFragment();
    private Fragment imageFragment = new ImageFragment();
    Dialog dialogBottom;

    PickvideoimageBinding pickvideoimageBinding;
    private List<String> imagesPath;
    private List<String> videosPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_media);

        ViewPager mPager = findViewById(R.id.pager);
        PagerAdapter mPagerAdapter = new PickerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mPager);
        for (int i = 0; i < mPagerAdapter.getCount(); i++)
            tabLayout.getTabAt(i).setText(mPagerAdapter.getPageTitle(i));

        reportAd_Dialog();
    }

    private void reportAd_Dialog() {
        dialogBottom = new Dialog(MainActivityMedia.this, R.style.DialogAnimationtwo);
        dialogBottom.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBottom.setCancelable(false);
        pickvideoimageBinding = DataBindingUtil.inflate(LayoutInflater.from(MainActivityMedia.this), R.layout.pickvideoimage, null, false);
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


    }

    @Override
    public void onClick(int position, Object object, String callerIdentity) {
        if (callerIdentity.equals("event1")) {
        }
        if (callerIdentity.equals("event2")) {
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Log.d(TAG, "onActivityResult() called with: requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + data + "]");
        if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            imagesPath = data.getStringArrayListExtra(ImagePicker.EXTRA_IMAGE_PATH);
        }
        if (requestCode == VideoPicker.VIDEO_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            videosPath = data.getStringArrayListExtra(VideoPicker.EXTRA_VIDEO_PATH);
        }
    }

    private class PickerAdapter extends FragmentStatePagerAdapter {
        private static final int NUM_PAGES = 2;
        PickerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @VisibleForTesting
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return videoFragment;
                default:
                    return imageFragment;
            }
        }

        @VisibleForTesting
        public String getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.tab_title_video);
                default:
                    return getString(R.string.tab_title_image);
            }
        }
    }

  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //
        videoFragment.onActivityResult(requestCode, resultCode, data);
        imageFragment.onActivityResult(requestCode, resultCode, data);
    }*/
}