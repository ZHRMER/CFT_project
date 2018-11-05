package com.example.sweethome.rssreader.common_model;

public final class Constants {
    public static final String KEY_DATABASE_NAME = "rssDataBase";
    public static final String KEY_COLUMN_NAME = "name";
    public static final String KEY_COLUMN_LINK = "link";
    public static final String KEY_TABLE_NAME = "channellist";

    public static final String KEY_ARTICLE_LIST = "com.example.sweethome.rssreader.article_list";

    public static final int NUMBER_OF_THREADS = 4;

    public static final String KEY_ADD_INTENT_RESULT = "resultOfAddChannel";
    public static final String KEY_GET_CHANNEL_LIST_INTENT_RESULT = "resultOfGetChannelList";
    public static final String KEY_GET_ARTICLE_LIST_INTENT_RESULT = "resultOfGetArticleList";
    public static final String KEY_WARNING_INTENT_RESULT = "warning";

    public static final String BROADCAST_ADD_ACTION = "com.example.sweethome.rssreader.add_broadcast";
    public static final String BROADCAST_GET_CHANNEL_LIST_ACTION = "com.example.sweethome.rssreader.get_channel_list_broadcast";
    public static final String BROADCAST_WARNING_ACTION = "com.example.sweethome.rssreader.get_article_list_warning_broadcast";
    public static final String BROADCAST_GET_ARTICLE_LIST_ACTION = "com.example.sweethome.rssreader.get_article_list_broadcast";

    public static final String DELETE_CONTEXT_MENU_ITEM_TEXT = "Удалить";

    public static final String KEY_PARSER_ITEM = "item";
    public static final String KEY_PARSER_TITLE = "title";
    public static final String KEY_PARSER_LINK = "link";
    public static final String KEY_PARSER_DESCRIPTION = "description";
    public static final String KEY_PARSER_PUBLICATION_DATE = "pubDate";
}
