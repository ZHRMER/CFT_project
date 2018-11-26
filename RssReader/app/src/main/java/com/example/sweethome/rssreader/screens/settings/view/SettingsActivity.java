package com.example.sweethome.rssreader.screens.settings.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.sweethome.rssreader.R;

public final class SettingsActivity extends AppCompatActivity {
    private SettingsView mSettingsView;

    public static Intent newIntent(final Context context) {
        return new Intent(context, SettingsActivity.class);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mSettingsView = new SettingsView(this);
        mSettingsView.onCreate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSettingsView.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSettingsView.onPause();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
