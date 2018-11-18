package com.example.sweethome.rssreader.service.tasks;

import com.example.sweethome.rssreader.common_model.Channel;
import com.example.sweethome.rssreader.common_model.database.channel.ChannelDBPresenter;

import java.util.ArrayList;

public class UpdateChannelsListTask implements Runnable {
    private final ChannelDBPresenter mChannelDBPresenter;
    private final ArrayList<Channel> mChannelArrayList;

    public UpdateChannelsListTask(final ArrayList<Channel> channelArrayList, final ChannelDBPresenter channelDBPresenter) {
        mChannelArrayList = channelArrayList;
        mChannelDBPresenter = channelDBPresenter;
    }

    @Override
    public void run() {
        mChannelDBPresenter.updateChannelsList(mChannelArrayList);
    }
}
