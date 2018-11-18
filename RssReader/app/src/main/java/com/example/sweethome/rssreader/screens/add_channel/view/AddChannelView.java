package com.example.sweethome.rssreader.screens.add_channel.view;

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

class AddChannelView implements IAddChannelPresenterContract {
    private AppCompatActivity mAppCompatActivity;
    private AddChannelPresenter mAddChannelPresenter;
    private EditText linkEditText;
    private EditText nameEditText;
    private TextView mInfoTextView;

    AddChannelView(final AppCompatActivity appCompatActivity) {
        mAppCompatActivity = appCompatActivity;
    }

    void onSaveInstanceSaved(final Bundle outState) {
        outState.putString(KEY_INFO_TEXT, mInfoTextView.getText().toString());
    }

    void onCreate(final Bundle savedInstanceState) {
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
            actionBar.setTitle(R.string.add_channel_Navigation_View);
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

    @Override
    public void setWarningMessage(final String message) {
        mInfoTextView.setTextColor(mAppCompatActivity.getResources().getColor(R.color.colorWarning));
        mInfoTextView.setText(message);
    }

    @Override
    public void setSuccessfulMessage(final String message) {
        mInfoTextView.setTextColor(mAppCompatActivity.getResources().getColor(R.color.colorSuccess));
        mInfoTextView.setText(message);
    }
}
