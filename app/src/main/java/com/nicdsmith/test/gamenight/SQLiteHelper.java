package com.nicdsmith.test.gamenight;

/**
 * Created by nicsmith on 1/12/16.
 */
    import android.content.Context;
    import android.database.sqlite.SQLiteDatabase;
    import android.database.sqlite.SQLiteOpenHelper;
    import android.util.Log;

    public class SQLiteHelper extends SQLiteOpenHelper {
        private static final String TAG = SQLiteHelper.class.getSimpleName();;
        private static SQLiteHelper mInstance = null;
        public static final String EVENTS_TABLE_NAME = "events";
        public static final String EVENTS_COLUMN_ID = "_id";
        public static final String EVENTS_COLUMN_EVENT_TITLE = "title";
        public static final String EVENTS_COLUMN_EVENT_DISCRIPTION = "description";
        public static final String EVENTS_START_DATA = "start_date";
        public static final String EVENTS_END_DATA = "end_date";
        public static final String EVENTS_CYCLE_LENGTH = "cycle_length";


        private static final String DATABASE_NAME = "events.db";
        private static final int DATABASE_VERSION = 1;

        private Context mContext;

        // Database creation sql statement for EVENTS database
        private static final String EVENTS_DATABASE_CREATE = "CREATE TABLE "
                + EVENTS_TABLE_NAME + "(" + EVENTS_COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + EVENTS_COLUMN_EVENT_TITLE + " TEXT NOT NULL," + EVENTS_COLUMN_EVENT_DISCRIPTION
                + " TEXT NOT NULL," + EVENTS_START_DATA + " LONG," + EVENTS_END_DATA + " LONG," + EVENTS_CYCLE_LENGTH + " INTEGER);";

        //Database creation sql statement for PERSONS database
        public static final String PERSONS_TABLE_NAME = "persons";
        public static final String PERSONS_COLUMN_ID = "_id";
        public static final String PERSONS_COLUMN_FIRST_NAME = "first_name";
        public static final String PERSONS_COLUMN_LAST_NAME = "last_name";

        private static final String PERSONS_DATABASE_CREATE = "CREATE TABLE "
                + PERSONS_TABLE_NAME + "(" + PERSONS_COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PERSONS_COLUMN_FIRST_NAME + " TEXT NOT NULL," + PERSONS_COLUMN_LAST_NAME
                + " TEXT NOT NULL);";



        //Database creation sql statement for EVENT_PERSONS pivot table
        public static final String EVENT_PERSONS_TABLE_NAME = "event_persons";
        public static final String EVENT_PERSONS_PERSONS_COLUMN_ID = "persons_id";
        public static final String EVENT_PERSON_EVENT_COLUMN_ID = "event_id";
        public static final String PERSONS_POSITION = "person_position";

        private static final String EVENT_PERSONS_DATABASE_CREATE = "CREATE TABLE "
                + EVENT_PERSONS_TABLE_NAME +
                "(" + EVENT_PERSONS_PERSONS_COLUMN_ID + " INTEGER NOT NULL REFERENCES " + PERSONS_TABLE_NAME + "(" + PERSONS_COLUMN_ID + "), "
                + EVENT_PERSON_EVENT_COLUMN_ID + " INTEGER NOT NULL REFERENCES " + EVENTS_TABLE_NAME + "(" + EVENTS_COLUMN_ID + "),"
                + PERSONS_POSITION + " INTEGER," +
                " PRIMARY KEY(" + EVENT_PERSONS_PERSONS_COLUMN_ID + "," + EVENT_PERSON_EVENT_COLUMN_ID +"));";


        private static final String FILL_PEOPLE_TABLE_ADAM = "INSERT INTO " + PERSONS_TABLE_NAME + "(" + PERSONS_COLUMN_FIRST_NAME + "," + PERSONS_COLUMN_LAST_NAME +
                ") VALUES ('Adamn', 'Alien');";
        private static final String FILL_PEOPLE_TABLE_SEAN = "INSERT INTO " + PERSONS_TABLE_NAME + "(" + PERSONS_COLUMN_FIRST_NAME + "," + PERSONS_COLUMN_LAST_NAME +
                ") VALUES ('Seen','Breaches');";
        private static final String FILL_PEOPLE_TABLE_JORDAN = "INSERT INTO " + PERSONS_TABLE_NAME + "(" + PERSONS_COLUMN_FIRST_NAME + "," + PERSONS_COLUMN_LAST_NAME +
                ") VALUES ('Jaydan', 'Smavis');";
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
            Log.i(TAG, "onCreate: EVENT_PERSONS_DATABASE_CREATE = " + EVENT_PERSONS_DATABASE_CREATE);
            database.execSQL(EVENTS_DATABASE_CREATE);
            database.execSQL(PERSONS_DATABASE_CREATE);
            database.execSQL(EVENT_PERSONS_DATABASE_CREATE);
            database.execSQL(FILL_PEOPLE_TABLE_ADAM);
            database.execSQL(FILL_PEOPLE_TABLE_JORDAN);
            database.execSQL(FILL_PEOPLE_TABLE_SEAN);
            database.execSQL("INSERT INTO " + PERSONS_TABLE_NAME + "(" + PERSONS_COLUMN_FIRST_NAME + "," + PERSONS_COLUMN_LAST_NAME + ") VALUES ('spuder', 'min');");
            database.execSQL("INSERT INTO " + PERSONS_TABLE_NAME + "(" + PERSONS_COLUMN_FIRST_NAME + "," + PERSONS_COLUMN_LAST_NAME + ") VALUES ('bob', 'sagit');");
            database.execSQL("INSERT INTO " + PERSONS_TABLE_NAME + "(" + PERSONS_COLUMN_FIRST_NAME + "," + PERSONS_COLUMN_LAST_NAME + ") VALUES ('peter', 'file');");
            database.execSQL("INSERT INTO " + PERSONS_TABLE_NAME + "(" + PERSONS_COLUMN_FIRST_NAME + "," + PERSONS_COLUMN_LAST_NAME + ") VALUES ('philip', 'enis');");
            database.execSQL("INSERT INTO " + PERSONS_TABLE_NAME + "(" + PERSONS_COLUMN_FIRST_NAME + "," + PERSONS_COLUMN_LAST_NAME + ") VALUES ('private', 'dancer');");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(SQLiteHelper.class.getName(),
                    "Upgrading database from version " + oldVersion + " to "
                            + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + EVENTS_TABLE_NAME);
            onCreate(db);
        }

    }
