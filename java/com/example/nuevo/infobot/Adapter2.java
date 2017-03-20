package com.example.nuevo.infobot;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by shyam on 7/20/2016.
 */
public class Adapter2 extends BaseAdapter {
    private LayoutInflater inflater;
    private Activity activity;
    private List<Item2> items;
    public Adapter2(Activity activity,List<Item2> items){
        this.activity=activity;
        this.items=items;
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater==null){
            inflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView==null){
            convertView=inflater.inflate(R.layout.custom2_layout,null);
        }
        TextView subject=(TextView)convertView.findViewById(R.id.subject_id);
        TextView year=(TextView)convertView.findViewById(R.id.year_id);
        TextView date=(TextView)convertView.findViewById(R.id.date_id);
        TextView time=(TextView)convertView.findViewById(R.id.time_id);
        TextView venue=(TextView)convertView.findViewById(R.id.venue_id);
        Item2 item=items.get(position);

        subject.setText(item.getSubject());
        year.setText(item.getYear());
        date.setText(item.getDate());
        time.setText(item.getTime());
        venue.setText(item.getVenue());

        return convertView;
    }
}
