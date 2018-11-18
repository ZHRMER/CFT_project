package com.example.sweethome.rssreader.screens.channel_list.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sweethome.rssreader.R;
import com.example.sweethome.rssreader.common_model.Channel;
import com.example.sweethome.rssreader.screens.channel_list.presenter.ChannelListPresenter;

import java.util.List;

public final class ChannelListAdapter extends RecyclerView.Adapter<ChannelListAdapter.ChannelViewHolder> {
    private List<Channel> mChannelList;
    private ChannelListPresenter mChannelListPresenter;
    private final String DELETE_CONTEXT_MENU_ITEM_TEXT = "Удалить";

    ChannelListAdapter(final List<Channel> rssNewsModelList, final ChannelListPresenter channelListPresenter) {
        mChannelList = rssNewsModelList;
        mChannelListPresenter = channelListPresenter;
    }

    @NonNull
    @Override
    public ChannelListAdapter.ChannelViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int position) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.channel_view, viewGroup, false);
        return new ChannelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ChannelListAdapter.ChannelViewHolder newsViewHolder, final int position) {
        newsViewHolder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(DELETE_CONTEXT_MENU_ITEM_TEXT);
                menu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        mChannelListPresenter.deleteChannel(newsViewHolder.mChannelLink.getText().toString());
                        return false;
                    }
                });
            }
        });
        final Channel rssNewsModel = mChannelList.get(position);
        ((TextView) newsViewHolder.mChannelName.findViewById(R.id.channel_name)).setText(rssNewsModel.getName());
        ((TextView) newsViewHolder.mChannelLink.findViewById(R.id.channel_link)).setText(rssNewsModel.getLinkString());
        ((TextView) newsViewHolder.mChannelLastArticleDate.findViewById(R.id.channel_last_article_date)).setText(rssNewsModel.getLastArticlePubDate());
    }

    @Override
    public int getItemCount() {
        return mChannelList.size();
    }

    class ChannelViewHolder extends RecyclerView.ViewHolder {
        private TextView mChannelName;
        private TextView mChannelLink;
        private TextView mChannelLastArticleDate;

        ChannelViewHolder(@NonNull final View itemView) {
            super(itemView);
            mChannelName = itemView.findViewById(R.id.channel_name);
            mChannelLink = itemView.findViewById(R.id.channel_link);
            mChannelLastArticleDate = itemView.findViewById(R.id.channel_last_article_date);
        }
    }
}
