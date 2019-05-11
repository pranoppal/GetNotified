package com.example.noti_listener;

import android.graphics.Bitmap;
import android.os.Parcelable;

import android.os.Parcel;

public class About implements Parcelable{
    private Bitmap mbitmap;
    private String mTitle;
    private String mAbout;
    private String mName;

    public About(Bitmap mbitmap, String mTitle, String mAbout, String mName){
        this.mAbout=mAbout;
        this.mbitmap=mbitmap;
        this.mName=mName;
        this.mTitle=mTitle;
    }

    public Bitmap getmBitmap(){
        return mbitmap;
    }
    public String getmTitle(){
        return mTitle;
    }

    public String getmName() {
        return mName;
    }

    public void setMbitmap(Bitmap mbitmap) {
        this.mbitmap = mbitmap;
    }

    public String getmAbout() {
        return mAbout;
    }
    protected About(Parcel in) {
        mName=in.readString();
        mTitle = in.readString();
        mAbout = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mTitle);
        dest.writeString(mAbout);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<About> CREATOR = new Parcelable.Creator<About>() {
        @Override
        public About createFromParcel(Parcel in) {
            return new About(in);
        }

        @Override
        public About[] newArray(int size) {
            return new About[size];
        }
    };

    public void setmBitmap(Bitmap mbitmap) {
        this.mbitmap = mbitmap;
    }
}