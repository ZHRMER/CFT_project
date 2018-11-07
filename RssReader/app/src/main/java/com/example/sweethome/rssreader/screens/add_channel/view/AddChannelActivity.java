package com.example.sweethome.rssreader.screens.add_channel.view;

import android.content.Context;
import android.content.Intent;
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
        mAddChannelView.onCreate();

    }

    public static Intent newIntent(final Context context) {
        return new Intent(context, AddChannelActivity.class);
    }

    @Override
    protected void onResume() {
        mAddChannelView.onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        mAddChannelView.onPause();
        super.onPause();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
