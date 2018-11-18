package com.example.sweethome.rssreader.screens.articles_list.view;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.example.sweethome.rssreader.R;

public final class ArticlesListActivity extends AppCompatActivity {

    private ArticlesListView mArticlesListView;

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        mArticlesListView.onSaveInstanceSaved(outState);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        mArticlesListView = new ArticlesListView(this);
        mArticlesListView.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mArticlesListView.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mArticlesListView.onPause();
    }


    @Override
    public void onBackPressed() {
        if (mArticlesListView.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mArticlesListView.onConfigurationChanged();
    }

    @Override
    protected void onPostCreate(@Nullable final Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mArticlesListView.onPostCreate();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.tool_bar_menu, menu);
        return true;
    }
}
