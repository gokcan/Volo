package com.example.skylife.parsedb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by berkesoysal8a on 18/12/15.
 */
public class EventAdapter extends ArrayAdapter<ParseObject> {

    protected Context mContext;
    protected List<ParseObject> mEvents;
    private String eventid;
    public EventAdapter ( Context context , List<ParseObject> events)
    {
        super(context, R.layout.fragment_home , events);
        mContext = context;
        mEvents = events;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        ViewHolder holder ;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_home,null);
            holder = new ViewHolder();

            holder.eventNameHP= (TextView) convertView.findViewById(R.id.eventName);
            holder.eventConHP = (TextView) convertView.findViewById(R.id.eventContext);
            holder.imageHP = (ImageView) convertView.findViewById(R.id.eventImage);

            convertView.setTag(holder);



        }
        else{
            holder= (ViewHolder) convertView.getTag();
        }
        ParseObject eventObject = mEvents.get(position);

        String eventName =  eventObject.getString("eventName");
        holder.eventNameHP.setText(eventName);

        String eventCon = eventObject.getString("eventContext");
        holder.eventConHP.setText(eventCon);

        Picasso.with(getContext().
                getApplicationContext()).
                load(eventObject.getParseFile("eventPhoto").
                        getUrl()).noFade().transform(new TransformationCircular()).
                into(holder.imageHP);

        eventid=eventObject.getObjectId();

        return convertView;
    }
    public static class ViewHolder {
        TextView eventNameHP;
        TextView eventConHP;
        ImageView imageHP;
    }


}