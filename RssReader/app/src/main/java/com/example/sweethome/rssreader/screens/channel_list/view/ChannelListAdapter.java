package com.example.sweethome.rssreader.screens.channel_list.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sweethome.rssreader.R;
import com.example.sweethome.rssreader.common_model.Channel;

import java.util.List;

public final class ChannelListAdapter extends RecyclerView.Adapter<ChannelListAdapter.ChannelViewHolder> {
    private List<Channel> mChannelList;

    public ChannelListAdapter(List<Channel> rssNewsModelList) {
        mChannelList = rssNewsModelList;
    }

    @NonNull
    @Override
    public ChannelListAdapter.ChannelViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.channel_view, viewGroup, false);
        return new ChannelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelListAdapter.ChannelViewHolder newsViewHolder, int i) {
        final Channel rssNewsModel = mChannelList.get(i);
        ((TextView) newsViewHolder.mNewsTitle.findViewById(R.id.channel_name)).setText(rssNewsModel.getName());
        ((TextView) newsViewHolder.mNewsDescription.findViewById(R.id.channel_link)).setText(rssNewsModel.getLinkString());
    }

    @Override
    public int getItemCount() {
        return mChannelList.size();
    }

    class ChannelViewHolder extends RecyclerView.ViewHolder {
        private TextView mNewsTitle;
        private TextView mNewsDescription;

        ChannelViewHolder(@NonNull View itemView) {
            super(itemView);
            mNewsTitle = itemView.findViewById(R.id.channel_name);
            mNewsDescription = itemView.findViewById(R.id.channel_link);
        }
    }
}
