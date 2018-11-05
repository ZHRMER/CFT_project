package com.example.sweethome.rssreader.web_work;

import android.content.Context;
import android.content.Intent;
import android.util.Xml;

import com.example.sweethome.rssreader.R;
import com.example.sweethome.rssreader.common_model.Article;
import com.example.sweethome.rssreader.common_model.Channel;

import org.xmlpull.v1.XmlPullParser;
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
import static com.example.sweethome.rssreader.common_model.Constants.KEY_PARSER_DESCRIPTION;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_PARSER_ITEM;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_PARSER_LINK;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_PARSER_PUBLICATION_DATE;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_PARSER_TITLE;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_WARNING_INTENT_RESULT;

public class WebWorker {
    private ArrayList<Channel> mChannelArrayList;
    private Context mContext;

    public WebWorker(ArrayList<Channel> channelArrayList, Context context) {
        mChannelArrayList = channelArrayList;
        mContext = context;
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
        for (int i = 0; i < mChannelArrayList.size(); i++) {
            try {
                Channel currentChannel = mChannelArrayList.get(i);
                channelName = currentChannel.getName();
                URL url = new URL(currentChannel.getLinkString());
                inputStream = url.openConnection().getInputStream();
                tempArticleList = parseFeed(inputStream);
                inputStream.close();
            } catch (MalformedURLException e) {
                sendWarningBroadcast(mContext.getString(R.string.url_warning) + " " + channelName);
                continue;
            } catch (IOException e) {
                sendWarningBroadcast(mContext.getString(R.string.io_warning) + " " + channelName);
                continue;
            } catch (XmlPullParserException e) {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e1) {
                        sendWarningBroadcast(mContext.getString(R.string.io_warning) + " " + channelName);
                    }
                }
                sendWarningBroadcast(mContext.getString(R.string.xml_pullparser_warning) + " " + channelName);
                continue;
            }
            articleArrayList.addAll(tempArticleList);
        }
        Collections.sort(articleArrayList);
        Collections.reverse(articleArrayList);
        sendArticlesList(articleArrayList);
    }

    private ArrayList<Article> parseFeed(InputStream inputStream) throws XmlPullParserException, IOException {
        String title = null;
        String link = null;
        String description = null;
        String publicationDate = null;
        boolean isItem = false;
        ArrayList<Article> articles = new ArrayList<>();

        XmlPullParser xmlPullParser = Xml.newPullParser();
        xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        xmlPullParser.setInput(inputStream, null);
        while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
            switch (xmlPullParser.getEventType()) {
                case XmlPullParser.START_DOCUMENT: {
                    break;
                }
                case XmlPullParser.START_TAG: {
                    if (xmlPullParser.getName().equalsIgnoreCase(KEY_PARSER_ITEM)) {
                        isItem = true;
                    }
                    if (xmlPullParser.getName().equalsIgnoreCase(KEY_PARSER_TITLE) && isItem) {
                        title = xmlPullParser.nextText();
                    }
                    if (xmlPullParser.getName().equalsIgnoreCase(KEY_PARSER_DESCRIPTION) && isItem) {
                        description = xmlPullParser.nextText();
                    }
                    if (xmlPullParser.getName().equalsIgnoreCase(KEY_PARSER_LINK) && isItem) {
                        link = xmlPullParser.nextText();
                    }
                    if (xmlPullParser.getName().equalsIgnoreCase(KEY_PARSER_PUBLICATION_DATE) && isItem) {
                        publicationDate = xmlPullParser.nextText();
                    }
                    break;
                }
                case XmlPullParser.END_TAG: {
                    if (xmlPullParser.getName().equalsIgnoreCase(KEY_PARSER_ITEM)) {
                        Article tempArticle = new Article(title, link, description, publicationDate);
                        articles.add(tempArticle);
                        isItem = false;
                    }
                    break;
                }
                case XmlPullParser.END_DOCUMENT: {
                    break;
                }
                case XmlPullParser.TEXT: {
                    break;
                }
            }
            xmlPullParser.next();
        }
        return articles;
    }

    private void sendArticlesList(final ArrayList<Article> articles) {
        Intent intent = new Intent(BROADCAST_GET_ARTICLE_LIST_ACTION);
        intent.putParcelableArrayListExtra(KEY_GET_ARTICLE_LIST_INTENT_RESULT, articles);
        mContext.sendBroadcast(intent);
    }

    private void sendWarningBroadcast(final String isAdd) {
        Intent intent = new Intent(BROADCAST_WARNING_ACTION);
        intent.putExtra(KEY_WARNING_INTENT_RESULT, isAdd);
        mContext.sendBroadcast(intent);
    }
}
