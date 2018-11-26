package com.example.sweethome.rssreader.time_update_work;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.sweethome.rssreader.service.UpdateByTimeService;

public class AlarmUpdateByTimeReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        Log.d("myLogs", "Get broadcast");
        Intent updateByTimeServiceIntent = UpdateByTimeService.newIntent(context);
        context.startService(updateByTimeServiceIntent);
    }
}
