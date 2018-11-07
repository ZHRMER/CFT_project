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
import java.util.Collections;

import static com.example.sweethome.rssreader.common_model.Constants.BROADCAST_ADD_ACTION;
import static com.example.sweethome.rssreader.common_model.Constants.BROADCAST_GET_CHANNEL_LIST_ACTION;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_ADD_INTENT_RESULT;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_COLUMN_LINK;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_COLUMN_NAME;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_GET_CHANNEL_LIST_INTENT_RESULT;
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
            String newLinkChannel;
            mSQLiteDataBase = mChannelDBHelper.getWritableDatabase();
            newLinkChannel = isURLLink(linkChannel);
            if (null == newLinkChannel) {
                sendIsAddBroadcast(false);
                return;
            }
            ContentValues contentValuesToPut = new ContentValues();
            contentValuesToPut.put(KEY_COLUMN_NAME, nameChannel);
            contentValuesToPut.put(KEY_COLUMN_LINK, newLinkChannel);
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
        Intent isAddIntent = new Intent(BROADCAST_ADD_ACTION);
        isAddIntent.putExtra(KEY_ADD_INTENT_RESULT, isAdd);
        mContext.sendBroadcast(isAddIntent);
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
                int nameColInd = cursor.getColumnIndex(KEY_COLUMN_NAME);
                int emailColInd = cursor.getColumnIndex(KEY_COLUMN_LINK);
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
                Collections.sort(channelList);
                sendGetChannelListBroadcast(channelList);
            }
        }
    }

    private void sendGetChannelListBroadcast(final ArrayList<Channel> channelList) {
        if (channelList == null) {
            return;
        }
        Intent channelListIntent = new Intent(BROADCAST_GET_CHANNEL_LIST_ACTION);
        channelListIntent.putParcelableArrayListExtra(KEY_GET_CHANNEL_LIST_INTENT_RESULT, channelList);
        mContext.sendBroadcast(channelListIntent);
    }
    //endregion

    //region deleteChannel region
    public void deleteChannelFromDB(final String name) {
        if (null == name) {
            return;
        }
        try {
            mSQLiteDataBase = mChannelDBHelper.getWritableDatabase();
            mSQLiteDataBase.delete(KEY_TABLE_NAME, KEY_COLUMN_NAME + " = '" + name + "'", null);
        } catch (SQLException e) {
            //TODO handle SQLException
        } finally {
            getChannelList();
            if (mSQLiteDataBase != null) {
                mSQLiteDataBase.close();
            }
        }
    }
    //endregion


    private String isURLLink(final String url) {
        if (null == url) {
            return null;
        }
        StringBuilder linkString = new StringBuilder();
        try {
            if (!url.startsWith("http://") || !url.startsWith("https://")) {
                linkString.append("http://").append(url);
            }
            new URL(linkString.toString());
            return linkString.toString();
        } catch (MalformedURLException e) {
            return null;
        }
    }
}
