package com.example.sweethome.rssreader.web_work;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.Xml;

import com.example.sweethome.rssreader.common_model.Article;
import com.example.sweethome.rssreader.common_model.Channel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.example.sweethome.rssreader.common_model.Constants.BROADCAST_GET_ARTICLE_LIST_ACTION;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_GET_ARTICLE_LIST_INTENT_RESULT;

public class WebWorker {
    private ArrayList<Channel> mChannelArrayList;
    private ArrayList<Article> mArticleArrayList;
    private Context mContext;

    public WebWorker(ArrayList<Channel> channelArrayList, Context context) {
        mChannelArrayList = channelArrayList;
        mContext = context;
    }

    public void downloadArticle() {
        try {
            if (mChannelArrayList.size() == 0) {
                sendIsDownloadBroadcast("No channel");
                return;
            }
            Channel currentChanel = mChannelArrayList.get(0);
            URL url = new URL(currentChanel.getLinkString());
            InputStream inputStream = url.openConnection().getInputStream();
            mArticleArrayList = parseFeed(inputStream);
            inputStream.close();
        } catch (MalformedURLException e) {
            Log.d("myLogs", "Malformed");
            return;
        } catch (IOException e) {
            Log.d("myLogs", "IOException");
            return;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            Log.d("myLogs", "XML");
            return;
        }
        sendArticlesList(mArticleArrayList);
        //sendIsDownloadBroadcast("All is okey");
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
                    Log.d("myLogs", "START_DOCUMENT");
                    break;
                }
                case XmlPullParser.START_TAG: {
                    Log.d("myLogs", "START_TAG " + xmlPullParser.getName());
                    if (xmlPullParser.getName().equalsIgnoreCase("item")) {
                        isItem = true;
                    }
                    if (xmlPullParser.getName().equalsIgnoreCase("title") && isItem) {
                        title = xmlPullParser.nextText();
                    }
                    if (xmlPullParser.getName().equalsIgnoreCase("description") && isItem) {
                        description = xmlPullParser.nextText();
                    }
                    if (xmlPullParser.getName().equalsIgnoreCase("link") && isItem) {
                        link = xmlPullParser.nextText();
                    }
                    if (xmlPullParser.getName().equalsIgnoreCase("pubDate") && isItem) {
                        publicationDate = xmlPullParser.nextText();
                    }
                    break;
                }
                case XmlPullParser.END_TAG: {
                    if (xmlPullParser.getName().equalsIgnoreCase("item")) {
                        Article tempArticle = new Article(title, link, description,publicationDate);
                        articles.add(tempArticle);
                        isItem = false;
                    }
                    Log.d("myLogs", "END_TAG " + xmlPullParser.getName());
                    break;
                }
                case XmlPullParser.END_DOCUMENT: {
                    Log.d("myLogs", "END_DOCUMENT");
                    break;
                }
                case XmlPullParser.TEXT: {
                    Log.d("myLogs", "TEXT " + xmlPullParser.getText());
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

    private void sendIsDownloadBroadcast(final String isAdd) {
        Intent intent = new Intent(BROADCAST_GET_ARTICLE_LIST_ACTION);
        intent.putExtra(KEY_GET_ARTICLE_LIST_INTENT_RESULT, isAdd);
        mContext.sendBroadcast(intent);
    }
}
