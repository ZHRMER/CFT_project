package com.example.sweethome.rssreader.screens.channel_list.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.sweethome.rssreader.R;

public final class ChannelListActivity extends AppCompatActivity {

    private ChannelListView mChannelListView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_list);

        mChannelListView = new ChannelListView(this);
        mChannelListView.onCreate();
    }

    public static Intent newIntent(final Context context) {
        return new Intent(context, ChannelListActivity.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mChannelListView.onResume(this);
    }

    @Override
    protected void onPause() {
        mChannelListView.onPause();
        super.onPause();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
