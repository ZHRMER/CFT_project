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

import static com.example.sweethome.rssreader.common_model.Constants.DELETE_CONTEXT_MENU_ITEM_TEXT;

public final class ChannelListAdapter extends RecyclerView.Adapter<ChannelListAdapter.ChannelViewHolder> {
    private List<Channel> mChannelList;
    private ChannelListPresenter mChannelListPresenter;

    ChannelListAdapter(final List<Channel> rssNewsModelList, final ChannelListPresenter channelListPresenter) {
        mChannelList = rssNewsModelList;
        mChannelListPresenter = channelListPresenter;
    }

    @NonNull
    @Override
    public ChannelListAdapter.ChannelViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.channel_view, viewGroup, false);
        return new ChannelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ChannelListAdapter.ChannelViewHolder newsViewHolder, final int i) {
        newsViewHolder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(DELETE_CONTEXT_MENU_ITEM_TEXT);
                menu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        mChannelListPresenter.deleteChannel(newsViewHolder.mChannelName.getText().toString());
                        return false;
                    }
                });
            }
        });
        final Channel rssNewsModel = mChannelList.get(i);
        ((TextView) newsViewHolder.mChannelName.findViewById(R.id.channel_name)).setText(rssNewsModel.getName());
        ((TextView) newsViewHolder.mChannelLink.findViewById(R.id.channel_link)).setText(rssNewsModel.getLinkString());
    }

    @Override
    public int getItemCount() {
        return mChannelList.size();
    }

    class ChannelViewHolder extends RecyclerView.ViewHolder {
        private TextView mChannelName;
        private TextView mChannelLink;

        ChannelViewHolder(@NonNull final View itemView) {
            super(itemView);
            mChannelName = itemView.findViewById(R.id.channel_name);
            mChannelLink = itemView.findViewById(R.id.channel_link);
        }
    }
}
