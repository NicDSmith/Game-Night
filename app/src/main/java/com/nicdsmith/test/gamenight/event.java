package com.nicdsmith.test.gamenight;

/**
 * Created by nicsmith on 1/10/16.
 */
//defines our event object with setters and getters as well as a constructor
class Event {
    String eventTitle;
    String eventDesc;
    public Event(String eventTitle, String eventDesc){
        this.eventTitle = eventTitle;
        this.eventDesc = eventDesc;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }
}
