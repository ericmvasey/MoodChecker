package com.apotheosis.moodchecker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.Calendar;

public class MoodBootCompleteReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        SharedPreferences sp = context.getSharedPreferences("MoodChecker", 0);
        
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.set(Calendar.HOUR_OF_DAY, 10);

        Intent i = new Intent(context, MoodNotifyReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, i, 0);

        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_HALF_DAY,
                alarmIntent);

        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("registeredNotifications", true);
        editor.apply();
    }
}
