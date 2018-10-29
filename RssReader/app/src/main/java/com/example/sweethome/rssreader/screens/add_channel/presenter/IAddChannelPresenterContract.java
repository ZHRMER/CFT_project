package com.example.sweethome.rssreader.screens.add_channel.presenter;

public interface IAddChannelPresenterContract {

    void addLinkSuccess();
    void addLinkFail();
    String getLink();
    String getName();
}
