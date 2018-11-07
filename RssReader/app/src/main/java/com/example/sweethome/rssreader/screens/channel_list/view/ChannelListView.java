package com.example.sweethome.rssreader.screens.channel_list.view;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.sweethome.rssreader.R;
import com.example.sweethome.rssreader.common_model.Channel;
import com.example.sweethome.rssreader.screens.channel_list.presenter.ChannelListPresenter;
import com.example.sweethome.rssreader.screens.channel_list.presenter.IChannelListPresenterContract;

import java.util.ArrayList;

public class ChannelListView implements IChannelListPresenterContract {
    private AppCompatActivity mAppCompatActivity;
    private RecyclerView mRecyclerView;
    private ChannelListPresenter mChannelListPresenter;

    ChannelListView(final AppCompatActivity appCompatActivity) {
        mAppCompatActivity = appCompatActivity;
    }

    void onCreate() {
        initToolBar();
        mChannelListPresenter = new ChannelListPresenter(this, mAppCompatActivity);
        mRecyclerView = mAppCompatActivity.findViewById(R.id.channel_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mAppCompatActivity));
    }

    void onResume(final AppCompatActivity appCompatActivity) {
        mAppCompatActivity = appCompatActivity;
        mChannelListPresenter.attach(this, mAppCompatActivity);
    }

    void onPause() {
        mAppCompatActivity = null;
        mChannelListPresenter.detach();
    }

    private void initToolBar() {
        Toolbar toolbar = mAppCompatActivity.findViewById(R.id.toolbar_channel_list_activity);
        mAppCompatActivity.setSupportActionBar(toolbar);
        ActionBar actionBar = mAppCompatActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public void setChannelListAdapter(final ArrayList<Channel> channelList) {
        if (null == channelList) {
            return;
        }
        mRecyclerView.setAdapter(new ChannelListAdapter(channelList, mChannelListPresenter));
    }
}
