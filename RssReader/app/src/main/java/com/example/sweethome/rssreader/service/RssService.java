package com.example.sweethome.rssreader.service;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.example.sweethome.rssreader.common_model.Article;
import com.example.sweethome.rssreader.common_model.Channel;
import com.example.sweethome.rssreader.common_model.database.articles.ArticleDBPresenter;
import com.example.sweethome.rssreader.common_model.database.channel.ChannelDBPresenter;
import com.example.sweethome.rssreader.service.tasks.AddArticlesListTask;
import com.example.sweethome.rssreader.service.tasks.AddChannelTask;
import com.example.sweethome.rssreader.service.tasks.DeleteChannelTask;
import com.example.sweethome.rssreader.service.tasks.DownloadArticlesTask;
import com.example.sweethome.rssreader.service.tasks.GetArticlesListTask;
import com.example.sweethome.rssreader.service.tasks.GetChannelListTask;
import com.example.sweethome.rssreader.service.tasks.UpdateChannelsListTask;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public final class RssService extends android.app.Service {
    private static final int NUMBER_OF_THREADS = 4;
    private ExecutorService mExecutorService;
    private RssBinder binder = new RssBinder();


    public static Intent newIntent(final Context context) {
        return new Intent(context, RssService.class);
    }

    public void onCreate() {
        super.onCreate();
        mExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mExecutorService.shutdown();
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
    public void deleteChannelFromDB(final String channelLink) {
        ChannelDBPresenter channelDBPresenter = new ChannelDBPresenter(this);
        mExecutorService.execute(new DeleteChannelTask(channelLink, channelDBPresenter));
    }
    //endregion

    //region updateChannels region
    public void updateChannels(final ArrayList<Channel> channelArrayList) {
        ChannelDBPresenter channelDBPresenter = new ChannelDBPresenter(this);
        mExecutorService.execute(new UpdateChannelsListTask(channelArrayList, channelDBPresenter, false));
    }
    //endregion

    //region addChannel region
    public void addChannelToDB(final String name, final String url) {
        ChannelDBPresenter channelDBPresenter = new ChannelDBPresenter(this);
        mExecutorService.execute(new AddChannelTask(name, url, channelDBPresenter));
    }
    //endregion

    //region getChannelList region
    public void getChannelListFromDB() {
        ChannelDBPresenter channelDBPresenter = new ChannelDBPresenter(this);
        mExecutorService.execute(new GetChannelListTask(channelDBPresenter, false));
    }
    //endregion

    //region downloadArticle region
    public void downloadArticles(final ArrayList<Channel> channelArrayList) {
        mExecutorService.execute(new DownloadArticlesTask(channelArrayList, this, false));
    }
    //endregion

    //region getArticlesFromDB region
    public void getArticlesListFromDB(final String channelLink) {
        ArticleDBPresenter articleDBPresenter = new ArticleDBPresenter(this);
        mExecutorService.execute(new GetArticlesListTask(articleDBPresenter, channelLink));
    }
    //endregion

    //region addArticlesToDB region
    public void addArticlesToDB(final ArrayList<Article> articleArrayList) {
        ArticleDBPresenter articleDBPresenter = new ArticleDBPresenter(this);
        mExecutorService.execute(new AddArticlesListTask(articleArrayList, articleDBPresenter));
    }
    //endregion

}
