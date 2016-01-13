package com.nicdsmith.test.gamenight;

/**
 * Created by nicsmith on 1/12/16.
 */
    import android.content.Context;
    import android.database.sqlite.SQLiteDatabase;
    import android.database.sqlite.SQLiteOpenHelper;
    import android.util.Log;

    public class SQLiteHelper extends SQLiteOpenHelper {

        public static final String TABLE_NAME = "events";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_EVENT_TITLE = "title";
        public static final String COLUMN_EVENT_DISCRIPTION = "description";

        private static final String DATABASE_NAME = "events.db";
        private static final int DATABASE_VERSION = 1;

        // Database creation sql statement
        private static final String DATABASE_CREATE = "create table "
                + TABLE_NAME + "(" + COLUMN_ID
                + " integer primary key autoincrement, " + COLUMN_EVENT_TITLE + " text not null," + COLUMN_EVENT_DISCRIPTION
                + " text not null);";

        public SQLiteHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase database) {
            database.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(SQLiteHelper.class.getName(),
                    "Upgrading database from version " + oldVersion + " to "
                            + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }

    }