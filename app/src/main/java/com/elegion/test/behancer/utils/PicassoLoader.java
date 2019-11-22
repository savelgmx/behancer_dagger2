package com.elegion.test.behancer.utils;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class PicassoLoader {
    public void load(Context context, String url, ImageView view){
        Picasso.with(context)
                .load(url)
                .fit()
                .into(view);
    }
}
