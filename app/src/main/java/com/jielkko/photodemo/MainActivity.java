package com.jielkko.photodemo;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jielkko.lphotopreview.ImageLoader;
import com.jielkko.lphotopreview.LPhotoModel;
import com.jielkko.lphotopreview.LPhotoPicker;
import com.jielkko.photodemo.adapter.MainPhotoAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    protected final String TAG = this.getClass().getSimpleName();
    public Context mContext;
    public Activity mActivity;

    private ImageView mToutiaoImage;
    private ImageView mWeixinImage;
    private RecyclerView mRecyclerView;


    private String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1603091533219&di=0304262e58607b481a60b9a40a6baf94&imgtype=0&src=http%3A%2F%2Fa3.att.hudong.com%2F14%2F75%2F01300000164186121366756803686.jpg";


    int index = 0;  //显示第几张
    public ArrayList<LPhotoModel> mData = new ArrayList<>();
    public MainPhotoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化控件
        mContext = this;
        mActivity = this;

        mToutiaoImage = (ImageView) findViewById(R.id.toutiaoImage);
        mWeixinImage = (ImageView) findViewById(R.id.weixinImage);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);


        index = 1;
//        mData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1603091533219&di=0304262e58607b481a60b9a40a6baf94&imgtype=0&src=http%3A%2F%2Fa3.att.hudong.com%2F14%2F75%2F01300000164186121366756803686.jpg");
//        mData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1603553477282&di=3366b05ae90556941ac88e67153e8d77&imgtype=0&src=http%3A%2F%2Fa3.att.hudong.com%2F64%2F52%2F01300000407527124482522224765.jpg");
//        mData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1603603448114&di=e9dc4c075b1e9b89160faf6c146e9fbe&imgtype=0&src=http%3A%2F%2Fa0.att.hudong.com%2F56%2F12%2F01300000164151121576126282411.jpg");


        mData.add(new LPhotoModel(url));
        mData.add(new LPhotoModel(R.drawable.test1));
        mData.add(new LPhotoModel(R.drawable.test2));


        Glide.with(this).load(url).into(mToutiaoImage);
        mToutiaoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LPhotoPicker.getInstance()
                        .builder(mActivity)
                        .setMultiMode(LPhotoPicker.TOUXIAO) //默认头条 LPhotoPicker.TOUXIAO
                        .setPage(0) //默认0 是第一页
                        .setImageList(mData) //图片列表
                        .setImageView(mToutiaoImage)
                        .show();

            }
        });

        Glide.with(this).load(url).into(mWeixinImage);
        mWeixinImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                LPhotoPicker.getInstance()
                        .builder(mActivity)
                        .setMultiMode(LPhotoPicker.WEIXIN) //默认头条 LPhotoPicker.TOUXIAO
                        .setPage(0) //默认0 是第一页
                        .setImageList(mData) //图片列表
                        .setImageView(mWeixinImage)
                        .show();
            }
        });

        initListView();
    }

    private void initListView() {


        //LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        GridLayoutManager mLayoutManager = new GridLayoutManager(mContext, 3);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MainPhotoAdapter(mContext, mData);
        mRecyclerView.setAdapter(mAdapter);
        ((DefaultItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);// 取消动画效果
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mAdapter.setOnItemClickListener(new MainPhotoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                LPhotoPicker.getInstance()
                        .builder(mActivity)
                        .setMultiMode(LPhotoPicker.TOUXIAO) //默认头条 LPhotoPicker.TOUXIAO
                        .setPage(position) //默认0 是第一页
                        .setImageList(mData) //图片列表
                        .setImageView((ImageView) view)
                        .show();


            }
        });

    }

}