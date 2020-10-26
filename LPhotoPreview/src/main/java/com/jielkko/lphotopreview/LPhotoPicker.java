package com.jielkko.lphotopreview;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.widget.ImageView;

import com.jielkko.lphotopreview.test.PhotoTestActivity;
import com.jielkko.lphotopreview.test.SlideCloseActivity;
import com.jielkko.lphotopreview.weixin.PhotoDragActivity;
import com.jielkko.lphotopreview.toutiao.PhotoTouTiaoActivity;

import java.util.ArrayList;

public class LPhotoPicker {

    public static final String TAG = LPhotoPicker.class.getSimpleName();

    public static final int TOUXIAO = 1;
    public static final int WEIXIN = 2;

    private ImageLoader loader;


    private int page;
    private Activity activity;
    private ImageView imageView;
    private int multiMode = TOUXIAO;    //图片选择模式
    private ArrayList<LPhotoModel> imageList = new ArrayList<>();

    private static LPhotoPicker mInstance;

    private LPhotoPicker() {
    }

    public static LPhotoPicker getInstance() {
        if (mInstance == null) {
            synchronized (LPhotoPicker.class) {
                if (mInstance == null) {
                    mInstance = new LPhotoPicker();
                }
            }
        }
        return mInstance;
    }


    public LPhotoPicker builder(Activity activity) {
        this.activity = activity;
        return this;
    }

    /**
     * 图片加载必须先初始化
     *
     * @param loader
     */
    public void setImageLoader(@NonNull ImageLoader loader) {
        this.loader = loader;
    }

    public void displayImage(Context context, String path, ImageView imageView) {
        if (loader != null) {
            loader.displayImage(context, path, imageView);
        }
    }

    public void displayImage(Context context, int path, ImageView imageView) {
        if (loader != null) {
            loader.displayImage(context, path, imageView);
        }
    }

    public LPhotoPicker setPage(int page) {
        this.page = page;
        return this;
    }

    public LPhotoPicker setMultiMode(int multiMode) {
        this.multiMode = multiMode;
        return this;
    }


    public LPhotoPicker setImageView(ImageView imageView) {
        this.imageView = imageView;
        return this;
    }

    public ArrayList<LPhotoModel> getImageList() {
        return imageList;
    }

    public LPhotoPicker setImageList(ArrayList<LPhotoModel> imageList) {
        this.imageList = imageList;
        return this;
    }


    public void show() {
        if (multiMode == TOUXIAO) {
            toutiao();
        }
        if (multiMode == WEIXIN) {
            weixin();
        }
    }

    //仿头条图片预览
    private void toutiao() {
        Intent intent = new Intent(activity, PhotoTouTiaoActivity.class);
        intent.putExtra("page", page);
        intent.putParcelableArrayListExtra("imageList", imageList);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.photo_slide_bottom_in, R.anim.photo_silde_bottom);
    }

    //仿微信图片预览
    public void weixin() {
        Intent intent = new Intent(activity, PhotoDragActivity.class);
        int location[] = new int[2];

        imageView.getLocationOnScreen(location);
        intent.putExtra("left", location[0]);
        intent.putExtra("top", location[1]);
        intent.putExtra("height", imageView.getHeight());
        intent.putExtra("width", imageView.getWidth());

        intent.putExtra("page", page);
        intent.putParcelableArrayListExtra("imageList", imageList);
        activity.startActivity(intent);
        activity.overridePendingTransition(0, 0);
    }


    private void slideclose() {
        Intent intent = new Intent(activity, SlideCloseActivity.class);
        intent.putExtra("page", page);
        intent.putParcelableArrayListExtra("imageList", imageList);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, imageView, "ABC");
        activity.startActivity(intent, options.toBundle());
    }

    //图片预览
    private void photoTest() {
        Intent intent = new Intent(activity, PhotoTestActivity.class);
        intent.putExtra("page", page);
        intent.putParcelableArrayListExtra("imageList", imageList);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, imageView, "ABC");
        activity.startActivity(intent, options.toBundle());
    }
}