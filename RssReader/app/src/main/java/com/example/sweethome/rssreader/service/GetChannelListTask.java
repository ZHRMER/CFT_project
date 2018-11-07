package com.example.sweethome.rssreader.service;

import com.example.sweethome.rssreader.common_model.database.channel.ChannelDBPresenter;

public class GetChannelListTask implements Runnable {
    private final ChannelDBPresenter mChannelDBPresenter;

    GetChannelListTask(ChannelDBPresenter channelDBPresenter) {
        mChannelDBPresenter = channelDBPresenter;
    }

    @Override
    public void run() {
        mChannelDBPresenter.getChannelList();
    }
}
