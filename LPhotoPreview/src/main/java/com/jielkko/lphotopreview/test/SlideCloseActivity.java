package com.jielkko.lphotopreview.test;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import com.jielkko.lphotopreview.LPhotoModel;
import com.jielkko.lphotopreview.R;

import com.jielkko.lphotopreview.adapter.PhotoBaseAdapter;
import com.jielkko.lphotopreview.widget.SlideCloseLayout;
import com.jielkko.lphotopreview.widget.TouchViewPager;

import java.util.ArrayList;


public class SlideCloseActivity extends AppCompatActivity {

    private SlideCloseLayout mSlideCloseLayout;
    private TouchViewPager mViewPager;





    private ArrayList<LPhotoModel> imageList = new ArrayList<>();
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_close);




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0 全透明实现
            //getWindow.setStatusBarColor(Color.TRANSPARENT)
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |                 View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4 全透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        mSlideCloseLayout = (SlideCloseLayout) findViewById(R.id.SlideCloseLayout);
        mViewPager = (TouchViewPager) findViewById(R.id.view_pager);




        imageList.add(new LPhotoModel("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1603091533219&di=0304262e58607b481a60b9a40a6baf94&imgtype=0&src=http%3A%2F%2Fa3.att.hudong.com%2F14%2F75%2F01300000164186121366756803686.jpg"));
        imageList.add(new LPhotoModel("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1603091533219&di=0304262e58607b481a60b9a40a6baf94&imgtype=0&src=http%3A%2F%2Fa3.att.hudong.com%2F14%2F75%2F01300000164186121366756803686.jpg"));
        //设置activity的背景为黑色
        getWindow().getDecorView().setBackgroundColor(Color.BLACK);
        //给控件设置需要渐变的背景。如果没有设置这个，则背景不会变化
        mSlideCloseLayout.setGradualBackground(getWindow().getDecorView().getBackground());
        //给控件设置需要渐变的背景。如果没有设置这个，则背景不会变化
        mSlideCloseLayout.setGradualBackground(getWindow().getDecorView().getBackground());
        //设置监听，滑动一定距离后让Activity结束
        mSlideCloseLayout.setLayoutScrollListener(new SlideCloseLayout.LayoutScrollListener() {


            @Override
            public void onLayoutClosed() {
                onBackPressed();
            }
        });
        mSlideCloseLayout.setImageViews(mViewPager);


        PhotoBaseAdapter adapter = new PhotoBaseAdapter(SlideCloseActivity.this, imageList);
        mViewPager.setAdapter(adapter);





    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
           // mSlideCloseLayout.layoutExitAnim(1000, false);


            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }



}