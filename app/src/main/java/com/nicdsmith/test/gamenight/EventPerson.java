package com.nicdsmith.test.gamenight;

/**
 * Created by nicsmith on 2/6/16.
 */
public class EventPerson {
    long eventID;
    long personID;
    int personPosition;

    public EventPerson(){}
    public EventPerson(long eventID, long personID, int personPosition){
        this.eventID = eventID;
        this.personID = personID;
        this.personPosition = personPosition;

    }

    public void setEventID(long eventID) {
        this.eventID = eventID;
    }

    public long getEventID() {
        return eventID;
    }

    public void setPersonID(long personID) {
        this.personID = personID;
    }

    public long getPersonID() {
        return personID;
    }

    public void setPersonPosition(int personPosition) {
        this.personPosition = personPosition;
    }

    public int getPersonPosition() {
        return personPosition;
    }
}
