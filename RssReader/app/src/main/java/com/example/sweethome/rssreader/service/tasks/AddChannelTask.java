package com.example.sweethome.rssreader.service.tasks;

import com.example.sweethome.rssreader.common_model.database.channel.ChannelDBPresenter;

public class AddChannelTask implements Runnable {
    private final ChannelDBPresenter mChannelDBPresenter;
    private final String mURL;
    private final String mName;

    public AddChannelTask(final String name, final String url, final ChannelDBPresenter channelDBPresenter) {
        mChannelDBPresenter = channelDBPresenter;
        mURL = url;
        mName = name;
    }

    @Override
    public void run() {
        mChannelDBPresenter.addChannelToDB(mName, mURL);
    }
}
