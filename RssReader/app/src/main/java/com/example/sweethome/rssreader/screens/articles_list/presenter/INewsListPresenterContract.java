package com.example.sweethome.rssreader.screens.articles_list.presenter;

import com.example.sweethome.rssreader.common_model.Article;

import java.util.ArrayList;

public interface INewsListPresenterContract {
    void stopRefresh();

    void addArticlesToListAdapter(final ArrayList<Article> articleArrayList);

    void setArticlesListToListAdapter(final ArrayList<Article> articleArrayList);
}
