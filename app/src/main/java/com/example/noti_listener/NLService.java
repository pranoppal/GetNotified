package com.example.noti_listener;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class NLService extends NotificationListenerService {

    private String TAG = this.getClass().getSimpleName();
    private NLServiceReceiver nlservicereciver;
    ArrayList<About> rawlist=new ArrayList<About>();
    private int pAppicon=0;
    private String pTitle;
    private Bitmap bmp;
    private String text;
    private Drawable icon;
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
                    String pack=sbn.getPackageName();

                    Bundle extras = sbn.getNotification().extras;

                    pTitle = extras.getString("android.title");
                    int iconId = extras.getInt(Notification.EXTRA_SMALL_ICON);
                    //String text = extras.getCharSequence("android.textLines ").toString();
                    //String text = extras.getCharSequence("android.text").toString();
                    text=sbn.getNotification().extras.getCharSequence(Notification.EXTRA_TEXT).toString();

                    //if (extras.getCharSequenceArray("android.textLines") != null) {
                       // text = Arrays.toString(extras.getCharSequenceArray("android.textLines"));
                    //}
                    final PackageManager pm = getApplicationContext().getPackageManager();
                    ApplicationInfo ai;
                    try {
                        ai = pm.getApplicationInfo( pack, 0);
                    } catch (final PackageManager.NameNotFoundException e) {
                        ai = null;
                    }
                    final String applicationName = (String) (ai != null ? pm.getApplicationLabel(ai) : "(unknown)");
                    /*Drawable newicon=getPackageManager().getApplicationIcon(pack);
                    try {
                        PackageManager manager = getPackageManager();
                        Resources resources = manager.getResourcesForApplication(pack);

                        icon = resources.getDrawable(iconId);

                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                    */
                    //if (extras.containsKey(Notification.EXTRA_PICTURE)) {
                       // bmp = (Bitmap) extras.get(Notification.EXTRA_PICTURE);
                    //}
                    //bmp = sbn.getNotification().largeIcon;
                    Context remotePackageContext = null;
                    try {
                        remotePackageContext = getApplicationContext().createPackageContext(pack, 0);
                        Drawable icon = remotePackageContext.getResources().getDrawable(iconId);
                        if(icon !=null) {
                            bmp = ((BitmapDrawable) icon).getBitmap();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    /*if(bmp != null) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();
                        i2.putExtra("icon",byteArray);
                    }*/
                    Log.i(TAG, "onReceive: "+ bmp);

                    rawlist.add(new About(bmp,pTitle,text,applicationName));
                    i2.putParcelableArrayListExtra("ImpArraylist", rawlist);
                    sendBroadcast(i2);
                    i++;
                }
                rawlist.clear();
            }
        }
    }
}
