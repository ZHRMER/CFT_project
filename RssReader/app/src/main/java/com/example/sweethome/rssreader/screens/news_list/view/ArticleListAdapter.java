package com.example.sweethome.rssreader.screens.news_list.view;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ((TextView) newsViewHolder.mArticleDescription.findViewById(R.id.article_description)).
                    setText(Html.fromHtml(article.getDescription(),Html.FROM_HTML_MODE_LEGACY,null,null));
        } else {
            ((TextView) newsViewHolder.mArticleDescription.findViewById(R.id.article_description)).
                    setText(Html.fromHtml(article.getDescription()));
        }
        ((TextView) newsViewHolder.mArticlePublicationDate.findViewById(R.id.article_publication_date)).setText(article.getPublicationDate().toString());
    }

    @Override
    public int getItemCount() {
        return mArticleList.size();
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder {
        private TextView mArticleTitle;
        private TextView mArticleLink;
        private TextView mArticleDescription;
        private TextView mArticlePublicationDate;

        ArticleViewHolder(@NonNull final View itemView) {
            super(itemView);
            mArticleTitle = itemView.findViewById(R.id.article_name);
            mArticleDescription = itemView.findViewById(R.id.article_description);
            mArticleLink = itemView.findViewById(R.id.article_link);
            mArticlePublicationDate = itemView.findViewById(R.id.article_publication_date);
        }
    }
}
