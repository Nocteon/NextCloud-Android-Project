package com.owncloud.android.ui.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.owncloud.android.utils.CalendarEvent;

import java.util.ArrayList;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "calendar_db";
    private static final int DATABASE_VERSION = 1;
    // table
    private static final String TABLE_CAL = "calendar_events";
    //Table Columns
    private static final String KEY_EVENT_DESCRIPTION = "description";
    private static final String KEY_EVENT_DATE_TIME = "date_time";
    private static final String KEY_EVENT_LOC = "loc";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //db connection
    @Override
    public void onConfigure(SQLiteDatabase database) {
        super.onConfigure(database);
        database.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase database){
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_CAL +
            "("
            + "event_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_EVENT_DESCRIPTION + " TEXT," +
            KEY_EVENT_DATE_TIME + " TEXT," +
            KEY_EVENT_LOC + " TEXT"
            + ")";
        database.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //implement update logic later if needed
    }

    public void saveEventDetails(String description, String datetime, String location) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // get the values
        values.put(KEY_EVENT_DESCRIPTION, description);
        values.put(KEY_EVENT_DATE_TIME, datetime);
        values.put(KEY_EVENT_LOC, location);

        //insert the values into the database
        database.insert(TABLE_CAL, null, values);

        //close the db
        database.close();
    }


}
