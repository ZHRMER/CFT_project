package com.example.sweethome.rssreader.common_model.database.channel;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.sweethome.rssreader.common_model.Channel;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.example.sweethome.rssreader.common_model.Constants.BROADCAST_ADD_ACTION;
import static com.example.sweethome.rssreader.common_model.Constants.BROADCAST_GET_CHANNEL_LIST_ACTION;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_ADD_INTENT_RESULT;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_GET_CHANNEL_LIST_INTENT_RESULT;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_LINK;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_NAME;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_TABLE_NAME;


public final class ChannelDBPresenter {
    private final ChannelDBHelper mChannelDBHelper;
    private SQLiteDatabase mSQLiteDataBase;
    private final Context mContext;

    public ChannelDBPresenter(final Context context) {
        mChannelDBHelper = new ChannelDBHelper(context);
        mContext = context;
    }

    //region addChanel region
    public void addChannelToDB(final String nameChannel, final String linkChannel) {
        if (nameChannel == null || linkChannel == null) {
            sendIsAddBroadcast(false);
            return;
        }
        try {
            mSQLiteDataBase = mChannelDBHelper.getWritableDatabase();
            if (!isURLLink(linkChannel)) {
                sendIsAddBroadcast(false);
                return;
            }
            ContentValues contentValuesToPut = new ContentValues();
            contentValuesToPut.put(KEY_NAME, nameChannel);
            contentValuesToPut.put(KEY_LINK, linkChannel);
            mSQLiteDataBase.insertOrThrow(KEY_TABLE_NAME, null, contentValuesToPut);
        } catch (SQLException e) {
            sendIsAddBroadcast(false);
            return;
        } finally {
            mSQLiteDataBase.close();
        }
        sendIsAddBroadcast(true);
    }


    private void sendIsAddBroadcast(final boolean isAdd) {
        Intent intent = new Intent(BROADCAST_ADD_ACTION);
        intent.putExtra(KEY_ADD_INTENT_RESULT, isAdd);
        mContext.sendBroadcast(intent);
    }
    //endregion

    //region getChannelList region
    public void getChannelList() {
        Cursor cursor = null;
        ArrayList<Channel> channelList = null;
        try {
            mSQLiteDataBase = mChannelDBHelper.getReadableDatabase();
            cursor = mSQLiteDataBase.query(KEY_TABLE_NAME, null, null, null, null, null, null);
            channelList = new ArrayList<>();
            if (cursor.moveToFirst()) {
                int nameColInd = cursor.getColumnIndex(KEY_NAME);
                int emailColInd = cursor.getColumnIndex(KEY_LINK);
                do {
                    Channel channel = new Channel(cursor.getString(nameColInd), cursor.getString(emailColInd));
                    channelList.add(channel);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            //TODO if cant get channel list
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (mSQLiteDataBase != null) {
                mSQLiteDataBase.close();
            }
            if (channelList != null) {
                sendGetListBroadcast(channelList);
            }
        }
    }

    private void sendGetListBroadcast(final ArrayList<Channel> channelList) {
        if (channelList == null) {
            return;
        }
        Intent intent = new Intent(BROADCAST_GET_CHANNEL_LIST_ACTION);
        intent.putParcelableArrayListExtra(KEY_GET_CHANNEL_LIST_INTENT_RESULT, channelList);
        mContext.sendBroadcast(intent);
    }
    //endregion

    private boolean isURLLink(final String url) {
        try {
            new URL(url);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

}
