package com.example.sweethome.rssreader.service;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.example.sweethome.rssreader.common_model.Channel;
import com.example.sweethome.rssreader.common_model.database.channel.ChannelDBPresenter;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public final class RssService extends android.app.Service {
    private ExecutorService mExecutorService;
    private Context mContext;
    public RssBinder binder = new RssBinder();


    public static Intent newIntent(final Context context) {
        return new Intent(context, RssService.class);
    }

    public void onCreate() {
        super.onCreate();
        int NUMBER_OF_THREADS = 4;
        mExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        mContext = this;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mExecutorService.shutdownNow();
    }

    public IBinder onBind(final Intent arg0) {
        return binder;
    }

    public class RssBinder extends Binder {
        public RssService getService() {
            return RssService.this;
        }
    }

    //region deleteChannel region
    public void deleteChannelFromDB(final String channelName) {
        ChannelDBPresenter channelDBPresenter = new ChannelDBPresenter(mContext);
        mExecutorService.execute(new DeleteChannelTask(channelName, channelDBPresenter));
    }
    //endregion

    //region addChannel region
    public void addChannelToDB(final String name, final String url) {
        ChannelDBPresenter channelDBPresenter = new ChannelDBPresenter(mContext);
        mExecutorService.execute(new AddChannelTask(name, url, channelDBPresenter));
    }
    //endregion

    //region getChannelList region
    public void getChannelListFromDB() {
        ChannelDBPresenter channelDBPresenter = new ChannelDBPresenter(mContext);
        mExecutorService.execute(new GetChannelListTask(channelDBPresenter));
    }
    //endregion

    //region downloadArticle region
    public void downloadArticles(final ArrayList<Channel> channelArrayList) {
        mExecutorService.execute(new DownloadArticlesTask(channelArrayList, mContext));
    }
    //endregion

}
