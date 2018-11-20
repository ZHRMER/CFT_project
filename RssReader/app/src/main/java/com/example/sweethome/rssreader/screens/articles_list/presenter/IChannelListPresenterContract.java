package com.example.sweethome.rssreader.screens.articles_list.presenter;

import com.example.sweethome.rssreader.common_model.Channel;

import java.util.ArrayList;

public interface IChannelListPresenterContract {
    void setChannelList(final ArrayList<Channel> channelListAdapter);
}
