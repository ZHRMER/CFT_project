package com.example.sweethome.rssreader.service.tasks;

import android.content.Context;

import com.example.sweethome.rssreader.common_model.Channel;
import com.example.sweethome.rssreader.web_work.WebWorker;

import java.util.ArrayList;

final public class DownloadArticlesTask implements Runnable {
    private ArrayList<Channel> mChannelArrayList;
    private Context mContext;
    private final boolean mIsByTime;

    public DownloadArticlesTask(final ArrayList<Channel> channelArrayList, final Context context, final boolean isByTime) {
        mChannelArrayList = channelArrayList;
        mContext = context;
        mIsByTime = isByTime;
    }

    @Override
    public void run() {
        WebWorker worker = new WebWorker(mChannelArrayList, mContext);
        worker.downloadArticle(mIsByTime);
    }
}
