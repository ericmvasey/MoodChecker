package com.apotheosis.moodchecker;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class MoodNotifyReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.notification_template_icon_bg)
                .setContentTitle("How are you doing right now?")
                .setDefaults(Notification.DEFAULT_SOUND)
                .setVibrate(new long[]{750})
                .setAutoCancel(true)
                .setContentText("Let me know!");

        Intent result = new Intent(context, MoodActivity.class);

        PendingIntent pending = PendingIntent.getActivity(context,
                0, result, PendingIntent.FLAG_ONE_SHOT);

        builder.setContentIntent(pending);
        nm.notify(builder.hashCode(), builder.build());
    }
}
