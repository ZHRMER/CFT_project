package com.example.sweethome.rssreader.web_work;

import android.content.Context;
import android.content.Intent;

import com.example.sweethome.rssreader.R;
import com.example.sweethome.rssreader.common_model.Article;
import com.example.sweethome.rssreader.common_model.Channel;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import static com.example.sweethome.rssreader.common_model.Constants.BROADCAST_GET_ARTICLE_LIST_ACTION;
import static com.example.sweethome.rssreader.common_model.Constants.BROADCAST_WARNING_ACTION;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_GET_ARTICLE_LIST_INTENT_RESULT;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_WARNING_INTENT_RESULT;

public class WebWorker {
    private ArrayList<Channel> mChannelArrayList;
    private Context mContext;

    public WebWorker(final ArrayList<Channel> channelArrayList, final Context context) {
        if (null != channelArrayList && null != context) {
            mChannelArrayList = channelArrayList;
            mContext = context;
        }
    }

    public void downloadArticle() {
        if (mChannelArrayList.size() == 0) {
            sendWarningBroadcast(mContext.getString(R.string.no_channel_warning));
            return;
        }
        ArrayList<Article> tempArticleList;
        ArrayList<Article> articleArrayList = new ArrayList<>();
        String channelName = null;
        InputStream inputStream = null;
        for (int position = 0; position < mChannelArrayList.size(); position++) {
            try {
                Channel currentChannel = mChannelArrayList.get(position);
                channelName = currentChannel.getName();
                URL url = new URL(currentChannel.getLinkString());
                inputStream = url.openConnection().getInputStream();

                RssParser rssParser = new RssParser();
                tempArticleList = rssParser.parseFeed(inputStream);
                if (tempArticleList.size() != 0) {
                    mChannelArrayList.get(position).setLastArticlePubDate(tempArticleList.get(0).getPublicationDate().toString());
                }
                inputStream.close();
            } catch (final MalformedURLException e) {
                sendWarningBroadcast(mContext.getString(R.string.url_warning) + " " + channelName);
                continue;
            } catch (final IOException e) {
                sendWarningBroadcast(mContext.getString(R.string.io_warning) + " " + channelName);
                continue;
            } catch (final XmlPullParserException e) {
                sendWarningBroadcast(mContext.getString(R.string.xml_pullparser_warning) + " " + channelName);
                continue;
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (final IOException e1) {
                        sendWarningBroadcast(mContext.getString(R.string.io_warning) + " " + channelName);
                    }
                }
            }
            articleArrayList.addAll(tempArticleList);
        }
        Collections.sort(articleArrayList);
        Collections.reverse(articleArrayList);
        sendArticlesList(articleArrayList);
    }

    private void sendArticlesList(final ArrayList<Article> articles) {
        if (null == articles) {
            return;
        }
        Intent articleListIntent = new Intent(BROADCAST_GET_ARTICLE_LIST_ACTION);
        articleListIntent.putParcelableArrayListExtra(KEY_GET_ARTICLE_LIST_INTENT_RESULT, articles);
        mContext.sendBroadcast(articleListIntent);
    }

    private void sendWarningBroadcast(final String warningMessage) {
        if (null == warningMessage) {
            return;
        }
        Intent warningIntent = new Intent(BROADCAST_WARNING_ACTION);
        warningIntent.putExtra(KEY_WARNING_INTENT_RESULT, warningMessage);
        mContext.sendBroadcast(warningIntent);
    }
}
