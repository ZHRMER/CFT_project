package com.example.sweethome.rssreader.common_model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public final class Article implements Parcelable, Comparable<Article> {
    private static final String DEFAULT_VALUE = "DEFAULT_TEXT";
    private String mChannelLink;
    private String mTitle;
    private String mDescription;
    private Date mPublicationDate;
    private String mLinkString;
    private String mImageLinkString;

    public Article(final String title, final String linkString, final String description, final String date,
                   final String channelLink) {
        if (null == channelLink) {
            mChannelLink = DEFAULT_VALUE;
        } else {
            mChannelLink = channelLink;
        }
        if (null == title) {
            mTitle = DEFAULT_VALUE;
        } else {
            mTitle = title;
        }
        if (null == linkString) {
            mLinkString = DEFAULT_VALUE;
        } else {
            mLinkString = linkString;
        }
        if (null == description) {
            mDescription = DEFAULT_VALUE;
        } else {
            mDescription = description;
        }
        if (null == date) {
            mPublicationDate = new Date();
        } else {
            mPublicationDate = new Date(date);
        }
    }

    private Article(final Parcel in) {
        mTitle = in.readString();
        mDescription = in.readString();
        mLinkString = in.readString();
        mImageLinkString = in.readString();
        mPublicationDate = new Date(in.readString());
        mChannelLink = in.readString();
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(final Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(final int size) {
            return new Article[size];
        }
    };

    //region Getters region
    public String getTitle() {
        return mTitle;
    }


    public String getChannelLink() {
        return mChannelLink;
    }

    public String getDescription() {
        return mDescription;
    }

    public Date getPublicationDate() {
        return mPublicationDate;
    }

    public String getLinkString() {
        return mLinkString;
    }
    //endregion

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(mTitle);
        dest.writeString(mDescription);
        dest.writeString(mLinkString);
        dest.writeString(mImageLinkString);
        dest.writeString(mPublicationDate.toString());
        dest.writeString(mChannelLink);
    }

    @Override
    public int compareTo(final Article otherArticle) {
        return mPublicationDate.compareTo(otherArticle.mPublicationDate);
    }
}
