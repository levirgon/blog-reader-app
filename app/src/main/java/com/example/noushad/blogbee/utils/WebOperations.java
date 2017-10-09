package com.example.noushad.blogbee.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.noushad.blogbee.R;

/**
 * Created by noushad on 10/9/17.
 */

public class WebOperations {

    public static void loadImage(Context context, final ImageView imageView, final String url) {

        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).fitCenter().crossFade().listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                imageView.setImageResource(R.drawable.wait);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                return false;
            }
        }).into(imageView);
    }
}
