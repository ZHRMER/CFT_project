package com.example.sweethome.rssreader.screens.news_list.view;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;

import com.example.sweethome.rssreader.R;
import com.example.sweethome.rssreader.screens.add_channel.view.AddChannelActivity;
import com.example.sweethome.rssreader.screens.channel_list.view.ChannelListActivity;


public final class NewsListView {
    private DrawerLayout mDrawerLayout;
    private Activity mActivity;

    NewsListView(final Activity activity) {
        mActivity = activity;
        mDrawerLayout = mActivity.findViewById(R.id.drawer_layout);
    }

    boolean onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return false;
        } else {
            return true;
        }
    }

    void onConfigurationChanged(final ActionBarDrawerToggle mToggle) {
        mToggle.syncState();
    }

    void onPostCreate(final ActionBarDrawerToggle mToggle) {
        mToggle.syncState();
    }

    void onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.add_links_item: {
                Intent addChannelIntent = AddChannelActivity.newIntent(mActivity);
                mActivity.startActivity(addChannelIntent);
                break;
            }
            case R.id.list_links: {
                Intent channelListIntent = ChannelListActivity.newIntent(mActivity);
                mActivity.startActivity(channelListIntent);
                break;
            }
        }
    }

    void detach() {
        mActivity = null;
    }

    void attach(final Activity activity) {
        mActivity = activity;
    }

    public boolean onMenuItemClick(MenuItem menuItem) {
        return true;
    }
}
