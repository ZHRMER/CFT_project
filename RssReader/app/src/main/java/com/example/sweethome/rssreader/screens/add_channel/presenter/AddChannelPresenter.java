package com.example.sweethome.rssreader.screens.add_channel.presenter;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.example.sweethome.rssreader.common_model.MyService;
import com.example.sweethome.rssreader.common_model.database.channel.ChannelDBPresenter;

import static com.example.sweethome.rssreader.common_model.Constants.BROADCAST_ADD_ACTION;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_ADD_INTENT_RESULT;


public final class AddChannelPresenter {
    private IAddChannelPresenterContract mView;
    private Context mContext;
    private ChannelDBPresenter mChannelDBPresenter;
    private MyService mMyService;
    private ServiceConnection mServiceConnection;
    private BroadcastReceiver mBroadcastReceiver;

    public AddChannelPresenter(IAddChannelPresenterContract view, Context context) {
        mView = view;
        mContext = context;
        mChannelDBPresenter = new ChannelDBPresenter(context);
        bindToService();
        registerBroadcastReceiver();
    }

    private void registerBroadcastReceiver() {
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean isAdd = intent.getBooleanExtra(KEY_ADD_INTENT_RESULT, false);
                if (isAdd) {
                    mView.addLinkSuccess();
                } else {
                    mView.addLinkFail();
                }
            }
        };
        IntentFilter intFilt = new IntentFilter(BROADCAST_ADD_ACTION);
        mContext.registerReceiver(mBroadcastReceiver, intFilt);
    }

    private void bindToService() {
        Intent intent = new Intent(mContext, MyService.class);
        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder binder) {
                mMyService = ((MyService.MyBinder) binder).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
            }
        };
        mContext.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    public void onAddLinkButtonClick() {
        mMyService.addChannelToDB(mChannelDBPresenter, mView.getName(), mView.getLink());
    }

    public void attach(IAddChannelPresenterContract view, Context context) {
        mView = view;
        mContext = context;
        bindToService();
        registerBroadcastReceiver();
    }

    public void detach() {
        mContext.unbindService(mServiceConnection);
        mContext.unregisterReceiver(mBroadcastReceiver);
        mContext = null;
        mView = null;
    }
}
