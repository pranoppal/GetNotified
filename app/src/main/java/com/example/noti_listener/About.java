package com.example.noti_listener;

import android.os.Parcelable;

import android.os.Parcel;

public class About implements Parcelable{
    private int mAppicon;
    private String mTitle;
    private String mAbout;

    public About(int mAppicon, String mTitle, String mAbout){
        this.mAbout=mAbout;
        this.mAppicon=mAppicon;
        this.mTitle=mTitle;
    }

    public Integer getmAppicon(){
        return mAppicon;
    }
    public String getmTitle(){
        return mTitle;
    }

    public String getmAbout() {
        return mAbout;
    }
    protected About(Parcel in) {
        mAppicon = in.readInt();
        mTitle = in.readString();
        mAbout = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mAppicon);
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
}