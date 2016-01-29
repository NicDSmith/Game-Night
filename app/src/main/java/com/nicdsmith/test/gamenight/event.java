package com.nicdsmith.test.gamenight;

/**
 * Created by nicsmith on 1/10/16
 */
//defines our event object with setters and getters as well as a constructor
public class Event {
    String eventTitle;
    String eventDesc;
    long startDate;
    long endDate;
    int eventCycleLength;
    long id;

    public Event(){}
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

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public int getEventCycleLength() {
        return eventCycleLength;
    }

    public void setEventCycleLength(int eventCycleLength) {
        this.eventCycleLength = eventCycleLength;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
