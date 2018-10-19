package com.example.sweethome.rss_reader;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RssNewsListAdapter extends RecyclerView.Adapter<RssNewsListAdapter.NewsViewHolder> {
    private List<RssNewsModel> mRssNewsModels;

    public RssNewsListAdapter(List<RssNewsModel> rssNewsModelList){
        mRssNewsModels=rssNewsModelList;
    }
    @NonNull
    @Override
    public RssNewsListAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.news_item_view,viewGroup,false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RssNewsListAdapter.NewsViewHolder newsViewHolder, int i) {
        final RssNewsModel rssNewsModel=mRssNewsModels.get(i);
        ((TextView)newsViewHolder.mNewsTitle.findViewById(R.id.news_title)).setText(rssNewsModel.getTitle());
        ((TextView)newsViewHolder.mNewsDescription.findViewById(R.id.news_description)).setText(rssNewsModel.getDescription());
    }

    @Override
    public int getItemCount() {
        return mRssNewsModels.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder{
        private TextView mNewsTitle;
        private TextView mNewsDescription;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            mNewsTitle=itemView.findViewById(R.id.news_title);
            mNewsDescription=itemView.findViewById(R.id.news_description);
        }
    }
}
