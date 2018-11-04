package com.example.sweethome.rssreader.screens.news_list.presenter;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.example.sweethome.rssreader.common_model.Article;
import com.example.sweethome.rssreader.common_model.Channel;
import com.example.sweethome.rssreader.service.RssService;

import java.util.ArrayList;

import static com.example.sweethome.rssreader.common_model.Constants.BROADCAST_GET_ARTICLE_LIST_ACTION;
import static com.example.sweethome.rssreader.common_model.Constants.BROADCAST_GET_CHANNEL_LIST_ACTION;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_GET_ARTICLE_LIST_INTENT_RESULT;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_GET_CHANNEL_LIST_INTENT_RESULT;


public final class NewsListPresenter {
    private Context mContext;
    private RssService mRssService;
    private ServiceConnection mServiceConnection;
    private BroadcastReceiver mBroadcastReceiver;
    private ArrayList<Channel> channelArrayList;
    private INewsListPresenterContract mINewsListPresenterContract;

    public NewsListPresenter(final Context context,final INewsListPresenterContract iNewsListPresenterContract) {
        mContext = context;
        mINewsListPresenterContract=iNewsListPresenterContract;
    }

    private void bindToService() {
        Intent intent = new Intent(mContext, RssService.class);
        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(final ComponentName name, final IBinder binder) {
                mRssService = ((RssService.RssBinder) binder).getService();
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
                String actionIntent=intent.getAction();
                if (actionIntent!=null) {
                    switch (actionIntent) {
                        case BROADCAST_GET_CHANNEL_LIST_ACTION: {
                            channelArrayList = intent.getParcelableArrayListExtra(KEY_GET_CHANNEL_LIST_INTENT_RESULT);
                            downloadArticles(channelArrayList);
                            break;
                        }
                        case BROADCAST_GET_ARTICLE_LIST_ACTION: {
                            if(intent.getParcelableArrayListExtra(KEY_GET_ARTICLE_LIST_INTENT_RESULT)!=null) {
                                mINewsListPresenterContract.setArticlListAdapter(intent.<Article>getParcelableArrayListExtra(KEY_GET_ARTICLE_LIST_INTENT_RESULT));
                            }
                        }
                    }
                }
            }
        };
        IntentFilter getChannelIntentFilter = new IntentFilter(BROADCAST_GET_CHANNEL_LIST_ACTION);
        IntentFilter getArticleIntentFilter = new IntentFilter(BROADCAST_GET_ARTICLE_LIST_ACTION);
        mContext.registerReceiver(mBroadcastReceiver, getChannelIntentFilter);
        mContext.registerReceiver(mBroadcastReceiver, getArticleIntentFilter);
    }

    private void downloadArticles(final ArrayList<Channel> channels) {
        mRssService.downloadArticles(channels);
    }

    private void getChannelList() {
        mRssService.getChannelListFromDB();
    }

    public void showArticles(){
        getChannelList();
    }

    public void detach() {
        mContext.unbindService(mServiceConnection);
        mContext.unregisterReceiver(mBroadcastReceiver);
        mINewsListPresenterContract=null;
        mContext = null;
    }

    public void attach(final Context context, final INewsListPresenterContract iNewsListPresenterContract) {
        mINewsListPresenterContract=iNewsListPresenterContract;
        mContext = context;
        bindToService();
        registerBroadcastReceiver();
    }
}
