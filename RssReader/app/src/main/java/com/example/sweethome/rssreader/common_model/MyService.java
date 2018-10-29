package com.example.sweethome.rssreader.common_model;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.example.sweethome.rssreader.common_model.database.channel.ChannelDBPresenter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import static com.example.sweethome.rssreader.common_model.Constants.NUMBER_OF_THREADS;

public final class MyService extends android.app.Service {
    private ExecutorService mExecutorService;
    public MyBinder binder = new MyBinder();

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

    public class MyBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }
    }

    //region addChannel region
    public void addChannelToDB(ChannelDBPresenter channelDBPresenter, String name, String url) {
        mExecutorService.execute(new addChanelRun(channelDBPresenter, name, url));
    }

    private class addChanelRun implements Runnable {
        private ChannelDBPresenter mChannelDBPresenter;
        private String mURL;
        private String mName;

        addChanelRun(ChannelDBPresenter channelDBPresenter, String name, String url) {
            mChannelDBPresenter = channelDBPresenter;
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
    public void getChannelListFromDB(ChannelDBPresenter channelDBPresenter) {
        mExecutorService.execute(new getChannelListRun(channelDBPresenter));
    }

    private class getChannelListRun implements Runnable {
        private ChannelDBPresenter mChannelDBPresenter;

        getChannelListRun(ChannelDBPresenter channelDBPresenter) {
            mChannelDBPresenter = channelDBPresenter;
        }

        @Override
        public void run() {
            mChannelDBPresenter.getChannelList();
        }
    }
    //endregion
}
