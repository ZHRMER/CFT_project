package com.example.sweethome.rssreader.common_model;

import java.util.Date;
import java.util.UUID;

public final class Article {
    private UUID mUUID;
    private UUID mChannelUUid;

    private String mTitle;
    private String mSummary;
    private String mFullText;

    private Date mPublicationDate;

    private String mLinkString;
    private String mImageLinkString;

    //region Getters region
    public UUID getUUID() {
        return mUUID;
    }

    public UUID getChannelUUid() {
        return mChannelUUid;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSummary() {
        return mSummary;
    }

    public String getFullText() {
        return mFullText;
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

    //region Setters region
    public void setUUID(UUID UUID) {
        mUUID = UUID;
    }

    public void setChannelUUid(UUID channelUUid) {
        mChannelUUid = channelUUid;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public void setFullText(String fullText) {
        mFullText = fullText;
    }

    public void setPublicationDate(Date publicationDate) {
        mPublicationDate = publicationDate;
    }

    public void setLinkString(String linkString) {
        mLinkString = linkString;
    }

    public void setImageLinkString(String imageLinkString) {
        mImageLinkString = imageLinkString;
    }
    //endregion

    public Article() {
        mUUID = UUID.randomUUID();
    }
}
