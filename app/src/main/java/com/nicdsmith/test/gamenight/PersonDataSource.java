package com.nicdsmith.test.gamenight;

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

public class PersonDataSource {

    // Database fields
    private static SQLiteDatabase database;
    private SQLiteHelper dbHelper;
    private final int personIDColumnIndex = 0;
    private final int personFirstNameColumnIndex = 1;
    private final int personLastNameIndex = 2;


    private static final String TAG = PersonDataSource.class.getSimpleName();


    private String[] allColumns = { SQLiteHelper.PERSONS_COLUMN_ID,
            SQLiteHelper.PERSONS_COLUMN_FIRST_NAME, SQLiteHelper.PERSONS_COLUMN_LAST_NAME};

    public PersonDataSource(Context context) {
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

    public void createPerson(String firstName,String lastName) {
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.PERSONS_COLUMN_FIRST_NAME, firstName);
        values.put(SQLiteHelper.PERSONS_COLUMN_LAST_NAME, lastName);



        long insertId = database.insert(SQLiteHelper.PERSONS_TABLE_NAME, null,
                values);
        Log.i(TAG, "createPerson: insertID = " + insertId);


    }

    public void deletePerson(Person person) {
        long id = person.getId();
        database.delete(SQLiteHelper.PERSONS_TABLE_NAME, SQLiteHelper.PERSONS_COLUMN_ID
                + " = " + id, null);
    }

    public List<Person> getAllPersons() {
        List<Person> persons = new ArrayList<Person>();

        Cursor cursor = database.query(SQLiteHelper.PERSONS_TABLE_NAME,
                allColumns, null, null, null, null, null);
        try {

            while (cursor.moveToNext()) {
                Person person = cursorToPerson(cursor);
                persons.add(person);
            }
        } finally {
            //closing the cursor
            if(cursor != null) {
                cursor.close();
            }
        }
        return persons;
    }

    private Person cursorToPerson(Cursor cursor) {
        Person person = new Person();
        person.setId(cursor.getLong(cursor.getColumnIndex("_id")));
        person.setfirstName(cursor.getString(cursor.getColumnIndex("first_name")));
        person.setlastName(cursor.getString(cursor.getColumnIndex("last_name")));
        return person;
    }
}

