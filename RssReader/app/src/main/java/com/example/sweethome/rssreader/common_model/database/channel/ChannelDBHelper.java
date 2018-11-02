package com.example.sweethome.rssreader.common_model.database.channel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.sweethome.rssreader.common_model.Constants.KEY_DATABASE_NAME;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_LINK;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_NAME;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_TABLE_NAME;

public final class ChannelDBHelper extends SQLiteOpenHelper {

    ChannelDBHelper(final Context context) {
        super(context, KEY_DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        db.execSQL("create table "+KEY_TABLE_NAME+" ("
                + KEY_NAME+" text ,"
                + KEY_LINK +" text primary key" + ");");
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        db.execSQL("drop table "+KEY_TABLE_NAME+";");
        onCreate(db);
    }
}

