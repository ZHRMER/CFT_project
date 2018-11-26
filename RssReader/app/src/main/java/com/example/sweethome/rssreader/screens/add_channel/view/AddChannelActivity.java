package com.example.sweethome.rssreader.screens.add_channel.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.sweethome.rssreader.R;

public final class AddChannelActivity extends AppCompatActivity {

    private AddChannelView mAddChannelView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_channel);
        mAddChannelView = new AddChannelView(this);
        mAddChannelView.onCreate(savedInstanceState);
        Uri data = this.getIntent().getData();
        if (null != data) {
            mAddChannelView.handleUriData(getIntent().getDataString());
        }
    }

    public static Intent newIntent(final Context context) {
        return new Intent(context, AddChannelActivity.class);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mAddChannelView.onSaveInstanceSaved(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAddChannelView.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAddChannelView.onPause();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
