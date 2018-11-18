package com.example.sweethome.rssreader.screens.articles_list.presenter;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.widget.Toast;

import com.example.sweethome.rssreader.common_model.Article;
import com.example.sweethome.rssreader.common_model.Channel;
import com.example.sweethome.rssreader.service.RssService;

import java.util.ArrayList;

import static com.example.sweethome.rssreader.common_model.Constants.BROADCAST_GET_ARTICLE_LIST_ACTION;
import static com.example.sweethome.rssreader.common_model.Constants.BROADCAST_GET_CHANNEL_LIST_ACTION;
import static com.example.sweethome.rssreader.common_model.Constants.BROADCAST_UPDATE_CHANNELS_LIST_ACTION;
import static com.example.sweethome.rssreader.common_model.Constants.BROADCAST_WARNING_ACTION;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_GET_ARTICLE_LIST_FROM_DB_INTENT_RESULT;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_GET_ARTICLE_LIST_FROM_WEB_INTENT_RESULT;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_GET_CHANNEL_LIST_INTENT_RESULT;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_UPDATE_CHANNELS_LIST_INTENT_RESULT;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_WARNING_INTENT_RESULT;

public final class NewsListPresenter {
    private Context mContext;
    private RssService mRssService;
    private ServiceConnection mServiceConnection;
    private BroadcastReceiver mBroadcastReceiver;
    private ArrayList<Channel> channelArrayList;
    private INewsListPresenterContract mINewsListPresenterContract;

    public NewsListPresenter(final Context context, final INewsListPresenterContract iNewsListPresenterContract) {
        mContext = context;
        mINewsListPresenterContract = iNewsListPresenterContract;
    }

    private void bindToService() {
        Intent intent = RssService.newIntent(mContext);
        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(final ComponentName name, final IBinder binder) {
                mRssService = ((RssService.RssBinder) binder).getService();
                showArticles();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
            }
        };
        mContext.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private void registerBroadcastReceiver() {
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                String actionIntent = intent.getAction();
                if (null != actionIntent) {
                    switch (actionIntent) {
                        case BROADCAST_GET_CHANNEL_LIST_ACTION: {
                            channelArrayList = intent.getParcelableArrayListExtra(KEY_GET_CHANNEL_LIST_INTENT_RESULT);
                            downloadArticles(channelArrayList);
                            break;
                        }
                        case BROADCAST_GET_ARTICLE_LIST_ACTION: {
                            if (null != intent.getParcelableArrayListExtra(KEY_GET_ARTICLE_LIST_FROM_WEB_INTENT_RESULT)) {
                                mINewsListPresenterContract.addArticlesToListAdapter(intent.
                                        <Article>getParcelableArrayListExtra(KEY_GET_ARTICLE_LIST_FROM_WEB_INTENT_RESULT));
                                saveArticles(intent.<Article>getParcelableArrayListExtra(KEY_GET_ARTICLE_LIST_FROM_WEB_INTENT_RESULT));
                            }
                            if (null != intent.getParcelableArrayListExtra(KEY_GET_ARTICLE_LIST_FROM_DB_INTENT_RESULT)) {
                                mINewsListPresenterContract.setArticlesListToListAdapter(intent.
                                        <Article>getParcelableArrayListExtra(KEY_GET_ARTICLE_LIST_FROM_DB_INTENT_RESULT));
                            }
                            break;
                        }
                        case BROADCAST_WARNING_ACTION: {
                            Toast.makeText(mContext, intent.getStringExtra(KEY_WARNING_INTENT_RESULT), Toast.LENGTH_SHORT).show();
                            mINewsListPresenterContract.stopRefresh();
                            break;
                        }
                        case BROADCAST_UPDATE_CHANNELS_LIST_ACTION: {
                            channelArrayList = intent.getParcelableArrayListExtra(KEY_UPDATE_CHANNELS_LIST_INTENT_RESULT);
                            updateChannelsList(channelArrayList);
                            break;
                        }
                        default: {
                            mINewsListPresenterContract.stopRefresh();
                        }
                    }
                }
            }
        };
        IntentFilter getChannelIntentFilter = new IntentFilter(BROADCAST_GET_CHANNEL_LIST_ACTION);
        getChannelIntentFilter.addAction(BROADCAST_GET_ARTICLE_LIST_ACTION);
        getChannelIntentFilter.addAction(BROADCAST_WARNING_ACTION);
        getChannelIntentFilter.addAction(BROADCAST_UPDATE_CHANNELS_LIST_ACTION);
        mContext.registerReceiver(mBroadcastReceiver, getChannelIntentFilter);
    }

    private void updateChannelsList(final ArrayList<Channel> channels) {
        mRssService.updateChannels(channels);
    }

    private void downloadArticles(final ArrayList<Channel> channels) {
        mRssService.downloadArticles(channels);
    }

    private void getChannelList() {
        mRssService.getChannelListFromDB();
    }

    private void showArticles() {
        mRssService.getArticlesListFromDB();
    }

    private void saveArticles(ArrayList<Article> articles) {
        mRssService.addArticlesToDB(articles);
    }

    public void updateArticles() {
        getChannelList();
    }

    public void detach() {
        mContext.unbindService(mServiceConnection);
        mContext.unregisterReceiver(mBroadcastReceiver);
        mINewsListPresenterContract = null;
        mContext = null;
    }

    public void attach(final Context context, final INewsListPresenterContract iNewsListPresenterContract) {
        mINewsListPresenterContract = iNewsListPresenterContract;
        mContext = context;
        bindToService();
        registerBroadcastReceiver();
    }
}
