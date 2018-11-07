package com.example.sweethome.rssreader.service;

import android.content.Context;

import com.example.sweethome.rssreader.common_model.Channel;
import com.example.sweethome.rssreader.web_work.WebWorker;

import java.util.ArrayList;

public class DownloadArticlesTask implements Runnable {
    private ArrayList<Channel> mChannelArrayList;
    private Context mContext;

    DownloadArticlesTask(final ArrayList<Channel> channelArrayList, final Context context) {
        mChannelArrayList = channelArrayList;
        mContext = context;
    }

    @Override
    public void run() {
        WebWorker worker = new WebWorker(mChannelArrayList, mContext);
        worker.downloadArticle();
    }
}
