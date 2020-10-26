package com.jielkko.lphotopreview;

import android.content.Context;
import android.widget.ImageView;

import java.io.Serializable;

public interface ImageLoader extends Serializable {
    void displayImage(Context context, String path, ImageView imageView);

    void displayImage(Context context, int path, ImageView imageView);
}
