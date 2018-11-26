package com.example.sweethome.rssreader.service.tasks;

import com.example.sweethome.rssreader.common_model.database.channel.ChannelDBPresenter;

final public class GetChannelListTask implements Runnable {
    private final ChannelDBPresenter mChannelDBPresenter;
    private final boolean mIsByTime;

    public GetChannelListTask(final ChannelDBPresenter channelDBPresenter,final boolean isByTime) {
        mChannelDBPresenter = channelDBPresenter;
        mIsByTime=isByTime;
    }

    @Override
    public void run() {
        mChannelDBPresenter.getChannelList(mIsByTime);
    }
}
