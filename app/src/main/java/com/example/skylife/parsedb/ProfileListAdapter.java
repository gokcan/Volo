package com.example.skylife.parsedb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProfileListAdapter extends ArrayAdapter<ParseObject> {

    protected Context mContext;
    protected List<ParseObject> mParticipatedEvents;
    private String eventid;

    ViewHolder holder;

    public ProfileListAdapter ( Context context , List<ParseObject> events)
    {
        super(context, R.layout.fragment_profile_list , events);
        mContext = context;
        mParticipatedEvents = events;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent){

        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_profile_list,null);
            holder = new ViewHolder();

            holder.eventNameProfile= (TextView) convertView.findViewById(R.id.eventName);
            holder.eventContextProfile = (TextView) convertView.findViewById(R.id.eventContext);
            holder.imageEventProfile = (ImageView) convertView.findViewById(R.id.eventImage);

            convertView.setTag(holder);


        }
        else{

            holder= (ViewHolder) convertView.getTag();
        }

        String eventPartID = mParticipatedEvents.get(position).getObjectId();

        ParseQuery qryEvent = new ParseQuery("EventData");
        qryEvent.getInBackground(eventPartID, new GetCallback() {
            @Override
            public void done(ParseObject object, ParseException e) {

                String eventName = object.getString("eventName");
                holder.eventNameProfile.setText(eventName);

                String eventCon = object.getString("eventContext");
                holder.eventContextProfile.setText(eventCon);


                Picasso.with(getContext().
                        getApplicationContext()).
                        load(object.getParseFile("eventPhoto").
                                getUrl()).transform(new TransformationCircular()).
                        into(holder.imageEventProfile);

                eventid=object.getObjectId();

                Toast.makeText(getContext(), eventid, Toast.LENGTH_SHORT).show();

            }

            public void done(Object a , Throwable r) {



            }


        });


        return convertView;
    }
    public static class ViewHolder {

        TextView eventNameProfile;
        TextView eventContextProfile;
        ImageView imageEventProfile;
    }


}