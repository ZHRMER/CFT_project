package com.example.sweethome.rssreader.service;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.example.sweethome.rssreader.common_model.Channel;
import com.example.sweethome.rssreader.common_model.database.channel.ChannelDBPresenter;
import com.example.sweethome.rssreader.web_work.WebWorker;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.sweethome.rssreader.common_model.Constants.NUMBER_OF_THREADS;

public final class RssService extends android.app.Service {
    private ExecutorService mExecutorService;
    public RssBinder binder = new RssBinder();

    public void onCreate() {
        super.onCreate();
        mExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mExecutorService.shutdown();
    }

    public IBinder onBind(Intent arg0) {
        return binder;
    }

    public class RssBinder extends Binder {
        public RssService getService() {
            return RssService.this;
        }
    }

    //region addChannel region
    public void addChannelToDB(final String name, final String url) {
        mExecutorService.execute(new addChanelRun(name, url));
    }

    private class addChanelRun implements Runnable {
        private final ChannelDBPresenter mChannelDBPresenter;
        private final String mURL;
        private final String mName;

        addChanelRun(String name, String url) {
            mChannelDBPresenter = new ChannelDBPresenter(getApplicationContext());
            mURL = url;
            mName = name;
        }

        @Override
        public void run() {
            mChannelDBPresenter.addChannelToDB(mName, mURL);
        }
    }
    //endregion

    //region getChannelList region
    public void getChannelListFromDB() {
        mExecutorService.execute(new getChannelListRun());
    }

    private class getChannelListRun implements Runnable {
        private final ChannelDBPresenter mChannelDBPresenter;

        getChannelListRun() {
            mChannelDBPresenter = new ChannelDBPresenter(getApplicationContext());
        }

        @Override
        public void run() {
            mChannelDBPresenter.getChannelList();
        }
    }
    //endregion

    //region downloadArticle region
    public void downloadArticles(final ArrayList<Channel> channelArrayList) {
        mExecutorService.execute(new downloadArticleRun(channelArrayList));
    }

    private class downloadArticleRun implements Runnable {
        private ArrayList<Channel> mChannelArrayList;
        private Context mContext;

        downloadArticleRun(final ArrayList<Channel> channelArrayList) {
            mChannelArrayList = channelArrayList;
            mContext = getApplicationContext();
        }

        @Override
        public void run() {
            WebWorker worker = new WebWorker(mChannelArrayList, mContext);
            worker.downloadArticle();
        }
    }
    //endregion

}
