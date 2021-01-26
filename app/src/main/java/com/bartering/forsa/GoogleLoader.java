package com.bartering.forsa;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;

import androidx.databinding.DataBindingUtil;

import com.bartering.forsa.R;
import com.bartering.forsa.databinding.LoaderLayoutBinding;

public class GoogleLoader {
    Activity activity;
    Dialog dialogBottom;
    LoaderLayoutBinding loaderLayoutBinding;

    public GoogleLoader(Activity activity) {
        this.activity = activity;
    }

    public void showLoader() {
            dialogBottom = new Dialog(activity, R.style.DialogAnimationtwo);
            dialogBottom.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogBottom.setCancelable(false);
            loaderLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.loader_layout, null, false);
            dialogBottom.setContentView(loaderLayoutBinding.getRoot());
            dialogBottom.getWindow().setBackgroundDrawableResource(R.color.transparent);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialogBottom.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.gravity = Gravity.CENTER;
            //  lp.windowAnimations = R.style.DialogAnimationtwo;
            dialogBottom.getWindow().setAttributes(lp);
            if(!dialogBottom.isShowing()){
                dialogBottom.show();
            }
    }

    public void dismiss() {
        if (dialogBottom.isShowing())
            dialogBottom.dismiss();
    }
}
