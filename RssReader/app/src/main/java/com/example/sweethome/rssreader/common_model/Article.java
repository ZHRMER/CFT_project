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
    public void setUUID(final UUID UUID) {
        mUUID = UUID;
    }

    public void setChannelUUid(final UUID channelUUid) {
        mChannelUUid = channelUUid;
    }

    public void setTitle(final String title) {
        mTitle = title;
    }

    public void setSummary(final String summary) {
        mSummary = summary;
    }

    public void setFullText(final String fullText) {
        mFullText = fullText;
    }

    public void setPublicationDate(final Date publicationDate) {
        mPublicationDate = publicationDate;
    }

    public void setLinkString(final String linkString) {
        mLinkString = linkString;
    }

    public void setImageLinkString(final String imageLinkString) {
        mImageLinkString = imageLinkString;
    }
    //endregion

    public Article() {
        mUUID = UUID.randomUUID();
    }
}
