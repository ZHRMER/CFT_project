package com.example.sweethome.rssreader.service;

import com.example.sweethome.rssreader.common_model.database.channel.ChannelDBPresenter;

public class DeleteChannelTask implements Runnable {
    private final ChannelDBPresenter mChannelDBPresenter;
    private final String mName;

    DeleteChannelTask(final String name, final ChannelDBPresenter channelDBPresenter) {
        mChannelDBPresenter = channelDBPresenter;
        mName = name;
    }

    @Override
    public void run() {
        mChannelDBPresenter.deleteChannelFromDB(mName);
    }
}
