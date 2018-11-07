package com.example.sweethome.rssreader.screens.add_channel.view;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sweethome.rssreader.R;
import com.example.sweethome.rssreader.screens.add_channel.presenter.AddChannelPresenter;
import com.example.sweethome.rssreader.screens.add_channel.presenter.IAddChannelPresenterContract;

class AddChannelView implements IAddChannelPresenterContract {
    private AppCompatActivity mAppCompatActivity;
    private AddChannelPresenter mAddChannelPresenter;
    private EditText linkEditText;
    private EditText nameEditText;

    AddChannelView(final AppCompatActivity appCompatActivity) {
        mAppCompatActivity = appCompatActivity;
    }

    void onCreate() {
        linkEditText = mAppCompatActivity.findViewById(R.id.add_links_EditText);
        nameEditText = mAppCompatActivity.findViewById(R.id.add_name_EditText);

        Button addLinkButton = mAppCompatActivity.findViewById(R.id.add_links_Button);
        initToolBar();
        addLinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mAddChannelPresenter.onAddLinkButtonClick();
            }
        });
        mAddChannelPresenter = new AddChannelPresenter(this, mAppCompatActivity);
    }

    void onResume(final AppCompatActivity appCompatActivity) {
        mAppCompatActivity = appCompatActivity;
        mAddChannelPresenter.attach(this, mAppCompatActivity);
    }

    void onPause() {
        mAppCompatActivity = null;
        mAddChannelPresenter.detach();
    }

    private void initToolBar() {
        Toolbar toolbar = mAppCompatActivity.findViewById(R.id.toolbar_add_channel_activity);
        mAppCompatActivity.setSupportActionBar(toolbar);
        ActionBar actionBar = mAppCompatActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public String getLink() {
        return linkEditText.getText().toString();
    }

    @Override
    public String getName() {
        return nameEditText.getText().toString();
    }

}
