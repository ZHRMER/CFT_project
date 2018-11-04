package com.example.sweethome.rssreader.screens.channel_list.presenter;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.example.sweethome.rssreader.common_model.Channel;
import com.example.sweethome.rssreader.service.RssService;

import java.util.ArrayList;

import static com.example.sweethome.rssreader.common_model.Constants.BROADCAST_GET_CHANNEL_LIST_ACTION;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_GET_CHANNEL_LIST_INTENT_RESULT;

public final class ChannelListPresenter {
    private IChannelListPresenterContract mView;
    private Context mContext;
    private RssService mRssService;
    private ServiceConnection mServiceConnection;
    private BroadcastReceiver mBroadcastReceiver;

    public ChannelListPresenter(final IChannelListPresenterContract view, final Context context) {
        mView = view;
        mContext = context;
    }

    private void bindToService() {
        Intent intent = new Intent(mContext, RssService.class);
        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(final ComponentName name, final IBinder binder) {
                mRssService = ((RssService.RssBinder) binder).getService();
                getChannelList();
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
                ArrayList<Channel> channelArrayList = intent.getParcelableArrayListExtra(KEY_GET_CHANNEL_LIST_INTENT_RESULT);
                mView.setChannelListAdapter(channelArrayList);
            }
        };
        IntentFilter intentFilter = new IntentFilter(BROADCAST_GET_CHANNEL_LIST_ACTION);
        mContext.registerReceiver(mBroadcastReceiver, intentFilter);
    }

    private void getChannelList() {
        mRssService.getChannelListFromDB();
    }

    public void detach() {
        mContext.unbindService(mServiceConnection);
        mContext.unregisterReceiver(mBroadcastReceiver);
        mContext = null;
        mView = null;
    }

    public void attach(final IChannelListPresenterContract view, final Context context) {
        mView = view;
        mContext = context;
        bindToService();
        registerBroadcastReceiver();
    }
}
