package com.example.sweethome.rssreader.screens.articles_list.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sweethome.rssreader.R;
import com.example.sweethome.rssreader.common_model.Article;

import java.util.List;

public final class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder> {
    private List<Article> mArticleList;

    ArticleListAdapter(final List<Article> rssNewsModelList) {
        mArticleList = rssNewsModelList;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.article_view, viewGroup, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ArticleViewHolder newsViewHolder, final int i) {
        final Article article = mArticleList.get(i);
        ((TextView) newsViewHolder.mArticleTitle.findViewById(R.id.article_name)).setText(article.getTitle());
        ((TextView) newsViewHolder.mArticleLink.findViewById(R.id.article_link)).setText(article.getLinkString());
        ((TextView) newsViewHolder.mArticlePublicationDate.findViewById(R.id.article_publication_date)).setText(article.getPublicationDate().toString());
    }

    @Override
    public int getItemCount() {
        return mArticleList.size();
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder {
        private TextView mArticleTitle;
        private TextView mArticleLink;
        private TextView mArticlePublicationDate;

        ArticleViewHolder(@NonNull final View itemView) {
            super(itemView);
            mArticleTitle = itemView.findViewById(R.id.article_name);
            mArticleLink = itemView.findViewById(R.id.article_link);
            mArticlePublicationDate = itemView.findViewById(R.id.article_publication_date);
        }
    }
}
