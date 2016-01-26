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
    private final int eventIDColumnIndex = 0;
    private final int eventTitleColumnIndex = 1;
    private final int eventDescColumnIndex = 2;
    private static final String TAG = EventDataSource.class.getSimpleName();


    private String[] allColumns = { SQLiteHelper.COLUMN_ID,
            SQLiteHelper.COLUMN_EVENT_TITLE, SQLiteHelper.COLUMN_EVENT_DISCRIPTION };

    public EventDataSource(Context context) {
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

    public void createEvent(String eventTitle,String eventDesc) {
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_EVENT_TITLE, eventTitle);
        values.put(SQLiteHelper.COLUMN_EVENT_DISCRIPTION, eventDesc);


        long insertId = database.insert(SQLiteHelper.TABLE_NAME, null,
                values);
        Log.i(TAG, "createEvent: insertID = " + insertId);


    }

    public void deleteEvent(Event event) {
        long id = event.getId();
        database.delete(SQLiteHelper.TABLE_NAME, SQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<Event>();

        Cursor cursor = database.query(SQLiteHelper.TABLE_NAME,
                allColumns, null, null, null, null, null);
        try {

            while (cursor.moveToNext()) {
                Event event = cursorToEvent(cursor);
                events.add(event);
            }
        } finally {
            //closing the cursor
            cursor.close();
        }
        return events;
    }

    private Event cursorToEvent(Cursor cursor) {
        Event event = new Event();
        event.setId(cursor.getLong(eventIDColumnIndex));
        event.setEventTitle(cursor.getString(eventTitleColumnIndex));
        event.setEventDesc(cursor.getString(eventDescColumnIndex));

        return event;
    }
} 
