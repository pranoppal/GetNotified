package com.example.noti_listener;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomArrayAdapter extends ArrayAdapter<About> {
    private Context mcontext;
    private List<About> notiList=new ArrayList<About>();

    public CustomArrayAdapter(Context context, List<About> abouts){
        super(context,0,abouts);
        mcontext=context;
        notiList=abouts;
    }

    @Override
    public void clear() {
        super.clear();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem=convertView;
        if(listItem==null){
            listItem= LayoutInflater.from(mcontext).inflate(R.layout.list_item,parent,false);
        }
        About about=notiList.get(position);

        listItem.setBackgroundColor(Color.BLACK);

        ImageView imageView=(ImageView)listItem.findViewById(R.id.imageView_appicon);
        imageView.setImageBitmap(about.getmBitmap());


        TextView titleview=(TextView)listItem.findViewById(R.id.textView_title);
        titleview.setText(about.getmTitle());


        TextView aboutView=(TextView)listItem.findViewById(R.id.textView_about);
        aboutView.setText(about.getmAbout());

        TextView nameView=(TextView)listItem.findViewById(R.id.textView_name);
        nameView.setText(about.getmName());

        return listItem;
    }
}
