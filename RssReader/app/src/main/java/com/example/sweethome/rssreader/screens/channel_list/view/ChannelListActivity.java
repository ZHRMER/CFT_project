package com.example.sweethome.rssreader.screens.channel_list.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.sweethome.rssreader.R;
import com.example.sweethome.rssreader.common_model.Channel;
import com.example.sweethome.rssreader.screens.channel_list.presenter.ChannelListPresenter;
import com.example.sweethome.rssreader.screens.channel_list.presenter.IChannelListPresenterContract;

import java.util.ArrayList;

public final class ChannelListActivity extends AppCompatActivity implements IChannelListPresenterContract {

    private RecyclerView mRecyclerView;
    private ChannelListPresenter mChannelListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_list);

        initToolBar();

        mChannelListPresenter = new ChannelListPresenter(this, this);

        mRecyclerView = findViewById(R.id.channel_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar_add_channel_activity);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    public static Intent newIntent(final Context context) {
        return new Intent(context, ChannelListActivity.class);
    }

    @Override
    public void setChannelListAdapter(final ArrayList<Channel> channelList) {
        mRecyclerView.setAdapter(new ChannelListAdapter(channelList));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mChannelListPresenter.attach(this, this);
    }

    @Override
    protected void onPause() {
        mChannelListPresenter.detach();
        super.onPause();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
