package com.example.sweethome.rssreader.web_work;

import android.util.Xml;

import com.example.sweethome.rssreader.common_model.Article;
import com.example.sweethome.rssreader.common_model.Channel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

final class RssParser {
    private static final String KEY_PARSER_ITEM = "item";
    private static final String KEY_PARSER_TITLE = "title";
    private static final String KEY_PARSER_DESCRIPTION = "description";
    private static final String KEY_PARSER_PUBLICATION_DATE = "pubDate";
    private static final String KEY_PARSER_LINK = "link";
    private static final String KEY_PARSER_ENTRY = "entry";
    private static final String KEY_PARSER_DESCRIPTION_ATOM = "content";
    private static final String KEY_PARSER_PUBLICATION_DATE_ATOM = "updated";
    private static final String KEY_PARSER_REL_ATTRIBUTE_ATOM = "rel";
    private static final String KEY_PARSER_ALTERNATE_ATTRIBUTE_ATOM = "alternate";
    private static final String KEY_PARSER_HREF_ATTRIBUTE_ATOM = "href";
    private StringBuilder title = new StringBuilder();
    private String link = null;
    private String description = null;
    private String publicationDate = null;
    private boolean inTitle = false;
    private boolean isItem = false;
    private boolean isLastDateReached = false;
    private ArrayList<Article> articles = new ArrayList<>();


    ArrayList<Article> parseFeed(final InputStream inputStream, final Channel currentChannel) throws XmlPullParserException, IOException {
        if (null == inputStream || null == currentChannel) {
            throw new IOException();
        }
        XmlPullParser xmlPullParser = Xml.newPullParser();
        xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        xmlPullParser.setInput(inputStream, null);
        String tagName;
        while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT && !isLastDateReached) {
            tagName = xmlPullParser.getName();
            switch (xmlPullParser.getEventType()) {
                case XmlPullParser.START_DOCUMENT: {
                    break;
                }
                case XmlPullParser.START_TAG: {
                    if (KEY_PARSER_ITEM.equalsIgnoreCase(tagName) || KEY_PARSER_ENTRY.equalsIgnoreCase(tagName)) {
                        isItem = true;
                    }
                    if (KEY_PARSER_TITLE.equalsIgnoreCase(tagName) && isItem) {
                        inTitle = true;
                    }
                    if ((KEY_PARSER_DESCRIPTION.equalsIgnoreCase(tagName) ||
                            KEY_PARSER_DESCRIPTION_ATOM.equalsIgnoreCase(tagName)) && isItem) {
                        description = xmlPullParser.nextText();
                    }
                    if (KEY_PARSER_LINK.equalsIgnoreCase(tagName) && isItem) {
                        readLink(xmlPullParser);
                    }
                    if ((KEY_PARSER_PUBLICATION_DATE.equalsIgnoreCase(tagName) ||
                            KEY_PARSER_PUBLICATION_DATE_ATOM.equalsIgnoreCase(tagName)) && isItem) {
                        readDate(xmlPullParser, currentChannel);
                    }
                    break;
                }
                case XmlPullParser.END_TAG: {
                    if (KEY_PARSER_TITLE.equalsIgnoreCase(tagName)) {
                        inTitle = false;
                    }
                    if (KEY_PARSER_ITEM.equalsIgnoreCase(tagName) || KEY_PARSER_ENTRY.equalsIgnoreCase(tagName)) {
                        addArticle(currentChannel);
                    }
                    break;
                }
                case XmlPullParser.END_DOCUMENT: {
                    break;
                }
                case XmlPullParser.TEXT: {
                    if (inTitle) {
                        title.append(xmlPullParser.getText());
                    }
                    break;
                }
            }
        }
        return articles;
    }

    private void addArticle(final Channel currentChannel) {
        if (title.length() != 0 && link != null && publicationDate != null) {
            Article tempArticle = new Article(title.toString(), link, description, publicationDate, currentChannel.getLinkString());
            articles.add(tempArticle);
        }
        isItem = false;
        title.setLength(0);
        link = null;
        description = null;
        publicationDate = null;
    }

    private void readDate(final XmlPullParser xmlPullParser, final Channel currentChannel) throws IOException, XmlPullParserException {
        publicationDate = DateUtils.parseDate(xmlPullParser.nextText());
        if (new Date(publicationDate).compareTo(new Date(currentChannel.getLastArticlePubDate())) <= 0) {
            isLastDateReached = true;
        }
    }

    private void readLink(final XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        final String relType = xmlPullParser.getAttributeValue(null, KEY_PARSER_REL_ATTRIBUTE_ATOM);
        if (null == relType) {
            if (null != xmlPullParser.getAttributeValue(null, KEY_PARSER_HREF_ATTRIBUTE_ATOM)) {
                link = xmlPullParser.getAttributeValue(null, KEY_PARSER_HREF_ATTRIBUTE_ATOM);
            } else {
                link = xmlPullParser.nextText();
            }
        } else {
            if (KEY_PARSER_ALTERNATE_ATTRIBUTE_ATOM.equalsIgnoreCase(relType)) {
                link = xmlPullParser.getAttributeValue(null, KEY_PARSER_HREF_ATTRIBUTE_ATOM);
            }
        }
    }
}
