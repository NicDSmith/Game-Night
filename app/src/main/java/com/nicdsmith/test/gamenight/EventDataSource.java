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

public class EventDataSource {

    // Database fields
    private static SQLiteDatabase database;
    private SQLiteHelper dbHelper;
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

    public Event createEvent(String eventTitle,String eventDesc) {
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_EVENT_TITLE, eventTitle);
        values.put(SQLiteHelper.COLUMN_EVENT_DISCRIPTION, eventDesc);


        long insertId = database.insert(SQLiteHelper.TABLE_NAME, null,
                values);

        Cursor cursor = database.query(SQLiteHelper.TABLE_NAME,
                allColumns, SQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);

        cursor.moveToFirst();
        Event newEvent = cursorToEvent(cursor);
        cursor.close();
        return newEvent;
    }

    public void deleteEvent(Event event) {
        long id = event.getId();
        System.out.println("event deleted with id: " + id);
        database.delete(SQLiteHelper.TABLE_NAME, SQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<Event>();

        Cursor cursor = database.query(SQLiteHelper.TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Event event = cursorToEvent(cursor);
            events.add(event);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return events;
    }

    private Event cursorToEvent(Cursor cursor) {
        Event event = new Event();
        event.setId(cursor.getLong(0));
        event.setEventTitle(cursor.getString(1));
        event.setEventDesc(cursor.getString(2));

        return event;
    }
} 
