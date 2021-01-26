package com.bartering.forsa;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bartering.forsa.ACRA_Slack.application.AcraSlackSample;
import com.bartering.forsa.Fragment.Home_BM_Fragment;
import com.bartering.forsa.Fragment.Profile_BM_Fragment;
import com.bartering.forsa.activities.ChooseCategoryActivity;
import com.bartering.forsa.activities.bartering_detail.ChooseBarteringItemCameraActivity;
import com.bartering.forsa.activities.boots_Section.MyAdsActivity;
import com.bartering.forsa.databinding.FragmentHomeBinding;
import com.bartering.forsa.utils.AlphaHolder;


public class BottomMenuFragment extends Fragment implements View.OnClickListener {

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 88;
    FragmentHomeBinding fragmentHomeBinding;
    Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        listnerInit();

        return fragmentHomeBinding.getRoot();
    }

    private void listnerInit() {
        fragmentHomeBinding.homeMenuLinLayoutId.setOnClickListener(this);
        fragmentHomeBinding.uploadMenuLinLayoutId.setOnClickListener(this);
        fragmentHomeBinding.uploadRounLinLayoutId.setOnClickListener(this);
        fragmentHomeBinding.categoryMenuLinLayoutId.setOnClickListener(this);
        fragmentHomeBinding.boostMenuLinLayoutId.setOnClickListener(this);
        fragmentHomeBinding.profileMenuLinLayoutId.setOnClickListener(this);
        callFragement(new Home_BM_Fragment());
    }

    @Override
    public void onClick(View view) {
        if (view == fragmentHomeBinding.homeMenuLinLayoutId) {
            callFragement(new Home_BM_Fragment());
        }
        if (view == fragmentHomeBinding.categoryMenuLinLayoutId) {
            ((AcraSlackSample) activity.getApplication()).switcher(activity, ChooseCategoryActivity.class, 0);
        }
        if (view == fragmentHomeBinding.uploadMenuLinLayoutId) {
            if (AlphaHolder.isGuestUser(activity)) {
                GlobalDialog.showDialog(activity);
            } else {
                checkPermission();
            }
        }
        if (view == fragmentHomeBinding.boostMenuLinLayoutId) {
            if (AlphaHolder.isGuestUser(activity)) {
                GlobalDialog.showDialog(activity);
            } else {
                ((AcraSlackSample) getActivity().getApplication()).switcher(activity, MyAdsActivity.class, 0);
            }

        }
        if (view == fragmentHomeBinding.profileMenuLinLayoutId) {
            callFragement(new Profile_BM_Fragment());
        }
        if (view == fragmentHomeBinding.uploadRounLinLayoutId) {
            if (AlphaHolder.isGuestUser(activity)) {
                GlobalDialog.showDialog(activity);
            } else {
                checkPermission();
            }
        }
    }

    private void callFragement(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containerView, fragment);
        fragmentTransaction.commit();
    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        // request camera permission if it has not been grunted.
        if (activity.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                activity.checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
                activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSION_REQUEST_CODE);
            return false;
        } else {
            AlphaHolder.isFromMyAds = "HOME";
            Intent intent = new Intent(activity, ChooseBarteringItemCameraActivity.class);
            startActivity(intent);
            activity.overridePendingTransition(0, 0);
        }
        return true;
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CAMERA_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    AlphaHolder.isFromMyAds = "HOME";
                    Intent intent = new Intent(activity, ChooseBarteringItemCameraActivity.class);
                    startActivity(intent);
                    activity.overridePendingTransition(0, 0);

                } else {
                    Toast.makeText(activity, "[WARN] camera permission is not grunted.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}