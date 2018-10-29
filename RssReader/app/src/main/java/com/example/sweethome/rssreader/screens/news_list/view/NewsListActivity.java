package com.example.sweethome.rssreader.screens.news_list.view;

import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;

import com.example.sweethome.rssreader.R;

public final class NewsListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        Toolbar.OnMenuItemClickListener {

    private ActionBarDrawerToggle mToggle;
    private NewsListView mNewsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("myLogs", "NewsListActivity create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        mNewsListView = new NewsListView(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        RecyclerView recyclerView = findViewById(R.id.news_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
        mNewsListView.onConfigurationChanged(newConfig, mToggle);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mNewsListView.onPostCreate(savedInstanceState, mToggle);
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
        super.onPause();
    }

    @Override
    protected void onResume() {
        mNewsListView.attach(this);
        super.onResume();
    }
}
