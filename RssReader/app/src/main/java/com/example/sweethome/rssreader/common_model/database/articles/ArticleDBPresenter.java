package com.example.sweethome.rssreader.common_model.database.articles;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.sweethome.rssreader.common_model.Article;

import java.util.ArrayList;
import java.util.Collections;

import static com.example.sweethome.rssreader.common_model.Constants.BROADCAST_GET_ARTICLE_LIST_ACTION;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_ARTICLES_TABLE;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_COLUMN_ARTICLE_CHANNEL_LINK;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_COLUMN_ARTICLE_DESCRIPTION;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_COLUMN_ARTICLE_LINK;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_COLUMN_ARTICLE_PUBLICATION_DATE;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_COLUMN_ARTICLE_TITLE;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_GET_ARTICLE_LIST_FROM_DB_INTENT_RESULT;

public final class ArticleDBPresenter {
    private final DBHelper mDBHelper;
    private SQLiteDatabase mSQLiteDataBase;
    private final Context mContext;

    public ArticleDBPresenter(final Context context) {
        mDBHelper = new DBHelper(context);
        mContext = context;
    }

    //region addArticles region
    public void addArticlesToDB(final ArrayList<Article> articleArrayList) {
        if (null == articleArrayList) {
            return;
        }
        try {
            mSQLiteDataBase = mDBHelper.getWritableDatabase();
            ContentValues contentValuesToPut;
            for (Article currentArticle : articleArrayList) {
                contentValuesToPut = new ContentValues();
                contentValuesToPut.put(KEY_COLUMN_ARTICLE_LINK, currentArticle.getLinkString());
                contentValuesToPut.put(KEY_COLUMN_ARTICLE_CHANNEL_LINK, currentArticle.getChannelLink());
                contentValuesToPut.put(KEY_COLUMN_ARTICLE_TITLE, currentArticle.getTitle());
                contentValuesToPut.put(KEY_COLUMN_ARTICLE_DESCRIPTION, currentArticle.getDescription());
                contentValuesToPut.put(KEY_COLUMN_ARTICLE_PUBLICATION_DATE, currentArticle.getPublicationDate().toString());
                mSQLiteDataBase.insertOrThrow(KEY_ARTICLES_TABLE, null, contentValuesToPut);
            }
        } catch (final SQLException ignored) {

        } finally {
            mDBHelper.close();
        }
    }
    //endregion

    //region getArticlesList region
    public void getArticlesList(final String channelLink) {
        Cursor cursor = null;
        ArrayList<Article> articleArrayList = null;
        try {
            mSQLiteDataBase = mDBHelper.getReadableDatabase();
            if ("".equals(channelLink)) {
                cursor = mSQLiteDataBase.query(KEY_ARTICLES_TABLE, null, null, null,
                        null, null, null);
            } else {
                cursor = mSQLiteDataBase.query(KEY_ARTICLES_TABLE, null, KEY_COLUMN_ARTICLE_CHANNEL_LINK + " = '" + channelLink + "'",
                        null, null, null, null);
            }
            articleArrayList = new ArrayList<>();
            if (cursor.moveToFirst()) {
                int articleTitleColumnIndex = cursor.getColumnIndex(KEY_COLUMN_ARTICLE_TITLE);
                int articleLinkColumnIndex = cursor.getColumnIndex(KEY_COLUMN_ARTICLE_LINK);
                int articleChannelLinkColumnIndex = cursor.getColumnIndex(KEY_COLUMN_ARTICLE_CHANNEL_LINK);
                int articleDescriptionColumnIndex = cursor.getColumnIndex(KEY_COLUMN_ARTICLE_DESCRIPTION);
                int articlePublicationDateColumnIndex = cursor.getColumnIndex(KEY_COLUMN_ARTICLE_PUBLICATION_DATE);
                do {
                    Article article = new Article(cursor.getString(articleTitleColumnIndex), cursor.getString(articleLinkColumnIndex),
                            cursor.getString(articleDescriptionColumnIndex), cursor.getString(articlePublicationDateColumnIndex),
                            cursor.getString(articleChannelLinkColumnIndex));
                    articleArrayList.add(article);
                } while (cursor.moveToNext());
            }
        } catch (final SQLException ignored) {

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            mDBHelper.close();
            if (articleArrayList != null) {
                Collections.sort(articleArrayList);
                sendGetArticlesListBroadcast(articleArrayList);
            }
        }
    }

    private void sendGetArticlesListBroadcast(final ArrayList<Article> articleArrayList) {
        if (articleArrayList == null) {
            return;
        }
        Intent channelListIntent = new Intent(BROADCAST_GET_ARTICLE_LIST_ACTION);
        channelListIntent.putParcelableArrayListExtra(KEY_GET_ARTICLE_LIST_FROM_DB_INTENT_RESULT, articleArrayList);
        mContext.sendBroadcast(channelListIntent);
    }
    //endregion
}
