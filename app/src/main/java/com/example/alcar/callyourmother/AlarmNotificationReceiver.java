package com.example.alcar.callyourmother;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;

public class AlarmNotificationReceiver extends BroadcastReceiver {

    // Notification ID to allow for future updates
    private static final int[] MY_NOTIFICATION_IDs ={0,1,2} ;

    // Notification Text Elements
    private final CharSequence[] tickerTexts = {"Low Contacts","Med Contacts","High Contacts"};
    private final CharSequence contentTitle = "Call Your Mother";
    // Notification Sound and Vibration on Arrival
    private final Uri mSoundURI = Uri
            .parse("android.resource://com.example.alcar.callyourmother/"
                    + R.raw.alarm_rooster);
    private final long[] mVibratePattern = {0, 200, 200, 300};
    private Context mContext;
    private String mChannelID;

    @Override
    public void onReceive(Context context, Intent intent) {
        //get contacts name from database
        SQLiteHelper sQLiteHelper = new SQLiteHelper(context);
        ArrayList<ContactModel> contacts = sQLiteHelper.getAllRecords();
        ContactModel contact;
        mContext = context;
        int type = intent.getIntExtra("type",0);
        String contentText=new String();
        contentText = "Remember to call:";
        for(int i =0;i<contacts.size();i++){
            contact = contacts.get(i);
            int t = Integer.parseInt(contact.getPriority());
            if(t==type){
                contentText += "  " + contact.getFirstName()+" "+contact.getLastName()+";";
            }
        }
        if(contentText.length()==0) {//delete the alarm if no one has the priority
            return;
        }
        createNotificationChannel();

        Intent mNotificationIntent = new Intent(context, MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent mContentIntent = PendingIntent.getActivity(context, 0,
                mNotificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Build the Notification
        Notification.Builder notificationBuilder = new Notification.Builder(
                mContext,mChannelID).setTicker(tickerTexts[type])
                .setSmallIcon(android.R.drawable.stat_sys_warning)
                .setAutoCancel(true).setContentTitle(contentTitle)
                .setContentText(contentText).setContentIntent(mContentIntent);

        // Get the NotificationManager
        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        // Pass the Notification to the NotificationManager:
        mNotificationManager.notify(MY_NOTIFICATION_IDs[type],
                notificationBuilder.build());
        AlarmOperation.enableAlert(context,type);
    }

    private void createNotificationChannel() {
        NotificationManager mNotificationManager =
                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        mChannelID = mContext.getPackageName() + ".channel_01";

        // The user-visible name of the channel.
        CharSequence name = mContext.getString(R.string.channel_name);

        // The user-visible description of the channel
        String description = mContext.getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = new NotificationChannel(mChannelID, name, importance);

        // Configure the notification channel.
        mChannel.setDescription(description);
        mChannel.enableLights(true);

        // Sets the notification light color for notifications posted to this
        // channel, if the device supports this feature.
        mChannel.setLightColor(Color.RED);
        mChannel.enableVibration(true);
        mChannel.setVibrationPattern(mVibratePattern);

        mChannel.setSound(mSoundURI, (new AudioAttributes.Builder())
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build());

        mNotificationManager.createNotificationChannel(mChannel);
    }
}