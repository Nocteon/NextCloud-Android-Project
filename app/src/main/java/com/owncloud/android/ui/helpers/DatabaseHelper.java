package com.owncloud.android.ui.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
    private static final String KEY_DISPLAYABLE_STR = "diplayable_event";

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
            KEY_EVENT_LOC + " TEXT," +
            KEY_DISPLAYABLE_STR + " TEXT"
            + ")";
        database.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //implement update logic later if needed
    }

    //save events to db
    public void saveEventDetails(String description, String datetime, String location, String displayableEvent) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // get the values
        values.put(KEY_EVENT_DESCRIPTION, description);
        values.put(KEY_EVENT_DATE_TIME, datetime);
        values.put(KEY_EVENT_LOC, location);
        values.put(KEY_DISPLAYABLE_STR, displayableEvent);

        //insert the values into the database
        database.insert(TABLE_CAL, null, values);

        //close the db
        database.close();
    }

    //retrieve elements from the database
    public ArrayList<String> getDisplayableEvents(String dateSubstr){
        SQLiteDatabase database = this.getReadableDatabase();
        ArrayList<String> eventsList = new ArrayList<>();

        Cursor cursor = database.rawQuery("SELECT " + KEY_DISPLAYABLE_STR + " FROM " + TABLE_CAL + " WHERE "
                                              + KEY_EVENT_DATE_TIME + " LIKE " + "\'%" + dateSubstr + "%\'", null);

        if (cursor != null){
            while (cursor.moveToNext()) {
                String eventData = cursor.getString(cursor.getColumnIndexOrThrow(KEY_DISPLAYABLE_STR));
                System.out.println(eventData);
                eventsList.add(eventData);
            }
            cursor.close();
        }
        else{
            eventsList.add("No Events");
        }
        database.close();
        return eventsList;
    }


}
