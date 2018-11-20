package com.example.sweethome.rssreader.service.tasks;

import com.example.sweethome.rssreader.common_model.database.articles.ArticleDBPresenter;

final public class GetArticlesListTask implements Runnable {
    private final ArticleDBPresenter mArticleDBPresenter;
    private final String mChannelLink;

    public GetArticlesListTask(final ArticleDBPresenter articleDBPresenter, final String channelLink) {
        mArticleDBPresenter = articleDBPresenter;
        mChannelLink = channelLink;
    }

    @Override
    public void run() {
        mArticleDBPresenter.getArticlesList(mChannelLink);
    }
}
