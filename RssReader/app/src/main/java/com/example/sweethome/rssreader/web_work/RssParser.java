package com.example.sweethome.rssreader.web_work;

import android.util.Xml;

import com.example.sweethome.rssreader.common_model.Article;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

class RssParser {
    private static final String KEY_PARSER_ITEM = "item";
    private static final String KEY_PARSER_TITLE = "title";
    private static final String KEY_PARSER_LINK = "link";
    private static final String KEY_PARSER_DESCRIPTION = "description";
    private static final String KEY_PARSER_PUBLICATION_DATE = "pubDate";
    private String title = null;
    private String link = null;
    private String description = null;
    private String publicationDate = null;
    private boolean isItem = false;
    private ArrayList<Article> articles = new ArrayList<>();


    ArrayList<Article> parseFeed(InputStream inputStream) throws XmlPullParserException, IOException {
        if (null == inputStream) {
            throw new IOException();
        }
        XmlPullParser xmlPullParser = Xml.newPullParser();
        xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
        xmlPullParser.setInput(inputStream, null);
        while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
            switch (xmlPullParser.getEventType()) {
                case XmlPullParser.START_DOCUMENT: {
                    break;
                }
                case XmlPullParser.START_TAG: {

                    if (KEY_PARSER_ITEM.equalsIgnoreCase(xmlPullParser.getName())) {
                        isItem = true;
                    }
                    if (KEY_PARSER_TITLE.equalsIgnoreCase(xmlPullParser.getName()) && isItem) {
                        title = xmlPullParser.nextText();
                    }
                    if (KEY_PARSER_DESCRIPTION.equalsIgnoreCase(xmlPullParser.getName()) && isItem) {
                        description = xmlPullParser.nextText();
                    }
                    if (KEY_PARSER_LINK.equalsIgnoreCase(xmlPullParser.getName()) && isItem) {
                        link = xmlPullParser.nextText();
                    }
                    if (KEY_PARSER_PUBLICATION_DATE.equalsIgnoreCase(xmlPullParser.getName()) && isItem) {
                        publicationDate = xmlPullParser.nextText();
                    }
                    break;
                }
                case XmlPullParser.END_TAG: {
                    if (xmlPullParser.getName().equalsIgnoreCase(KEY_PARSER_ITEM)) {
                        if (title != null && link != null && description != null && publicationDate != null) {
                            Article tempArticle = new Article(title, link, description, publicationDate);
                            articles.add(tempArticle);
                        }
                        isItem = false;
                        title = null;
                        link = null;
                        description = null;
                        publicationDate = null;
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
        }
        return articles;
    }
}
