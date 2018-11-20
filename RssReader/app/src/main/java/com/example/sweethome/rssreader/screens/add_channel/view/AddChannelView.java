package com.example.sweethome.rssreader.screens.add_channel.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sweethome.rssreader.R;
import com.example.sweethome.rssreader.screens.add_channel.presenter.AddChannelPresenter;
import com.example.sweethome.rssreader.screens.add_channel.presenter.IAddChannelPresenterContract;

import static com.example.sweethome.rssreader.common_model.Constants.KEY_INFO_TEXT;

final class AddChannelView implements IAddChannelPresenterContract {
    private static final String APP_PREFERENCES = "mysettings";
    private static final String APP_PREFERENCES_CHANNEL_NAME = "channel_name";
    private static final String APP_PREFERENCES_CHANNEL_LINK = "channel_link";
    private AppCompatActivity mAppCompatActivity;
    private AddChannelPresenter mAddChannelPresenter;
    private EditText mChannelLinkEditText;
    private EditText mChannelNameEditText;
    private TextView mInfoTextView;
    private SharedPreferences mSettings;

    AddChannelView(final AppCompatActivity appCompatActivity) {
        mAppCompatActivity = appCompatActivity;
    }

    void onSaveInstanceSaved(final Bundle outState) {
        outState.putString(KEY_INFO_TEXT, mInfoTextView.getText().toString());
    }

    void onCreate(final Bundle savedInstanceState) {
        mSettings = mAppCompatActivity.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        mInfoTextView = mAppCompatActivity.findViewById(R.id.add_info_TextView);
        if (null != savedInstanceState) {
            String message = savedInstanceState.getString(KEY_INFO_TEXT);
            if (mAppCompatActivity.getString(R.string.add_channel_successful_toast).equalsIgnoreCase(message)) {
                mInfoTextView.setTextColor(mAppCompatActivity.getResources().getColor(R.color.colorSuccess));
            } else {
                mInfoTextView.setTextColor(mAppCompatActivity.getResources().getColor(R.color.colorWarning));
            }
            mInfoTextView.setText(savedInstanceState.getString(KEY_INFO_TEXT));
        }
        mChannelLinkEditText = mAppCompatActivity.findViewById(R.id.add_links_EditText);
        mChannelNameEditText = mAppCompatActivity.findViewById(R.id.add_name_EditText);

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
        loadSharedPreferences();
        mAddChannelPresenter.attach(this, mAppCompatActivity);
    }

    void onPause() {
        saveSharedPreferences();
        mAppCompatActivity = null;
        mAddChannelPresenter.detach();
    }

    private void loadSharedPreferences() {
        if (mSettings.contains(APP_PREFERENCES_CHANNEL_NAME)) {
            mChannelNameEditText.setText(mSettings.getString(APP_PREFERENCES_CHANNEL_NAME, ""));
        }
        if (mSettings.contains(APP_PREFERENCES_CHANNEL_LINK)) {
            mChannelLinkEditText.setText(mSettings.getString(APP_PREFERENCES_CHANNEL_LINK, ""));
        }
    }

    private void saveSharedPreferences() {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(APP_PREFERENCES_CHANNEL_NAME, getName());
        editor.putString(APP_PREFERENCES_CHANNEL_LINK, getLink());
        editor.apply();
    }

    private void initToolBar() {
        Toolbar toolbar = mAppCompatActivity.findViewById(R.id.toolbar_add_channel_activity);
        mAppCompatActivity.setSupportActionBar(toolbar);
        ActionBar actionBar = mAppCompatActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.add_channel_Navigation_View);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public String getLink() {
        return mChannelLinkEditText.getText().toString();
    }

    @Override
    public String getName() {
        return mChannelNameEditText.getText().toString();
    }

    @Override
    public void setWarningMessage(final String message) {
        mInfoTextView.setTextColor(mAppCompatActivity.getResources().getColor(R.color.colorWarning));
        mInfoTextView.setText(message);
    }

    @Override
    public void setSuccessfulMessage(final String message) {
        mChannelLinkEditText.setText("");
        mChannelNameEditText.setText("");
        mInfoTextView.setTextColor(mAppCompatActivity.getResources().getColor(R.color.colorSuccess));
        mInfoTextView.setText(message);
    }
}
