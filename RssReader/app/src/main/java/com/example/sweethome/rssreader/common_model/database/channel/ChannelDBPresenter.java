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

import static com.example.sweethome.rssreader.common_model.Constants.*;

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
        mSQLiteDataBase = mChannelDBHelper.getReadableDatabase();
        Cursor cursor = mSQLiteDataBase.query(KEY_TABLE_NAME, null, null, null, null, null, null);
        ArrayList<Channel> channelList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            int nameColInd = cursor.getColumnIndex(KEY_NAME);
            int emailColInd = cursor.getColumnIndex(KEY_LINK);
            do {
                Channel channel = new Channel(cursor.getString(nameColInd), cursor.getString(emailColInd));
                channelList.add(channel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        mSQLiteDataBase.close();
        sendGetListBroadcast(channelList);
    }

    private void sendGetListBroadcast(final ArrayList<Channel> channelList) {
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
