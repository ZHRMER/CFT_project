package com.example.sweethome.rssreader.screens.add_channel.presenter;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.example.sweethome.rssreader.R;
import com.example.sweethome.rssreader.service.RssService;

import static com.example.sweethome.rssreader.common_model.Constants.BROADCAST_ADD_ACTION;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_ADD_INTENT_RESULT;


public final class AddChannelPresenter {
    private IAddChannelPresenterContract mView;
    private Context mContext;
    private RssService mRssService;
    private ServiceConnection mServiceConnection;
    private BroadcastReceiver mBroadcastReceiver;

    public AddChannelPresenter(final IAddChannelPresenterContract view, final Context context) {
        mView = view;
        mContext = context;
    }

    private void registerBroadcastReceiver() {
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                boolean isAdd = intent.getBooleanExtra(KEY_ADD_INTENT_RESULT, false);
                if (isAdd) {
                    mView.setSuccessfulMessage(mContext.getResources().getText(R.string.add_channel_successful_toast).toString());
                } else {
                    mView.setWarningMessage(mContext.getResources().getText(R.string.add_channel_fail_toast).toString());
                }
            }
        };
        IntentFilter broadcastAddIntentFilter = new IntentFilter(BROADCAST_ADD_ACTION);
        mContext.registerReceiver(mBroadcastReceiver, broadcastAddIntentFilter);
    }

    private void bindToService() {
        Intent rssServiceIntent = RssService.newIntent(mContext);
        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(final ComponentName name, final IBinder binder) {
                mRssService = ((RssService.RssBinder) binder).getService();
            }

            @Override
            public void onServiceDisconnected(final ComponentName name) {
            }
        };
        mContext.bindService(rssServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    public void onAddLinkButtonClick() {
        mRssService.addChannelToDB(mView.getName(), mView.getLink());
    }

    public void attach(final IAddChannelPresenterContract view, final Context context) {
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
