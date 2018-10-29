package com.example.sweethome.rssreader.common_model.database.channel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.sweethome.rssreader.common_model.Constants.*;

public final class ChannelDBHelper extends SQLiteOpenHelper {

    public ChannelDBHelper(Context context) {
        super(context, KEY_DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+KEY_TABLE_NAME+" ("
                + KEY_NAME+" text ,"
                + KEY_LINK +" text primary key" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table "+KEY_TABLE_NAME+";");
        onCreate(db);
    }
}

