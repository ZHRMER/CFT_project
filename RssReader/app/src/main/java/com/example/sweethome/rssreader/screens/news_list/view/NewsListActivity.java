package com.example.sweethome.rssreader.screens.news_list.view;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.example.sweethome.rssreader.R;

public final class NewsListActivity extends AppCompatActivity {

    private NewsListView mNewsListView;

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        mNewsListView.onSaveInstanceSaved(outState);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        mNewsListView = new NewsListView(this);
        mNewsListView.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        mNewsListView.onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        mNewsListView.onPause();
        super.onPause();
    }


    @Override
    public void onBackPressed() {
        if (mNewsListView.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mNewsListView.onConfigurationChanged();
    }

    @Override
    protected void onPostCreate(@Nullable final Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mNewsListView.onPostCreate();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.tool_bar_menu, menu);
        return true;
    }
}
