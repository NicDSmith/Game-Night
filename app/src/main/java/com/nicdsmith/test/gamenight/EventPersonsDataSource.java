package com.nicdsmith.test.gamenight;

/**
 * Created by nicsmith on 2/6/16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by nicsmith on 1/28/16.
 */

public class EventPersonsDataSource {

    // Database fields
    private static SQLiteDatabase database;
    private SQLiteHelper dbHelper;
    private final int EventPersonsPersonIDColumnIndex = 0;
    private final int EventPersonsEventIDColumnIndex = 1;
    private final int PersonPositionIndex = 2;


    private static final String TAG = EventPersonsDataSource.class.getSimpleName();


    private String[] allColumns = { SQLiteHelper.EVENT_PERSONS_PERSONS_COLUMN_ID,
            SQLiteHelper.EVENT_PERSONS_EVENT_COLUMN_ID, SQLiteHelper.PERSONS_POSITION};

    public EventPersonsDataSource(Context context) {
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

    public void createEventPersons(long eventID,long personID, int personPosition) {
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.EVENT_PERSONS_PERSONS_COLUMN_ID, personID);
        values.put(SQLiteHelper.EVENT_PERSONS_EVENT_COLUMN_ID, eventID);
        values.put(SQLiteHelper.PERSONS_POSITION, personPosition);




        long insertId = database.insert(SQLiteHelper.EVENT_PERSONS_TABLE_NAME, null,
                values);
        Log.i(TAG, "createEventPersons: insertID = " + insertId);


    }

    public void deleteEventPersons(long eventID,long personID) {

        database.delete(SQLiteHelper.EVENT_PERSONS_TABLE_NAME, SQLiteHelper.EVENT_PERSONS_EVENT_COLUMN_ID
                + " = " + eventID + " AND " + SQLiteHelper.EVENT_PERSONS_PERSONS_COLUMN_ID
                + " = " + personID, null);
    }

    public List<Long> getAllPersonsForEventID(long eventID) {
        List<Long> personIDs = new ArrayList<Long>();

        String WHERE = SQLiteHelper.EVENT_PERSONS_EVENT_COLUMN_ID + " = " + eventID;

        Cursor cursor = database.query(SQLiteHelper.EVENT_PERSONS_TABLE_NAME,
                allColumns, WHERE, null, null, null, null);
        try {

            while (cursor.moveToNext()) {
                personIDs.add(cursorToEventPersons(cursor).getPersonID());
            }
        } finally {
            //closing the cursor
            if(cursor != null) {
                cursor.close();
            }
        }
        return personIDs;
    }


    private EventPerson cursorToEventPersons(Cursor cursor) {
        EventPerson EventPerson = new EventPerson();
        EventPerson.setEventID(cursor.getLong(cursor.getColumnIndex(SQLiteHelper.EVENT_PERSONS_EVENT_COLUMN_ID)));
        EventPerson.setPersonID(cursor.getLong(cursor.getColumnIndex(SQLiteHelper.EVENT_PERSONS_PERSONS_COLUMN_ID)));
        EventPerson.setPersonPosition(cursor.getInt(cursor.getColumnIndex(SQLiteHelper.PERSONS_POSITION)));
        return EventPerson;
    }
}

