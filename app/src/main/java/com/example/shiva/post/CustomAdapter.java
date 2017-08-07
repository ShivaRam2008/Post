package com.example.shiva.post;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by shiva on 7/23/2017.
 */

public class CustomAdapter extends BaseAdapter {

    private ArrayList<MessageDetails> data;
    Context c;
    CustomAdapter(ArrayList<MessageDetails> data,Context c) {
        this.data = data;
        this.c = c;

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if(v==null){
            LayoutInflater vi = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.lists,null);
        }
        TextView thisIsName = (TextView)v.findViewById(R.id.nameOfPoster);
        TextView thisIsArea = (TextView)v.findViewById(R.id.areafield);
        TextView thisIsNews = (TextView)v.findViewById(R.id.postingNews);
        TextView thisIsTime=(TextView)v.findViewById(R.id.timeOfPosting);
        MessageDetails msg = data.get(position);
        thisIsName.setText(msg.getMyName());
        thisIsArea.setText(msg.getMyArea());
        thisIsNews.setText(msg.getMyPost());
        thisIsTime.setText(msg.getTime());

        return v;
    }
}
