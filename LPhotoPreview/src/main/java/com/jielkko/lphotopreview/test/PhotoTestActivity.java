package com.jielkko.lphotopreview.test;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;

import com.jielkko.lphotopreview.LPhotoPicker;
import com.jielkko.lphotopreview.R;

import uk.co.senab.photoview.PhotoView;

public class PhotoTestActivity extends AppCompatActivity {

    private PhotoView mImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_test);

        mImageView = (PhotoView) findViewById(R.id.imageView);


        ViewCompat.setTransitionName(mImageView, "ABC");




        //图片加载
        LPhotoPicker.getInstance().displayImage(this, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1603091533219&di=0304262e58607b481a60b9a40a6baf94&imgtype=0&src=http%3A%2F%2Fa3.att.hudong.com%2F14%2F75%2F01300000164186121366756803686.jpg", mImageView);


    }



}