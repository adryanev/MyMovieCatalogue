package com.adryanev.dicoding.mymoviecatalogue.jobs;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import com.adryanev.dicoding.mymoviecatalogue.R;
import com.adryanev.dicoding.mymoviecatalogue.data.entities.upcoming.Result;
import com.adryanev.dicoding.mymoviecatalogue.ui.moviedetail.MovieDetailActivity;

import java.util.Calendar;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import timber.log.Timber;

public class ReleaseTodayReceiver extends BroadcastReceiver {


    public static final String NOTIFICATION_CHANNEL_ID = "1000011";

    private static int NOTIFICATION_ID = 1234;


    @Override
    public void onReceive(Context context, Intent intent) {

        int notifId = intent.getIntExtra("id", 0);
        String title = intent.getStringExtra("title");
        String id = intent.getStringExtra("movie_id");

        Timber.d("id: %s",String.valueOf(notifId));

        showAlarmNotification(context, title,id, notifId);
    }

    private void showAlarmNotification(Context context, String title, String id, int notifId) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra("movie_id", id);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notifId, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri alarmRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_access_alarm_black_24dp)
                .setContentTitle(title)
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setContentIntent(pendingIntent)
                .setContentText(context.getString(R.string.release_today_msg, title))
                .setSound(alarmRingtone);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.YELLOW);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 100});

            builder.setChannelId(NOTIFICATION_CHANNEL_ID);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        notificationManager.notify(notifId, builder.build());

    }
    public void setRepeatingAlarm(Context context, Result movies) {

            int delay = 0;
            cancelAlarm(context);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, ReleaseTodayReceiver.class);
            intent.putExtra("movie_id", movies.getId().toString());
            intent.putExtra("title", movies.getTitle());
            intent.putExtra("id", NOTIFICATION_ID);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 8);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            int SDK_INT = Build.VERSION.SDK_INT;
            if (SDK_INT > Build.VERSION_CODES.KITKAT && SDK_INT < Build.VERSION_CODES.M) {
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,  calendar.getTimeInMillis() + delay,
                        AlarmManager.INTERVAL_DAY,
                        pendingIntent
                );
            } else if (SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis() + delay, pendingIntent);
            }
            NOTIFICATION_ID += 1;
            delay += 5000;

    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(getPendingIntent(context));
    }

    private static PendingIntent getPendingIntent(Context context) {
        Intent alarmIntent = new Intent(context, ReleaseTodayReceiver.class);

        return PendingIntent.getBroadcast(context, 101, alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
