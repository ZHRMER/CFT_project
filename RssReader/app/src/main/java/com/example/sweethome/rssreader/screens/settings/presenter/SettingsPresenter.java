package com.example.sweethome.rssreader.screens.settings.presenter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.widget.Toast;

import com.example.sweethome.rssreader.time_update_work.AlarmUpdateByTimeReceiver;

public final class SettingsPresenter {
    private static final long MILLISECONDS_MINUTE = 60000;
    private static final long MINUTE_IN_HOUR = 60;
    private static final long HOUR_IN_DAY = 24;
    private static final String MATCH_MINUTE = "мин";
    private static final String MATCH_HOUR = "час";
    private ISettingsPresenterContract mView;
    private Context mContext;
    private boolean isUpdateByTimeChecked;
    private long mIntervalTime;

    public SettingsPresenter(final ISettingsPresenterContract view, final Context context) {
        mView = view;
        mContext = context;
        isUpdateByTimeChecked = false;
    }

    public void attach(final ISettingsPresenterContract view, final Context context) {
        mView = view;
        mContext = context;
    }

    public void detach() {
        mContext = null;
        mView = null;
    }

    public void initCheckBoxState(final boolean isCheck) {
        isUpdateByTimeChecked = isCheck;
    }

    public void initIntervalValue(final String time) {
        parseTime(time);
        mView.updateIntervalTimeTextView(time);
    }

    public void changeCheckBoxState(final boolean isChecked) {
        isUpdateByTimeChecked = isChecked;
        setAlarm();
    }

    public void setIntervalValue(final String time) {
        parseTime(time);
        mView.updateIntervalTimeTextView(time);
        setAlarm();
    }

    private void parseTime(final String time) {
        String[] inputStrings = time.split(" ");
        mIntervalTime = Long.parseLong(inputStrings[0]);
        if (inputStrings[1].contains(MATCH_MINUTE)) {
            mIntervalTime = mIntervalTime * MILLISECONDS_MINUTE;
        } else if (inputStrings[1].contains(MATCH_HOUR)) {
            mIntervalTime = mIntervalTime * MINUTE_IN_HOUR * MILLISECONDS_MINUTE;
        } else {
            mIntervalTime = mIntervalTime * HOUR_IN_DAY * MINUTE_IN_HOUR * MILLISECONDS_MINUTE;
        }
    }

    private void setAlarm() {
        final AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        final Intent intent = new Intent(mContext, AlarmUpdateByTimeReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.cancel(pendingIntent);
        if (isUpdateByTimeChecked) {
            Toast.makeText(mContext, "Alarm start " + mIntervalTime, Toast.LENGTH_SHORT).show();
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 3000, mIntervalTime, pendingIntent);
        } else {
            Toast.makeText(mContext, "Alarm cancelled", Toast.LENGTH_SHORT).show();
        }
    }
}
