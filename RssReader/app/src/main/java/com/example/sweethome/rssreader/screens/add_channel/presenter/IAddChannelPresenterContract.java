package com.example.sweethome.rssreader.screens.add_channel.presenter;

public interface IAddChannelPresenterContract {
    String getLink();

    String getName();

    void setWarningMessage(final String message);

    void setSuccessfulMessage(final String message);
}
