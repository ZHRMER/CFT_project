package com.example.sweethome.rssreader.screens.channel_list.presenter;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.example.sweethome.rssreader.common_model.Channel;
import com.example.sweethome.rssreader.common_model.MyService;
import com.example.sweethome.rssreader.common_model.database.channel.ChannelDBPresenter;

import java.util.ArrayList;

import static com.example.sweethome.rssreader.common_model.Constants.BROADCAST_GET_CHANNEL_LIST_ACTION;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_GET_CHANNEL_LIST_INTENT_RESULT;

public final class ChannelListPresenter {
    private IChannelListPresenterContract mView;
    private ChannelDBPresenter mChannelDBPresenter;
    private Context mContext;
    private MyService mMyService;
    private ServiceConnection mServiceConnection;
    private BroadcastReceiver mBroadcastReceiver;

    public ChannelListPresenter(IChannelListPresenterContract view, Context context) {
        mView = view;
        mContext = context;
        mChannelDBPresenter = new ChannelDBPresenter(mContext);
        bindToService();
        registerBroadcastReceiver();
    }

    private void bindToService() {
        Intent intent = new Intent(mContext, MyService.class);
        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder binder) {
                mMyService = ((MyService.MyBinder) binder).getService();
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
            public void onReceive(Context context, Intent intent) {
                ArrayList<Channel> channelArrayList = intent.getParcelableArrayListExtra(KEY_GET_CHANNEL_LIST_INTENT_RESULT);
                mView.setChannelListAdapter(channelArrayList);
            }
        };
        IntentFilter intentFilter = new IntentFilter(BROADCAST_GET_CHANNEL_LIST_ACTION);
        mContext.registerReceiver(mBroadcastReceiver, intentFilter);
    }

    public void getChannelList() {
        mMyService.getChannelListFromDB(mChannelDBPresenter);
    }

    public void detach() {
        mContext.unbindService(mServiceConnection);
        mContext.unregisterReceiver(mBroadcastReceiver);
        mContext = null;
        mView = null;
    }

    public void attach(IChannelListPresenterContract view, Context context) {
        mView = view;
        mContext = context;
        bindToService();
        registerBroadcastReceiver();
    }
}
