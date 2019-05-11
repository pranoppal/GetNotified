package com.example.noti_listener;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import java.util.ArrayList;

public class MainActivity extends Activity {

    private String TAGm = this.getClass().getSimpleName();

    private NotificationReceiver nReceiver;
    ArrayList<About> aboutArrayList=new ArrayList<>();
    Context mcontext;
    ListView listView;
    CustomArrayAdapter pcustomArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mcontext=this;
        listView = (ListView) findViewById(R.id.noti_list);

        nReceiver = new NotificationReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("NOTIFICATION_LISTENER_EXAMPLE");
        registerReceiver(nReceiver,filter);

        Intent i = new Intent("NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
        i.putExtra("command","list");
        sendBroadcast(i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(nReceiver);
    }

    public void buttonClicked(View v){


        if(v.getId() == R.id.btnListNotify){
            Intent i = new Intent("NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
            i.putExtra("command","list");
            sendBroadcast(i);
            }


    }
    class NotificationReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            aboutArrayList = intent.getParcelableArrayListExtra("ImpArraylist");
            Log.i(TAGm,"broadcast recieved"+ aboutArrayList);
            pcustomArrayAdapter=new CustomArrayAdapter(mcontext,aboutArrayList);
            listView.setAdapter(pcustomArrayAdapter);

        }
    }



}
