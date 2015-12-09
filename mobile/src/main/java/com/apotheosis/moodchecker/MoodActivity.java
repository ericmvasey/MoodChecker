package com.apotheosis.moodchecker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

public class MoodActivity extends FragmentActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sp = getSharedPreferences("MoodChecker", 0);

        if(!sp.contains("registeredNotifications"))
        {
            AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(System.currentTimeMillis());
            c.set(Calendar.HOUR_OF_DAY, 11);

            Intent i = new Intent(MoodActivity.this, MoodNotifyReceiver.class);
            PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, i, 0);

            am.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_HALF_DAY,
                    alarmIntent);

            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("registeredNotifications", true);
            editor.apply();
        }

        Button happy = (Button) findViewById(R.id.happy);
        Button sad = (Button) findViewById(R.id.sad);

        happy.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new MoodAsyncTask(getApplicationContext()).execute(1);
            }
        });

        sad.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new MoodAsyncTask(getApplicationContext()).execute(0);
            }
        });
    }
}
