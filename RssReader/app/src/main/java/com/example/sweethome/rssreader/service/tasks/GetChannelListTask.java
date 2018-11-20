package com.example.sweethome.rssreader.service.tasks;

import com.example.sweethome.rssreader.common_model.database.channel.ChannelDBPresenter;

final public class GetChannelListTask implements Runnable {
    private final ChannelDBPresenter mChannelDBPresenter;

    public GetChannelListTask(final ChannelDBPresenter channelDBPresenter) {
        mChannelDBPresenter = channelDBPresenter;
    }

    @Override
    public void run() {
        mChannelDBPresenter.getChannelList();
    }
}
