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
 * Created by shyam on 7/2/2016.
 */
public class Adapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Activity activity;
    private List<Item> items;
    public Adapter(Activity activity,List<Item> items){
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
            convertView=inflater.inflate(R.layout.custom_layout,null);
        }
        TextView name_e=(TextView)convertView.findViewById(R.id.name_id);
        TextView date=(TextView)convertView.findViewById(R.id.date_id);
        TextView time=(TextView)convertView.findViewById(R.id.time_id);
        TextView venue=(TextView)convertView.findViewById(R.id.venue_id);
        Item item=items.get(position);

        name_e.setText(item.getName_e());
        date.setText(item.getDate());
        time.setText(item.getTime());
        venue.setText(item.getVenue());

        return convertView;
    }
}
