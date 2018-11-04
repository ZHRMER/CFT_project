package com.example.sweethome.rssreader.common_model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.UUID;

public final class Article implements Parcelable {
    private UUID mUUID;

    private String mTitle;
    private String mSummary;

    private Date mPublicationDate;

    private String mLinkString;
    private String mImageLinkString;

    protected Article(Parcel in) {
        mTitle = in.readString();
        mSummary = in.readString();
        mLinkString = in.readString();
        mImageLinkString = in.readString();
        mPublicationDate=new Date(in.readString());
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    //region Getters region
    public UUID getUUID() {
        return mUUID;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSummary() {
        return mSummary;
    }

    public Date getPublicationDate() {
        return mPublicationDate;
    }

    public String getLinkString() {
        return mLinkString;
    }

    public String getImageLinkString() {
        return mImageLinkString;
    }
    //endregion

    public Article(final String title, final String linkString, final String summary, final String date) {
        mUUID = UUID.randomUUID();
        mTitle = title;
        mSummary = summary;
        mLinkString = linkString;
        mPublicationDate=new Date(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mSummary);
        dest.writeString(mLinkString);
        dest.writeString(mImageLinkString);
        dest.writeString(mPublicationDate.toString());
    }
}
