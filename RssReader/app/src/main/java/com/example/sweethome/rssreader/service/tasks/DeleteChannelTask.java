package com.example.sweethome.rssreader.service.tasks;

import com.example.sweethome.rssreader.common_model.database.channel.ChannelDBPresenter;

public class DeleteChannelTask implements Runnable {
    private final ChannelDBPresenter mChannelDBPresenter;
    private final String mChannelLink;

    public DeleteChannelTask(final String link, final ChannelDBPresenter channelDBPresenter) {
        mChannelDBPresenter = channelDBPresenter;
        mChannelLink = link;
    }


    @Override
    public void run() {
        mChannelDBPresenter.deleteChannelFromDB(mChannelLink);
    }
}
