package com.example.skylife.parsedb;

/**
 * Created by SKYLIFE on 17/12/15.
 */

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

/*
 * An extension of ParseObject that makes
 * it more convenient to access information
 * about a given Event
 */

@ParseClassName("EventData")
public class EventModal extends ParseObject {

    public EventModal() {
        // A default constructor is required.
    }

    public String getEventName() {
        return getString("eventName");
    }

    public void setEventName(String eventName) {
        put("eventName", eventName);
    }

    public void setEventDescription(String eventDescription) {
        put("eventDescription", eventDescription);
    }

    public ParseUser getAuthor() {
        return getParseUser("createdBy");
    }

    public void setAuthor(ParseUser user) {
        put("createdBy", user);
    }

    public String getAvailability() {
        return getString("availability");
    }

    public void setAvailability(boolean flag) {
        put("availability", flag);
    }


    public ParseFile getPhotoFile() {
        return getParseFile("photo");
    }

    public void setPhotoFile(ParseFile file) {
        put("photo", file);
    }

}