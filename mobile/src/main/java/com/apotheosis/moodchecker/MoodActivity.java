package com.apotheosis.moodchecker;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class MoodActivity extends AppCompatActivity
{
    public TextView label;
    public EditText user;
    public SeekBar sb;
    public Spinner emotions;

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
            c.set(Calendar.HOUR_OF_DAY, 10);

            Intent i = new Intent(MoodActivity.this, MoodNotifyReceiver.class);
            PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, i, 0);

            am.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_HALF_DAY,
                    alarmIntent);

            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("registeredNotifications", true);
            editor.apply();
        }

        ArrayAdapter<CharSequence> items = ArrayAdapter.createFromResource(this,
                R.array.emotions, android.R.layout.simple_dropdown_item_1line);

        user = (EditText) findViewById(R.id.username);
        emotions = (Spinner) findViewById(R.id.emotion);
        emotions.setAdapter(items);

        sb = (SeekBar) findViewById(R.id.level);
        label = (TextView) findViewById(R.id.level_text);

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                switch(progress)
                {
                    case 0: label.setText("1 - Very Low"); break;
                    case 1: label.setText("2 - Low"); break;
                    case 2: label.setText("3 - Average"); break;
                    case 3: label.setText("4 - High"); break;
                    case 4: label.setText("5 - Very High"); break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });

        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String username = user.getText().toString();
                String emotion = (String) emotions.getSelectedItem();
                String level = Integer.toString(sb.getProgress()+1);

                new MoodAsyncTask(v.getContext()).execute(username, emotion, level);
            }
        });
    }
}
