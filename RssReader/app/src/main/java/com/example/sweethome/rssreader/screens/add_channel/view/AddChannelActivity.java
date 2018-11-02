package com.example.sweethome.rssreader.screens.add_channel.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sweethome.rssreader.R;
import com.example.sweethome.rssreader.screens.add_channel.presenter.AddChannelPresenter;
import com.example.sweethome.rssreader.screens.add_channel.presenter.IAddChannelPresenterContract;

public final class AddChannelActivity extends AppCompatActivity implements IAddChannelPresenterContract {

    private AddChannelPresenter mAddChannelPresenter;
    private EditText linkEditText;
    private EditText nameEditText;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_channel);

        linkEditText = findViewById(R.id.add_links_EditText);
        nameEditText = findViewById(R.id.add_name_EditText);

        Button addLinkButton = findViewById(R.id.add_links_Button);
        initToolBar();
        addLinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddChannelPresenter.onAddLinkButtonClick();
            }
        });

        mAddChannelPresenter = new AddChannelPresenter(this, this);

    }

    public static Intent newIntent(final Context context) {
        return new Intent(context, AddChannelActivity.class);
    }

    private void initToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar_add_channel_activity);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    //region IAddChannelPresenterContract override
    @Override
    public String getLink() {
        return linkEditText.getText().toString();
    }

    @Override
    public String getName() {
        return nameEditText.getText().toString();
    }
    //endregion


    @Override
    protected void onResume() {
        mAddChannelPresenter.attach(this, this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        mAddChannelPresenter.detach();
        super.onPause();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
