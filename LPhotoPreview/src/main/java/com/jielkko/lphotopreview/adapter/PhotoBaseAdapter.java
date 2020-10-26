package com.jielkko.lphotopreview.adapter;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.jielkko.lphotopreview.LPhotoModel;
import com.jielkko.lphotopreview.LPhotoPicker;
import com.jielkko.lphotopreview.R;

import java.util.List;

import uk.co.senab.photoview.PhotoView;


public class PhotoBaseAdapter extends PagerAdapter {
    private static final String TAG = "PhotoBaseAdapter";

    private Context mContext;
    private List<LPhotoModel> mData;


    LayoutInflater mLayoutInflater;


    public PhotoBaseAdapter(Context context, List<LPhotoModel> data) {
        this.mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mData = data;
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LPhotoModel item =mData.get(position);

        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);
        PhotoView mImageView = (PhotoView) itemView.findViewById(R.id.imageView);
        //图片加载
        if(item.getUrl() != null){
            LPhotoPicker.getInstance().displayImage(mContext, item.getUrl(), mImageView);

        }else{
            LPhotoPicker.getInstance().displayImage(mContext, item.getPath(), mImageView);

        }




        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }


    //点击
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}