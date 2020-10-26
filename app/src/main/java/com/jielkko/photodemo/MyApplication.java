package com.jielkko.photodemo;


import android.app.Application;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jielkko.lphotopreview.ImageLoader;
import com.jielkko.lphotopreview.LPhotoPicker;

public class MyApplication extends Application {
    private static final String TAG = "BaseApplication";
    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    private Boolean isDebug = true;

    @Override
    public void onCreate() {
        super.onCreate();

        //图片加载必须先初始化
        LPhotoPicker.getInstance().setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                //图片加载框架自定义
                Glide.with(context).load(path).into(imageView);
            }

            @Override
            public void displayImage(Context context, int path, ImageView imageView) {
                //图片加载框架自定义
                Glide.with(context).load(path).into(imageView);
            }
        });

    }



}
