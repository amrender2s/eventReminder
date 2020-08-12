package com.example.sonu.eventreminder;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.text.DateFormat;
import java.util.Date;
import java.util.Random;

public class Receiver extends BroadcastReceiver {

    private String channelId;
    private String channelName;
    private NotificationManager mManager;

    @Override
    public void onReceive(final Context context, final Intent intent) {
        Runnable r=new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    String t2=intent.getStringExtra("time");
                    String location=intent.getStringExtra("location");
                    String duration=intent.getStringExtra("duration");
                    String about=intent.getStringExtra("data");
                    String info=intent.getStringExtra("info");
                    channelId="com.example.sonu.eventreminder";
                    channelName="myChannel";

                    String current = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.SHORT).format(new Date());
                    Log.i("t2",t2);
                    Log.i("current",current);
                    if(t2.equals(current)){
                        Log.i("current",current);
                        Intent notificationIntent = new Intent(context, AddEventActivity.class);
                        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        mManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
                        NotificationChannel androidChannel;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            Log.i("channel","channel");
                            androidChannel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
                            androidChannel.enableLights(true);
                            androidChannel.enableVibration(true);
                            androidChannel.setLightColor(Color.GREEN);
                            androidChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

                            mManager.createNotificationChannel(androidChannel);
                        }
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            Log.i("oreo","oreo");
                            Notification.Builder builder = new Notification.Builder(context, channelId)
                                    .setSmallIcon(R.drawable.location)
                                    .setContentTitle("You have an Upcoming event : " + about + " for " +duration )
                                    .setContentText(info + "\t at " +location)
                                    .setAutoCancel(true)
                                    .setOngoing(true)
                                    .setContentIntent(contentIntent)
                                    .setTicker("Hey Your event Notification")
                                    .setSubText("Tap to view");
                            mManager.notify(new Random().nextInt(50), builder.build());
                            context.stopService(intent);
                        }
                        else {
                            Log.i("noget","noget");
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                                    .setSmallIcon(R.drawable.location)
                                    .setContentTitle("You have an Upcoming event : " + about + " for " +duration )
                                    .setContentText(info + "\t at " +location)
                                    .setAutoCancel(true)
                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                    .setOngoing(true)
                                    .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                                    .setTicker("Hey Your event Notification")
                                    .setContentIntent(contentIntent)
                                    .setSubText("Tap to view");
                            mManager.notify(new Random().nextInt(50), builder.build());
                            context.stopService(intent);
                        }
                    }
                }
            }};

        Thread t=new Thread(r);
        t.start();
    }
}
