package com.example.sweethome.rssreader.web_work;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.sweethome.rssreader.R;
import com.example.sweethome.rssreader.common_model.Article;
import com.example.sweethome.rssreader.common_model.Channel;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;

import static com.example.sweethome.rssreader.common_model.Constants.BROADCAST_GET_ARTICLE_LIST_ACTION;
import static com.example.sweethome.rssreader.common_model.Constants.BROADCAST_GET_ARTICLE_LIST_UPDATE_BY_TIME_ACTION;
import static com.example.sweethome.rssreader.common_model.Constants.BROADCAST_UPDATE_CHANNELS_LIST_ACTION;
import static com.example.sweethome.rssreader.common_model.Constants.BROADCAST_UPDATE_CHANNELS_LIST_UPDATE_BY_TIME_ACTION;
import static com.example.sweethome.rssreader.common_model.Constants.BROADCAST_WARNING_ACTION;
import static com.example.sweethome.rssreader.common_model.Constants.BROADCAST_WARNING_UPDATE_BY_TIME_ACTION;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_GET_ARTICLE_LIST_FROM_WEB_INTENT_RESULT;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_UPDATE_CHANNELS_LIST_INTENT_RESULT;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_WARNING_INTENT_RESULT;

public final class WebWorker {
    private static final int TIME_TO_TIMEOUT = 5000;
    private ArrayList<Channel> mChannelArrayList;
    private Context mContext;
    private boolean isUpdateChannelList;

    public WebWorker(final ArrayList<Channel> channelArrayList, final Context context) {
        if (null != channelArrayList && null != context) {
            mChannelArrayList = channelArrayList;
            mContext = context;
            isUpdateChannelList = false;
        }
    }

    public void downloadArticle(final boolean isByTime) {
        if (!isOnline()) {
            sendWarningBroadcast(mContext.getString(R.string.no_internet), isByTime);
            return;
        }
        if (mChannelArrayList.size() == 0) {
            sendWarningBroadcast(mContext.getString(R.string.no_channel_warning), isByTime);
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
                URLConnection rssConnection = url.openConnection();
                rssConnection.setConnectTimeout(TIME_TO_TIMEOUT);
                inputStream = rssConnection.getInputStream();
                RssParser rssParser = new RssParser();
                tempArticleList = rssParser.parseFeed(inputStream, currentChannel);
                if (tempArticleList.size() != 0) {
                    mChannelArrayList.get(position).setLastArticlePubDate(tempArticleList.get(0).getPublicationDate().toString());
                    isUpdateChannelList = true;
                }
                inputStream.close();
            } catch (final MalformedURLException e) {
                if (!isByTime) {
                    sendWarningBroadcast(mContext.getString(R.string.url_warning) + " " + channelName, false);
                }
                continue;
            } catch (final IOException e) {
                if (!isByTime) {
                    sendWarningBroadcast(mContext.getString(R.string.io_warning) + " " + channelName, false);
                }
                continue;
            } catch (final XmlPullParserException e) {
                if (!isByTime) {
                    sendWarningBroadcast(mContext.getString(R.string.xml_pullparser_warning) + " " + channelName, false);
                }
                continue;
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (final IOException e1) {
                        if (!isByTime) {
                            sendWarningBroadcast(mContext.getString(R.string.io_warning) + " " + channelName, false);
                        }
                    }
                }
            }
            articleArrayList.addAll(tempArticleList);
        }
        if (isUpdateChannelList) {
            sendUpdateChannelsList(isByTime);
        }
        Collections.sort(articleArrayList);
        Collections.reverse(articleArrayList);
        sendArticlesList(articleArrayList, isByTime);
    }

    private void sendArticlesList(final ArrayList<Article> articles, final boolean isByTime) {
        if (null == articles) {
            sendWarningBroadcast("", isByTime);
        }
        Intent articleListIntent;
        if (isByTime) {
            articleListIntent = new Intent(BROADCAST_GET_ARTICLE_LIST_UPDATE_BY_TIME_ACTION);
        } else {
            articleListIntent = new Intent(BROADCAST_GET_ARTICLE_LIST_ACTION);
        }
        articleListIntent.putParcelableArrayListExtra(KEY_GET_ARTICLE_LIST_FROM_WEB_INTENT_RESULT, articles);
        mContext.sendBroadcast(articleListIntent);
    }

    private void sendUpdateChannelsList(final boolean isByTime) {
        Intent channelsListIntent;
        if (isByTime) {
            channelsListIntent = new Intent(BROADCAST_UPDATE_CHANNELS_LIST_UPDATE_BY_TIME_ACTION);
        } else {
            channelsListIntent = new Intent(BROADCAST_UPDATE_CHANNELS_LIST_ACTION);
        }
        channelsListIntent.putParcelableArrayListExtra(KEY_UPDATE_CHANNELS_LIST_INTENT_RESULT, mChannelArrayList);
        mContext.sendBroadcast(channelsListIntent);
    }

    private void sendWarningBroadcast(final String warningMessage, final boolean isByTime) {
        if (null == warningMessage) {
            return;
        }
        Intent warningIntent;
        if (isByTime) {
            warningIntent = new Intent(BROADCAST_WARNING_UPDATE_BY_TIME_ACTION);
        } else {
            warningIntent = new Intent(BROADCAST_WARNING_ACTION);
        }
        warningIntent.putExtra(KEY_WARNING_INTENT_RESULT, warningMessage);
        mContext.sendBroadcast(warningIntent);
    }

    private boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
