package com.bartering.forsa;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.databinding.DataBindingUtil;

import com.bartering.forsa.activities.SignInActivity;
import com.bartering.forsa.databinding.GuestuserLayoutBinding;

public class GlobalDialog {
    public static Dialog dialogBottom;
    Context context;
    public static GuestuserLayoutBinding layoutBinding;

    public static void showDialog(Context context) {
        reportAd_Dialog(context);
    }

    public static void reportAd_Dialog(Context context) {
        dialogBottom = new Dialog(context, R.style.DialogAnimationtwo);
        dialogBottom.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBottom.setCancelable(false);
        layoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.guestuser_layout, null, false);
        dialogBottom.setContentView(layoutBinding.getRoot());
        layoutBinding.closeDialogLinLayoutId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBottom.dismiss();
            }
        });
        layoutBinding.okTextViewId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(0, 0);
            }
        });
        layoutBinding.cancelTextViewId.setOnClickListener(new View.OnClickListener() {
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

}
