package com.example.sweethome.rssreader.service.tasks;

import com.example.sweethome.rssreader.common_model.database.articles.ArticleDBPresenter;

public class GetArticlesListTask implements Runnable {
    private final ArticleDBPresenter mArticleDBPresenter;

    public GetArticlesListTask(final ArticleDBPresenter articleDBPresenter) {
        mArticleDBPresenter = articleDBPresenter;
    }

    @Override
    public void run() {
        mArticleDBPresenter.getArticlesList();
    }
}
