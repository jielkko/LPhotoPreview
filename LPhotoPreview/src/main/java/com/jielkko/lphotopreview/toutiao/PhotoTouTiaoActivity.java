package com.jielkko.lphotopreview.toutiao;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.jielkko.lphotopreview.LPhotoModel;
import com.jielkko.lphotopreview.R;
import com.jielkko.lphotopreview.adapter.PhotoBaseAdapter;
import com.jielkko.lphotopreview.widget.SlideCloseTouTiaoLayout;
import com.jielkko.lphotopreview.widget.TouchViewPager;

import java.util.ArrayList;

public class PhotoTouTiaoActivity extends AppCompatActivity {


    private SlideCloseTouTiaoLayout mSlideCloseTouTiaoLayout;
    private TouchViewPager mViewPager;
    private RelativeLayout mBottomView;
    private TextView mPage;


    private ArrayList<LPhotoModel> imageList = new ArrayList<>();
    private int page = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_toutiao);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0 全透明实现
            //getWindow.setStatusBarColor(Color.TRANSPARENT)
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4 全透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        mSlideCloseTouTiaoLayout = (SlideCloseTouTiaoLayout) findViewById(R.id.SlideCloseTouTiaoLayout);
        mViewPager = (TouchViewPager) findViewById(R.id.view_pager);
        mBottomView = (RelativeLayout) findViewById(R.id.bottom_view);
        mPage = (TextView) findViewById(R.id.page);


        //新页面接收数据
        Bundle bundle = this.getIntent().getExtras();
        //接收name值
        page = bundle.getInt("page");
        imageList = bundle.getParcelableArrayList("imageList");


        //设置activity的背景为黑色
        getWindow().getDecorView().setBackgroundColor(Color.BLACK);
        //给控件设置需要渐变的背景。如果没有设置这个，则背景不会变化
        mSlideCloseTouTiaoLayout.setGradualBackground(getWindow().getDecorView().getBackground());
        //给控件设置需要渐变的背景。如果没有设置这个，则背景不会变化
        mSlideCloseTouTiaoLayout.setGradualBackground(getWindow().getDecorView().getBackground());
        //设置监听，滑动一定距离后让Activity结束
        mSlideCloseTouTiaoLayout.setLayoutScrollListener(new SlideCloseTouTiaoLayout.LayoutScrollListener() {


            @Override
            public void onMove() {
                hideBottomView();

            }

            @Override
            public void onCancel() {
                showBottomView();
            }

            @Override
            public void onLayoutClosed() {
                onBackPressed();
            }
        });
        mSlideCloseTouTiaoLayout.setImageViews(mViewPager);

        mPage.setText(String.valueOf(page + 1) + "/" + imageList.size());//<span style="white-space: pre;">在当前页面滑动至其他页面后，获取position值</span>


        PhotoBaseAdapter adapter = new PhotoBaseAdapter(PhotoTouTiaoActivity.this, imageList);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                page = i;
                mPage.setText(String.valueOf(page + 1) + "/" + imageList.size());//<span style="white-space: pre;">在当前页面滑动至其他页面后，获取position值</span>

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mViewPager.setCurrentItem(page);


        showBottomView200();
    }

    Handler handler;

    /**
     * 建议使用优先级：1
     */
    private void showBottomView200() {
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mBottomView.setVisibility(View.VISIBLE);
            }
        }, 200);
    }

    private void showBottomView() {
        mBottomView.setVisibility(View.VISIBLE);
    }

    private void hideBottomView() {
        mBottomView.setVisibility(View.GONE);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mSlideCloseTouTiaoLayout.layoutExitAnim(1000, false);

            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }


}