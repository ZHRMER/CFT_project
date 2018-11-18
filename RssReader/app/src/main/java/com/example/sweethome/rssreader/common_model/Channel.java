package com.example.sweethome.rssreader.common_model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.UUID;

public final class Channel implements Parcelable, Comparable<Channel> {
    private final UUID mUUID;
    private final String mName;
    private final String mLinkString;

    private String mLastArticlePubDate;

    public Channel(final String name, final String linkString, final String lastArticlePubDate) {
        String DEFAULT_VALUE = "DEFAULT_TEXT";
        mUUID = UUID.randomUUID();
        if (null == name) {
            mName = DEFAULT_VALUE;
        } else {
            mName = name;
        }
        if (null == linkString) {
            mLinkString = DEFAULT_VALUE;
        } else {
            mLinkString = linkString;
        }
        if (null == lastArticlePubDate) {
            mLastArticlePubDate = new Date(0).toString();
        } else {
            mLastArticlePubDate = lastArticlePubDate;
        }
    }

    //region Parcelable methods region
    private Channel(final Parcel in) {
        mUUID = UUID.fromString(in.readString());
        mName = in.readString();
        mLinkString = in.readString();
        mLastArticlePubDate = in.readString();
    }

    public static final Creator<Channel> CREATOR = new Creator<Channel>() {
        @Override
        public Channel createFromParcel(final Parcel in) {
            return new Channel(in);
        }

        @Override
        public Channel[] newArray(final int size) {
            return new Channel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(mUUID.toString());
        dest.writeString(mName);
        dest.writeString(mLinkString);
        dest.writeString(mLastArticlePubDate);
    }
    //endregion

    //region Getters region
    public UUID getUUID() {
        return mUUID;
    }

    public String getLastArticlePubDate() {
        return mLastArticlePubDate;
    }

    public String getName() {
        return mName;
    }

    public String getLinkString() {
        return mLinkString;
    }

    @Override
    public int compareTo(final Channel otherChannel) {
        return mName.compareTo(otherChannel.mName);
    }
    //endregion

    public void setLastArticlePubDate(final String lastArticlePubDate) {
        mLastArticlePubDate = lastArticlePubDate;
    }
}