package com.example.submition4.alarm;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.submition4.R;
import com.example.submition4.data.room.ContentRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class AlarmReceiver extends BroadcastReceiver {

    public static final String DAILY_ALARM = "Daily Alarm";
    public static final String RELEASE_ALARM = "Release Alarm";
    public static final String EXTRA_TYPE = "EXTRA_ALARM";
    private static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    private static final int DAILY_CODE = 100;
    private static final int RELEASE_CODE = 101;
    private static ArrayList<String> releases = new ArrayList<>();
    private static final String TAG = "AlarmReceiver";

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onReceive(Context context, Intent intent) {
        releases.clear();
        String type = intent.getStringExtra(EXTRA_TYPE);
        String message = intent.getStringExtra(EXTRA_MESSAGE);
        String title = Objects.equals(type, DAILY_ALARM) ?DAILY_ALARM:RELEASE_ALARM;
        int idNotification = Objects.equals(type, DAILY_ALARM) ?DAILY_CODE:RELEASE_CODE;
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Log.d(TAG, "onReceive: ");
                if (idNotification==RELEASE_CODE){
                    ContentRepository repository = new ContentRepository(context);
                    releases.addAll(repository.getMovieReleaseToday());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                showNotificationAlarm(context, message,title,idNotification);
            }
        }.execute();
    }

    private void showNotificationAlarm(Context context, String message, String title, int notificationCode){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        if (notificationCode==DAILY_CODE){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"chanel_01")
                    .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setColor(ContextCompat.getColor(context,android.R.color.transparent))
                    .setVibrate(new long[]{1000,1000,1000,1000,1000})
                    .setSound(alarmSound);
            if(Build.VERSION.SDK_INT  >= Build.VERSION_CODES.O){
                NotificationChannel channel = new NotificationChannel("chanel_id"
                        ,"Daily Notification"
                        ,NotificationManager.IMPORTANCE_HIGH);
                builder.setChannelId("chanel_id");
                if (notificationManager != null) {
                    notificationManager.createNotificationChannel(channel);
                }
            }
            if (notificationManager != null) {
                notificationManager.notify(notificationCode,builder.build());
            }
        }else if (!releases.isEmpty()){
            @SuppressLint("DefaultLocale")
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle()
                    .setBigContentTitle(String.format("%d%s", releases.size(), context.getString(R.string.release_movie_today)))
                    .setSummaryText(context.getString(R.string.lets_come));
            for (String release :
                    releases) {
                inboxStyle.addLine(String.format("%s%s", release, context.getString(R.string.release_today)));
            }

            NotificationCompat.Builder notification = new NotificationCompat.Builder(context,"chanel_01")
                    .setContentTitle(releases.size()+context.getString(R.string.release_movie_today))
                    .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                    .setContentText(context.getString(R.string.new_movie))
                    .setGroup("group_key")
                    .setGroupSummary(true)
                    .setStyle(inboxStyle);
            if(Build.VERSION.SDK_INT  >= Build.VERSION_CODES.O){
                NotificationChannel channel = new NotificationChannel("chanel_id"
                        ,"Daily Notification"
                        ,NotificationManager.IMPORTANCE_HIGH);
                notification.setChannelId("chanel_id");
                if (notificationManager != null) {
                    notificationManager.createNotificationChannel(channel);
                }
            }
            if (notificationManager != null) {
                notificationManager.notify(notificationCode,notification.build());
            }
        }

    }

    public void setDailyAlarm(Context context){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_TYPE,DAILY_ALARM);
        intent.putExtra(EXTRA_MESSAGE, context.getString(R.string.see_more_movie));
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,7);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, DAILY_CODE,intent,0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
        }
        Toast.makeText(context, context.getString(R.string.Daily_on),Toast.LENGTH_SHORT).show();
    }

    public void setReleaseAlarm(Context context){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_TYPE,RELEASE_ALARM);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,8);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, RELEASE_CODE,intent,0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
        }
        Toast.makeText(context, context.getString(R.string.daily_release_on),Toast.LENGTH_SHORT).show();
    }

    public void cancelAlarm(Context context, String type){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context,AlarmReceiver.class);
        int idNotification = type.equals(DAILY_ALARM)?DAILY_CODE:RELEASE_CODE;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, idNotification,intent,0);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
        if (idNotification==RELEASE_CODE){
            Toast.makeText(context, context.getString(R.string.release_off),Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, context.getString(R.string.daily_off),Toast.LENGTH_SHORT).show();
        }
    }
}
