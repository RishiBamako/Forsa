package com.bartering.forsa.infiniteindicator;


import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bartering.forsa.R;
import com.bartering.forsa.utils.RoundedCornersTransformation;

import cn.lightsky.infiniteindicator.ImageLoader;


/**
 * Created by lightsky on 16/1/31.
 */
public class GlideLoader implements ImageLoader {

    public void initLoader(Context context) {

    }

    @Override
    public void load(Context context, ImageView targetView, Object res) {
        Glide.with(context)
                .load(res)
                .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(context, 50, 2)))
                .placeholder(R.drawable.remove_drone)
                .into(targetView);
    }
}
