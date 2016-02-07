package com.nicdsmith.test.gamenight;

/**
 * Created by nicsmith on 1/12/16.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class EventDataSource {

    // Database fields
    private static SQLiteDatabase database;
    private SQLiteHelper dbHelper;
    

    private static final String TAG = EventDataSource.class.getSimpleName();


    private String[] allColumns = { SQLiteHelper.EVENTS_COLUMN_ID,
            SQLiteHelper.EVENTS_COLUMN_EVENT_TITLE, SQLiteHelper.EVENTS_COLUMN_EVENT_DISCRIPTION, SQLiteHelper.EVENTS_START_DATA, SQLiteHelper.EVENTS_END_DATA, SQLiteHelper.EVENTS_CYCLE_LENGTH };

    public EventDataSource(Context context) {
        Log.i(TAG, "EventDataSource: ");
            dbHelper = SQLiteHelper.getInstance(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public static SQLiteDatabase getDatabase() {
        return database;
    }

    public long createEvent(String eventTitle,String eventDesc, long eventStartDate, long eventEndDate, int eventCycleLength) {
        Log.i(TAG, "createEvent: creating event");
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.EVENTS_COLUMN_EVENT_TITLE, eventTitle);
        values.put(SQLiteHelper.EVENTS_COLUMN_EVENT_DISCRIPTION, eventDesc);
        values.put(SQLiteHelper.EVENTS_START_DATA, eventStartDate);
        values.put(SQLiteHelper.EVENTS_END_DATA, eventEndDate);
        values.put(SQLiteHelper.EVENTS_CYCLE_LENGTH, eventCycleLength);


        long insertId = database.insert(SQLiteHelper.EVENTS_TABLE_NAME, null,
                values);
        Log.i(TAG, "createEvent: insertID = " + insertId);
        return insertId;


    }

    public void deleteEvent(Event event) {
        long id = event.getId();
        database.delete(SQLiteHelper.EVENTS_TABLE_NAME, SQLiteHelper.EVENTS_COLUMN_ID
                + " = " + id, null);
    }

    public Event getEventByID(long id){
        String WHERE = SQLiteHelper.EVENTS_COLUMN_ID + " = " + id;

        Cursor cursor = database.query(SQLiteHelper.EVENTS_TABLE_NAME,
                allColumns, WHERE, null, null, null, null);
        if(cursor.moveToFirst()) {
            return cursorToEvent(cursor);
        }else{
            return null;
        }
    }
    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<Event>();

        Cursor cursor = database.query(SQLiteHelper.EVENTS_TABLE_NAME,
                allColumns, null, null, null, null, null);
        try {

            while (cursor.moveToNext()) {
                Event event = cursorToEvent(cursor);
                events.add(event);
            }
        } finally {
            //closing the cursor
            if(cursor != null) {
                cursor.close();
            }
        }
        return events;
    }

    private Event cursorToEvent(Cursor cursor) {
        Event event = new Event();
        event.setId(cursor.getLong(cursor.getColumnIndex("_id")));
        event.setEventTitle(cursor.getString(cursor.getColumnIndex("title")));
        event.setEventDesc(cursor.getString(cursor.getColumnIndex("description")));
        event.setStartDate(cursor.getLong(cursor.getColumnIndex("start_date")));
        event.setEndDate(cursor.getLong(cursor.getColumnIndex("end_date")));
        event.setEventCycleLength(cursor.getInt(cursor.getColumnIndex("cycle_length")));
        return event;
    }
} 
