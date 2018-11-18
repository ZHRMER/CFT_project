package com.example.sweethome.rssreader.common_model.database.articles;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.sweethome.rssreader.common_model.Constants.KEY_ARTICLES_TABLE;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_CHANNELS_TABLE;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_COLUMN_ARTICLE_CHANNEL_LINK;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_COLUMN_ARTICLE_DESCRIPTION;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_COLUMN_ARTICLE_LINK;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_COLUMN_ARTICLE_PUBLICATION_DATE;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_COLUMN_ARTICLE_TITLE;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_COLUMN_CHANNEL_LAST_ARTICLE_DATE;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_COLUMN_CHANNEL_LINK;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_COLUMN_CHANNEL_NAME;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_DATABASE_NAME;

public class ArticleDBHelper extends SQLiteOpenHelper {

    ArticleDBHelper(final Context context) {
        super(context, KEY_DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        db.execSQL("create table " + KEY_ARTICLES_TABLE + " ("
                + KEY_COLUMN_ARTICLE_LINK + " text primary key,"
                + KEY_COLUMN_ARTICLE_CHANNEL_LINK + " text ,"
                + KEY_COLUMN_ARTICLE_TITLE + " text ,"
                + KEY_COLUMN_ARTICLE_DESCRIPTION + " text ,"
                + KEY_COLUMN_ARTICLE_PUBLICATION_DATE + " text" + ");");
        db.execSQL("create table " + KEY_CHANNELS_TABLE + " ("
                + KEY_COLUMN_CHANNEL_NAME + " text ,"
                + KEY_COLUMN_CHANNEL_LINK + " text primary key,"
                + KEY_COLUMN_CHANNEL_LAST_ARTICLE_DATE + " text" + ");");
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        db.execSQL("drop table " + KEY_ARTICLES_TABLE + ";");
        onCreate(db);
    }
}
