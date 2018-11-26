package com.example.sweethome.rssreader.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.example.sweethome.rssreader.R;
import com.example.sweethome.rssreader.common_model.Article;
import com.example.sweethome.rssreader.common_model.Channel;
import com.example.sweethome.rssreader.common_model.database.articles.ArticleDBPresenter;
import com.example.sweethome.rssreader.common_model.database.channel.ChannelDBPresenter;
import com.example.sweethome.rssreader.screens.articles_list.view.ArticlesListActivity;
import com.example.sweethome.rssreader.service.tasks.AddArticlesListTask;
import com.example.sweethome.rssreader.service.tasks.DownloadArticlesTask;
import com.example.sweethome.rssreader.service.tasks.GetChannelListTask;
import com.example.sweethome.rssreader.service.tasks.UpdateChannelsListTask;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.sweethome.rssreader.common_model.Constants.BROADCAST_GET_ARTICLE_LIST_UPDATE_BY_TIME_ACTION;
import static com.example.sweethome.rssreader.common_model.Constants.BROADCAST_GET_CHANNEL_LIST_UPDATE_BY_TIME_ACTION;
import static com.example.sweethome.rssreader.common_model.Constants.BROADCAST_UPDATE_CHANNELS_LIST_UPDATE_BY_TIME_ACTION;
import static com.example.sweethome.rssreader.common_model.Constants.BROADCAST_WARNING_UPDATE_BY_TIME_ACTION;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_GET_ARTICLE_LIST_FROM_WEB_INTENT_RESULT;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_GET_CHANNEL_LIST_INTENT_RESULT;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_UPDATE_CHANNELS_LIST_INTENT_RESULT;

public final class UpdateByTimeService extends Service {
    private static final int NUMBER_OF_THREADS = 4;
    private ExecutorService mExecutorService;
    private BroadcastReceiver mBroadcastReceiver;
    private ArrayList<Channel> mChannelArrayList;
    private ArrayList<Article> mArticleArrayList;

    public static Intent newIntent(final Context context) {
        return new Intent(context, UpdateByTimeService.class);
    }

    public void onCreate() {
        super.onCreate();
        mExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        registerBroadcastReceiver();
        getChannelListFromDB();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
        mExecutorService.shutdown();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void registerBroadcastReceiver() {
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                String actionIntent = intent.getAction();
                if (null != actionIntent) {
                    switch (actionIntent) {
                        case BROADCAST_GET_CHANNEL_LIST_UPDATE_BY_TIME_ACTION: {
                            mChannelArrayList = intent.getParcelableArrayListExtra(KEY_GET_CHANNEL_LIST_INTENT_RESULT);
                            downloadArticles(mChannelArrayList);
                            break;
                        }
                        case BROADCAST_GET_ARTICLE_LIST_UPDATE_BY_TIME_ACTION: {
                            if (null != intent.getParcelableArrayListExtra(KEY_GET_ARTICLE_LIST_FROM_WEB_INTENT_RESULT)) {
                                mArticleArrayList = intent.getParcelableArrayListExtra(KEY_GET_ARTICLE_LIST_FROM_WEB_INTENT_RESULT);
                                addArticlesToDB(mArticleArrayList);
                                makeNotification();
                            }
                            break;
                        }
                        case BROADCAST_UPDATE_CHANNELS_LIST_UPDATE_BY_TIME_ACTION: {
                            mChannelArrayList = intent.getParcelableArrayListExtra(KEY_UPDATE_CHANNELS_LIST_INTENT_RESULT);
                            updateChannels(mChannelArrayList);
                            break;
                        }
                        case BROADCAST_WARNING_UPDATE_BY_TIME_ACTION: {
                            stopSelf();
                            break;
                        }
                    }
                }
            }
        };
        IntentFilter getChannelIntentFilter = new IntentFilter(BROADCAST_GET_ARTICLE_LIST_UPDATE_BY_TIME_ACTION);
        getChannelIntentFilter.addAction(BROADCAST_UPDATE_CHANNELS_LIST_UPDATE_BY_TIME_ACTION);
        getChannelIntentFilter.addAction(BROADCAST_WARNING_UPDATE_BY_TIME_ACTION);
        getChannelIntentFilter.addAction(BROADCAST_GET_CHANNEL_LIST_UPDATE_BY_TIME_ACTION);
        registerReceiver(mBroadcastReceiver, getChannelIntentFilter);
    }

    private void makeNotification() {
        // Create PendingIntent
        Intent resultIntent = new Intent(this, ArticlesListActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // Create Notification
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, "1")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("RssReader")
                        .setContentText("У вас " + mArticleArrayList.size() + " новых статей")
                        .setContentIntent(resultPendingIntent)
                        .setAutoCancel(true);

        Notification notification = builder.build();
        // Show Notification
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
        stopSelf();
    }

    //region updateChannels region
    public void updateChannels(final ArrayList<Channel> channelArrayList) {
        ChannelDBPresenter channelDBPresenter = new ChannelDBPresenter(this);
        mExecutorService.execute(new UpdateChannelsListTask(channelArrayList, channelDBPresenter, true));
    }
    //endregion

    //region getChannelList region
    public void getChannelListFromDB() {
        ChannelDBPresenter channelDBPresenter = new ChannelDBPresenter(this);
        mExecutorService.execute(new GetChannelListTask(channelDBPresenter, true));
    }
    //endregion

    //region downloadArticle region
    public void downloadArticles(final ArrayList<Channel> channelArrayList) {
        mExecutorService.execute(new DownloadArticlesTask(channelArrayList, this, true));
    }
    //endregion

    //region addArticlesToDB region
    public void addArticlesToDB(final ArrayList<Article> articleArrayList) {
        ArticleDBPresenter articleDBPresenter = new ArticleDBPresenter(this);
        mExecutorService.execute(new AddArticlesListTask(articleArrayList, articleDBPresenter));
    }
    //endregion
}
