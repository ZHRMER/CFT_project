package com.example.sweethome.rssreader.screens.settings.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.sweethome.rssreader.R;
import com.example.sweethome.rssreader.screens.settings.presenter.ISettingsPresenterContract;
import com.example.sweethome.rssreader.screens.settings.presenter.SettingsPresenter;

final class SettingsView implements ISettingsPresenterContract {
    private static final String KEY_LAST_CHECKED_TIME_INTERVAL = "KEY_LAST_CHECKED_TIME_INTERVAL";
    private static final String KEY_IS_CHECKED_INTERVAL = "KEY_IS_CHECKED_INTERVAL";
    private static final String APP_PREFERENCES = "mysettings";
    private String[] listItems;
    private SettingsPresenter mSettingsPresenter;
    private AppCompatActivity mAppCompatActivity;
    private TextView mIntervalTimeTextView;
    private SharedPreferences mSharedPreferences;
    private final int[] mLastCheckedItem = {0};
    private boolean mIsUpdateByTimeChecked;

    SettingsView(final AppCompatActivity appCompatActivity) {
        mAppCompatActivity = appCompatActivity;
        listItems = mAppCompatActivity.getResources().getStringArray(R.array.times);
    }

    void onCreate() {
        mSharedPreferences = mAppCompatActivity.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        final CheckBox updateByTimeCheckBox = mAppCompatActivity.findViewById(R.id.update_by_time_CheckBox);
        mIntervalTimeTextView = mAppCompatActivity.findViewById(R.id.interval_value_TextView);
        TextView intervalTextView = mAppCompatActivity.findViewById(R.id.interval_TextView);
        TextView updateByTimeTextView = mAppCompatActivity.findViewById(R.id.update_by_time_TextView);
        mSettingsPresenter = new SettingsPresenter(this, mAppCompatActivity);
        if (mSharedPreferences.contains(KEY_IS_CHECKED_INTERVAL)) {
            mIsUpdateByTimeChecked = mSharedPreferences.getBoolean(KEY_IS_CHECKED_INTERVAL, false);
            mSettingsPresenter.initCheckBoxState(mIsUpdateByTimeChecked);
        }
        if (mSharedPreferences.contains(KEY_LAST_CHECKED_TIME_INTERVAL)) {
            mLastCheckedItem[0] = mSharedPreferences.getInt(KEY_LAST_CHECKED_TIME_INTERVAL, 0);
            mSettingsPresenter.initIntervalValue(listItems[mLastCheckedItem[0]]);
        }
        updateByTimeCheckBox.setChecked(mIsUpdateByTimeChecked);
        mIntervalTimeTextView.setText(listItems[mLastCheckedItem[0]]);
        initToolBar();
        updateByTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsUpdateByTimeChecked = !mIsUpdateByTimeChecked;
                updateByTimeCheckBox.setChecked(mIsUpdateByTimeChecked);
            }
        });

        updateByTimeCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mIsUpdateByTimeChecked = isChecked;
                mSettingsPresenter.changeCheckBoxState(isChecked);
            }
        });

        intervalTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mAppCompatActivity);
                builder.setTitle(R.string.choose_time_text);

                builder.setSingleChoiceItems(listItems, mLastCheckedItem[0], new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mLastCheckedItem[0] = which;
                        mSettingsPresenter.setIntervalValue(listItems[which]);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    void onResume(final AppCompatActivity appCompatActivity) {
        mAppCompatActivity = appCompatActivity;
        mSettingsPresenter.attach(this, mAppCompatActivity);
    }

    void onPause() {
        mSettingsPresenter.detach();
        saveSharedPreferences();
        mAppCompatActivity = null;
    }

    private void initToolBar() {
        Toolbar toolbar = mAppCompatActivity.findViewById(R.id.toolbar_settings_activity);
        mAppCompatActivity.setSupportActionBar(toolbar);
        ActionBar actionBar = mAppCompatActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.settings);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public void updateIntervalTimeTextView(final String time) {
        mIntervalTimeTextView.setText(time);
    }

    private void saveSharedPreferences() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(KEY_LAST_CHECKED_TIME_INTERVAL, mLastCheckedItem[0]);
        editor.putBoolean(KEY_IS_CHECKED_INTERVAL, mIsUpdateByTimeChecked);
        editor.apply();
    }
}
