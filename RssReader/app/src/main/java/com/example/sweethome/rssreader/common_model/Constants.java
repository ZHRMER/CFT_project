package com.example.sweethome.rssreader.common_model;


public final class Constants {
    public static final String KEY_DATABASE_NAME = "rssDataBase";

    public static final String KEY_COLUMN_CHANNEL_NAME = "name";
    public static final String KEY_COLUMN_CHANNEL_LINK = "link";
    public static final String KEY_COLUMN_CHANNEL_LAST_ARTICLE_DATE = "last_article_date";
    public static final String KEY_CHANNELS_TABLE = "channellist";

    public static final String KEY_ARTICLES_TABLE = "articleslist";
    public static final String KEY_COLUMN_ARTICLE_CHANNEL_LINK = "channel_link";
    public static final String KEY_COLUMN_ARTICLE_TITLE = "title";
    public static final String KEY_COLUMN_ARTICLE_DESCRIPTION = "descripton";
    public static final String KEY_COLUMN_ARTICLE_PUBLICATION_DATE = "publication_date";
    public static final String KEY_COLUMN_ARTICLE_LINK = "link";

    public static final String KEY_ARTICLE_LIST = "com.example.sweethome.rssreader.article_list";
    public static final String KEY_ARTICLE_IS_REFRESH = "com.example.sweethome.rssreader.refresh";
    public static final String KEY_LAST_CHECKED_ITEM = "com.example.sweethome.rssreader.last_checked_item";
    public static final String KEY_LAST_CHECKED_ITEM_LINK = "com.example.sweethome.rssreader.last_checked_item_link";
    public static final String KEY_INFO_TEXT = "com.example.sweethome.rssreader.info_text";

    public static final String KEY_ADD_INTENT_RESULT = "resultOfAddChannel";
    public static final String KEY_GET_CHANNEL_LIST_INTENT_RESULT = "resultOfGetChannelList";
    public static final String KEY_GET_ARTICLE_LIST_FROM_WEB_INTENT_RESULT = "resultOfGetArticleListFromWeb";
    public static final String KEY_GET_ARTICLE_LIST_FROM_DB_INTENT_RESULT = "resultOfGetArticleListFromBD";
    public static final String KEY_UPDATE_CHANNELS_LIST_INTENT_RESULT = "resultOfUpdateChannelsList";
    public static final String KEY_WARNING_INTENT_RESULT = "warning";

    public static final String BROADCAST_ADD_ACTION = "com.example.sweethome.rssreader.add_broadcast";
    public static final String BROADCAST_GET_CHANNEL_LIST_ACTION = "com.example.sweethome.rssreader.get_channel_list_broadcast";
    public static final String BROADCAST_GET_CHANNEL_LIST_UPDATE_BY_TIME_ACTION = "com.example.sweethome.rssreader.get_channel_list_update_by_time_broadcast";
    public static final String BROADCAST_WARNING_ACTION = "com.example.sweethome.rssreader.get_article_list_warning_broadcast";
    public static final String BROADCAST_GET_ARTICLE_LIST_ACTION = "com.example.sweethome.rssreader.get_article_list_broadcast";
    public static final String BROADCAST_GET_ARTICLE_LIST_UPDATE_BY_TIME_ACTION = "com.example.sweethome.rssreader.get_article_list_update_by_time_broadcast";
    public static final String BROADCAST_UPDATE_CHANNELS_LIST_ACTION = "com.example.sweethome.rssreader.update_channels_list_broadcast";
    public static final String BROADCAST_UPDATE_CHANNELS_LIST_UPDATE_BY_TIME_ACTION = "com.example.sweethome.rssreader.update_channels_list_update_by_time_broadcast";
}
