package com.example.sweethome.rssreader.screens.news_list.view;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.sweethome.rssreader.R;
import com.example.sweethome.rssreader.common_model.Article;
import com.example.sweethome.rssreader.screens.news_list.presenter.INewsListPresenterContract;
import com.example.sweethome.rssreader.screens.news_list.presenter.NewsListPresenter;

import java.util.ArrayList;

import static com.example.sweethome.rssreader.common_model.Constants.KEY_ARTICLE_LIST;

public final class NewsListActivity extends AppCompatActivity implements INewsListPresenterContract, NavigationView.OnNavigationItemSelectedListener,
        Toolbar.OnMenuItemClickListener {

    private ActionBarDrawerToggle mToggle;
    private NewsListView mNewsListView;
    private NewsListPresenter mNewsListPresenter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ArrayList <Article> articles;
    private ArticleListAdapter articleListAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_ARTICLE_LIST,articles);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(savedInstanceState!= null){
            articles=savedInstanceState.getParcelableArrayList(KEY_ARTICLE_LIST);
        }
        else{
            articles=new ArrayList<>();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        mNewsListPresenter=new NewsListPresenter(this,this);
        mNewsListView = new NewsListView(this);

        Toolbar toolbar = findViewById(R.id.toolbar_news_list);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbar.setOnMenuItemClickListener(this);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mSwipeRefreshLayout=findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mNewsListPresenter.showArticles();
            }
        });
        mRecyclerView = findViewById(R.id.news_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        articleListAdapter=new ArticleListAdapter(articles);
        mRecyclerView.setAdapter(articleListAdapter);
    }


    @Override
    public void onBackPressed() {
        if (mNewsListView.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mNewsListView.onConfigurationChanged(mToggle);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mNewsListView.onPostCreate(mToggle);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        mNewsListView.onNavigationItemSelected(menuItem);
        return false;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool_bar_menu, menu);
        return true;
    }

    @Override
    protected void onPause() {
        mNewsListView.detach();
        mNewsListPresenter.detach();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mNewsListView.attach(this);
        mNewsListPresenter.attach(this,this);
        super.onResume();
    }

    @Override
    public void stopRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setArticlListAdapter(ArrayList<Article> articleArrayList) {
        articles=articleArrayList;
        articleListAdapter=new ArticleListAdapter(articles);
        mRecyclerView.setAdapter(articleListAdapter);
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
