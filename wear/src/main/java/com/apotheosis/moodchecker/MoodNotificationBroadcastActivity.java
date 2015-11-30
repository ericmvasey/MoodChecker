package com.apotheosis.moodchecker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Example shell activity which simply broadcasts to our receiver and exits.
 */
public class MoodNotificationBroadcastActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Intent i = new Intent();
        i.setAction("com.apotheosis.moodchecker.SHOW_NOTIFICATION");
        i.putExtra(MoodNotificationReciever.CONTENT_KEY, getString(R.string.title));
        sendBroadcast(i);
        finish();
    }
}
