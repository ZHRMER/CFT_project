package com.example.sweethome.rssreader.service.tasks;

import com.example.sweethome.rssreader.common_model.Article;
import com.example.sweethome.rssreader.common_model.database.articles.ArticleDBPresenter;

import java.util.ArrayList;

public class AddArticlesListTask implements Runnable {
    private final ArticleDBPresenter mArticleDBPresenter;
    private final ArrayList<Article> mArticleArrayList;

    public AddArticlesListTask(final ArrayList<Article> articleArrayList, final ArticleDBPresenter articleDBPresenter) {
        mArticleArrayList = articleArrayList;
        mArticleDBPresenter = articleDBPresenter;
    }

    @Override
    public void run() {
        mArticleDBPresenter.addArticlesToDB(mArticleArrayList);
    }
}
