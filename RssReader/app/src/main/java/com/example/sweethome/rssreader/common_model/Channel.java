package com.example.sweethome.rssreader.common_model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

public final class Channel implements Parcelable {
    private final UUID mUUID;
    private final String mName;
    private final String mLinkString;


    public Channel(String name, String linkString) {
        this.mName = name;
        this.mLinkString = linkString;
        this.mUUID = UUID.randomUUID();
    }

    //region Parcelable methods region
    private Channel(Parcel in) {
        mUUID=UUID.fromString(in.readString());
        mName = in.readString();
        mLinkString = in.readString();
    }

    public static final Creator<Channel> CREATOR = new Creator<Channel>() {
        @Override
        public Channel createFromParcel(Parcel in) {
            return new Channel(in);
        }

        @Override
        public Channel[] newArray(int size) {
            return new Channel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mUUID.toString());
        dest.writeString(mName);
        dest.writeString(mLinkString);
    }
    //endregion

    //region Getters region
    public UUID getUUID() {
        return mUUID;
    }

    public String getName() {
        return mName;
    }

    public String getLinkString() {
        return mLinkString;
    }
    //endregion
}