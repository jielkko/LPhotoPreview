package com.jielkko.photodemo.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.bumptech.glide.Glide;
import com.jielkko.lphotopreview.LPhotoModel;
import com.jielkko.lphotopreview.utils.SquareImageView;
import com.jielkko.photodemo.R;

import java.util.List;

public class MainPhotoAdapter extends RecyclerView.Adapter {
    private static final String TAG = "MainPhotoAdapter";
    private static final int TYPE_TIME = 0;
    private Context mContext;
    private List<LPhotoModel> mData;


    public MainPhotoAdapter(Context context, List<LPhotoModel> data) {
        this.mContext = context;
        this.mData = data;
    }


    @Override
    public int getItemViewType(int position) {
        return TYPE_TIME;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_main_photo, parent,
                false);
        return new ItemViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        LPhotoModel item =mData.get(position);
        if (holder instanceof ItemViewHolder) {
            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;


            if(item.getUrl() != null){
                Glide.with(mContext).load(item.getUrl()).into(itemViewHolder.mPhotoView);
            }else{
                Glide.with(mContext).load(item.getPath()).into(itemViewHolder.mPhotoView);
            }



            itemViewHolder.mPhotoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        int position = itemViewHolder.getLayoutPosition();
                        mOnItemClickListener.onItemClick(view, position);
                    }
                }
            });

        }

    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout mContainer;
        private SquareImageView mPhotoView;

        public ItemViewHolder(View view) {
            super(view);

            mContainer = (LinearLayout) view.findViewById(R.id.container);
            mPhotoView = (SquareImageView) view.findViewById(R.id.photo_view);

        }
    }


    //点击
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    //长按
    private OnItemLongClickListener mOnItemLongClickListener;

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }
}