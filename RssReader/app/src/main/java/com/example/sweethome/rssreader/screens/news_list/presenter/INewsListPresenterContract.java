package com.example.sweethome.rssreader.screens.news_list.presenter;

import com.example.sweethome.rssreader.common_model.Article;

import java.util.ArrayList;

public interface INewsListPresenterContract {
    void stopRefresh();
    void setArticlListAdapter(final ArrayList<Article> articleArrayList);
}
