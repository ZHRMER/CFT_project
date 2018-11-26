package com.example.sweethome.rssreader.service.tasks;

import com.example.sweethome.rssreader.common_model.Channel;
import com.example.sweethome.rssreader.common_model.database.channel.ChannelDBPresenter;

import java.util.ArrayList;

final public class UpdateChannelsListTask implements Runnable {
    private final ChannelDBPresenter mChannelDBPresenter;
    private final ArrayList<Channel> mChannelArrayList;
    private final boolean mIsByTime;

    public UpdateChannelsListTask(final ArrayList<Channel> channelArrayList, final ChannelDBPresenter channelDBPresenter, final boolean isByTime) {
        mChannelArrayList = channelArrayList;
        mChannelDBPresenter = channelDBPresenter;
        mIsByTime=isByTime;
    }

    @Override
    public void run() {
        mChannelDBPresenter.updateChannelsList(mChannelArrayList,mIsByTime);
    }
}
