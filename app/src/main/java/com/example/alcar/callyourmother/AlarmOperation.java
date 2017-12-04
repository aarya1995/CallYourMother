package com.example.alcar.callyourmother;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

/**
 * Created by Jiaxin on 2017/11/16.
 */

public class AlarmOperation {
    public static void enableAlert(Context context, int type){
        AlarmManager mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent();
        intent.putExtra("type", type);
        intent.setClass(context, AlarmNotificationReceiver.class);
        PendingIntent mNotificationReceiverPendingIntent = PendingIntent.getBroadcast(
                context, type, intent, 0);

        Calendar calendar =Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.DAY_OF_WEEK,1);
        if (type == 0) { // low priority
            if(calendar.getTimeInMillis() < System.currentTimeMillis()) {
                calendar.add(Calendar.DAY_OF_YEAR, 7*4);
            }
        } else if (type == 1) { // median priority
            if(calendar.getTimeInMillis() < System.currentTimeMillis()) {
                calendar.add(Calendar.DAY_OF_YEAR, 7*2);
            }
        } else { // high priority
            if(calendar.getTimeInMillis() < System.currentTimeMillis()) {
                calendar.add(Calendar.DAY_OF_YEAR, 7);
            }
        }

        // Set alarm
        mAlarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), mNotificationReceiverPendingIntent);
    }
    public static void cancel(Context context,int type){
        AlarmManager mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent();
        intent.putExtra("type", type);
        intent.setClass(context, AlarmNotificationReceiver.class);
        PendingIntent mNotificationReceiverPendingIntent = PendingIntent.getBroadcast(
                context, type, intent, 0);
        mAlarmManager.cancel(mNotificationReceiverPendingIntent);
    }
}