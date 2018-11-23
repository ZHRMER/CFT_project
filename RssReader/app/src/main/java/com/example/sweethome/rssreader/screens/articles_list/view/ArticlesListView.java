package com.example.sweethome.rssreader.screens.articles_list.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.sweethome.rssreader.R;
import com.example.sweethome.rssreader.common_model.Article;
import com.example.sweethome.rssreader.common_model.Channel;
import com.example.sweethome.rssreader.screens.add_channel.view.AddChannelActivity;
import com.example.sweethome.rssreader.screens.articles_list.presenter.ChannelListPresenter;
import com.example.sweethome.rssreader.screens.articles_list.presenter.IChannelListPresenterContract;
import com.example.sweethome.rssreader.screens.articles_list.presenter.INewsListPresenterContract;
import com.example.sweethome.rssreader.screens.articles_list.presenter.NewsListPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import static com.example.sweethome.rssreader.common_model.Constants.KEY_ARTICLE_IS_REFRESH;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_ARTICLE_LIST;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_LAST_CHECKED_ITEM;
import static com.example.sweethome.rssreader.common_model.Constants.KEY_LAST_CHECKED_ITEM_LINK;


final class ArticlesListView implements INewsListPresenterContract, Toolbar.OnMenuItemClickListener,
        NavigationView.OnNavigationItemSelectedListener, IChannelListPresenterContract {
    private ChannelListPresenter mChannelListPresenter;
    private NewsListPresenter mNewsListPresenter;
    private AppCompatActivity mAppCompatActivity;
    private RecyclerView mRecyclerView;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ArrayList<Article> mArticleArrayList;
    private ArticleListAdapter mArticleListAdapter;
    private int mLastCheckedItem;
    private String mLastCheckedItemLink;

    ArticlesListView(final AppCompatActivity appCompatActivity) {
        mAppCompatActivity = appCompatActivity;
        mDrawerLayout = mAppCompatActivity.findViewById(R.id.drawer_layout);
    }

    void onSaveInstanceSaved(final Bundle outState) {
        outState.putParcelableArrayList(KEY_ARTICLE_LIST, mArticleArrayList);
        outState.putBoolean(KEY_ARTICLE_IS_REFRESH, mSwipeRefreshLayout.isRefreshing());
        outState.putInt(KEY_LAST_CHECKED_ITEM, mLastCheckedItem);
        outState.putString(KEY_LAST_CHECKED_ITEM_LINK, mLastCheckedItemLink);
    }

    void onCreate(final Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mArticleArrayList = savedInstanceState.getParcelableArrayList(KEY_ARTICLE_LIST);
            mSwipeRefreshLayout = mAppCompatActivity.findViewById(R.id.swipe_refresh_layout);
            mSwipeRefreshLayout.setRefreshing(savedInstanceState.getBoolean(KEY_ARTICLE_IS_REFRESH));
            mLastCheckedItem = savedInstanceState.getInt(KEY_LAST_CHECKED_ITEM);
            mLastCheckedItemLink = savedInstanceState.getString(KEY_LAST_CHECKED_ITEM_LINK);
            mNewsListPresenter = new NewsListPresenter(mAppCompatActivity, this, mLastCheckedItemLink);
        } else {
            mArticleArrayList = new ArrayList<>();
            mLastCheckedItem = R.id.all_channels_item;
            mNewsListPresenter = new NewsListPresenter(mAppCompatActivity, this);
        }
        mChannelListPresenter = new ChannelListPresenter(this, mAppCompatActivity);

        Toolbar toolbar = mAppCompatActivity.findViewById(R.id.toolbar_news_list);
        mAppCompatActivity.setSupportActionBar(toolbar);
        ActionBar actionBar = mAppCompatActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbar.setOnMenuItemClickListener(this);

        mToggle = new ActionBarDrawerToggle(mAppCompatActivity, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        mNavigationView = mAppCompatActivity.findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        mSwipeRefreshLayout = mAppCompatActivity.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mNewsListPresenter.updateArticles();
            }
        });
        mRecyclerView = mAppCompatActivity.findViewById(R.id.news_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mAppCompatActivity));
        mArticleListAdapter = new ArticleListAdapter(mArticleArrayList);
        mRecyclerView.setAdapter(mArticleListAdapter);
    }

    void onResume(final AppCompatActivity appCompatActivity) {
        mAppCompatActivity = appCompatActivity;
        mNewsListPresenter.attach(mAppCompatActivity, this);
        mChannelListPresenter.attach(this, mAppCompatActivity);
    }

    void onPause() {
        mAppCompatActivity = null;
        mNewsListPresenter.detach();
        mChannelListPresenter.detach();
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
            case R.id.add_channel_item: {
                Intent addChannelIntent = AddChannelActivity.newIntent(mAppCompatActivity);
                mAppCompatActivity.startActivity(addChannelIntent);
                break;
            }
            case R.id.all_channels_item: {
                mLastCheckedItem = menuItem.getItemId();
                mNewsListPresenter.setDefineChannelLink("");
                break;
            }
            default: {
                menuItem.setChecked(true);
                mLastCheckedItem = menuItem.getItemId();
                TextView textView = menuItem.getActionView().findViewById(R.id.channel_link);
                mLastCheckedItemLink = textView.getText().toString();
                mNewsListPresenter.setDefineChannelLink(mLastCheckedItemLink);
                break;
            }
        }
        return true;
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
    public void addArticlesToListAdapter(final ArrayList<Article> articleArrayList) {
        mArticleArrayList.addAll(articleArrayList);
        updateArticlesAdapter();
    }

    @Override
    public void setArticlesListToListAdapter(final ArrayList<Article> articleArrayList) {
        mArticleArrayList = articleArrayList;
        updateArticlesAdapter();
    }

    private void updateArticlesAdapter() {
        Collections.sort(mArticleArrayList);
        Collections.reverse(mArticleArrayList);
        mArticleListAdapter = new ArticleListAdapter(mArticleArrayList);
        mRecyclerView.setAdapter(mArticleListAdapter);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setChannelList(final ArrayList<Channel> channelList) {

        Menu menu = mNavigationView.getMenu();
        menu.removeGroup(R.id.channel_list_group);
        for (final Channel currentChannel : channelList) {
            initMenuItem(menu, currentChannel);
        }
        menu.findItem(mLastCheckedItem).setChecked(true);
        mNavigationView.setNavigationItemSelectedListener(this);
        mNewsListPresenter.setChannelsArrayList(channelList);
    }

    private void initMenuItem(final Menu menu, final Channel currentChannel) {
        TextView channelNameTextView;
        TextView channelLinkTextView;
        final MenuItem item = menu.add(R.id.channel_list_group, currentChannel.getName().hashCode(), menu.NONE, "").
                setActionView(R.layout.channel_view);
        View view = item.getActionView();
        item.setCheckable(true);

        channelNameTextView = view.findViewById(R.id.channel_name);
        channelLinkTextView = view.findViewById(R.id.channel_link);
        if (new Date(currentChannel.getLastArticlePubDate()).compareTo(new Date(0)) == 0) {
            channelNameTextView.setTextColor(mAppCompatActivity.getResources().getColor(R.color.colorWarning));
        } else {
            channelNameTextView.setTextColor(mAppCompatActivity.getResources().getColor(R.color.colorBlack));
        }
        channelNameTextView.setText(currentChannel.getName());
        channelLinkTextView.setText(currentChannel.getLinkString());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNavigationItemSelected(item);
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                item.setChecked(true);
                AlertDialog.Builder builder = new AlertDialog.Builder(mAppCompatActivity);
                builder.setTitle(currentChannel.getName());
                final String[] paramsArray = {mAppCompatActivity.getString(R.string.delete_item_menu_text)};
                builder.setItems(paramsArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mAppCompatActivity.getString(R.string.delete_item_menu_text).equals(paramsArray[which])) {
                            mChannelListPresenter.deleteChannel(currentChannel.getLinkString());
                            mNewsListPresenter.deleteChannel(currentChannel.getLinkString());
                            mLastCheckedItem = R.id.all_channels_item;
                        }
                    }
                });
                builder.show();
                return true;
            }
        });
    }
}
