package com.example.noti_listener;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import java.util.ArrayList;

public class NLService extends NotificationListenerService {

    private String TAG = this.getClass().getSimpleName();
    private NLServiceReceiver nlservicereciver;
    ArrayList<About> rawlist=new ArrayList<About>();
    private int pAppicon=0;
    private String pTitle;
    private String pAbout=null;
    @Override
    public void onCreate() {
        super.onCreate();
        nlservicereciver = new NLServiceReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
        registerReceiver(nlservicereciver,filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(nlservicereciver);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {

        Log.i(TAG,"ID :" + sbn.getId() + "\t" + sbn.getNotification().tickerText + "\t" + sbn.getPackageName());
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i(TAG,"ID :" + sbn.getId() + "\t" + sbn.getNotification().tickerText +"\t" + sbn.getPackageName());
        Intent i = new  Intent("NOTIFICATION_LISTENER_EXAMPLE");
        i.putExtra("notification_event","onNotificationRemoved :" + sbn.getPackageName() + "\n");
        sendBroadcast(i);
    }

    class NLServiceReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getStringExtra("command").equals("list")){
                int i=1;
                for (StatusBarNotification sbn : NLService.this.getActiveNotifications()) {
                    Intent i2 = new  Intent("NOTIFICATION_LISTENER_EXAMPLE");
                    i2.putExtra("notification_event",i +" " + sbn.getPackageName() + "\n");

                    String pack=sbn.getPackageName();

                    Bundle extras = sbn.getNotification().extras;

                    pTitle = extras.getString("android.title");
                    int iconId = extras.getInt(Notification.EXTRA_SMALL_ICON);
                    String text = extras.getCharSequence("android.text").toString();
                    try {
                        PackageManager manager = getPackageManager();
                        Resources resources = manager.getResourcesForApplication(pack);

                        Drawable icon = resources.getDrawable(iconId);

                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }

                    if (extras.containsKey(Notification.EXTRA_PICTURE)) {
                        Bitmap bmp = (Bitmap) extras.get(Notification.EXTRA_PICTURE);
                    }

                    rawlist.add(new About(iconId,pTitle,text));

                    i2.putExtra("notification_event",text);
                    i2.putParcelableArrayListExtra("ImpArraylist", rawlist);
                    sendBroadcast(i2);
                    i++;
                }
                rawlist.clear();
            }
        }
    }
}
