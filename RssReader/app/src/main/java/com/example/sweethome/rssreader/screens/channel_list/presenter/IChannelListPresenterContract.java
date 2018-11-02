package com.example.sweethome.rssreader.screens.channel_list.presenter;

import com.example.sweethome.rssreader.common_model.Channel;

import java.util.ArrayList;

public interface IChannelListPresenterContract {
    void setChannelListAdapter(final ArrayList<Channel> channelListAdapter);
}
