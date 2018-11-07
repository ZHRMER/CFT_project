package com.example.sweethome.rssreader.screens.news_list.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.sweethome.rssreader.R;
import com.example.sweethome.rssreader.common_model.Article;
import com.example.sweethome.rssreader.screens.add_channel.view.AddChannelActivity;
import com.example.sweethome.rssreader.screens.channel_list.view.ChannelListActivity;
import com.example.sweethome.rssreader.screens.news_list.presenter.INewsListPresenterContract;
import com.example.sweethome.rssreader.screens.news_list.presenter.NewsListPresenter;

import java.util.ArrayList;

import static com.example.sweethome.rssreader.common_model.Constants.KEY_ARTICLE_LIST;


final class NewsListView implements INewsListPresenterContract, Toolbar.OnMenuItemClickListener,
        NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawerLayout;
    private AppCompatActivity mAppCompatActivity;
    private ActionBarDrawerToggle mToggle;
    private NewsListPresenter mNewsListPresenter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ArrayList<Article> articles;
    private ArticleListAdapter articleListAdapter;
    private RecyclerView mRecyclerView;

    NewsListView(final AppCompatActivity appCompatActivity) {
        mAppCompatActivity = appCompatActivity;
        mDrawerLayout = mAppCompatActivity.findViewById(R.id.drawer_layout);
    }

    void onSaveInstanceSaved(final Bundle outState){
        outState.putParcelableArrayList(KEY_ARTICLE_LIST, articles);
    }

    void onCreate(final Bundle savedInstanceState){
        if (savedInstanceState != null) {
            articles = savedInstanceState.getParcelableArrayList(KEY_ARTICLE_LIST);
        } else {
            articles = new ArrayList<>();
        }
        mNewsListPresenter = new NewsListPresenter(mAppCompatActivity, this);

        Toolbar toolbar = mAppCompatActivity.findViewById(R.id.toolbar_news_list);
        mAppCompatActivity.setSupportActionBar(toolbar);
        ActionBar actionBar = mAppCompatActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbar.setOnMenuItemClickListener(this);

        DrawerLayout drawerLayout = mAppCompatActivity.findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(mAppCompatActivity, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        NavigationView navigationView = mAppCompatActivity.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mSwipeRefreshLayout = mAppCompatActivity.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mNewsListPresenter.showArticles();
            }
        });
        mRecyclerView = mAppCompatActivity.findViewById(R.id.news_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mAppCompatActivity));
        articleListAdapter = new ArticleListAdapter(articles);
        mRecyclerView.setAdapter(articleListAdapter);
    }

    void onResume(final AppCompatActivity appCompatActivity){
        mAppCompatActivity=appCompatActivity;
        mNewsListPresenter.attach(mAppCompatActivity, this);
    }

    void onPause(){
        mAppCompatActivity=null;
        mNewsListPresenter.detach();
    }

    boolean onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return false;
        } else {
            return true;
        }
    }

    void onConfigurationChanged() {
        mToggle.syncState();
    }

    void onPostCreate() {
        mToggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.add_links_item: {
                Intent addChannelIntent = AddChannelActivity.newIntent(mAppCompatActivity);
                mAppCompatActivity.startActivity(addChannelIntent);
                break;
            }
            case R.id.list_links: {
                Intent channelListIntent = ChannelListActivity.newIntent(mAppCompatActivity);
                mAppCompatActivity.startActivity(channelListIntent);
                break;
            }
        }
        return false;
    }

    @Override
    public boolean onMenuItemClick(final MenuItem menuItem) {
        return false;
    }

    @Override
    public void stopRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setArticleListAdapter(final ArrayList<Article> articleArrayList) {
        articles = articleArrayList;
        articleListAdapter = new ArticleListAdapter(articles);
        mRecyclerView.setAdapter(articleListAdapter);
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
