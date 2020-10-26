package com.jielkko.lphotopreview;

import android.os.Parcel;
import android.os.Parcelable;

public class LPhotoModel implements Parcelable {

    private String url;
    private int path;

    public LPhotoModel() {

    }

    public LPhotoModel(String url) {
        this.url = url;
    }

    public LPhotoModel(int path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPath() {
        return path;
    }

    public void setPath(int path) {
        this.path = path;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeInt(this.path);
    }

    protected LPhotoModel(Parcel in) {
        this.url = in.readString();
        this.path = in.readInt();
    }

    public static final Parcelable.Creator<LPhotoModel> CREATOR = new Parcelable.Creator<LPhotoModel>() {
        @Override
        public LPhotoModel createFromParcel(Parcel source) {
            return new LPhotoModel(source);
        }

        @Override
        public LPhotoModel[] newArray(int size) {
            return new LPhotoModel[size];
        }
    };
}
