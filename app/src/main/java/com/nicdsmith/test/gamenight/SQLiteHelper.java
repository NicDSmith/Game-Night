package com.nicdsmith.test.gamenight;

/**
 * Created by nicsmith on 1/12/16.
 */
    import android.content.Context;
    import android.database.sqlite.SQLiteDatabase;
    import android.database.sqlite.SQLiteOpenHelper;
    import android.util.Log;

    public class SQLiteHelper extends SQLiteOpenHelper {
        private static SQLiteHelper mInstance = null;
        public static final String TABLE_NAME = "events";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_EVENT_TITLE = "title";
        public static final String COLUMN_EVENT_DISCRIPTION = "description";

        private static final String DATABASE_NAME = "events.db";
        private static final int DATABASE_VERSION = 1;

        private Context mContext;

        // Database creation sql statement
        private static final String DATABASE_CREATE = "CREATE TABLE "
                + TABLE_NAME + "(" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EVENT_TITLE + " TEXT NOT NULL," + COLUMN_EVENT_DISCRIPTION
                + " TEXT NOT NULL);";

        private SQLiteHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.mContext = context;
        }

        public static SQLiteHelper getInstance(Context context){

            if (mInstance == null) {
                mInstance = new SQLiteHelper(context.getApplicationContext());
            }
            return mInstance;
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
